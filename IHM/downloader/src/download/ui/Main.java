package download.ui;

import downloader.fc.Downloader;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

	public void start(Stage stage) {
		BorderPane root = new BorderPane();
		HBox hb = new HBox(50);
		ProgressBar pb = new ProgressBar();
		pb.setProgress(0);
		Downloader downloader = new Downloader("http://iihm.imag.fr/index.html");
		new Thread(downloader).start();
		System.out.format("Downloading %s:", downloader);
		hb.getChildren().add(pb);

		downloader.progressProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				pb.setProgress((double) arg2);

			}

		});

		downloader.progressProperty().addListener((obs, o, n) -> {
			System.out.print(".");
			System.out.flush();
		});

/*		String filename;
		try {
			filename = downloader.download();
			System.out.format("into %s\n", filename);
		}

		catch (Exception e) {
			System.err.println("failed!");
		}*/
		root.setTop(hb);
		stage.setScene(new Scene(root));
		stage.show();

	}

	public static void main(String argv[]) {
		launch(argv);
	}
}
