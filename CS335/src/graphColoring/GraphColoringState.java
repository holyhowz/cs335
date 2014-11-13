package graphColoring;

import java.util.List;

public class GraphColoringState implements State, Comparable{
	private boolean[][] graph;
	private List<String> colors;
	private final int[] colorCost = new int[] { 2, 3, 5, 2 };
	private String[] gcolors;
	private int currentColor;
	private int firstUncolored;
	private boolean hasMoreChildren;
	private boolean allNodesColored;

	public GraphColoringState(boolean[][] graph, List<String> colors) {
		this.graph = graph;
		this.colors = colors;
		this.gcolors = new String[] { "", "", "", "", "", "" };
		this.firstUncolored = 0;
		this.currentColor = 0;
		hasMoreChildren = true;
		allNodesColored = false;
		return;
	}

	@Override
	public boolean hasMoreChildren() {
		return hasMoreChildren && !allNodesColored;
	}

	@Override
	public State nextChild() {
		// System.out.println("currentColor: "+ colors.get(currentColor) +" firstUncolored " + this.firstUncolored);
		// previous line for debugging
		GraphColoringState child = childSetup();
		child.gcolors[firstUncolored] = colors.get(currentColor++);
		if (this.currentColor == colors.size()) {
			this.currentColor = 0;
			this.hasMoreChildren = false;
		}
		if(firstUncolored == gcolors.length-1){
			this.hasMoreChildren = false;
		}
		return child;
	}

	@Override
	public boolean isFeasible() {
		boolean feasibility = true;
		int nodeNum = 0;
		int nodeNum2 = 0;
		for (boolean[] node : graph) {
			for (boolean adjNode : node) {
				if (adjNode && (gcolors[nodeNum].equals(gcolors[nodeNum2])) && !gcolors[nodeNum].equals("")) {
					feasibility = false;
				}
				nodeNum2++;
			}
			nodeNum++;
			nodeNum2 = 0;
		}
		return feasibility;
	}

	@Override
	public boolean isSolved() {
		return allNodesColored && isFeasible();
	}

	@Override
	public int getBound() {
		int bound = 0;
		for (String node : gcolors) {
			if (node.equals("")) {
				bound += 2;
			} else {
				bound += colorCost[colors.indexOf(node)];
			}
		}
		return bound;
	}

	private GraphColoringState childSetup() {
		GraphColoringState theClone = new GraphColoringState(graph, colors);
		theClone.gcolors = this.gcolors.clone();
		theClone.firstUncolored = this.firstUncolored + 1;
		if(theClone.firstUncolored == gcolors.length){
			theClone.hasMoreChildren = false;
		}
		theClone.currentColor = 0;
		return theClone;
	}

	@Override
	public String toString() {
		String reddo = new String();
		reddo = "";
		for (String addMe : gcolors) {
			reddo += addMe + ", ";
		}
		return reddo;
	}

	@Override
	public int compareTo(Object arg0) {
		int thisBound = this.getBound();
		int otherBound = ((GraphColoringState) arg0).getBound();
		int reddo = 0;
		
		if(otherBound > thisBound){
			reddo = -1;
		}else if(thisBound > otherBound){
			reddo = 1;
		}
		
		return reddo;
	}
}