package mycontroller;

import tiles.MapTile;

public class CoordinateRecord {
	
	private boolean reachable;
	private MapTile tile;
	private boolean isVisited;
	public CoordinateRecord(MapTile tile) {
		this.tile = tile;
		this.reachable = true;
		this.isVisited = false;
	}
	
	public void setIsVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	
	public boolean getIsVisited() {
		return this.isVisited;
	}
	
	public void setUnreachable() {
		this.reachable = false;
	}
	public boolean getReachable() {
		return this.reachable;
	}
}
