package PT;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Hlavni extends Application{
	
	static Galaxie g;
	
	static GUI gui = new GUI();
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args){
		//g = new Galaxie(800,5000);
		//g.generujVesmir();
		//Soubor.uloz("Soubor", g);
		g = Soubor.nacti();
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
