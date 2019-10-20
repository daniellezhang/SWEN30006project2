package mycontroller;

import utilities.Coordinate;

public class Node {
	
	
	public Coordinate coord;
	public CoordinateRecord cr;
	
	public Node(Coordinate coord, CoordinateRecord cr) {
		this.coord = coord;
		this.cr = cr;
	}
	
	@Override
	public int hashCode(){
		
		return coord.hashCode();
	
	}
	
	@Override
	public boolean equals(Object obj) {
        return (obj.hashCode() == this.hashCode());
	}
	
	public String toString() {
		return "(" + coord.toString() + ")";
	}
	
}

