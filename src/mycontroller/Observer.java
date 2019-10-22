package mycontroller;

import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;

public interface Observer {
	public abstract void respondEvent(HashMap<Coordinate, MapTile> currentView, CarMove move,Coordinate currentCoordinate);
}

