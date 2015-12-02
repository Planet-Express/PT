package pt;

import java.util.Stack;

/********************************************************************
 * Instance třídy {@code Lod} představují
 * loď, která zásobuje planety léky, které
 * se vyrábí na stanicích.
 * 
 * @author Michal Štrunc a Jakub Váverka
 *
 */
public class Lod {

	/** x-ová souřadnice pozice*/
	private int posX;
	
	/** y-ová souřadnice pozice*/
	private int posY;
	
	/** velikost nákladu*/
	private int naklad;

	/** stav lodi*/
	private int stav = -1;
	
	/** id lodi*/
	private int id;
	
	/** stanice, pod kterou lod spadá*/
	private final Stanice start;
	
	/** uleťená část cesty*/
	private double procentaCesty;
	
	/** kde se lod nachází*/
	private Object lokace;
	
	/** zásobovaná planeta*/
	private Planeta chciNa;
	
	/** zásobník cílových planet*/
	private final Stack<Planeta> cil = new Stack<Planeta>();
	
	/** rozpis objednávek pro cílové planety*/
	private Stack<Integer> rozpis = new Stack<Integer>();
	
	/** jestli je lod pouzita*/
	private boolean pouzita = false;
	
	/***************************************************************
	 * vytvoří lod se zadanou stanicí a
	 * id. Pozice a lokace se nastaví
	 * na stanici a lod se vloží do
	 * zádobníku stanice.
	 * 
	 * @param start příslušná stanice
	 * @param id id lodě
	 */
	public Lod(Stanice start, int id){
		this.id = id;
		this.posX = start.getPosX();
		this.posY = start.getPosY();
		this.lokace = start;
		this.start = start;
		start.getDok().push(this);
	}
	
	/*****************************************************
	 * je loď použita
	 * 
	 * @return pouziti lodě
	 */
	public boolean getPouzita(){
		return this.pouzita;
	}
	
	/*****************************************************
	 * nastav pouziti lodě
	 * 
	 * @param flag pouzita
	 */
	public void setPouzita(boolean flag){
		this.pouzita = flag;
	}
	
	/***************************************************
	 * vrátí id lodě
	 * 
	 * @return id lodě
	 */
	public int getId() {
		return id;
	}

	/***************************************************
	 * vratí stav lodě
	 * 
	 * @return stav lodě
	 */
	public int getStav() {
		return stav;
	}

	/****************************************************************
	 * spočítá, jestli loď stihne obsloužit
	 * objednávku
	 * 
	 * @param ob instance třídy {@code Objednávka}
	 * @param den aktuální den
	 * 
	 * @return vrátí, jestli je podmínka splněna
	 */
	public boolean stihne(Objednavka ob, int den){
		int cas = (int)(ob.getVzdalenost()/25 + cil.size()*2+2);
		boolean podminka = true;
		if(cas+den%30>=30){
			podminka = false;
		}
		return podminka;
	}
	
	/**************************************************************
	 * nastaví stav na požadovaný
	 * 
	 * @param stav požadovaný stav
	 */
	public void setStav(int stav) {
		this.stav = stav;
	}


	/***************************************************************
	 * vrátí x-ovou sořadnici pozice lodě 
	 * 
	 * @return vrátí x-ovou sořadnici
	 */
	public int getPosX() {
		return posX;
	}

	/**************************************************************
	 * nastaví x-ouvou sořadnici pozice
	 * lodě na požadovanou
	 * 
	 * @param posX požadovaná souřadnice x
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/***************************************************************
	 * vrátí y-ovou sořadnici pozice lodě 
	 * 
	 * @return vrátí y-ovou sořadnici
	 */
	public int getPosY() {
		return posY;
	}

	/**************************************************************
	 * nastaví x-ouvou sořadnici pozice
	 * lodě na požadovanou
	 * 
	 * @param posX požadovaná souřadnice x
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	/******************************************************
	 * vrátí velikost nákladu lodě
	 * 
	 * @return velikost nákladu
	 */
	public int getNaklad() {
		return naklad;
	}

	/*****************************************************
	 * nastaví velikost nákladu na 
	 * požadovanou hodnotu
	 * 
	 * @param naklad požadovaná velikost nákladu
	 */
	public void setNaklad(int naklad) {
		this.naklad = naklad;
	}

	/**************************************************
	 * vrátí instanci třídy {@code Object} představují
	 * aktuální lokaci lodě
	 * 
	 * @return lokace lodi
	 */
	public Object getLokace() {
		return lokace;
	}

	/***************************************************
	 * nastaví lokaci lodě na požadovanou
	 * lokaci
	 * 
	 * @param lokace požadovaná lokace
	 */
	public void setLokace(Object lokace) {
		if(lokace==null){
			System.out.println(id);
		}
		this.lokace = lokace;
	}

	/************************************************************************
	 * vrátí zásobník cílových planet
	 * 
	 * @return zásobník planet
	 */
	public Stack<Planeta> getCil() {
		return cil;
	}
	
	/*******************************************************************
	 * vypíše informace o lodi do Stringu
	 * 
	 * @return popis lodi
	 */
	public String toString(){
		return "id = " + getId() + ", size = " + getCil().size() + 
		", naklad = " + getNaklad() + ", stav = " + getStav();
	}

	/****************************************************************
	 * vrátí aktuální zásobovanou planetu
	 * 
	 * @return zásobovaná planeta
	 */
	public Planeta getChciNa() {
		return chciNa;
	}
	
	/****************************************************************
	 * vrátí výchozí stanici
	 * 
	 * @return výchozí stanice
	 */
	public Stanice getStart(){
		return start;
	}

	/*******************************************************************************************************
	 * nastaví cíl letu na planetu na vrcholu zásobníku 
	 */
	public void setChciNa() {
		if(this.lokace instanceof Planeta){
			if(this.cil.size()!=0){
				if(this.cil.peek().getCesta().get(0)==(Planeta)this.lokace){this.chciNa = this.cil.peek();}
				else{
					for (int i = this.cil.peek().getCesta().size()-1; i >= 0; i--) {
						if(this.cil.peek().getCesta().get(i)==(Planeta)this.lokace && (i-1) >= 0){
							this.chciNa = this.cil.peek().getCesta().get(i-1);	
						}
					}
				}
			}else{
				if(((Planeta) lokace).getId()>5000){
					this.chciNa = start;
					this.lokace = start;
				}
				else{
					this.chciNa=((Planeta)lokace).getCesta().get(0);
				}
			}
		}
	}
	
	/*************************************************************
	 * vymaže informace o cestě
	 */
	public void resetLod(){
		cil.clear();
		rozpis.clear();
		naklad = 0;
	}

	/*********************************************************
	 * vrátí uraženou část cesty v procentech
	 * 
	 * @return uražená část cesty
	 */
	public double getProcentaCesty() {
		return procentaCesty;
	}

	/*******************************************************
	 * nastaví hodnotu uražený části cesty
	 * na zadanou
	 * @param procentaCesty uražená část cesty
	 */
	public void setProcentaCesty(double procentaCesty) {
		this.procentaCesty = procentaCesty;
	}

	/******************************************************
	 * vrátí zásobník množství
	 * léků pro jednotlivé planety
	 * 
	 * @return zásobník množství léků
	 */
	public Stack<Integer> getRozpis() {
		return rozpis;
	}

	
	

}
