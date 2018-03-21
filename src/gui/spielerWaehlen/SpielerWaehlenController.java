package gui.spielerWaehlen;

import java.util.ArrayList;
import java.util.Optional;

import control.MasterController;
import gui.heldenkartenWaehlen.HeldenkartenWaehlenController;
import gui.startbildschirm.StartbildschirmController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

public class SpielerWaehlenController {

	MasterController masterController;

	@FXML
	private TextField spieler1Feld;

	@FXML
	private TextField spieler2Feld;

	@FXML
	private TextField spieler3Feld;

	@FXML
	private TextField spieler4Feld;

	@FXML
	private ComboBox<String> KI1Box;

	@FXML
	private ComboBox<String> KI2Box;

	@FXML
	private ComboBox<String> KI3Box;

	@FXML
	private ComboBox<String> KI4Box;

	@FXML
	private Button abbrechenButton;

	@FXML
	private TextField rundenzahlFeld;

	@FXML
	private Button oKButton;

	@FXML
	private AnchorPane pane;

	public SpielerWaehlenController() {

	}

	public void setMasterController(MasterController masterController) {
		this.masterController = masterController;
	}

	@FXML
	void initialize() {
		KI1Box.getItems().addAll("", "VORSICHTIG", "AUSGEGLICHEN", "RISIKOFREUDIG");
		KI2Box.getItems().addAll("", "VORSICHTIG", "AUSGEGLICHEN", "RISIKOFREUDIG");
		KI3Box.getItems().addAll("", "VORSICHTIG", "AUSGEGLICHEN", "RISIKOFREUDIG");
		KI4Box.getItems().addAll("", "VORSICHTIG", "AUSGEGLICHEN", "RISIKOFREUDIG");
		KI1Box.setValue("");
		KI2Box.setValue("");
		KI3Box.setValue("");
		KI4Box.setValue("");
	}

