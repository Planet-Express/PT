package pt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/***********************************************************************
 * Instance třídy {@code Galaxie} představují
 * galaxii, která obsahuje planety, které jsou 
 * zásobovány léky pomocí lodí ze stanic,
 * kde se léky vyrábí. Lodě létají po nejkratších
 * cestách, po cestě mohou být přepadeny, při
 * přepadení přichází o náklad. 
 * 
 * @author Michal Štrunc a Jakub Váverka
 *
 */
public class Galaxie{
	
	/** seznam planet*/
	private final List<Planeta> planety = new ArrayList<Planeta>(5005);
	
	/** seznam stanic*/
	private final List<Stanice> stanice = new ArrayList<Stanice>(5);
	
	/** seznam cest*/
	private final List<Cesta> cesty = new ArrayList<Cesta>();
	
	/** počet planet*/
	private final int pocet;
	
	/** rozměr galaxie (rozmer*rozmer)*/
	private final int rozmer;
	
	/** odsazení stanic od stran*/
	private final int ODSAZENI = 160;
	
	/** čas na měření času*/
	public long time = System.nanoTime();
	
	/**********************************************************************
	 * vytvoří galaxii o zadaném rozměru a
	 * počtu planet, 5 stanic je pevně
	 * rozmístěno.
	 * 
	 * @param rozmer rozměr galaxie
	 * @param pocet pocet planet
	 */
	public Galaxie(int rozmer, int pocet){
		this.pocet = pocet;
		this.rozmer = rozmer;
	}
	
	/************************************************************************
	 * má nastarosti vygenerování celé galaxie.
	 * Začne s vytvářením planet, poté vytvoří
	 * stanice a dohledá planetám sousedy.
	 * Mezi sousedy vytvoří cesty a náhodné
	 * označí za nebezpečné. Nakonec najde
	 * nejkratší cestu os planety k stanici.
	 */
	public void generujVesmir(){
		vytvorStanice();
		System.out.println(System.nanoTime()-time+" -- Vytvoreni planet");
		for (int i = 1; i <= pocet; i++) {
			planety.add(vytvorPlanetu(i));
		}
		System.out.println(System.nanoTime()-time+" -- Vytvoreni stanic");
		for (int i = 0; i < stanice.size(); i++) {
			planety.add(stanice.get(i));
		}
		dohledejSousedy(planety);
		System.out.println(System.nanoTime()-time+" -- Dohledavam sousedy");
		vytvorCesty(planety);
		ostatniSousedi();
		generujNebezpecneCesty();
		System.out.println(System.nanoTime()-time+" -- Pruchod Dijkstrem");
		udelejDijkstra();
		System.out.println(System.nanoTime()-time+" -- Vykresluji");
	}
	

	/************************************************************************
	 * vytvoří planetu o zadaném id,
	 * na náhodně vygenerované pozici, které je od 
	 * nejbližší planety nejblíže ve vzdálenosti 2.
	 * Populace planety se generuje pomocí Gaussovi
	 * funkce.
	 * 
	 * @param id id planety
	 * 
	 * @return insttance třídy {@code Planeta} 
	 */
	private Planeta vytvorPlanetu(int id){
		int x = -1;
		int y = -1;
		boolean lze = false;
		int counter = 0;
		while(!lze&&counter<100){
			counter++;
			x = (int) (Math.random()*801);
			y = (int) (Math.random()*801);
			
			lze = testRozlozeni(x, y);
			if(counter==99){
				System.out.println("Planeta "+id+" se do galaxie nevesla");
			}
		}
		
		int populace = generujPopulaci();
		Planeta p = new Planeta(id,x,y,populace);
		return p;
	}
	
	/************************************************************************
	 * testuje zda je planeta v povolené vzdálenosti
	 * od ostatních
	 * 
	 * @param x x-ová souřadnice
	 * @param y y-ová souřadnice
	 * @return je planeta na volných souřadnicích
	 */
	private boolean testRozlozeni(int x, int y) {
		boolean lze = false;
		if(planety.size()==0){
			lze = true;
			}
		for (int i = 0; i < planety.size(); i++) {
			double vzdalenost = vzdalenostBodu(planety.get(i), x, y);
			if(vzdalenost<=3){
				break;
				}
			
			if((i+1)==planety.size()){
				lze = true;
				}
		}
		//Kontrola se stanicemi
		for (int j = 0; j < stanice.size(); j++) {
			double vzdalenostStanice = vzdalenostBodu(stanice.get(j),x,y);
			if(vzdalenostStanice<=4){
				lze = false;
				break;
			}
		}
		return lze;
	}

