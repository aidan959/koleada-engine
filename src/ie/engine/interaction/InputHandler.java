package ie.engine.interaction;

public class InputHandler{
    public enum inputs{
        LEFT(0),
        RIGHT(1),
        UP(2),
        DOWN(3),
        SPACE(4),
        FRONT(5),
        BACK(6);
        private final int value;
        private inputs(int  value){
            this.value = value;
        }
        public int get(){
            return value;
        }
    }
    private boolean[] inputsDown = {false, false, false, false, false};
    public InputHandler(){
        inputsDown = new boolean[inputs.values().length];
        for(int i = 0; i <= inputs.values().length - 1; i++){
            inputsDown[i] = false;
        }
    }
    public void down(inputs button){
        inputsDown[button.get()] = true;
    }
    public void up(inputs button){
        inputsDown[button.get()] = false;
    }
    public boolean get(inputs button){
        return inputsDown[button.get()];
    }
}
