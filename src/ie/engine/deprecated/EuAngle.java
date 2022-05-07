package ie.engine.maths;

public class EuAngle {
    public float p;
    public float y;
    public float r;
    public EuAngle(){
        p = y = r = 0;
    }
    public EuAngle(float p,float y,float r){
        this.p = p;
        this.y = y;
        this.r = r;

    }
    Coordinate toCoord(){
        return new Coordinate((float)(Math.cos(y)*Math.cos(p)), (float)(Math.sin(p)), (float)(Math.sin(y)* Math.cos(p)));
    }
    public void normalize(){
        if(p > 1)
            p = 1;
        else if(p < -1)
            p = -1;
    }
}
