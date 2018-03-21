package control.regeln;

import java.util.ArrayList;
import java.util.List;

import control.MasterController;
import control.RegelController;
import model.Held;
import model.Heldtyp;
import model.Quelle;
import model.Spieler;
import model.Spielzustand;
import model.Wuerfel;
import model.Ziel;

public class SoeldnerRegel extends RegelController {

	@Override
	public void setMasterController(MasterController masterController) {
		super.setMasterController(masterController);
	}

	@Override
	public boolean pruefeAuswahlErlaubt(Quelle quelle, List<Ziel> ziel) {
		if (quelle.alsHeld() == null || ziel == null || ziel.isEmpty()) {
			return false;
		}
		
		Held held = quelle.alsHeld();
		if (held.getHeldtyp() != Heldtyp.SOELDNERKOMMANDANT) {
			return false;
		}
		
		if(held.isLevelUp()) {
			return false;
		}
		
		boolean ersteAktion = false;
		
		if(masterController.getDungeonRoll().getAktuellerZustand().getSpieler().size()!=1) {
			Spieler aktuellerSpieler = masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler();
			Spieler vorherigerSpieler = null;
			if (masterController.getDungeonRoll().getAktuellerZustand().getVorherigerSpielzustand() != null) {
				masterController.getDungeonRoll().getAktuellerZustand().getVorherigerSpielzustand().aktuellerSpieler();
			}
			if(aktuellerSpieler != vorherigerSpieler) {
				ersteAktion = true;
			}
		}else {
			if(masterController.getDungeonRoll().getAktuellerZustand().getVorherigerSpielzustand() != null) {
				ersteAktion = masterController.getDungeonRoll().getAktuellerZustand().getRunde() != masterController.getDungeonRoll().getAktuellerZustand().getVorherigerSpielzustand().getRunde();
			}else {
				ersteAktion = true;
			}
		}
		
		if(!ersteAktion) {
			return false;
		}
		
		for(Ziel aktuellesZiel : ziel) {
			if(aktuellesZiel.alsWuerfel() == null) {
				return false;
			}
			if(!masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getGefaehrten().contains(aktuellesZiel.alsWuerfel())) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void regelAusfuehren(Quelle quelle, List<Ziel> ziel) {
		// Spielzustand klonen und aktualisieren
		Spielzustand geklonterZustand = masterController.getDungeonRoll().getAktuellerZustand().neuenSpielzustandErzeugen();
		masterController.getDungeonRoll().setAktuellerZustand(geklonterZustand);
		
		List<Wuerfel> wuerfel = new ArrayList<Wuerfel>();
		
		//Wuerfel der Zielliste neu wuerfeln
		for(Ziel aktuellesZiel : ziel) {
			wuerfel.add(aktuellesZiel.alsWuerfel());
		}
		
		masterController.getWuerfelController().wuerfeln(wuerfel);
			
	}

}
