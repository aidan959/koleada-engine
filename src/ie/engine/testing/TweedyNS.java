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
        // audioSync.song.jumpFrame((int)AudioSync.songParts.BRIDGE2.get());
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
    int circlecol = 0;
    
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
                
        if (wasBeat) {
            // runs if there was a beat provided

            smoothBackground = lerp(0.8f,tempEvent.volume, smoothBackground );

            smoothColor = 100;

            if (circles > 9 && currentFrame >= 9341420) { // when circles appear they start at 1
                circles = 1;
                multiplier = 4.5f;
            } else {
                circles++;
                multiplier-= 0.25f; // decreases circles radius multiplier the more that are added
            }
            
            circlecol++; // increments to swap spheres' colours

            System.out.println(currentFrame);
        } else {
            smoothBackground = lerp(0.8f,0, smoothBackground );
            
            // angle is in radians (in map change sin(angle) -> sin(degrees(angle)) 
            angle += 0.02f;

            // center circle
            if (currentFrame >= 9341420) {
                pushMatrix();
                noFill();
                strokeWeight(5);
                for (int i = 0; i < circles; i++) {
                    RGBColor cRGB = RGBColor.toRGB((int)(map(sin(angle*(i+1)), -1, 1, 0, 255)), 255, 255, this);

                    if (currentFrame >= 10695281) {
                        stroke(cRGB.r, cRGB.g, cRGB.b); // rgb 
                    } else {
                        if (circlecol % 3 == 0) {
                            stroke(map(sin(angle*4), -1, 1, 125, 255)+(i*10), 0, 199); // red/pink
                        } else if (circlecol % 3 == 1) {
                            stroke(0, map(sin(angle*4), -1, 1, 150, 255)+(i*10), 150); // green
                        } else if (circlecol % 3 == 2) {
                            stroke(0, 199, map(sin(angle*4), -1, 1, 150, 255)+(i*10)); // blue 
                        }
                    }

                    circle(width/2, height/2, (50+(i*50))+(smoothCircle*2)*multiplier);
                }
                popMatrix();
            }

            // sphere grid
            pushMatrix();
            translate(0, 0, -5);
            noStroke();
            for (int i = 0; i < numCircles; i++) {
                for (int j = 0; j < numCircles; j++) {
                    fill(0, 255, 128);
                    float t = i*j % 2 == 0 ? -angle : angle;
                    float c = map((i * j), 0, 9, 0, 255) % 256;
                    RGBColor cRGB = RGBColor.toRGB((int)((map(sin(t*(i+j+1)), -1, 1, 0, 255))+smoothColor), 255, 255, this);

                    if (currentFrame > 11172844) {
                        fill(cRGB.r, cRGB.g, cRGB.b);
                    } else {
                        if (circlecol % 3 == 0) {
                            fill(c+180, c+12, c+60); // red
                        } else if (circlecol % 3 == 1) {
                            fill(c+12, c+100, c+60); // green
                        } else if (circlecol % 3 == 2) {
                            fill(c+12, c+110, c+180); // blue
                        }
                    }

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
                    rectMode(CENTER);
                    RGBColor c = RGBColor.toRGB((int)((map(sin(angle*(i+j+1)), -1, 1, 0, 255))+smoothColor), 255, 255, this);
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
                        strokeWeight(4);
                        stroke(255);   
                        box(smoothCircle+70);
                    }
                    if (currentFrame >= 10534321) {
                        stroke(c.r, c.g, c.b);
                        box(smoothCircle+70);
                    }
                    popMatrix();

                    // smaller cubes
                    pushMatrix();
                    translate(width*i/4, height*j/4, map(sin(angle), -1, 1, -400, -10));
                    rotate(sin(angle)*4);
                    rotateY(angle*3);
                    rotateZ(cos(angle*2));
                    strokeWeight(2);
                    if (circlecol % 3 == 0) {
                        stroke(255, 0, 153); // red
                    } else if (circlecol % 3 == 1) {
                        stroke(0, 255, 140); // green
                    } else if (circlecol % 3 == 2) {
                        stroke(5, 210, 255); // blue
                    }
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
