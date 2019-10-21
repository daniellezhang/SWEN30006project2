package mycontroller;

import tiles.MapTile;

public class CoordinateRecord {
	
	private TileStatus reachable;
	private MapTile tile;
	private boolean isVisited;
	public CoordinateRecord(MapTile tile) {
		this.tile = tile;
		this.reachable = TileStatus.UNKNOW;
		this.isVisited = false;
	}
	
	public void setIsVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	
	public boolean getIsVisited() {
		return this.isVisited;
	}
	
	public void setReachable(TileStatus status) {
		this.reachable = status;
	}
	public TileStatus getReachable() {
		return this.reachable;
	}
}
