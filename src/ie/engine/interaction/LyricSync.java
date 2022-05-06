package ie.engine.interaction;

import java.util.ArrayList;

import ie.engine.implementations.LyricEventLL;
import ie.engine.implementations.LyricEventLL.LyricEvent;
import ie.engine.loading.SongInfo;
import processing.core.PApplet;
import processing.sound.*;


public class LyricSync {
    public PApplet pa;
    public SongInfo songInfo;
    public float magnitude;
    public LyricEventLL lyricLL;
    public SoundFile song;

    public LyricSync(PApplet pa, AudioSync songInfo){
        this.songInfo = songInfo.songInfo;
        this.pa = pa;
        song = songInfo.song;
        lyricLL = new LyricEventLL(pa);
        lyricLL.load(songInfo.songInfo.songFile);
        listOfEvents = new ArrayList<>();
    }
    public LyricEvent lastEvent;
    public boolean wasLyric;
    public int missedFrameCounter;
    public int averageBeatTime = 0;
    public int beatCount = 0;
    public LyricEvent prev ;
    public ArrayList<LyricEvent> listOfEvents;
    public LyricEvent isLyric(){
        if(lyricLL.end){
            wasLyric = false;
            return lyricLL.blank;
        }
        this.wasLyric = false;
        missedFrameCounter = -1;
        lastEvent = lyricLL.blank;
        LyricEvent peekFrame = lyricLL.peek();
        if((peekFrame.frame > song.positionFrame())){
            // returns this blank value
            return lyricLL.blank;
        }

        // otherwise it gets updated
        if(peekFrame.frame < song.positionFrame() ){
            peekFrame = lyricLL.peek();
            if(lyricLL.end){
                return peekFrame;
            }
            listOfEvents.add(peekFrame);
            prev = peekFrame;
            lastEvent = lyricLL.pop();
            // audioSync.updateMagnitude();
            this.wasLyric = true;
        }
        return lastEvent;
    }
}
