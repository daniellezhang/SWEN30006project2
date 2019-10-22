package mycontroller;

import controller.CarController;
import world.Car;

import java.util.ArrayList;
import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class MyAutoController extends CarController implements Subject{	
		// How many minimum units the wall is away from the player.

		private MoveHistory history;
		private Sensor sensor;
		// Car Speed to move at
		private CarStrategy strategy;
		
		private ArrayList<Observer> observers;
		
		public MyAutoController(Car car) {
			super(car);
			strategy = CarStrategyManager.getCarStrategyManager();
			sensor  = new Sensor(car);
			history = new MoveHistory();
			
			this.observers = new ArrayList<Observer>();
			
			addObserver(MemoryMap.getMemoryMap());
			
		}
		
		// Coordinate initialGuess;
		// boolean notSouth = true;
		@Override
		public void update() {
			// Gets what the car can see
			HashMap<Coordinate, MapTile> currentView = sensor.getView();
			
			CarMove move = strategy.decideMove(sensor);
			//MemoryMap.getMemoryMap().updateMap(currentView);
			//history.addMove(move);
			Coordinate currentCoordinate = sensor.getCoordinate();
			publishEvent(currentView,move,currentCoordinate);
			//the coordinate the vehicle is currently on has been visited. update it in MemoryMap
			// MemoryMap.getMemoryMap().getCoordinateRecord(sensor.getCoordinate()).setIsVisited(true);

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
		@Override
		public void addObserver(Observer observer) {
			this.observers.add(observer);
			
		}

		@Override
		public void removeObserver(Observer observer) {
			this.observers.remove(observer);
		}

		@Override
		public void publishEvent(HashMap<Coordinate, MapTile> currentView, CarMove move) {
		
			for(Observer observer: this.observers) {
				observer.respondEvent(currentView,move);
			}
		
		}
	
		
	}
