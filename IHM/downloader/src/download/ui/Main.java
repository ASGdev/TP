package download.ui;

import downloader.fc.Downloader;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	public void start(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root,800,600);
		
		for(String url: getParameters().getRaw()) {
			Downloader downloader;
			try {
				downloader = new Downloader(url);
			}
			catch(RuntimeException e) {
				System.err.format("skipping %s %s\n", url, e);
				continue;
			}
			Thread t = new Thread(downloader);
			System.out.format("Downloading %s:", downloader);
			
			downloader.progressProperty().addListener((obs, o, n) -> {
				System.out.print(".");
				System.out.flush();
			});
			
			String filename;
			try {
				filename = downloader.download();
			}
			catch(Exception e) {
				System.err.println("failed!");
				continue;
			}
			System.out.format("into %s\n", filename);
		}
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String argv[]) {
		launch(argv);
	}
}
