package ie.engine.maths;

import processing.core.PApplet;

public class RGBColor {
    public int r;
    public int g;
    public int b;

    public RGBColor(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }
    public static RGBColor toRGB(int h, int s, int b, PApplet pa){
        pa.pushStyle();
        pa.colorMode(PApplet.HSB,360,100,100,255);
        int dissect = pa.color(h,s,b,255);
        pa.popStyle();
        return new RGBColor((dissect>>16)&255,(dissect>>8)&255,(dissect>>0)&255);
    }

}
