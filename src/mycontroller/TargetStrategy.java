package mycontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import tiles.MapTile;
import utilities.Coordinate;

public class TargetStrategy implements CarStrategy {

	
	
	public CarMove decideMove(Sensor sensor) {


		
		
		return CarMove.BACKWARD;
	}

	@Override
	public String getName() {
		return null;
	}
	
	

	
}
