package pt;

public class Cesta{
	
	private Planeta od;
	private Planeta kam;
	private boolean nebezpecna = false;

	public Cesta(Planeta od, Planeta kam){
		this.od = od;
		this.kam = kam;
	}
	
	public void setNebezpeci(boolean b){
		this.nebezpecna =  b;
	}
	
	public boolean isNebezpecna(){
		return nebezpecna;
	}
	
	public Planeta getOd(){
		return od;
	}
	
	public Planeta getKam(){
		return kam;
	}
	
	public int getAX(){
		return od.getPosX();
	}
	
	public int getAY(){
		return od.getPosY();
	}
	
	public int getBX(){
		return kam.getPosX();
	}
	
	public int getBY(){
		return kam.getPosY();
	}

}
