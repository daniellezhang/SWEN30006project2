package mycontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import tiles.MapTile;
import utilities.Coordinate;

public class TargetStrategy implements CarStrategy {

	private ArrayList<Coordinate> parcels;

	private boolean reachedTarget;

	private Node target;

	private ArrayList<Coordinate> path;

	public TargetStrategy() {

		parcels = MemoryMap.getMemoryMap().getParcels();

	}

	public CarMove decideMove(Sensor sensor) {


		sensor.getCoordinate();

		Graph g = new Graph(MemoryMap.getMemoryMap());

		g.BFS(sensor.getCoordinate());

		return CarMove.BRAKE;

	}

	@Override
	public String getName() {
		return "target";
	}

}
