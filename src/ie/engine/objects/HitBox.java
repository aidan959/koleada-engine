package ie.engine.objects;

import java.util.Vector;
public class HitBox {
    Vector<Circle> hitbox_circle;
    Vector<Rectangle> hitbox_rect;
    public HitBox(Vector<Circle> circle_shapes, Vector<Rectangle> rectangle_shapes){
        hitbox_circle = circle_shapes;
        hitbox_rect = rectangle_shapes;
    }
}
