package ie.engine.interaction;
import ie.engine.maths.Coordinate;
import processing.core.PApplet;

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
    public PApplet pa;
    int rectColor;
    int rectHighlight;
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
    public Menu(PApplet pa){
        this.menuItems = new ArrayList<>();
        this.pa = pa;
        rectColor = pa.color(0);
        rectHighlight = pa.color(51);
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
    public void draw(){
        for(Menu.MenuObject menuObject : returnMenuObjects()){
            menuObject.selected = false;
            if(mouseOver(menuObject.position, (int)menuObject.size.x, (int)menuObject.size.y)){
                pa.fill(rectHighlight);
                if(pa.mousePressed){
                    menuObject.clicked();
                }
            } else{
                pa.fill(rectColor);
            }
            pa.stroke(255);
            pa.rect(menuObject.position.x, menuObject.position.y, menuObject.size.x, menuObject.size.y);
            pa.fill(0,255,255);
            pa.textSize(30);
            pa.text(menuObject.menuText,  menuObject.textStart.x, menuObject.textStart.y , menuObject.textEnd.x, menuObject.textEnd.y) ;
            
            
        }
        setOutput();
    }
    public boolean mouseOver(Coordinate position, int width, int height){
        return (pa.mouseX >= position.x && pa.mouseX <= position.x + width && pa.mouseY >= position.y && pa.mouseY <= position.y + height);
    }
}
