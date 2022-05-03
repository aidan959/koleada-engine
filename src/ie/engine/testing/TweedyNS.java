package ie.engine.testing;


import ie.engine.Scene;
import ie.engine.implementations.AudioEventLL.AudioEvent;
import ie.engine.interaction.AudioSync;
import ie.engine.loading.SongInfo;
import ie.engine.maths.Animation;

import ie.engine.objects.Waves;
import processing.core.PApplet;

public class TweedyNS  extends Scene{
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
    float circleFill = 0;
    float angle = 0;

    public void draw(){
        super.draw();
        noStroke();
        camera();
        lights();
        tempEvent = audioSync.isBeat();
        wasBeat = audioSync.wasBeat;
        background(0,0, smoothBackground);
        smoothCircle = lerp(smoothCircle, (tempEvent.volume * 1000) + 5, 0.05f );

        // int x = 0;
        
        // sphere grid

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                fill(0, 255, 153);
                float mapX = map(i, 0, 12, 0, width);
                float mapY = map(j, 0, 12, 0, height);
                // circle(mapX+20, mapY+20, 40);

                pushMatrix();
                translate(mapX+25, mapY+25, -50);
                sphere(smoothCircle);
                popMatrix();
            }
        }

        // cuboids

        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                pushMatrix();
                translate(width*i/4, height*j/4, map(audioSync.song.positionFrame(), 0, 100000, 50, 100));
                box(70);
                popMatrix();
            }
        }

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

            // center sphere
            pushMatrix();
            // fill(255, 0, 119);
            translate(width/2, height/2, -50);
            sphere(smoothCircle);
            popMatrix();
        } else {
            
            smoothBackground = lerp(0.8f,0, smoothBackground );
            // angle is in radians (in map change sin(angle) -> sin(degrees(angle)) 
            //                      if you want degrees)
            angle += 0.02f;
            pushMatrix();
            
            // nice red
            // fill(255, 0, 119);

            // first var in map is what you're converting with range (var2->var3) 
            // to the range (var4->var5) for fill() etc
            fill(map(sin(angle), -1, 1, 0, 255), map(sin(angle*3), -1, 1, 0, 255), map(cos(angle), -1, 1, 0, 255));
            translate(width/2+25, height/2, -50);
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
        PApplet.runSketch(gameArgs, new TweedyNS());
    }
}
