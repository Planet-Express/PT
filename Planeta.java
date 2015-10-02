package PT;

import java.util.ArrayList;
import java.util.TreeMap;

public class Planeta {

	private ArrayList<Planeta> sousedi = new ArrayList<Planeta>(5);
	private int posX;
	private int posY;
	private int pop;
	private int id;
	
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
	
	public void vypis(){
		System.out.println("{"+id+"}"+posX+"/"+posY);
	}
	
	public void trimSousedi(){
		ArrayList<Planeta> s = new ArrayList<>(5);
		for (int i = 0; i < s.size(); i++) {
			s.set(i, sousedi.get(i));
		}
		sousedi = s;
	}
}
