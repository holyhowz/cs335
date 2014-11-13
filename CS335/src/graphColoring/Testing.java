package graphColoring;

import java.util.ArrayList;
import java.util.List;

public class Testing {
	public static void main(String[] args) {

		Backtracker bt = new Backtracker();
		// Define the graph from the ppt
		boolean[][] graph = { { false, true, false, false, false, true },
				{ true, false, true, false, false, true },
				{ false, true, false, true, true, false },
				{ false, false, true, false, true, false },
				{ false, false, true, true, false, true },
				{ true, true, false, false, true, false } };
		// Define the colors used in the ppt
		List<String> colors = new ArrayList<String>();
		colors.add("Red");
		colors.add("Green");
		colors.add("Blue");
		colors.add("Yellow");

		List<State> tester = new ArrayList();
		tester.add(new GraphColoringState(graph,colors));
		while(!tester.isEmpty()){
			State s = tester.get(0);
			tester.remove(0);
			System.out.println(s + "Bound: "+ s.getBound());
			while(s.hasMoreChildren()){
				tester.add(s.nextChild());
			}
		}
	}

}
