package ie.engine.audio;
import processing.core.PApplet;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
public class Audio1 {
    Minim minim;
    PApplet pa;
    AudioPlayer ap;
    AudioInput ai;
    AudioBuffer ab;
    public Audio1(){
        minim = new Minim(this);
        ai = minim.getLineIn(Minim.MONO, width, 44100, 16);
        ab = ai.mix;
        halfH = height/2f;
    }
    float halfH;
    float average_val;
    float smoothY;
    public void draw(){
        background(0);
        stroke(255);
        average_val = 0;
        
        for(int i= 0; i < ab.size(); i++){
            line(i, halfH, i , halfH + ab.get(i));
            average_val += abs(ab.get(i));

        }
        average_val /= ab.size();
        stroke(255);
        fill(100,255,255);
        smoothY = lerp(smoothY, average_val, 0.1f);
        circle(width/2f, halfH, smoothY * 100f);
        

    }
    public static void main(String[] args){
        Audio1 a=  new Audio1();
        a.runSketch();
    }
}
