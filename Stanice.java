package PT;

import java.util.ArrayList;

public class Stanice extends Planeta{

	private ArrayList<Lod> dok = new ArrayList<Lod>();
	
	public ArrayList<Lod> getDok() {
		return dok;
	}

	public void setDok(ArrayList<Lod> dok) {
		this.dok = dok;
	}

	public Stanice(int id, int x, int y){
		super(id, x, y, 0);
	}
	
	
	
}
