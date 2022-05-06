package ie.engine.maths;

import ie.engine.Scene;
import processing.core.PApplet;

public class BeatPulse {
    Scene scene;
    public BeatPulse(Scene scene){
        this.scene = scene;
    }
    public float process(){
        // System.out.println(PApplet.map(scene.audioSync.song.positionFrame() - scene.lastEvent.frame, 0, scene.audioSync.peekNext().frame - scene.lastEvent.frame, 0, 1 ));
        return PApplet.map(scene.audioSync.song.positionFrame(), scene.lastEvent.frame, scene.audioSync.peekNext().frame , 0, 1 );
    }
    public float processSin(){
        return PApplet.sin(PApplet.degrees(PApplet.map(scene.audioSync.song.positionFrame(), scene.lastEvent.frame, scene.audioSync.peekNext().frame , 0, 360 )));
    }
}
