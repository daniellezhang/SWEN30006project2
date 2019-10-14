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
	
	

}
