package ie.engine.testing;


import ie.engine.Scene;
import ie.engine.implementations.AudioEventLL.AudioEvent;
import ie.engine.interaction.AudioSync;
import ie.engine.loading.SongInfo;
import ie.engine.maths.Animation;
import ie.engine.maths.RGBColor;
import ie.engine.objects.Waves;
import processing.core.PApplet;

public class TweedyNS extends Scene{
    public void settings(){
        super.settings();
    }
    public void setup(){
        super.setup();
        String songName = "assets/audio/songs/nrgq.wav";
        audioSync = new AudioSync(this, songName);
        audioSync.play();
        songInfo = new SongInfo(songName);
        lastEvent = audioSync.songInfo.eventList.blank;
        // bridge to chorus -- will start from bridge2
        audioSync.song.jumpFrame((int)AudioSync.songParts.BRIDGE2.get());
    }

    float lerpValue;
    Animation testAnimation;
    float smoothBackground;
    float smoothCircle = 0;
    float smoothCircle2 = 0;
    float smoothColor = 0;
    float circleFill = 0;
    float angle = 0;
    float multiplier = 4.5f;
    int filled = 0;
    int maxCubes = 4;
    int circles = 1;
    int numCircles = 9;
    
    public float border = 0;

    public void draw(){
        super.draw();
        noStroke();
        camera();
        lights();
        tempEvent = audioSync.isBeat();
        wasBeat = audioSync.wasBeat;
        background(0,0, smoothBackground);
        smoothCircle = lerp(smoothCircle, (tempEvent.volume * 1000) + 5, 0.05f);
        smoothCircle2 = lerp(smoothCircle2, (tempEvent.volume * 555) + 5, 0.02f);
        smoothColor = lerp(smoothColor, (tempEvent.volume * 555) + 5, 0.07f);
        int currentFrame = audioSync.song.positionFrame();
        RGBColor rand = RGBColor.toRGB((int)(random(0, 255)), 255, 255, this);
        

        
        // can draw stuff outside the if(wasBeat) too
        if (wasBeat) {
            // runs if there was a beat provided

            // only execute before / after first verse etc
            // if(audioSync.song.positionFrame() < AudioSync.songParts.VERSE1.get() ){

            // how close to next frame
            // map(audioSync.song.positionFrame(),0, AudioSync.songParts.VERSE1.get(), 0, 100 );

            // grow from 0 to 100
            // map(audioSync.song.positionFrame(),AudioSync.songParts.BRIDGE1.get(), AudioSync.songParts.CHORUS1.get(), 0, 100 );

            smoothBackground = lerp(0.8f,tempEvent.volume, smoothBackground );

            smoothColor = 100;

            if (currentFrame > 9341420) {
                if (circles > 9) {
                    circles = 1;
                    multiplier = 4.5f;
                } else {
                    circles++;
                    multiplier-= 0.25f;
                }
            }

            System.out.println(currentFrame);
        } else {
            smoothBackground = lerp(0.8f,0, smoothBackground );
            
            // angle is in radians (in map change sin(angle) -> sin(degrees(angle)) 
            angle += 0.02f; //      if you want degrees)
            // first var in map is what you're converting with range (var2->var3) 
            // to the range (var4->var5) for fill() etc
            // fill(map(sin(angle), -1, 1, 0, 255), map(sin(angle*5), -1, 1, 0, 255), map(sin(angle), -1, 1, 0, 255));

            // center circle
            // if (currentFrame >= 9341420) {
                pushMatrix();
                noFill();
                strokeWeight(5);
                // translate(0, 0, 100);
                for (int i = 0; i < circles; i++) {
                    RGBColor c = RGBColor.toRGB((int)(map(sin(angle*(i+1)), -1, 1, 0, 255)), 255, 255, this);
                    // RGBColor c = RGBColor.toRGB(, 255, 255, this);
                    if (currentFrame < 9907784) {
                        stroke(map(sin(angle*4), -1, 1, 125, 255)+(i*10), 0, 199);
                        // stroke(c.r, c.g, c.b);
                    } else if (currentFrame < 9898417 && currentFrame > 9907784) {
                        stroke(map(sin(angle*4), -1, 1, 125, 255)+(i*10), 0, 199);
                    } else if (currentFrame >= 11172844) {
                        stroke(c.r, c.g, c.b);
                    } else {
                        stroke(0, map(sin(angle*4), -1, 1, 150, 255)+(i*10), 199);
                        // stroke(0, 255, 119);
                    }
                    circle(width/2, height/2, (50+(i*50))+(smoothCircle*2)*multiplier);
                }
                // circle(width/2, height/2, smoothCircle*10);
                popMatrix();
            // }

            

            // sphere grid
            pushMatrix();
            translate(0, 0, -5);
            noStroke();
            for (int i = 0; i < numCircles; i++) {
                for (int j = 0; j < numCircles; j++) {
                    fill(0, 255, 128);
                    float t = i*j % 2 == 0 ? -angle : angle;
                    // float c = map(arg0, arg1, arg2, arg3, arg4)
                    float c = map((i * j), 0, 9, 0, 255) % 256;
                    RGBColor cRGB = RGBColor.toRGB((int)((map(sin(t*(i+j+1)), -1, 1, 0, 255))+smoothColor), 255, 255, this);

                    // if (currentFrame < 9907784) {
                    //     if (circles % 2 == 0) {
                    //         fill(c+180, c+12, c+60);
                    //     } else {
                    //         fill(c+12, c+100, c+60);
                    //     }
                    // }
                    // else if (currentFrame > 10534321) {
                        
                    // } 
                    if (currentFrame > 11172844) {
                        fill(cRGB.r, cRGB.g, cRGB.b);
                    } else {
                        // fill(c.r+12, c.g+100, c.b+60);
                        // fill(c+12, c+100, c+60);
                        if (circles % 2 == 0) {
                            fill(c+180, c+12, c+60);
                        } else {
                            fill(c+12, c+100, c+60);
                        }
                    }

                    // float mapX = map(i, 0, 9, (width)/11, (width*10)/11);
                    // float mapY = map(j, 0, 9, (height)/11, (height*10)/11);
                    float mapX = map(i, 0, numCircles, 0, width);
                    float mapY = map(j, 0, numCircles, 0, height);
                    
                    pushMatrix();
                    translate(mapX+25, mapY+25);
                    sphere(5+smoothCircle*1.2f);
                    popMatrix();
                }
            }
            popMatrix();
            
        }
        if(currentFrame > 9907784 ) {
            // cuboids
            for (int i = 1; i < maxCubes; i++) {
                for (int j = 1; j < maxCubes; j++) {
                    strokeWeight(2);
                    pushMatrix();
                    // translate(width*i/4, height*j/4, map(sin(audioSync.song.positionFrame()), 0, 100000, 60, 50));
                    rectMode(CENTER);
                    RGBColor c = RGBColor.toRGB((int)((map(sin(angle*(i+j+1)), -1, 1, 0, 255))+smoothColor), 255, 255, this);
                    stroke(0, 255, 140);
                    noFill();
                    translate(width*i/4, height*j/4, map(sin(angle), -1, 1, -400, -10));
                    rotate(sin(angle)*2);
                    rotateY(angle*sin(j));
                    rotateZ(sin(angle));
                    if (currentFrame >= 11172844) {
                        stroke(rand.r, rand.g, rand.b);
                        rotate(cos(angle)*2);
                        rotateY(angle*sin(j));
                        rotateZ(angle);
                        box((int)(70*(i/0.2)));
                    } else if (currentFrame < 11172844) {
                        if (circles % 2 == 0) {
                            stroke(255, 0, 153);
                        } else {
                            stroke(0, 255, 140);
                        }
                        box(smoothCircle+70);
                    }
                    if (currentFrame >= 10534321) {
                        stroke(c.r, c.g, c.b);
                        box(smoothCircle+70);
                    }
                    // box(70);
                    popMatrix();

                    pushMatrix();
                    translate(width*i/4, height*j/4, map(sin(angle), -1, 1, -400, -10));
                    rotate(sin(angle)*4);
                    rotateY(angle*3);
                    rotateZ(cos(angle*2));
                    box(25);
                    popMatrix();
                }
            }
        }
        // System.out.println(bp.process());
        
        // debug camera
        camera();
        hint(DISABLE_DEPTH_TEST); 
        noLights();

        if(debugger.doDebug){
            debugDictionary.get("frametime").updateValue(debugger.getFrameTime());
            debugDictionary.get("framerate").updateValue(frameRate);
            debugDictionary.get("avgbeattime").updateValue((((float)audioSync.averageBeatTime)/(float)(songInfo.sampleRate)));
            debugger.draw();
        }
    }
    public void keyPressed(){
        super.keyPressed();
        if(key == 'n' ){
            debugger.doDebug = !debugger.doDebug;
        }
    }
    static String[] gameArgs = {"Main"};
    public static void main(String[] args){
        PApplet.runSketch(gameArgs, new TweedyNS());
    }
}
