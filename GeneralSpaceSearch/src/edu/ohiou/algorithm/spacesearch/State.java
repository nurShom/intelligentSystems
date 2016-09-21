package edu.ohiou.algorithm.spacesearch;

import java.util.ArrayList;

public abstract class State {

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

	protected abstract State getChild(State st, String move);

	protected abstract ArrayList<? extends State> getChildren(State state);
	
	protected void printChildren(State st) {
		for(State child : this.getChildren(st)){
			System.out.println(child.toString());
		}
	}
	
}
