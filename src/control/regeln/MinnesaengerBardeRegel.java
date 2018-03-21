package control.regeln;

import java.util.List;

import control.MasterController;
import control.RegelController;
import model.Held;
import model.Heldtyp;
import model.Quelle;
import model.Spielzustand;
import model.Ziel;

public class MinnesaengerBardeRegel extends RegelController {

	@Override
	public void setMasterController(MasterController masterController) {
		super.setMasterController(masterController);
	}

	@Override
	public boolean pruefeAuswahlErlaubt(Quelle quelle, List<Ziel> ziel) {
		if(!ziel.isEmpty()) {
			return false;
		}
		if (quelle.alsHeld() == null) {
			return false;
		}
		Held held = quelle.alsHeld();
		if (held.getHeldtyp() != Heldtyp.MINNESAENGERBARDE) {
			return false;
		}
		if (held.isUltimativeEingesetzt()) {
			return false;
		}
		return true;

	}

	@Override
	public void regelAusfuehren(Quelle quelle, List<Ziel> ziel) {
		// Spielzustand klonen und aktualisieren
		Spielzustand geklonterZustand = masterController.getDungeonRoll().getAktuellerZustand()
				.neuenSpielzustandErzeugen();
		masterController.getDungeonRoll().setAktuellerZustand(geklonterZustand);
		geklonterZustand.getAbenteuer().getDrachen().clear();
		geklonterZustand.aktuellerSpieler().getHeld().setUltimativeEingesetzt(true);
		System.out.println("Drache besänftigt");
		}

}