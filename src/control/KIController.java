package control;

import gui.spielfeld.SpielfeldController;
import javafx.application.Platform;
import model.Action;
import model.DungeonRoll;
import model.KI;
import model.KI.DefaultPossibleMoveGenerator;
import model.KI.PossibleActionGenerator;
import model.Spieler;
import model.Spielertyp;
import model.Spielzustand;


public class KIController {

	private KI ki;
	private MasterController masterController;

	private PossibleActionGenerator actionGenerator;

	public KIController(MasterController masterController) {
		actionGenerator = new DefaultPossibleMoveGenerator(ki, masterController);
		this.masterController = masterController;
	}

	public final void setKi(KI ki) {
		this.ki = ki;
	}

	public Action tippHolen(Spielzustand spiel) {
		return ki.getAction(masterController, KI.DefaultValueFunction, actionGenerator, KI.DefaultRisikoFunction, spiel);
	}

	/**
	 * @author Merle
	 * @description Methode sorgt dafuer, das zwischen zwei KI zuegen pause ist,
	 *              damit der Nutzer verfolgen kann, was die KI macht
	 * @preconditions Die Funktion neues Abenteuer erstellen wurde aufgerufen,
	 *                und der dadurch an die Reihe kommende Spieler ist eine KI
	 */
	public void zugKI() {
		// Zeige ersten Spielzustand 10 Sekunden an
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("Hier lief was falsch...");
			System.err.println("KI CONTROLLER zugKI");
		}
		DungeonRoll dungeonRoll = masterController.getDungeonRoll();
		Spielzustand spielzustand = dungeonRoll.getAktuellerZustand().neuenSpielzustandErzeugen();
		Spieler aktuellerSpieler = dungeonRoll.getAktuellerZustand().aktuellerSpieler();

		KI aktuelleKI = (KI) aktuellerSpieler;
		Action naechsterKIZug = aktuelleKI.getAction(masterController, KI.DefaultValueFunction, KI.defaultPossibleMoveGenerator(masterController, aktuelleKI),
				KI.DefaultRisikoFunction, spielzustand);

		// Solange Zuege moeglich sind
		while (naechsterKIZug != null && spielzustand.aktuellerSpieler().getSpielertyp()!=Spielertyp.MENSCHLICH) {
			// Zug ausfuehren
			naechsterKIZug.apply(spielzustand);
			masterController.getDungeonRoll().setAktuellerZustand(spielzustand);
			// TODO Phase aktualisieren ?

			// Neuen Spielzustand zeigen
			//Platform.runLater(masterController.getSpielfeldController()::refresh);
			masterController.getSpielfeldController().refresh();
			System.out.println("Refresh");

			// Schlafen
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Hier lief was falsch...");
				System.err.println("KI CONTROLLER zugKI");
			}
			//
			// Naechsten Zug holen
			naechsterKIZug = aktuelleKI.getAction(masterController, KI.DefaultValueFunction, KI.defaultPossibleMoveGenerator(masterController, aktuelleKI),
					KI.DefaultRisikoFunction, spielzustand);
			spielzustand = dungeonRoll.getAktuellerZustand();
			;
		}
	}

}
