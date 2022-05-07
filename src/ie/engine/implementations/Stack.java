package ie.engine.implementations;

import java.util.EmptyStackException;

public class Stack {
    public class Node{
        int value;
        Node next;
        public Node(int v){
            value = v;
            next = top;
        }
    }
    int index;
    Node top;
    public Stack(){
    }
    public void push(int x){
        top = new Node(x);
    }
    public int pop(){
        if(top == null){
            throw new EmptyStackException();
        }
        int temp = top.value;
        top = top.next;
        return temp;
    }
    public void display(){
        Node t = top;
        while(t != null){
            System.out.print(t.value + ", ");
            t= t.next;
        }

        System.out.println("");
    }
}
