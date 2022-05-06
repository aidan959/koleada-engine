package ie.engine;

public class Main{
    private static void gameStart(String[] args){

        processing.core.PApplet.runSketch(args, new NRGQAnim());
    } 
    static String[] gameArgs = {"Main"};
    public static void main(String[] args){
        gameStart(gameArgs);
    }
}