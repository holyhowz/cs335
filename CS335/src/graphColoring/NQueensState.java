package graphColoring;

public class NQueensState implements State {
	
	private int n;
	private int[] board;
	private int nextCol;
	private int nextRow;
	
	// Construct the inital state
	public NQueensState(int n) {
		this.n = n;
		this.board = new int[n];
		for (int i=0; i<n; i++) {
			this.board[i] = -1;
		}
		this.nextCol = 0;
		this.nextRow = 0;
	}
	
	public NQueensState(NQueensState s) {
		this.n = s.n;
		this.nextCol = s.nextCol;
		this.nextRow = s.nextRow;
		
		// Make a deep copy
		this.board = new int[n];
		for (int i=0; i<n; i++) {
			this.board[i] = s.board[i];
		}
		
	}
	
	public String toString() {
		String result = "[";
		for (int i=0; i<n; i++) {
			result += " " + this.board[i];
		}
		result += "]\n";
		return result;
	}

	@Override
	public boolean hasMoreChildren() {
		return (nextRow<n);
	}

	@Override
	public State nextChild() {
		// Will only be called if I have more children
		
		// Make a copy of myself
		NQueensState child = new NQueensState(this);
		
		// Modify the child
		child.board[nextCol] = nextRow;		
		
		//System.out.println("-->" + child);
		
		// Set child up to produce its next Child
		child.nextCol++;
		child.nextRow = 0;
		
		// Set myself up to produce my next Child
		this.nextRow++;
		
		
		return child;
	}

	@Override
	public boolean isFeasible() {
		// Check to see if the placement is valid
		
		int lastPlaced = nextCol - 1;
		
		boolean isValid = true;
		for (int i=0; i<lastPlaced; i++) {
		
			// Check row
			if (board[i] == board[lastPlaced]) {
				isValid = false;
			}
			
			// Check diag
			if ((lastPlaced - i) == Math.abs(board[i] - board[lastPlaced])) {
				isValid = false;
			}
		}
		return isValid;
	}

	
	@Override
	public boolean isSolved() {
		// Assumes that state is feasible
		return (nextCol == n);
	}

	@Override
	public int getBound() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
