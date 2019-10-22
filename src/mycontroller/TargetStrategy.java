package mycontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;
import world.WorldSpatial.Direction;

public class TargetStrategy implements CarStrategy {

	private ArrayList<Coordinate> parcels;

	public TargetStrategy() {

		parcels = MemoryMap.getMemoryMap().getParcels();

	}

	public CarMove decideMove(Sensor sensor) {

		// create a new graph at every move
		Graph g = new Graph(MemoryMap.getMemoryMap());
		
		System.out.println(sensor.getOrientation());

		// the target is for now, (2,3)
		
		ArrayList<Coordinate> parcels = MemoryMap.getMemoryMap().getParcels();
		
		if (parcels != null && parcels.size() == 0) {
			System.out.println("TargetStrategy.java - decideMove(): No parcels in MemoryMap.");
			return CarMove.BRAKE;
		}
		
		Coordinate firstParcel = parcels.get(0);
		
		System.out.println(MemoryMap.getMemoryMap().getCoordinateRecord(firstParcel).getMapTile());
		
		List<Coordinate> path = g.BFS(sensor.getCoordinate(),firstParcel);
		CarMove res = getNextMove(path,sensor.getOrientation());
		System.out.println(res);
		return res;

	}
	
	
	public CarMove getNextMove(List<Coordinate> path,WorldSpatial.Direction direction) {
		
		if (path == null || path.size() < 2) {
			return CarMove.BRAKE;
		}
		
		Coordinate curr = path.get(0);
		Coordinate next = path.get(1);
		
		int horizontal = curr.x - next.x;
		int vertical = curr.y - next.y;

		if (direction == Direction.NORTH) {
			
			if (horizontal != 0) {
				return (horizontal > 0 ? CarMove.LEFT : CarMove.RIGHT);
			}
			
			return (vertical > 0 ? CarMove.BACKWARD : CarMove.FORWARD);
			
		}
		
		if (direction == Direction.SOUTH) {
			
			if (horizontal != 0) {
				return (horizontal > 0 ? CarMove.RIGHT : CarMove.LEFT);
			}
			
			return (vertical > 0 ? CarMove.FORWARD : CarMove.BACKWARD);
			
		}
		
		if (direction == Direction.EAST) {
			
			if (horizontal != 0) {
				return (horizontal > 0 ? CarMove.BACKWARD : CarMove.FORWARD);
			}
			
			return (vertical > 0 ? CarMove.RIGHT : CarMove.LEFT);
			
		}
		
		if (direction == Direction.WEST) {
			
			if (horizontal != 0) {
				return (horizontal > 0 ? CarMove.FORWARD : CarMove.BACKWARD);
			}
			
			return (vertical > 0 ? CarMove.LEFT : CarMove.RIGHT);
			
		}
		
		return CarMove.BRAKE;
				
	}

	@Override
	public String getName() {
		return "target";
	}

}
