package ie.engine;

import processing.core.PApplet;
import processing.core.PSurface;

import java.util.HashMap;

import ie.engine.Scene;
import ie.engine.implementations.AudioEventLL.AudioEvent;
import ie.engine.interaction.AudioSync;
import ie.engine.loading.SongInfo;
import ie.engine.maths.BeatPulse;
import ie.engine.testing.Debug;
import ie.engine.testing.DebugObject;


public class Scene extends PApplet {
    public AudioSync audioSync;
    public SongInfo songInfo; 
    public AudioEvent tempEvent;
    public boolean wasBeat;
    public Debug debugger;
    public BeatPulse bp;
    public AudioEvent lastEvent;
    public HashMap<String, DebugObject<Object>> debugDictionary;
    public int currentFrame;
    // original width and height used to scale
    public int w,h;
    public void settings(){
        size(1280, 720, P3D);
        
    }
    public void draw(){
        debugger.start();
        currentFrame = audioSync.song.positionFrame();
        clear();
    }
    public void setSurface(PSurface surface){
        this.surface = surface;
    }
    public void setup(){
        w = 480;
        h = 480;
        frameRate(60);
        w = 480;
        h = 480;
        surface.setResizable(true);
        if(audioSync !=null)
            currentFrame = audioSync.song.positionFrame();
        textMode(MODEL);
        colorMode(RGB);
        debugDictionary = new HashMap<String, DebugObject<Object>>();
        debugDictionary.put("frametime", new DebugObject<>("frametime", 0, "ms"));
        debugDictionary.put("framerate", new DebugObject<>("framerate", 0, "ms"));
        debugDictionary.put("avgbeattime",new DebugObject<>("average beat time", 0, "seconds"));
        debugDictionary.put("currentframe",new DebugObject<>("current frame", 0, "frame"));
        debugDictionary.put("lastframe",new DebugObject<>("last beat frame", 0, "frame"));
        debugDictionary.put("nextframe",new DebugObject<>("next beat frame", 0, "frame"));

        
        debugger = new Debug(this, debugDictionary);
        bp = new BeatPulse(this);
    }
    public void keyPressed(){
        if(key == '0'){
            audioSync.song.jumpFrame((int)AudioSync.songParts.INTRO.get());
        }
        else if(key == '1'){
            audioSync.song.jumpFrame((int)AudioSync.songParts.VERSE1.get());
        } else if(key == '2'){
            audioSync.song.jumpFrame((int)AudioSync.songParts.BRIDGE1.get());
        } else if(key == '3'){
            audioSync.song.jumpFrame((int)AudioSync.songParts.CHORUS1.get());
        }  else if(key == '4'){
            audioSync.song.jumpFrame((int)AudioSync.songParts.VERSE2.get());
        }
        else if(key == '5'){
            audioSync.song.jumpFrame((int)AudioSync.songParts.BRIDGE2.get());
        }
        else if(key == '6'){
            audioSync.song.jumpFrame((int)AudioSync.songParts.CHORUS2.get());
        }
    }
}
