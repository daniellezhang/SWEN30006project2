package mycontroller;
import java.util.ArrayList;

public class MoveHistory {
	
	private ArrayList<CarMove> moves;
	
	
	public MoveHistory() {
		this.moves = new ArrayList<CarMove>();
	}
	
	public void addMove(CarMove m) {
		this.moves.add(m);
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
