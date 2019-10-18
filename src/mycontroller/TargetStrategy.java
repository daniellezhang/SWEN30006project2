package mycontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import tiles.MapTile;
import utilities.Coordinate;

public class TargetStrategy implements CarStrategy {

	
	
	public CarMove decideMove(Sensor sensor) {


		
		
		return CarMove.BACKWARD;
	}

	@Override
	public String getName() {
		return null;
	}
	
	

	
}

class Graph {
	

	/* the graph is represented by vertices, represented by maptiles */
	/* each maptile has a corresponding list of adjacent maptile */

	private HashMap<Node,ArrayList<Node>> adj; // array of adjacency lists
			
	
	public Graph(int numTiles) {
		
		adj = new HashMap<Node,ArrayList<Node>>();
		
	}
	
	// creates an empty adjacency list for node n
	public void addNode(Node n) {
		
		adj.put(n,new ArrayList<Node>());
		
	}
	
	// adds a node v to the adjacency list for a node u
	public void addEdge(Node u, Node v) {
		
		ArrayList<Node> uEdges = adj.get(u);
		uEdges.add(v);
		
	}
	
	// prints out the graph
	
	public void printGraph() {
		
		
		for (Node n: adj.keySet()) {
			
			System.out.println("Node " + n.toString() + ": " + adj.get(n).toString());
			
		}
		
	}
	
	public Graph(MemoryMap m) {
		
		adj = new HashMap<Node,ArrayList<Node>>();
		
		for (Coordinate c : m.getCoordinates()) {
			
			CoordinateRecord cr = m.getCoordinateRecord(c);
			
			Node u = new Node(c,cr);
			
			addNode(u);
			
			ArrayList<Coordinate> possibleNeighbours = m.getNeighbour(c);
			
			for (Coordinate possibleNeighbour: possibleNeighbours) {
				
				CoordinateRecord testCR = m.getCoordinateRecord(possibleNeighbour);
				
				if (testCR != null) {
				
					Node v = new Node(possibleNeighbour,testCR);
					
					addEdge(u,v);
					
				}
				
			}
			
			
		}
		
		
	}
	
	public boolean containsNode(Node u) {
		
		return (adj.get(u) == null) ? false : true;
		
	}
	
	public void BFS(Node source, Node dest) {
		
	}
	
	
	
}

class Node {
	
	
	public Coordinate coord;
	public CoordinateRecord cr;
	
	public Node(Coordinate coord, CoordinateRecord cr) {
		this.coord = coord;
		this.cr = cr;
	}
	
	public int hashCode(){
		return coord.hashCode();
	}
	
	public String toString() {
		return coord.toString();
	}
	
}

