package ie.engine;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import java.util.concurrent.*;

import ie.engine.implementations.AudioEventLL;
import ie.engine.implementations.PenroseLSystem;
import ie.engine.implementations.Synchronization;
import ie.engine.interaction.*;
import ie.engine.loading.SongInfo;
import ie.engine.maths.*;
import ie.engine.objects.*;
import ie.engine.testing.Debug;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Window;

public class NRGQAnim extends Scene{
    
    enum  GameState{
        SPLASH,
        MENU,
        RUNNING,
        EXIT
    }
    
    public void drawBugs(List<Bug> bugs){
        for(Bug bug : bugs){
            bug.draw();
        }
    }
    public void playerFire(Player player){
        if(projectiles.size() < projectileLimit ){
            tempProjectile = player.shoot(mouseX, mouseY, 5f); 
            projectiles.add(tempProjectile);
            listObjs.add(tempProjectile);
        }else{
            System.out.println("limit");
        }
    }
    @Override
    public void settings(){
        size(500, 500, P3D);
    }
    int playerSize = 10;
    int bugSize = 1;
    int numBugs = 10;
    int projectileLimit = 10;
    int maximumObjectIndex = projectileLimit + numBugs;
    // temporary projectile used when adding to list of objs
    Projectile tempProjectile;
    ArrayList<Bug> enemyBugs = new ArrayList<Bug>(numBugs);
    ArrayList<Coordinate> bugLocations = new ArrayList<Coordinate>(numBugs);
    ArrayList<Projectile> projectiles = new ArrayList<Projectile>(projectileLimit);
    ArrayList<Projectile> projectiles2remove = new ArrayList<>();
    RandomNumbers randomNumberGen = new RandomNumbers();
    
    Robot rub;
    Window w;
    Player player;
    Menu menu;
    GameState engineState;
    
    Physics physics;
    boolean debugStats = true;
    Koleada koleada;
    ArrayList<Entity> listObjs = new ArrayList<Entity>(maximumObjectIndex);
    ThreadPoolExecutor executor;
    ArrayList<Koleada.Collision> collisionsList = new ArrayList<Koleada.Collision>();
    BlockingQueue<Runnable> threadQueue;
    Semaphore entityListLock = new Semaphore(3);
    float frameTime;
    AudioSync audioSync;
    ForkJoinPool forkJoinPool;
    com.jogamp.newt.opengl.GLWindow win;
    PenroseLSystem ds;
    PenroseLSystem ds1;
    Debug debugger;
    // used to hold threads until all threads are completed
    public Synchronization threadSyncer;
    
    // holds the song information
    SongInfo songInfo;
    
    public void generateBugLocations(){
        float[] randomX = randomNumberGen.generateUniqueSet(0, width, numBugs, this);
        float[] tempLocationY =  randomNumberGen.generateUniqueSet(0, height/2f, numBugs, this);
        
        // spawns d bugs
        for(int i=0; i< numBugs; i++){
            //enemyBugs.add(new Bug(new Coordinate(randomX[i], tempLocationY[i]), 100, 1, this));
        }
    }
    
