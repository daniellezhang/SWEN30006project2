package mycontroller;
import java.util.Random;

import tiles.MapTile;

import java.util.HashMap;
import java.util.ArrayList;
import utilities.Coordinate;
import world.WorldSpatial;

public class ExploreStrategy implements CarStrategy {
	private Random rand;
	private Coordinate currentCoordinate;
	private String name = "explore";
	
	public ExploreStrategy() {
		rand = new Random();
		
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public CarMove decideMove(Sensor sensor) {
		
		//get the sensor's detection    
		HashMap<Coordinate,MapTile> currentView = sensor.getView();
		this.currentCoordinate = sensor.getCoordinate();
		WorldSpatial.Direction orientation = sensor.getOrientation();
		HashMap<CarMove,Coordinate> possibleMove =new HashMap<CarMove,Coordinate>();
		HashMap<CarMove,Coordinate> unvisited =new HashMap<CarMove,Coordinate>();
		//the carMove command that will let the vehicle continue to travel straight
		CarMove goStraight = null;
		//the car isn't moving, decide whether to accelerate forward or backward
		if(sensor.getVelocity()==0) {
			//no wall ahead, can accelerate
			if(!checkWallAhead(orientation, currentView, currentCoordinate)) {
				Coordinate neighbour = getNeighbourCoordinate(orientation, currentCoordinate);
				possibleMove.put(CarMove.FORWARD,neighbour);
			}
			if(!checkWallAhead(WorldSpatial.reverseDirection(orientation),currentView,currentCoordinate)) {
				WorldSpatial.Direction reverse = WorldSpatial.reverseDirection(orientation);
				Coordinate neighbour = getNeighbourCoordinate(reverse, currentCoordinate);
				possibleMove.put(CarMove.BACKWARD,neighbour);
			}
		}
		//the car is moving
		else {
			//the car is moving forward. check if there is wall on the left, right and ahead
			if(sensor.getVelocity()>0) {
				//no wall head, can accelerate. 
				if(!checkWallAhead(orientation, currentView, currentCoordinate)) {
					possibleMove.put(CarMove.FORWARD, getNeighbourCoordinate(orientation, currentCoordinate));
					goStraight = CarMove.FORWARD;
				}
				//no wall on the left, can turn left
				if(!checkWallAhead(WorldSpatial.changeDirection(orientation, WorldSpatial.RelativeDirection.LEFT),
						currentView, currentCoordinate)) {
					WorldSpatial.Direction left = WorldSpatial.changeDirection(orientation, WorldSpatial.RelativeDirection.LEFT);
					possibleMove.put(CarMove.LEFT, getNeighbourCoordinate(left, currentCoordinate));
				}
				//no wall on the right, can turn right
				if(!checkWallAhead(WorldSpatial.changeDirection(orientation, WorldSpatial.RelativeDirection.RIGHT),
						currentView, currentCoordinate)) {
					WorldSpatial.Direction right = WorldSpatial.changeDirection(orientation, WorldSpatial.RelativeDirection.RIGHT);
					possibleMove.put(CarMove.RIGHT, getNeighbourCoordinate(right, currentCoordinate));
				}
			}
			
			//the car is moving backward. check if there is wall on the back
			else {
				//no wall at the back, can continue move backward
				if(!checkWallAhead(WorldSpatial.reverseDirection(orientation),currentView,currentCoordinate)) {
					WorldSpatial.Direction reverse = WorldSpatial.reverseDirection(orientation);
					possibleMove.put(CarMove.BACKWARD, getNeighbourCoordinate(reverse, currentCoordinate));
					goStraight = CarMove.BACKWARD;
				}
				else {
					orientation = WorldSpatial.reverseDirection(orientation);
					//no wall on the elft, can turn left
					if(!checkWallAhead(WorldSpatial.changeDirection(orientation, WorldSpatial.RelativeDirection.LEFT),
							currentView, currentCoordinate)) {
						WorldSpatial.Direction left = WorldSpatial.changeDirection(orientation, WorldSpatial.RelativeDirection.LEFT);
						possibleMove.put(CarMove.LEFT, getNeighbourCoordinate(left, currentCoordinate));
					}
					//no wall on the right, can turn right
					if(!checkWallAhead(WorldSpatial.changeDirection(orientation, WorldSpatial.RelativeDirection.RIGHT),
							currentView, currentCoordinate)) {
						WorldSpatial.Direction right = WorldSpatial.changeDirection(orientation, WorldSpatial.RelativeDirection.RIGHT);
						possibleMove.put(CarMove.RIGHT, getNeighbourCoordinate(right, currentCoordinate));
					}
				}
				
			}
			
			
			//No possible move so far. brake
			if(possibleMove.size()==0) {
				return CarMove.BRAKE;
			}
		}
		
		//get a list of moves that leads to unvisited and reachable coordinates
		for(CarMove move:possibleMove.keySet()) {
			Coordinate neighbour = possibleMove.get(move);
			CoordinateRecord neighbourRecord = MemoryMap.getMemoryMap().getCoordinateRecord(neighbour);
			//the coordinate that the move leads to immediately hasn't been visited
			if(neighbourRecord == null || ! neighbourRecord.getIsVisited()) {
				unvisited.put(move,neighbour);
				break;
			}
			
			//the neighbours of the coordinate that the move leads to immediately hasn't been visited and it isn't unreachable
			for(Coordinate nextNeighbour: MemoryMap.getMemoryMap().getNeighbour(neighbour)) {
				neighbourRecord = MemoryMap.getMemoryMap().getCoordinateRecord(nextNeighbour) ;
				if(neighbourRecord == null || (! neighbourRecord.getIsVisited() && neighbourRecord.getReachable() != TileStatus.UNREACHABLE)) {
					unvisited.put(move,neighbour);
					break;
				}
			}
		}		
		
		//if going straight go to a coordinate that hasn't been visited, keep going straight
		if(goStraight != null) {
			if(unvisited.getOrDefault(goStraight, null) != null) {
				return goStraight;
			}
		}
		//choose a random move that leads the vehicle to an unvisited coordinate
		if(unvisited.size() > 0) {
			int index = rand.nextInt(unvisited.size());
			ArrayList<CarMove> unvisitedMove = new ArrayList<CarMove>(unvisited.keySet());
			return unvisitedMove.get(index);
		}
		//no move leads the vehicle to an unvisited coordinate. choose a random possible move
		else if(possibleMove.size() > 0){
			int index = rand.nextInt(possibleMove.size());
			ArrayList<CarMove> possibleMoveList = new ArrayList<CarMove>(possibleMove.keySet());
			return possibleMoveList.get(index);
		}
		return CarMove.BRAKE;
		
	}
}
