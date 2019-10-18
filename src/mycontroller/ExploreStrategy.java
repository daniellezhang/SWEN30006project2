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
	private boolean isFollowingWall;
	
	public ExploreStrategy() {
		rand = new Random();
		isFollowingWall = false;
		
	}
	
	@Override
	public CarMove decideMove(Sensor sensor) {
		
		//get the sensor's detection    
		HashMap<Coordinate,MapTile> currentView = sensor.getView();
		this.currentCoordinate = sensor.getCoordinate();
		WorldSpatial.Direction orientation = sensor.getOrientation();
		ArrayList<CarMove> possibleMove = new ArrayList<CarMove>();
		
		//the car isn't moving, decide whether to accelerate forward or backward
		if(sensor.getVelocity()==0) {
			//no wall ahead, can accelerate
			if(!checkWallAhead(orientation, currentView, currentCoordinate)) {
				possibleMove.add(CarMove.FORWARD);
			}
			if(!checkWallAhead(WorldSpatial.reverseDirection(orientation),currentView,currentCoordinate)) {
				possibleMove.add(CarMove.BACKWARD);
			}
		}
		//the car is moving
		else {
			//the car is moving forward. check if there is wall on the left, right and ahead
			if(sensor.getVelocity()>0) {
				//no wall head, can accelerate. prioritise driving straight
				if(!checkWallAhead(orientation, currentView, currentCoordinate)) {
					possibleMove.add(CarMove.FORWARD);
					return CarMove.FORWARD;
				}
				//no wall on the left, can turn left
				if(!checkWallAhead(WorldSpatial.changeDirection(orientation, WorldSpatial.RelativeDirection.LEFT),
						currentView, currentCoordinate)) {
					possibleMove.add(CarMove.LEFT);
				}
				//no wall on the right, can turn right
				if(!checkWallAhead(WorldSpatial.changeDirection(orientation, WorldSpatial.RelativeDirection.RIGHT),
						currentView, currentCoordinate)) {
					possibleMove.add(CarMove.RIGHT);
				}
			}
			//the car is moving backward. check if there is wall on the back
			else {
				if(!checkWallAhead(WorldSpatial.reverseDirection(orientation),currentView,currentCoordinate)) {
					return CarMove.BACKWARD;
				}
				else {
					orientation = WorldSpatial.reverseDirection(orientation);
					if(!checkWallAhead(WorldSpatial.changeDirection(orientation, WorldSpatial.RelativeDirection.LEFT),
							currentView, currentCoordinate)) {
						possibleMove.add(CarMove.LEFT);
					}
					//no wall on the right, can turn right
					if(!checkWallAhead(WorldSpatial.changeDirection(orientation, WorldSpatial.RelativeDirection.RIGHT),
							currentView, currentCoordinate)) {
						possibleMove.add(CarMove.RIGHT);
					}
				}
				
			}
			
			
			//No possible move so far. brake
			if(possibleMove.size()==0) {
				return CarMove.BRAKE;
			}
		}
		//choose a random move from the list of possible moves
		int i = rand.nextInt(possibleMove.size());
		return possibleMove.get(i);
	}

}