    public void setupMenu(){
        // button coords
        Coordinate buttonSize = new Coordinate((2f * width) /3f, height/6f);
        Coordinate startBtnCoord = new Coordinate(width / 6f, height/16f);
        Coordinate quitBtnCoord = new Coordinate(width / 6f, buttonSize.y + startBtnCoord.y + height / 6f);
        Coordinate creditBtnCoord  = new Coordinate(width / 6f, buttonSize.y + quitBtnCoord.y + height / 6f);
        
        menu = new Menu(this);
        menu.createMenuObject(Menu.MenuChoice.START, "Start Game", startBtnCoord, buttonSize);
        menu.createMenuObject(Menu.MenuChoice.QUIT, "Quit Game", quitBtnCoord, buttonSize);
        menu.createMenuObject(Menu.MenuChoice.CREDITS, "Credits", creditBtnCoord, buttonSize);
    }
    // initializes game options
    public void setupGame(){
        // creates our player
        player = new Player((width/2),(height/2 + height/4), true, 100, playerSize*10f, this);
        // adds new object to the list of objects
        listObjs.add(player);
        
        // generates bugs and random locations
        // generateBugLocations();
        
        
        // for(Bug bug : enemyBugs){
        //     listObjs.add(bug);
        // }
        
        // initializes collision enginer
        koleada = new Koleada(listObjs);
        // passes koleada to physics engine and initializes it
        physics = new Physics(listObjs, this, koleada);

        String songName = "assets/audio/songs/nrgq.wav"; 
        audioSync = new AudioSync(this, songName);
        audioSync.play();
        
        songInfo = new SongInfo(songName);
        tempEvent = songInfo.eventList.blank;
        
        threadQueue = new LinkedBlockingDeque<>(5);
        // MAKE SURE TO INCREASE THIS TO MATCH THE NUMBER OF THREADS
        executor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.MILLISECONDS, threadQueue, new ThreadPoolExecutor.AbortPolicy());
        executor.prestartAllCoreThreads();
        frameCount = 0;
        
        // gets our window entity
        win = (com.jogamp.newt.opengl.GLWindow) getSurface().getNative();
        // hides the mouse
        win.setPointerVisible(false);

        // this is used to keep mouse centered if using mouse movement
        try {
            rub = new Robot();
        } catch(AWTException e){
            exit();
        }

        // takes collision and psychics engine objects
        threadSyncer = new Synchronization(List.of(koleada, physics));
        
        // important
        koleada.entityLock = threadSyncer;
        physics.entityLock = threadSyncer;

        // used to generate items
        // ds = new PenroseLSystem(this, new Coordinate(width/2/3, height/2, -100), new Coordinate(0, radians(-30f), 0));
        // ds1 = new PenroseLSystem(this, new Coordinate(width/3, height/2, -100), new Coordinate(0, radians(30f), 0));
        
