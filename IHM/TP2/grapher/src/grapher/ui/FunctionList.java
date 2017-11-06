package grapher.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class FunctionList extends ListView<String> {
	GrapherCanvas canva;
	public FunctionList(GrapherCanvas canva) {
		this.getItems().addAll(canva.getFuntionList());
		this.canva = canva;
		this.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		//Even handler
		this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    System.out.println("ListView Selection Changed (selected: " + newValue.toString() + ")");
		    if(newValue == null) {
		    	canva.setBold("");
		    }else {
		    	canva.setBold(newValue);
		    }
		    
		});

	}

	public FunctionList(ObservableList<String> items) {
		super(items);
		// TODO Auto-generated constructor stub
	}

}
