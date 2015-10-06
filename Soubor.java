package PT;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Soubor {

	public Galaxie nacti(){
		return new Galaxie(10,10);
	}
	
	public void uloz(String nazev, Galaxie g){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(nazev+".txt")));
			for (int i = 0; i < g.getPlanety().size(); i++) {
				Planeta a = g.getPlanety().get(i);
				writer.println("{"+a.getId()+"}"+"["+a.getPosX()+","+a.getPosY()+"]"+"|"+a.getPop()+"|");
				
			}
		
		
		}catch(Exception e){}finally{
			try{writer.close();}finally{}
		}
	}
}
