package PT;


import java.util.Stack;

public class Stanice extends Planeta{

	private Stack<Lod> dok = new Stack<Lod>();
	
	public Stack<Lod> getDok() {
		return dok;
	}

	public void setDok(Stack<Lod> dok) {
		this.dok = dok;
	}

	public Stanice(int id, int x, int y){
		super(id, x, y, 0);
	}
	
	
	
}
