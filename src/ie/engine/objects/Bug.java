package ie.engine.objects;
import ie.engine.maths.Coordinate;
import processing.core.PApplet;
public class Bug extends Entity
{
    
    public Bug(Coordinate coords, float health, float radius, PApplet pa){
        super(coords.x, coords.y, health, radius, pa);
        mass = 100; 
    }
    @Override
    public void draw(){
        updateModel();
        pa.stroke(255, 0 , 0);
        pa.fill(200,0, 0);
        pa.ellipse(getX(), getY(), size,size);
    }
}
