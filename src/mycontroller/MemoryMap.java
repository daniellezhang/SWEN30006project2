package mycontroller;
import java.util.HashMap;
import java.util.ArrayList;
import utilities.Coordinate;
import tiles.*;
import world.World;

public class MemoryMap {
/*a class to record information about all the tiles that the car has detected*/
	private HashMap<Coordinate,MapTile> coordinateRecord;
	private static MemoryMap map = null;;
	private MemoryMap(){
		coordinateRecord = new HashMap<Coordinate,MapTile>();
	}
	
	/*getter for the singleton object*/
	public static MemoryMap getMemoryMap() {
		if(map==null) {
			map = new MemoryMap();
		}
		return map;
	}
	
	/*update the coordinate record with the current view from the car*/
	public void updateMap(HashMap<Coordinate, MapTile> currentView) {
		for(Coordinate key: currentView.keySet()) {
			coordinateRecord.put(key, currentView.get(key));
		}
	}
	
	/*get a list of coordinates that are neighbours to the given position*/
	public ArrayList<Coordinate> getNeighbour(Coordinate position){
		ArrayList<Coordinate> neighbours = new ArrayList<Coordinate>();
		int x = position.x;
		int y = position.y;
		int[] x_coords = {x-1,x,x+1};
		int[] y_coords = {y-1,y,y+1};
		for (int i=0;i<3;i++) {
			for (int j=0;j<3;j++) {
				/*check if the value of the x and y coordinate are within the range of map size*/
				if(x_coords[i] > 0 && x_coords[i] <= World.MAP_WIDTH) {
					if(y_coords[j]>0 && y_coords[j] <= World.MAP_HEIGHT) {
						/*do not include the input coordinate as its own neighbour*/
						if(x_coords[i] != x && y_coords[j] != y) {
							neighbours.add(new Coordinate(x_coords[i],y_coords[j]));
						}
					}
				}
			}
		}
		
		return neighbours;
	}

}
