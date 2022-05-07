package ie.engine;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.*;

import ie.engine.implementations.AudioEventLL;
import ie.engine.implementations.PenroseLSystem;
import ie.engine.implementations.Synchronization;
import ie.engine.interaction.*;
import ie.engine.loading.SongInfo;
import ie.engine.maths.*;
import ie.engine.testing.AidanTesting;
import ie.engine.testing.BenTesting;
import ie.engine.testing.Kamilatesting;
import ie.engine.testing.TweedyNS;


public class NRGQAnim extends Scene {

    enum GameState {
        SPLASH,
        MENU,
        RUNNING,
        EXIT
    }

    Menu menu;
    GameState state;

    public void setupMenu() {
        // button coords
        Coordinate buttonSize = new Coordinate((2f * width) / 3f, height / 6f);
        Coordinate startBtnCoord = new Coordinate(width / 6f, height / 16f);
        Coordinate quitBtnCoord = new Coordinate(width / 6f, buttonSize.y + startBtnCoord.y + height / 6f);
        Coordinate creditBtnCoord = new Coordinate(width / 6f, buttonSize.y + quitBtnCoord.y + height / 6f);

        menu = new Menu(this);
        menu.createMenuObject(Menu.MenuChoice.START, "Start Game", startBtnCoord, buttonSize);
        menu.createMenuObject(Menu.MenuChoice.QUIT, "Quit Game", quitBtnCoord, buttonSize);
        menu.createMenuObject(Menu.MenuChoice.CREDITS, "Credits", creditBtnCoord, buttonSize);

    }
    public void setupScene(Scene scene){
        scene.setSurface(surface);
        scene.g = g;
        scene.audioSync = audioSync;
        scene.songInfo = songInfo;
        scene.debugDictionary = debugDictionary;
        scene.debugger = debugger;
        scene.bp = bp;
    }
    public void updateScene(Scene scene){
        scene.width = width;
        scene.height = height;
        scene.frameCount = frameCount;
        pushMatrix();
        pushStyle();
        colorMode(RGB);
        scene.draw();
        popStyle();
        popMatrix();
    }
    BenTesting benCode;
    TweedyNS tweedyCode;
    AidanTesting aidanCode;
    Kamilatesting kamilaCode;
    boolean benRan;
    boolean tweedyRan;
    boolean kamilaRan;
    boolean aidanRan;

    @Override
    public void setup() {
        super.setup();

        state = GameState.MENU;

    }

    // TODO check all things in here are being created and recycled before creation
    // ALL USES
    boolean menuCreated = false;
    boolean doCleanGame = false;
    boolean wasBeat = false;
    public void startAnim(){
        String songName = "assets/audio/songs/nrgq.wav";
        audioSync = new AudioSync(this, songName);
        songInfo = new SongInfo(songName);
        lastEvent = audioSync.songInfo.eventList.blank;
        benCode = new BenTesting();
        tweedyCode = new TweedyNS();
        aidanCode = new AidanTesting();
        kamilaCode = new Kamilatesting();
        audioSync.play();
    }
    @Override
    public void draw() {
        super.draw();
        switch (state) {
            case SPLASH:
                break;
            case MENU:
                if (doCleanGame) {
                    doCleanGame = false;
                } else {
                    if (menuCreated) {
                        menu.draw();
                        switch (menu.output) {
                            case START:
                                state = GameState.RUNNING;
                                menuCreated = false;
                                menu = null;
                                startAnim();
                                break;
                            case QUIT:
                                state = GameState.EXIT;
                                menuCreated = false;
                                break;
                            case CREDITS:
                                break;
                            case UNSELECTED:
                                break;
                        }
                    } else {
                        setupMenu();
                        menuCreated = true;
                    }
                }
                break;

            case RUNNING:
                if(currentFrame < AudioSync.songParts.CHORUS1.get()){
                    if(!aidanRan){
                        aidanRan = true;
                        setupScene(aidanCode);
                        aidanCode.setupInstance(); 
                    }
                    updateScene(aidanCode);

                } else if(currentFrame < AudioSync.songParts.BRIDGE1.get()){
                    if(!benRan){
                        benRan = true;
                        setupScene(benCode);
                        benCode.setupInstance(); 
                        aidanCode = null;
                    }
                    
                    updateScene(benCode);
                } else if (currentFrame < AudioSync.songParts.BRIDGE2.get()){
                    if(!kamilaRan){
                        kamilaRan = true;
                        setupScene(kamilaCode);
                        kamilaCode.setupInstance(); 
                        benCode = null;
                    }
                    updateScene(kamilaCode);

                } else {
                    if(!tweedyRan){
                        tweedyRan = true;
                        setupScene(tweedyCode);
                        tweedyCode.setupInstance(); 
                        kamilaCode = null;
                    }
                    updateScene(tweedyCode);
                }
                
                break;
            case EXIT:
                exit();
                break;
        }

    }
    public void keyPressed(){
        super.keyPressed();
        aidanCode.keyPressed();
    }
}
