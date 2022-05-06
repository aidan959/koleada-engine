package ie.engine.testing;


import java.util.HashMap;
import java.util.Map;

import ie.engine.Scene;
import processing.core.PApplet;

public class Debug {
    PApplet pa;
    public float frameTime;
    public boolean doDebug = false;
    public HashMap<String, DebugObject<Object>> debugMap;
    public Debug(Scene pa, Map<String, DebugObject<Object>> debugMap){
        this.pa = pa;
        this.debugMap = (HashMap<String, DebugObject<Object>>)debugMap;
    }
    public void start(){
        frameTime = System.nanoTime();

    }
    public float getFrameTime(){
        return(System.nanoTime() - frameTime) / (float)1000000.0;
    }
    public void draw(){
        frameTime = (System.nanoTime() - frameTime) / (float)1000000.0;
        pa.fill(200);
        pa.textSize(15);
        pa.textAlign(PApplet.LEFT);
        int i = 0;
        for(DebugObject<Object> dObject : debugMap.values()){
            pa.text(dObject.toString() , 5f , 15 + (i++ * 15f));  // Text wraps within text box
        }
        
    }

}
