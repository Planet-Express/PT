package PT;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Hlavni extends Application{
	
	static Galaxie g;
	GraphicsContext gc;
	final static int skok = 6;

	public static void main(String[] args){
		g = new Galaxie(800,5000);
		g.generujVesmir();
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene s = new Scene(createScene(), 800, 800, Color.BLACK);
		/*Group root = new Group();
		Scene s = new Scene(root, 800, 800, Color.BLACK);
		final Canvas canvas = new Canvas(800,800);
		gc = canvas.getGraphicsContext2D();
		///////////
		gc.scale(0.75, 0.75);
		root = new Group();
		s = new Scene(root, 800*0.75, 800*0.75, Color.BLACK);
		//////////
		gc.setFill(Color.YELLOW);
		nakresliCesty();
		nakresliPlanety();
		nakresliStanice();
		root.getChildren().add(canvas);
		*/
		primaryStage.setScene(s);
		primaryStage.show();
	}
	
	private Parent createScene() {
		BorderPane bp = new BorderPane();
		bp.setCenter(getCenter());
		return bp;
	}

	private Node getCenter() {
		FlowPane fp = new FlowPane();
		final Canvas canvas = new Canvas(800,800);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.YELLOW);
		nakresliCesty();
		nakresliPlanety();
		nakresliStanice();
		fp.getChildren().add(canvas);
		return fp;
	}

	private void nakresliStanice() {
		for(int i = 0; i < g.getStanice().size(); i++){
			int x = g.getStanice().get(i).getPosX();
			int y = g.getStanice().get(i).getPosY();
			gc.setFill(Color.BLUE);
			gc.fillOval(x-3, y-3, 6, 6);
			gc.setStroke(Color.BLUE);
			gc.strokeOval(x-150, y-150, 300, 300);
		}
	}

	private void nakresliPlanety(){
		for (int i = 0; i < g.getPlanety().size(); i++) {
			int x = g.getPlanety().get(i).getPosX();
			int y = g.getPlanety().get(i).getPosY();
			int pop = g.getPlanety().get(i).getPop()/1000000;
			gc.setFill(getColor(pop));
			gc.fillOval(x-2, y-2, 4, 4);
		}		
	}
	
	private void nakresliCesty(){
		gc.setStroke(Color.GREEN);
		for (int i = 0; i < g.getCesty().size(); i++) {
			Planeta a = g.getCesty().get(i).getOd();
			int x1 = a.getPosX();
			int y1 = a.getPosY();
			Planeta b = g.getCesty().get(i).getKam();
			int x2 = b.getPosX();
			int y2 = b.getPosY();
			if(g.getCesty().get(i).isNebezpecna()){
				gc.setStroke(Color.FIREBRICK);
				gc.strokeLine(x1, y1, x2, y2);
				gc.setStroke(Color.GREEN);
			}else{
				gc.strokeLine(x1, y1, x2, y2);
			}
			
		}
	}
	
	private static Color getColor(int pop){		
		Color color = Color.hsb(50-(skok*pop), 1, 1);
		return color;
	}

}
