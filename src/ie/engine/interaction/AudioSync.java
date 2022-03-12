package ie.engine.interaction;

import processing.core.PApplet;
import processing.sound.*;

public class AudioSync {
    public PApplet pa;
    public SoundFile song;
    Waveform bd;
    int samples = 50;
    int timeToDie = 30;
    public float magnitude;
    public enum songParts{
        INTRO(0),
        VERSE1(1305290L),
        BRIDGE1(3214644L),
        CHORUS1(3),
        VERSE2(4);
        private final long value;
        private songParts(long  value){
            this.value = value;
        }
        public long get(){
            return value;
        }
    }
    public AudioSync(PApplet pa){

        this.pa = pa;
        song = new SoundFile(pa, "assets/audio/songs/nrgq.wav", true);
        bd = new Waveform(pa, samples);
        bd.input(song);
    }
    public void play(){
        song.play();
    }
    public boolean isBeat(){
        float avg = 0;
        bd.analyze();
        for (int i = 0; i < samples; i++){
            //avg += PApplet.abs(bd.data[i]);
            magnitude = bd.data[i];
            if(PApplet.abs(bd.data[i])>0.7f){
                 
                return true;
            }
        }
        //System.out.println(avg/samples);
        return false;
    }

}
