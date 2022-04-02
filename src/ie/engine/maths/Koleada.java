package ie.engine.maths;

import java.util.ArrayList;
import java.util.Queue;
import java.util.List;

import ie.engine.implementations.Synchronization;
import ie.engine.objects.*;

import java.util.LinkedList;
public class Koleada implements Runnable{
    public List<Entity> detecting;
    public Queue<Collision> collisionQueue = new LinkedList<>();
    int tick=0;
    int i,j;
    public Synchronization entityLock;
    int gameTick = 0;
    ArrayList<Collision> collisions = new ArrayList<Collision>();
    public Koleada(List<Entity> list){
        detecting = list;
    }
    public void run(){
        
        for(i = 0; i < detecting.size(); i++){
            // checks if the object being detected is sleeping
            if(detecting.get(i).collision_sleeping){
                // goes through the list of other objects (we need to check everything
                // here as non sleeping objects cannot cause collisions but can be collided
                // with
                for(j=0; j < detecting.size(); j++){
                    // checks if we're checking the same objects
                    if(i!=j && !detecting.get(i).handled){
                        if((detecting.get(i) instanceof Player && detecting.get(j) instanceof Projectile) 
                        ||detecting.get(j) instanceof Player && detecting.get(i) instanceof Projectile ){

                        }
                    // check if the two objects are circle models     
                        else if(detecting.get(i).model instanceof Circle && detecting.get(j).model instanceof Circle){
                            // if they are not handled handle them
                            
                            if(checkEdges((Circle)detecting.get(i).model, (Circle)detecting.get(j).model)){
                                Collision collision = new Collision(detecting.get(i), detecting.get(j));
                                if(!collision.to.handled){
                                    collisionQueue.offer(collision);
                                }

                            }
                                
                        
                        } else if(detecting.get(i).model instanceof Rectangle && detecting.get(j).model instanceof Rectangle){
                            if(checkEdges((Rectangle)detecting.get(i).model, (Rectangle)detecting.get(j).model)){
                                Collision collision = new Collision(detecting.get(i), detecting.get(j));
                                if(!collision.to.handled){
                                    collisionQueue.offer(collision);
                                }

                            }
                        }
                    }
                }
            }
        }
        entityLock.workComplete(this);
    }
    public boolean checkEdges(Circle shape1, Circle shape2){
        return shape1.points.get(0).distance(shape2.points.get(0)) <= shape1.radius + shape2.radius;
    }
    public boolean checkEdges(Rectangle shape1, Rectangle shape2){

        // TODO add this
        return false;
    }

    public class Collision{
        Entity from;
        Entity to;
        public Collision(Entity from, Entity to){
            this.from = from;
            this.to = to;
        }
    }

}
