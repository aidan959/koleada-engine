package ie.engine.objects;

import ie.engine.maths.Coordinate;
import java.util.Vector;

public class Circle extends Shape {
    public Circle(Coordinate center, float radius){
        this.points = new Vector<Coordinate>(1,1);
        this.points.add(center);
        this.radius = radius;

    }
}
