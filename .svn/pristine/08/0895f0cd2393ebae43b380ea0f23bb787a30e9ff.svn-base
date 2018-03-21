package control.regeln;

import java.util.List;

import control.MasterController;
import control.RegelController;
import model.Quelle;
import model.Schatz;
import model.Spielzustand;
import model.Ziel;
/**
 * 
 * Lege alle Drachenwürfel aus der Drachenhöhle ab, dies zählt nicht als Besiegen des Drachens. 
 * Keine Erfahrung keine Schätze
 *
 */
public class UnsichtbarkeitsringRegel extends RegelController {

	@Override
	public void setMasterController(MasterController masterController) {
		// TODO Auto-generated method stub
		super.setMasterController(masterController);
	}

	@Override
	public boolean pruefeAuswahlErlaubt(Quelle quelle, List<Ziel> ziel) {
		//quelle überprüfen
		return quelle.alsSchatz() == Schatz.UNSICHTBARKEITSRING;
	}

	@Override
	public void regelAusfuehren(Quelle quelle, List<Ziel> ziel) {
		Spielzustand geklonterZustand = masterController.getDungeonRoll().getAktuellerZustand().neuenSpielzustandErzeugen();
		masterController.getDungeonRoll().setAktuellerZustand(geklonterZustand);
		//quelle verarbeiten
		masterController.getSchatzController().schatzZuruecklegen(quelle.alsSchatz());
		//alle Würfel aus der Drachenhöhle entfernen
		masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDrachen().clear();
	}

}
