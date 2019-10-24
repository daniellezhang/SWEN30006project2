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


		private Sensor sensor;
		private CarStrategy strategy;
		private ArrayList<Observer> observers;
		
		public MyAutoController(Car car) {
			super(car);
			strategy = CarStrategyManager.getCarStrategyManager();
			sensor  = new Sensor(car);
			
			//add the listener into Observer list
			this.observers = new ArrayList<Observer>();
			addObserver(MemoryMap.getMemoryMap());
			addObserver(MoveHistory.getMoveHistory());
			
		}
		
		@Override
		public void update() {
			// Gets what the car can see
			HashMap<Coordinate, MapTile> currentView = sensor.getView();
			
			publishEvent(currentView,null,sensor.getCoordinate());
			
			// get nest move according to strategy result
			CarMove move = strategy.decideMove(sensor);
			System.out.println(move);
			// Notification for observers everytime when the car moves
			Coordinate currentCoordinate = sensor.getCoordinate();
			publishEvent(currentView,move,currentCoordinate);

            // car actions according to next move
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
		public void publishEvent(HashMap<Coordinate, MapTile> currentView, CarMove move,Coordinate currentCoordinate) {
		
			for(Observer observer: this.observers) {
				observer.respondEvent(currentView,move,currentCoordinate);
			}
		
		}
	
		
	}
