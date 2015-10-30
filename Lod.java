package PT;

import java.util.ArrayList;
import java.util.Stack;


public class Lod {

	private int posX;
	private int posY;
	private int naklad;
	//-1 - ve stanici,0 - leti, 1 - naklad|vylozeni
	private int stav = -1;
	
	private int id;
	
	private double procentaCesty;
	private Object lokace;
	private Planeta chciNa;
	private Stack<Planeta> cil = new Stack<Planeta>();
	private ArrayList<Cesta> cestovalaPres = new ArrayList<Cesta>();
	
	public Lod(Stanice start, int id){
		this.id = id;
		this.posX = start.getPosX();
		this.posY = start.getPosY();
		this.lokace = start;
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

	public void setStav(int stav) {
		this.stav = stav;
	}

	public ArrayList<Cesta> getCestovalaPres() {
		return cestovalaPres;
	}

	public void setCestovalaPres(ArrayList<Cesta> cestovalaPres) {
		this.cestovalaPres = cestovalaPres;
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
			System.out.println(this.procentaCesty);
			System.out.println(this.lokace);
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
				this.chciNa=((Planeta)lokace).getCesta().get(0);
			}
		}
	}

	public double getProcentaCesty() {
		return procentaCesty;
	}

	public void setProcentaCesty(double procentaCesty) {
		this.procentaCesty = procentaCesty;
	}
	
	

}
