public class IntLL {
    // main method
    public static void main(String[] args){
        IntNode L = null;
        addToFront(L, 5);
        traverse(L); // print
        addToFront(L,2);
        addToBack(L,6);
        traverse(L); // print
        System.out.println(search(L,2));
    }

    public static IntNode addToFront(IntNode front, int data){
        IntNode node = new IntNode(data, front);
        return node;
    }

    public static void traverse(IntNode front){
        IntNode ptr = front;
        while(ptr != null){
            System.out.println(ptr.data + "->");
            ptr = ptr.next;
        }
        System.out.println("\\"); // prints \, where \ = null
    }

    public static IntNode removeFront(IntNode front){
        if(front == null){
            return null;
        }else {
            return front.next;
        }
    }

    public static boolean search(IntNode front, int target){
        for(IntNode ptr = front; ptr != null; ptr = ptr.next){
            if(ptr.data == target){
                return true;
            }
        }
        return false;
    }

    public static IntNode addToBack(IntNode front, int data){
        if(front == null){
            return addToFront(front, data);
        }else {
            IntNode ptr = front;
            while(ptr.next != null){
                ptr = ptr.next;
            }// ptr points to the last item in the Linked List
            IntNode node = new IntNode(data, null);
            ptr.next = node;
            return front;
        }
    }
}
