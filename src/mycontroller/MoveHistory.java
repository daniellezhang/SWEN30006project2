package mycontroller;
import java.util.ArrayList;
import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;

public class MoveHistory implements Observer{
	
	private ArrayList<CarMove> moves;
	private static MoveHistory moveHistory;
	
	private MoveHistory() {
		this.moves = new ArrayList<CarMove>();

	}
	public static MoveHistory getMoveHistory() {
		if(moveHistory == null) {
			moveHistory = new MoveHistory();
		}
		return moveHistory;
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
	
	public boolean isInLoop() {
		int i = moves.size()-1, nTurns = 0;
		CarMove direction = null;
		/*back tracking past moves to check if the vehicle is travelling in a circle*/
		while(i>=0) {
			CarMove currentMove = moves.get(i);
			if(direction == null && (currentMove == CarMove.LEFT || currentMove == CarMove.RIGHT)) {
				direction = currentMove;
				nTurns += 1;
			}
			else if(direction != null && (currentMove == CarMove.LEFT || currentMove == CarMove.RIGHT)) {
				if(currentMove==direction) {
					nTurns += 1;
				}
				else {
					return false;
				}
			}
			/*the vehicle has made 7 consecutive turns that are the same. it is moving in a loop */
			if(nTurns == 7) {
				return true;
			}
			i-=1;
		}
		
		return false;
	}

}
