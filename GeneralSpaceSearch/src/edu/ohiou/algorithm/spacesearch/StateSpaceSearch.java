package edu.ohiou.algorithm.spacesearch;

import java.util.ArrayDeque;
import java.util.ArrayList;
//import java.util.concurrent.Callable;

public class StateSpaceSearch {
	
	public enum StateOrdering{DFS, BFS}; //LIFO, FIFO
	
	private ArrayDeque<State> open, closed;
	private StateOrdering order = null;

	public StateSpaceSearch() {
		this.open = new ArrayDeque<State>();
		this.closed = new ArrayDeque<State>();
	}
	
	private void clearList() {
		this.open = new ArrayDeque<State>();
		this.closed = new ArrayDeque<State>();
	}

	//Remove child from front or back of the list, depending on state ordering
	private State getNextChildInOrder(){
		if(this.order == StateOrdering.BFS){
			// Queue implementation for BFS
			return this.open.pollFirst();
		}
		else if(this.order == StateOrdering.DFS){
			// Stack implementation for DFS
			return this.open.pollLast();
		}
		else{
			// Default case: DFS
			return this.open.pollLast();
		}
	}
	
	// public boolean spaceSearch(State initState, State goalState, Callable<String> transitionState) {
	@SuppressWarnings("unchecked")
	public boolean spaceSearch(State initState, State goalState) {

		State curState = initState;
		if (curState.equals(goalState)) {
			//If goal reached, print goal state
			System.out.println("Goal reached: " + curState.toString());
			return true;
		} else if(this.closed.contains(curState)) {
			//This node already visited, try the next node
			
//			System.out.println("Visited before: " + curState.toString());
			
		} else{
			try{
				//Add visited current node to Closed queue
				this.closed.addLast(curState);
				
				// Add all child nodes to the Open queue
				ArrayList<State> children = (ArrayList<State>) curState.getChildren(curState); //This is the transition function, different implementation for different types of state representation
				if(children.isEmpty()){ 
					//If children list empty, it's a leaf
					System.out.print("Leaf ");
				}
				else{
					for (State child : children) {
						this.open.addLast(child);
					}
				}
				System.out.println("Node reached: " + curState.toString());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	
		State nextChild = this.getNextChildInOrder();
		return this.spaceSearch(nextChild, goalState);
	}
	
	public void execute(State initState, State goalState, StateOrdering order){
		this.order = order; //sets the order of search
		this.clearList(); //clears the open and closed lists.
		this.spaceSearch(initState, goalState);

//		Callable c = new Callable<Integer>() {
//			   public Integer call() {
//			        return init.getChildren(State);
//			   }
//		};
//		this.spaceSearch(init, goal, c);
	}
	
	public static void main(String args[]){
		StateSpaceSearch sps = new StateSpaceSearch();
		
		System.out.println("WATER-JUG Problem:");
		State init = new WaterJug(0, 0);
		State goal = new WaterJug(2, 0);
		System.out.println("Attempting DFS:");
		sps.execute(init, goal, StateOrdering.DFS);
		System.out.println("\nAttempting BFS:");
		sps.execute(init, goal, StateOrdering.BFS);
		
		System.out.println("\nFWGC Problem:");
		init = new FWGC("LLLL");
		goal = new FWGC("RRRR");
		System.out.println("Attempting DFS:");
		sps.execute(init, goal, StateOrdering.DFS);
		System.out.println("\nAttempting BFS:");
		sps.execute(init, goal, StateOrdering.BFS);
				
		System.out.println("\nTSP Problem:");
		init = new TSP("Athens", "Cincinnati");
		goal = ((TSP) init).getGoalState();
		System.out.println("Attempting DFS:");
		sps.execute(init, goal, StateOrdering.DFS);
		System.out.println("\nAttempting BFS:");
		sps.execute(init, goal, StateOrdering.BFS);

	}

}
