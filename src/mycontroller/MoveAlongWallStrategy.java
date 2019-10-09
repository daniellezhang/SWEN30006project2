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
	public String decideMove(HashMap<Coordinate, MapTile> currentView, WorldSpatial.Direction orientation,String currentPosition) {
		this.currentCoordinate = new Coordinate(currentPosition);
		if (isFollowingWall) {
			// If wall no longer on left, turn left
			if(!checkFollowingWall(orientation, currentView)) {
				return "left";
			} else {
				// If wall on left and wall straight ahead, turn right
				if(checkWallAhead(orientation, currentView)) {
					return "right";
				}
			}
		} else {
			// Start wall-following (with wall on left) as soon as we see a wall straight ahead
			if(checkWallAhead(orientation,currentView)) {
				isFollowingWall = true;
				return "right";
			}
		}
		return "accelerate";
	}
	
	private boolean checkWallAhead(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView){
		switch(orientation){
		case EAST:
			return checkEast(currentView);
		case NORTH:
			return checkNorth(currentView);
		case SOUTH:
			return checkSouth(currentView);
		case WEST:
			return checkWest(currentView);
		default:
			return false;
		}
	}
	private boolean checkFollowingWall(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {
		
		switch(orientation){
		case EAST:
			return checkNorth(currentView);
		case NORTH:
			return checkWest(currentView);
		case SOUTH:
			return checkEast(currentView);
		case WEST:
			return checkSouth(currentView);
		default:
			return false;
		}	
	}
	
	
	
	public boolean checkEast(HashMap<Coordinate, MapTile> currentView){
		// Check tiles to my right
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentCoordinate.x+i, currentCoordinate.y));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkWest(HashMap<Coordinate,MapTile> currentView){
		// Check tiles to my left
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentCoordinate.x-i, currentCoordinate.y));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkNorth(HashMap<Coordinate,MapTile> currentView){
		// Check tiles to towards the top
		
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentCoordinate.x, currentCoordinate.y+i));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkSouth(HashMap<Coordinate,MapTile> currentView){
		// Check tiles towards the bottom
		
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentCoordinate.x, currentCoordinate.y-i));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
}
