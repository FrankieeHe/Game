package control;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Abenteuer;
import model.Heldtyp;
import model.Phase;
import model.SchwarzeWuerfelseite;
import model.SpielerWerte;
import model.Spielertyp;
import model.Spielzustand;
import model.WeisseWuerfelseite;
import model.Wuerfel;

/**
 * Der Controller fuer das Abenteuer
 * 
 * @author Dong
 *
 */
public class AbenteuerController {
	/**
	 * MasterController des Projektes. Mit Hilfe von MasterController bekommt
	 * AbenteuerController Zugriff zu DungeonRoll und anderen Controllers.
	 */
	private MasterController masterController;

	/**
	 * Konstruktor, produziert eine AbenteuerController.
	 * 
	 * @param masterController
	 *            MasterController des Projektes.
	 */
	public AbenteuerController(MasterController masterController) {
		this.masterController = masterController;
	}

	/**
	 * Ein neues Abenteuer zu erstellen und anfang.
	 */
	public void neuesAbenteuerErstellen() {
		Abenteuer abenteuer = this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer();
		if (abenteuer == null) {
			abenteuer = new Abenteuer();
			this.masterController.getDungeonRoll().getAktuellerZustand().setAbenteuer(abenteuer);
			abenteuer.setPhase(Phase.WUERFELPHASE);
			abenteuer.setGefaehrten(new ArrayList<Wuerfel>());
			abenteuer.setDrachen(new ArrayList<Wuerfel>());
			abenteuer.setFriedhof(new ArrayList<Wuerfel>());
			abenteuer.setDungeon(new ArrayList<Wuerfel>());
			abenteuer.setLevel(1);
			abenteuer.setSpielerWerte(new SpielerWerte());
			abenteuer.getSpielerWerte().setErfahrungspunkte(0);
			// this.masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getSpielerWerte().setSchaetze(new
			// ArrayList<Schatz>());
			this.masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld()
					.setUltimativeEingesetzt(false);
		}
		// New abenteuer fangt in WUERFELPHASE an
		if (abenteuer.getPhase() != Phase.WUERFELPHASE) {
			// System.err.println("nicht richtig beenden");
			abenteuer.setPhase(Phase.WUERFELPHASE);
		}
		if (abenteuer.getDrachen().size() > 0 || abenteuer.getDungeon().size() > 0 || abenteuer.getFriedhof().size() > 0
				|| abenteuer.getGefaehrten().size() > 0) {
			abenteuer.getDrachen().clear();
			abenteuer.getDungeon().clear();
			abenteuer.getFriedhof().clear();
			abenteuer.getGefaehrten().clear();

		}
		// sichern die akteuller spielende Spieler
		int spielerIndex = this.masterController.getDungeonRoll().getAktuellerZustand().getSpielerIndex();
		int alterRundenIndex = this.masterController.getDungeonRoll().getAktuellerZustand().getRunde();
		if (spielerIndex == 0) {
			masterController.getDungeonRoll().getAktuellerZustand().setRunde(alterRundenIndex + 1);
		}
		int rundeIndex = this.masterController.getDungeonRoll().getAktuellerZustand().getRunde();
		int maxRund = this.masterController.getDungeonRoll().getAktuellerZustand().getMaxRunde();
		int spielerAnzahl = this.masterController.getDungeonRoll().getAktuellerZustand().getSpieler().size();
		System.out.println("Runde = " + rundeIndex + "Berechnung " + (rundeIndex) + " Ende bei "
				+ ((maxRund * spielerAnzahl) + 1));
		if (rundeIndex == maxRund) {
			this.masterController.spielBeenden();
		}

		// neu Abenteuer muss erst die List vom Gruppewuerfel erstellen
		// init fuer die Gefarhtegruppe
		int anzahlWeisseW = 7;

		List<Wuerfel> weissWurfel = new ArrayList<Wuerfel>();
		for (int i = 0; i < anzahlWeisseW; i++) {
			Wuerfel w = new Wuerfel(true);
			weissWurfel.add(w);
		}
		this.masterController.getWuerfelController().wuerfeln(weissWurfel);

		// falls RITTERDRACHENTOETER in Spielen, wird alles Spruchrolle sofort
		// als
		// Champions werden
		if (this.masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld().getHeldtyp()
				.equals(Heldtyp.RITTERDRACHENTOETER)) {
			for (Wuerfel w : weissWurfel) {
				if (w.getWeisseWuerfelseite().equals(WeisseWuerfelseite.SPRUCHROLLE)) {
					w.setWeisseWuerfelseite(WeisseWuerfelseite.CHAMPION);
				}
			}
		}
		// setze das Spielfeld an
		abenteuer.setGefaehrten(weissWurfel);
		abenteuer.setDrachen(new ArrayList<Wuerfel>());
		abenteuer.setFriedhof(new ArrayList<Wuerfel>());
		abenteuer.setDungeon(new ArrayList<Wuerfel>());

		this.masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld()
				.setUltimativeEingesetzt(false);

		// automatisch in KampfenPhase geleitet
		// falls keine Monster gewürfelt wurden wird hier dann in die
		// Plünderphase
		// gewechselt
		this.phaseWechseln();

		if (this.masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler()
				.getSpielertyp() != Spielertyp.MENSCHLICH) {
			masterController.getkIController().zugKI();

			System.out.println("STOP!!!!!!!!!!");

		}
	}

