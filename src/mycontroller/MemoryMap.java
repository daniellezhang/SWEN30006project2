package mycontroller;
import java.util.HashMap;
import utilities.Coordinate;
import tiles.*;
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

}
