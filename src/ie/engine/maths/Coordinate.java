package ie.engine.maths;

public class Coordinate{
    public float x;
    public float y;
    public float z;
    public Coordinate(float x, float y){
        this.x = x;
        this.y = y;
    }
    public Coordinate(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public String toString(){
        return "X: " + this.x + ", Y: " + this.y + ", Z: " + this.z;
    }
    public boolean hasMagnitude(){
        if(x == 0  && y == 0 && z == 0){
            return false;
        }
        else{
            return true;
        }
    }
    public void flip(){
        x = -x;
        y = -y;
        z = -z;
    }
    public void add(Coordinate coord){
        x += coord.x;
        y += coord.y;
        z += coord.z;
    }
    public void add(float v, float t, float u){
        x += v;
        y += t;
        z += u;
    }
    public void divide(float v){
        x /= v;
        y /= v;
        z /= v;
    }
    public void divide(Coordinate c){
        x/=c.x; 
        y/=c.y;
        z/=c.z;
    }
    public void divide(float v, float t, float u){
        x /= v;
        y /= t;
        z /= u;
    }
    public void multiply(float v){
        x *= v;
        y *= v;
        z *= v;
    }
    public void multiply(float v, float t, float u){
        x *= v;
        y *= t;
        z *= u;
    }
    public void multiply(Coordinate c){
        x *= c.x;
        y *= c.y;
        z *= c.z;
    }
    public void clear(){
        x = 0;
        y = 0;
        z = 0;
    }
    public Coordinate get(){
        return this;
    }
    public float distance(Coordinate c){
        
        return distance(c.x, c.y, c.z);
    }
    public float distance(float v, float t, float u){
        return (float) Math.sqrt(Math.pow(x-v, 2) + Math.pow(y-t,2) + Math.pow(z-u,2));
    }
    public float magnitude(){
        return((float)Math.sqrt(x*x + y*y + z*z));
    }
    public Coordinate map(Coordinate destination, float line){
        return new Coordinate((destination.x - this.x) * line, 
                              (destination.y - this.y) * line,
                              (destination.z - this.z) * line);
    }
    public Coordinate duplicate(){
        return new Coordinate(x, y, z);
    }
}