package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {

		/* COMPLETE THIS METHOD */
		PartialTreeList list = new PartialTreeList();
		int i = 0;
		while(i < graph.vertices.length){ // vertices
			PartialTree v = new PartialTree(graph.vertices[i]);
			//MinHeap<PartialTree.Arc> hp = new MinHeap<>();
			Vertex ptr = graph.vertices[i];

			while(ptr.neighbors != null){
				PartialTree.Arc arc = new PartialTree.Arc(graph.vertices[i], ptr.neighbors.vertex, ptr.neighbors.weight);
				//hp.insert(arc);
				v.getArcs().insert(arc);
				ptr.neighbors = ptr.neighbors.next;
			}

			list.append(v);

			i++;
		}

		return list;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {

		ArrayList<PartialTree.Arc> result = new ArrayList<PartialTree.Arc>();
		while(ptlist.size() > 1){
			PartialTree PTX = ptlist.remove();
			PartialTree.Arc arc= PTX.getArcs().deleteMin();
			Vertex v2 = arc.v2;

				while(!PTX.getArcs().isEmpty()){
				if (PTX.getRoot().equals(v2.getRoot())){
					arc = PTX.getArcs().deleteMin();
					v2 = arc.v2;
				} else {
					break;
				}
			}

			PartialTree PTY = ptlist.removeTreeContaining(v2);
			PTX.merge(PTY);
			ptlist.append(PTX);
			result.add(arc);
		}

		return result;
	}
}
