package mycontroller;
import java.util.HashMap;
public abstract class CompositeCarStrategy implements CarStrategy {
	private HashMap<String,CarStrategy> baseStrategy;
	private String name;
	
	public CompositeCarStrategy(String name) {
		this.name = name;
		this.baseStrategy = new HashMap<String,CarStrategy>();
	}

	//add strategy in the composite strategy
	public void addStrategy(CarStrategy strategy) {
		if(baseStrategy.getOrDefault(strategy.getName(), null) == null) {
			baseStrategy.put(strategy.getName(), strategy);
		}
	}

	public HashMap<String,CarStrategy> getBaseStrategy() {
		return baseStrategy;
	}
	
	public String getName() {
		return name;
	}
}
