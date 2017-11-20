package grapher.ui;



import java.util.Optional;

import grapher.fc.FunctionFactory;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;



public class Main extends Application {
	public void start(Stage stage) {
		//Creation des structures graphiques
		BorderPane root = new BorderPane();
		SplitPane p = new SplitPane();
		SplitPane functionControlPanel = new SplitPane();
		GrapherCanvas canva = new GrapherCanvas(getParameters());
		FunctionList functionlist = new FunctionList(canva);
		Button plus = new Button("+");
		Button minus = new Button("-");
		ToolBar functioncontrol = new ToolBar();
		functionControlPanel.setOrientation(Orientation.VERTICAL);
		MenuBar main = new MenuBar();
		Menu listInteract = new Menu("Fonctions");
		MenuItem plusMenuList = new MenuItem("Ajouter");
		MenuItem minusMenuList = new MenuItem("Supprimer");
		
		
		//Events
		plus.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent me) {
		    	TextInputDialog newfunction = new TextInputDialog();
		    	newfunction.setHeaderText("Ajouter une fonction");
		    	newfunction.setContentText("Entrer l'expression de votre fonction :");
		    	Optional<String> result = newfunction.showAndWait();
		    	if (result.isPresent()){
		    		canva.addFunction(FunctionFactory.createFunction(result.get()));
		    	}
		    }
	
		});
		minus.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent me) {
		    	int index = functionlist.getSelectionModel().getSelectedIndex();
		    	canva.removeFunction(index);
		    }
	
		});
		plusMenuList.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	TextInputDialog newfunction = new TextInputDialog();
		    	newfunction.setHeaderText("Ajouter une fonction");
		    	newfunction.setContentText("Entrer l'expression de votre fonction :");
		    	Optional<String> result = newfunction.showAndWait();
		    	if (result.isPresent()){
		    		canva.addFunction(FunctionFactory.createFunction(result.get()));
		    	}
		    }
		});
		minusMenuList.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	int index = functionlist.getSelectionModel().getSelectedIndex();
		    	canva.removeFunction(index);
		    }
		});
		plusMenuList.setAccelerator(KeyCombination.valueOf("Ctrl+n"));
		minusMenuList.setAccelerator(KeyCombination.valueOf("Ctrl+Backspace"));
		
		
		
		
		//Mise en page
		listInteract.getItems().addAll(plusMenuList,minusMenuList);
		main.getMenus().add(listInteract);
		root.setTop(main);
		functioncontrol.getItems().add(plus);
		functioncontrol.getItems().add(minus);
		functionControlPanel.getItems().add(functionlist);
		functionControlPanel.getItems().add(functioncontrol);
		p.getItems().add(functionControlPanel);
		p.getItems().add(canva);
		root.setCenter(p);		
		stage.setTitle("grapher");
		stage.setScene(new Scene(root));
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	
	//Control function
}