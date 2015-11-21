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
				for (int i = 0; i < 30; i++) {
					////////////// ZACATEK DNE
					Soubor.getLogger().log(Level.SEVERE, "Začíná den "+(den+1)+", měsíc "+(mesic));
					for (int j = 0; j < objednavky.size(); j++) {
						if((objednavky.get(j).getKam().getVzdalenost()/25+7+den%30)<30){
							if(!objednavky.get(j).getKam().isMrtva()){
								while(objednavky.get(j).getPotreba()>0){
									obsluzObjednavku(j);
								}
							}
						}
					}
					for (int j = 0; j < lode.size(); j++) {
						posunLeticiLod(lode.get(j));
						vylozLod(lode.get(j));
					}
					/*
					for (int j = 0; j < g.getStanice().size(); j++) {
						for (int j2 = 0; j2 < g.getStanice().get(j).getDok().size(); j2++) {
							if(g.getStanice().get(j).getDok().get(j2).getStav()==-1){
								System.out.println(g.getStanice().get(j).getDok().get(j2).getId());
							}
						}
					}	
					*/				
					den++;
					Soubor.getLogger().log(Level.SEVERE, "Den "+den+" skončil");
					zacniVykladatLode();
					vysliNalozeneLode();
					if(den%30==0){
						zabijLidi();
						System.out.println(nadvyrobenoLeku());
					}
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
	
	int i = 0;
	public void vylozLod(Lod l){
		if(l.getStav()==3){
			Planeta a = l.getCil().pop();
			int naklad = l.getRozpis().pop();
			l.setNaklad(l.getNaklad()-naklad);
			a.getObjednavka().setKolik(a.getObjednavka().getKolik()-naklad);
			a.getObjednavka().setPotencial(a.getObjednavka().getPotencial()-naklad);
			if(a.getObjednavka().getKolik() == 0){
				//System.out.println("Objednavka "+ a.getId()+ " kolik? " + a.getObjednavka().getKolik()+ " "+ i);
			}
			l.setStav(0);
		}
	}
	int okradeno = 0;
	public void okradLod(Lod l){
		okradeno++;
		l.setNaklad(0);
		for (int i = 0; i < l.getCil().size(); i++) {
			Planeta p = l.getCil().get(i);
			for (int j = 0; j < objednavky.size(); j++) {
				Objednavka ob = objednavky.get(j);
				if(ob.getKam().equals(p)){
					ob.setPotencial(ob.getPotencial()-l.getRozpis().pop());
				}
			}
		}
		l.getCil().clear();
		//System.out.println("Lod "+l.getId()+" byla okradena. "+ okradeno +" za tento rok.");
	}
	
	public void posunLeticiLod(Lod l){
		l.setChciNa();
		if(l.getLokace() instanceof Planeta){
			if(((Planeta)l.getLokace()).getId() == l.getChciNa().getId()&&l.getChciNa().getId()>5000&&l.getCil().size()==0){
				if(l.getStav()!=-1){
					l.getStart().getDok().push(l);
					l.setStav(-1);
				}
			}
			
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
			while(cesta<doleti){
				if(budeOkradena(l)){
					break;
				}else{
					l.setLokace(l.getChciNa());
					l.setChciNa();
					doleti-=cesta;
					if(l.getLokace() instanceof Planeta){
						if(l.getCil().size()>0){
							if(l.getLokace().equals(l.getCil().peek())){
								l.setStav(2);
								cesta = 0;
								break;
							}
							else{
								cesta = g.vzdalenostPlanet((Planeta)l.getLokace(), l.getChciNa());
							}
						}else{
							if(((Planeta)l.getLokace()).getId() == l.getStart().getId()){
								if(l.getStav()!=-1){
								l.getStart().getDok().push(l);
								l.setStav(-1);
								}
								break;
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
					if(!budeOkradena(l)){
						if(!l.getLokace().equals(l.getChciNa())){
						l.setLokace(najdiCestu((Planeta)l.getLokace(), l.getChciNa()));
						l.setProcentaCesty(1-((cesta-doleti)/cesta));
						}
						if(cesta==doleti){
							l.setLokace(l.getChciNa());
							l.setChciNa();
						}
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
	
	public boolean budeOkradena(Lod l){
		l.setChciNa();
		if(l.getNaklad()>0){
			if(l.getLokace() instanceof Planeta){
				if(((Planeta)l.getLokace()).getId()!=l.getChciNa().getId()){
					Cesta c = najdiCestu((Planeta)l.getLokace(), l.getChciNa());
					if(c.isNebezpecna()){
						if(Math.random()<0.1){
							okradLod(l);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public void obsluzObjednavku(int kterou){
		Objednavka ob = objednavky.get(kterou);
		if(ob.getPotreba()>0){
			int unosnost = 5000000;
			Lod l = getLod(ob.getOd());
				if((ob.getPotreba()+l.getNaklad())>unosnost){
					if(l.getNaklad()!=0){
					ob.getOd().getDok().pop();
					logLod(l);
					l = getLod(ob.getOd());
					}
					naplnLod(l, ob, unosnost);
					l.getStart().getDok().pop();
					l = getLod(ob.getOd());
				//System.out.println("1. "+l.getId()+", size = "+l.getCil().size()+", naklad = "+l.getNaklad());
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
										l=getLod(ob.getOd());
										break;
										
									}
								}
							}
						}
					}
					if(ob.getOd().getDok().size()>0){
						if(ob.getOd().getDok().peek().getNaklad()>0){
							ob.getOd().getDok().pop();
							logLod(l);
							l = getLod(ob.getOd());
						}
					}
				}
		}
	}
	
	public void vytvorObjednavky(){
		objednavky = getObjednavky();
		objednavky.sort(new FarComparator());
	}
	
	public long potrebaLeku(){
		long leku = 0;
		for (int i = 0; i < objednavky.size(); i++) {
			leku += (objednavky.get(i).getPotreba());
		}
		return leku;
	}

	public long nadvyrobenoLeku(){
		long leku = 0;
		for (int i = 0; i < objednavky.size(); i++) {
			leku += (objednavky.get(i).getPotencial());
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
			l.getRozpis().push(naklad);
			l.setStav(1);
			l.setLokace(ob.getOd());
			l.setPosX(ob.getOd().getPosX());
			l.setPosY(ob.getOd().getPosY());
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
				System.out.println(("Naklad se nevesel na lod "+l.getId()));
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
	
	
	public ArrayList<Objednavka> getObjednavky(){
		ArrayList<Objednavka> objednavky = new ArrayList<Objednavka>();
		
		for (int i = 0; i < g.getPlanety().size()-5; i++) {
			Planeta a = g.getPlanety().get(i);
			int objednavka = a.getPop()-a.vyrobLeky();
			Stanice sc = (Stanice)a.getCesta().get(a.getCesta().size()-1);
			Objednavka ob = new Objednavka(a, sc,objednavka, a.getVzdalenost(), i+1);
			objednavky.add(ob);
			Soubor.getLogger().log(Level.INFO, "Planeta "+a.getJmeno()+ " poslala objednávku na "+objednavka+" léků.");
			a.setObjednavka(ob);
		}
 		return objednavky;
	}
	
	public void zabijLidi(){
		long mrtvych = 0;
		for (int i = 0; i < objednavky.size(); i++) {
			Objednavka ob = objednavky.get(i);
			ob.getKam().zabij(ob.getPotreba());
			mrtvych+=ob.getPotreba();
		}
		//System.out.println("Zemřelo "+mrtvych+" lidí.");
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
