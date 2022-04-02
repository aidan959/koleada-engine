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
    float lerpValue;
    public void draw(){
        clear();
        camera();
        tempEvent = audioSync.isBeat();
        wasBeat = audioSync.wasBeat;
        background(0,0,tempEvent.volume*600);
        if(wasBeat){
            System.out.println("Beat " + tempEvent.frame + " of volume: " + tempEvent.volume);
        } else {
            //System.out.println("SHOULD BE BLANK Beat " + tempEvent.frame + " of volume: " + tempEvent.volume);

        }
        rect(0, 0, 100, 100);
    }
    static String[] gameArgs = {"Main"};
    public static void main(String[] args){
        PApplet.runSketch(gameArgs, new NewScene());
    }
}
