package PT;

public class Objednavka {

	private Planeta kam;
	private int kolik;
	private double vzdalenost;
	private int stav = 0;
	private int id;
	
	public Objednavka(Planeta kam, int kolik, double vzdalenost, int id){
		this.kam = kam;
		this.kolik = kolik;
		this.vzdalenost = vzdalenost;
		System.out.println("Planeta "+kam.getId()+" si objednala "+kolik+" lékù");
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
		this.kolik = kolik;
	}
	public double getVzdalenost() {
		return vzdalenost;
	}
	public void setVzdalenost(int vzdalenost) {
		this.vzdalenost = vzdalenost;
	}
	
	
}
