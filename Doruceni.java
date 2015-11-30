package PT;

public class Doruceni {
	private int kolik;
	private Lod lod;
	private int den;
	
	public Doruceni(int kolik, Lod l, int den){
		this.kolik = kolik;
		this.lod = l;
		this.den = den;
	}

	public int getKolik() {
		return kolik;
	}

	public void setKolik(int kolik) {
		this.kolik = kolik;
	}

	public Lod getLod() {
		return lod;
	}

	public void setLod(Lod lod) {
		this.lod = lod;
	}

	public int getDen() {
		return den;
	}

	public void setDen(int den) {
		this.den = den;
	}
	
	
}
