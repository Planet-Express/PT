package pt;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class GUI{

	Counter cas = new Counter();
	static Affine af;
	final static int skok = 6;
	static Galaxie g;
	static int zoom = 0;
	static int quality = 1;
	static Canvas canvas = new Canvas(800*quality,800*quality);
	static GraphicsContext gc = canvas.getGraphicsContext2D();
	
	private static ObservablePlanet oPlaneta = new ObservablePlanet(new Planeta(0, 0, 0, 0));


	private TableView<Lod> tv;

	public Parent createScene(Galaxie galaxy) {
		g = galaxy;
		BorderPane bp = new BorderPane();
		canvas.setScaleX(1.0/quality*0.9);
		canvas.setScaleY(1.0/quality*0.9);
		bp.setPrefSize(800, 600);
		bp.setLeft(getControlBar());
		bp.setCenter(getCenter());
		bp.setRight(getInfoPane());
		return bp;
	}

	
	private Node getInfoPane() {
		VBox vb = new VBox();
		ObservingLabel oLabel = new ObservingLabel();
		oPlaneta.addObserver(oLabel);
		TextField tf = new TextField("Zadej objednávku");
		Button objednat = new Button("Objednat");
		objednat.setOnAction(event ->{
		System.out.println("objednáno: " + tf.getText());
		oPlaneta.getPlaneta().getObjednavka().setKolik(Integer.parseInt(tf.getText()));});
		vb.setPrefWidth(300);
		tv = getTableView();
		vb.getChildren().addAll(getTree(), oLabel, tf, objednat, tv);
		return vb;
	}

	private TableView<Lod> getTableView() {
		TableView<Lod> tableView = new TableView<Lod>();
		
		tableView.getColumns().addAll(getColumnId(), getColumnNaklad(), getColumnStav(), getColumnStanice());
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setItems(getLode());
		return tableView;
	}


	private TableColumn<Lod, Stanice> getColumnStanice() {

		TableColumn<Lod, Stanice> stanice = new TableColumn<Lod, Stanice>("Stanice");
		stanice.setCellValueFactory(new PropertyValueFactory<Lod, Stanice>("start"));
		stanice.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Stanice>() {
			public String toString(Stanice arg0) {
				return "Stanice " + (arg0.getId()-5000);
			}
			@Override
			public Stanice fromString(String arg0) {
				return null;
			}
		}));
		return stanice;
	}


	private TableColumn<Lod, Integer> getColumnStav() {
		TableColumn<Lod, Integer> stav = new TableColumn<Lod, Integer>("Stav");
		stav.setCellValueFactory(new PropertyValueFactory<Lod, Integer>("stav"));
		stav.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
			public String toString(Integer arg0) {
				return arg0 + "";
			}
			public Integer fromString(String arg0){ 
				try{
					return Integer.parseInt((arg0));
				}
				catch(NumberFormatException nfe){
					return 0;
				}
			}
		}));
		
		return stav;
	}


	private TableColumn<Lod, Integer> getColumnNaklad() {
		TableColumn<Lod, Integer> naklad = new TableColumn<Lod, Integer>("Naklad");
		naklad.setCellValueFactory(new PropertyValueFactory<Lod, Integer>("naklad"));
		naklad.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
			public String toString(Integer arg0) {
				return arg0 + "";
			}
			public Integer fromString(String arg0){ 
				try{
					return Integer.parseInt((arg0));
				}
				catch(NumberFormatException nfe){
					return 0;
				}
			}
		}));
		return naklad;
	}


	private TableColumn<Lod, Integer> getColumnId() {
		TableColumn<Lod, Integer> id = new TableColumn<Lod, Integer>("Id");
		id.setCellValueFactory(new PropertyValueFactory<Lod, Integer>("id"));
		id.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
			public String toString(Integer arg0) {
				return arg0 + "";
			}
			public Integer fromString(String arg0){ 
				try{
					return Integer.parseInt((arg0));
				}
				catch(NumberFormatException nfe){
					return 0;
				}
			}
		}));
		return id;
	}


	private ObservableList<Lod> getLode() {
		ObservableList<Lod> ol = FXCollections.observableArrayList();
		for (int i = 0; i < cas.lode.size(); i++) {
			ol.add(cas.lode.get(i));
		}
		return ol;
	}
	
	private Node getTree() {
		TreeView<Planeta>treeView = new TreeView<Planeta>();
		treeView.setRoot(createDefaultChildren());
		treeView.setShowRoot(false);
		treeView.setEditable(false);
		treeView.setCellFactory(new Callback<TreeView<Planeta>, TreeCell<Planeta>>() {
			
			@Override
			public TreeCell<Planeta> call(TreeView<Planeta> arg0) {
				return new PlanetCell();
			}
		});	
		treeView.setOnMouseClicked(event -> {
			if(!treeView.getSelectionModel().isEmpty()){
				oPlaneta.setPlaneta(treeView.getSelectionModel().getSelectedItem().getValue());
				prekresliPlatno();
			}
		});
		return treeView;
	}

	
	private TreeItem<Planeta> createDefaultChildren() {
		TreeItem<Planeta> root = new TreeItem<Planeta>();
		TreeItem<Planeta> s1 = new TreeItem<Planeta>(g.getPlanety().get(5000));
		TreeItem<Planeta> s2 = new TreeItem<Planeta>(g.getPlanety().get(5001));
		TreeItem<Planeta> s3 = new TreeItem<Planeta>(g.getPlanety().get(5002));
		TreeItem<Planeta> s4 = new TreeItem<Planeta>(g.getPlanety().get(5003));
		TreeItem<Planeta> s5 = new TreeItem<Planeta>(g.getPlanety().get(5004));
		for(int i = 0; i < 5000; i++){
			Planeta tmp = g.getPlanety().get(i);
			Planeta stanice = tmp.getCesta().get(tmp.getCesta().size()-1);
			switch (stanice.getId()-5000) {
			case 1:
				s1.getChildren().add(new TreeItem<Planeta>(tmp));
				break;
			case 2:
				s2.getChildren().add(new TreeItem<Planeta>(tmp));
				break;
			case 3:
				s3.getChildren().add(new TreeItem<Planeta>(tmp));
				break;
			case 4:
				s4.getChildren().add(new TreeItem<Planeta>(tmp));
				break;
			case 5:
				s5.getChildren().add(new TreeItem<Planeta>(tmp));
				break;
			default:
				System.out.println("chyba prirazeni stanice");
				break;
			}
		}
		root.getChildren().addAll(s1, s2, s3, s4, s5);
		return root;
		
	}

	private Node getControlBar() {
		VBox vb = new VBox();
		vb.setPadding(new Insets(10));
		vb.setSpacing(10);
		vb.setAlignment(Pos.CENTER);
		Button generuj = new Button("Generuj");
		generuj.setMinWidth(100);
		Button start = new Button("Start");
		start.setMinWidth(100);
		Button stats = new Button("Vytvo� statistiku");
		start.setMinWidth(100);
		generuj.setOnAction(event -> {
			if(!cas.getState().toString().equals("WAITING")||!cas.isAlive()){
				g = new Galaxie(800, 5000);
				g.generujVesmir();
				Soubor.uloz("Soubor", g);
				prekresliPlatno();
			}
		});
		start.setOnAction(event -> {
			if(!cas.isAlive()){
			cas.start(g,this);
			}else if(cas.getState().toString().equals("WAITING")){synchronized (cas) {
				cas.notify();
			}}
			prekresliPlatno();			
		});
		
		stats.setOnAction(event -> {
				Soubor.vytvorStatistiku("Statistika", cas,g.getPlanety());
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
		vb.getChildren().addAll(start,generuj,stats,slid);
		return vb;
	}

	
	public void prekresliPlatno() {
		canvas.setScaleX(1.0/quality*0.9  * 100.0/(101-zoom));
		canvas.setScaleY(1.0/quality*0.9  * 100.0/(101-zoom));
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, 800*quality, 800*quality);
		nakresliCesty();
		nakresliPlanety();
		nakresliStanice();
		testCesty(oPlaneta.getPlaneta());
		//tv.getItems().clear();
		tv.setItems(getLode());
		tv.getColumns().get(0).setVisible(false);
		tv.getColumns().get(0).setVisible(true);
		for (int i = 0; i < tv.getSelectionModel().getSelectedItems().size(); i++) {			
			nakresliLod(tv.getSelectionModel().getSelectedItems().get(i));			
		}
	}

	
	
	private static void nakresliLod(Lod l) {
		if(l != null){
			gc.setFill(Color.CRIMSON);
			if(l.getChciNa()!=null){
				gc.fillRect((l.getChciNa().getPosX()-3)*quality, (l.getChciNa().getPosY()-3)*quality, 6*quality, 6*quality);
			}
			if(l.getLokace() instanceof Cesta){
				gc.setStroke(Color.DARKSLATEBLUE);
				Cesta c = (Cesta)l.getLokace();
				gc.strokeLine(c.getOd().getPosX(), c.getOd().getPosY(), c.getKam().getPosX(), c.getKam().getPosY());
			}else{
				Planeta a = (Planeta)l.getLokace();
				gc.setFill(Color.DARKSLATEBLUE);
				gc.fillOval((a.getPosX()-5)*quality, (a.getPosY()-5)*quality, 10*quality, 10*quality);
			}
		}
	}

	private static Node getCenter() {
		ScrollPane sc = new ScrollPane();
		sc.setMinWidth(730);
		af = gc.getTransform();
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
			if(g.getPlanety().get(i).isMrtva()){
				gc.setFill(Color.DODGERBLUE);
			}else{
				gc.setFill(getColor(pop));
			}
			gc.fillOval(x-2*quality, y-2*quality, 4*quality, 4*quality);
		}		
	}
	
	
	public static void testCesty(Planeta z){
		gc.setFill(Color.WHITE);
		gc.fillOval((z.getPosX()-3)*quality, (z.getPosY()-3)*quality, 6*quality, 6*quality);
		gc.setFill(Color.GRAY);
		for (int i = 0; i < z.getCesta().size(); i++) {
			Planeta a = z.getCesta().get(i);
			gc.fillOval((a.getPosX()-3)*quality, (a.getPosY()-3)*quality, 6*quality, 6*quality);
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
