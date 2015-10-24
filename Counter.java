package PT;

import java.util.ArrayList;
import java.util.Comparator;

public class Counter extends Thread{

	Galaxie g;
	GUI gui;
	ArrayList<Lod> lode = new ArrayList<Lod>();
	ArrayList<Objednavka> objednavky = new ArrayList<Objednavka>();
	int den = 0;
	int mesic = 0;
	
	@SuppressWarnings("static-access")
	public void run(){
		synchronized (this) {
			while(true){
				
				////////////////ZACATEK MESICE
				objednavky = g.getObjednavky();
				objednavky.sort(new FarComparator());
				int cntr = 0;
				while (potrebaLeku()>0) {
					Objednavka ob = objednavky.get(cntr);
					int unosnost = 5000000;
					Lod l = getLod(ob.getOd());
						if(ob.getKolik()>unosnost){
							obsluzObjednavku(l, ob, unosnost);
							ob.getOd().getDok().remove(0);
						}else{
							obsluzObjednavku(l, ob, ob.getKolik());	
							for (int j = 0; j < ob.getKam().getCesta().size()-1; j++) {
								if(l.getNaklad()<unosnost){
									for (int j2 = 0; j2 < objednavky.size(); j2++) {
										Objednavka dalsiOb = objednavky.get(j2);
										Planeta dalsiP = ob.getKam().getCesta().get(j);
										if(dalsiOb.getKam().equals(dalsiP)){
											if(dalsiOb.getKolik()+l.getNaklad()<=unosnost){
												obsluzObjednavku(l, dalsiOb, dalsiOb.getKolik());
												break;
											}else{
												obsluzObjednavku(l, dalsiOb, unosnost-l.getNaklad());
												ob.getOd().getDok().remove(0);
												break;
											}
										}
									}
								}
							}
						}
					cntr++;
					System.out.println(potrebaLeku());
					for (int i = 0; i < objednavky.size(); i++) {
						if(objednavky.get(i).getPotencial()>objednavky.get(i).getKolik()){System.out.println("fail");}
					}
				}
				/*
				for (int i = 0; i < lode.size(); i++) {
					Lod l = lode.get(i);
					System.out.println(l.getId()+", size = "+l.getCil().size()+", naklad = "+l.getNaklad());
				}
				*/
				for (int i = 0; i < 30; i++) {
					////////////// ZACATEK DNE
					System.out.println("Za��n� den "+(den+1)+", m�s�c "+(mesic));
					
					den++;
					System.out.println("Den "+den+" skon�il");
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
	
	public long potrebaLeku(){
		long leku = 0;
		for (int i = 0; i < objednavky.size(); i++) {
			leku += (objednavky.get(i).getKolik()-objednavky.get(i).getPotencial());
		}
		return leku;
	}
	public void obsluzObjednavku(Lod l, Objednavka ob, int naklad){
		l.setNaklad(l.getNaklad()+naklad);
		l.getCil().push(ob.getKam());
		l.setStav(1);
		ob.setPotencial(ob.getPotencial()+l.getNaklad());
		//System.out.println("Lod �."+l.getId()+" se nakl�d� v doku "+((Stanice)l.getLokace()).getId());
	}
	private int counter = 1;
	public Lod getLod(Stanice s){
		if(s.getDok().size()==0){
			Lod l = new Lod(s, counter);
			counter++;
			lode.add(l);
			return l;
		}else{return s.getDok().get(0);}
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
