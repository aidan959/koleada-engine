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
        CHORUS1(4327088L),
        VERSE2(6238896L),
        BRIDGE2(8798316L),
        CHORUS2(9898417L),
        ;
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
        prev = songInfo.eventList.blank;
    }
    public void play(){
        song.play();
    }
    public AudioEvent lastEvent;
    public boolean wasBeat;
    public int missedFrameCounter;
    public int averageBeatTime = 0;
    public int beatCount = 0;
    public long totalDifference;
    public AudioEvent prev ;
    public AudioEvent isBeat(){
        this.wasBeat = false;
        missedFrameCounter = -1;
        lastEvent = songInfo.eventList.blank;
        AudioEvent peekFrame = peekNext();
        if((peekFrame.frame > song.positionFrame())){
            // returns this blank value
            return songInfo.eventList.blank;
        }
        // otherwise it gets updated
        while(peekFrame.frame < song.positionFrame() ){
            peekFrame = peekNext();
            missedFrameCounter++;
            beatCount++;
            if(prev != peekFrame){ 
                totalDifference +=  peekFrame.frame - prev.frame;
            }
            prev = peekFrame;
            averageBeatTime = (int)(totalDifference/beatCount);
            lastEvent = songInfo.eventList.pop();
            // audioSync.updateMagnitude();
            this.wasBeat = true;
        }
        
        return lastEvent;
    }
    public AudioEvent peekNext(){
        return songInfo.eventList.peek();
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
