package PT;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Soubor {

	public static Galaxie nacti(){
		BufferedReader br = null;
		Galaxie g = new Galaxie(800, 5000);
		
		try {
			br = new BufferedReader(new FileReader(new File("Soubor.txt")));
			try {
				String[] pole;
				ArrayList<String[]> poleGalaxie = new ArrayList<String[]>(5005);
				br.readLine();
				for(int j = 0; j < 5005; j++){
					String nazev = br.readLine().trim();
					pole = nazev.split(";");
					poleGalaxie.add(pole);
				}
				for (int i = 0; i < poleGalaxie.size(); i++) {					
					pole = poleGalaxie.get(i);
					int id = Integer.parseInt(pole[0]);
					int posX = Integer.parseInt(pole[1]);
					int posY = Integer.parseInt(pole[2]);
					int pop = Integer.parseInt(pole[3]);
					if(i<5000){
						g.planety.add(new Planeta(id, posX, posY, pop));						
					}
					else{
						g.planety.add(new Planeta(id, posX, posY, pop));
						g.stanice.add(new Stanice(id, posX, posY));
					}
				}
				for (int i = 0; i < poleGalaxie.size(); i++) {
					pole = poleGalaxie.get(i);
					for (int j = 4; j < pole.length; j++) {	
 						int id = Integer.parseInt(pole[j]);
						g.planety.get(i).getSousedi().add(g.planety.get(id-1));
					}						
				}
				g.vytvorCesty(g.planety);
				g.generujNebezpecneCesty();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return g;
	}
	
	public static void uloz(String nazev, Galaxie g){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(nazev+".txt")));
			writer.print("id planety;x souradnice;y souradnice;pocet obyvatel;id sousedicich planet....\n");
			for (int i = 0; i < g.getPlanety().size(); i++) {
				Planeta a = g.getPlanety().get(i);
				writer.print(a.getId()+";"+a.getPosX()+";"+a.getPosY()+";"+a.getPop());
				for (int j = 0; j < a.getSousedi().size(); j++) {
					writer.print(";"+a.getSousedi().get(j).getId());
				}
				writer.print("\n");
			}
		
		
		}catch(Exception e){}finally{
			try{writer.close();}finally{}
		}
	}
}
