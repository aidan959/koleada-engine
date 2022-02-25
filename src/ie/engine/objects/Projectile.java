package ie.engine.objects;
import ie.engine.maths.Coordinate;
public class Projectile extends Entity{
    long timeToLive = 500000000L;
    public Projectile(float X,float Y, Coordinate velocity, float health, int WIDTH, int HEIGHT, float radius){
        super(X, Y, health, WIDTH, HEIGHT, radius);
        this.velocity = velocity;
        System.out.println(velocity);
    }
    public boolean remove(){
        return((System.nanoTime() - returnTimeSpawn()) <= timeToLive);
        
    }
}
