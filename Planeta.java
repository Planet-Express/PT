package PT;

import java.util.ArrayList;
import java.util.TreeMap;

public class Planeta {

	private TreeMap<Double, Planeta> sousedi = new TreeMap<Double, Planeta>();
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
		ArrayList<Planeta> sousedi = new ArrayList<Planeta>(1);
		sousedi.add(this.sousedi.get(this.sousedi.firstKey()));
		return sousedi;
	}
	
	public TreeMap<Double, Planeta> getMapa(){
		return this.sousedi;
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
	
	public int getPop(){
		return pop;
	}
	
	public void vypis(){
		System.out.println("{"+id+"}"+posX+"/"+posY+ " " + pop);
	}
}
