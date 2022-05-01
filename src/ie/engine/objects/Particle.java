package ie.engine.objects;

import ie.engine.maths.Coordinate;
import processing.core.PApplet;

public class Particle {
    Coordinate position;
    Coordinate velocity;
    Coordinate acceleration;
    float lifespan;
    PApplet pa;
    public Particle(Coordinate center, PApplet pa) {
      acceleration = new Coordinate(0, 0f, -0.5f);
      this.pa = pa;
      velocity = new Coordinate(5, 5, 5);
      position = center.duplicate();
      lifespan = 100.0f;
    }
    public Particle(Coordinate center, Coordinate accel, Coordinate vel, PApplet pa){
      acceleration = accel;
      this.pa = pa;
      velocity = vel;
      position = center.duplicate();
      lifespan = 100.0f;
    }
  
    public void run() {
      update();
      display();
    }
  
    // Method to update position
    public void update() {
      velocity.add(acceleration);
      position.add(velocity);
      lifespan -= 2.0;
    }
  
    // Method to display
    public void display() {
        pa.pushMatrix();
        pa.stroke(255, lifespan);
        pa.fill(255, lifespan);
        pa.translate(position.x, position.y, position.z);
        pa.box(8);
        pa.popMatrix();
    }
  
    // Is the particle still useful?
    public boolean isDead() {
      return (lifespan < 0.0);
    }
  }