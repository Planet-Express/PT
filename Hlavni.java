package PT;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Hlavni extends Application{
	
	static Galaxie g;

	public static void main(String[] args){
		g = new Galaxie(800,5000);
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		Scene s = new Scene(root, 800, 800, Color.BLACK);

		final Canvas canvas = new Canvas(800,800);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.setFill(Color.YELLOW);
		
		for (int i = 0; i < g.getPlanety().size(); i++) {
			int x = g.getPlanety().get(i).getPosX();
			int y = g.getPlanety().get(i).getPosY();
			int pop = g.getPlanety().get(i).getPop()/1000000;
			switch(pop){
			case 0: gc.setFill(Color.hsb(1, 1, 0.1));
			break;
			case 1: gc.setFill(Color.hsb(1, 1, 0.2));
			break;
			case 2: gc.setFill(Color.hsb(1, 1, 0.3));
			break;
			case 3: gc.setFill(Color.hsb(1, 1, 0.4));
			break;
			case 4: gc.setFill(Color.hsb(1, 1, 0.5));
			break;
			case 5: gc.setFill(Color.hsb(1, 1, 0.6));
			break;
			case 6: gc.setFill(Color.hsb(1, 1, 0.7));
			break;
			case 7: gc.setFill(Color.hsb(1, 1, 0.8));
			break;
			case 8: gc.setFill(Color.hsb(1, 1, 0.9));
			break;
			case 9: gc.setFill(Color.hsb(1, 1, 1));
			break;
			default: gc.setFill(Color.WHITE);
			}
			gc.fillOval(x-2, y-2, 4, 4);
			
		}
		
		root.getChildren().add(canvas);
		primaryStage.setScene(s);
		
		primaryStage.show();
	}

}
