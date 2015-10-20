package PT;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Hlavni extends Application{
	
	static Galaxie g;
	
	static GUI gui = new GUI();
	private static final int ROZMER = 800;
	
	public static void main(String[] args){
		//g = new Galaxie(ROZMER,5000);
		//g.generujVesmir();
		//Soubor.uloz("Soubor", g);
		g = Soubor.nacti();
		/*
		for (int i = 0; i < g.getCesty().size(); i++) {
			Planeta od = g.getCesty().get(i).getOd();
			Planeta kam = g.getCesty().get(i).getKam();
			if(!od.getSousedi().contains(kam)){
				System.out.println("d/pridano");
				od.getSousedi().add(kam);
			}
			if(!kam.getSousedi().contains(od)){
				System.out.println("k/pridano");
				double vzdalenost = g.vzdalenostPlanet(kam, od);
				for (int k = 0; k < kam.getSousedi().size(); k++) {
					if(vzdalenost <= g.vzdalenostPlanet(kam, kam.getSousedi().get(k))){
						kam.getSousedi().add(k, od);
						break;
					}
				}		
				
				
			}
		}
		for (int i = 0; i < g.getPlanety().size(); i++) {
			for (int j = 0; j < g.getPlanety().get(i).getSousedi().size(); j++) {
				if(g.getPlanety().get(i).getSousedi().size()>5){
				System.out.print(g.vzdalenostPlanet(g.getPlanety().get(i).getSousedi().get(j), g.getPlanety().get(i))+", ");
				}
			}
			if(g.getPlanety().get(i).getSousedi().size()>5){
			System.out.println();
			}
		}
		*/
		g.projdi(g.getPlanety(), g.getPlanety().get(5000));
		g.projdi(g.getPlanety(), g.getPlanety().get(5001));
		g.projdi(g.getPlanety(), g.getPlanety().get(5002));
		g.projdi(g.getPlanety(), g.getPlanety().get(5003));
		g.projdi(g.getPlanety(), g.getPlanety().get(5004));
		launch();
		gui.cas.stop();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene s = new Scene(gui.createScene(g));
		primaryStage.setScene(s);
		primaryStage.show();
	}
	
	
	
	

}
