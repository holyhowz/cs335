package graphColoring;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Backtracker {
	
	// Takes in root state, returns solved state (if any)
	// Assumes s is feasible
	// No bound pruning and depth first search on this recursive version
	public State backtrack(State s) {
		
		System.out.println(s);
		
		State result = null;
		
		// Base case
		if (s.isSolved()) {
			System.out.println("Solved");
			result = s;
		} else {    // Recursive case
			
			// Bail out when we find a single solution
			while (s.hasMoreChildren() && (result == null)) {
				State child = s.nextChild();
				
				//System.out.println("-->" + child);
				
				if (child.isFeasible()) {
					result = backtrack(child);
				}
			}
		}
		
		return result;
	}
	
	
	// Iterative version that includes bound pruning
	public State backtrackerIterative(State s) {
		// This is used to keep track of the number of states for which
        //   we need to examine 
		int numberOfStatesExpanded = 0;
 
        State bestSolution = null;
 
        // Initialize the statesToProcess data structure
        //List<State> statesToProcess = new LinkedList<State>();  // Breadth-first, Depth-first
        PriorityQueue<State> statesToProcess = new PriorityQueue<State>();  // Best-first
        statesToProcess.add(s);
 
        // While there are still states to process
        while ( !statesToProcess.isEmpty() ) {
        	// Remove the next state from the data structure
        	// State currentState = statesToProcess.remove(0);  // Breadth-first, Depth-first
        	State currentState = statesToProcess.poll();  // Best-first
        	
            numberOfStatesExpanded++;
               
//            System.out.println(currentState);
                       
            // Check to see if the state is solved
            if (currentState.isSolved()) {
 
            	// Replace the best solution if the current solution is better
            	if (bestSolution == null || currentState.getBound() < bestSolution.getBound()) {
            		bestSolution = currentState;
            	}
            	
            } else { // The state is not solved
                       
            	// Check to see if we need to continue expanding the state
            	// This is where the bound pruning occurs
            	// Recall that we cannot prune until we have a solution to prune against
            	if ( bestSolution == null || (currentState.getBound() < bestSolution.getBound()) ) {
            		// Expand the state by producing all its children
            		while (currentState.hasMoreChildren()) {
            			State childState = currentState.nextChild();
            			
            			// Add each child that is feasible to the data structure
            			if (childState.isFeasible()) {
            				statesToProcess.add(childState);  // Breadth-first, Best-first
            				//statesToProcess.add(0, childState);  // Depth-first
            			}
            		}
            	} else {
            		// For Best-first only
            		statesToProcess.clear();            		
            	}
            }
        }
 
        System.out.println(numberOfStatesExpanded);
        return bestSolution;
	}
}
