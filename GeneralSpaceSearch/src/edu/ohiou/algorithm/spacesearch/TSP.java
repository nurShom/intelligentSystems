package edu.ohiou.algorithm.spacesearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TSP extends State {
	
	private static final Map<String, Integer> cityList;
	static{
		Map<String, Integer> cities = new HashMap<String, Integer>();
		cities.put("Athens", 0);
		cities.put("Columbus", 1);
		cities.put("Cincinnati", 2);
		cities.put("Cleveland", 3);
		cities.put("Marietta", 4);
		cityList = Collections.unmodifiableMap(cities);
	}
	private static final int[][] distanceMatrix = { { 0, 75, 155, 206, 46 }, { 75, 0, 105, 137, 120 },
			{ 155, 105, 0, 241, 225 }, { 206, 137, 241, 0, 160 }, { 46, 120, 225, 160, 0 } };
	
	private ArrayList<String> cities = null; //this is the "State value" of this state
	private String firstCity = null, lastCity = null;

	public TSP() {
		// TODO Auto-generated constructor stub
	}

	public TSP(String state) {
		// TODO Auto-generated constructor stub
	}

	public TSP(TSP State) {
		// TODO Auto-generated constructor stub
	}

	public TSP(int abc) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean equals(Object st) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected State getChild(State st, String move) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ArrayList<? extends State> getChildren(State state) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
