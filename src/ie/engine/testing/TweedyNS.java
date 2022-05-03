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
    float smoothCircle2 = 0;
    float circleFill = 0;
    float angle = 0;
    int filled = 0;
    int maxCubes = 4;
    int circles = 1;
    
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
        smoothCircle2 = lerp(smoothCircle, (tempEvent.volume * 555) + 5, 0.07f);
        int currentFrame = audioSync.song.positionFrame();
        

        // rotateX(sin(angle));
        // rotateY(angle);
        
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
            if (circles > 9) {
                circles = 1;
            } else {
                circles++;
            }
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
                    circle(mapX+20, mapY+20, 40);
                    
                    pushMatrix();
                    translate(mapX+25, mapY+25, -5);
                    sphere(smoothCircle+5);
                    popMatrix();
                }
            }

            // System.out.println(audioSync.song.positionFrame());
        } else {
            smoothBackground = lerp(0.8f,0, smoothBackground );
            
            // angle is in radians (in map change sin(angle) -> sin(degrees(angle)) 
            angle += 0.02f; //      if you want degrees)
            // first var in map is what you're converting with range (var2->var3) 
            // to the range (var4->var5) for fill() etc
            fill(map(sin(angle), -1, 1, 0, 255), map(sin(angle*5), -1, 1, 0, 255), map(sin(angle), -1, 1, 0, 255));


            // center circle
            pushMatrix();
            noFill();
            strokeWeight(5);
            for (int i = 0; i < circles; i++) {
                if (currentFrame < 9907784){
                    stroke(map(sin(angle*4), -1, 1, 125, 255)+(i*10), 0, 199);
                } else {
                    stroke(0, map(sin(angle*4), -1, 1, 150, 255)+(i*10), 199);
                    // stroke(0, 255, 119);
                }
                circle(width/2, height/2, (50+(i*50))+smoothCircle2*2);
            }
            // circle(width/2, height/2, smoothCircle*10);
            popMatrix();

            
            noStroke();
            for (int i = 0; i < 9; i+=1) {
                for (int j = 0; j < 9; j+=1) {
                    fill(0, 255, 128);
                    // float c = map(arg0, arg1, arg2, arg3, arg4)
                    float c = map((i * j), 0, 9, 0, 255) % 256;
                    if (currentFrame < 9907784){
                        fill(c+180, c+12, c+60);
                    } else {
                        fill(c+12, c+100, c+60);
                    }
                    float mapX = map(i, 0, 9, 0, width);
                    float mapY = map(j, 0, 9, 0, height);
                    
                    pushMatrix();
                    translate(mapX+25, mapY+25, -5);
                    sphere(smoothCircle+5);
                    popMatrix();
                }
            }
            
            
            
        }
        if(currentFrame > 9907784 ) {
            // cuboids
            for (int i = 1; i < maxCubes; i++) {
                for (int j = 1; j < maxCubes; j++) {
                    strokeWeight(1);
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
