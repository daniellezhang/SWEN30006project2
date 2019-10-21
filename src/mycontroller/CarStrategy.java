package mycontroller;

import java.util.HashMap;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;


public interface CarStrategy {
/*interface for strategy to decide the movement of the car*/
	/*method to return string that correspond to the move of the car*/
	int wallSensitivity = 1;
	public CarMove decideMove(Sensor sensor);
	
	public String getName();
	
	public default boolean checkWallAhead(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView, Coordinate currentCoordinate){
		switch(orientation){
		case EAST:
			return checkEast(currentView, currentCoordinate);
		case NORTH:
			return checkNorth(currentView, currentCoordinate);
		case SOUTH:
			return checkSouth(currentView, currentCoordinate);
		case WEST:
			return checkWest(currentView, currentCoordinate);
		default:
			return false;
		}
	}
	public default boolean checkFollowingWall(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView, Coordinate currentCoordinate) {
		
		switch(orientation){
		case EAST:
			return checkNorth(currentView, currentCoordinate);
		case NORTH:
			return checkWest(currentView, currentCoordinate);
		case SOUTH:
			return checkEast(currentView, currentCoordinate);
		case WEST:
			return checkSouth(currentView, currentCoordinate);
		default:
			return false;
		}	
	}
	//give the coordinate that is adjacent to the current coordinate that is in the given orientation
	public default Coordinate getNeighbourCoordinate(WorldSpatial.Direction neighbourOrientation, Coordinate currentCoordinate) {
		
		switch(neighbourOrientation){
		case EAST:
			return eastNeighbour(currentCoordinate);
		case NORTH:
			return northNeighbour(currentCoordinate);
		case SOUTH:
			return southNeighbour(currentCoordinate);
		case WEST:
			return westNeighbour(currentCoordinate);
		default:
			return null;
		}	
	}
		
	
	
	public default boolean checkEast(HashMap<Coordinate, MapTile> currentView, Coordinate currentCoordinate){
		// Check tiles to my right
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentCoordinate.x+i, currentCoordinate.y));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	
	public default boolean checkWest(HashMap<Coordinate,MapTile> currentView, Coordinate currentCoordinate){
		// Check tiles to my left
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentCoordinate.x-i, currentCoordinate.y));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	public default boolean checkNorth(HashMap<Coordinate,MapTile> currentView, Coordinate currentCoordinate){
		// Check tiles to towards the top
		
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentCoordinate.x, currentCoordinate.y+i));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	public default boolean checkSouth(HashMap<Coordinate,MapTile> currentView, Coordinate currentCoordinate){
		// Check tiles towards the bottom
		
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentCoordinate.x, currentCoordinate.y-i));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	//return the adjacent coordinate in East direction
	public default Coordinate eastNeighbour(Coordinate currentCoordinate) {
		return new Coordinate(currentCoordinate.x+1, currentCoordinate.y);
	}

	//return the adjacent coordinate in West direction
	public default Coordinate westNeighbour(Coordinate currentCoordinate) {
		return new Coordinate(currentCoordinate.x-1, currentCoordinate.y);
	}	
	//return the adjacent coordinate in North direction
	public default Coordinate northNeighbour(Coordinate currentCoordinate) {
		return new Coordinate(currentCoordinate.x, currentCoordinate.y+1);
	}

	//return the adjacent coordinate in South direction
	public default Coordinate southNeighbour(Coordinate currentCoordinate) {
		return new Coordinate(currentCoordinate.x, currentCoordinate.y-1);
	}	
}
