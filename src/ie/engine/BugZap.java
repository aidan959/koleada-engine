package ie.engine;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import java.util.concurrent.*;

import ie.engine.interaction.*;

import ie.engine.maths.*;
import ie.engine.objects.*;

class RandomNumbers extends PApplet{
    public float[] generateUniqueSet(float lowBound, float highBound, int size) {
        boolean passTest = false;
        float[] output = new float[size];
        for(int x = 0; x < size; x++) {
            output[x] = random(lowBound, highBound); 
        }
        while(!passTest){
            int[] duplicates = checkDuplicates(output, size);
            if(duplicates[0] > 0){
                for(int i = 1; i < duplicates[0]; i++){
                    output[duplicates[i]] = random(lowBound, highBound);
                }
            } else{
                passTest = true;
            }
        }
        return output;
    }
    // returns position of all duplicates - null if none
    private int[] checkDuplicates(float[] array, int size){
        int[] output = {0};
        int array_c = 0;
        for (int i = 0; i < size; i++){
            for(int j = i + 1; j < size; j++){
                if(array[i] == array[j]){
                    output[array_c++] = i;
                }
            }
        }
        // size of array is 0 value - idk how strict java is - with c you can't tell how big an array
        // is when its been passed around. assuming thats the case w generic / legacy arrays?
        output[0] = array_c;
        return output;
    }
}


public class BugZap extends PApplet{
    int windowWidth = 480;
    int windowHeight = 480;

    enum  GameState{
        SPLASH,
        MENU,
        RUNNING,
        EXIT
    }
    public void drawPlayer(Player player){
        player.updateModel();
        stroke(0, 255, 0);
        fill(0, 200, 0);
        ellipse(player.model.points.get(0).x, player.model.points.get(0).y, player.model.radius,  (player.model.radius * 2));
    }

    public void drawProjectile(Projectile projectile){
        stroke( 0,255,0);
        fill(0,200,0);
        ellipse(projectile.getX(), projectile.getY(), 4, 3);
    }

    public void drawBug(Bug bug){
        stroke(255, 0 , 0);
        fill(200,0, 0);
        ellipse(bug.getX(), bug.getY(), bug.size, bug.size);
    }
    public void drawBugs(List<Bug> bugs){
        for(Bug bug : bugs){
            drawBug(bug);
        }
    }
    public void drawMenu(){
        
        for(Menu.MenuObject menuObject : menu.returnMenuObjects()){
            menuObject.selected = false;
            if(mouseOver(menuObject.position, (int)menuObject.size.x, (int)menuObject.size.y)){
                fill(rectHighlight);
                if(mousePressed){
                    menuObject.clicked();
                }
            } else{
                fill(rectColor);
            }
            stroke(255);
            rect(menuObject.position.x, menuObject.position.y, menuObject.size.x, menuObject.size.y);
            fill(0,255,255);
            textSize(30);
            text(menuObject.menuText,  menuObject.textStart.x, menuObject.textStart.y , menuObject.textEnd.x, menuObject.textEnd.y) ;
            
            
        }
        menu.setOutput();
    }
    
