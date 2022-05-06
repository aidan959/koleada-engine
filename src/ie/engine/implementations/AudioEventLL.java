package ie.engine.implementations;

class EndOfLinkedListRE extends RuntimeException{

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
        public AudioEvent next;
        EventType eventType;
        public int frame;
        public float volume;
        public AudioEvent(EventType eT, int frame, float volume){
            eventType = eT;
            this.frame = frame;
            this.volume = volume;
        }
    }
    public boolean end;
    AudioEvent head; 
    public AudioEvent pop(){
        if(head == null ){
            end = true;
            return blank;
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
    public AudioEvent getHead(){
        return head;
    }

}
