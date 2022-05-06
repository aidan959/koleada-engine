package ie.engine.testing;

import ie.engine.NRGQAnim;
import ie.engine.Scene;
//import ie.engine.implementations.AudioEventLL.AudioEvent;
import ie.engine.interaction.AudioSync;
import ie.engine.loading.SongInfo;
import ie.engine.maths.Animation;


import processing.core.PApplet;


public class KamilaTesting extends Scene {

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

    int R, B, G;
    int red1, blue1, green1;
    int zz;
    int st;

    // Objects

    // flashing sq 
    public void sq1(){
        int currentFrame = audioSync.song.positionFrame();
        int MAX = 150;
        translate(width / 2, height / 2, -100);

        if (currentFrame > 5207564 && currentFrame < 6007564){
            //blue
            R = 88;
            B = 203;
            G = 252;
        }else{
            //pink
            R = 231;
            B = 42;
            G = 252;
        }

        for(int xx = -MAX; xx <= MAX; xx += 50 ){
            for(int yy = -MAX; yy <= MAX; yy += 50 ){
                pushMatrix();
                translate( xx, yy, 0);
                rotateZ((float) (PI/3.0));
                fill(R, B, G);
                box(smoothCircle);
                popMatrix();
            }
        }

    }
    // circles that form in an x
    public void xcircle(){
        //rotateY((float) (frameCount * .04));

        int circles = 20; // amount of circles
        float h = width / (float) circles;
        int extent = 50; // size of circle
        for (int i = -circles; i < circles; i += 50) {
            stroke(231, 42, 252);
            noFill();
            float x = map(i, 0, circles, 0, width);
            circle(x+(extent/2), x+(extent/2), extent);
            circle(((width-h) - x)+(extent/2), x+(extent/2), extent);
        }
    }
    // 3D moving sq
    public void sq3d(){
        int MAX = 150;

        translate(width / 2, height / 2, -100);
        rotateX((float)(frameCount * .02));
        rotateY((float) (frameCount * .02));
    
        for(int xx = -MAX; xx <= MAX; xx += 50 ){
          for(int yy = -MAX; yy <= MAX; yy += 50 ){
            for(int zz = -MAX; zz <= MAX; zz += 50 ){
                pushMatrix();
                translate( xx, yy, zz);
                rotateX((float)(frameCount * .02));
                rotateY((float) (frameCount * .02));
                fill(R, B, G);
                box((float) (20 + (Math.sin(frameCount / 20.0)) * 15));
                popMatrix();
            }
          }
        }
    }
    // Small spinning spheres x format
    public void Ssph(){
   
        //(float) (20 + (Math.sin(frameCount / 20.0)) * 15)
        //rotateX((float)(frameCount * .1));
        
        rotateZ((float) (frameCount * .04));

        // highest to lowest sphere

        pushMatrix(); 
        fill(R, B, G);
        translate(525, 525, -200);
        sphere(50);
        popMatrix();

        pushMatrix(); 
        fill(R, B, G);
        translate(-525, 525, -200);
        sphere(50);
        popMatrix();
        
        pushMatrix(); 
        fill(R, B, G);
        translate(350, 350, -200);
        sphere(50);
        popMatrix();

        pushMatrix(); 
        fill(R, B, G);
        translate(-350, 350, -200);
        sphere(50);
        popMatrix();
        
        pushMatrix();
        fill(R, B, G);
        translate(175, 175, -200);
        sphere(50);
        popMatrix();

        pushMatrix();
        fill(R, B, G);
        translate(-175, 175, -200);
        sphere(50);
        popMatrix();
        
        pushMatrix();
        fill(R, B, G);
        translate(0, 0, -200);
        sphere(50);
        popMatrix();
        
        pushMatrix();
        fill(R, B, G);
        translate(-175, -175, -200);
        sphere(50);
        popMatrix();

        pushMatrix();
        fill(R, B, G);
        translate(175, -175, -200);
        sphere(50);
        popMatrix();
                    
        pushMatrix();
        fill(R, B, G);
        translate(-350, -350, -200);
        sphere(50);
        popMatrix();

        pushMatrix();
        fill(R, B, G);
        translate(350, -350, -200);
        sphere(50);
        popMatrix();

        pushMatrix();
        fill(R, B, G);
        translate(-525, -525, -200);
        sphere(50);
        popMatrix();

        pushMatrix();
        fill(R, B, G);
        translate(525, -525, -200);
        sphere(50);
        popMatrix();

    }
    // Big spinning circles x format
    public void Bsph(){

   
        //(float) (20 + (Math.sin(frameCount / 20.0)) * 15)
        //rotateX((float)(frameCount * .1));
        
        rotateZ((float) (frameCount * .04));

        // highest to lowest sphere

        pushMatrix(); 
        fill(R, B, G);
        translate(616, 616, -600);
        sphere(50);
        popMatrix();
    
        pushMatrix(); 
        fill(R, B, G);
        translate(-616, 616, -600);
        sphere(50);
        popMatrix();
        
        pushMatrix(); 
        fill(R, B, G);
        translate(262, 262, -600);
        sphere(50);
        popMatrix();
    
        pushMatrix(); 
        fill(R, B, G);
        translate(-262, 262, -600);
        sphere(50);
        popMatrix();
        
        pushMatrix();
        fill(R, B, G);
        translate(87, 87, -600);
        sphere(50);
        popMatrix();
    
        pushMatrix();
        fill(R, B, G);
        translate(-87, 87, -600);
        sphere(50);
        popMatrix();
        
        //middle//
        
        pushMatrix();
        fill(R, B, G);
        translate(-87, -87, -600);
        sphere(50);
        popMatrix();
    
        pushMatrix();
        fill(R, B, G);
        translate(87, -87, -600);
        sphere(50);
        popMatrix();
                    
        pushMatrix();
        fill(R, B, G);
        translate(-262, -262, -600);
        sphere(50);
        popMatrix();
    
        pushMatrix();
        fill(R, B, G);
        translate(262, -262, -600);
        sphere(50);
        popMatrix();
    
        pushMatrix();
        fill(R, B, G);
        translate(-616, -616, -600);
        sphere(50);
        popMatrix();
    
        pushMatrix();
        fill(R, B, G);
        translate(616, -616, -600);
        sphere(50);
        popMatrix();
    }
    // pink cirlces section2
    public void circ(){
        int MAX = 150;
        for(int xx = -MAX; xx <= MAX; xx += 50 ){
            for(int yy = -MAX; yy <= MAX; yy += 50 ){
                pushMatrix();
                fill(231, 42, 252);
                circle(xx, yy, 20);
                popMatrix();
            }
        }
    }
    // empty cirlces section2
    public void circ2(){
        int MAX = 150;
        for(int xx = -MAX; xx <= MAX; xx += 50 ){
            for(int yy = -MAX; yy <= MAX; yy += 50 ){
                pushMatrix();
                noFill();
                stroke(red1, blue1, green1);
                circle(xx, yy, zz);
                popMatrix();
            }
        }
    }    
    // big triangle
    public void Btri(){
        pushMatrix(); 
        fill(red1, blue1, green1);
        triangle(120, 320, width/2, 100, 344, 320);
        popMatrix();
    }
    //small rotating triangles
    public void Stri(){

        //top
        pushMatrix(); 
        rotate((float) (frameCount / 0.4));
        stroke(R, B, G);
        noFill();
        triangle(177, 155, 232, 45, 287, 155);
        popMatrix();
        //bottom left
        pushMatrix(); 
        rotate((float) (frameCount / 0.4));
        stroke(R, B, G);
        noFill();
        triangle(65, 375, 120, 265, 175, 375);
        popMatrix();
        //bottom right
        pushMatrix(); 
        rotate((float) (frameCount / 0.4));
        stroke(R, B, G);
        noFill();
        triangle(284, 375, 344, 265, 399, 375);
        popMatrix();

        //top
        pushMatrix(); 
        rotate((float) (frameCount / 0.2));
        stroke(R, B, G);
        noFill();
        triangle(177, 155, 232, 45, 287, 155);
        popMatrix();
        //bottom left
        pushMatrix(); 
        rotate((float) (frameCount / 0.2));
        stroke(R, B, G);
        noFill();
        triangle(65, 375, 120, 265, 175, 375);
        popMatrix();
        //bottom right
        pushMatrix(); 
        rotate((float) (frameCount / 0.2));
        stroke(R, B, G);
        noFill();
        triangle(284, 375, 344, 265, 399, 375);
        popMatrix();
    }
    // grid in section 4
    public void grid(){
        int gridSize = 40;

        for (int x = gridSize; x <= width - gridSize; x += gridSize) {
            for (int y = gridSize; y <= height - gridSize; y += gridSize) {
                noStroke();
                fill(255);
                rect(x-1, y-1, 3, 3);
                stroke(st);
                line(x, y, width/2, height/2);
            }
        }
    }

