package mycontroller;

import java.util.HashMap;
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
	
	public MoveAlongWallStrategy(String currentPosition) {
		isFollowingWall = false;
		this.currentCoordinate = new Coordinate(currentPosition);
	}
	
	@Override
	public CarMove decideMove(HashMap<Coordinate, MapTile> currentView, WorldSpatial.Direction orientation,String currentPosition) {
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
				return CarMove.RIGHT;
			}
		}
		return CarMove.FORWARD;
	}
	

}
