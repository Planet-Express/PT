package PT;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;

public class Counter extends Thread{
	
	Galaxie g;
	GUI gui;
	ArrayList<Lod> lode = new ArrayList<Lod>();
	ArrayList<Objednavka> objednavky = new ArrayList<Objednavka>();
	int den = 0;
	int mesic = 0;
	final int RYCHLOST = 25;
	public void run(){
		Soubor.initLogger();
		synchronized (this) {
			while(true){
				
				////////////////ZACATEK MESICE
				vytvorObjednavky();
				int cntr = 0;
				while (potrebaLeku()>0) {
					obsluzObjednavku(cntr);
					cntr++;
					if(cntr==5000){cntr = 0;}
				}
	
				for (int i = 0; i < 30; i++) {
					////////////// ZACATEK DNE
					Soubor.getLogger().log(Level.SEVERE, "Začíná den "+(den+1)+", měsíc "+(mesic));
					for (int j = 0; j < lode.size(); j++) {
						vylozLod(lode.get(j));
						posunLeticiLod(lode.get(j));
					}
										
					den++;
					Soubor.getLogger().log(Level.SEVERE, "Den "+den+" skončil");
					vysliNalozeneLode();
					zacniVykladatLode();
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				mesic++;
			}
		}
	}
	
	public void vylozLod(Lod l){
		if(l.getStav()==3){
			if(l.getCil().size()==0){
				System.out.println(l.getId());
			}
			l.getCil().pop();
			l.setStav(0);
		}
	}
	
	public void posunLeticiLod(Lod l){
		if(l.getLokace()==l.getChciNa()&&l.getChciNa().getId()>5000){
			l.getStart().getDok().add(l);
			l.setStav(-1);
		}
		double doleti = RYCHLOST;
		if(l.getStav()==0){
			l.setChciNa();
			double cesta = 0;
			double velikost = 0;
			if(l.getLokace() instanceof Planeta){
				cesta = g.vzdalenostPlanet((Planeta)l.getLokace(), l.getChciNa());
			}else{
				velikost = g.vzdalenostPlanet(((Cesta)l.getLokace()).getOd(),((Cesta)l.getLokace()).getKam());
				cesta = velikost - velikost*l.getProcentaCesty();
			}
			if(cesta<doleti){
				while(cesta<doleti){
					l.setLokace(l.getChciNa());
					l.setChciNa();
					doleti-=cesta;
					if(l.getLokace() instanceof Planeta){
						if(l.getCil().size()>0){
							if(l.getLokace() == l.getCil().peek()){
								l.setStav(2);
								cesta = 0;
								break;
							}
							else{
								cesta = g.vzdalenostPlanet((Planeta)l.getLokace(), l.getChciNa());
							}
						}
					}else{
						velikost = g.vzdalenostPlanet(((Cesta)l.getLokace()).getOd(),((Cesta)l.getLokace()).getKam());
						cesta = velikost - velikost*l.getProcentaCesty();
					}
				}				
			}
			if(cesta>=doleti&&cesta!=0){		// zustavam na ceste
				if(l.getLokace() instanceof Planeta){
					l.setChciNa();
					l.setLokace(najdiCestu((Planeta)l.getLokace(), l.getChciNa()));
					l.setProcentaCesty(1-((cesta-doleti)/cesta));
					if(cesta==doleti){
						l.setLokace(l.getChciNa());
						l.setChciNa();
					}
				}else{
					l.setProcentaCesty(l.getProcentaCesty()+(1-(cesta-doleti)/velikost));
					if(cesta==doleti){
						l.setLokace(l.getChciNa());
						l.setChciNa();
					}
				}
			}
		}
	}
	
	public Cesta najdiCestu(Planeta od, Planeta kam){
		for (int i = 0; i < g.getCesty().size(); i++) {
			//if((g.getCesty().get(i).getOd()==od&&g.getCesty().get(i).getKam()==kam)||(g.getCesty().get(i).getOd()==kam&&g.getCesty().get(i).getKam()==od)){
			if((g.getCesty().get(i).getOd().getId()==od.getId()&&g.getCesty().get(i).getKam().getId()==kam.getId())||(g.getCesty().get(i).getOd().getId()==kam.getId()&&g.getCesty().get(i).getKam().getId()==od.getId())){
	
				return g.getCesty().get(i);
			}
		}
		return null;
	}
	
	public void vysliNalozeneLode(){
		for (int i = 0; i < lode.size(); i++) {
			if(lode.get(i).getStav()==1){
				lode.get(i).setStav(0);
				Soubor.getLogger().log(Level.FINE, "Lod č."+lode.get(i).getId()+" byla vyslána na cestu.");
			}
		}
	}
	
	public void zacniVykladatLode(){
		for (int i = 0; i < lode.size(); i++) {
			if(lode.get(i).getStav()==2){
				lode.get(i).setStav(3);
				
			}
		}
	}
	
	public void obsluzObjednavku(int kterou){
		Objednavka ob = objednavky.get(kterou);
		int unosnost = 5000000;
		Lod l = getLod(ob.getOd());
			if(ob.getPotreba()+l.getNaklad()>unosnost){
				if(l.getNaklad()!=0){
				ob.getOd().getDok().pop();
				logLod(l);
				l = getLod(ob.getOd());
				}
				naplnLod(l, ob, unosnost);
			//	System.out.println("1. "+l.getId()+", size = "+l.getCil().size()+", naklad = "+l.getNaklad());
				kterou--;
			}else{
				naplnLod(l, ob, ob.getPotreba());
				for (int j = 0; j < ob.getKam().getCesta().size()-1; j++) {
					if(l.getNaklad()<unosnost){
						for (int j2 = 0; j2 < objednavky.size(); j2++) {
							Objednavka dalsiOb = objednavky.get(j2);
							Planeta dalsiP = ob.getKam().getCesta().get(j);
							if(dalsiOb.getKam().equals(dalsiP)){
								if((dalsiOb.getPotreba()+l.getNaklad())<unosnost){
									naplnLod(l, dalsiOb, dalsiOb.getPotreba());
								//	System.out.println("2. "+l.getId()+", size = "+l.getCil().size()+", naklad = "+l.getNaklad());
									if(unosnost-l.getNaklad()!=0){
										break;
										}
								}else{
									naplnLod(l, dalsiOb, unosnost-l.getNaklad());
								//	System.out.println("3. "+l.getId()+", size = "+l.getCil().size()+", naklad = "+l.getNaklad());
									ob.getOd().getDok().pop();
									logLod(l);
									break;
									
								}
							}
						}
					}
				}
				if(ob.getOd().getDok().size()==1){
					if(ob.getOd().getDok().peek().getNaklad()>0){
						ob.getOd().getDok().pop();
						logLod(l);
					}
				}
			}
	}
	
	public void vytvorObjednavky(){
		objednavky = g.getObjednavky();
		objednavky.sort(new FarComparator());
	}
	
	public long potrebaLeku(){
		long leku = 0;
		for (int i = 0; i < objednavky.size(); i++) {
			leku += (objednavky.get(i).getPotreba());
		}
		return leku;
	}
	
	public void logLod(Lod l){
		if(l.getCil().size()>0){
		int cas = 0;
		int vzdalenost = (int)(l.getCil().get(l.getCil().size()-1).vzdalenost/25);
		cas = vzdalenost + l.getCil().size();
		int casB = cas + vzdalenost;
		Soubor.getLogger().log(Level.INFO, "Lod č. "+l.getId()+
				" veze "+l.getNaklad()+" léků na "+l.getCil().size()+
				" planet. Loď je naplněna z "+(int)(l.getNaklad()/50000.0)+
				"%."+" Lod č."+l.getId()+" doručí svůj celý náklad "+cas+" den. A vrátí se "+casB+" den.");
		}
	}
	
	public void naplnLod(Lod l, Objednavka ob, int naklad){
		if(naklad != 0){		
			ob.setPotencial(ob.getPotencial()+naklad);
			l.setNaklad(l.getNaklad()+naklad);
			l.getCil().push(ob.getKam());
			l.setStav(1);
			l.setLokace(ob.getOd());
			l.setPosX(ob.getOd().getPosX());
			l.setPosY(ob.getOd().getPosY());
			l = getLod(ob.getOd());
			/*Soubor.getLogger().log(Level.INFO, "Lod �. "+l.getId()+
					" doveze "+naklad+" l�k� na planetu "+ob.getKam().getJmeno()+
					". Lo� je napln�na z "+(int)(l.getNaklad()/50000.0)+
					"% a cestou bude z�sobovat "+l.getCil().size()+" planet(u).");
					*/
		}
		if(l.getNaklad()>5000000){
			try {
				throw new Exception();
			} catch (Exception e) {
				System.out.println(("Naklad se nevesel na lod"));
				e.printStackTrace();
			}
		}
		//System.out.println("Lod �."+l.getId()+" se nakl�d� v doku "+((Stanice)l.getLokace()).getId());
	}
	
	private int counter = 1;
	public Lod getLod(Stanice s){
		if(s.getDok().size()==0){
			Lod l = new Lod(s, counter);
			counter++;
			lode.add(l);
			Soubor.getLogger().log(Level.INFO, "Proběhla výroba nové lodi č."+counter+" v doku Stanice "+(s.getId()-5000));
			return l;
		}else{return s.getDok().peek();}
	}
	
	public void start(Galaxie g, GUI gui){
		super.start();
		this.g = g;
	}
	
}


class FarComparator implements Comparator<Objednavka> {

	@Override
	public int compare(Objednavka o1, Objednavka o2) {
		if(o1.getVzdalenost()<o2.getVzdalenost()){
			return 1;
		}else if(o1.getVzdalenost()>o2.getVzdalenost()){
			return -1;
		}else{
		return 0;
		}
	}
   
}