        // ds.simulate(5);
        // ds1.simulate(5);
        
    }
    public void quitGame(){
        beginCamera();
        camera();
        // resets orientation
        rotateX(0);
        rotateY(0);
        rotateZ(0);
        endCamera();
        win.setPointerVisible(true);
        koleada = null;
        physics = null;
        audioSync.song.stop();
        audioSync = null;
        threadSyncer = null;
        threadQueue = null;
        executor.shutdown();
        executor = null;
        win = null;
        player = null;
        ds = null;
    }
    @Override
    public void setup(){
        super.setup();
        engineState = GameState.MENU;
    }
    
    // TODO check all things in here are being created and recycled before creation
    // ALL USES
    long startTime;
    float smoothedColor;
    float currentColor = 46;
    float constColor = 46;
    float currentSize;
    float lastVolume;
    AudioEventLL.AudioEvent tempEvent;
    boolean menuCreated = false;
    boolean doCleanGame = false;
    boolean wasBeat = false;
    @Override
    public void draw(){ 
        super.draw();  
        clear();
        switch (engineState){
            case SPLASH:
                break;
            case MENU:
                if(doCleanGame){
                    quitGame();
                    doCleanGame = false;
                }else{
                if(menuCreated){
                    menu.draw();
                    switch(menu.output){
                        case START:
                            engineState = GameState.RUNNING;
                            setupGame();
                            menuCreated = false;
                            menu = null;
                            break;
                        case QUIT:
                            engineState = GameState.EXIT;
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
                threadSyncer.newFrame();
                try{
                    if(!threadQueue.offer(koleada))
                        throw new IllegalStateException("Could not offer collision queue");
                    if(!threadQueue.offer(physics))
                        throw new IllegalStateException("Could not offer collision queue");
                } catch (IllegalStateException e){
                    System.out.println("Thread failed to be added to queue");
                    exit();
                }

                beginCamera();
                // rotateX(-player.viewAngle.p);
                // rotateY(player.viewAngle.y);
                // rotateZ(player.viewAngle.r);
                pushMatrix();
                // translate(player.getX(),player.getY() , player.getZ());
                camera();
                popMatrix();
                
                endCamera();
                // sets background
                currentColor = lerp(currentColor, constColor, 0.05f );
                tempEvent = audioSync.isBeat();
                wasBeat = audioSync.wasBeat;
                if(wasBeat){
                    currentColor = 255;
                    background(constColor, 20, 20);
                    directionalLight(constColor, 162, 200, -1, 0, 0);
                    
                } else {
                    background(currentColor/10, 20, 20);
                    directionalLight(currentColor, 162, 200, -1, 0, 0);
                    
                }

                if(audioSync.song.positionFrame() < AudioSync.songParts.VERSE1.get() ){
                    pushMatrix();
                    translate(0, height*0.35f, -200f);
                    fill(255,0,0);
                    noStroke();

                    //stroke(255);
                    //sphere(currentSize);
                    sphere(map(audioSync.song.positionFrame(),0, AudioSync.songParts.VERSE1.get(), 0, 100 ));
                    popMatrix(); 
                } else if(audioSync.song.positionFrame() > AudioSync.songParts.VERSE1.get() ){
                    pushMatrix();
                    translate(0, height*0.35f, map(audioSync.song.positionFrame(),AudioSync.songParts.VERSE1.get(), AudioSync.songParts.BRIDGE1.get(), -200, 0 ));
                    fill(255,0,0);
                    noStroke();
                    //stroke(255);
                    sphere(currentSize);
                    //sphere(map(audioSync.song.positionFrame(),0, AudioSync.songParts.VERSE1.get(), 0, 100 ));
                    popMatrix();
                }
                //camera(, player.getY() -player.viewAngle.y *5f, , player.getY(), player.getZ(), 0.0f, 1.0f, 0.0f);
                currentSize = lerp(currentSize, 50 + tempEvent.volume*100, 0.1f );
                pushMatrix();
                translate(0, 0,100);
                for(int i = 0; i < 20; i++){
                    box(15);
                    translate(20, frameCount/100f, 0);
                }
                popMatrix();
                // makes sure the player state inputs match
                player.takeInputs();
                /*
                // if(frameCount % 60 == 0){
                //     flip = !flip;
                //     if(flip){
                //         enemyBugs.get(0).acceleration.x = random(-4,4);
                //         enemyBugs.get(0).acceleration.y = random(-4,4);
                //     } else{
                //         enemyBugs.get(0).acceleration.clear();
                //     }
                // }
                // TODO - change this to something that checks if listObjs is > 0
                // if it is then we draw each using their own element
                // add something like entity.draw()
                // or a rendering engine which takes the entity and draws it to the screen
                // rendering.draw(entity)
                // draws le player
                for(Entity object : listObjs){
                    object.draw();
                }
                
                // // checks if there are any bugs alive
                // if(!enemyBugs.isEmpty()){
                //     drawBugs(enemyBugs);
                // }
                // if(player.fire){

                //     playerFire(player);
                // }

                // // checks if we have any projectiles to draw
                // for(Projectile projectile : projectiles){
                    
                //     if(projectile.remove()){
                //         projectile.draw();
                //     } else {
                //         System.out.println("Removed at " + projectile.timeSpawn());

                //         projectiles2remove.add(projectile);
                //     }
                // }
                // for(Projectile projectile : projectiles2remove){
                //     projectiles.remove(projectile);
                //     listObjs.remove(projectile);
                // } 
                // projectiles2remove.clear();

                // checks if its were debugging
                // HUD to display text 
                // ALL THINGS RENDERED IN HERE RENDER ON TOP
                */
                // ds.render();
                // ds1.render();
                // camera used for HUD
                camera();
                hint(DISABLE_DEPTH_TEST); 
                noLights();
                if(debugStats){
                    debugger.draw(); 
                }
                while(!threadSyncer.isFree());
                
                break;
            case EXIT:
                exit();
                break;
        }
        
    }
    public boolean flip = false;
    public void polygon(float x, float y, float radius, int npoints) {
        float angle = TWO_PI / npoints;
        beginShape();
        int count = 0;
        for (float a = 0; a < TWO_PI; a += angle) {
            if(count % 2 == 1){
                vertex(x + cos(a) * radius, y + sin(a) * radius);
            } else {
                vertex(x + cos(a) * random(2, 4) * radius, sin(a) * random(2, 4) * radius);
            }
            count++;
            
            
            if(frameCount % 255 == 254){
                flip = !flip;
            }
            if (flip){
                fill(frameCount % 255, frameCount % 255, frameCount % 255);
            } else {
                fill(random(0,255), random(0,255), random(0,255));
            }

            
        }
        endShape(CLOSE);
    }
    float cyclusMouse ;
    Coordinate axisRotationPos(float XY, float distanceToFocus)
    {
    
        cyclusMouse = map(XY, 0, width, 0, TAU) ;
        
        float dataPosXY =  cos(cyclusMouse) *distanceToFocus ;
        float dataPosZ =  sin(cyclusMouse) *distanceToFocus ;
        return new Coordinate(dataPosXY, dataPosXY, dataPosZ) ;
    }

    float flSensitivity = 0.001f;
    @Override
    public void mouseMoved(){
        if(engineState == GameState.RUNNING){
            player.viewAngle.p += (mouseY - height/2f) * flSensitivity;
            player.viewAngle.y += (mouseX - width/2f) * flSensitivity;
            player.viewAngle.r = 0;
            player.viewAngle.normalize();
            // println(player.viewAngle.p);
            // resets mouse to center of screen
            rub.mouseMove( win.getX() +  width/2, win.getY() + height/2);
        }
    }
    @Override
    public void keyPressed()
	{
        if(engineState == GameState.RUNNING){
            // this looks complicated - its basically just a more readable way of
            // checking what button is down
            if (keyCode == LEFT || key == 'a')
            {
                player.inputHandle.down(InputHandler.inputs.LEFT);
            }
            else if(keyCode == RIGHT || key == 'd'){
                player.inputHandle.down(InputHandler.inputs.RIGHT);

            }
            else if(keyCode == UP || key == 'w'){
                player.inputHandle.down(InputHandler.inputs.UP);

            }
            else if(keyCode == DOWN || key == 's'){
                player.inputHandle.down(InputHandler.inputs.DOWN);

            }
            else if( key == 'x'){
                player.inputHandle.down(InputHandler.inputs.FRONT);

            }
            else if( key == 'c'){
                player.inputHandle.down(InputHandler.inputs.BACK);

            }
            else if(key == 'n' ){
                debugStats = !debugStats;
            }
            else if (key == ' ')
            {
                player.inputHandle.down(InputHandler.inputs.SPACE);

                if(debugStats){
                    player.debugPhys();
                }
                System.out.println("SPACE key pressed");
            }
            else if(keyCode == ESC){
                key = 0;
                doCleanGame = true;
                engineState = GameState.MENU;
            } else if(key == '0'){
                audioSync.song.jumpFrame((int)AudioSync.songParts.INTRO.get());
            }
            else if(key == '1'){
                audioSync.song.jumpFrame((int)AudioSync.songParts.VERSE1.get());
            }
        }
	}
    @Override
    public void keyReleased(){
        if(engineState == GameState.RUNNING){
            if (keyCode == LEFT || key == 'a')
            {
                player.inputHandle.up(InputHandler.inputs.LEFT);
            }
            else if(keyCode == RIGHT || key == 'd'){
                player.inputHandle.up(InputHandler.inputs.RIGHT);

            }
            else if(keyCode == UP || key == 'w'){
                player.inputHandle.up(InputHandler.inputs.UP);

            }
            else if(keyCode == DOWN || key == 's'){
                player.inputHandle.up(InputHandler.inputs.DOWN);

            }
            else if( key == 'x'){
                player.inputHandle.up(InputHandler.inputs.FRONT);

            }
            else if( key == 'c'){
                player.inputHandle.up(InputHandler.inputs.BACK);

            }
            else if (key == ' ')
            {
                player.inputHandle.up(InputHandler.inputs.SPACE);
            }
        }
    }
}
