package pt;

/*************************************************
 * Instance třídy {@code Cesta} představují
 * cestu mezi dvěma planetami.
 * 
 * @author Michal Štrunc a Jakub Váverka
 *
 */
public class Cesta{
	
	/** výchozí planeta cesty*/
	private final Planeta od;
	
	/** cílová planeta cesty*/
	private final Planeta kam;
	
	/** jestli na cestě hrozí přepadení*/
	private boolean nebezpecna = false;

	/********************************************
	 * yytvoří instanci, která má zadanou
	 * počátečná a cílovou planetu.
	 * 
	 * @param od výchozí planeta
	 * @param kam cílová planeta
	 */
	public Cesta(Planeta od, Planeta kam){
		this.od = od;
		this.kam = kam;
	}
	
	/******************************************
	 * nastaví cestu na bezpečnou, 
	 * nebo nebezpečnou.
	 * 
	 * @param b je cesta nebezpečná
	 */
	public void setNebezpeci(boolean b){
		this.nebezpecna =  b;
	}
	
	/******************************************
	 * vrátí jestli je cesta nebezpečná
	 * 
	 * @return jestli je cesta nebezpečná
	 */
	public boolean isNebezpecna(){
		return nebezpecna;
	}
	
	/******************************************
	 * vrátí výchozí planetu
	 * 
	 * @return výchozí planeta
	 */
	public Planeta getOd(){
		return od;
	}
	
	/******************************************
	 * vrátí cílovou planetu
	 * 
	 * @return cíová planeta
	 */
	public Planeta getKam(){
		return kam;
	}
}
