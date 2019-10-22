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
	private ArrayList<Coordinate> finish;
	
	private boolean isViable;

	public TargetStrategy() {

		parcels = MemoryMap.getMemoryMap().getParcels();
		finish = MemoryMap.getMemoryMap().getFinish();
		
	}

	public CarMove decideMove(Sensor sensor) {
		
		System.out.println("TARGET");

		// if we're sitting on the first parcel, remove it.
		if (parcels.size() > 0 && sensor.getCoordinate().equals(parcels.get(0))) {
			parcels.remove(0);
		}
		
		// create a new graph at every move
		Graph g = new Graph(MemoryMap.getMemoryMap());

		if (parcels.size() == 0) {
			
			// this is guaranteed to be > 0, as target strategy is only used
			// when all parcels and at least 1 finish tile has been seen.
			
			if (finish.size() > 0) {
				
				Coordinate firstFinish = finish.get(0);
				
				List<Coordinate> path = g.BFS(sensor.getCoordinate(),firstFinish);
				
				return getNextMove(path,sensor.getOrientation());
				
			}
			
			// but if it fails, let user know.
			System.out.println("TargetStrategy.java - decideMove(): all parcels eaten, no finish tile in sight.");
			return CarMove.BRAKE;
			
		}

		// if we still have parcels left, find them
		Coordinate firstParcel = parcels.get(0);
				
		List<Coordinate> path = g.BFS(sensor.getCoordinate(),firstParcel);
		
		return getNextMove(path,sensor.getOrientation());
		
	}
	
	public CarMove getNextMove(List<Coordinate> path,WorldSpatial.Direction direction) {
		
		if (path == null || path.size() < 2) {
			System.out.println("No path available");
			isViable = false;
			return CarMove.BRAKE;
		}
		
		isViable = true;
		
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
