package PT;

public class Lod {

	private int posX;
	private int posY;
	private int naklad;
	
	private Lokace lokace;
	private Planeta cil;
	
	private Lod(Stanice start, int naklad, Planeta cil){
		this.naklad = naklad;
		this.cil = cil;
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

	public Lokace getLokace() {
		return lokace;
	}

	public void setLokace(Lokace lokace) {
		this.lokace = lokace;
	}

	public Planeta getCil() {
		return cil;
	}

	public void setCil(Planeta cil) {
		this.cil = cil;
	}
}
