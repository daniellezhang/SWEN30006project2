package mycontroller;

import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;


public interface CarStrategy {
/*interface for strategy to decide the movement of the car*/
	/*method to return string that correspond to the move of the car*/
	public String decideMove(HashMap<Coordinate,MapTile> currentView, WorldSpatial.Direction orientation,String currentPosition);
	
}
