package mycontroller;

public class CarStrategyManager extends CompositeCarStrategy {
	private static CarStrategyManager manager;
	private CarStrategyManager() {
		super("strategyManager");
	}
	
	public static CarStrategyManager getCarStrategyManager() {
		if (manager == null) {
			manager = new CarStrategyManager();
		}
		return manager;
	}
	
	
	@Override
	public CarMove decideMove(Sensor sensor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

}
