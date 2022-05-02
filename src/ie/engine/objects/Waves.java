package ie.engine.objects;

import java.time.chrono.HijrahEra;
import java.util.ArrayList;

import ie.engine.maths.Coordinate;
import processing.core.PApplet;

public class Waves {
    int offset = 0;
    static int backgroundWaves = 7;
    static int backgroundWaveMaxHeight = 6;
    static float backgroundWaveCompression  = 1/10f;
    public float[] sineOffsets;
    public float[] sineAmplitudes;
    public float[] sineStretch;
    public float[] offsetStretches;
    public String tableContent = "";

    public PApplet pa;

    int numPoints = 80;
    //int width = 600;
    float springConstant = 0.005f;
    float springConstantBaseline = 0.005f;
    int yOffset ;
    float damping = 0.99f;
    int iterations = 1;
    class Point{
        Coordinate coord;
        Coordinate speed;
        float mass;
        public Point(Coordinate coord, Coordinate speed, float weight){
            this.coord = coord;
            this.speed = speed;
            this.mass = weight;
        }
        public String toString(){
            return("Point " + " position:" + coord + " speed:" + speed + " weight:" + mass);
        }
        public void draw(){
            pa.fill(0,0);
            pa.stroke(124);
            pa.ellipse(this.coord.x, yOffset + overlapSines(this.coord.x), 5, 5);

            pa.fill(0,0);
            pa.stroke(0x00,0x33,0xbb);
            pa.ellipse(this.coord.x, this.coord.y + overlapSines(this.coord.x), 5, 5);
        }

    } 
    Point[] wavePoints;
    public void makeWavePoints(){
        wavePoints = new Point[numPoints];
        for(int i = 0; i < numPoints; i++){
            wavePoints[i] = new Point(new Coordinate(i / (float)numPoints * pa.width, yOffset, i/(float)numPoints * pa.width), new Coordinate(0, 0,0), 1f);
        }
    }
    public void updatePoints(){
        int randomIteration = (int)pa.random(0, iterations);
        for(var i = 0; i < iterations; i++){
            for(int j = 0; j <numPoints; j ++){
                
                Point p = wavePoints[j];
                float force = 0;
                float forceFromLeft;
                float forceFromRight;
                float forceFromBack;
                float forceFromFront;
                if(j==0){
                    float dy = wavePoints[wavePoints.length -1].coord.y - p.coord.y;
                    forceFromLeft = springConstant * dy;
                } else {
                    float dy = wavePoints[j - 1].coord.y - p.coord.y;
                    forceFromLeft = springConstant * dy;
                } 
                if(j == wavePoints.length -1){
                    float dy = wavePoints[0].coord.y - p.coord.y;
                    forceFromRight = springConstant * dy;
                } else {
                    float dy = wavePoints[j + 1].coord.y - p.coord.y;
                    forceFromRight = springConstant * dy;
                }
                float dy = yOffset - p.coord.y;
                force += forceFromLeft + forceFromRight +  (springConstantBaseline * dy);

                float acceleration = force/ p.mass;
                p.speed.y = damping * p.speed.y + acceleration;
                p.coord.y += p.speed.y;

                // if(randomIteration == j){
                //     force *= 3;
                //     acceleration = force/ p.mass;
                //     p.speed.y = damping * p.speed.y + acceleration;
                //     p.coord.y += p.speed.y*2;
                // }
                //System.out.println(p);
            }
        }
    }
    public Waves(PApplet pa){
        this.pa = pa;
        sineOffsets = new float[backgroundWaves];
        sineAmplitudes = new float[backgroundWaves];
        sineStretch = new float[backgroundWaves];
        offsetStretches = new float[backgroundWaves];
        for(int i = -0; i< backgroundWaves; i++){
            sineOffsets[i] = (float)(-Math.PI + 2f * Math.PI * Math.random());
            sineAmplitudes[i] = (float)Math.random() * backgroundWaveMaxHeight;
            sineStretch[i] = (float)Math.random() * backgroundWaveCompression;
            offsetStretches[i] = (float)Math.random() * backgroundWaveCompression;
        }
        yOffset = (pa.height  /2 )-100;
        makeWavePoints();
    }
    public float overlapSines(float x){
        float result = 0;
        for(int i = 0; i < backgroundWaves; i++){
            result += sineOffsets[i] + sineAmplitudes[i] + PApplet.sin(x * sineStretch[i] + offset + offsetStretches[i]);

        }
        return result;
    }
    public void update(float dt){
        offset += 1;
        // for(int i = -0; i< backgroundWaves; i++){
        //     sineOffsets[i] = (float)(-Math.PI + 2 * Math.PI * Math.random());
        //     sineAmplitudes[i] = (float)Math.random() * backgroundWaveMaxHeight;
        //     sineStretch[i] = (float)Math.random() * backgroundWaveCompression;
        //     offsetStretches[i] = (float)Math.random() * backgroundWaveCompression;
        // }
        updatePoints();
    }
    public void draw(){
        pa.stroke(0xff, 0x33, 0x33, 0x40);
        // pa.line(0, yOffset, pa.width, yOffset);
        
        for(int i = 0; i < numPoints; i++){
            wavePoints[i].draw();
            if(i == 0){

            } else {
                pa.line(wavePoints[i-1].coord.x, wavePoints[i-1].coord.y + overlapSines(wavePoints[i-1].coord.x), wavePoints[i].coord.x,wavePoints[i].coord.y + overlapSines(wavePoints[i].coord.x));
            }
        }
        
        pa.fill(255);
        pa.pushMatrix();
        pa.translate(pa.width * .5f, pa.height*.5f,0f);
        pa.rotateY(PApplet.map(pa.mouseX,0f,pa.width,-PApplet.PI,PApplet.PI));
        pa.rotateX(PApplet.map(pa.mouseY,0f,pa.height,-PApplet.PI,PApplet.PI));
        pa.text("foo!", -20, 0, 20);
        pa.popMatrix();
        }
}
