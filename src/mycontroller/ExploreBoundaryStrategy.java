package mycontroller;
import utilities.Coordinate;
import java.util.*;

public class ExploreBoundaryStrategy extends TargetStrategy {
	private String name = "explore_boundary";
	private List<Coordinate> path;
	private Coordinate target;
	
	public ExploreBoundaryStrategy() {
		path = new ArrayList<Coordinate>();
	}
	public CarMove decideMove(Sensor sensor) {
		// generate a graph
		System.out.println(getName());
		System.out.println(path.toString());
		Graph g = new Graph(MemoryMap.getMemoryMap());
		if(path.size() != 0) {
			path = g.BFS(sensor.getCoordinate(), target);
		}
		else {
			path = g.furtherestCoordinates(sensor.getCoordinate());
			if(path != null && path.size()>0) {
				target = path.get(path.size()-1);
			}
			
		}

		return getNextMove(path,sensor.getOrientation());
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
