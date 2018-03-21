package gui.spielerWaehlen;

import control.MasterController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		MasterController masterController = new MasterController();
		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(getClass().getResource("SpielerWaehlen.fxml"));
			Parent root = loader.load();
			SpielerWaehlenController controller = loader.getController();
			controller.setMasterController(masterController);

			Scene scene = new Scene(root, 600, 399);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
			primaryStage.close();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}