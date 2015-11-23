package PT;

import java.util.Observable;

public class ObservablePlanet extends Observable {
	
	private Planeta planeta;
	
	public ObservablePlanet(Planeta p){
		this.planeta = p;
		notifyObservers(p);
	}

	public Planeta getPlaneta() {
		return planeta;
	}

	public void setPlaneta(Planeta planeta) {
		this.planeta = planeta;
		setChanged();
		notifyObservers(planeta);
	}

}
