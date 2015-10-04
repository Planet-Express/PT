package PT;

import java.util.ArrayList;

public class Planeta{

	private ArrayList<Planeta> sousedi = new ArrayList<Planeta>();
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
	
	public int getPop(){
		return pop;
	}
	
	public void vypis(){
		System.out.println("{"+id+"}"+posX+"/"+posY+ " " + pop);
	}
	
	public void trimSousedi(){
		ArrayList<Planeta> s = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			s.add(sousedi.get(i));
		}
		sousedi = s;
		
	}

	
}