    public boolean mouseOver(Coordinate position, int width, int height){
        return (mouseX >= position.x && mouseX <= position.x + width && mouseY >= position.y && mouseY <= position.y + height);
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
        size(windowWidth,windowHeight);
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
    int frameLoop = 300;
    Player player;
    Menu menu;
    GameState state;
    int rectColor;
    int rectHighlight;
    Physics physics;
    boolean flipper = false;
    boolean debugStats = true;
    Koleada koleada;
    ArrayList<Entity> listObjs = new ArrayList<Entity>(maximumObjectIndex);
    ThreadPoolExecutor executor;
    ArrayList<Koleada.Collision> collisionsList = new ArrayList<Koleada.Collision>();
    BlockingQueue<Runnable> threadQueue;
    Semaphore entityListLock = new Semaphore(1);
    float frameTime;
    ForkJoinPool forkJoinPool;
    
    public void generateBugLocations(){
        float[] randomX = randomNumberGen.generateUniqueSet(0, width, numBugs);
        float[] tempLocationY =  randomNumberGen.generateUniqueSet(0, height/2, numBugs);
        
        // spawns d bugs
        for(int i=0; i< numBugs; i++){
            enemyBugs.add(new Bug(new Coordinate(randomX[i], tempLocationY[i]), 100, width, height, 1));
        }
    }

    public void setupMenu(){
        // button coords
        Coordinate buttonSize = new Coordinate((2f * width) /3f, height/6f);
        Coordinate startBtnCoord = new Coordinate(width / 6f, height/16f);
        Coordinate quitBtnCoord = new Coordinate(width / 6f, buttonSize.y + startBtnCoord.y + height / 6f);
        Coordinate creditBtnCoord  = new Coordinate(width / 6f, buttonSize.y + quitBtnCoord.y + height / 6f);
    
        rectColor = color(0);
        rectHighlight = color(51);
        
        menu = new Menu();
        menu.createMenuObject(Menu.MenuChoice.START, "Start Game", startBtnCoord, buttonSize);
        menu.createMenuObject(Menu.MenuChoice.QUIT, "Quit Game", quitBtnCoord, buttonSize);
        menu.createMenuObject(Menu.MenuChoice.CREDITS, "Credits", creditBtnCoord, buttonSize);

    }
    // initializes game options
    public void setupGame(){
        // creates our player
        player = new Player((width/2),(height/2 + height/4), true, 100, width, height, playerSize*10f);
        
        // generates bugs and random locations
        generateBugLocations();
        
        // adds new object to the list of objects
        listObjs.add(player);
        for(Bug bug : enemyBugs){
            listObjs.add(bug);
        }


        // initializes collision enginer
        koleada = new Koleada(listObjs, entityListLock);
        // passes koleada to physics engine and initializes it
        physics = new Physics(listObjs, koleada);

        threadQueue = new LinkedBlockingDeque<>(5);
        // MAKE SURE TO INCREASE THIS TO MATCH THE NUMBER OF THREADS
        executor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.MILLISECONDS, threadQueue, new ThreadPoolExecutor.AbortPolicy());
        executor.prestartAllCoreThreads();
    }
    @Override
    public void setup(){
        state = GameState.MENU;
        setupMenu();
    }
    // TODO check all things in here are being created and recycled before creation
    // ALL USES
    long startTime;
    @Override
    public void draw(){ 
        startTime = System.nanoTime();
        clear();
        switch (state){
            case SPLASH:
                break;
            case MENU:
                drawMenu();
                switch(menu.output){
                    case START:
                        state = GameState.RUNNING;
                        setupGame();
                        break;
                    case QUIT:
                        state = GameState.EXIT;
                        break;
                    case CREDITS:
                        break;
                    case UNSELECTED:
                        break;
                }
                break;
            
            case RUNNING:
                
                try{
                    if(!threadQueue.offer(koleada))
                        throw new IllegalStateException("Could not offer collision queue");
                    if(!threadQueue.offer(physics))
                        throw new IllegalStateException("Could not offer collision queue");
                } catch (IllegalStateException e){
                    System.out.println("Thread failed to be added to queue");
                }

                // sets background
                background(46, 162, 200);
                
                // makes sure the player state inputs match
                player.takeInputs(frameCount);

                if(frameCount % 60 == 0){
                    flip = !flip;
                    if(flip){
                        enemyBugs.get(0).acceleration.x = random(-4,4);
                        enemyBugs.get(0).acceleration.y = random(-4,4);
                    } else{
                        enemyBugs.get(0).acceleration.clear();
                    }
                }
                // TODO - change this to something that checks if listObjs is > 0
                // if it is then we draw each using their own element
                // add something like entity.draw()
                // or a rendering engine which takes the entity and draws it to the screen
                // rendering.draw(entity)
                // draws le player
                drawPlayer(player);
                // checks if there are any bugs alive
                if(!enemyBugs.isEmpty()){
                    drawBugs(enemyBugs);
                }
                if(player.fire){
                    playerFire(player);
                }

                // checks if we have any projectiles to draw
                for(Projectile projectile : projectiles){
                    
                    if(projectile.remove()){
                        drawProjectile(projectile);
                    } else {
                        System.out.println("Removed at " + projectile.returnTimeSpawn());

                        projectiles2remove.add(projectile);
                    }
                }
                for(Projectile projectile : projectiles2remove){
                    projectiles.remove(projectile);
                    listObjs.remove(projectile);
                } 
                projectiles2remove.clear();

                // checks if its were debugging
                if(debugStats){
                    fill(200);
                    frameTime = (System.nanoTime() - startTime) / (float)1000000.0;

                    textSize(10);
                    text("frame time: " + frameTime + "ms" , 5, 10);  // Text wraps within text box
                    
                }
                try{
                    entityListLock.acquire();
                }
                catch(InterruptedException exc){
                    System.out.println(exc);
                    Thread.currentThread().interrupt();

                }
                entityListLock.release();
                

                
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
        float sx;
        float sy;
        for (float a = 0; a < TWO_PI; a += angle) {
            if(count % 2 == 1){
                sx = x + cos(a) * radius;
                sy = y + sin(a) * radius;
                
            } else {
                sx = x + cos(a) * random(2, 4) * radius;
                sy = y + sin(a) * random(2, 4) * radius;
            }
            count++;
            vertex(sx, sy);
            
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
    @Override
    public void keyPressed()
	{
        if(state == GameState.RUNNING){
            // this looks complicated - its basically just a more readable way of
            // checking what button is down
            if (keyCode == LEFT)
            {
                player.inputHandle.down(InputHandler.inputs.LEFT);
            }
            else if(keyCode == RIGHT){
                player.inputHandle.down(InputHandler.inputs.RIGHT);

            }
            else if(keyCode == UP){
                player.inputHandle.down(InputHandler.inputs.UP);

            }
            else if(keyCode == DOWN){
                player.inputHandle.down(InputHandler.inputs.DOWN);

            }
            else if(key == 'n' ){
                debugStats = !debugStats;
            }
            else if (key == ' ')
            {
                player.inputHandle.down(InputHandler.inputs.SPACE);

                player.debugPhys();
                System.out.println("SPACE key pressed");
            }
            else if(keyCode == ESC){
                key = 0;
                state = GameState.MENU;
            }
        }
	}
    public void keyReleased(){
        if(state == GameState.RUNNING){
            if (keyCode == LEFT)
            {
                player.inputHandle.up(InputHandler.inputs.LEFT);
            } else if(keyCode == RIGHT){
                player.inputHandle.up(InputHandler.inputs.RIGHT);
            } else if(keyCode == UP){
                player.inputHandle.up(InputHandler.inputs.UP);
            } else if(keyCode == DOWN){
                player.inputHandle.up(InputHandler.inputs.DOWN);
            } 
            if (key == ' ')
            {
                player.inputHandle.up(InputHandler.inputs.SPACE);
            }
        }
    }
}
