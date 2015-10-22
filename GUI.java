package PT;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class GUI{

	Counter cas = new Counter();
	static Affine af;
	final static int skok = 6;
	static Galaxie g;
	static int zoom = 0;
	static int quality = 1;
	static Canvas canvas = new Canvas(800*quality,800*quality);
	static GraphicsContext gc = canvas.getGraphicsContext2D();
	
	
	public Parent createScene(Galaxie galaxy) {
		g = galaxy;
		BorderPane bp = new BorderPane();
		canvas.setScaleX(1.0/quality*0.9);
		canvas.setScaleY(1.0/quality*0.9);
		bp.setPrefSize(800, 600);
		bp.setLeft(getControlBar());
		bp.setCenter(getCenter());
		testCesty();
		return bp;
	}

	private Node getControlBar() {
		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		Button generuj = new Button("Generuj");
		generuj.setMinWidth(100);
		Button start = new Button("Start");
		start.setMinWidth(100);
		generuj.setOnAction(event -> {
			g = new Galaxie(800, 5000);
			g.generujVesmir();
			Soubor.uloz("Soubor", g);
			prekresliPlatno();
		});
		start.setOnAction(event -> {
			cas.start(g,this);
		});
		
		Button test = new Button("Test");
		test.setMinWidth(100);
		test.setOnAction(event -> {
			testCesty();
		});
		
		
		Slider slid = new Slider();
		slid.setPrefSize(100, 15);
		slid.setMin(0);
		slid.setMax(90);
		slid.setValue(0);
		slid.setOnMouseDragged(event -> {
			zoom = (int)(slid.getValue());
			quality = 1 + zoom/(20);
			GUI.canvas.setHeight(800*quality);
			GUI.canvas.setWidth(800*quality);
			prekresliPlatno();
		});
		vb.getChildren().addAll(test,start,generuj,slid);
		return vb;
	}

	
	public static void prekresliPlatno() {
		canvas.setScaleX(1.0/quality*0.9  * 100.0/(101-zoom));
		canvas.setScaleY(1.0/quality*0.9  * 100.0/(101-zoom));
		//gc.setTransform(af);
		//gc.scale(100.0/(101-zoom),100.0/(101-zoom));
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, 800*quality, 800*quality);
		nakresliCesty();
		nakresliPlanety();
		nakresliStanice();
	}

	
	
	private static Node getCenter() {
		ScrollPane sc = new ScrollPane();
		af = gc.getTransform();
	//	fp.setScaleX(0.85);
	//	fp.setScaleY(0.85);
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, 800*quality, 800*quality);
		nakresliCesty();
		nakresliPlanety();
		nakresliStanice();
		Group g = new Group();
		g.getChildren().addAll(canvas);
		sc.setContent(g);
		return sc;
	}
	
	@SuppressWarnings("unused")
	private Node getScrollPane(){
		ScrollPane sc = new ScrollPane();
		return sc;
	}

	public static void nakresliStanice() {
		for(int i = 0; i < g.getStanice().size(); i++){
			int x = g.getStanice().get(i).getPosX()*quality;
			int y = g.getStanice().get(i).getPosY()*quality;
			gc.setFill(Color.BLUE);
			gc.fillOval(x-3*quality, y-3*quality, 6*quality, 6*quality);
		}
	}

	public static void nakresliPlanety(){
		for (int i = 0; i < g.getPlanety().size(); i++) {
			int x = g.getPlanety().get(i).getPosX()*quality;
			int y = g.getPlanety().get(i).getPosY()*quality;
			int pop = g.getPlanety().get(i).getPop()/1000000;
			int vzd = (int)g.getPlanety().get(i).getVzdalenost()/10;
			gc.setFill(getColor(pop));
			gc.fillOval(x-2*quality, y-2*quality, 4*quality, 4*quality);
		}		
	}
	
	
	public static void testCesty(){
		Planeta z = g.getPlanety().get((int)(Math.random()*300));
		Planeta z1 = g.getPlanety().get((int)(Math.random()*300));
		Planeta z2 = g.getPlanety().get((int)(Math.random()*300));
		gc.setFill(Color.RED);
		gc.fillOval(z.getPosX()-10, z.getPosY()-10, 20, 20);
		gc.fillOval(z1.getPosX()-10, z1.getPosY()-10, 20, 20);
		gc.fillOval(z2.getPosX()-10, z2.getPosY()-10, 20, 20);
		gc.setFill(Color.WHITE);
		for (int i = 0; i < z.getCesta().size(); i++) {
			Planeta a = z.getCesta().get(i);
			gc.fillOval(a.getPosX()-3, a.getPosY()-3, 6, 6);
		}
		for (int i = 0; i < z1.getCesta().size(); i++) {
			Planeta a = z1.getCesta().get(i);
			gc.fillOval(a.getPosX()-3, a.getPosY()-3, 6, 6);
		}
		for (int i = 0; i < z2.getCesta().size(); i++) {
			Planeta a = z2.getCesta().get(i);
			gc.fillOval(a.getPosX()-3, a.getPosY()-3, 6, 6);
		}
	}
	
	public static void nakresliCesty(){
		gc.setStroke(Color.GREEN);
		for (int i = 0; i < g.getCesty().size(); i++) {
			Planeta a = g.getCesty().get(i).getOd();
			int x1 = a.getPosX()*quality;
			int y1 = a.getPosY()*quality;
			Planeta b = g.getCesty().get(i).getKam();
			int x2 = b.getPosX()*quality;
			int y2 = b.getPosY()*quality;
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
