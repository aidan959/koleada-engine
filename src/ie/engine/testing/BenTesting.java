package ie.engine.testing;


import ie.engine.Scene;
import ie.engine.implementations.AudioEventLL.AudioEvent;
import ie.engine.interaction.AudioSync;
import ie.engine.loading.SongInfo;
import ie.engine.maths.Animation;

import ie.engine.objects.Waves;
import processing.core.PApplet;

public class BenTesting extends Scene{
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
        x = width/2;
        y = height/2;
        z = 0;
        colorMode(HSB);

    }

    float lerpValue;
    Animation testAnimation;
    float smoothBackground;
    float box = 5;
    float x, y, z;
    int size = 10;
    float angle;
    float jitter;
    int i;
    float c;

    

    public void draw(){
        super.draw();
        noStroke();
        camera();
        lights();
        tempEvent = audioSync.isBeat();
        wasBeat = audioSync.wasBeat;
        background(0,0, smoothBackground);
        box = lerp(box, (tempEvent.volume * 1000) + 25, 0.05f );
        camera(mouseX, mouseY, (width) / tan(PI/6), width*i/2, height*i/2, 0, 1, 1, 0);
        if (c >= 255)  c=0;  else  c++;
        //background(c, 255, 255);



        
        // can draw stuff outside the if(wasBeat) too
        if(wasBeat){
            // runs if there was a beat provided

            // only execute before / after first verse etc
            // if(audioSync.song.positionFrame() < AudioSync.songParts.VERSE1.get() ){

            // how close to next frame
            // map(audioSync.song.positionFrame(),0, AudioSync.songParts.VERSE1.get(), 0, 100 );

            // grow from 0 to 100
            // map(audioSync.song.positionFrame(),AudioSync.songParts.BRIDGE1.get(), AudioSync.songParts.CHORUS1.get(), 0, 100 );
            /*
            smoothBackground = lerp(0.8f,tempEvent.volume, smoothBackground );
            pushMatrix();
            stroke(0, 100, 255); //change the edges' colour
            fill(0, 0, 255); //fill the `box` in a blue colour
            box(box); 
            popMatrix();
            */

            for(int i = 0; i < size ;i++){
                for(int j = 0; j < size; j++)
                {
                    pushMatrix();
                    fill(map(i, 0, size, 0, 255),255, 255);
    
                    if (second() % 3 == 0) {  
                        jitter = random(-1, 0);
                    }
    
                    angle = angle + jitter;
                    float c = tan(angle);
    
                    //translate(width*i/2, height*i/2, -100);
    
                    rotate(c);
                    rotateY(9);
                    rotateX(5); 
                    translate(width*i/2, height*i/2, 100);     
                    box(box);
                    popMatrix(); 
                    

                }
            }

            for(int i = 0; i < size ;i++){
                for(int j = 0; j < size; j++)
                {
                    pushMatrix();
                    fill(map(i, 0, size, 0, 255),255, 255);
                    if (second() % 3 == 0) {  
                        jitter = random(-1, 0);
                    }
    
                    angle = angle + jitter;
                    float c = tan(angle);
    
                    //translate(width*i/2, height*i/2, -100);
    
                    rotate(c);
                    rotateY(9);
                    rotateX(5);
                    translate(width/4, height/4, 100);     
                    box(box);
                    popMatrix(); 
                    

                }
            }


        } else {
            
            /*
            smoothBackground = lerp(0.8f,0, smoothBackground );
            pushMatrix();
            stroke(0, 100, 255); //change the edges' colour
            fill(0, 0, 255); //fill the `box` in a blue colour
            box(box); 
            popMatrix();
            */


            for(int i = 0; i < size ;i++){
                for(int j = 0; j < size; j++)
                {
                    pushMatrix();
                    fill(map(i, 0, size, 0, 255),255, 255);
    
                    if (second() % 3 == 0) {  
                        jitter = random(-1, 0);
                    }
    
                    angle = angle + jitter;
                    float c = tan(angle);
    
                    //translate(width*i/2, height*i/2, -100);
    
                    rotate(c);
                    rotateY(9);
                    rotateX(5); 
                    translate(width*i/2, height*i/2, 100);     
                    box(box);
                    popMatrix(); 
                    

                }
            }
            for(int i = 0; i < size ;i++){
                for(int j = 0; j < size; j++)
                {
                    pushMatrix();
                    fill(map(i, 0, size, 0, 255),255, 255);
    
                    if (second() % 3 == 0) {  
                        jitter = random(-1, 0);
                    }
    
                    angle = angle + jitter;
                    float c = tan(angle);
    
                    //translate(width*i/2, height*i/2, -100);
    
                    rotate(c);
                    rotateY(9);
                    rotateX(5); 
                    translate(width/4, height/4, 100);     
                    box(box);
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
        PApplet.runSketch(gameArgs, new BenTesting());
    }
}
