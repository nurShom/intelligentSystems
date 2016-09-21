package edu.ohiou.algorithm.spacesearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class TSP01 extends State implements Comparable<TSP01> {
	
	private static final int[][] distanceMatrix = { { 0, 75, 155, 206, 46 }, { 75, 0, 105, 137, 120 },
			{ 155, 105, 0, 241, 225 }, { 206, 137, 241, 0, 160 }, { 46, 120, 225, 160, 0 } };
	
	private static final Map<String, Integer> cityIndex; // = new HashMap<String, Integer>(); 
	// = Map.of(); //this syntax available only in Java 9 or later
	static{
		Map<String, Integer> cities = new HashMap<String, Integer>();
		cities.put("Athens", 0);
		cities.put("Columbus", 1);
		cities.put("Cincinnati", 2);
		cities.put("Cleveland", 3);
		cities.put("Marietta", 4);
		cityIndex = Collections.unmodifiableMap(cities);
	}
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	
	private String city = null;
	private ArrayList<TSP01> children = null;
	private String parent = null;
	
	// default constructor
	public TSP01() {
//		this.setCity(null);
//		this.setChildren(null);
//		this.setParent(null);
	}
	
	// copy constructor
	public TSP01(State st) {
		this.setCity(((TSP01) st).getCity());
		this.setChildren(((TSP01) st).getChildrenList());
		this.setParent(((TSP01) st).getParent());
	}
	
	// city value only constructor, parent unknown. Maybe the root node
	public TSP01(String city) {
		this.setChildren(null);
		this.setParent(null);
		if(TSP01.cityIndex.containsKey(city)){
			this.setCity(city);
		}
		else{
			this.setCity(null);
		}
	}
	
	// value assigning constructor
	public TSP01(String city, String parent) {
		this.setChildren(null);
		this.setParent(parent);
		if(TSP01.cityIndex.containsKey(city)){
			this.setCity(city);
		}
		else{
			this.setCity(null);
		}
	}
	
	private void initializeChildren(){
		this.setChildren(null);
		ArrayList<TSP01> nextCities = new ArrayList<TSP01>();
		int index = TSP01.cityIndex.get(this.getCity());
		int dis = 0;
		for(int i=0; i<cityIndex.size(); i++ ){
			dis = TSP01.distanceMatrix[index][i];
			if(dis != 0){
				String city = TSP01.getKeyByValue(TSP01.cityIndex, i);
				nextCities.add(new TSP01(city, this.getCity()));
			}
		}
		Collections.sort(nextCities, Collections.reverseOrder());
		this.setChildren(nextCities);
	}
	

	protected String getCity() {
		return this.city;
	}

	protected void setCity(String city) {
		if(this.getCity()!=null && (!this.getCity().equals(city))){
			this.setChildren(null);
		}
		this.city = city;
	}

	public ArrayList<TSP01> getChildrenList() {
		return this.children;
	}
	
	public void setChildren(ArrayList<TSP01> children) {
		this.children = children;
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Override
	public boolean equals(Object st) {
		if (st == this) {
			return true;
		}
		if (st instanceof TSP01 && st != null) {
			TSP01 s = (TSP01) st;
			if (this.getCity().equals(s.getCity())) {
				return true;
			}
		} 
		
		return false;
	}

	@Override
	public int hashCode() {
		return 378 + this.city.hashCode();
	}

	@Override
	public String toString() {
		int distance = 0;
		if (this.getParent() != null) {
			distance = TSP01.distanceMatrix[TSP01.cityIndex.get(this.getParent())][TSP01.cityIndex.get(this.getCity())];
		}
		return "[" + this.parent + "->" + this.getCity() + ":: " + distance + "]";
	}

	// compares between the distances of cities with their respective parent node 
	@Override
	public int compareTo(TSP01 t2) {
		int ind1P = TSP01.cityIndex.get(this.getParent());
		int ind1 = TSP01.cityIndex.get(this.getCity());
		
		int ind2P = TSP01.cityIndex.get(t2.getParent());
		int ind2 = TSP01.cityIndex.get(t2.getCity());
		
		return ((Integer) TSP01.distanceMatrix[ind1P][ind1]).compareTo(TSP01.distanceMatrix[ind2P][ind2]);
	}
	
	@Override
	protected State getChild(State st, String move) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ArrayList<? extends State> getChildren(State state) {
		if(this.getChildrenList()==null){
			this.initializeChildren();
		}
		return this.getChildrenList();
		//following workaround is type-unsafe, may invoke type mismatch exception:
		//return (ArrayList<State>) ((ArrayList<?>) this.getChildrenList());
	}
	

	public static void main(String[] args) {

		TSP01 tsp = new TSP01("Athens");
		tsp.printChildren(tsp);
		//tsp.setChildren(null);
		tsp.setCity("Cleveland");
		tsp.printChildren(tsp);

	}


}
