package ie.engine.implementations;

class EndOfLinkedList extends RuntimeException{

}
public class AudioEventLL {
    public enum EventType{
        KICK, SNARE
    };
    public class AudioEvent{
        AudioEvent next;
        EventType eventType;
        int frame;
        AudioEvent(EventType eT, int frame){
            eventType = eT;
            this.frame = frame;
        }
    }
    AudioEvent head; 
    public AudioEvent pop(){
        if(head == null ){
            throw new EndOfLinkedList();
        }
        AudioEvent tmp = head;  
        head = head.next;
        return tmp;   
    }
    public void push(EventType eventType, int frame){
        AudioEvent newItem = new AudioEvent(eventType, frame);
        if(head == null){
            head = newItem;
            return;
        } else {
            head.next = newItem;
            return;
        }
    }

}
