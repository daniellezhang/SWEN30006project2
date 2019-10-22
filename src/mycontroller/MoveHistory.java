package mycontroller;
import java.util.ArrayList;
import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;

public class MoveHistory implements Observer{
	
	private ArrayList<CarMove> moves;
	
	
	public MoveHistory() {
		this.moves = new ArrayList<CarMove>();
	}
	
//	public void addMove(CarMove m) {
//		this.moves.add(m);
//	}
	
	@Override
	public void respondEvent(HashMap<Coordinate, MapTile> currentView, CarMove move,Coordinate currentCoordinate) {
		this.moves.add(move);
	}
	
	//look up the ith last move of the car
	public CarMove getLastMove(int i) {
		int index = moves.size()-i;
		if(index >= 0) {
			return moves.get(index);
		}
		return null;
	}
	

}
