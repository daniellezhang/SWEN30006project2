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
		
		Graph g =new Graph(MemoryMap.getMemoryMap());
		boolean isFound = false;
		ArrayList<Coordinate> newPath = new ArrayList<Coordinate>();
		for(int i=0; i < path.size();i++) {
			if(isFound == true) {
				newPath.add(path.get(i));
			}
			if(path.get(i).equals(sensor.getCoordinate())) {
				isFound = true;
				newPath.add(path.get(i));
			}
		}
		System.out.println(sensor.getPosition()+' '+path.toString());
		path = newPath;
		System.out.println(sensor.getPosition()+' '+path.toString());

		if(path.size() <=1||target == null ) {

			path = g.boundaryCoordinates(sensor.getCoordinate());
			
		}
		if(path !=null && path.size()> 1) {
			target = path.get(path.size()-1);
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
