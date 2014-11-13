package graphColoring;

public class NQueensMain {

	public static void main(String[] args) {

		State initState = new NQueensState(8);
	
		Backtracker bt = new Backtracker();
		bt.backtrack(initState);

	}

}