	/***************************************************************************
	 * vytvoří 5 stanic, rovnoměrně rozložených
	 * b galaxii.
	 * 
	 */
	private void vytvorStanice() {
		stanice.add(new Stanice(5001, ODSAZENI, ODSAZENI));
		stanice.add(new Stanice(5002, rozmer - ODSAZENI, ODSAZENI));
		stanice.add(new Stanice(5003, ODSAZENI, rozmer - ODSAZENI));
		stanice.add(new Stanice(5004, rozmer - ODSAZENI, rozmer - ODSAZENI));
		stanice.add(new Stanice(5005, (int)(rozmer/2.0), (int)(rozmer/2.0)));
	}

	/*****************************************************************************************
	 * dohledá každé planetě galaxie min. 5
	 * nejbližších sousedů
	 * 
	 * @param planety seznam planet
	 */
	private void dohledejSousedy(List<Planeta> planety){
		for (int i = 0; i < planety.size(); i++) {
			Planeta a = planety.get(i);
			for (int j = 0; j < planety.size(); j++) {
				if(i!=j){
					Planeta b = planety.get(j);
					List<Planeta>sousedi = a.getSousedi();
					double vzdalenost = vzdalenostPlanet(a, b);
					for(int l = 0; l < 5; l++){						
						sousedi.add(new Planeta(0, Integer.MAX_VALUE, Integer.MAX_VALUE, 0));
					}
					for (int k = 0; k < sousedi.size(); k++) {
						if(vzdalenost <= vzdalenostPlanet(a, sousedi.get(k))){
							sousedi.add(k, b);
							a.trimSousedi();
							sousedi = a.getSousedi();
							break;
						}
					}					
				}
			}
		}
	}
	
	/****************************************************************************
	 * vytvoří cesty mezi sousedy všech planet
	 * galaxie
	 * 
	 * @param planety seznam planet
	 */
	public void vytvorCesty(List<Planeta> planety){
		for (int i = 0; i < planety.size(); i++) {
			Planeta a = planety.get(i);
			for (int j = 0; j < a.getSousedi().size(); j++) {
				if(!existujeCesta(a,a.getSousedi().get(j))){
					Cesta c = new Cesta(a, a.getSousedi().get(j));
					cesty.add(c);
				}
			}
		}
	}

	/**************************************************************************
	 * ruší orientaci hran
	 * 
	 */
	private void ostatniSousedi(){
		for(int i = 0; i < cesty.size(); i++){
			boolean existuje = false;
			Planeta planetaOd = cesty.get(i).getOd();
			Planeta planetaDo = cesty.get(i).getKam();
			for(int j = 0; j < planetaDo.getSousedi().size(); j++){
				Planeta soused = planetaDo.getSousedi().get(j);
				if(planetaOd == soused){
					existuje = true;
				}
			}
			if(!existuje){
				planetaDo.getSousedi().add(planetaOd);		
			}
		}
	}

	/************************************************************
	 * označí 20% cest za nebezpečné
	 */
	public void generujNebezpecneCesty(){
		Collections.shuffle(cesty);
		for (int i = 0; i < (cesty.size()/100)*20; i++) {
			cesty.get(i).setNebezpeci(true);
		}
	}

	/************************************************************
	 * pro každou planetu najde nekratší cestu
	 * na nejbližší stanici
	 */
	private void udelejDijkstra(){
		projdi(getPlanety().get(5000));
		projdi(getPlanety().get(5001));
		projdi(getPlanety().get(5002));
		projdi(getPlanety().get(5003));
		projdi(getPlanety().get(5004));
		for (int i = 0; i < getPlanety().size()-5; i++) {
			Planeta a = getPlanety().get(i);
			Planeta b = a.getCesta().get(0);
			do{
				if(b.getId()>5000){
					a.getCesta().add(b);
					break;
				}
				b = b.getCesta().get(0);
				a.getCesta().add(b);
			}while(b.getId()<=5000);		
		}
	}

