package edu.ohiou.algorithm.spacesearch;

import java.util.ArrayList;

public abstract class State {

	private char[] stateVal;
	private int stateLen;

	// Default Constructor
	protected State() {
		this.stateLen = 0;
		this.stateVal = new char[this.stateLen];
	}
	
	// Copy Constructor
	protected State(State st) {
		this.stateLen = st.getLength();
		this.stateVal = st.getState().clone();
	}

	protected char[] getState() {
		return stateVal.clone();
	}

	protected String getStateString() {
		return new String(this.getState());
	}

	protected void setState(char[] state) {
		this.stateVal = state.clone();
		this.setLength(state.length);
	}

	protected void setState(String state) {
		this.setState(state.toCharArray());
	}

	protected void setState(State state) {
		this.setState(state.getState());
	}

	protected int getLength() {
		return stateLen;
	}

	private void setLength(int length) {
		this.stateLen = length;
	}

	// To facilitate "equals" and "list.contains" methods for State type:
	@Override
	public boolean equals(Object st) {
		if (st == this) {
			return true;
		}
		if (st instanceof State && st != null) {
			State s = (State) st;
			if (this.getStateString().equals(s.getStateString()) && this.getLength() == s.getLength()) {
				return true;
			}
		} 
		
		return false;
	}

	// To facilitate "equals" and "list.contains" methods for State type:
	@Override
	public int hashCode() {
		int hash = 42;
		hash = 9 * hash + (this.stateVal != null ? this.stateVal.hashCode() : 0);
		hash = hash + this.stateLen;
		return hash;
	}

	protected abstract State getChild(State st, String move);

	protected abstract ArrayList<State> getChildren(State state);

}
