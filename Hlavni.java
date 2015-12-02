package pt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**************************************************
 * Instance třídy {@code Hlavni} představuje
 * hlvní třídu aplikace, která ma nastorosti 
 * chod aplikace.
 * 
 * @author Michal Štrunc a Jakub Váverka
 *
 */
public class Hlavni extends Application{
	
	/** galaxie*/
	private static Galaxie g;
	
	/** gui*/
	private static GUI gui = GUI.getINSTANCE();
	
	/*************************************************
	 * je hlavní metodou aplikace, která
	 * řídí chod aplikace.
	 * 
	 * @param args argumenty příkazové řádky
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args){
		//g = new Galaxie(800,5000);
		//g.generujVesmir();
		//Soubor.uloz("Soubor", g);
		g = Soubor.nacti();
		launch();
		gui.getCas().stop();
	}

	/************************************************
	 * spustí gui aplikace
	 */
	@Override
	public void start(Stage primaryStage){
		Scene s = new Scene(gui.createScene(g));
		primaryStage.setScene(s);
		primaryStage.show();
	}
	
	
	
	

}
