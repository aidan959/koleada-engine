package ie.engine.loading;

import javax.management.RuntimeErrorException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.*;

import ie.engine.implementations.AudioEventLL;
import ie.engine.implementations.AudioEventLL.EventType;

public class SongInfo{
    public float bpm;
    public AudioEventLL eventList;
    public int firstBeat;
    public long songLength;
    public int sampleRate = 44100;
    public SongInfo(float bpm, int firstBeat, int songLength, int sampleRate) {
        this.bpm = bpm;
        this.firstBeat = firstBeat;
        this.songLength = songLength;
        this.sampleRate = sampleRate;
        eventList = new AudioEventLL();
    }

    public String songFile;
    public String analysisFile;
    public String backupAnalysisFile;

    public SongInfo(String songFile){
        this.songFile = songFile;
        analysisFile = songFile + ".anl";
        backupAnalysisFile =  analysisFile + ".bk";
        eventList = new AudioEventLL();
        AudioInputStream song;
        try{
            song = AudioSystem.getAudioInputStream(new File(songFile));
        }
        catch (IOException e){
            throw new RuntimeException("File had issue");
        } catch(UnsupportedAudioFileException e){
            throw new RuntimeException("File is unsupported");
        }
        if(!analysisFileExists()){
            try{
                autoGenerate();
            } catch (Exception e){
                System.out.println("Python file failed to generate - checking now for backup file");
                if(backupFileExists()){
                        revertToBackup();
                }
            }
        }
        loadFromFile();
        if((int)song.getFormat().getSampleRate() == song.getFormat().getSampleRate()){
            sampleRate = (int)song.getFormat().getSampleRate() ;
            songLength = song.getFrameLength();
        } else {
            throw new RuntimeException("Irregular sampleRate");
        }
    }
    public boolean analysisFileExists(){
        File f = new File(analysisFile);
        return f.exists();
    }
    public boolean backupFileExists(){
        File f = new File(backupAnalysisFile);
        return f.exists();
    }
    public boolean songFileExists(){
        File f = new File(songFile);
        return f.exists();
    }
    public void revertToBackup(){
        if(backupFileExists()){
            try{
                InputStream backupStream =new FileInputStream(new File(backupAnalysisFile));
                Path newFile = Paths.get(analysisFile);
                System.out.println("Reverting to backup file.");
                Files.copy(backupStream, newFile, REPLACE_EXISTING);

            } catch (FileNotFoundException e){
                System.err.println("File " + backupAnalysisFile +" not found.");
                
                throw new RuntimeException(e);
            } catch(IOException e){
                System.err.println("Failed to copy " + backupAnalysisFile + " to "+ analysisFile +" not found.");

                throw new RuntimeException(e);
                
            }
        } else {
            throw new RuntimeException("No backup file");
        }
    }
    // public SongInfo(String songFile, int firstBeat){
    //     this.songFile = songFile;
    //     eventList = new AudioEventLL();
    //     this.firstBeat = firstBeat;
    // }
    public String pythonPathify(String fileName){
        return "src/python/" + fileName;
    }
    public boolean autoGenerate() throws Exception{
        ProcessBuilder processBuilder = new ProcessBuilder("python", pythonPathify("beatdetection.py"), songFile, analysisFile, backupAnalysisFile);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        BufferedReader n = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null)
            System.out.println("tasklist: " + line);
        process.waitFor();
        int exitVal = process.exitValue();
        if(process.exitValue() == 1){
            throw new RuntimeException("f");
        } else if(process.exitValue() == 100){
            return true;
        }
        return false;
    }
    public void calculate(){

    }
    public void loadFromFile(){
        String[] lines; 
        try (BufferedReader br = new BufferedReader(new FileReader(songFile+".anl"))) {
            String a;
            while ( (a = br.readLine()) != null) {
                lines = a.split("\t");
                eventList.push(EventType.KICK, Integer.parseInt(lines[0]), Float.parseFloat(lines[1]));
            }
        } catch(IOException e){
            throw new RuntimeException("a");
        }
    }
    
    public static void main(String args[]){
        SongInfo sf = new SongInfo("assets/audio/songs/nrgq.wav");
        
    }
}