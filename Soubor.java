package PT;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Soubor {

	public Galaxie nacti(){
	//try{}catch(Exception e){}
		return new Galaxie(100,100);
	}
	
	public void uloz(String nazev, Galaxie g){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(nazev+".txt")));
			for (int i = 0; i < g.getPlanety().size(); i++) {
				Planeta a = g.getPlanety().get(i);
				writer.print("{"+a.getId()+"}"+"["+a.getPosX()+","+a.getPosY()+"]"+"|"+a.getPop()+"|");
				for (int j = 0; j < a.getSousedi().size(); j++) {
					writer.print(":"+a.getSousedi().get(j).getId()+":");
				}
				System.out.println();
			}
		
		
		}catch(Exception e){}finally{
			try{writer.close();}finally{}
		}
	}
}
