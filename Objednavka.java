package pt;

/*****************************************************************************
 * Instance třídy {@code Objednavka} představují
 * objednávku léků planety.
 * 
 * @author Michal Štrunc a Jakub Váverka
 *
 */
public class Objednavka {

	/** planeta doručení*/
	private final Planeta kam;
	
	/** výchozí stanice*/
	private final Stanice od;
	
	/** potřeba léků*/
	private int kolik;
	
	/** vyrobeno léků*/
	private int potencial = 0;
	
	/** vzdálenost stanice a planety*/
	private final double vzdalenost;
	
	/** původní velkost objednávka*/
	private int puvodni;
	
	/*******************************************************************************
	 * vytvoří objednávku z zadaný planety od
	 * příslušné stanice o zadaném množství léků
	 * a vzdálenosti mezi planetou a stanicí.
	 * 
	 * @param kam zásobovaná planeta
	 * @param od výchozí stanice
	 * @param kolik počet léků
	 * @param vzdalenost planety a stanice
	 */
	public Objednavka(Planeta kam, Stanice od, int kolik, double vzdalenost){
		this.kam = kam;
		this.od = od;
		this.kolik = kolik;
		this.vzdalenost = vzdalenost;
		this.setPuvodni(kolik);
	}
	
	/*************************************************************************
	 * vrátí instanci třídy{@code Stanice}, 
	 * odkud je vyslána loď
	 * 
	 * @return instance stanice
	 */
	public Stanice getOd() {
		return od;
	}

	/**************************************************************
	 * vrátí potřebu léků, které ještě
	 * nybyli vytvořeny.
	 * 
	 * @return potřeba léků
	 */
	public int getPotreba(){
		return this.kolik - this.potencial;
	}

	/*************************************************************
	 * vrátí potenciál, což je
	 * množství již vyrobených léků
	 * objednávky
	 * 
	 * @return množství vyrobených léků
	 */
	public int getPotencial() {
		return potencial;
	}

	/**********************************************
	 * nastaví potenciál na zadanou
	 * hodnotu
	 * 
	 * @param potencial množství vyrobených léků
	 */
	public void setPotencial(int potencial) {
		this.potencial = potencial;
	}

	/*********************************************
	 * vrátí instanci třídy {@code Planeta},
	 * místo doručení objednávky
	 * 
	 * @return instance planeta
	 */
	public Planeta getKam() {
		return kam;
	}
	
	/********************************************************
	 * vrátí množství aktuálně objednaných léků
	 * 
	 * @return množství objednaných léků
	 */
	public int getKolik() {
		return kolik;
	}
	
	/*****************************************************
	 * nastaví množství aktuálně objednaných léků
	 * na zadanou hodnotu
	 * 
	 * @param kolik množství objednaných léků
	 */
	public void setKolik(int kolik) {
		this.kolik = kolik;
	}
	
	/*************************************************
	 * vrátí vzdálenost planety od stanice
	 * 
	 * @return vzdálenost planety astanice
	 */
	public double getVzdalenost() {
		return vzdalenost;
	}

	/************************************************
	 * vrátí původní množství objednaných léků
	 * 
	 * @return množství objednaných léků
	 */
	public int getPuvodni() {
		return puvodni;
	}

	/************************************************
	 * nastaví původní množství objednaných
	 * léků na zadanou hodnotu 
	 * 
	 * @param puvodni množství objednaných léků
	 */
	private void setPuvodni(int puvodni) {
		this.puvodni = puvodni;
	}
	
	
}
