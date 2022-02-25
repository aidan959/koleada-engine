package ie.engine.interaction;
import ie.engine.maths.Coordinate;
import java.util.ArrayList;
import java.util.List;

public class Menu
{
   
    private ArrayList<MenuObject> menuItems;
    public enum MenuChoice{
        UNSELECTED,
        START,
        CREDITS,
        QUIT
    }
    public MenuChoice output;
    public void setOutput(){
        boolean objFound = false;
        for(MenuObject obj : menuItems){
            if(obj.selected){
                output = obj.option;
                objFound = true;
                break;
            }
        }
        if (!objFound){
            output = MenuChoice.UNSELECTED;
        }
    }
    public Menu(){
        this.menuItems = new ArrayList<>();
    }
    public MenuObject createMenuObject(MenuChoice option, String menuText, Coordinate position, Coordinate size){
        MenuObject menuObj = new Menu.MenuObject(option, menuText, position, new Coordinate(position.x+size.x, position.y+size.y), size );
        menuItems.add(menuObj);
        return menuObj;
    }
    public class MenuObject{
        public MenuChoice option;
        public String menuText;
        public Coordinate position;
        public Coordinate bottomRightPosition;
        public Coordinate size;
        public Runnable functionToExec;
        public Boolean selected = false;
        public Coordinate textStart;
        public Coordinate textEnd;
        public MenuObject(MenuChoice option, String menuText, Coordinate position, Coordinate bottomPosition, Coordinate size){
            this.menuText = menuText;
            this.position = position;
            this.bottomRightPosition = bottomPosition;
            this.option = option;
            this.size = size;
            textStart = new Coordinate(this.position.x + (this.size.x)/16, this.position.y + (this.size.y)/8); 
            textEnd = new Coordinate(this.bottomRightPosition.x -(this.size.x)/16, this.bottomRightPosition.y - (this.size.y) /8  );
            /**
             * System.out.println("Position 1: " + position);
             * System.out.println("Position 2: " + bottomRightPosition);
             * System.out.println("Text start: " + textStart);
             * System.out.println("Text end: " +  textEnd);
             * System.out.println("Size: " + size);
            **/

        }
        public void menuRun(Runnable toRun){
            toRun.run();
        }
        public void clicked(){
            selected = true;
        }
        
    
    }
    // returns the object - returned when displaying
    public List<MenuObject> returnMenuObjects(){
        return this.menuItems;
    }
}
