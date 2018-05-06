package apps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import structures.Graph;
import apps.MST;
import apps.PartialTree;
import apps.PartialTreeList;
import structures.Vertex;

public class MSTDriver {

    public static void main(String[] args) throws IOException {
        Graph test1 = new Graph("graph2.txt");
        PartialTreeList list1 = MST.initialize(test1);

       //Vertex v1 = list1.remove().getArcs().getMin().v2;
       //list1.removeTreeContaining(v1);
        //list1.remove();
        while(list1.size() != 0){
            System.out.println(list1.remove());
        }
        System.out.println();
        System.out.println();

        System.out.print(MST.execute(list1));

    }
}