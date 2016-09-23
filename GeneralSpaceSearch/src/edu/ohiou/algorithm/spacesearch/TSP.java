package edu.ohiou.algorithm.spacesearch;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TSP extends State {
	
	protected enum Completeness{Complete, Incomplete};
	
	//STATIC BLOCK BEGINS
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
	
	private static int bestDistance = 1000000;
	private static TSP bestRoute = null;
	//STATIC BLOCK ENDS
	
	private Deque<String> visited = null;
	private List<String> unvisited = null;
	private String goalCity = null;
	private int distance = 0;
	private Completeness stateStatus = TSP.Completeness.Incomplete;

	// default constructor
	public TSP() {
		this.setVisited(new ArrayDeque<String>());
		this.setUnvisited(new ArrayList<String>());
		this.setGoalCity("");
	}

	// copy constructor
	public TSP(TSP tsp) {
		this.setVisited(tsp.getVisited());
		this.setUnvisited(tsp.getUnvisited());
		this.setGoalCity(tsp.getGoalCity());
	}
	
	// copy constructor
	public TSP(State state) {
		this();
		if (state instanceof TSP) {
			TSP tsp = (TSP) state;
			this.setVisited(tsp.getVisited());
			this.setUnvisited(tsp.getUnvisited());
			this.setGoalCity(tsp.getGoalCity());
		}
	}
	
	// functioning initialization constructor
	public TSP(String startCity, String goalCity){
		this.setVisited(new ArrayDeque<String>(Arrays.asList(startCity)));
		List<String> others = new ArrayList<String>();
		for(String city : TSP.cityList.keySet()){
			if(!city.equals(startCity) && !city.equals(goalCity)){
				others.add(city);
			}
		}
		this.setUnvisited(others);
		this.setGoalCity(goalCity);
	}
	
	protected Deque<String> getVisited() {
		return visited;
	}

	protected void setVisited(Deque<String> visited) {
		this.visited = new ArrayDeque<String>(visited);
	}
	
	protected void addVisited(String city) {
		Deque<String> visited = this.getVisited();
		visited.addLast(city);
		this.setVisited(visited);
	}

	protected List<String> getUnvisited() {
		return unvisited;
	}

	protected void setUnvisited(List<String> unvisited) {
		this.unvisited = new ArrayList<String>(unvisited);
	}

	protected void removeUnvisited(String city) {
		List<String> unvisited = this.getUnvisited();
		unvisited.remove(city);
		this.setUnvisited(unvisited);
	}

	protected String getGoalCity() {
		return goalCity;
	}

	protected void setGoalCity(String goalCity) {
		this.goalCity = new String(goalCity.toCharArray());
	}

	public Completeness getStateStatus() {
		return stateStatus;
	}

	public void setStateStatus(Completeness stateStatus) {
		this.stateStatus = stateStatus;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public boolean equals(Object st) {
		if (st == this) {
			return true;
		}
		if (st instanceof TSP && st != null) {
			TSP s = (TSP) st;
			if (this.getGoalCity().equals(s.getGoalCity()) && (this.getVisited().size() == s.getVisited().size())
					&& (this.getUnvisited().size() == s.getUnvisited().size())
					&& (this.getStateStatus() == s.getStateStatus())) {
				Deque<String> thQ = new ArrayDeque<String>(this.getVisited());
				Deque<String> stQ = new ArrayDeque<String>(s.getVisited());
				while(thQ.size()>0) {
					if(!thQ.pop().equals(stQ.pop())){
						return false;
					}
				}
				for (int i=0; i<this.getUnvisited().size(); i++) {
					if (!s.getUnvisited().get(i).equals(this.getUnvisited().get(i))) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 378 + this.getGoalCity().hashCode() + this.getVisited().hashCode() + this.getUnvisited().hashCode();
	}

	@Override
	public String toString() {
		String state = "[";
		for(String city : this.getVisited()){
			state += city + "]->[";
		}
		state = state.substring(0, state.length()-3) + " => {";
		for(String city : this.getUnvisited()){
			state += city + ", ";
		}
		if(state.endsWith(", ")){
			state = state.trim().substring(0, state.length()-2);
		}
		state += "} => [" + this.getGoalCity() + "] (\"" + this.getStateStatus() + "\")";
		state += " Distance covered: " + this.getDistance() + ", Best Distance: " + TSP.bestDistance;
		
		return state ;
	}

	@Override
	protected State getChild(State state, String move) {
		TSP tsp = new TSP(state);
		tsp.addVisited(move);
		tsp.removeUnvisited(move);
		if(tsp.getUnvisited().isEmpty()){
			tsp.setStateStatus(TSP.Completeness.Complete);
		}
		int dist = 0;
		List<String> visited = new ArrayList<String>(tsp.getVisited()); 
		for(int i=0; i<visited.size()-1; i++){
			dist += TSP.distanceMatrix[TSP.cityList.get(visited.get(i))][TSP.cityList.get(visited.get(i+1))];
		}
		tsp.setDistance(dist);
		
		if((tsp.getStateStatus()==TSP.Completeness.Complete) && (dist < TSP.bestDistance)){
			TSP.bestDistance = dist;
			TSP.bestRoute = tsp;
		}
		
		return tsp;
	}

	@Override
	protected ArrayList<? extends State> getChildren(State state) {
		TSP tsp = new TSP(state);
		ArrayList<TSP> children = new ArrayList<TSP>();
		for(String move: this.getUnvisited()){
			children.add((TSP) this.getChild(tsp, move));
		}
		return children;
	}
	
	protected State getGoalState(){
		TSP tsp = new TSP();
		tsp.setGoalCity(this.getGoalCity());
		
		Deque<String> fullpath = new ArrayDeque<String>();
		String head = this.getVisited().peekFirst(); 
		fullpath.add(head);
		for(String city : TSP.cityList.keySet()){
			if((!city.equals(head)) && (!city.equals(goalCity))){
				fullpath.addLast(city);
			}
		}
		tsp.setVisited(fullpath);
		
		tsp.setStateStatus(TSP.Completeness.Complete );
		
		return tsp;
	}
	
	protected static void clearResult(){
		TSP.bestDistance = 1000000;
		TSP.bestRoute = null;
	}

	public static void main(String[] args) {
		TSP tsp = new TSP("Athens", "Columbus");
		System.out.println(tsp.toString() + "\n---Children:");
		tsp.printChildren(tsp);
		
		System.out.println("\n-----------\n-----------\nTSP Problem:");
		State init = new TSP("Athens", "Cincinnati");
		System.out.println(init.toString() + "\n---Children:");
		init.printChildren(init);
		System.out.println("--------------------");
		State goal = ((TSP) init).getGoalState();
		System.out.println(goal.toString() + "\n---Children:");
		goal.printChildren(goal);
		
	}

}
