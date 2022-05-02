package ie.engine.testing;
import processing.core.PApplet;

import java.util.HashMap;
import java.util.Hashtable;

import ie.engine.Scene;
import ie.engine.implementations.ParticleSystem;
import ie.engine.implementations.AudioEventLL.AudioEvent;
import ie.engine.interaction.AudioSync;
import ie.engine.loading.SongInfo;
import ie.engine.maths.Animation;
import ie.engine.maths.Coordinate;
import ie.engine.maths.KeyFrame;
import ie.engine.objects.Waves;

public class AidanTesting extends Scene{

    public Waves wavey;
    public void setup(){
        super.setup();
        String songName = "assets/audio/songs/nrgq.wav"; 
        audioSync = new AudioSync(this, songName);
        audioSync.play();
        songInfo = new SongInfo(songName);
        
        


        KeyFrame[] frames = new KeyFrame[4];
        frames[0] = new KeyFrame(0, new Coordinate(0, 0, 0));
        frames[1] = new KeyFrame(100000, new Coordinate(15, 15, 15));
        frames[2] = new KeyFrame(600000, new Coordinate(10, 10, 10));
        frames[3] = new KeyFrame(1000000, new Coordinate(20, 20,20));

        testAnimation = new Animation(4, frames);
        ps = new ParticleSystem(new Coordinate(width/4f, height/2f), this);
        ps2 = new ParticleSystem(new Coordinate((3*width)/4f, height/2f), this);
        wavey = new Waves(this);

    }
    ParticleSystem ps;
    ParticleSystem ps2;

    float lerpValue;
    Animation testAnimation;
    float smoothBackground;

    
    int cumulativeFrames;
    public void draw(){
        super.draw();
        
        testAnimation.run(audioSync.song.positionFrame());
        //clear();
        camera();
        tempEvent = audioSync.isBeat();
        wasBeat = audioSync.wasBeat;
        smoothBackground = lerp(0.8f,tempEvent.volume*600, smoothBackground );
        background(0,0, smoothBackground);
        if(wasBeat){
            // int divisions = (songInfo.eventList.peek().frame - tempEvent.frame)/4;
            //System.out.println("Beat " + tempEvent.frame + " of volume: " + tempEvent.volume);
            lastEvent = tempEvent;
            cumulativeFrames += lastEvent.frame;

            for(int i = 0; i < 4; i++){
                ps.addParticle(new Coordinate(0, 10f * i, 0), new Coordinate(0.5f, 0, 5f), new Coordinate(5, 5, 5));
                ps2.addParticle(new Coordinate(0, 10f * i, 0), new Coordinate(-0.5f, 0, 5f), new Coordinate(-5, 5, 5));

            }
        } else {
            //System.out.println("SHOULD BE BLANK Beat " + tempEvent.frame + " of volume: " + tempEvent.volume);
            
        }
        if(frameCount %2 == 1){
            wavey.update(1);

        }
        angle += 0.001f;
        if(lastEvent != null){
            // System.out.println(map(audioSync.song.positionFrame() - lastEvent.frame, 0, audioSync.peekNext().frame - lastEvent.frame, 0, 180));
            // System.out.println("Length in frame " + (audioSync.song.positionFrame() - lastEvent.frame) 
            //                 +  "\nLength of gap " + (audioSync.peekNext().frame - lastEvent.frame)
            //                 + "\nLast Frame: "  + lastEvent.frame 
            //                 + "\nNext Frame: " +  audioSync.peekNext().frame
            //                 + "\nCurrent Frame: " + audioSync.song.positionFrame() );
            lightning(10, width * sin(bp.process()), cos(degrees(angle)));
        }
        //rect(0, 0, 100, 100);
        ps.run();
        ps2.run();
        camera();
        wavey.draw();
        hint(DISABLE_DEPTH_TEST); 
        noLights();
        if(debugger.doDebug){
            debugDictionary.get("frametime").updateValue(debugger.getFrameTime());
            debugDictionary.get("framerate").updateValue(frameRate);
            debugDictionary.get("avgbeattime").updateValue((((float)audioSync.averageBeatTime)/(float)(songInfo.sampleRate)));
            debugger.draw();
        }
    }
    float angle =0;
    void lightning(int n, float posX,float posY)
        {
            for(int i = 0; i < n; i++)
            {
                fill(map(i,0,n,0,255),255,255);
                pushMatrix();
                rotateY(TAU * i/n);
                rotateZ(PI/12);
                translate(posX,100,posY);      
                box(10,100,10);
                if(i == 1)lightning(n - 1,posX, posY);
                popMatrix();
            }
        }  
    public void keyPressed(){
        if(key == 'n' ){
            debugger.doDebug = !debugger.doDebug;
        }
    }
    static String[] gameArgs = {"Main"};
    public static void main(String[] args){
        PApplet.runSketch(gameArgs, new AidanTesting());
    }
}

