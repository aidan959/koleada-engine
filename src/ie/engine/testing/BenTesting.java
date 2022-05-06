package ie.engine.testing;


import ie.engine.Scene;
import ie.engine.implementations.AudioEventLL.AudioEvent;
import ie.engine.interaction.AudioSync;
import ie.engine.loading.SongInfo;
import ie.engine.maths.Animation;
import ie.engine.maths.RGBColor;
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
        // bridge to chorus -- will start from chorus 1
        audioSync.song.jumpFrame((int)AudioSync.songParts.CHORUS1.get());
        colorMode(HSB);


    }

    float lerpValue;
    Animation testAnimation;
    float smoothBackground;
    float box;
    float centrebox;
    float sphereCentre;
    float angle;
    float jitter;
    int i;

    public void draw(){
        super.draw();
        noStroke();
        //camera();
        camera(mouseX, mouseY, (width) / tan(PI/6), width*i, height*i, 0, 1, 1, 0);
        lights();
        tempEvent = audioSync.isBeat();
        wasBeat = audioSync.wasBeat;
        background(0,0, smoothBackground);
        box = lerp(box, (tempEvent.volume * 400) + 20, 0.05f );
        centrebox = lerp(centrebox, (tempEvent.volume * 600) + 25, 0.05f );
        sphereCentre = lerp(sphereCentre, (tempEvent.volume * 8000) + 100, 0.05f );
       
        
        int currentFrame = audioSync.song.positionFrame();

        // to illustrate sphere 
        pushMatrix();
        stroke(random(255), random(255), random(255));
        noFill();
        sphere(sphereCentre);
        popMatrix();
     
        // for 2nd beat drop of song
        if (currentFrame > 4984496 ){
            for(int i = 0; i < box ;i++){
                for(int j = 0; j < box; j++)
                {
                    fill(map(i, 0, box, 0, 255),255, 255);
                    pushMatrix();

                    // controls the small beat wave 
                    if (second() % 10 == 0) {  
                        jitter = random(-1, 0);
                    }
                    
                
                    angle = angle + jitter;
                    float c = tan(angle);
                    noStroke();

                    rotate(c);
                    rotateY(9);
                    rotateX(5); 
                    translate(width*i/100, height*i/100, -400);   
        
                    box(box);
                    popMatrix();  
                        
                }
            }
                for(int i = 0; i < box ;i++){
                    for(int j = 0; j < box; j++)
                    {
                        fill(map(i, 0, box, 0, 255),255, 255);
                        pushMatrix();
                
                        if (second() % 2 == 0) {  
                            jitter = random(-2, 0);
                        }
                
                        angle = angle + jitter;
                        float c = tan(angle);
                        noStroke();
                        rotate(c);
                        rotateY(9);
                        rotateX(5); 
                        translate(width*i/20, height*i/20, -400);   
        
                        box(box);
                        popMatrix();          
            
                    }
                }

                for(int i = 0; i < centrebox ;i++){
                    for(int j = 0; j < centrebox; j++)
                    {
                        fill(map(i, 0, centrebox, 0, 255),255, 255);
                        pushMatrix();
        
                        if (second() % 2 == 0) {  
                            jitter = random(-1, 0);
                        }
        
                        angle = angle + jitter;
                        float c = tan(angle);       
        
                        rotate(c);
                        rotateY(9);
                        rotateX(5); 
                        translate(width*i/20, height*i/20, -400);     
                        box(centrebox);
                        popMatrix(); 
                        
                        
                    }
                }
            }
        // for 1st drop        
        else {
            for(int i = 0; i < box ;i++){
                for(int j = 0; j < box; j++)
                {
                    fill(map(i, 0, box, 0, 255),255, 255);
                    pushMatrix();
            
                    if (second() % 10 == 0) {  
                        jitter = random(-1, 0);
                    }
            
                    angle = angle + jitter;
                    float c = tan(angle);
                    noStroke();
                    lerp(box, (tempEvent.volume * 10000) + 100, 0.05f );
                    rotate(c);
                    rotateY(9);
                    rotateX(5); 
                    translate(width*i/100, height*i/100, -400);   
    
                    box(box);
                    popMatrix();  
                    
                }
            }
        for(int i = 0; i < centrebox ;i++){
            for(int j = 0; j < centrebox; j++)
            {
                fill(map(i, 0, centrebox, 0, 255),255, 255);
                pushMatrix();
    
                if (second() % 2 == 0) {  
                    jitter = random(-1, 0);
                }
    
                angle = angle + jitter;
                float c = tan(angle);
    

                rotate(c);
                rotateY(9);
                rotateX(5); 
                translate(width/2, height/2, -400);     
                box(centrebox);
                popMatrix(); 
                    
                    
            }
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
            /*
            smoothBackground = lerp(0.8f,tempEvent.volume, smoothBackground );
            pushMatrix();
            stroke(0, 100, 255); //change the edges' colour
            fill(0, 0, 255); //fill the `box` in a blue colour
            box(box); 
            popMatrix();
            */





            System.out.println(currentFrame);

        } else {
             
            angle += 0.02f;
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
