package ie.engine.testing;

import ie.engine.implementations.AudioEventLL.AudioEvent;
import ie.engine.interaction.AudioSync;
import ie.engine.loading.SongInfo;
import processing.core.PApplet;

public class NewScene  extends PApplet{

    AudioSync audioSync;
    SongInfo songInfo; 
    AudioEvent tempEvent;
    boolean wasBeat;

    public void settings(){
        size(480, 480, P3D);
    }
    public void setup(){
        String songName = "assets/audio/songs/nrgq.wav"; 
        audioSync = new AudioSync(this, songName);
        audioSync.play();
        songInfo = new SongInfo(songName);
    }
    public void draw(){
        camera();
        tempEvent = audioSync.isBeat();
        wasBeat = audioSync.wasBeat;
        // never use tempEvent outside of a if(wasBeat) - it will be null otherwise
        // may remove wasbeat and just check if tempEvent == null?? maybe
        if(wasBeat){
            System.out.println("Beat " + tempEvent.frame + " of volume: " + tempEvent.volume);
        } else {

        }
        rect(0, 0, 100, 100);
    }
    static String[] gameArgs = {"Main"};
    public static void main(String[] args){
        PApplet.runSketch(gameArgs, new NewScene());
    }
}
