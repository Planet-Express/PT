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
				for (int i = 0; i < objednavky.size(); i++) {
					Objednavka ob = objednavky.get(i);
					int unosnost = 5000000;
					Lod l = getLod(ob.getOd());
						if(ob.getKolik()>unosnost){
								l.setNaklad(unosnost);
								l.getCil().push(ob.getKam());
								l.setStav(1);
								ob.setPotencial(ob.getPotencial()+l.getNaklad());
								System.out.println("Lod è."+l.getId()+" se nakládá v doku "+((Stanice)l.getLokace()).getId());
								ob.getOd().getDok().remove(0);
						}else{
							l.setNaklad(ob.getKolik());
							l.getCil().push(ob.getKam());
							l.setStav(1);
							ob.setPotencial(ob.getPotencial()+l.getNaklad());
							System.out.println("Lod è."+l.getId()+" se nakládá v doku "+((Stanice)l.getLokace()).getId());					
						}
						if(ob.getKolik()-ob.getPotencial()>0){
							i--;
						}
					
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
