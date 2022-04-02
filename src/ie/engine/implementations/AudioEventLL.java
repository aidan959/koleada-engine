package ie.engine.implementations;

class EndOfLinkedList extends RuntimeException{

}
public class AudioEventLL {
    public enum EventType{
        KICK, SNARE
    };
    public AudioEventLL(){
        blank = new AudioEvent(EventType.KICK, 0, 0);
    }
    public AudioEvent blank = new AudioEvent(EventType.KICK, 0, 0.0f);
    public class AudioEvent{
        AudioEvent next;
        EventType eventType;
        public int frame;
        public float volume;
        public AudioEvent(EventType eT, int frame, float volume){
            eventType = eT;
            this.frame = frame;
            this.volume = volume;
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
    public AudioEvent peek(){
        return head;
    }
    AudioEvent p;
    public void push(EventType eventType, int frame, float volume){
        AudioEvent newItem = new AudioEvent(eventType, frame, volume);
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
