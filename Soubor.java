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
		long time = System.nanoTime();
		System.out.println(System.nanoTime()-time+" -- Start");
		try {
			br = new BufferedReader(new FileReader(new File("Soubor.txt")));
			try {
				String[] polePlaneta;
				String[] poleCesta;
				ArrayList<String[]> poleGalaxie = new ArrayList<String[]>(5005);
				ArrayList<String[]> poleCest = new ArrayList<String[]>(5005);
				br.readLine();
				for(int j = 0; j < 5005; j++){
					String nazev = br.readLine().trim();
					polePlaneta = nazev.split(";");
					poleGalaxie.add(polePlaneta);
					nazev = br.readLine().trim();
					poleCesta = nazev.split(";");
					poleCest.add(poleCesta);
				}
				System.out.println(System.nanoTime()-time+" -- pole vytvoreno");
				for (int i = 0; i < poleGalaxie.size(); i++) {					
					polePlaneta = poleGalaxie.get(i);
					int id = Integer.parseInt(polePlaneta[0]);
					int posX = Integer.parseInt(polePlaneta[1]);
					int posY = Integer.parseInt(polePlaneta[2]);
					int pop = Integer.parseInt(polePlaneta[3]);
					if(i<5000){
						g.planety.add(new Planeta(id, posX, posY, pop));						
					}
					else{
						g.planety.add(new Planeta(id, posX, posY, pop));
						g.stanice.add(new Stanice(id, posX, posY));
					}
				}
				System.out.println(System.nanoTime()-time+" -- planety vytvoreny");
				for (int i = 0; i < poleGalaxie.size(); i++) {
					polePlaneta = poleGalaxie.get(i);
					for (int j = 4; j < polePlaneta.length; j++) {	
 						int id = Integer.parseInt(polePlaneta[j]);
						g.planety.get(i).getSousedi().add(g.planety.get(id-1));
					}						
				}
				System.out.println(System.nanoTime()-time+" -- pridani sousedi");
				for (int i = 0; i < poleCest.size()-5; i++) {
					poleCesta = poleCest.get(i);
					Planeta p = g.planety.get(Integer.parseInt(poleCesta[0])-1);
					p.setVzdalenost(Double.parseDouble(poleCesta[1]));
					for (int j = 2; j < poleCesta.length; j++) {
						if(j<(poleCesta.length-1)){
							p.cesta.add(g.planety.get(Integer.parseInt(poleCesta[j])-1));
						}else{
							p.cesta.add(g.stanice.get(Integer.parseInt(poleCesta[j])-5001));
						}
					}
				}
				System.out.println(System.nanoTime()-time+" -- pridana min cesta");
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
				writer.print("\n" + a.getId() + ";" + a.getVzdalenost());
				for (int j = 0; j < a.getCesta().size(); j++) {
					writer.print(";" + a.getCesta().get(j).getId());
					
				}
				writer.print("\n");
			}
		
		
		}catch(Exception e){}finally{
			try{writer.close();}finally{}
		}
	}
}
