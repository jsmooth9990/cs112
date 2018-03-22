import java.util.NoSuchElementException;

/**
 * Generic Linked List
 */
public class LinkedList <T>{
    Node<T> front;
    int size;

    // constructor
    LinkedList(){
        front = null;
        size = 0;
    }

    public void addToFront(T data){
        front = new Node<T>(data, front);
        size +=1;
    }

    public void traverse(){
        for(Node<T> ptr = front; ptr!= null; ptr = ptr.next){
            System.out.print(ptr.data + "->");
        }
        System.out.println("\\");
    }

    public T removeFront(){
        if(front == null){
            // list is empty
            throw new NoSuchElementException("You Suck and the list is empty");
        }
        T frontData = front.data;
        front = front.next;
        size -=1;
        return frontData;
    }

}
