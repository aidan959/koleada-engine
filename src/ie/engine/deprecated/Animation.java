package ie.engine.maths;


public class Animation {
    int size, counter;
    KeyFrame[] keyFrames;
    KeyFrame currentFrame;
    Coordinate currentPosition;
    public Animation(int size, KeyFrame[] frames){
        this.size = size;
        counter = 0;
        keyFrames = frames;
        currentFrame = frames[0];
    }
    public void run(int songFrame){
        if(animationFinished()){
            // animation finished
            // tell program to destroy?
            return;
        }
        if(songFrame >= keyFrames[counter+1].frame){
            currentFrame = keyFrames[++counter];
        }
        for(int i = 0; i >= size-2; i++){
            if(keyFrames[i].frame <= songFrame && songFrame <= keyFrames[i+1].frame ){
                int frameDistance = keyFrames[i+1].frame - keyFrames[i].frame  ;
                int songDistanceFromFrame = songFrame - keyFrames[i+1].frame;
                float percentageComplete = songDistanceFromFrame / frameDistance; 
                currentPosition = keyFrames[i].coordinate.map(keyFrames[i+1].coordinate, percentageComplete);
                System.out.println(percentageComplete);
            }
        }
    }
    public boolean animationFinished(){
        return counter + 1 >= size ;
    }
}