	/**
	 * Akteueres Abenteuer zu beenden. Der bisher geschafft Erfahrungpunkt wird
	 * berechnet und die Ultimative Faehigkeit des Helds rueckgesetzt. Der Spieler
	 * sitzt in Ruhe. Der Spielzustand wird am Ende gespeichert.
	 */
	public void abenteuerBeenden() {
		if (this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer() == null) {
			return;
		}
		
		Spielzustand neuerSpielzustand = masterController.getDungeonRoll().getAktuellerZustand()
				.neuenSpielzustandErzeugen();
		masterController.getDungeonRoll().setAktuellerZustand(neuerSpielzustand);

		if (this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getPhase()
				.equals(Phase.UMGRUPPIERUNGSPHASE)) {
			// this.masterController.getSpeicherController().spielSpeichern();

			// hat alle Monster am Leben besiegt
			int level = this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getLevel();
			int abenteuerErfahrungspunkte = this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer()
					.getSpielerWerte().getErfahrungspunkte();
			int spielerErfahrungspunkte = this.masterController.getDungeonRoll().getAktuellerZustand()
					.aktuellerSpieler().getSpielerWerte().getErfahrungspunkte();

			// fuer Ende einer Abenteuer der alle bekommten Erfahrungspunkte
			// Level zu zeigen
			spielerErfahrungspunkte += abenteuerErfahrungspunkte + level;

			// opt
			// this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getSpielerWerte()
			// .setErfahrungspunkte(0);

			this.masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getSpielerWerte()
					.setErfahrungspunkte(spielerErfahrungspunkte);
			if(masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getSpielerWerte().getErfahrungspunkte() >= 5){
				masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld().setLevelUp(true);
			}
		}
		//Abenteuer gescheitert, ggf Held-Level zurücksetzen
		else if(masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getSpielerWerte().getErfahrungspunkte() < 5){
			masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld().setLevelUp(false);
		}

		int spielerIndex = this.masterController.getDungeonRoll().getAktuellerZustand().getSpielerIndex();
		if (spielerIndex >= this.masterController.getDungeonRoll().getAktuellerZustand().getSpieler().size() - 1) {
			this.masterController.getDungeonRoll().getAktuellerZustand().setSpielerIndex(0);
		} else {
			this.masterController.getDungeonRoll().getAktuellerZustand().setSpielerIndex(spielerIndex + 1);
		}
		if (this.masterController.getDungeonRoll().getAktuellerZustand().getSpielerIndex() == this.masterController
				.getDungeonRoll().getAktuellerZustand().getSpieler().size()) {
			int rund = this.masterController.getDungeonRoll().getAktuellerZustand().getRunde();
			if (rund == this.masterController.getDungeonRoll().getAktuellerZustand().getMaxRunde()) {
				this.masterController.spielBeenden();
				return;
			}
			this.masterController.getDungeonRoll().getAktuellerZustand().setRunde(rund + 1);
		}

		this.masterController.getDungeonRoll().getAktuellerZustand().setAbenteuer(null);
		neuesAbenteuerErstellen();

		// this.masterController.getSpeicherController().spielSpeichern();
	}

	/**
	 * welchseln der Spiel in naechsten Level. Nur Dungeon Wuerfel wird in Anzahl
	 * von neuem Level gewuerfelt. aktuellePhase wird auf WUERFELPHASE gesetzt
	 */
	public void naechstesLevel() {
		if (this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer()
				.getPhase() != Phase.UMGRUPPIERUNGSPHASE) {
			System.err.println("Nur in UMGRUPPIERUNGSPHASE the next level anfangen kann");
			return;
		}
		Spielzustand neuerSpielzustand = masterController.getDungeonRoll().getAktuellerZustand()
				.neuenSpielzustandErzeugen();
		masterController.getDungeonRoll().setAktuellerZustand(neuerSpielzustand);
		int aktueLevel = this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getLevel() + 1;
		this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().setLevel(aktueLevel);
		
		// zu next Phase wechseln, sgn Wurfelpahse
		// wie ist hier zu wuerfeln
		// falls keine Monster gewürfelt wurden wird hier dann in die
		// Plünderphase
		// gewechselt
		this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().setPhase(Phase.WUERFELPHASE);
		this.phaseWechseln();

		// fuer Redo und Undo muss hier geklont werden
		Spielzustand spielzustand = masterController.getDungeonRoll().getAktuellerZustand().neuenSpielzustandErzeugen();
		masterController.getDungeonRoll().setAktuellerZustand(spielzustand);

	}

