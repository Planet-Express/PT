package pt;


import java.util.ArrayList;
import java.util.Stack;


public class Lod {

	private int posX;
	private int posY;
	private int naklad;
	//-1 - ve stanici,0 - leti, 1 - naklad|vylozeni
	private int stav = -1;
	
	private int id;
	private Stanice start;
	
	private double procentaCesty;
	private Object lokace;
	private Planeta chciNa;
	private Stack<Planeta> cil = new Stack<Planeta>();
	private Stack<Integer> rozpis = new Stack<Integer>();

	public Lod(Stanice start, int id){
		this.id = id;
		this.posX = start.getPosX();
		this.posY = start.getPosY();
		this.lokace = start;
		this.start = start;
		start.getDok().push(this);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStav() {
		return stav;
	}

	public boolean stihne(Objednavka ob, int den){
		int cas = (int)(ob.getVzdalenost()/25 + cil.size()*2+2);
		if(cas+den%30>=30){
			return false;
		}else{
			return true;
		}
	}
	
	public void setStav(int stav) {
		this.stav = stav;
	}


	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getNaklad() {
		return naklad;
	}

	public void setNaklad(int naklad) {
		this.naklad = naklad;
	}

	public Object getLokace() {
		return lokace;
	}

	public void setLokace(Object lokace) {
		if(lokace==null){
			System.out.println(id);
		}
		this.lokace = lokace;
	}

	public Stack<Planeta> getCil() {
		return cil;
	}
	
	public String toString(){
		return "id = " + getId() + ", size = " + getCil().size() + ", naklad = " + getNaklad() + ", stav = " + getStav();
	}

	public Planeta getChciNa() {
		return chciNa;
	}
	
	public Stanice getStart(){
		return start;
	}

	public void setChciNa() {
		if(this.lokace instanceof Planeta){
			if(this.cil.size()!=0){
				if(this.cil.peek().getCesta().get(0)==(Planeta)this.lokace){this.chciNa = this.cil.peek();}
				else{
					for (int i = this.cil.peek().getCesta().size()-1; i >= 0; i--) {
						if(this.cil.peek().getCesta().get(i)==(Planeta)this.lokace){
							if((i-1)>=0){
								this.chciNa = this.cil.peek().getCesta().get(i-1);	
							}
						}
					}
				}
			}else{
				if(((Planeta) lokace).getId()>5000){
					/*
					if(stav != -1){
						start.getDok().push(this);
					}
					stav = -1;
					*/
					this.chciNa = start;
					this.lokace = start;
				}
				else{
					this.chciNa=((Planeta)lokace).getCesta().get(0);
				}
			}
		}
	}
	
	public void resetLod(){
		cil.clear();
		rozpis.clear();
		naklad = 0;
	}

	public double getProcentaCesty() {
		return procentaCesty;
	}

	public void setProcentaCesty(double procentaCesty) {
		this.procentaCesty = procentaCesty;
	}

	public Stack<Integer> getRozpis() {
		return rozpis;
	}

	public void setRozpis(Stack<Integer> rozpis) {
		this.rozpis = rozpis;
	}
	
	

}