	@FXML
	void abbrechen(ActionEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Abbrechen");
		alert.setHeaderText("Sie brechen die Eingabe der Spieler ab.");
		alert.setContentText("Möchten Sie die Eingabe der Spieler wirklich abbrechen?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			FXMLLoader loader = new FXMLLoader();
			try {
				loader.setLocation(getClass().getResource("/gui/startbildschirm/Startbildschirm.fxml"));
				Parent root = loader.load();

				StartbildschirmController controller = loader.getController();
				controller.setMasterController(masterController);

				pane.getChildren().clear();
				pane.getChildren().add(root);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void ok(ActionEvent event) {
		ArrayList<String> l = new ArrayList<String>();
		l.add("VORSICHTIG");
		l.add("AUSGEGLICHEN");
		l.add("RISIKOFREUDIG");
		ArrayList<String> spielernamenliste = spielernamenListe();
		if (nichtsAusgewaehlt()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Eingabefehler");
			alert.setHeaderText("Sie haben keine Spieler ausgewählt.");
			alert.setContentText("Bitte wählen sie mindestens einen Spieler aus!");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
		} else if (mehrAlsVier()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Eingabefehler");
			alert.setHeaderText("Es sind maximal vier Spieler möglich.");
			alert.setContentText("Bitte geben Sie maximal vier Spieler an!");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
		}

		else if (rundenzahlFeld.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Eingabefehler");
			alert.setHeaderText("Es muss eine Rundenzahl angegeben werden.");
			alert.setContentText("Bitte geben Sie eine Rundenzahl an!");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
		} else if (l.contains(spieler1Feld.getText()) || l.contains(spieler2Feld.getText())
				|| l.contains(spieler3Feld.getText()) || l.contains(spieler4Feld.getText())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Eingabefehler");
			alert.setHeaderText("Sie können keinen Spielmodus der KI als Namen für einen Spieler vergeben.");
			alert.setContentText("Bitte geben Sie als Spielernamen keinen Spielmodus der KI an!");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
		} else if(doppelteNamen(spielernamenliste)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Eingabefehler");
			alert.setHeaderText("Sie haben einen Spielernamen mindestens doppelt vergeben.");
			alert.setContentText("Bitte verwenden Sie keinen Spielernamen doppelt!");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
			
		}

		else
			try {
				Integer.parseInt(rundenzahlFeld.getText());
				if (Integer.parseInt(rundenzahlFeld.getText()) < 1) {
					throw new NumberFormatException();
				}
				heldenkartenWaehlenAnzeigen();
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Eingabefehler");
				alert.setHeaderText("Es muss als Rundenzahl eine natürliche Zahl angegeben werden.");
				alert.setContentText("Bitte geben Sie eine natürliche Zahl an.");
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.showAndWait();
			}
	}

	private void heldenkartenWaehlenAnzeigen() {
		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(getClass().getResource("/gui/heldenkartenWaehlen/HeldenkartenWaehlen.fxml"));
			Parent root = loader.load();

			HeldenkartenWaehlenController controller = loader.getController();
			controller.setMasterController(masterController);

			controller.setSpielernamen(spielernamenListe());
			controller.setRundenzahl(Integer.parseInt(rundenzahlFeld.getText()));

			pane.getChildren().clear();
			pane.getChildren().add(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void rundenzahlEingeben(ActionEvent event) {
	}

	@FXML
	void spieler1Eingeben(ActionEvent event) {

	}

	@FXML
	void spieler2Eingeben(ActionEvent event) {

	}

	@FXML
	void spieler3Eingeben(ActionEvent event) {

	}

	@FXML
	void spieler4Eingeben(ActionEvent event) {
	}

	@FXML
	void KI1Eingeben(ActionEvent event) {
	}

	@FXML
	void KI2Eingeben(ActionEvent event) {
	}

	@FXML
	void KI3Eingeben(ActionEvent event) {
	}

	@FXML
	void KI4Eingeben(ActionEvent event) {
	}

	/**
	 * Prueft, ob insgesamt mehr als vier Spieler und KI ausgewaehlt wurden.
	 */
	private boolean mehrAlsVier() {
		int count = 0;
		if (!spieler1Feld.getText().equals(""))
			count++;
		if (!spieler2Feld.getText().equals(""))
			count++;
		if (!spieler3Feld.getText().equals(""))
			count++;
		if (!spieler4Feld.getText().equals(""))
			count++;
		if (!KI1Box.getValue().equals(""))
			count++;
		if (!KI2Box.getValue().equals(""))
			count++;
		if (!KI3Box.getValue().equals(""))
			count++;
		if (!KI4Box.getValue().equals(""))
			count++;

		if (count > 4)
			return true;
		return false;
	}

	/**
	 * Prueft, ob weder Spieler noch KI augewaehlt wurden.
	 */
	private boolean nichtsAusgewaehlt() {
		if (!spieler1Feld.getText().equals(""))
			return false;
		if (!spieler2Feld.getText().equals(""))
			return false;
		if (!spieler3Feld.getText().equals(""))
			return false;
		if (!spieler4Feld.getText().equals(""))
			return false;
		if (!KI1Box.getValue().equals(""))
			return false;
		if (!KI2Box.getValue().equals(""))
			return false;
		if (!KI3Box.getValue().equals(""))
			return false;
		if (!KI4Box.getValue().equals(""))
			return false;
		return true;
	}

	/**
	 * Gibt eine Liste mit den Namen der Spieler zurück.
	 */
	private ArrayList<String> spielernamenListe() {

		ArrayList<String> liste = new ArrayList<String>();
		if (!spieler1Feld.getText().equals(""))
			liste.add(spieler1Feld.getText());
		if (!spieler2Feld.getText().equals(""))
			liste.add(spieler2Feld.getText());
		if (!spieler3Feld.getText().equals(""))
			liste.add(spieler3Feld.getText());
		if (!spieler4Feld.getText().equals(""))
			liste.add(spieler4Feld.getText());
		if (!KI1Box.getValue().equals(""))
			liste.add(KI1Box.getValue());
		if (!KI2Box.getValue().equals(""))
			liste.add(KI2Box.getValue());
		if (!KI3Box.getValue().equals(""))
			liste.add(KI3Box.getValue());
		if (!KI4Box.getValue().equals(""))
			liste.add(KI4Box.getValue());

		return liste;
	}
	
	/**
	 * Prueft, ob in der uebergebenen Liste ein Element doppelt vorkommt.
	 */
	private boolean doppelteNamen(ArrayList<String> list) {
		ArrayList<String> l = new ArrayList<String>();
		l.add("VORSICHTIG");
		l.add("AUSGEGLICHEN");
		l.add("RISIKOFREUDIG");
		for (int i = 0; i< list.size(); i++) {
			for(int j = 0; j< list.size(); j++) {
				if (i != j && list.get(i).equals(list.get(j)) && !l.contains(list.get(i))) {
					return true;
				}
			}
		}
		return false;
	}
}
