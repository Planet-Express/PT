package pt;

import javafx.scene.control.TreeCell;

/*********************************************************************************
 * Instance třídy {@code PlanetCell} představují
 * buky pro treeView.
 * 
 * @author Michal Štrunc a Jakub Váverka
 *
 */
public class PlanetCell extends TreeCell<Planeta> {

	/****************************************************************************
	 * připravý zadanou planetu na výpis do buňky 
	 * treeView
	 */
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
