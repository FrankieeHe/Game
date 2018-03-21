package control;

import model.DungeonRoll;
import model.Schatz;
import model.Spieler;
import model.Spielertyp;
import model.Spielzustand;

import java.util.ArrayList;
import java.util.List;

import gui.spielfeld.SpielfeldController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;

public class MasterController {

	private DungeonRoll dungeonRoll;

	private WuerfelController wuerfelController;

	private SchatzController schatzController;

	private KIController kIController;

	private HighscoreController highscoreController;

	private RegelController regelController;

	private AbenteuerController abenteuerController;

	private AuswahlController auswahlController;

	private SpeicherController speicherController;

	private SpielfeldController spielfeldController;

	public MasterController() {
		super();
		this.dungeonRoll = new DungeonRoll();
		this.wuerfelController = new WuerfelController(this);
		this.schatzController = new SchatzController(this);
		this.kIController = new KIController(this);
		this.highscoreController = new HighscoreController(this);
		this.abenteuerController = new AbenteuerController(this);
		this.auswahlController = new AuswahlController(this);
		this.speicherController = new SpeicherController(this);
		if (!speicherController.highscoresLaden()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Ladefehler");
			alert.setHeaderText("");
			alert.setContentText("Leider konnte die Highscore-Liste nicht geladen werden.");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
		}

		/*
		 * HighscoreEintrag h1 = new HighscoreEintrag("Räuber Hotzenplotz", 5,
		 * 42); HighscoreEintrag h2 = new HighscoreEintrag("Rumpelstielzchen",
		 * 3, 50); HighscoreEintrag h3 = new HighscoreEintrag("Der Goldesel", 7,
		 * 32); HighscoreEintrag h4 = new HighscoreEintrag("Der böse Wolf", 8,
		 * 45); ArrayList<HighscoreEintrag> l= new ArrayList<>(); l.add(h1);
		 * l.add(h2); l.add(h3); l.add(h4); dungeonRoll.setHighscore(l);
		 * speicherController.highscoreSpeichern();
		 */
	}

	/**
	 * Macht die letzten von einer KI durchgefuehrten Zuege rueckgaengig. Das
	 * Flag {@code highscoreRelevant} wird auf <b>false</b> gesetzt, wodurch das
	 * aktuelle Spiel nicht mehr in die Highscore-Liste aufgenommen werden kann.
	 */
	public void undo() {
		System.out.println("Undo");
		Spielzustand spielZustand = dungeonRoll.getAktuellerZustand();
		Spielertyp spielerTyp = spielZustand.aktuellerSpieler().getSpielertyp();
		if (spielerTyp != Spielertyp.MENSCHLICH) {
			while (spielZustand.getVorherigerSpielzustand() != null && spielerTyp != Spielertyp.MENSCHLICH) {
				spielZustand = spielZustand.getVorherigerSpielzustand();
				spielerTyp = spielZustand.aktuellerSpieler().getSpielertyp();
			}
		} else if (spielZustand.getVorherigerSpielzustand() != null) {
			spielZustand = spielZustand.getVorherigerSpielzustand();
		}
		spielZustand.setHighscoreRelevant(false);
		spielzustandSetzen(spielZustand);
		getSpielfeldController().refresh();
	}

	/**
	 * Stellt die zuletzt rueckgaengig gemachten Spielzuege wieder her.
	 */
	public void redo() {
		Spielzustand spielZustand = dungeonRoll.getAktuellerZustand();
		Spielertyp spielerTyp = spielZustand.aktuellerSpieler().getSpielertyp();
		if (spielerTyp != Spielertyp.MENSCHLICH) {
			while (spielZustand.getNachfolgenderSpielzustand() != null && spielerTyp != Spielertyp.MENSCHLICH) {
				spielZustand = spielZustand.getNachfolgenderSpielzustand();
				spielerTyp = spielZustand.aktuellerSpieler().getSpielertyp();
			}
		} else if (spielZustand.getNachfolgenderSpielzustand() != null) {
			spielZustand = spielZustand.getNachfolgenderSpielzustand();
		}
		spielZustand.setHighscoreRelevant(false);
		spielzustandSetzen(spielZustand);
		getSpielfeldController().refresh();
	}

	/**
	 * Setzt den uebergebenen Spielzustand als den aktuellen.
	 * 
	 * @param zustand
	 *            zu setzender Spielzustand
	 * 
	 */
	public void spielzustandSetzen(Spielzustand zustand) {
		dungeonRoll.setAktuellerZustand(zustand);
	}

