package mycontroller;

import tiles.MapTile;

public class CoordinateRecord {
	
	private boolean reachable;
	private MapTile tile;
	
	public CoordinateRecord(MapTile tile) {
		this.tile = tile;
		this.reachable = true;
	}
	
	public void setUnreachable() {
		this.reachable = false;
	}
	public boolean getReachable() {
		return this.reachable;
	}
}
