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
		
		private Sensor sensor;
		// Car Speed to move at
		private final int CAR_MAX_SPEED = 1;
		private CarStrategy alongWall;
		public MyAutoController(Car car) {
			super(car);
			alongWall = new MoveAlongWallStrategy(this.getPosition());
			sensor  = new Sensor(car);
		}
		
		// Coordinate initialGuess;
		// boolean notSouth = true;
		@Override
		public void update() {
			// Gets what the car can see
			HashMap<Coordinate, MapTile> currentView = getView();
			CarMove move = alongWall.decideMove(sensor);
			// checkStateChange();
			if (move == CarMove.LEFT) {
				this.turnLeft();
			}
			else if (move == CarMove.RIGHT) {
				this.turnRight();
			}
			else if(move == CarMove.FORWARD) {
				this.applyForwardAcceleration();
			}
			else if (move==CarMove.BRAKE) {
				this.applyBrake();
			}
			else if (move==CarMove.BACKWARD) {
				this.applyReverseAcceleration();
			}
		}

	
		
	}
