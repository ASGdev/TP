package download.ui;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import downloader.fc.Downloader;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//

public class Main extends Application {
	public void start(Stage stage) {
		BorderPane root = new BorderPane();
		ScrollPane scrollPane = new ScrollPane();
		Button addButton = new Button();
		VBox conteneur = new VBox();
		HBox hbox = new HBox();
		TextField url = new TextField();
		

		root.setCenter(scrollPane);
		root.setBottom(hbox);
		//prend toute la largeur
		scrollPane.setFitToWidth(true);
		scrollPane.setContent(conteneur);
		hbox.getChildren().add(url);
		hbox.getChildren().add(addButton);
		
		
		EventHandler<ActionEvent> addEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Download_url downloadItem = new Download_url(url.getText());
				downloadItem.prefWidthProperty().bind(conteneur.widthProperty());
				conteneur.getChildren().add(downloadItem);
				
				url.clear();
			}
		};

		url.setOnAction(addEvent);

		addButton.setText("Add");
		addButton.setOnAction(addEvent);
		stage.setTitle("TP 3 - Downloader");
		stage.setScene(new Scene(root));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
