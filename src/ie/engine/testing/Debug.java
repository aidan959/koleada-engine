package ie.engine.testing;

import processing.core.PApplet;

public class Debug {
    PApplet pa;
    float frameTime;
    float frameRate;
    boolean doDebug = false;
    public Debug(PApplet pa){
        this.pa = pa;
    }
    public void start(){
        frameTime = System.nanoTime();

    }
    public void draw(){
        frameTime = (System.nanoTime() - frameTime) / (float)1000000.0;
        frameRate = pa.frameRate;
        pa.fill(200);
        pa.textSize(10);
        pa.text("frametime: " + frameTime + "ms" , 5, 5);  // Text wraps within text box
        pa.text("framerate: " + frameRate + "ms" , 5, 15);  // Text wraps within text box

    }

}
