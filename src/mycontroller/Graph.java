package mycontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import utilities.Coordinate;

public class Graph {
	

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
		
		// create new set of adjacency lists
		adj = new HashMap<Node,ArrayList<Node>>();
		
		for (Coordinate c : m.getCoordinates()) {
			
			// check this is a valid tile
			if (c.x < 0 | c.y < 0) {
				continue;
			}
			
			// get each key-value pair in the memory map
			CoordinateRecord cr = m.getCoordinateRecord(c);
			
			// create a new node u from it
			Node u = new Node(c,cr);
			
			// add it to the graph
			addNode(u);
			
			// get possible neighbours for that node
			ArrayList<Coordinate> possibleNeighbours = m.getNeighbour(c);
						
			for (Coordinate possibleNeighbour: possibleNeighbours) {
				
				// if a possible neighbour is indeed in the memory map:
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
	
	// implemented from CLRS pseudocode
	
	public void BFS() {

		
		ArrayList<Node> possibleSources = new ArrayList<Node>(adj.keySet());
		
		if (possibleSources.size() < 3) {
			return;
		}
		
		Node source = possibleSources.get(0);
		
				
		HashMap<Node,String> color = new HashMap<Node,String>();
		HashMap<Node,Node> pred = new HashMap<Node,Node>();
		HashMap<Node,Integer> dist = new HashMap<Node,Integer>();
		LinkedList<Node> queue = new LinkedList<Node>();
	
		// set all nodes to unvisited (white)
		for (Node u : adj.keySet()) {
			color.put(u,"white");
		}
		 
		// set the source to visited (black)
		 color.put(source,"black");
		 
		 // predecessor is null
		 pred.put(source,null);
		 
		 // distance is 0
		 dist.put(source,0);
		 
		 // enqueue source
		 queue.add(source);
		 
		 while (queue.size() != 0) {
			 			 
			 // dequeue
			 Node u = queue.poll();
			 Integer distU = dist.get(u);
			 
			 ArrayList<Node> neighbours = adj.get(u);

			 
			 if (neighbours == null) {
				 continue;
			 }
					 
			 for (Node v : neighbours) {
				 
				 // check if it's unvisited
				 if (color.get(v) == "white") {
					 
					 
					 color.put(v,"black");
					 dist.put(v,distU+1);
					 pred.put(v,u);
					 
					 queue.add(v);
				 
				 }
				 
				 
			 }
			 
		 }
		 
		 System.out.println(Arrays.asList(pred));
		 
		
	}
	
	
	
}