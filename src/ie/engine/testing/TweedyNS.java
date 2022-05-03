package ie.engine.testing;


import ie.engine.Scene;
import ie.engine.implementations.AudioEventLL.AudioEvent;
import ie.engine.interaction.AudioSync;
import ie.engine.loading.SongInfo;
import ie.engine.maths.Animation;

import ie.engine.objects.Waves;
import processing.core.PApplet;

public class TweedyNS extends Scene{
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
        audioSync.song.jumpFrame((int)AudioSync.songParts.BRIDGE2.get());
    }

    float lerpValue;
    Animation testAnimation;
    float smoothBackground;
    float smoothCircle = 0;
    float circleFill = 0;
    float angle = 0;
    int filled = 0;
    int maxCubes = 4;
    
    public float border = 0;

    public void draw(){
        super.draw();
        noStroke();
        camera();
        lights();
        tempEvent = audioSync.isBeat();
        wasBeat = audioSync.wasBeat;
        background(0,0, smoothBackground);
        smoothCircle = lerp(smoothCircle, (tempEvent.volume * 1000) + 5, 0.07f);
        int currentFrame = audioSync.song.positionFrame();

        // rotateX(sin(angle));
        // rotateY(angle);

        if(currentFrame > 9907884) {
            // cuboids
            for (int i = 1; i < maxCubes; i++) {
                for (int j = 1; j < maxCubes; j++) {
                    pushMatrix();
                    // translate(width*i/4, height*j/4, map(sin(audioSync.song.positionFrame()), 0, 100000, 60, 50));
                    rectMode(CENTER);
                    stroke(0, 255, 140);
                    noFill();
                    translate(width*i/4, height*j/4, map(sin(angle), -1, 1, -1000, -100));
                    rotate(sin(angle)*2);
                    rotateY(angle);
                    rotateZ(sin(angle));
                    box(70);
                    popMatrix();
                }
            }
        }

        // can draw stuff outside the if(wasBeat) too
        if (wasBeat) {
            // runs if there was a beat provided

            // only execute before / after first verse etc
            // if(audioSync.song.positionFrame() < AudioSync.songParts.VERSE1.get() ){

            // how close to next frame
            // map(audioSync.song.positionFrame(),0, AudioSync.songParts.VERSE1.get(), 0, 100 );

            // grow from 0 to 100
            // map(audioSync.song.positionFrame(),AudioSync.songParts.BRIDGE1.get(), AudioSync.songParts.CHORUS1.get(), 0, 100 );

            smoothBackground = lerp(0.8f,tempEvent.volume, smoothBackground );
            // sphere grid
            noStroke();
            for (int i = 0; i < 9; i+=1) {
                for (int j = 0; j < 9; j+=1) {
                    // float c = map(arg0, arg1, arg2, arg3, arg4)
                    // float c = map((i * j), 0, 9, 0, 255) % 256;
                    
                    if ((frameCount % 120) == 0) {
                        fill(random(100, 240), random(100, 240), random(100, 240));
                    }
                    float mapX = map(i, 0, 9, 0, width);
                    float mapY = map(j, 0, 9, 0, height);
                    // circle(mapX+20, mapY+20, 40);
                    
                    pushMatrix();
                    translate(mapX+25, mapY+25, -10);
                    sphere(smoothCircle);
                    popMatrix();
                }
            }

            // cuboids
                for (int i = 1; i < 4; i++) {
                    for (int j = 1; j < 4; j++) {
                        pushMatrix();
                        // translate(width*i/4, height*j/4, map(sin(audioSync.song.positionFrame()), 0, 100000, 60, 50));
                        rectMode(CENTER);
                        // stroke(0, 255, 140);
                        stroke(255, 0, 0);
                        noFill();
                        fill(random(100, 240), random(100, 240), random(100, 240));
                        translate(width*i/4, height*j/4, map(sin(angle), -1, 1, -1000, -100));
                        rotate(sin(angle)*2);
                        rotateY(angle*5100);
                        rotateZ(sin(angle));
                        scale(10);
                        box(70);
                        popMatrix();
                    }
                }

            System.out.println(audioSync.song.positionFrame());
        } else {
            smoothBackground = lerp(0.8f,0, smoothBackground );
            
            // angle is in radians (in map change sin(angle) -> sin(degrees(angle)) 
            angle += 0.02f; //      if you want degrees)
            // first var in map is what you're converting with range (var2->var3) 
            // to the range (var4->var5) for fill() etc
            fill(map(sin(angle), -1, 1, 0, 255), map(sin(angle*5), -1, 1, 0, 255), map(sin(angle), -1, 1, 0, 255));

            // center sphere
            pushMatrix();
            // fill(255, 0, 119);
            translate(width/2, height/2, -100);
            // sphere(smoothCircle);
            circle(width/2-100, height/2-100, 200);
            popMatrix();
            
            noStroke();
            for (int i = 0; i < 9; i+=1) {
                for (int j = 0; j < 9; j+=1) {
                    // float c = map(arg0, arg1, arg2, arg3, arg4)
                    // float c = map((i * j), 0, 9, 0, 255) % 256;
                    if ((frameCount % 120) == 0) {
                        fill(random(100, 240), random(100, 240), random(100, 240));
                    }
                    // fill(c, c+12, c+60);
                    float mapX = map(i, 0, 9, 0, width);
                    float mapY = map(j, 0, 9, 0, height);
                    // circle(mapX+20, mapY+20, 40);
                    
                    pushMatrix();
                    translate(mapX+25, mapY+25, -10);
                    sphere(smoothCircle);
                    popMatrix();
                }
            }
            
            
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
