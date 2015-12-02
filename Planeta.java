package pt;

import java.util.ArrayList;
import java.util.List;

/********************************************************************
 * Instance třídy {@code Planeta} představují
 * planetu, která se nachází v galaxii na 
 * určitých souřadnicích a o určitém počtu obyvatel. 
 * @author Michal
 *
 */
public class Planeta{

	/** id planety*/
	private final int id;
	
	/** x-ová souřadnic*/
	private final int posX;
	
	/** y-ová souřadnice*/
	private final int posY;
	
	/** populace planety*/
	private int pop;
	
	/** zda má cenu planety obsluhovat*/
	private boolean mrtva = false;
	
	/** vzdálenost od stanice*/
	private double vzdalenost = Integer.MAX_VALUE;
	
	/** seznam sousedů*/
	private List<Planeta> sousedi = new ArrayList<Planeta>();
	
	/** seznam planet tvořících cestu na stanici*/
	private List<Planeta> cesta = new ArrayList<Planeta>(1);
	
	/** objednávka planety*/
	private Objednavka objednavka;
	
	/** vývoj obyvatelstva po měsíci*/
	private final List<Integer> obyvatelsto = new ArrayList<Integer>();
	
	/** vývoj doručenek po měsíci*/
	private final List<Doruceni> doruceno = new ArrayList<Doruceni>();
	
	/************************************************************
	 * vytvoří planetu s id na sořadnicích 
	 * x a y, a zadané populaci.
	 * 
	 * @param id id planety
	 * @param posX x-ová souřadnice planety
	 * @param posY y-ová souřadnice planety
	 * @param pop velikost populace
	 */
	public Planeta(int id, int posX, int posY, int pop){
		this.id = id;
		this.posX = posX;
		this.posY = posY;
		this.pop = pop;
		this.obyvatelsto.add(pop);
	}

	/**********************************************************
	 * vrátí seznam, který reprezentuje vývoj
	 * obyvatelstva po jednotlivých měsících
	 * 
	 * @return seznam vývoje obyvatelstva
	 */
	public List<Integer> getObyvatelsto() {
		return obyvatelsto;
	}

	/*******************************************************
	 * vrátí seznam, který reprezentuje vývoj
	 * doručování lěků po jednotlivých měsících
	 * 
	 * @return seznam vývoje doručování
	 */
	public List<Doruceni> getDoruceno() {
		return doruceno;
	}

	/******************************************
	 * vrátí seznam sousedních planet
	 * 
	 * @return seznam sousedních planet
	 */
	public List<Planeta> getSousedi(){
		return sousedi;
	}
	
	/*****************************************
	 * vrátí id planety
	 * 
	 * @return id planety
	 */
	public int getId(){
		return id;
	}
	
	/**************************************
	 * vrátí x-ouvo souřadnici planety
	 * 
	 * @return x-ová souřadnice planety
	 */
	public int getPosX(){
		return posX;
	}
	
	/**************************************
	 * vrátí x-ouvo souřadnici planety
	 * 
	 * @return x-ová souřadnice planety
	 */
	public int getPosY(){
		return posY;
	}
	
	/***********************************
	 * vrátí populaci planety
	 * 
	 * @return populace planety
	 */
	public int getPop(){
		return pop;
	}
	
	/**********************************************************
	 * vrátí text popisující planetu
	 * 
	 * @return popis planety
	 */
	public String toString(){
		return "{"+getJmeno()+"}"+posX+"/"+posY+ " " + pop;
	}
	
	/*********************************************************
	 * ořízne pole sousedů na prvních pět,
	 * poleje seřazené, tak je to i zároveň
	 * pět nejbližších.
	 */
	public void trimSousedi(){
		ArrayList<Planeta> s = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			s.add(sousedi.get(i));
		}
		sousedi = s;
		
	}
	
	/***************************************************
	 * vrátí seznam planet, které předdstavují cestu
	 * z stanice k planetě.
	 * 
	 * @return seznam planet na cestě
	 */
	public List<Planeta> getCesta(){
		return cesta;
	}
	
	/***************************************************
	 * nastaví seznam planet tvořící cestu
	 * na nově zadaný
	 * 
	 * @param a zadaný seznam planet
	 */
	public void setCesta(List<Planeta> a){
		this.cesta = a;
	}
	
	/************************************************
	 * nastaví vzdálenost planety od stanice
	 * na nově zadanou
	 * 
	 * @param vzd požadovaná vzdálenost
	 */
	public void setVzdalenost(double vzd){
		this.vzdalenost = vzd;
	}
	
	/*****************************************************
	 * vrátí vzdálenost planety od stanice
	 * 
	 * @return vzdálenost planety a stanice
	 */
	public double getVzdalenost(){
		return vzdalenost;
	}
	
	/*****************************************************
	 * planeta si vyrobí sama mezi 20-80% léků 
	 * 
	 * @return množství vorobených lěků
	 */
	public int vyrobLeky(){
		double pravdepodobnost = Math.random()*0.6+0.2;
		return (int)(pop*pravdepodobnost);
	}
	
	/****************************************************
	 * vrátí, zda má cenu (pop > 40k) obsluhovat
	 * planetu
	 * 
	 * @return jestli se má planeta zásobovat
	 */
	public boolean isMrtva(){
		return mrtva;
	}
	
	/***********************************************************************
	 * zabije lidi, který nedostali léky
	 * 
	 * @param lidi množství lidí bez léků
	 */
	public void zabij(int lidi){
		this.pop = this.pop - lidi;
		this.obyvatelsto.add(pop);
		if(this.pop<40000){
			mrtva = true;
		}
	}
	
	/***********************************************************************
	 * vrátí jméno planetu, které se vygeneru
	 * z id
	 * 
	 * @return jméno planty
	 */
	public String getJmeno(){
		String s = "";
		int c = (id-1)%26;
		int zbytek = (id-1)/26;
		for (int i = 0; i < 2; i++) {
			s += generujNazev(c);
			c = (zbytek%26);
		}
		int cisla = ((id-1)/676)%10;
		s += (cisla+1);
		return s;
	}

	/***********************************************************************
	 * vrací jméno podle zadaného čísla
	 * 
	 * @param c číslo přepočtené z id
	 * 
	 * @return název odpovídající zadané hodnotě
	 */
	private String generujNazev(int c) {
		String[] pole = {"Alpha", "Bravo", "Charlie", "Delta", "Echo",
				"Foxtrot", "Gold", "Hotel", "India", "Juliet", "Kilo",
				"Lima", "Mike", "November", "Oscar", "Papa", "Quebec",
				"Romeo", "Sierra", "Tango", "Uniform", "Victor", "Whiskey",
				"Xray", "Yankee", "Zulu"};
		return pole[c];
	}

	/***********************************************************************
	 * vrátí aktuální onjednávku
	 * 
	 * @return instance třídy {@code Objednavka}
	 */
	public Objednavka getObjednavka() {
		return objednavka;
	}

	/*********************************************************************
	 * nastaví objednávku na novou, která je 
	 * zadané
	 * 
	 * @param objednavka zadané objednávka
	 */
	public void setObjednavka(Objednavka objednavka) {
		this.objednavka = objednavka;
	}
}
