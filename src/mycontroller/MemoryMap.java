package mycontroller;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

import utilities.Coordinate;
import tiles.*;
import world.World;

public class MemoryMap implements Observer{
/*a class to record information about all the tiles that the car has detected*/
	private HashMap<Coordinate,CoordinateRecord> record;
	private static MemoryMap map = null;
	private Coordinate start;
	private ArrayList<Coordinate> finish;
	private ArrayList<Coordinate> parcelCoord;
	
	
	private MemoryMap(){
		record = new HashMap<Coordinate,CoordinateRecord>();
		finish = new ArrayList<Coordinate>();
		parcelCoord = new ArrayList<Coordinate>();
	}
	
	/*getter for the singleton object*/
	public static MemoryMap getMemoryMap() {
		if(map==null) {
			map = new MemoryMap();
		}
		return map;
	}
	
//	/*update the coordinate record with the current view from the car*/
//	public void updateMap(HashMap<Coordinate, MapTile> currentView) {
//		
//		for(Coordinate key: currentView.keySet()) {
//			
//			CoordinateRecord cr = new CoordinateRecord(currentView.get(key));
//			
//			//
//			// check whether it already exists in our record
//			//
//			
//			record.put(key, cr);
//			
//		}
//		
//	}
	
	@Override
	public void respondEvent(HashMap<Coordinate, MapTile> currentView, CarMove move,Coordinate currentCoordinate) {
		

		
		for(Coordinate key: currentView.keySet()) {
			
			CoordinateRecord cr = new CoordinateRecord(currentView.get(key));
			
			//
			// check whether it already exists in our record
			//
			
			if (!record.containsKey(key)) {
				record.put(key, cr);
				//check whether the coordinate is a start tile
				if (currentView.get(key).getType() == MapTile.Type.START) {
					start = key;
				}
				//check whether the coordinate is a finish tile
				if (currentView.get(key).getType() == MapTile.Type.FINISH) {
					finish.add(key);
				}
				//check whether the coordinate is a parcel tile
				if(currentView.get(key) instanceof ParcelTrap) {
					if(!parcelCoord.contains(key)) {
						parcelCoord.add(key);
					}
				}
				//check whether the coordinate is a wall
				if(currentView.get(key).getType() == MapTile.Type.WALL) {

					record.get(key).setReachable(TileStatus.UNREACHABLE);
				}
			}
			else {
				/*if this coordinate used to be a parcel tile but not anymore, 
				 * remove it from ParcelCoord and update its tile information in record
				 */ 
				if(parcelCoord.contains(key)) {
					if(!(currentView.get(key) instanceof ParcelTrap)) {
						parcelCoord.remove(key);
						record.get(key).setTile(currentView.get(key));
					}
				}
				
			}
			
		}
		//mark the current coordinate as visited and reachable
		record.get(currentCoordinate).setIsVisited(true);
		record.get(currentCoordinate).setReachable(TileStatus.REACHABLE);
	}
	
	public CoordinateRecord getCoordinateRecord(Coordinate coord) {
		
		return record.get(coord);
		
	}
	
	public ArrayList<Coordinate> getCoordinates() {
	
		return new ArrayList<>(record.keySet());
	
	}
	
	
	/*get a list of coordinates that are neighbours to the given position*/
	public ArrayList<Coordinate> getNeighbour(Coordinate position){
		ArrayList<Coordinate> neighbours = new ArrayList<Coordinate>();
		int x = position.x;
		int y = position.y;
		int[] x_coords = {x-1,x+1};
		int[] y_coords = {y-1,y+1};
		for (int i=0;i<2;i++) {
			/*check if the value of the x and y coordinate are within the range of map size*/
			if(x_coords[i] > 0 && x_coords[i] <= World.MAP_WIDTH) {
				neighbours.add(new Coordinate(x_coords[i],y));
			}
		}
		
		for (int j=0;j<2;j++) {
			/*check if the value of the x and y coordinate are within the range of map size*/
			if(y_coords[j]>0 && y_coords[j] <= World.MAP_HEIGHT) {
				neighbours.add(new Coordinate(x,y_coords[j]));
			}
		}
		
		return neighbours;
	}
	
	public Coordinate getStart() {
		return start;
	}
	
	public ArrayList<Coordinate> getFinish(){
		return finish;
	}
	public ArrayList<Coordinate> getParcels(){
		return parcelCoord;
	}

}
