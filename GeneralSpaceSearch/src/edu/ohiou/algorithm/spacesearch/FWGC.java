package edu.ohiou.algorithm.spacesearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

public class FWGC extends State {

	private char[] stateVal;
	private int stateLen;
	
	private static final ArrayList<String> unsafe = new ArrayList<String>(Arrays.asList("RLLL", "RLLR", "RRLL", "LRRR", "LRRL", "LLRR"));
	private static final ArrayList<String> moves = new ArrayList<String>(Arrays.asList("F", "FW", "FG", "FC"));

	// Default Constructor
	protected FWGC(){
		this.setState("LLLL");
	}
	
	// Copy Constructor
	protected FWGC(State state){
		this.setState((FWGC)state);
	}
	
	protected FWGC(String state){
		this.setState(state);	
	}
	
	protected FWGC(char[] state){
		this.setState(state);
	}
	
	protected char[] getState() {
		return stateVal.clone();
	}

	protected String getStateString() {
		return new String(this.getState());
	}

	protected int getLength() {
		return this.getState().length;
	}
	
	protected void setState(char[] state) {
		this.stateVal = state.clone();
	}

	protected void setState(String state) {
		this.setState(state.toCharArray());
	}

	protected void setState(FWGC state) {
		this.setState(state.getState());
	}
		
	private char switchValue(char val){
		switch(val){
		case 'R':
			return 'L';
		case 'L':
			return 'R';				
		}
		throw new InputMismatchException("Invalid value:" + val);
	}
	
	private State moveFarmer(FWGC st){
		char[] curSt = st.getState();
		curSt[0] = this.switchValue(curSt[0]);
		
		return new FWGC(curSt);
	}
	
	private State moveFarmerWolf(FWGC st) throws InputMismatchException {
		char[] curSt = st.getState();
		if(curSt[0] == curSt[1]){
			curSt[0] = this.switchValue(curSt[0]);
			curSt[1] = this.switchValue(curSt[1]);
			return new FWGC(curSt);
		}
		return null;
	}
	
	private State moveFarmerGoat(FWGC st) throws InputMismatchException {
		char[] curSt = st.getState();
		if(curSt[0] == curSt[2]){
			curSt[0] = this.switchValue(curSt[0]);
			curSt[2] = this.switchValue(curSt[2]);
			return new FWGC(curSt);
		}
		return null;
	}
	
	private State moveFarmerCabbage(FWGC st) throws InputMismatchException {
		char[] curSt = st.getState();
		if(curSt[0] == curSt[3]){
			curSt[0] = this.switchValue(curSt[0]);
			curSt[3] = this.switchValue(curSt[3]);
			return new FWGC(curSt);
		}
		return null;
	}
	
	@Override
	protected State getChild(State state, String move){
		FWGC st = new FWGC(state);
		try{
			if(move.length()==1){
				st = (FWGC) this.moveFarmer(st);
			}
			else{
				if(move.charAt(1)=='W'){
					st = (FWGC) this.moveFarmerWolf(st);
				}
				else if(move.charAt(1)=='G'){
					st = (FWGC) this.moveFarmerGoat(st);
				}
				else if(move.charAt(1)=='C'){
					st = (FWGC) this.moveFarmerCabbage(st);
				}
			}
		}catch(InputMismatchException ime){
			ime.printStackTrace();
		}
		
		return st;
	}
	
	@Override
	protected ArrayList<State> getChildren(State state) {
		ArrayList<State> states = new ArrayList<State>();
		
		for(String move: FWGC.moves){
			FWGC child = (FWGC) this.getChild(state, move);
			if((child!=null) && (!FWGC.unsafe.contains(child.getStateString()))){
				states.add(child);
			}
		}
		
		return states;
	}
	
	// To facilitate "equals" and "list.contains" methods for State type:
	@Override
	public boolean equals(Object st) {
		if (st == this) {
			return true;
		}
		if (st instanceof FWGC && st != null) {
			FWGC s = (FWGC) st;
			if (this.getStateString().equals(s.getStateString())) {
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
	
	@Override
	public String toString() {
		return this.getStateString();
	}
	
	public static void main(String[] args){
		State fw = new FWGC("RLRL");
//		State fw1 = ((FWGC) fw).moveFarmer(fw);		
		System.out.println("node: "+fw.toString());
		System.out.print("children: ");
		for(State ch : fw.getChildren(fw)){
			System.out.print(ch.toString() + " ");
		}
	}


}
