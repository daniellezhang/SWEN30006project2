package mycontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;
import world.WorldSpatial.Direction;

public class TargetStrategy implements CarStrategy {

	private ArrayList<Coordinate> parcels;
	private ArrayList<Coordinate> finish;
	
	private static int SQUARE = 2;
	
	public TargetStrategy() {

		parcels = MemoryMap.getMemoryMap().getParcels();
		finish = MemoryMap.getMemoryMap().getFinish();
		
	}
	
	public CarMove decideMove(Sensor sensor) {
		parcels = MemoryMap.getMemoryMap().getParcels();
		System.out.println(parcels);
		
		System.out.println("TARGET");

		// if we're sitting on the first parcel, remove it.
		if (parcels.size() > 0 && sensor.getCoordinate().equals(parcels.get(0))) {
			parcels.remove(0);
		}
		

		Graph g =Graph.getGraph();

		if (sensor.enoughParcels()) {
			
			// if we have enough parcels and we have a finish tile, go!
			if (finish.size() > 0) {
				
				Coordinate firstFinish = pickClosest(sensor.getCoordinate(), finish, g);
				
				List<Coordinate> path = g.BFS(sensor.getCoordinate(),firstFinish);
				
				return getNextMove(path,sensor.getOrientation());
				
			}
			
			// if it fails, return brake to trigger
			System.out.println("TargetStrategy.java - decideMove(): all parcels eaten, no finish tile in sight.");
			return CarMove.BRAKE;
			
		}
		
		if (parcels.size() == 0) {
			return CarMove.BRAKE;
		}

		// if we still have parcels left, find them (in order of closeness)
		Coordinate closest = pickClosest(sensor.getCoordinate(),parcels,g);
					
		if(closest != null) {
			List<Coordinate> path = g.BFS(sensor.getCoordinate(),closest);
			System.out.println(path);
			return getNextMove(path,sensor.getOrientation());
		}
		
	
		return CarMove.BRAKE;
			
	}
		
	
	public CarMove getNextMove(List<Coordinate> path,WorldSpatial.Direction direction) {
		
		if (path == null || path.size() == 0) {
			System.out.println(path);
			System.out.println("No path available");
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
	
	public Coordinate pickClosest(Coordinate current, List<Coordinate> options, Graph g) {
		
		if (options.size() == 0) {
			return null;
		}
		
		// filter reachable targets
		List<Coordinate> reachableOptions = options.stream()
												.filter(x -> (g.BFS(current,x).size() > 0))
												.collect(Collectors.toList());
	
		///no reachable target
		if (reachableOptions.size() == 0) {
			return null;
		}
		
		// otherwise, pick the closest one.
		Coordinate closest = reachableOptions.get(0);
		double minDist = getDistance(current,closest);
		
		for (Coordinate coordinate : reachableOptions) {
			
			double dist = getDistance(current,coordinate);
			
			if (dist < minDist) {
				closest = coordinate;
				minDist = dist;
			}
			
		}
		
		return closest;
		
	}

	public double getDistance(Coordinate a, Coordinate b) {
		
		double xdiff = Math.pow((a.x - b.x),SQUARE);
		double ydiff = Math.pow((a.y - b.y),SQUARE);
		
		return Math.sqrt(xdiff + ydiff);
		
	}

}
