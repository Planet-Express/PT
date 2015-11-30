package pt;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Soubor {

	private final  static Logger LOGGER = Logger.getLogger(Soubor.class.getName());
	
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
						g.getPlanety().add(new Planeta(id, posX, posY, pop));						
					}
					else{
						g.getPlanety().add(new Planeta(id, posX, posY, pop));
						g.getStanice().add(new Stanice(id, posX, posY));
					}
				}
				System.out.println(System.nanoTime()-time+" -- planety vytvoreny");
				for (int i = 0; i < poleGalaxie.size(); i++) {
					polePlaneta = poleGalaxie.get(i);
					for (int j = 4; j < polePlaneta.length; j++) {	
 						int id = Integer.parseInt(polePlaneta[j]);
						g.getPlanety().get(i).getSousedi().add(g.getPlanety().get(id-1));
					}						
				}
				System.out.println(System.nanoTime()-time+" -- pridani sousedi");
				for (int i = 0; i < poleCest.size()-5; i++) {
					poleCesta = poleCest.get(i);
					Planeta p = g.getPlanety().get(Integer.parseInt(poleCesta[0])-1);
					p.setVzdalenost(Double.parseDouble(poleCesta[1]));
					for (int j = 2; j < poleCesta.length; j++) {
						if(j<(poleCesta.length-1)){
							p.cesta.add(g.getPlanety().get(Integer.parseInt(poleCesta[j])-1));
						}else{
							p.cesta.add(g.getStanice().get(Integer.parseInt(poleCesta[j])-5001));
						}
					}
				}
				System.out.println(System.nanoTime()-time+" -- pridana min cesta");
				g.vytvorCesty(g.getPlanety());
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
	
	public static Logger getLogger(){
		return LOGGER;
	}
	
	public static void initLogger(){
		FileHandler f = null;
		try {
			f = new FileHandler("Log.log", false);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		SimpleFormatter formater = new SimpleFormatter();
		f.setFormatter(formater);
		f.setLevel(Level.SEVERE);
		LOGGER.setLevel(Level.SEVERE);
		LOGGER.addHandler(f);
		
	}
	
	
	public static void uloz(String nazev, Galaxie g){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(nazev+".txt")));
			writer.print("id planety;x souradnice;y souradnice;pocet obyvatel;id sousedicich planet....\n");
			writer.print("id planety;vzdalenost od stanice;id planet na ceste;...;id stanice\n");
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
	
	public static void vytvorStatistiku(String nazev, Counter cas, ArrayList<Planeta> planety){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(nazev+".txt")));
			for (int i = 0; i < cas.getStatistikaObjednavek().size(); i++) {
				writer.println("..............---------/////////|||MĚSÍC "+i+".|||\\\\\\\\\\\\\\\\\\---------..............");
				for (int j = 0; j < cas.getStatistikaObjednavek().get(i).size(); j++) {
					writer.println("Planeta "+cas.getStatistikaObjednavek()
								.get(i).get(j).getKam().getId()+" {"+
								cas.getStatistikaObjednavek()
								.get(i).get(j).getKam().getJmeno()+"} si objednala "+
								cas.getStatistikaObjednavek()
								.get(i).get(j).getPuvodni());
					for (int j2 = 0; j2 < cas.getStatistikaObjednavek().get(i).get(j).getKam().getDoruceno().size(); j2++) {
						if(cas.getStatistikaObjednavek().get(i).get(j).getKam().getDoruceno().get(j2).getDen()/30==i){
						writer.println("\tLoď "+cas.getStatistikaObjednavek()
						.get(i).get(j).getKam().getDoruceno().get(j2).getLod().getId()+" doručila "+cas.getStatistikaObjednavek()
						.get(i).get(j).getKam().getDoruceno().get(j2).getKolik());
						}
					}
					Planeta a = cas.getStatistikaObjednavek().get(i).get(j).getKam();
					if(a.getObyvatelsto().get(i)>a.getObyvatelsto().get(i+1)&&a.getObyvatelsto().size()>i+1){
						writer.println("\tUmřelo "+(a.getObyvatelsto().get(i)-a.getObyvatelsto().get(i+1)));
					}
					
				}
			}
			
			for (int i = 0; i < planety.size(); i++) {
				writer.println("Planeta "+planety.get(i).getId()+" {"+
				planety.get(i).getJmeno()+"} vývoj populace "+Arrays.toString(planety.get(i).getObyvatelsto().toArray()));
			}
			
			
		
		}catch(Exception e){}finally{
			try{writer.close();}finally{}
		}
	}
}
