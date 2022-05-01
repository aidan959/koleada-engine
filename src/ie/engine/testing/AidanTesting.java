package ie.engine.testing;
import processing.core.PApplet;
import ie.engine.implementations.ParticleSystem;
import ie.engine.implementations.AudioEventLL.AudioEvent;
import ie.engine.interaction.AudioSync;
import ie.engine.loading.SongInfo;
import ie.engine.maths.Animation;
import ie.engine.maths.Coordinate;
import ie.engine.maths.KeyFrame;
import ie.engine.objects.Waves;

public class AidanTesting extends PApplet{

    AudioSync audioSync;
    SongInfo songInfo; 
    AudioEvent tempEvent;
    boolean wasBeat;
    Debug debugger;
    Waves wavey;
    public void settings(){
        size(480, 480, P3D);
    }
    public void setup(){
        frameRate(60);
        String songName = "assets/audio/songs/nrgq.wav"; 
        audioSync = new AudioSync(this, songName);
        audioSync.play();
        songInfo = new SongInfo(songName);
        debugger = new Debug(this);
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
    public void draw(){
        debugger.start();
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
            audioSync.isBeat();
            

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
        //rect(0, 0, 100, 100);
        ps.run();
        camera();
        wavey.draw();
        hint(DISABLE_DEPTH_TEST); 
        noLights();
        if(debugger.doDebug){
            debugger.draw();
        }
    }
    public void keyPressed(){
        if(key == 'n' ){
            debugger.doDebug = !debugger.doDebug;
        }
    }
    static String[] gameArgs = {"Main"};
    public static void main(String[] args){
        PApplet.runSketch(gameArgs, new NewScene());
    }
}

