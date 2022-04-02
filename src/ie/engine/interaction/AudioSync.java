package ie.engine.interaction;

import ie.engine.implementations.AudioEventLL.AudioEvent;
import ie.engine.loading.SongInfo;
import processing.core.PApplet;
import processing.sound.*;


public class AudioSync {
    public PApplet pa;
    public SoundFile song;
    Waveform bd;
    int samples = 50;
    int timeToDie = 30;

    public SongInfo songInfo;
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
    public AudioSync(PApplet pa, String songFile){
        this.pa = pa;
        song = new SoundFile(pa, songFile, true);
        songInfo = new SongInfo(songFile);
        bd = new Waveform(pa, samples);
        bd.input(song);
    }
    public void play(){
        song.play();
    }
    public AudioEvent lastEvent;
    public boolean wasBeat;
    public int missedFrameCounter;

    public AudioEvent isBeat(){
        this.wasBeat = false;
        missedFrameCounter = -1;
        if(songInfo.eventList.peek().frame > song.positionFrame()){
            // returns this blank value
            return songInfo.eventList.blank;
        }
        // otherwise it gets updated
        while(songInfo.eventList.peek().frame < song.positionFrame() ){
            missedFrameCounter++;
            lastEvent = songInfo.eventList.pop();
            // audioSync.updateMagnitude();
            this.wasBeat = true;
        }
        
        return lastEvent;
    }
    // laggier - make sure to use the newer isBeat which relies on processed data from before
    // remains as a fallback
    public boolean isBeatOld(){  
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
