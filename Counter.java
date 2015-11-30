package pt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;

import javafx.application.Platform;

public class Counter extends Thread{
	
	Galaxie g;
	GUI gui;
	ArrayList<Lod> lode = new ArrayList<Lod>();
	ArrayList<Objednavka> objednavky = new ArrayList<Objednavka>();
	ArrayList<ArrayList<Objednavka>> statistikaObjednavek = new ArrayList<ArrayList<Objednavka>>();
	List<Long> celkoveVyrobeno = new ArrayList<Long>();
	List<Long> celkoveUkradeno = new ArrayList<Long>();
	List<Long> celkovaPopulace = new ArrayList<Long>();
	List<Long> celkoveUmrti = new ArrayList<Long>();

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
							if(!objednavky.get(j).getKam().isMrtva()){
									obsluzObjednavku(j, den);
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
					Platform.runLater(new Runnable() {
				            @Override
				            public void run() {
				            	gui.prekresliPlatno();
				            }
				    });    
					Soubor.getLogger().log(Level.SEVERE, "Den "+den+" skončil");
					zacniVykladatLode();
					vysliNalozeneLode();
					if(den%30==0){
						zabijLidi();
						if(nadvyrobenoLeku()!=0){
							throw new RuntimeException("Byly vyrobeny léky, které jsme nestihli dodat.");
						}
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
	
	public ArrayList<Lod> getLode(){
		return this.lode;
	}
	
	public ArrayList<ArrayList<Objednavka>> getStatistikaObjednavek(){
		return this.statistikaObjednavek;
	}
	
	int i = 0;
	public void vylozLod(Lod l){
		if(l.getStav()==3){

			Planeta a = l.getCil().pop();
			int naklad = l.getRozpis().pop();
			Soubor.getLogger().log(Level.FINE,"Lod č."+l.getId()+
					" vyložila na "+a.toString()+
					" náklad "+naklad+" léků.");
			a.getDoruceno().add(new Doruceni(naklad, l, den));
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
					if(ob.getPotencial()<l.getRozpis().get(i)){
						System.out.println(ob.getPotencial()+" "+l.getRozpis().get(i));
					}
					ob.setPotencial(ob.getPotencial()-l.getRozpis().get(i));
				}
			}
		}
		l.getCil().clear();
		l.getRozpis().clear();
		//System.out.println("Lod "+l.getId()+" byla okradena. "+ okradeno +" za tento rok.");
	}
	
