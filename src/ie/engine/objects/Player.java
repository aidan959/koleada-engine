package ie.engine.objects;
import ie.engine.interaction.*;
import ie.engine.maths.Coordinate;

public class Player extends Entity
{

    public float playerSpeed = 1;
    public InputHandler inputHandle = new InputHandler();
    public int lastFire = 0;
    static int fireWait = 1000;
    public boolean fire = false;
    public Player(float pX,float pY, boolean defSpawn, float health, int WIDTH, int HEIGHT, float radius){    
        super(pX, pY, health, WIDTH, HEIGHT, radius);
        if (defSpawn){
            setX(WIDTH/2f);
            setY(HEIGHT - (HEIGHT/8f));
        }
        this.WIDTH = WIDTH;
        this.HEIGHT = WIDTH;
        this.model = new Circle(this.getCoord(), 10);
    }
    
    public void takeInputs(int frame){
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
        if(inputHandle.get(InputHandler.inputs.SPACE) && (frame - lastFire) > (fireWait)){
            fire = true;
        }
    }
    public Projectile shoot(float mouseX, float mouseY, float velocity1){
        // 
        float heighttoMouse = mouseY - getY();
        float widthtoMouse = mouseX - getX();
        float angleOfProjectile = (float) Math.tanh(heighttoMouse/widthtoMouse);
        Coordinate velocity = new Coordinate((float)(velocity1 * Math.cos(angleOfProjectile)),(float)(velocity1 * Math.sin(angleOfProjectile)) );    
        return new Projectile(getX(), getY(), velocity, health, WIDTH, HEIGHT, 1);
    }
};
