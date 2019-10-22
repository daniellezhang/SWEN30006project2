package mycontroller;


import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;

public interface Subject {
	public abstract void addObserver(Observer observer);
	public abstract void removeObserver(Observer observer);
	public abstract void publishEvent(HashMap<Coordinate, MapTile> currentView, CarMove move,Coordinate currentCoordinate);
}
