package PT;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Hlavni extends Application{
	
	static Galaxie g;
	
	static GUI gui = new GUI();
	
	public static void main(String[] args){
		//g = new Galaxie(800,5000);
		//g.generujVesmir();
		//Soubor.uloz("Soubor", g);
		g = Soubor.nacti();
		g.projdi(g.getPlanety(), g.getPlanety().get(5000));
		g.projdi(g.getPlanety(), g.getPlanety().get(5001));
		g.projdi(g.getPlanety(), g.getPlanety().get(5002));
		g.projdi(g.getPlanety(), g.getPlanety().get(5003));
		g.projdi(g.getPlanety(), g.getPlanety().get(5004));
		for (int i = 0; i < g.getPlanety().size()-5; i++) {
			Planeta a = g.getPlanety().get(i);
			Planeta b = a.getCesta().get(0);
			do{
				if(b.getId()>5000){
					a.getCesta().add(b);
					break;
				}
				b = b.getCesta().get(0);
				a.getCesta().add(b);
			}while(b.getId()<=5000);		
		}
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
