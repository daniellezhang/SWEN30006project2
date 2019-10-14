package mycontroller;

import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;
import world.Car;
import world.WorldSpatial;

public class Sensor {
	
	private Car car;
	
	public Sensor(Car car) {
		
		this.car = car;
		
	}
	
	public String getPosition() {
		return car.getPosition();
	}
	
	public float getHealth() {
		return car.getHealth();
	}
	
	public float getVelocity() {
		return car.getVelocity();
	}
	
	public WorldSpatial.Direction getOrientation() {
		return car.getOrientation();
	}
	
	public HashMap<Coordinate,MapTile> getView() {
		return car.getView();
	}
	
	// update a memory map
	public MemoryMap update(MemoryMap m) {
		return null;
	}

}