    // Sections

    //Section 1
    public void sec1(){
        int currentFrame = audioSync.song.positionFrame();

        if (currentFrame % 20 == 0){
            R = 88;
            B = 203;
            G = 252;

            sq3d();
            //test();

        }else{
            R = 231;
            B = 42;
            G = 252;
            sq3d();

        }

    }
    // Section 2
    public void sec2(){
        int currentFrame = audioSync.song.positionFrame();
        sq1();

        //(float) (20 + (Math.sin(frameCount / 20.0)) * 15)
        //rotateX((float)(frameCount * .1));
        if (currentFrame > 4253612 && currentFrame < 4635316){
            //green
            R = 38; 
            B = 236;
            G = 104;
            Bsph();
            Ssph();
       

        }else if (currentFrame > 4635316 && currentFrame < 5007564){
            //rotateZ((float) (frameCount * .04));
            //blue
            R = 88;
            B = 203;
            G = 252;
            Bsph();
            Ssph();
            //circleSpin();

        }else if (currentFrame > 5007564 && currentFrame < 5207564){
            //blue
            R = 88;
            B = 203;
            G = 252;
            rotateZ((float) (frameCount * .1));
            Bsph();
            Ssph();

        }

    }
    // Section 3
    public void sec3(){
        int currentFrame = audioSync.song.positionFrame();

        if (currentFrame % 30 == 0){
            red1 = 231;
            blue1 = 42;
            green1 = 252;
            zz = 20;
            sq1();
            //xcircle();
            circ();
            circ2();

        }else{
            red1 = 88;
            blue1 = 203;
            green1 = 252;
            zz = 50;
            sq1();
            //xcircle();
            circ();
            circ2();

        }

    }  
    // Section 4
    public void sec4(){
        int currentFrame = audioSync.song.positionFrame();

        if (currentFrame % 20 == 0){
            st = 255; // white
            grid();

        }else{
            st = 0; //black
            grid();

        }
        if (currentFrame > 6007564 && currentFrame < 6507564){
            red1 = 88;
            blue1 = 203;
            green1 = 252;
            R = 231;
            B = 42;
            G = 252;

            Btri();
            Stri();

        }else if (currentFrame > 6507564 && currentFrame < 7507564){
            R = 88;
            B = 203;
            G = 252;
            red1 = 231;
            blue1 = 42;
            green1 = 252;

            Btri();
            Stri();
            
        }

    }
 
    public void draw(){
        super.draw();
        noStroke();
        camera();
        lights();
        tempEvent = audioSync.isBeat();
        wasBeat = audioSync.wasBeat;
        background(0,0, smoothBackground);
        int currentFrame = audioSync.song.positionFrame();
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
        
            // pushMatrix();
            // translate(width-50, height/2, -50);
            // sphere(smoothCircle);
            // popMatrix();
            // pushMatrix();
            // translate(width+50, height/2, -50);
            // sphere(smoothCircle);
            // popMatrix();
        } else {
            
            smoothBackground = lerp(0.8f,0, smoothBackground );
            
            if (currentFrame > 3349548 && currentFrame < 4253612){
                sec1();

            }else if (currentFrame > 4253612 && currentFrame < 5207564){
                sec2();

            }else if (currentFrame > 5207564 && currentFrame < 6007564){
                sec3();

            }else if (currentFrame > 6007564 && currentFrame < 7507564){
                sec4();
            }

            if (frameCount % 30 == 0){
                System.out.println(audioSync.song.positionFrame());
            }

        }
        
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
        PApplet.runSketch(gameArgs, new KamilaTesting());
    }
}

