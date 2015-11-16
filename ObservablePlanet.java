package PT;

import java.util.Observable;

public class ObservablePlanet extends Observable {
	
	private String planeta;
	
	public ObservablePlanet(String p){
		this.planeta = p;
		notifyObservers(p);
	}

	public String getPlaneta() {
		return planeta;
	}

	public void setPlaneta(String planeta) {
		this.planeta = planeta;
		setChanged();
		notifyObservers(planeta);
	}

}
