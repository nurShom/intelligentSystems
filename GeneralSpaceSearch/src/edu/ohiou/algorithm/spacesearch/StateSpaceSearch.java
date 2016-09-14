package edu.ohiou.algorithm.spacesearch;

import java.util.ArrayDeque;
import java.util.ArrayList;
//import java.util.concurrent.Callable;

public class StateSpaceSearch {
	
	public enum StateOrdering{DFS, BFS}; //FIFO, LIFO
	
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
	public boolean spaceSearch(State initState, State goalState) {

		State curState = initState;
		if (curState.equals(goalState)) {
			//If goal reached, print goal state
			System.out.println("Goal reached: " + curState.getStateString());
			return true;
		} else if(this.closed.contains(curState)) {
			//This node already visited, try the next node
			//System.out.println("Visited before: " + curState.getStateString());
			
		} else{
			try{
				//Add visited current node to Closed queue
				this.closed.addLast(curState);
				
				// Add all child nodes to the Open queue
				ArrayList<State> children = curState.getChildren(curState);
				if(children.isEmpty()){ 
					//If children list empty, it's a leaf
					System.out.print("Leaf ");
				}
				else{
					for (State child : children) {
						this.open.addLast(child);
					}
				}
				System.out.println("Node reached: " + curState.getStateString());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	
		State nextChild = this.getNextChildInOrder();
		return this.spaceSearch(nextChild, goalState);
	}
	
	public void execute(State initState, State goalState, StateOrdering order){
		this.order = order;
		this.clearList();
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
		State init = new FWGC("LLLL");
		State goal = new FWGC("RRRR");
		
		System.out.println("Attempting DFS:");
		sps.execute(init, goal, StateOrdering.DFS);
		
		System.out.println("\nAttempting BFS:");
		sps.execute(init, goal, StateOrdering.BFS);
				
//		State fw = new FWGC("LLLL");	
//		System.out.println("node: "+fw.getStateString());
//		System.out.print("children: ");
//		for(State ch : fw.getChildren(fw)){
//			System.out.print(ch.getStateString() + " ");
//		}
		
	}

}
