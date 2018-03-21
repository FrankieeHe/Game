package control.regeln;

import java.util.List;
import java.util.stream.Collectors;

import control.MasterController;
import control.RegelController;
import model.Quelle;
import model.Schatz;
import model.SchwarzeWuerfelseite;
import model.Spielzustand;
import model.Wuerfel;
import model.Ziel;
/**
 * 
 * Verwandelt alle Monster in Drachenköpfe und legt diese in der Drachenhöhle ab
 * 
 */
public class DrachenkoederRegel extends RegelController {

	@Override
	public void setMasterController(MasterController masterController) {
		super.setMasterController(masterController);
	}

	@Override
	public boolean pruefeAuswahlErlaubt(Quelle quelle, List<Ziel> ziel) {
		//quelle testen - muss drachenköder schatz sein
		if(quelle.alsSchatz() != Schatz.DRACHENKOEDER) return false;
		//ziele sind leer
		return ziel.isEmpty();
	}

	@Override
	public void regelAusfuehren(Quelle quelle, List<Ziel> ziel) {
		Spielzustand geklonterZustand = masterController.getDungeonRoll().getAktuellerZustand().neuenSpielzustandErzeugen();
		masterController.spielzustandSetzen(geklonterZustand);
		//quelle verarbeiten
		masterController.getSchatzController().schatzZuruecklegen(quelle.alsSchatz());
		//ziele verarbeiten - auf Drachenkopf ändern aus DungeonListe nehmen zur Höhle hinzufügen

		List<Wuerfel> dungeon = masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDungeon();
		List<Wuerfel> dragon = masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDrachen();
		List<Wuerfel> monster = dungeon.stream().filter(dungeonwuerfel -> dungeonwuerfel.isMonster()).collect(Collectors.toList());
		
		for(Wuerfel dungeonwuerfel : monster) {		
				masterController.getWuerfelController().wuerfelseiteErsetzen(dungeonwuerfel, SchwarzeWuerfelseite.DRACHENKOPF);		
		}
		dungeon.removeAll(monster);
		dragon.addAll(monster);
		masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().setDungeon(dungeon);
		masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().setDrachen(dragon);
		
		if(!monster.isEmpty()){
			masterController.getAbenteuerController().phaseWechseln();
		}
		
	}
}
