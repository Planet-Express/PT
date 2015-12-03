package pt;

import java.util.Stack;

/*************************************************
 * Instance třídy {@code Stanice} představují
 * planety, na kterých se vyrábí léky, které 
 * se z nich pomocí lodí rozesílají 
 * 
 * @author Michal Štrunc a Jakub Váverka
 *
 */
public class Stanice extends Planeta{

	/** zásobník lodí v doku*/
	private final Stack<Lod> dok = new Stack<Lod>();

	/***********************************************
	 * vytvoří stanici zavoláním konstruktoru
	 * předka, kterému se předá zadané id a
	 * souřadnice, populace se nastaví na 0.
	 * 
	 * @param id id stanice
	 * @param x x-ov souřadnice
	 * @param y y-ová souřadnice
	 */
	public Stanice(int id, int x, int y){
		super(id, x, y, 0);
	}
	
	/******************************************
	 * vrátí zásobní lodí v doku
	 * 
	 * @return zásoník lodí
	 */
	public Stack<Lod> getDok() {
		return dok;
	}

	
	
	
}
