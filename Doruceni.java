package pt;

/*******************************************************
 * Instance třídy {@code Doruceni} představjí
 * přepravku pro uchování dat o doruceni.
 * 
 * @author Michal Štrunc a Jakub Váverka
 *
 */
public class Doruceni {
	
	/** množství doručených léků*/
	private final int kolik;
	
	/** lod, která doručila objednávku*/
	private final Lod lod;
	
	/** den doručení*/
	private final int den;
	
	/*************************************************
	 * vytvoří instanci zadaného množštví léků, 
	 * doručovací lodí a dnem doručení.
	 * 
	 * @param kolik množství doručených léků
	 * @param l lod přepravující náklad
	 * @param den den doručení
	 */
	public Doruceni(int kolik, Lod l, int den){
		this.kolik = kolik;
		this.lod = l;
		this.den = den;
	}

	/***********************************************
	 * vrátí množství doručených léků
	 * 
	 * @return množství léků
	 */
	public int getKolik() {
		return kolik;
	}

	/***********************************************
	 * vrátí instanci třídy {@code Lod},  která
	 * představuje doručující lodl
	 *
	 * @return lod, která doručila náklad
	 */
	public Lod getLod() {
		return lod;
	}

	/**********************************************
	 * vrátí den doručení léků
	 * 
	 * @return den doručení
	 * 
	 */
	public int getDen() {
		return den;
	}	
}
