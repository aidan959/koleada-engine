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
    AudioEvent p;
    public void push(EventType eventType, int frame){
        AudioEvent newItem = new AudioEvent(eventType, frame);
        if(head == null){
            head = newItem;
            
        } else {
            p = head;
            while(p.next != null){
                p = p.next;
            }
            p.next = newItem;
        }
    }

}