	public void plunderPhase_verzichten() {
		int drachenkopf = this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDrachen()
				.size();
		int d_dungeon = drachenkopf / 3;
		boolean drag = true;

		for (int i = 0; i < d_dungeon; i++) {
			int l = this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDrachen().size();

			Wuerfel drachen_leben = this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer()
					.getDrachen().get(l - 1);
			// wenn es mehr als 3 Drachenkopf in Drachhoehle muss in
			// Drachenphase in
			// Spielfield bringen
			// dazu sie muss erst aus der Drachhoehle entfehrnen.
			for (int j = 0; j < 3; j++) {
				this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDrachen()
						.remove(l - 1 - j);
			}
			// 3 Drachenkopf in Drachkohle ist gleich Drachenkopf in Dungeon
			if (drag) {
				this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDungeon()
						.add(drachen_leben);
				drag = false;
			}
		}
		this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().setPhase(Phase.DRACHENPHASE);
		phaseWechseln();

	}

	/**
	 * welchseln die aktuellePhase des Spiels in naechste Phase. die Reinfolge von
	 * Phase ist allgemein im Zyklus als WUEFELPHASE,KAMPFPHASE, PLUENDERPHASE,
	 * DRACHENPHASE, UMGRUPPIERUNGSPHASE
	 */
	public void phaseWechseln() {

		switch (this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getPhase()) {
		case WUERFELPHASE:
			// die Dungeonwurfel werden ernuert gewuerfelt
			this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDungeon().clear();

			// init the neu wurfel List mit den vorhandenen Wuerfel
			List<Wuerfel> dungeonWuerfel = new ArrayList<Wuerfel>();
			int anzahlDW = 7
					- this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDrachen().size();
			if (anzahlDW > this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getLevel()) {
				anzahlDW = this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getLevel();
			}

			for (int i = 0; i < anzahlDW; i++) {
				Wuerfel w = new Wuerfel(false);
				dungeonWuerfel.add(w);
			}
			this.masterController.getWuerfelController().wuerfeln(dungeonWuerfel);

			List<Wuerfel> gewuerfelteDrachen = dungeonWuerfel.stream()
					.filter(dra -> dra.getSchwarzeWuerfelseite().equals(SchwarzeWuerfelseite.DRACHENKOPF))
					.collect(Collectors.toList());
			dungeonWuerfel.removeAll(gewuerfelteDrachen);
			this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDrachen()
					.addAll(gewuerfelteDrachen);
			this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().setDungeon(dungeonWuerfel);
			this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().setPhase(Phase.KAMPFPHASE);

			if (this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDungeon().stream()
					.noneMatch(wuerfel -> wuerfel.isMonster())) {
				phaseWechseln();
			}

			break;

		case KAMPFPHASE:
			// kein Monster hier am Leben
			for (Wuerfel sw : this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer()
					.getDungeon()) {
				if (sw.getSchwarzeWuerfelseite().equals(SchwarzeWuerfelseite.TRANK)) {
					continue;
				} else if (sw.getSchwarzeWuerfelseite().equals(SchwarzeWuerfelseite.TRUHE)) {
					continue;
				} else {
					System.out.println("Darf kein Monster von KAMPFPHASE to PLUENDERPHASE ");
					return;
				}
			}
			this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().setPhase(Phase.PLUENDERPHASE);
			if (this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDungeon().isEmpty()) {
				phaseWechseln();
			}
			break;

		case PLUENDERPHASE:
			if (this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDungeon().stream()
					.anyMatch(wuerfel -> wuerfel.getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.TRANK
							|| wuerfel.getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.TRUHE)) {
				return;
			}
			// Zu Drachenphase soll die Drachen in Dungeon bringen
			plunderPhase_verzichten();

			if (this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDrachen().size() < 3) {
				phaseWechseln();
			}

			break;

		case DRACHENPHASE:
			for (Wuerfel sw : this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer()
					.getDungeon()) {
				if (sw.getSchwarzeWuerfelseite().equals(SchwarzeWuerfelseite.TRANK)
						|| sw.getSchwarzeWuerfelseite().equals(SchwarzeWuerfelseite.TRUHE)) {
					continue;
				} else {
					System.out.println("Darf kein Monster von DRACHENPHASE to UMGRUPPIERUNGSPHASE ");
					return;
				}
			}
			this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer()
					.setPhase(Phase.UMGRUPPIERUNGSPHASE);
			phaseWechseln();

			break;

		case UMGRUPPIERUNGSPHASE:
			// Von UMGRUPPIERUNGSPHASE zu WUERFELPHASE setzt so vor, dass kein
			// Monster noch
			// erleben ist.
			// Falls noch Monster am Leben ist, kann Spieler nicht weiter
			// spielen, sgn in
			// Ruhe.

			// 10 level muss sich beenden.
			if (this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getLevel() == 10) {
				this.abenteuerBeenden();
				return;
			}
			// bei UMGRUPPIERUNGSPHASE muss man mit fortsetzen weiter gehen.
			// this.masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().setPhase(Phase.WUERFELPHASE);

			// nicht automatisch mehr machen kann
			// this.naechstesLevel();
			break;

		default:
			break;

		}

	}

}