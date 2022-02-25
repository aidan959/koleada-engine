package ie.engine.loading;
import ie.engine.maths.Coordinate;
public class Engine
{
    Level currentLevel = new Level("map1.map");
    public void run(){
        this.loadLevel();
    }

    public void loadLevel(){
        this.loadLevel();
    }
}
class Level
{
    public Level(String MAP_NAME){
        load(MAP_NAME);
    }
    Coordinate spawnPoint;
    // TODO implement how this shit works
    public void load(String MAP_NAME){
        /**
         * 
         */
    }
}
class Render 
{
    public void render(){
        // hopefully handles rendering shit
    }
}