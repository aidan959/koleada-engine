package ie.engine.implementations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ie.engine.maths.Coordinate;
import processing.core.PApplet;

class EndOfLyricLinkedList extends RuntimeException{

}
public class LyricEventLL {
    public LyricEvent blank;
    PApplet pa;
    private float lyricScaleX = 1;
    private float lyricScaleY = 1;

    private int defaultX;
    private int defaultY;
    public void updateScale(){
        lyricScaleX = (float)pa.width/(float)defaultX;
        lyricScaleY = (float)pa.height/(float)defaultY; 
    }
    public LyricEventLL(PApplet pa){
        this.pa = pa;
        blank = new LyricEvent(0,"", new Coordinate(0, 0, 0), 0, 0,0);
        defaultX = 480;
        defaultY = 480;
    }
    public class LyricEvent{
        public LyricEvent next;
        private String lyric;
        private Coordinate origin;
        private float textSize;
        private float rotation;
        private float width;
        private float height;
        
        private float smoothedSize;

        public int timeToDie = 100;
        public int frame;
        private float textSize1;
        private float scaledTextSize;
        public LyricEvent(int frame, String lyric, Coordinate origin, float textSize, float rotation, int timeToDie){

            this.frame = frame;
            this.lyric = lyric;
            this.origin = origin;
            this.textSize = textSize;
            this.rotation = rotation;
            this.timeToDie = timeToDie;
            this.textSize1 = textSize;
            
            pa.pushMatrix();
            pa.textSize(textSize);
            width = pa.textWidth(lyric);
            height = pa.textAscent();
            pa.popMatrix();
        }
        public void draw(float scale){
            pa.pushMatrix();
            scaledTextSize = textSize * ((lyricScaleX + lyricScaleY)/2);
            if(scale != 1){
                float mappedSize  =PApplet.map(scale, 0,1,scaledTextSize,textSize+5);
                textSize1 = PApplet.lerp(textSize1,mappedSize, 0.1f );
            } else{
                textSize1 = scaledTextSize;
            }
            pa.textSize(textSize1);
            height = pa.textAscent() + textSize1*0.25f;
            width = pa.textWidth(lyric);
            pa.textAlign(processing.core.PConstants.CENTER);
            // pa.fill(0);
            // pa.text(lyric, PApplet.map(origin.x,0,480,0,pa.width), PApplet.map(origin.y,0,480,0,pa.width));
            float x = origin.x * lyricScaleX;
            float y = origin.y  * lyricScaleY;
            pa.fill(255,0,0);
            pa.translate(x, y);
            pa.rotate(PApplet.radians(rotation));
            pa.translate(-x, -y);
            pa.rect(x - width/2, y - textSize1, width, height);
            pa.fill(255);
            pa.text(lyric, x, y);
            
            pa.popMatrix();
        }
        public String toString(){
            return (frame + " at frame, " + lyric + ", origin: " + origin.toString() + ", text-size: " + textSize + " angle: " + rotation );
        }
    }
    public boolean end;
    LyricEvent head; 
    public LyricEvent pop(){
        
        
        if(end || head ==null){
            return blank;
            //throw new EndOfLinkedList();
        }
        LyricEvent tmp = head;  
        head = head.next;
        System.out.println(tmp);
        return tmp;   
    }
    public LyricEvent peek(){
        if(head == null ){
            end = true;
            return blank;
        }
        return head;
    }
    public void load(String file){
        String[] lines; 
        
        try (BufferedReader br = new BufferedReader(new FileReader(file + ".lyr"))) {
            String a;
            int i = 0;
            while ( (a = br.readLine()) != null) {
                lines = a.split(",");
                //System.out.println(++i);
                push(Integer.parseInt(lines[0]), lines[1], new Coordinate(Integer.parseInt(lines[2]),Integer.parseInt(lines[3]),0), Float.parseFloat(lines[4]), Integer.parseInt(lines[5]),Integer.parseInt(lines[6]));
            }
        } catch(IOException e){
            throw new RuntimeException("Couldn't load file");
        }
    }
    LyricEvent p;
    public void push(int frame, String lyrics, Coordinate origin, float textSize, float rotation, int timeToDie){
        LyricEvent newItem = new LyricEvent(frame, lyrics, origin, textSize, rotation,timeToDie);
        if(head == null){
            head = newItem;
            
        } else {
            p = head;
            while(p.next != null){
                p = p.next;
            }
            p.next = newItem;
        }
    }
    public LyricEvent getHead(){
        return head;
    }

}
