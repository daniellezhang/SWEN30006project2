package mycontroller;

import controller.CarController;
import world.Car;
import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class MyAutoController extends CarController{		
		// How many minimum units the wall is away from the player.
		private int wallSensitivity = 1;
		
		private boolean isFollowingWall = false; // This is set to true when the car starts sticking to a wall.
		
		// Car Speed to move at
		private final int CAR_MAX_SPEED = 1;
		private CarStrategy alongWall;
		public MyAutoController(Car car) {
			super(car);
			alongWall = new MoveAlongWallStrategy(this.getPosition());
		}
		
		// Coordinate initialGuess;
		// boolean notSouth = true;
		@Override
		public void update() {
			// Gets what the car can see
			HashMap<Coordinate, MapTile> currentView = getView();
			String move = alongWall.decideMove(currentView, this.getOrientation(), this.getPosition());
			// checkStateChange();
			if (move == "left") {
				this.turnLeft();
			}
			else if (move == "right") {
				this.turnRight();
			}
			else if(move == "accelerate") {
				this.applyForwardAcceleration();
			}
		}

		/**
		 * Check if you have a wall in front of you!
		 * @param orientation the orientation we are in based on WorldSpatial
		 * @param currentView what the car can currently see
		 * @return
		 */
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
		
		/**
		 * Check if the wall is on your left hand side given your orientation
		 * @param orientation
		 * @param currentView
		 * @return
		 */
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
		
		/**
		 * Method below just iterates through the list and check in the correct coordinates.
		 * i.e. Given your current position is 10,10
		 * checkEast will check up to wallSensitivity amount of tiles to the right.
		 * checkWest will check up to wallSensitivity amount of tiles to the left.
		 * checkNorth will check up to wallSensitivity amount of tiles to the top.
		 * checkSouth will check up to wallSensitivity amount of tiles below.
		 */
		public boolean checkEast(HashMap<Coordinate, MapTile> currentView){
			// Check tiles to my right
			Coordinate currentPosition = new Coordinate(getPosition());
			for(int i = 0; i <= wallSensitivity; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x+i, currentPosition.y));
				if(tile.isType(MapTile.Type.WALL)){
					return true;
				}
			}
			return false;
		}
		
		public boolean checkWest(HashMap<Coordinate,MapTile> currentView){
			// Check tiles to my left
			Coordinate currentPosition = new Coordinate(getPosition());
			for(int i = 0; i <= wallSensitivity; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x-i, currentPosition.y));
				if(tile.isType(MapTile.Type.WALL)){
					return true;
				}
			}
			return false;
		}
		
		public boolean checkNorth(HashMap<Coordinate,MapTile> currentView){
			// Check tiles to towards the top
			Coordinate currentPosition = new Coordinate(getPosition());
			for(int i = 0; i <= wallSensitivity; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y+i));
				if(tile.isType(MapTile.Type.WALL)){
					return true;
				}
			}
			return false;
		}
		
		public boolean checkSouth(HashMap<Coordinate,MapTile> currentView){
			// Check tiles towards the bottom
			Coordinate currentPosition = new Coordinate(getPosition());
			for(int i = 0; i <= wallSensitivity; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y-i));
				if(tile.isType(MapTile.Type.WALL)){
					return true;
				}
			}
			return false;
		}
		
	}
