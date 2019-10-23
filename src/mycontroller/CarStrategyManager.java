package mycontroller;
import java.util.HashMap;
public class CarStrategyManager extends CompositeCarStrategy {
	private static CarStrategyManager manager;
	private String currentStrategy;

	private CarStrategyManager() {
		super("strategyManager");
	}

	public static CarStrategyManager getCarStrategyManager() {
		if (manager == null) {
			manager = new CarStrategyManager();
			manager.addStrategy(new ExploreStrategy());
			manager.addStrategy(new TargetStrategy());
			manager.addStrategy(new MoveAlongWallStrategy());
			manager.addStrategy(new ExploreBoundaryStrategy());
			manager.currentStrategy = "explore";
		}
		return manager;
	}


	@Override
	public CarMove decideMove(Sensor sensor) {
		
		boolean seenFinishTile = (MemoryMap.getMemoryMap().getFinish().size() > 0);
		
		// if we've seen a parcel, do target strategy
		if (MemoryMap.getMemoryMap().getParcels().size() > 0) {
			
			setCurrentStrategy("target");
		
		}
		
		
		else if (sensor.enoughParcels()) {
			// if we have enough parcels AND we've seen a finish tile, do target strategy
			if(seenFinishTile) {
				setCurrentStrategy("target");
			}
			else {
				setCurrentStrategy("explore_boundary");
			}
		
		}
		// if the vehicle is on a tile that has been visited, check if it is traveling in a circle 
		else if(MemoryMap.getMemoryMap().getCoordinateRecord(sensor.getCoordinate()) != null) {
			if(MemoryMap.getMemoryMap().getCoordinateRecord(sensor.getCoordinate()).getIsVisited()) {
				if(MoveHistory.getMoveHistory().isInLoop()||MoveHistory.getMoveHistory().isBackAndForward()) {
					setCurrentStrategy("explore_boundary");
					
				}
				else {
					setCurrentStrategy("explore");
				}
			}
			else {
				setCurrentStrategy("explore");
			}
		}
		// otherwise keep exploring
		else {
			if(currentStrategy != "explore_boudnary") {
			setCurrentStrategy("explore");
			}
		}

		CarStrategy strategy = manager.getBaseStrategy().get(currentStrategy);
		
		CarMove move = strategy.decideMove(sensor);
		
		// if we're braking (no path available), just do explore.
		
		if (move == CarMove.BRAKE) {
			if(currentStrategy == "target") {
				setCurrentStrategy("explore_boundary");
			}
			else {
				setCurrentStrategy("explore");
			}

			return manager.getBaseStrategy().get(currentStrategy).decideMove(sensor);
		}
		
		return move;
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	public void setCurrentStrategy(String name) {
		if(manager.getBaseStrategy().get(name) != null) {
			manager.currentStrategy = name;
		}
	}



}
