package mycontroller;

import tiles.MapTile;

public class CoordinateRecord {
	private TileStatus reachable;
	private MapTile tile;
	
	public CoordinateRecord(MapTile tile) {
		this.tile = tile;
		this.reachable = TileStatus.UNKONW;
	}
	
	public void setUnreachable(TileStatus status) {
		this.reachable = status;
	}
	public TileStatus getReachable() {
		return this.reachable;
	}
}
