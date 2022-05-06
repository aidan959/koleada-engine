package ie.engine.loading;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

import processing.sound.*;

public class SongAlyzer {
    File songFile;
    AudioInputStream song;
    int bytesPerFrame;
    int bufferSize = 1024;
    int numBytes;
    byte[] audioBytes;
    SongAlyzer(String songFile){
        
        try{
            this.songFile = new File(songFile);   
            song = AudioSystem.getAudioInputStream(this.songFile);
        }
        catch(IOException e){
            throw new RuntimeException("File failed to load");
        } catch(UnsupportedAudioFileException e){
            throw new RuntimeException("Unsupported Audio file used.");
        }
        bytesPerFrame = song.getFormat().getFrameSize();
        numBytes = bytesPerFrame * numBytes;

    }

    public static void main(String[] args){

        SongAlyzer sa = new SongAlyzer("assets/audio/songs/nrgq.wav");
        sa.process();
    }
    public void bl(int nbands, int bandlimit){
        //Math.floor(bandlimits[1]);
    }
    public void br(int nbands){
    }
    public int[][] filterBank(int bands){
        FFT[] fft = new FFT[bands];
        int[][] output = new int[bands][1]; 
        int[] bandlimits = new int[bands];
        int maxFreq = 4096;
        bandlimits[0] = 0;
        bandlimits[1] = 200;

        for(int i=2; i < bands; i ++){
            bandlimits[i] = bandlimits[i-1] * 2;  
        }
        for(int i=0; i < bands; i++){
            output[i][0] = 1;
        }
        return output;
    }
    public void smoothing(){

    }
    public void differentiateRect(){

    }
    public void combFilter(){

    }
    int[][] arrayofFrquencies;
    public void process(){
        // int bands = 6;
        // // First split audio file into 
        // // 0-200, 200-400,400-800,800-1600,1600-3200 hz
        // arrayofFrquencies = filterBank(bands);
        // // next we smooth our signal to help identify sudden changes
        // // low pass each of our FFTs
        // for(int i = 0; i < bands; i++){
        //     smoothing();
        //     // Differentiate each 
        //     differentiateRect();

        //     combFilter();
        // }
    }
}