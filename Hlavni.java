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
		g = Soubor.nacti();
		g.projdi(g.getPlanety(), g.getPlanety().get(5000));
		g.projdi(g.getPlanety(), g.getPlanety().get(5001));
		g.projdi(g.getPlanety(), g.getPlanety().get(5002));
		g.projdi(g.getPlanety(), g.getPlanety().get(5003));
		g.projdi(g.getPlanety(), g.getPlanety().get(5004));
		for (int i = 0; i < g.getPlanety().size(); i++) {
			System.out.println(g.getPlanety().get(i).getVzdalenost());
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
