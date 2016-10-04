package edu.ohiou.algorithm.spacesearch;

import java.util.ArrayList;

public abstract class State {
	
	public static enum ProblemClass{ConstraintSearch, Optimization}; //LIFO, FIFO 

	// Default Constructor
	protected State() {
	}
	
	// To facilitate "equals" and "list.contains" methods for State type:
	@Override
	public abstract boolean equals(Object st) ;

	// To facilitate "equals" and "list.contains" methods for State type:
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract String toString();

	protected abstract State getChild(State state, String move);

	protected abstract ArrayList<? extends State> getChildren(State state);
	
	protected abstract boolean isGoalState(State state);
	
	protected abstract ProblemClass getProblemClass();
	
	protected abstract void clearState();
	
	protected void printChildren(State st) {
		for(State child : this.getChildren(st)){
			System.out.println(child.toString());
		}
	}
	
}
