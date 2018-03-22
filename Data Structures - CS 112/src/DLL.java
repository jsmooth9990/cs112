public class DLL<T>{

    DLLNode<T> front;
    int size;

    DLL(){
        front = null;
        size = 0;
    }

    public void addToFront(T data){
        DLLNode<T> node = new DLLNode<T>(data, front, null);
        if(front != null){
            front.prev = null; // make current front node point back to node
        }
        front = node;
        size += 1;
    }

    public void traverse(){
        for(DLLNode<T> ptr = front; ptr != null; ptr = ptr.next){
            if(ptr.next == null){
                System.out.print(ptr.data);
            }else{
                System.out.print(ptr.data + " <-> ");
            }
        }
        System.out.println();
    }

    public void removeFront(){
        if(front != null){
            front = front.next;
            front.prev = null;
            size--;
        }
    }

    public static void main(String[] args){
        DLL<String> pies = new DLL<String>();
        pies.addToFront("pecan");
        pies.addToFront("apple");
        pies.addToFront("pumpkin");

        pies.traverse();

        pies.removeFront();

        pies.traverse();
    }

}

