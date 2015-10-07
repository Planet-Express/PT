package PT;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Soubor {

	public Galaxie nacti(){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File("soubor.txt")));
			try {
				String[] pole;
				for(int j = 0; j < 5005; j++){
					String nazev = br.readLine().trim();
					pole = nazev.split(",");
					System.out.println(Arrays.toString(pole));
				}
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
		return new Galaxie(100,100);
	}
	
	public void uloz(String nazev, Galaxie g){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(nazev+".txt")));
			for (int i = 0; i < g.getPlanety().size(); i++) {
				Planeta a = g.getPlanety().get(i);
				writer.print(a.getId()+","+a.getPosX()+","+a.getPosY()+","+a.getPop());
				for (int j = 0; j < a.getSousedi().size(); j++) {
					writer.print(","+a.getSousedi().get(j).getId());
				}
				writer.print("\n");
			}
		
		
		}catch(Exception e){}finally{
			try{writer.close();}finally{}
		}
	}
}
