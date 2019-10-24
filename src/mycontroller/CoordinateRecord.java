package mycontroller;


import tiles.MapTile;
import tiles.MapTile.Type;
import tiles.ParcelTrap;
import tiles.TrapTile;

public class CoordinateRecord {

	private TileStatus reachable;
	private MapTile tile;
	private boolean isVisited;
	public CoordinateRecord(MapTile tile) {
		this.tile = tile;
		this.reachable = TileStatus.UNKNOWN;
		this.isVisited = false;
	}

	public void setIsVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	public boolean getIsVisited() {
		return this.isVisited;
	}

	public void setTile(MapTile tile) {
		this.tile = tile;
	}

	public void setReachable(TileStatus status) {
		this.reachable = status;
	}
	public TileStatus getReachable() {
		return this.reachable;
	}

	public MapTile getMapTile() {
		return this.tile;
	}

	public boolean canWalkThrough() {

		if (!tile.isType(Type.WALL)) {
			
			// if the tile is not a wall, but is a non-parcel trap tile, return false
			if (tile instanceof TrapTile && !(tile instanceof ParcelTrap)) {
				return false;
			}
			
			return true;
		}
		
		// return false for walls
		return false;
		
	}
	
	public boolean isWall() {
		
		return (tile.isType(Type.WALL));
		
	}

}
