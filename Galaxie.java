package PT;

import java.util.ArrayList;
import java.util.Random;

public class Galaxie{

	private ArrayList<Planeta> planety = new ArrayList<Planeta>(5000);
	private ArrayList<Stanice> stanice = new ArrayList<Stanice>(5);
	private int pocet = 0;
	private int delka = 0;
	private int populace;
	
	public Galaxie(int delka, int pocet){
		this.pocet = pocet;
		this.delka = delka;
		/**
		//VYPIS VSECH SOUSEDU PO VZDALENOSTECH
		for (int i = 0; i < planety.size(); i++) {
			
			for (int j = 0; j < planety.get(i).getSousedi().size(); j++) {
				System.out.print("|"+vzdalenostPlanet(planety.get(i), planety.get(i).getSousedi().get(j))+"|");
			}
			System.out.println();
		}
		**/
	}
	
	public void generujVesmir(){
		for (int i = 1; i <= pocet; i++) {
			planety.add(vytvorPlanetu(i));
		}
		vytvorStanice();
		dohledejSousedy(planety);		
	}
	
	private void vytvorStanice() {
		stanice.add(new Stanice(1, 200, 200));
		stanice.add(new Stanice(2, 600, 200));
		stanice.add(new Stanice(3, 200, 600));
		stanice.add(new Stanice(4, 600, 600));
		stanice.add(new Stanice(5, 400, 400));
		
	}

	public Planeta vytvorPlanetu(int id){
		int x = -1;
		int y = -1;
		boolean lze = false;
		int counter = 0;
		while(!lze&&counter<100){
			counter++;
			x = randomRange(0, delka);
			y = randomRange(0, delka);
			if(planety.size()==0){break;}
			else{
				for (int i = 0; i < planety.size(); i++) 
				{
					int posPX = planety.get(i).getPosX();
					int posPY = planety.get(i).getPosY();
					int rozdilX = Math.abs(posPX-x);
					int rozdilY = Math.abs(posPY-y);
					double vzdalenost = Math.sqrt(Math.pow(rozdilX, 2)+Math.pow(rozdilY, 2));
					if(vzdalenost<=4){break;}
					if((i+1)==planety.size()){lze = true;}
				}
				if(counter==99){System.out.println("Planeta "+id+" se do galaxie nevesla");}
			}
		}
		
		populace = generujPopulaci();
		Planeta p = new Planeta(id,x,y,populace);
		//p.vypis();
		return p;
	}
	
	public void dohledejSousedy(ArrayList<Planeta> planety){
		for (int i = 0; i < planety.size(); i++) {
			Planeta a = planety.get(i);
			for (int j = 0; j < planety.size(); j++) {
				if(i!=j){
					Planeta b = planety.get(j);
					ArrayList<Planeta>sousedi = a.getSousedi();
					double vzdalenost = vzdalenostPlanet(a, b);
					for(int l = 0; l < 5; l++){						
						sousedi.add(new Planeta(0, Integer.MAX_VALUE, Integer.MAX_VALUE, 0));
					}
					for (int k = 0; k < sousedi.size(); k++) {
						if(vzdalenost <= vzdalenostPlanet(a, sousedi.get(k))){
							sousedi.add(k, b);
							a.trimSousedi();
							sousedi = a.getSousedi();
							break;
						}
					}					
				}
			}
		}
	}
	
	public double vzdalenostPlanet(Planeta a, Planeta b){
		int posAX = a.getPosX();
		int posAY = a.getPosY();
		int posBX = b.getPosX();
		int posBY = b.getPosY();
		int rozdilX = Math.abs(posAX-posBX);
		int rozdilY = Math.abs(posAY-posBY);
		return Math.sqrt(Math.pow(rozdilX, 2)+Math.pow(rozdilY, 2));
	}
	
	public int randomRange(int odkud, int kam){
		int rozmezi = (kam-odkud)+1;
		return (int)(Math.random()*rozmezi)+odkud;
	}

	public ArrayList<Planeta> getPlanety(){
		return planety;
	}
	
	public ArrayList<Stanice> getStanice(){
		return stanice;
	}
	
	public int generujPopulaci(){
		int stredHodnota = 3000000;
		int rozptyl = 3000000;
		Random rand = new Random();
			int k;
			while(true){
				k = (int) (stredHodnota + rozptyl * rand.nextGaussian());
				if(k >= 100000 && k <= 10000000){
					break;
				}
			}
		return k;
	}
}
