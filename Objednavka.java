package PT;

public class Objednavka {

	private Planeta kam;
	private Stanice od;
	private int kolik;
	private int potencial = 0;
	private double vzdalenost;
	private int stav = 0;
	private int puvodni;
	
	public Objednavka(Planeta kam, Stanice od, int kolik, double vzdalenost, int id){
		this.kam = kam;
		this.od = od;
		this.kolik = kolik;
		this.vzdalenost = vzdalenost;
		this.setPuvodni(kolik);
		//System.out.println("Planeta "+kam.getId()+" si objednala "+kolik+" lékù");
	}
	
	
	public Stanice getOd() {
		return od;
	}


	public int getPotreba(){
		return this.kolik - this.potencial;
	}
	
	public void setOd(Stanice od) {
		this.od = od;
	}


	public int getPotencial() {
		return potencial;
	}


	public void setPotencial(int potencial) {
		this.potencial = potencial;
	}


	public int getStav() {
		return stav;
	}


	public void setStav(int stav) {
		this.stav = stav;
	}

	public void setVzdalenost(double vzdalenost) {
		this.vzdalenost = vzdalenost;
	}
	
	public Planeta getKam() {
		return kam;
	}
	public void setKam(Planeta kam) {
		this.kam = kam;
	}
	public int getKolik() {
		return kolik;
	}
	public void setKolik(int kolik) {
	//	this.setPuvodni(this.getPuvodni() + this.kolik-kolik);
		this.kolik = kolik;
	}
	public double getVzdalenost() {
		return vzdalenost;
	}
	public void setVzdalenost(int vzdalenost) {
		this.vzdalenost = vzdalenost;
	}


	public int getPuvodni() {
		return puvodni;
	}


	public void setPuvodni(int puvodni) {
		this.puvodni = puvodni;
	}
	
	
}
