package ie.engine.objects;
import java.util.Vector;
import ie.engine.maths.*;
import processing.core.PApplet;
public class Entity
{
    public float health;
    public float size = 10;
    // physics info
    private Coordinate coordinate;
    public Coordinate acceleration = new Coordinate(0, 0);
    public Coordinate velocity = new Coordinate(0, 0);
    public float mass = 1;
    public Shape model;
    public HitBox hitbox;
    public boolean collision_sleeping;
    private long timeSpawn;
    public PApplet pa;
    // set if the object has already been handled by collision detection
    public boolean handled;
    public Entity(float objX, float objY, float health, float radius, PApplet pa ){
        coordinate = new Coordinate(objX, objY);
        this.pa = pa;
        this.health = health;
        timeSpawn = System.nanoTime();
        model = new Circle(this.getCoord(), radius);
        hitbox = new HitBox(new Vector<Circle>(1), new Vector<Rectangle>(1));
    }
    public void setX(float x){
        
        coordinate.x = x;
    }
    public void setY(float y){
        coordinate.y = y;
    }
    public void setZ(float z){
        coordinate.z = z;
    }
    public float getX(){
        return coordinate.x;
    }
    public float getY(){
        return coordinate.y;
    }
    public float getZ(){
        return coordinate.z;
    }
    public Coordinate getCoord(){
        return coordinate;
    }
    public float moveX(float amount){
        setX(coordinate.x + amount);
        return coordinate.x;
    }
    public float moveY(float amount){
        setY(coordinate.y + amount);
        return coordinate.y;
    }
    public float moveZ(float amount){
        setY(coordinate.z + amount);
        return coordinate.z;
    }
    public void updateModel(){
        this.model.points.set(0, this.getCoord());
    }
    public void moveCoord(Coordinate amount){
        this.coordinate.add(amount);
    }
    public void addAcceleration(Coordinate acceleration){
        this.acceleration.add(acceleration);
    }
    public void addVelocity(Coordinate velocity){
        this.velocity.add(velocity);
    }
    public void setCoord(Coordinate coord){
        this.coordinate = coord;
    }
    public void debugPhys(){
        System.out.println("Player Coords: " + this.coordinate);
        System.out.println("Player Velocity: " + this.velocity);
        System.out.println("Player Acceleration: " + this.acceleration);

    }
    public boolean isTouchingWall(){
        if((getX() <= 0 )|| (getX() >= pa.width) || getY() <= 0 ||  getY() >= pa.height){
            return true;
        }
        return false;
    }
    public long timeSpawn(){
        return timeSpawn;
    }
    public void draw(){

    }
}