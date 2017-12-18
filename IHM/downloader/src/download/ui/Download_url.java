package download.ui;

import downloader.fc.Downloader;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Download_url extends VBox {
	Downloader downloader;
	Label ref = new Label();
	HBox hbox = new HBox();
	ProgressBar progressBar = new ProgressBar();
	Button pausebutton = new Button();
	
	public Download_url(String url) {
		downloader = new Downloader(url);
		//On met à jour la barre de progression lorsque ça dl
		downloader.progressProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Platform.runLater(new Runnable() {
					public void run() {
						progressBar.setProgress(newValue.doubleValue());
					}
				});
			}
		});
		new Thread(downloader).start();

		this.getChildren().add(ref);
		this.getChildren().add(hbox);
		ref.setText(url);
		hbox.getChildren().add(progressBar);
		hbox.getChildren().add(pausebutton);
		//maj bar / largeur
		progressBar.prefWidthProperty().bind(hbox.widthProperty());
		pausebutton.setText("X");
		//Pause
	EventHandler<ActionEvent> playPauseAction = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
			downloader.Pause();
		}
	};
	//Ajouter removebutton
	pausebutton.setOnAction(playPauseAction);
	
	}
}
