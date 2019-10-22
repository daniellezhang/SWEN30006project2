package mycontroller;

import java.util.Objects;

import utilities.Coordinate;

public class Node {


	public Coordinate coord;
	public CoordinateRecord cr;

	public Node(Coordinate coord, CoordinateRecord cr) {
		this.coord = coord;
		this.cr = cr;
	}

	// (2,1) and (1,32) collide, so I had to change this
	@Override
	public int hashCode(){

		return coord.hashCode() + coord.x + coord.y;

	}

	public Coordinate getCoordinate() {
		return this.coord;
	}

	public CoordinateRecord getCoordinateRecord() {
		return this.cr;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
			return false;
		}

        return (obj.hashCode() == this.hashCode());
	}

	public String toString() {
		return "(" + coord.toString() + ")";
	}

}
