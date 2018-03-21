package gui.startbildschirm;

import java.io.File;

import control.MasterController;
import gui.highscore.HighscoreGUIController;
import gui.spielerWaehlen.SpielerWaehlenController;
import gui.spielfeld.SpielfeldController;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class StartbildschirmController {

	private MasterController masterController;

	@FXML
	private Button neuesSpielButton;

	@FXML
	private Button spielstandLadenButton;

	@FXML
	private Button highscoresButton;

	@FXML
	private Button anleitungButton;

	@FXML
	private Button fortsetzenButton;

	@FXML
	private Button beendenButton;

	@FXML
	private AnchorPane pane;

	private HostServices hostServices;

	@FXML
	void initialize() {
		fortsetzenButton.setDisable(true);
	}

	public void setMasterController(MasterController masterController) {
		this.masterController = masterController;
	}

	public void setFortsetzenMoeglich(boolean value) {
		fortsetzenButton.setDisable(!value);
	}

	@FXML
	void anleitungAnzeigen(ActionEvent event) {
		try {
		File file = new File("Anleitung.pdf");
		hostServices.showDocument(file.getAbsolutePath());
		}
		catch(Exception e) {
			anleitungButton.setDisable(true);
		}
	}

	@FXML
	void highscoresAnzeigen(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(getClass().getResource("/gui/highscore/Highscore.fxml"));
			Parent root = loader.load();

			HighscoreGUIController controller = loader.getController();
			controller.setMasterController(masterController);
			controller.highscoresAnzeigen();

			pane.getChildren().clear();
			pane.getChildren().add(root);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void neuesSpiel(ActionEvent event) {

		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(getClass().getResource("/gui/spielerWaehlen/SpielerWaehlen.fxml"));
			Parent root = loader.load();

			SpielerWaehlenController controller = loader.getController();
			controller.setMasterController(masterController);

			pane.getChildren().clear();
			pane.getChildren().add(root);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void spielstandLaden(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Datei zum Importieren auswählen");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("DungeonRoll-Spielstand", "*.dunro"));
		File file = fileChooser.showOpenDialog(new Stage());
		if (file == null)
			return;
		if (masterController.getSpeicherController().spielLaden(file.getAbsolutePath())) {
			FXMLLoader loader = new FXMLLoader();
			try {
				loader.setLocation(getClass().getResource("/gui/spielfeld/Spielfeld.fxml"));
				Parent root = loader.load();

				SpielfeldController controller = loader.getController();
				controller.setMasterController(masterController);

				pane.getChildren().clear();
				pane.getChildren().add(root);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setContentText("Beim Laden des Spiels ist ein Fehler aufgetreten.");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
		}
	}

	@FXML
	void fortsetzen(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(getClass().getResource("/gui/spielfeld/Spielfeld.fxml"));
			Parent root = loader.load();

			SpielfeldController controller = loader.getController();
			controller.setMasterController(masterController);

			pane.getChildren().clear();
			pane.getChildren().add(root);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void beenden(ActionEvent event) {
		masterController.programmBeenden();
	}

	public void setHostServices(HostServices hs) {
		this.hostServices = hs;
	}

}
