package ie.engine.testing;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PShapeSVG.Font;
import ie.engine.Scene;
import ie.engine.implementations.LyricEventLL;
import ie.engine.implementations.ParticleSystem;
import ie.engine.implementations.LyricEventLL.LyricEvent;
import ie.engine.interaction.AudioSync;
import ie.engine.interaction.LyricSync;
import ie.engine.loading.SongInfo;
import ie.engine.maths.Animation;
import ie.engine.maths.Coordinate;
import ie.engine.maths.KeyFrame;
import ie.engine.objects.Waves;

public class AidanTesting extends Scene {
    PFont myFont;
    public Waves wavey;

    public void setup() {
        super.setup();
        String songName = "assets/audio/songs/nrgq.wav";
        audioSync = new AudioSync(this, songName);
        songInfo = new SongInfo(songName);

        setupInstance();
        audioSync.song.play();

    }
    public void setupInstance(){
        lastEvent = audioSync.songInfo.eventList.blank;
        lyricSync = new LyricSync(this, audioSync);
        ps = new ParticleSystem(new Coordinate(width, height), new Coordinate(1/4f,1/2f), this);
        ps2 = new ParticleSystem(new Coordinate(width, height), new Coordinate(3/4f,1/2f), this);

        lyricSync.listOfEvents.clear();
        myFont = createFont("assets/fonts/Manrope-Regular.ttf", 100, true);
        textFont(myFont);
        debugDictionary.put("bp", new DebugObject<>("bp.process()", 0, ""));
        debugDictionary.put("bp2", new DebugObject<>("bp.processSin()", 0, ""));
        debugDictionary.put("fov", new DebugObject<>("fov", 0, "°"));
    }
    ParticleSystem ps;
    ParticleSystem ps2;

    LyricSync lyricSync;
    LyricEvent tempLyricEvent;
    float lerpValue;
    Animation testAnimation;
    float smoothBackground;
    float smoothTubes;

    public void pre() {
        // ran at the start of the scene to ensure scaling for text works when full screening etc
        if (w != width || h != height) {
            lyricSync.lyricLL.updateScale();
            ps.updatePositions();
            ps2.updatePositions();
            w = width;
            h = height;
        }
    }
    int fov = 65;
    @Override
    public void draw() {
        pre();
        super.draw();
        camera();
        perspective(radians(fov%180), ((float)width/(float)height),0.00001f, 1000);
        //this fades the end of the scene to the start of the next one -> will need to be positioned correctly when moving between each persons scenes
        if (currentFrame > 4288737) {
            background(map(currentFrame, 4288737, AudioSync.songParts.CHORUS1.get(), 0, 255));
            
        } else {
            background(0, 0, smoothBackground);
        }
        // this is used to check if there was a lyric to display this frame
        tempLyricEvent = lyricSync.isLyric();
        tempEvent = audioSync.isBeat();
        // returns a value that bounces between beats from -1 -> 1
        float sinLoop = bp.processSin();
        float regLoop = bp.process();

        // returns true if there was a beat
        wasBeat = audioSync.wasBeat;
        // when 3d rendering this removes the wireframe
        noStroke();
        // used for sin function to change values
        angle += 0.002f;
        // this is ued to pulse and smoothen the changes to the size of the storm
        smoothTubes = lerp(smoothTubes, (tempEvent.volume * 1000) + 5, 0.005f);
        // intro
        if (currentFrame < AudioSync.songParts.VERSE1.get()) {

            lightning(width / 2f, (height) / 4f, 10, map(currentFrame, 0, AudioSync.songParts.VERSE1.get(), 0, 100),
                    cos(degrees(angle)), true);
            storm(width / 4f, height / 2f, 15, map(currentFrame, 0, AudioSync.songParts.VERSE1.get(), 0, 100),
                    sin(degrees(angle)), true, 1, false);
            storm((width * 3) / 4f, height / 2f, 15, map(currentFrame, 0, AudioSync.songParts.VERSE1.get(), 0, 100),
                    sin(degrees(angle)), true, 0, true);

            // verse
        } else if (currentFrame < AudioSync.songParts.BRIDGE1.get()) {
            lightning(width / 2f, (height) / 4f, 10, width / 2f * sin(bp.process()), cos(degrees(angle)), true);
            storm(width / 4f, height / 2f, 15, map(currentFrame, 0, AudioSync.songParts.VERSE1.get(), 0, 100),
                    sin(degrees(angle)), true, smoothTubes, false);
            storm((width * 3) / 4f, height / 2f, 15, map(currentFrame, 0, AudioSync.songParts.VERSE1.get(), 0, 100),
                    sin(degrees(angle)), true, smoothTubes, true);
            if(wasBeat){
                for(int i = 0; i < 4; i++){
                    ps.addParticle(new Coordinate(0, 10f * i, 0), new Coordinate(0.5f, 0, 5f),
                    new Coordinate(5, 5, 5));
                    ps2.addParticle(new Coordinate(0, 10f * i, 0), new Coordinate(-0.5f, 0,
                5f), new Coordinate(-5, 5, 5));
                }
            }
            // bridge
        } else if (currentFrame < AudioSync.songParts.CHORUS1.get()) {
            if(currentFrame > 4288737)
                fov = (int)map(currentFrame,4288737, AudioSync.songParts.CHORUS1.get(),0,65);
            else if(currentFrame > 3872222){
                fov = (int)map(currentFrame,3872222, 4288737,65,0);
            } 
            // creates our nested circles
            pushMatrix();
            stroke(255);
            // maps the current frame from bridge to chorus, and maps that to the screen width
            float circleMap = map(currentFrame, AudioSync.songParts.BRIDGE1.get(), AudioSync.songParts.CHORUS1.get(), 0,
                    width);
            // this reverses the circle map so the circles collapse on eachother
            if (currentFrame > 4288737) {
                circleMap = map(currentFrame, 4288737, AudioSync.songParts.CHORUS1.get(), width, 0);
            }
            // increasesthe number of circles gradually -> limit should be 48 (depending on resolution - may add an upper limit (rendering this in 4k would lead to 384 circles lol)) 
            int numCircles = (int) circleMap/10;
            numCircles = numCircles > 70 ? 70: numCircles;

            for (int i = 1; i < numCircles; i++) {
                // TODO: may update to tweedy HSB color converter to make this a nice looking gradient?
                // we'll see!
                fill((circleMap) % 255, (circleMap * i) % 255, (circleMap * (i + 1)) % 255);
                circle(width / 2f, height / 2f, (circleMap / i) + (regLoop * i));
            }
            popMatrix();
            // chorus - BENS PART
        } else if (currentFrame < AudioSync.songParts.VERSE2.get()) {

        }
        if (wasBeat) {
            lastEvent = tempEvent;
        }
        // wavey.update(wasBeat);
        ps.run();
        ps2.run();
        camera();
        //perspective();
        hint(DISABLE_DEPTH_TEST);
        noLights();
        // backwards traversal to allow active removal
        for (int i = lyricSync.listOfEvents.size() - 1; i >= 0; i--) {
            LyricEvent lEvent = lyricSync.listOfEvents.get(i);
            // println(lEvent + " dying at: " + lEvent.timeToDie);
            // check if the frame has died yet
            if (currentFrame > lEvent.timeToDie) {
                lyricSync.listOfEvents.remove(lEvent);
            } else {

                if (currentFrame > 1283965) {

                    lEvent.draw(sinLoop);
                } else {
                    lEvent.draw(1);
                }
            }
        }

        if (debugger.doDebug) {
            debugDictionary.get("frametime").updateValue(debugger.getFrameTime());
            debugDictionary.get("framerate").updateValue(frameRate);
            debugDictionary.get("avgbeattime")
                    .updateValue((((float) audioSync.averageBeatTime) / (float) (songInfo.sampleRate)));
            debugDictionary.get("currentframe").updateValue(audioSync.song.positionFrame());
            debugDictionary.get("lastframe").updateValue(lastEvent.frame);
            debugDictionary.get("nextframe").updateValue(audioSync.peekNext().frame);
            debugDictionary.get("bp").updateValue(regLoop);
            debugDictionary.get("bp2").updateValue(sinLoop);
            debugDictionary.get("fov").updateValue(fov);


            debugger.draw();
        }
    }

