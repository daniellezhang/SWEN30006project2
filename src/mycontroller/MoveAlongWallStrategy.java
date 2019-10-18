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
	private int wallSensitivity = 1;
	private boolean isFollowingWall;
	private Coordinate currentCoordinate;
	private Random rand;
	
	public MoveAlongWallStrategy(String currentPosition) {
		isFollowingWall = false;
		this.currentCoordinate = new Coordinate(currentPosition);
		rand = new Random();
	}
	
	@Override
	public CarMove decideMove(Sensor sensor) {
		
		System.out.println("Hello" + sensor.getView());

		//HashMap<Coordinate, MapTile> currentView, WorldSpatial.Direction orientation,String currentPosition
		HashMap<Coordinate, MapTile> currentView = sensor.getView();
		WorldSpatial.Direction orientation = sensor.getOrientation();
		String currentPosition = sensor.getPosition();
		this.currentCoordinate = new Coordinate(currentPosition);
		if (isFollowingWall) {
			// If wall no longer on left, turn left
			if(!checkFollowingWall(orientation, currentView, currentCoordinate)) {
				return CarMove.LEFT;
			} else {
				// If wall on left and wall straight ahead, turn right
				if(checkWallAhead(orientation, currentView, currentCoordinate)) {
					return CarMove.RIGHT;
				}
			}
		} else {
			// Start wall-following (with wall on left) as soon as we see a wall straight ahead
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
		return CarMove.FORWARD;
	}
	

}
