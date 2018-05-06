package lse;

public class Tester {
    public static void main(String[] args) {
        String aString = "distance.";
        String bString = "equi-distant";
        String cString = "Rabbit";
        String dString = "Through";
        String eString = "we're";
        String fString = "World...";
        String gString = "World?!";
        String hString = "What,ever";

        LittleSearchEngine nEngine = new LittleSearchEngine();
        System.out.println(nEngine.getKeyword(aString));
        System.out.println(nEngine.getKeyword(bString));
        System.out.println(nEngine.getKeyword(cString));
        System.out.println(nEngine.getKeyword(dString));
        System.out.println(nEngine.getKeyword(eString));
        System.out.println(nEngine.getKeyword(fString));
        System.out.println(nEngine.getKeyword(gString));
        System.out.println(nEngine.getKeyword(hString));
    }
}