	/**
	 * Beendet das aktuelle Spiel und trägt den Highscore ein, sofern die letzte
	 * Runde abgeschlossen wurde
	 */
	public void spielBeenden() {
		if (dungeonRoll.getAktuellerZustand().getAbenteuer() == null) {
			return;
		}
		
		//Muss das hinter der Validierung passieren?
		if(dungeonRoll.getAktuellerZustand().getMaxRunde() == dungeonRoll.getAktuellerZustand().getRunde()){
			//EP für Schätze verteilen
			
			List<Spieler> spieler = dungeonRoll.getAktuellerZustand().getSpieler();
			for(Spieler einSpieler : spieler){
				List<Schatz> schaetze = einSpieler.getSpielerWerte().getSchaetze();
				int schatzEP = schaetze.size();
				for (Schatz s : schaetze) {
					if (Schatz.STADTPORTAL.equals(s)) {
						schatzEP++;
					}
				}
				int erfahrungspunkte = einSpieler.getSpielerWerte().getErfahrungspunkte();
				einSpieler.getSpielerWerte().setErfahrungspunkte(erfahrungspunkte + schatzEP);
				
			}
			
		}
		
		
		if (dungeonRoll.getAktuellerZustand().isHighscoreRelevant()
				&& dungeonRoll.getAktuellerZustand().getMaxRunde() == dungeonRoll.getAktuellerZustand().getRunde()) {
			highscoreController.highscoreEintragen(dungeonRoll.getAktuellerZustand());
		}
	}

	/**
	 * Beendet das komplette Programm
	 */
	public void programmBeenden() {
		speicherController.highscoreSpeichern();
		System.exit(0);
	}

	/**
	 * Startet ein neues Spiel
	 * 
	 * @param spieler
	 *            Liste mit Spielern, die an neuem Spiel teilnehmen
	 * @param rundenanzahl
	 *            Rundenzahl des neuen Spiels
	 */
	public void spielStarten(List<Spieler> spieler, int rundenanzahl) {
		Spielzustand spielzustand = new Spielzustand();
		spielzustand.setSpieler(spieler);
		spielzustand.setMaxRunde(rundenanzahl);
		spielzustand.setHighscoreRelevant(true);
		spielzustand.setRunde(-1);
		System.out.println("Spiel wird angelegt -  Runde ist " + spielzustand.getRunde());
		System.out.println("Spiel wird angelegt - MaxRunde ist " + spielzustand.getMaxRunde());
		ArrayList<Schatz> schaetze = new ArrayList<Schatz>();
		for (int i = 0; i < 3; i++) {
			schaetze.add(Schatz.KOEPFERKLINGE);
			schaetze.add(Schatz.TALISMAN);
			schaetze.add(Schatz.ZEPTERDERMACHT);
			schaetze.add(Schatz.DIEBESWERKZEUG);
			schaetze.add(Schatz.SPRUCHROLLE);
			schaetze.add(Schatz.TRANK);
		}
		for (int i = 0; i < 4; i++) {
			schaetze.add(Schatz.UNSICHTBARKEITSRING);
			schaetze.add(Schatz.DRACHENKOEDER);
			schaetze.add(Schatz.STADTPORTAL);
		}
		for (int i = 0; i < 6; i++) {
			schaetze.add(Schatz.DRACHENSCHUPPEN);
		}
		spielzustand.setSchaetze(schaetze);
		spielzustandSetzen(spielzustand);
		abenteuerController.neuesAbenteuerErstellen();
	}

	public DungeonRoll getDungeonRoll() {
		return dungeonRoll;
	}

	public WuerfelController getWuerfelController() {
		return wuerfelController;
	}

	public SchatzController getSchatzController() {
		return schatzController;
	}

	public KIController getkIController() {
		return kIController;
	}

	public HighscoreController getHighscoreController() {
		return highscoreController;
	}

	public RegelController getRegelController() {
		return regelController;
	}

	public AbenteuerController getAbenteuerController() {
		return abenteuerController;
	}

	public AuswahlController getAuswahlController() {
		return auswahlController;
	}

	public SpeicherController getSpeicherController() {
		return speicherController;
	}

	public SpielfeldController getSpielfeldController() {
		return spielfeldController;
	}

	public void setSpielfeldController(SpielfeldController spielfeldController) {
		this.spielfeldController = spielfeldController;
	}
}
