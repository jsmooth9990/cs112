public class TestLinkedLIst {
    public static void main(String[] args){
        LinkedList<String> states = new LinkedList<String>();
        states.addToFront("NJ");
        states.addToFront("NY");
        states.addToFront("CA");
        states.traverse();

        LinkedList<Integer> numbers = new LinkedList<Integer>();
        numbers.addToFront(5);
        numbers.addToFront(3);
        numbers.addToFront(1);
        numbers.traverse();

        LinkedList<Float> fnumbers = new LinkedList<Float>();
        fnumbers.addToFront(3.14159f);
        Float value = fnumbers.removeFront();
        System.out.println("value = " + value);
    }
}
