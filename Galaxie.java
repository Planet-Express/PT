package PT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class Galaxie{

	private ArrayList<Planeta> planety = new ArrayList<Planeta>(5000);
	private ArrayList<Stanice> stanice = new ArrayList<Stanice>(5);
	private ArrayList<Cesta> cesty = new ArrayList<Cesta>();
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
		vytvorStanice();
		for (int i = 1; i <= pocet; i++) {
			planety.add(vytvorPlanetu(i));
		}
		dohledejSousedy(planety);
		vytvorCesty(planety);
		generujNebezpecneCesty();
	}
	
	private void vytvorStanice() {
		stanice.add(new Stanice(5001, 175, 175));
		stanice.add(new Stanice(5002, 625, 175));
		stanice.add(new Stanice(5003, 175, 625));
		stanice.add(new Stanice(5004, 625, 625));
		stanice.add(new Stanice(5005, 400, 400));
		
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
			
				if(planety.size()==0){
					lze = true;
					}
				for (int i = 0; i < planety.size(); i++) {
					double vzdalenost = vzdalenostBodu(planety.get(i), x, y);
					if(vzdalenost<=2){break;}
					
					if((i+1)==planety.size()){lze = true;}
				}
				//Kontrola se stanicemi
				for (int j = 0; j < stanice.size(); j++) {
					double vzdalenostStanice = vzdalenostBodu(stanice.get(j),x,y);
					if(vzdalenostStanice<=4){
						lze = false;
						break;
					}
				}
				if(counter==99){
					System.out.println("Planeta "+id+" se do galaxie nevesla");
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
	
	public void vytvorCesty(ArrayList<Planeta> planety){
		for (int i = 0; i < planety.size(); i++) {
			Planeta a = planety.get(i);
			for (int j = 0; j < a.getSousedi().size(); j++) {
				if(!existujeCesta(a,a.getSousedi().get(j))){
					Cesta c = new Cesta(a, a.getSousedi().get(j));
					cesty.add(c);
				}
			}
		}
	}
	
	public boolean existujeCesta(Planeta a, Planeta b){
		for (int i = 0; i < cesty.size(); i++) {
			Planeta od = cesty.get(i).getOd();
			Planeta kam = cesty.get(i).getKam();
			if(a==od&&b==kam||a==kam&&b==od){
				return true;
			}
		}
		return false;
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
	
	public double vzdalenostBodu(Planeta a, int x, int y)
	{
		int posPX = a.getPosX();
		int posPY = a.getPosY();
		int rozdilX = Math.abs(posPX-x);
		int rozdilY = Math.abs(posPY-y);
		double vzdalenost = Math.sqrt(Math.pow(rozdilX, 2)+Math.pow(rozdilY, 2));
		return vzdalenost;
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
	
	public ArrayList<Cesta> getCesty(){
		return cesty;
	}
	
	public void generujNebezpecneCesty(){
		Collections.shuffle(cesty);
		for (int i = 0; i < (cesty.size()/100)*20; i++) {
			cesty.get(i).setNebezpeci(true);
		}
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
