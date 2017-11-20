package grapher.ui;

import grapher.fc.Function;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class FunctionList extends ListView<Function> {
	GrapherCanvas canva;
	public FunctionList(GrapherCanvas canva) {
		this.setItems(canva.getFunctionList());
		this.canva = canva;
		this.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		//Even handler
		this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    System.out.println("ListView Selection Changed (selected: " + newValue.toString() + ")");
		    canva.setBold(newValue);
	
		   
		});

	}

	public FunctionList(ObservableList<Function> items) {
		super(items);
		
		// TODO Auto-generated constructor stub
	}

}