	/*****************************************************************
	 * projdevšechny planety od dané stanice a přiřadí jim
	 * nejkraší vzdálenost, bud od nově testované stanice, 
	 * nebo zůstává vzdálenost bližší z testovaných stanic.
	 * 
	 * @param stanice stanice, od které se prohledává graf
	 */
	private void projdi(Planeta stanice){
		Queue<Planeta> fronta = new LinkedList<Planeta>();
		int[] pole = new int[5005];
		for (int i = 0; i < pole.length; i++) {
			pole[i] = 0;
		}	
		Planeta vrchol = stanice;
		vrchol.setCesta(new ArrayList<Planeta>());
		vrchol.setVzdalenost(0);
		fronta.add(vrchol);
		while(!fronta.isEmpty()){
			Planeta node = fronta.poll();
			for(int i = 0; i < node.getSousedi().size(); i++){
				Planeta soused = node.getSousedi().get(i);
				if(pole[soused.getId()-1] == 0){
					pole[soused.getId()-1] = 1;
					if(soused.getVzdalenost()>(node.getVzdalenost()+vzdalenostPlanet(node, soused))){
						soused.setVzdalenost(node.getVzdalenost()+vzdalenostPlanet(node, soused));
						if(soused.getCesta().size()!=0){
						soused.getCesta().set(0, node);
						}else{soused.getCesta().add(node);}
					}
					fronta.add(soused);
				}
			}
		}
	}

	/*************************************************************
	 * testuje zda wxistuje mezi planetami cesta
	 * 
	 * @param a první planeta
	 * @param b druhá planeta
	 * @return existuje cesta
	 */
	private boolean existujeCesta(Planeta a, Planeta b){
		for (int i = 0; i < cesty.size(); i++) {
			Planeta od = cesty.get(i).getOd();
			Planeta kam = cesty.get(i).getKam();
			if(a==od&&b==kam||a==kam&&b==od){
				return true;
			}
		}
		return false;
	}
	
	/*******************************************************
	 * zjistí vzdálenost dvou planet
	 * 
	 * @param a první planeta
	 * @param b druhá planeta
	 * @return vzdálenost planet
	 */
	public double vzdalenostPlanet(Planeta a, Planeta b){
		int posAX = a.getPosX();
		int posAY = a.getPosY();
		int posBX = b.getPosX();
		int posBY = b.getPosY();
		int rozdilX = Math.abs(posAX-posBX);
		int rozdilY = Math.abs(posAY-posBY);
		return Math.sqrt(Math.pow(rozdilX, 2)+Math.pow(rozdilY, 2));
	}
	
	/******************************************************************************
	 * zjistí vzdálenost mezi planetou a bodem
	 * 
	 * @param a planeta
	 * @param x x-ová souřadnice bodu
	 * @param y y-ová sořadnice boud
	 * @return vzdálenost planety a bodu
	 */
	private double vzdalenostBodu(Planeta a, int x, int y)
	{
		int posPX = a.getPosX();
		int posPY = a.getPosY();
		int rozdilX = Math.abs(posPX-x);
		int rozdilY = Math.abs(posPY-y);
		double vzdalenost = Math.sqrt(Math.pow(rozdilX, 2)+Math.pow(rozdilY, 2));
		return vzdalenost;
	}

	/*********************************************
	 * vrátí seznam planet
	 * 
	 * @return seznam planet
	 */
	public List<Planeta> getPlanety(){
		return planety;
	}
	
	/********************************************
	 * vrátí seznam stanic
	 * 
	 * @return stanic
	 */
	public List<Stanice> getStanice(){
		return stanice;
	}
	
	/******************************************
	 * vrátí seznam cest
	 * 
	 * @return seznam cest
	 */
	public List<Cesta> getCesty(){
		return cesty;
	}
	
	/******************************************
	 * generuje populaci pomocí Gaussovi
	 * funkce, která má vrchol 3M
	 * 
	 * @return vrátí populaci
	 */
	private int generujPopulaci(){
		int stredHodnota = 3000000;
		int rozptyl = 3000000;
		Random rand = new Random();
			int k;
			while(true){
				k = (int) (stredHodnota + rozptyl * rand.nextGaussian());
				if(k >= 100000 && k <= 10000000){
					break;
				}
			}
		return k;
	}
}
