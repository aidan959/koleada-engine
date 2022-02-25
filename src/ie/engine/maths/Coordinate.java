package ie.engine.maths;

public class Coordinate{
    public float x;
    public float y;
    public Coordinate(float x, float y){
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return "X: " + this.x + ", Y: " + this.y;
    }
    public boolean hasMagnitude(){
        if(x == 0  && y == 0){
            return false;
        }
        else{
            return true;
        }
    }
    public void flip(){
        x = -x;
        y = -y;
    }
    public void add(Coordinate coord){
        x += coord.x;
        y += coord.y;
    }
    public void add(float v, float t){
        x += v;
        y += t;
    }
    public void divide(float v){
        x /= v;
        y /= v;
    }
    public void divide(Coordinate c){
        x/=c.x; 
        y/=c.y;
    }
    public void divide(float v, float t){
        x /= v;
        y /= t;
    }
    public void multiply(float v){
        x *= v;
        y *= v;
    }
    public void multiply(float v, float t){
        x *= v;
        y *= t;
    }
    public void multiply(Coordinate c){
        x *= c.x;
        y *= c.y;
    }
    public void clear(){
        x = 0;
        y = 0;
    }
    public float distance(Coordinate c){
        
        return distance(c.x, c.y);
    }
    public float distance(float v, float t){
        return (float) Math.sqrt(Math.pow(x-v, 2) + Math.pow(y-t,2));
    }
    public float magnitude(){
        return((float)Math.sqrt(x*x + y*y));
    }
    
}