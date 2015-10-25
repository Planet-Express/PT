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
						if(ob.getPotreba()+l.getNaklad()>unosnost){
							ob.getOd().getDok().remove(0);
							l = getLod(ob.getOd());
							obsluzObjednavku(l, ob, unosnost);
						//	System.out.println("1. "+l.getId()+", size = "+l.getCil().size()+", naklad = "+l.getNaklad());
							cntr--;
						}else{
							obsluzObjednavku(l, ob, ob.getPotreba());
							for (int j = 0; j < ob.getKam().getCesta().size()-1; j++) {
								if(l.getNaklad()<unosnost){
									for (int j2 = 0; j2 < objednavky.size(); j2++) {
										Objednavka dalsiOb = objednavky.get(j2);
										Planeta dalsiP = ob.getKam().getCesta().get(j);
										if(dalsiOb.getKam().equals(dalsiP)){
											if((dalsiOb.getPotreba()+l.getNaklad())<unosnost){
												obsluzObjednavku(l, dalsiOb, dalsiOb.getPotreba());
											//	System.out.println("2. "+l.getId()+", size = "+l.getCil().size()+", naklad = "+l.getNaklad());
												if(unosnost-l.getNaklad()!=0){
													break;
													}
											}else{
												obsluzObjednavku(l, dalsiOb, unosnost-l.getNaklad());
											//	System.out.println("3. "+l.getId()+", size = "+l.getCil().size()+", naklad = "+l.getNaklad());
												ob.getOd().getDok().remove(0);
												break;
												
											}
										}
									}
								}
							}
						}
					cntr++;
					if(cntr==5000){cntr = 0;}
				}
				
				for (int i = 0; i < lode.size(); i++) {
					Lod l = lode.get(i);
					System.out.println(l.getId()+", size = "+l.getCil().size()+", naklad = "+l.getNaklad());
				}
				
				for (int i = 0; i < 30; i++) {
					////////////// ZACATEK DNE
					System.out.println("Zaèíná den "+(den+1)+", mìsíc "+(mesic));
					
					den++;
					System.out.println("Den "+den+" skonèil");
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
		if(naklad != 0){		
			ob.setPotencial(ob.getPotencial()+naklad);
			l.setNaklad(l.getNaklad()+naklad);
			l.getCil().push(ob.getKam());
			l.setStav(1);
			l = getLod(ob.getOd());
		}
		if(l.getNaklad()>5000000){
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println("Lod è."+l.getId()+" se nakládá v doku "+((Stanice)l.getLokace()).getId());
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
