package mycontroller;

import java.util.HashMap;
import java.util.Random;

import exceptions.UnsupportedModeException;
import swen30006.driving.Simulation;
import tiles.MapTile;
import utilities.Coordinate;
import world.Car;
import world.WorldSpatial;

public class MoveAlongWallStrategy implements CarStrategy {
	/*strategy that let the car move along the wall. adopted from SimpleAutoController */
	
	
	// How many minimum units the wall is away from the player.
	private boolean isFollowingWall;
	private Coordinate currentCoordinate;
	private Random rand;
	private String name = "move_along_wall";

	
	public MoveAlongWallStrategy() {
		isFollowingWall = false;
		rand = new Random();
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public CarMove decideMove(Sensor sensor) {
		
		System.out.println("move along wall: ");

		HashMap<Coordinate, MapTile> currentView = sensor.getView();
		WorldSpatial.Direction orientation = sensor.getOrientation();
		String currentPosition = sensor.getPosition();
		this.currentCoordinate = new Coordinate(currentPosition);
		if (isFollowingWall) {
			if(!checkFollowingWall(orientation, currentView, currentCoordinate)) {
				return CarMove.BRAKE;
			} 
			else {
				if(checkWallAhead(orientation, currentView, currentCoordinate)) {
					return CarMove.BRAKE;
				}
			}
		} else {
			// Start wall-following (with wall either side) as soon as we see a wall straight ahead
			if(checkWallAhead(orientation,currentView, currentCoordinate)) {
				isFollowingWall = true;
				//check whether there is wall on the left or right
				world.WorldSpatial.Direction right = WorldSpatial.changeDirection(orientation, WorldSpatial.RelativeDirection.RIGHT);
				world.WorldSpatial.Direction left = WorldSpatial.changeDirection(orientation, WorldSpatial.RelativeDirection.LEFT);
				if(!checkWallAhead(right,currentView, currentCoordinate)) {
					return CarMove.RIGHT;
				}
				if(!checkWallAhead(left,currentView,currentCoordinate)) {
					return CarMove.LEFT;
					}
					
				
				//wall on both side. brake and reverse 
				return CarMove.BACKWARD;
			}
		}
		return CarMove.BRAKE;
	}
	

}
