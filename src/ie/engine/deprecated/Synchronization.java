package ie.engine.implementations;

import java.util.HashMap;
import java.util.List;


public class Synchronization {
    private int counter;
    private int limit;
    private HashMap<Object, Boolean> objectsToSync;
    public Synchronization( List<Object> engineSynchronizeRequired){
        this.limit = engineSynchronizeRequired.size();
        this.counter = 0;
        this.objectsToSync = new HashMap<>();
        for (Object object : engineSynchronizeRequired) {
            objectsToSync.put(object, false);   
        }
    }
    public void newFrame(){
        counter = 0;
        for(Object obj : objectsToSync.keySet()){
            objectsToSync.put(obj, false);
        }
    }
    public void workComplete(Object obj){
        objectsToSync.put(obj, true);
        counter++;
    }
    public boolean checkFree(Object obj){
        return objectsToSync.get(obj);
    }
    public boolean isFree(){
        return (counter == limit);
    }

}
