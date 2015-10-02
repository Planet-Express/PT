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
	GraphicsContext gc;
	final static int skok = 6;

	public static void main(String[] args){
		g = new Galaxie(800,5000);
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		Scene s = new Scene(root, 800, 800, Color.BLACK);

		final Canvas canvas = new Canvas(800,800);
		gc = canvas.getGraphicsContext2D();

		gc.setFill(Color.YELLOW);
		
		for (int i = 0; i < g.getPlanety().size(); i++) {
			int x = g.getPlanety().get(i).getPosX();
			int y = g.getPlanety().get(i).getPosY();
			int pop = g.getPlanety().get(i).getPop()/1000000;
			gc.setFill(getColor(pop));
			gc.fillOval(x-2, y-2, 4, 4);
			
		}
		
		root.getChildren().add(canvas);
		primaryStage.setScene(s);
		
		primaryStage.show();
	}
	
	public static Color getColor(int pop){		
		Color color = Color.hsb(skok*pop, 1, 1);
		return color;
	}

}
