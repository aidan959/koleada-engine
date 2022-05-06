package ie.engine.implementations;
// Sorted linked list with a sentinel node
// Skeleton code

public class SortedLL
{
    // internal data structure and
    // constructor code to be added here
    
    public SortedLL(){
        z = new Node(0);
        z.next = z;
        head = z;
    }
    Node head, z;
    class Node{
        int data;
        Node next = null;
        public Node(int x){
            data = x;
        }
    }
    // this is the crucial method
    public void insert(int x)
    {
        Node prev = null;
        Node curr = head;
        Node t = new Node(x);
        
        if(curr == null){
            head = t;
            return;
        }
        
        while(t.data > curr.data && curr.next != null){
            prev = curr;
            curr = curr.next;
        }
        if(curr == head){
            t.next = curr;
            head = curr;    
        } else{
            prev.next = t;
            t.next = curr;
        }
        
             
    }    
    
    
    public boolean remove(int x) {
        Node curr = head;
        while(curr.data != x && curr.next != null){
            curr = curr.next;
            if(curr.data == x){
                return true;
            }
        }
        return false;
    }
    
    public boolean isEmpty() {
        return head == null;
    }
    
    public void display()
    {
        Node t = head;
        System.out.print("\nHead -> ");
        while( t != null) {
            System.out.print(t.data + " -> ");
            t = t.next;
        }
        System.out.println("Z\n");
    }
    public void test(){
    }
    public static void main(String args[])   
    {
        SortedLL list = new SortedLL();

        list.insert(10);
        list.insert(15);

        list.insert(20);

        /*
           for(i = 1; i <= 5; ++i)  {
           x =  (Math.random()*100.0);
           r = (int) x; 
           System.out.println("Inserting " + r);
           list.insert(r);
           list.display();            
        }
        */
        
        /*
        while(!list.isEmpty())  {
            System.out.println("\nInput a value to remove: ");
            Scanner in = new Scanner(System.in);
            r = in.nextInt();
            if(list.remove(r)) {
                System.out.println("\nSuccessfully removed: " + r);
            list.display(); }
            else System.out.println("\nValue not in list");                        
        }
        */
    }
}