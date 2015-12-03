package pt;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.Label;

/*************************************************************
 * Instance třídy {@code ObservingLabel} představují
 * způsob zobrazení pozorujícího popisku
 * 
 * @author Michal Štrunc a Jakub Váverka
 *
 */
public class ObservingLabel extends Label implements Observer{

	/**********************************************
	 * aktualizace dat
	 * 
	 * @param o pozorovatelný
	 * @param arg pozorovaný objekt
	 */
	@Override
	public void update(Observable o, Object arg) {
		setText(((Planeta) arg).getJmeno() + "");
		
	}

}