	public void posunLeticiLod(Lod l){
		if(l.getStav() == 0){
			if(l.getCil().size()>0){
				Soubor.getLogger().log(Level.FINE,"Lod č."+l.getId()+
					" letí na "+Arrays.toString(l.getCil().toArray())+
					" s nakladem "+l.getNaklad());
			}else{
				Soubor.getLogger().log(Level.FINE,"Lod č."+l.getId()+
						" se vrací do Stanice.");
				
			}
		l.setChciNa();
		if(l.getLokace() instanceof Planeta){
			if(((Planeta)l.getLokace()).getId() == l.getChciNa().getId()&&l.getChciNa().getId()>5000&&l.getCil().size()==0){
				if(l.getStav()!=-1){
					l.getStart().getDok().push(l);
					l.setStav(-1);
					l.resetLod();
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
								l.resetLod();
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
	
	public void obsluzObjednavku(int kterou, int den){
		Objednavka ob = objednavky.get(kterou);
		while(ob.getPotreba()>0){
			int unosnost = 5000000;
			Lod l = getLod(ob.getOd());
			if(l.getNaklad()!=0){
				ob.getOd().getDok().pop();
				logLod(l,den);
				l = getLod(ob.getOd());
				}
			if(l.stihne(ob, den)){
				if((ob.getPotreba()+l.getNaklad())>unosnost){
					naplnLod(l, ob, unosnost);
					l.getStart().getDok().pop();
					l = getLod(ob.getOd());
				//System.out.println("1. "+l.getId()+", size = "+l.getCil().size()+", naklad = "+l.getNaklad());
				}else{
					naplnLod(l, ob, ob.getPotreba());
					for (int j = 0; j < ob.getKam().getCesta().size()-1; j++) {
						if(l.getNaklad()<unosnost&&l.stihne(ob, den)){
							for (int j2 = 0; j2 < objednavky.size(); j2++) {
								Objednavka dalsiOb = objednavky.get(j2);
								Planeta dalsiP = ob.getKam().getCesta().get(j);
								if(dalsiOb.getKam().equals(dalsiP)&&l.stihne(ob, den)){
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
										logLod(l,den);
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
							logLod(l,den);
							l = getLod(ob.getOd());
						}
					}
				}
			}else{break;}
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
			if(objednavky.get(i).getPotencial()>0){
			leku += (objednavky.get(i).getPotencial());
			for (int j = 0; j < lode.size(); j++) {
				Lod l = lode.get(j);
				for (int j2 = 0; j2 < l.getCil().size(); j2++) {
					if(l.getCil().get(j2).equals(objednavky.get(i).getKam())){
						System.out.println(l.getId());
						/*try {
							this.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
					}
				}
			}
			}
		}
		return leku;
	}
	
	public void logLod(Lod l, int den){
		if(l.getCil().size()>0){
		int cas = 0;
		int vzdalenost = (int)(l.getCil().get(l.getCil().size()-1).vzdalenost/25);
		cas = vzdalenost + l.getCil().size()+den;
		int casB = cas + vzdalenost;
		Soubor.getLogger().log(Level.INFO,"Lod č. "+l.getId()+
				" veze "+l.getNaklad()+" léků na "+l.getCil().size()+
				" planet. Loď je naplněna z "+(int)(l.getNaklad()/50000.0)+
				"%."+" Lod č."+l.getId()+" doručí svůj celý náklad "+cas+" den. A vrátí se "+casB+" den.");
		}
	}
	
	public void naplnLod(Lod l, Objednavka ob, int naklad){
		if(naklad != 0){
			if(celkoveVyrobeno.size()==mesic){
				celkoveVyrobeno.add((long)naklad);
			}else{
				celkoveVyrobeno.set(mesic, celkoveVyrobeno.get(mesic)+naklad);
			}
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
			throw new RuntimeException("Moc velký náklad");
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
			if(!a.isMrtva()){
				int objednavka = a.getPop()-a.vyrobLeky();
				Stanice sc = (Stanice)a.getCesta().get(a.getCesta().size()-1);
				Objednavka ob = new Objednavka(a, sc,objednavka, a.getVzdalenost());
				objednavky.add(ob);
				Soubor.getLogger().log(Level.INFO, "Planeta "+a.getJmeno()+ " poslala objednávku na "+objednavka+" léků.");
				a.setObjednavka(ob);
			}
		}
		statistikaObjednavek.add(objednavky);
 		return objednavky;
	}
	
	public void zabijLidi(){
		for (int i = 0; i < g.getPlanety().size()-5; i++) {
			if(g.getPlanety().get(i).isMrtva()){
				g.getPlanety().get(i).zabij(g.getPlanety().get(i).getPop()-g.getPlanety().get(i).vyrobLeky());
			}else{
				Objednavka ob = g.getPlanety().get(i).getObjednavka();
				ob.getKam().zabij(ob.getPotreba());
				if(celkoveUmrti.size()==mesic){
					celkoveVyrobeno.add((long)ob.getPotreba());
				}else{
					celkoveUmrti.set(mesic, celkoveUmrti.get(mesic)+ob.getPotreba());
				}
			}
		}
		//System.out.println("Zemřelo "+mrtvych+" lidí.");
	}
	
	public void start(Galaxie g, GUI gui){
		super.start();
		this.g = g;
		this.gui = gui;
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
