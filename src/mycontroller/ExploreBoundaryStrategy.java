package mycontroller;
import utilities.Coordinate;
import java.util.*;

public class ExploreBoundaryStrategy extends TargetStrategy {
	private String name = "explore_boundary";
	private Coordinate target;
	
	
	public ExploreBoundaryStrategy() {
	}
	
	public CarMove decideMove(Sensor sensor) {
		
		System.out.println(this.getName());
		
		Graph g = Graph.getGraph();
		
		
		// reset target if we've gotten there
		if (target == null || sensor.getCoordinate().equals(target)) {
			 target = g.closestUnvisitedLeaf(sensor.getCoordinate());
		}
		
		// if it's non null, return a path
		if (target != null) {
			List<Coordinate> path = g.BFS(sensor.getCoordinate(), target);
			System.out.println(path);
			return getNextMove(path,sensor.getOrientation());
		}
		
		return CarMove.BRAKE;
		
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
