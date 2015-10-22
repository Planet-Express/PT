package PT;

import java.util.ArrayList;

public class Counter extends Thread{

	Galaxie g;
	GUI gui;
	ArrayList<Lod> lode = new ArrayList<Lod>();
	ArrayList<Objednavka> objednavky = new ArrayList<Objednavka>();
	int den = 0;
	
	@SuppressWarnings("static-access")
	public void run(){
		synchronized (this) {
			while(true){
				System.out.println("Zaèíná den "+(den+1));
				
				objednavky = g.getObjednavky();
				
				den++;
				System.out.println("Den "+den+" skonèil");
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void start(Galaxie g, GUI gui){
		super.start();
		this.g = g;
	}
	
}
