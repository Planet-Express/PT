package pt;

import javafx.scene.control.TreeCell;

public class PlanetCell extends TreeCell<Planeta> {

	public void updateItem(Planeta planeta, boolean empty){
		super.updateItem(planeta, empty);
		
		if (empty || planeta == null) {
			setText(null);
		} else {
			if(planeta.getId() > 5000){
				setText("Stanice " + Integer.toString((planeta.getId()-5000)));
			}
			else{
				setText(planeta.toString());
			}
		}
	}
}
