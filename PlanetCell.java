package PT;

import javafx.scene.control.TreeCell;

public class PlanetCell extends TreeCell<Planeta> {

	public void updateItem(Planeta planeta, boolean empty){
		super.updateItem(planeta, empty);
		
		if (empty || planeta == null) {
			setText(null);
		} else {
			setText(planeta.toString());
		}
	}
}
