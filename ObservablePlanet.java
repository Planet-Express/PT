package pt;

import java.util.Observable;

/******************************************************
 * Instance třídy {@code ObservablePlanet} představuje
 * pozorovanou planetu.
 * 
 * @author Michal Štrunc a Jakub Váverka
 *
 */
public class ObservablePlanet extends Observable {
	
	/** poorovaná planeta*/
	private Planeta planeta;
	
	/************************************
	 * vytvoří zadanou planetu a upozorní
	 * pozorovatele
	 * 
	 * @param p planeta
	 */
	public ObservablePlanet(Planeta p){
		this.planeta = p;
		notifyObservers(p);
	}

	/************************************
	 * vrátí aktuální poorovanou planetu
	 *
	 * @return instance tžídy {@code Planeta}
	 */
	public Planeta getPlaneta() {
		return planeta;
	}

	/*****************************************
	 * nastaví pozorovanou planetu na novou
	 * 
	 * @param planeta zadaná planeta
	 */
	public void setPlaneta(Planeta planeta) {
		this.planeta = planeta;
		setChanged();
		notifyObservers(planeta);
	}

}
