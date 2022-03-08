package ie.engine.objects;
import ie.engine.interaction.*;
import ie.engine.maths.Coordinate;
import processing.core.PApplet;

public class Player extends Entity
{

    public float playerSpeed = 1;
    public InputHandler inputHandle = new InputHandler();
    public int lastFire = -1000;
    static int fireWait = 10;
    public boolean fire = false;
    public Player(float pX,float pY, boolean defSpawn, float health,  float radius, PApplet pa ){    
        super(pX, pY, health, radius, pa);
        if (defSpawn){
            setX(pa.width/2f);
            setY(pa.height - (pa.height/8f));
        }
        this.model = new Circle(this.getCoord(), 10);
    }
    
    public void takeInputs(){
        this.acceleration.clear();
        fire = false;
        if(inputHandle.get(InputHandler.inputs.LEFT)){
            this.acceleration.add(-playerSpeed, 0f);
            this.collision_sleeping = false;
        }
        if(inputHandle.get(InputHandler.inputs.RIGHT)){
            this.acceleration.add(playerSpeed, 0f);
            this.collision_sleeping = false;
        }
        if(inputHandle.get(InputHandler.inputs.UP)){
            this.acceleration.add(0, -playerSpeed);
            this.collision_sleeping = false;
        }
        if(inputHandle.get(InputHandler.inputs.DOWN)){
            this.acceleration.add(0, playerSpeed);
            this.collision_sleeping = false;    
        }
        // checks if enough frames have passed to fire
        if(inputHandle.get(InputHandler.inputs.SPACE) && (pa.frameCount - lastFire) > (fireWait)){
            fire = true;
        }
    }
    public Projectile shoot(float mouseX, float mouseY, float velocity1){
        // 
        float heighttoMouse = mouseY - getY();
        float widthtoMouse = mouseX - getX();
        float angleOfProjectile = (float) Math.tanh(heighttoMouse/widthtoMouse);
        Coordinate velocity;
        if(widthtoMouse < 0){
            if(heighttoMouse < 0){
                velocity = new Coordinate((float)(-velocity1 * Math.cos(angleOfProjectile)),(float)(velocity1 * Math.sin(angleOfProjectile)) );    

            }else{
                velocity = new Coordinate((float)(-velocity1 * Math.cos(angleOfProjectile)),(float)(-velocity1 * Math.sin(angleOfProjectile)) );    
            }
        } else {
            if(heighttoMouse < 0){
                velocity = new Coordinate((float)(velocity1 * Math.cos(angleOfProjectile)),(float)(-velocity1 * Math.sin(angleOfProjectile)) );    
            } else {
                velocity = new Coordinate((float)(velocity1 * Math.cos(angleOfProjectile)),(float)(velocity1 * Math.sin(angleOfProjectile)) );    

            }
        }
        return new Projectile(getX(), getY(), velocity, health, 1, pa);
    }
    @Override
    public void draw(){
        updateModel();
        pa.stroke(0, 255, 0);
        pa.fill(0, 200, 0);
        pa.ellipse(model.points.get(0).x, model.points.get(0).y, model.radius,  (model.radius * 2));
    }
};
