package grapher.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;



public class Main extends Application {
	public void start(Stage stage) {
		//Creation des structures graphiques
		BorderPane root = new BorderPane();
		SplitPane p = new SplitPane();
		GrapherCanvas canva = new GrapherCanvas(getParameters());
		FunctionList functionlist = new FunctionList(canva);
		
		
		
		
		p.getItems().add(functionlist);
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