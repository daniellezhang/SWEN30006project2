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


	@Override
	public int hashCode(){

		return coord.toString().hashCode();

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
