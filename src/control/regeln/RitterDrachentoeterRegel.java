package control.regeln;

import java.util.ArrayList;
import java.util.List;

import control.MasterController;
import control.RegelController;
import model.Heldtyp;
import model.Quelle;
import model.SchwarzeWuerfelseite;
import model.Spielzustand;
import model.Wuerfel;
import model.Ziel;

public class RitterDrachentoeterRegel extends RegelController {

	@Override
	public void setMasterController(MasterController masterController) {
		// TODO Auto-generated method stub
		super.setMasterController(masterController);
	}

	@Override
	public boolean pruefeAuswahlErlaubt(Quelle quelle, List<Ziel> ziel) {
		if(quelle.alsHeld() != null) {
			if(quelle.alsHeld().getHeldtyp() == Heldtyp.RITTERDRACHENTOETER) {
				if(!quelle.alsHeld().isUltimativeEingesetzt()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void regelAusfuehren(Quelle quelle, List<Ziel> ziel) {
		// Spielstand klonen und aktualisieren
		Spielzustand geklonterZustand = masterController.getDungeonRoll().getAktuellerZustand().neuenSpielzustandErzeugen();
		masterController.getDungeonRoll().setAktuellerZustand(geklonterZustand);
		
		// UF auf genutzt setzen
		geklonterZustand.aktuellerSpieler().getHeld().setUltimativeEingesetzt(true);
		
		// Monster in Drachenkoepfe verwandeln 
		List<Wuerfel> monsterWuerfel = masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDungeon();
		List<Wuerfel> drachenWuerfel = masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDrachen();
		
		List<Wuerfel> tempMonsterWuerfel = new ArrayList<>(monsterWuerfel);
		for(Wuerfel monster : tempMonsterWuerfel) {
			if(monster.getSchwarzeWuerfelseite()!= SchwarzeWuerfelseite.TRANK && monster.getSchwarzeWuerfelseite() != SchwarzeWuerfelseite.TRUHE) {
				masterController.getWuerfelController().wuerfelseiteErsetzen(monster, SchwarzeWuerfelseite.DRACHENKOPF);
				monsterWuerfel.remove(monster);
				drachenWuerfel.add(monster);
			}
		}
		
		masterController.getAbenteuerController().phaseWechseln();
	}

}
