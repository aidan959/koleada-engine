package ie.engine.objects;
import ie.engine.maths.Coordinate;
public class Bug extends Entity
{
    
    public Bug(Coordinate coords, float health, int WIDTH, int HEIGHT, float radius){
        super(coords.x, coords.y, health, WIDTH, HEIGHT, radius);
        mass = 100; 
    }
 };
