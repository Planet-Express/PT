package PT;

import java.util.ArrayList;

public class Planeta{

	private ArrayList<Planeta> sousedi = new ArrayList<Planeta>();
	private int posX;
	private int posY;
	private int pop;
	private int id;
	boolean mrtva = false;
	double vzdalenost = Integer.MAX_VALUE;
	ArrayList<Planeta> cesta = new ArrayList<Planeta>(1);
	private Objednavka objednavka;
	
	public Planeta(int id, int posX, int posY, int pop){
		this.id = id;
		this.posX = posX;
		this.posY = posY;
		this.pop = pop;
		
	}
	
	public ArrayList<Planeta> getSousedi(){
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
	
	public ArrayList<Planeta> getCesta(){
		return cesta;
	}
	
	public void setCesta(ArrayList<Planeta> a){
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
	
	public String getJmeno(){
		String s = "";
		int c = (id-1)%26;
		int zbytek = (id-1)/26;
		for (int i = 0; i < 2; i++) {
			switch (c) {
			case 0: s += "Alpha"; break;
			case 1: s += "Bravo"; break;
			case 2: s += "Charlie"; break;
			case 3: s += "Delta"; break;
			case 4: s += "Echo"; break;
			case 5: s += "Foxtrot"; break;
			case 6: s += "Gold"; break;
			case 7: s += "Hotel"; break;
			case 8: s += "India"; break;
			case 9: s += "Juliet"; break;
			case 10: s += "Kilo"; break;
			case 11: s += "Lima"; break;
			case 12: s += "Mike"; break;
			case 13: s += "November"; break;
			case 14: s += "Oscar"; break;
			case 15: s += "Papa"; break;
			case 16: s += "Quebec"; break;
			case 17: s += "Romeo"; break;
			case 18: s += "Sierra"; break;
			case 19: s += "Tango"; break;
			case 20: s += "Uniform"; break;
			case 21: s += "Victor"; break;
			case 22: s += "Whiskey"; break;
			case 23: s += "Xray"; break;
			case 24: s += "Yankee"; break;
			case 25: s += "Zulu"; break;
			default: s += "Error";
				break;
			}
			c = (zbytek%26);
		}
		int cisla = ((id-1)/676)%10;
		s += (cisla+1);
		return s;
	}

	public Objednavka getObjednavka() {
		return objednavka;
	}

	public void setObjednavka(Objednavka objednavka) {
		this.objednavka = objednavka;
	}
}
