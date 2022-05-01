package ie.engine.implementations;

import java.util.ArrayList;

import ie.engine.maths.Coordinate;
import ie.engine.objects.Particle;
import processing.core.PApplet;

public class ParticleSystem {
    ArrayList<Particle> particles;
    Coordinate center;
    PApplet pa;
    public ParticleSystem(Coordinate center, PApplet pa){
        this.center = center.duplicate();
        particles = new ArrayList<Particle>(5);
        this.pa = pa;
    }
    public void run(){
        for(int i = particles.size()-1; i>= 0; i--){
            Particle p = particles.get(i);
            p.run();
            if(p.isDead()){
                particles.remove(i);
            }
        }
    }
    public void addParticle() {
        Particle p;
        p = new Particle(center,pa);
        particles.add(p);
    }
    public void addParticle(Coordinate offset, Coordinate accel, Coordinate vel) {
        Particle p;
        Coordinate c = center.duplicate();
        c.add(offset);
        p = new Particle(c,accel,vel, pa);
        particles.add(p);
    }
    public void addParticle(Particle p){
        particles.add(p);
    }
    public boolean dead(){
        return particles.isEmpty();
    }
}
