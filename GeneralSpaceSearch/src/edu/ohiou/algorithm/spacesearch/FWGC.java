package edu.ohiou.algorithm.spacesearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

public class FWGC extends State {

	private static final ArrayList<String> unsafe = new ArrayList<String>(Arrays.asList("RLLL", "RLLR", "RRLL", "LRRR", "LRRL", "LLRR"));
	private static final ArrayList<String> moves = new ArrayList<String>(Arrays.asList("F", "FW", "FG", "FC"));

	// Default Constructor
	protected FWGC(){
		super();
		this.setState("LLLL");
	}
	
	protected FWGC(String state){
		super();
		this.setState(state);	
	}

	protected FWGC(char[] state){
		super();
		this.setState(state);
	}
	
	// Copy Constructor
	protected FWGC(State state){
		super(state);		
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
	
	private State moveFarmer(State st){
		char[] curSt = st.getState();
		curSt[0] = this.switchValue(curSt[0]);
		
		return new FWGC(curSt);
	}
	
	private State moveFarmerWolf(State st) throws InputMismatchException {
		char[] curSt = st.getState();
		if(curSt[0] == curSt[1]){
			curSt[0] = this.switchValue(curSt[0]);
			curSt[1] = this.switchValue(curSt[1]);
			return new FWGC(curSt);
		}
		return null;
	}
	
	private State moveFarmerGoat(State st) throws InputMismatchException {
		char[] curSt = st.getState();
		if(curSt[0] == curSt[2]){
			curSt[0] = this.switchValue(curSt[0]);
			curSt[2] = this.switchValue(curSt[2]);
			return new FWGC(curSt);
		}
		return null;
	}
	
	private State moveFarmerCabbage(State st) throws InputMismatchException {
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
		State st = new FWGC(state);
		try{
			if(move.length()==1){
				st = this.moveFarmer(st);
			}
			else{
				if(move.charAt(1)=='W'){
					st = this.moveFarmerWolf(st);
				}
				else if(move.charAt(1)=='G'){
					st = this.moveFarmerGoat(st);
				}
				else if(move.charAt(1)=='C'){
					st = this.moveFarmerCabbage(st);
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

	public static void main(String[] args){
		State fw = new FWGC("RLRL");
//		State fw1 = ((FWGC) fw).moveFarmer(fw);		
		System.out.println("node: "+fw.getStateString());
		System.out.print("children: ");
		for(State ch : fw.getChildren(fw)){
			System.out.print(ch.getStateString() + " ");
		}
	}

}
