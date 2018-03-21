package gui.startbildschirm;

import control.MasterController;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		MasterController masterController = new MasterController();
		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(getClass().getResource("Startbildschirm.fxml"));
			Parent root = loader.load();
			StartbildschirmController controller = loader.getController();
			controller.setMasterController(masterController);
			HostServices hs = getHostServices();
			controller.setHostServices(hs);

			Scene scene = new Scene(root, 1280, 1020);
			scene.getStylesheets().add(getClass().getResource("/gui/spielfeld/stylesheet.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("DungeonRoll");
			primaryStage.getIcons().add(new Image("/gui/spielfeld/Bilder/Schatzmarker/Drachenkoeder.png"));
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
			primaryStage.close();
		}

		primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				if (!masterController.getSpeicherController().highscoreSpeichern()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Speicherfehler");
					alert.setHeaderText("");
					alert.setContentText("Leider konnte die Highscore-Liste nicht gespeichert werden.");
					alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
					alert.showAndWait();
				}
			}

		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
