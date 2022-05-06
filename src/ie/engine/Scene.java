package ie.engine;

import processing.core.PApplet;
import java.util.HashMap;
import java.util.Hashtable;

import ie.engine.Scene;
import ie.engine.implementations.ParticleSystem;
import ie.engine.implementations.AudioEventLL.AudioEvent;
import ie.engine.interaction.AudioSync;
import ie.engine.loading.SongInfo;
import ie.engine.maths.Animation;
import ie.engine.maths.BeatPulse;
import ie.engine.maths.Coordinate;
import ie.engine.maths.KeyFrame;
import ie.engine.objects.Waves;
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
    public void settings(){
        size(1280, 720, P3D);
        // size(480, 480, P3D);
    }
    public void draw(){
        debugger.start();
        clear();
    }
    public void setup(){
        frameRate(60);
        colorMode(RGB);
        debugDictionary = new HashMap<String, DebugObject<Object>>();
        debugDictionary.put("frametime", new DebugObject<Object>("frametime", 0, "ms"));
        debugDictionary.put("framerate", new DebugObject<Object>("framerate", 0, "ms"));
        debugDictionary.put("avgbeattime",new DebugObject<Object>("average beat time", 0, "frames"));
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