    float angle = 0;

    public void lightning(float originX, float originY, int n, float posX, float posY, boolean firstLayer) {
        for (int i = 0; i < n; i++) {
            pushMatrix();
            if (firstLayer) {
                translate(originX, originY);
                scale(bp.process() / 5);
            } else {
                translate(posX, posY, 0);
            }
            fill(map(i, 0, n, 0, 255), 255, 255);
            rotateY(TAU * i / n);
            rotateZ(PI / 12);
            box(10, 100, 10);
            if (i == 1)
                lightning(originX, originY, n - 1, posX, posY, false);
            popMatrix();
        }
    }

    public void storm(float originX, float originY, int n, float posX, float posY, boolean firstLayer, float volume,
            boolean flip) {
        if (firstLayer) {
            pushMatrix();
            translate(originX, originY);
            rotateX(-45);
            if (flip) {
                rotateY(180);
            }
        }
        for (int i = 0; i < n; i++) {
            pushMatrix();
            fill(255, map(i, 0, n, 0, 255), 255);
            rotateY(TAU * i / n);
            // (flip ? 1 :-1 ) *
            rotateZ(PI / 12);
            translate(posX, 100 + volume, volume);
            box(10, 100, 10);
            if (i == 1)
                storm(originX, originY, n - 1, posX, posY, false, (volume * 9f) / 10f, flip);
            popMatrix();
        }
        if (firstLayer) {
            popMatrix();
        }
    }

    public void keyPressed() {
        // enables debugger
        if (key == 'n') debugger.doDebug = !debugger.doDebug;
        // allows to hop forward to next lyric
        else if (key == 'p') jumpTo(lyricSync.lyricLL.peek().frame - 100);
        // TODO: REMOVE AFTER TESTING
        else if(key=='q') fov+=5;
        else if(key=='z') fov-=5;
    }

    public void jumpTo(int positionFrame) {
        audioSync.song.jumpFrame(positionFrame);
        while (audioSync.song.positionFrame() < audioSync.peekNext().frame) {
            tempEvent = songInfo.eventList.pop();
        }
        smoothBackground = 0;
    }

    static String[] gameArgs = { "Main" };

    public static void main(String[] args) {
        PApplet.runSketch(gameArgs, new AidanTesting());
    }
}
