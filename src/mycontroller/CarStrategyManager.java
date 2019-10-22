package mycontroller;
import java.util.HashMap;
public class CarStrategyManager extends CompositeCarStrategy {
	private static CarStrategyManager manager;
	private String currentStrategy;
	private int numMoves = 0;

	private CarStrategyManager() {
		super("strategyManager");
	}

	public static CarStrategyManager getCarStrategyManager() {
		if (manager == null) {
			manager = new CarStrategyManager();
			manager.addStrategy(new ExploreStrategy());
			manager.addStrategy(new TargetStrategy());
			manager.currentStrategy = "explore";
		}
		return manager;
	}


	@Override
	public CarMove decideMove(Sensor sensor) {

		if (numMoves > 40) {
			setCurrentStrategy("target");
		}

		numMoves += 1;

		CarStrategy strategy = manager.getBaseStrategy().get(currentStrategy);
		CarMove move = strategy.decideMove(sensor);
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
