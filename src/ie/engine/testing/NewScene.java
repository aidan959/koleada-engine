package ie.engine.testing;


import ie.engine.Scene;
import ie.engine.implementations.AudioEventLL.AudioEvent;
import ie.engine.interaction.AudioSync;
import ie.engine.loading.SongInfo;
import ie.engine.maths.Animation;

import ie.engine.objects.Waves;
import processing.core.PApplet;

public class NewScene  extends Scene{
    public void settings(){
        super.settings();
    }
    public void setup(){
        super.setup();
        String songName = "assets/audio/songs/nrgq.wav"; 
        audioSync = new AudioSync(this, songName);
        audioSync.play();
        songInfo = new SongInfo(songName);
        lastEvent = audioSync.songInfo.eventList.blank;
        // bridge to chorus -- will start from bridge1
        audioSync.song.jumpFrame((int)AudioSync.songParts.BRIDGE1.get());
    }

    float lerpValue;
    Animation testAnimation;
    float smoothBackground;
    float smoothCircle = 0;

    public void draw(){
        super.draw();
        noStroke();
        camera();
        lights();
        tempEvent = audioSync.isBeat();
        wasBeat = audioSync.wasBeat;
        background(0,0, smoothBackground);
        smoothCircle = lerp(smoothCircle, (tempEvent.volume * 1000) + 5, 0.05f );        
        // can draw stuff outside the if(wasBeat) too
        if(wasBeat){
            // runs if there was a beat provided

            // only execute before / after first verse etc
            // if(audioSync.song.positionFrame() < AudioSync.songParts.VERSE1.get() ){

            // how close to next frame
            // map(audioSync.song.positionFrame(),0, AudioSync.songParts.VERSE1.get(), 0, 100 );

            // grow from 0 to 100
            // map(audioSync.song.positionFrame(),AudioSync.songParts.BRIDGE1.get(), AudioSync.songParts.CHORUS1.get(), 0, 100 );

            smoothBackground = lerp(0.8f,tempEvent.volume, smoothBackground );
            pushMatrix();
            translate(width/2, height/2, -50);
            sphere(smoothCircle);
            popMatrix();
        } else {
            
            smoothBackground = lerp(0.8f,0, smoothBackground );
            pushMatrix();
            translate(width/2, height/2, -50);
            sphere(smoothCircle);
            popMatrix();
        }
        // System.out.println(bp.process());
        
        // debug camera
        camera();
        hint(DISABLE_DEPTH_TEST); 
        noLights();

        if(debugger.doDebug){
            debugDictionary.get("frametime").updateValue(debugger.getFrameTime());
            debugDictionary.get("framerate").updateValue(frameRate);
            debugDictionary.get("avgbeattime").updateValue((((float)audioSync.averageBeatTime)/(float)(songInfo.sampleRate)));
            debugger.draw();
        }
    }
    public void keyPressed(){
        super.keyPressed();
        if(key == 'n' ){
            debugger.doDebug = !debugger.doDebug;
        }
    }
    static String[] gameArgs = {"Main"};
    public static void main(String[] args){
        PApplet.runSketch(gameArgs, new NewScene());
    }
}
