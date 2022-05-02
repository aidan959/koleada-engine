package ie.engine.maths;

import ie.engine.Scene;
import processing.core.PApplet;

public class BeatPulse {
    Scene scene;
    public BeatPulse(Scene scene){
        this.scene = scene;
    }
    public float process(){
        return PApplet.map(scene.audioSync.song.positionFrame() - scene.lastEvent.frame, 0, scene.audioSync.peekNext().frame - scene.lastEvent.frame, 0, 2* PApplet.PI );
    }
}
