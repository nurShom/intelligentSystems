package edu.ohiou.algorithm.spacesearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class TSP_test01 extends State implements Comparable<TSP_test01> {
	
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
	private ArrayList<TSP_test01> children = null;
	private String parent = null;
	
	// default constructor
	public TSP_test01() {
//		this.setCity(null);
//		this.setChildren(null);
//		this.setParent(null);
	}
	
	// copy constructor
	public TSP_test01(State st) {
		this.setCity(((TSP_test01) st).getCity());
		this.setChildren(((TSP_test01) st).getChildrenList());
		this.setParent(((TSP_test01) st).getParent());
	}
	
	// city value only constructor, parent unknown. Maybe the root node
	public TSP_test01(String city) {
		this.setChildren(null);
		this.setParent(null);
		if(TSP_test01.cityIndex.containsKey(city)){
			this.setCity(city);
		}
		else{
			this.setCity(null);
		}
	}
	
	// value assigning constructor
	public TSP_test01(String city, String parent) {
		this.setChildren(null);
		this.setParent(parent);
		if(TSP_test01.cityIndex.containsKey(city)){
			this.setCity(city);
		}
		else{
			this.setCity(null);
		}
	}
	
	private void initializeChildren(){
		this.setChildren(null);
		ArrayList<TSP_test01> nextCities = new ArrayList<TSP_test01>();
		int index = TSP_test01.cityIndex.get(this.getCity());
		int dis = 0;
		for(int i=0; i<cityIndex.size(); i++ ){
			dis = TSP_test01.distanceMatrix[index][i];
			if(dis != 0){
				String city = TSP_test01.getKeyByValue(TSP_test01.cityIndex, i);
				nextCities.add(new TSP_test01(city, this.getCity()));
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

	public ArrayList<TSP_test01> getChildrenList() {
		return this.children;
	}
	
	public void setChildren(ArrayList<TSP_test01> children) {
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
		if (st instanceof TSP_test01 && st != null) {
			TSP_test01 s = (TSP_test01) st;
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
			distance = TSP_test01.distanceMatrix[TSP_test01.cityIndex.get(this.getParent())][TSP_test01.cityIndex.get(this.getCity())];
		}
		return "[" + this.parent + "->" + this.getCity() + ":: " + distance + "]";
	}

	// compares between the distances of cities with their respective parent node 
	@Override
	public int compareTo(TSP_test01 t2) {
		int ind1P = TSP_test01.cityIndex.get(this.getParent());
		int ind1 = TSP_test01.cityIndex.get(this.getCity());
		
		int ind2P = TSP_test01.cityIndex.get(t2.getParent());
		int ind2 = TSP_test01.cityIndex.get(t2.getCity());
		
		return ((Integer) TSP_test01.distanceMatrix[ind1P][ind1]).compareTo(TSP_test01.distanceMatrix[ind2P][ind2]);
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

		TSP_test01 tsp = new TSP_test01("Athens");
		tsp.printChildren(tsp);
		//tsp.setChildren(null);
		tsp.setCity("Cleveland");
		tsp.printChildren(tsp);

	}


}
