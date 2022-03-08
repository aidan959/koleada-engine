package ie.engine.objects;
import ie.engine.maths.Coordinate;
import processing.core.PApplet;
public class Projectile extends Entity{
    long timeToLive = 500000000L;
    public Projectile(float X,float Y, Coordinate velocity, float health, float radius, PApplet pa){
        super(X, Y, health, radius, pa);
        this.velocity = velocity;
        System.out.println(velocity);
    }
    public boolean remove(){
        return((System.nanoTime() - timeSpawn()) <= timeToLive);
        
    }
    public void draw(){
        pa.stroke( 0,255,0);
        pa.fill(0,200,0);
        pa.ellipse(getX(), getY(), 4, 3);
    }
}
