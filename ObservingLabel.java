package PT;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.Label;

public class ObservingLabel extends Label implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		setText(arg.toString() + "");
		
	}

}
