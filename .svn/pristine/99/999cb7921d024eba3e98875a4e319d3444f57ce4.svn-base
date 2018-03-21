package control.regeln;

import java.util.List;

import control.MasterController;
import control.RegelController;
import model.Held;
import model.Heldtyp;
import model.Quelle;
import model.Schatz;
import model.SchwarzeWuerfelseite;
import model.Spielzustand;
import model.Wuerfel;
import model.Ziel;

public class PaladininRegel extends RegelController {

	@Override
	public void setMasterController(MasterController masterController) {
		super.setMasterController(masterController);
	}

	@Override
	public boolean pruefeAuswahlErlaubt(Quelle quelle, List<Ziel> ziel) {
		if(quelle.alsHeld() == null || ziel == null || ziel.isEmpty() || ziel.size() != 1) {
			return false;
		}
		
		Held held = quelle.alsHeld();
		
		if(held.getHeldtyp() != Heldtyp.KREUZRITTERINPALADININ) {
			return false;
		}
		
		if (!quelle.alsHeld().isLevelUp()) {
			return false;
		}
		
		if(ziel.get(0).alsSchatz() == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public void regelAusfuehren(Quelle quelle, List<Ziel> ziel) {
		
		// Spielzustand kopieren und umsetzen
		Spielzustand geklonterZustand = masterController.getDungeonRoll().getAktuellerZustand()
				.neuenSpielzustandErzeugen();
		masterController.getDungeonRoll().setAktuellerZustand(geklonterZustand);

		// UF genutzt setzen
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld().setUltimativeEingesetzt(true);
		
		// Schatz zuruecklegen
		masterController.getSchatzController().schatzZuruecklegen(ziel.get(0).alsSchatz());
		
		// Traenke trinken Truhen oeffnen
		for(Wuerfel aktuellerWuerfel : geklonterZustand.getAbenteuer().getDungeon()) {
			if(aktuellerWuerfel.getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.TRANK) {
				// Gefaehrten wiederbeleben
				geklonterZustand.getAbenteuer().getFriedhof().remove(0);
			}else if(aktuellerWuerfel.getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.TRUHE) {
				Schatz schatz = masterController.getSchatzController().schatzZiehen();
				if(schatz != null) {
					List<Schatz> gesammelteSchaetze = geklonterZustand.aktuellerSpieler().getSpielerWerte().getSchaetze();
					gesammelteSchaetze.add(schatz);
					geklonterZustand.aktuellerSpieler().getSpielerWerte().setSchaetze(gesammelteSchaetze);
				}else {
					int erfahrungspunkte = geklonterZustand.aktuellerSpieler().getSpielerWerte().getErfahrungspunkte();
					geklonterZustand.aktuellerSpieler().getSpielerWerte().setErfahrungspunkte(erfahrungspunkte + 1);
				}
			}
		}
		
		// Alle Monster besiegen
		geklonterZustand.getAbenteuer().getDungeon().clear();
		// Drachenhoehle leeren
		geklonterZustand.getAbenteuer().getDrachen().clear();
		
		masterController.getAbenteuerController().phaseWechseln();
			
	}

}
