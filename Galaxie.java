package PT;

import java.util.ArrayList;
import java.util.Random;

public class Galaxie{

	private ArrayList<Planeta> planety = new ArrayList<Planeta>();
	private int pocet = 0;
	private int delka = 0;
	private int populace;
	public Galaxie(int delka, int pocet){
		this.pocet = pocet;
		this.delka = delka;
		for (int i = 1; i <= pocet; i++) {
			planety.add(vytvorPlanetu(i));
		}
	//	dohledejSousedy(planety);
		for (int i = 0; i < planety.size(); i++) {
	//		planety.get(i).getSousedi().get(0).vypis();
		}
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
					if(vzdalenost<=2){break;}
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
			for (int j = 0; j < planety.size(); j++) {
				if(i!=j){
					Planeta a = planety.get(i);
					Planeta b = planety.get(j);
					a.getMapa().put(vzdalenostPlanet(a, b),b);
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
