package ie.engine.testing;

public class DebugObject <T extends Object>{
    private String name;
    private T value;
    private String measurement;
    public DebugObject(String name, T value, String measure){
        this.name = name;
        this.value = value;
        this.measurement = measure;
    }
    public String toString(){
        return name + ": " + value.toString() + " " + measurement;
    }
    public void updateValue(T val){
        value = val;
    } 
}
