package pt;

import java.util.ArrayList;
import java.util.List;

public class Planeta{

	private List<Planeta> sousedi = new ArrayList<Planeta>();
	private final int posX;
	private final int posY;
	private int pop;
	private final int id;
	private boolean mrtva = false;
	double vzdalenost = Integer.MAX_VALUE;
	List<Planeta> cesta = new ArrayList<Planeta>(1);
	private Objednavka objednavka;
	private List<Integer> obyvatelsto = new ArrayList<Integer>();
	private List<Doruceni> doruceno = new ArrayList<Doruceni>();
	
	public List<Integer> getObyvatelsto() {
		return obyvatelsto;
	}

	public void setObyvatelsto(List<Integer> obyvatelsto) {
		this.obyvatelsto = obyvatelsto;
	}

	public List<Doruceni> getDoruceno() {
		return doruceno;
	}

	public void setDoruceno(List<Doruceni> doruceno) {
		this.doruceno = doruceno;
	}

	public Planeta(int id, int posX, int posY, int pop){
		this.id = id;
		this.posX = posX;
		this.posY = posY;
		this.pop = pop;
		this.obyvatelsto.add(pop);
	}
	
	public List<Planeta> getSousedi(){
		return sousedi;
	}
	
	public int getId(){
		return id;
	}
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}
	
	public int getPop(){
		return pop;
	}
	
	public String toString(){
		return "{"+getJmeno()+"}"+posX+"/"+posY+ " " + pop;
	}
	
	public void trimSousedi(){
		ArrayList<Planeta> s = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			s.add(sousedi.get(i));
		}
		sousedi = s;
		
	}
	
	public List<Planeta> getCesta(){
		return cesta;
	}
	
	public void setCesta(List<Planeta> a){
		this.cesta = a;
	}
	
	public void setVzdalenost(double vzd){
		this.vzdalenost = vzd;
	}
	
	public double getVzdalenost(){
		return vzdalenost;
	}
	public void setMrtva(boolean b){
		this.mrtva = b;
	}
	
	public int vyrobLeky(){
		double pravdepodobnost = Math.random()*0.6+0.2;
		return (int)(pop*pravdepodobnost);
	}
	
	public boolean isMrtva(){
		return mrtva;
	}
	
	public void zabij(int lidi){
		this.pop = this.pop - lidi;
		this.obyvatelsto.add(pop);
		if(this.pop<40000){
			mrtva = true;
			System.out.println("Na planetu "+id+" u� nem� cenu let�t.");
		}
	}
	
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

	private String generujNazev(int c) {
		String[] pole = {"Alpha", "Bravo", "Charlie", "Delta", "Echo",
				"Foxtrot", "Gold", "Hotel", "India", "Juliet", "Kilo",
				"Lima", "Mike", "November", "Oscar", "Papa", "Quebec",
				"Romeo", "Sierra", "Tango", "Uniform", "Victor", "Whiskey",
				"Xray", "Yankee", "Zulu"};
		return pole[c];
	}

	public Objednavka getObjednavka() {
		return objednavka;
	}

	public void setObjednavka(Objednavka objednavka) {
		this.objednavka = objednavka;
	}
}
