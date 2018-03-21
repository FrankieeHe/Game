package control.regeln;

import java.util.List;

import control.MasterController;
import control.RegelController;
import model.Quelle;
import model.Schatz;
import model.Spielzustand;
import model.Ziel;

public class StadtportalRegel extends RegelController {

	@Override
	public void setMasterController(MasterController masterController) {
		super.setMasterController(masterController);
	}

	@Override
	public boolean pruefeAuswahlErlaubt(Quelle quelle, List<Ziel> ziel) {
		if(quelle.alsSchatz()==null) return false;
		else return quelle.alsSchatz() == Schatz.STADTPORTAL; 
	}

	@Override
	public void regelAusfuehren(Quelle quelle, List<Ziel> ziel) {
		Spielzustand geklonterZustand = masterController.getDungeonRoll().getAktuellerZustand().neuenSpielzustandErzeugen();
		masterController.getDungeonRoll().setAktuellerZustand(geklonterZustand);
		//Stadtportal zurücklegen
		masterController.getSchatzController().schatzZuruecklegen(quelle.alsSchatz());
		//Abenteuer beenden
		//Spieler Erfahrungspunkte entsprechend des Levels hinzufügen
		masterController.getAbenteuerController().abenteuerBeenden();
	}

}
