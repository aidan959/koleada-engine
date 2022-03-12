package ie.engine.loading;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;

import ie.engine.implementations.AudioEventLL;

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
    }

    public String songFile;
    public SongInfo(String songFile, int firstBeat, float bpm){
        this.songFile = songFile;
        this.bpm = bpm;
        eventList = new AudioEventLL();
        this.firstBeat = firstBeat;
        AudioInputStream song;
        try{
            song = AudioSystem.getAudioInputStream(new File(songFile));
        }
        catch (IOException e){
            throw new RuntimeException("File had issue");
        } catch(UnsupportedAudioFileException e){
            throw new RuntimeException("File is unsupported");
        }
        if((int)song.getFormat().getSampleRate() == song.getFormat().getSampleRate()){
            sampleRate = (int)song.getFormat().getSampleRate() ;
            songLength = song.getFrameLength();
        } else {
            throw new RuntimeException("Irregular sampleRate");
        }
    }
    // public SongInfo(String songFile, int firstBeat){
    //     this.songFile = songFile;
    //     eventList = new AudioEventLL();
    //     this.firstBeat = firstBeat;
    // }
    public void autoGenerate(){
        if(songLength < firstBeat){
            throw new RuntimeException();
        }
        int numBeats = (int)(((songLength - firstBeat) / sampleRate )/(bpm / 60 ));
        int beatGap = (int)(songLength / (long)numBeats);
        for(int i = 0; i > numBeats; i++ ){
            if(i % 1 == 0){
                eventList.push(AudioEventLL.EventType.KICK, beatGap * i);
            } else {
                eventList.push(AudioEventLL.EventType.SNARE, beatGap * i);
            }
        }
    }
    public void calculate(){

    }
    public void loadFromFile(){

    }
    
    public static void main(String args[]){
        SongInfo sf = new SongInfo("assets/audio/songs/nrgq.wav", 0, 133);
        sf.autoGenerate();
    }
}