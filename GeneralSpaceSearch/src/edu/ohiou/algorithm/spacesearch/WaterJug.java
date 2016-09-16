package edu.ohiou.algorithm.spacesearch;

import java.util.ArrayList;
import java.util.Arrays;

public class WaterJug extends State {

	//j4: 0, 1, 2, 3, 4
	//j3: 0, 1, 2, 3 
	private int j4, j3;
	/**
	 * "F4"    : Fill J4
	 * "F3"    : Fill J3
	 * "E4"    : Empty J4
	 * "E3"    : Empty J3
	 * "P43A"  : Pour J4 into J3 All its content
	 * "P34A"  : Pour J3 into J4 All its content
	 * "P43C"  : Pour J4 into J3 up to Capacity
	 * "P34C"  : Pour J3 into J4 up to Capacity
	 */
	private static final ArrayList<String> moves = new ArrayList<String>(
			Arrays.asList("F4", "F3", "E4", "E3", "P43A", "P34A", "P43C", "P34C"));
	
	//default constructor
	public WaterJug() {
		this.setJ4(0);
		this.setJ3(0);
	}
	
	//copy constructor
	public WaterJug(State st) {
		this.setJ4( ((WaterJug) st).getJ4() );
		this.setJ3( ((WaterJug) st).getJ3() );
	}

	public WaterJug(int j4, int j3) {
		this.setJ4(j4);
		this.setJ3(j3);
	}
	
	public int getJ3() {
		return j3;
	}

	public void setJ3(int j3) {
		this.j3 = j3;
	}

	public int getJ4() {
		return j4;
	}

	public void setJ4(int j4) {
		this.j4 = j4;
	}
	
	private boolean isValid(WaterJug wj){
		if(wj.getJ3()>=0 && wj.getJ3()<=3 && wj.getJ4()>=0 && wj.getJ4()<=4){
			return true;
		}
		
		return false;
	}
	
	//"F4", "F3", "E4", "E3", "P43A", "P34A", "P43C", "P34C"
	@Override
	protected State getChild(State st, String move) {
		WaterJug child = new WaterJug(st);
		if(move.equals("F4")){
			child.setJ4(4);
		}
		else if(move.equals("F3")){
			child.setJ3(3);
		}
		else if(move.equals("E4")){
			child.setJ4(0);
		}
		else if(move.equals("E3")){
			child.setJ3(0);
		}
		else if(move.equals("P43A")){
			//"P43A"  : Pour J4 into J3 All its content
			int j3 = ((child.getJ3() + child.getJ4())>3 ? 3: (child.getJ3() + child.getJ4()));
			child.setJ3(j3);
			child.setJ4(0);
		}
		else if(move.equals("P34A")){
			//"P34A"  : Pour J3 into J4 All its content
			int j4 = ((child.getJ4() + child.getJ3()) > 4 ? 4: (child.getJ4() + child.getJ3()));
			child.setJ4(j4);
			child.setJ3(0);
		}
		else if(move.equals("P43C")){
			//"P43C"  : Pour J4 into J3 up to Capacity
			int val = 3 - child.getJ3();
			val = ((child.getJ4() > val) ? val : child.getJ4());
			child.setJ3(child.getJ3() + val);
			child.setJ4(child.getJ4() - val);
		}
		else if(move.equals("P34C")){
			//"P34C"  : Pour J3 into J4 up to Capacity
			int val = 4 - child.getJ4();
			val = ((child.getJ3() > val) ? val : child.getJ3());
			child.setJ3(child.getJ3() - val);
			child.setJ4(child.getJ4() + val);
		}
		return child;
	}

	@Override
	protected ArrayList<State> getChildren(State state) {
		ArrayList<State> states = new ArrayList<State>();

		for (String move : WaterJug.moves) {
			WaterJug child = (WaterJug) this.getChild(state, move);
			
			if ((child != null) && this.isValid(child))	{
				states.add(child);
			}
		}

		return states;
	}
	
	@Override
	public boolean equals(Object st) {
		if (st == this) {
			return true;
		}
		if (st instanceof WaterJug && st != null) {
			WaterJug s = (WaterJug) st;
			if (this.getJ3() == s.getJ3() && this.getJ4() == s.getJ4()) {
				return true;
			}
		} 
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return 378 + this.getJ3() + this.getJ4();
	}

	@Override
	public String toString() {
		return "[J4: "+ this.getJ4() +", J3: " +this.getJ3() + "]";
	}

	public static void main(String[] args) {
		State wj = new WaterJug(4, 0);	
		System.out.println("node: "+wj.toString());
		System.out.println("children: ");
		for(State ch : wj.getChildren(wj)){
			System.out.println("Child: " + ch.toString());
		}
	}



}
