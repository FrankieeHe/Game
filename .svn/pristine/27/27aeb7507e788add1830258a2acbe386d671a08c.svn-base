/**
 * 
 */
package control.regeln;

import java.util.List;

import control.MasterController;
import control.RegelController;
import model.Held;
import model.Heldtyp;
import model.Quelle;
import model.SchwarzeWuerfelseite;
import model.Spielzustand;
import model.Wuerfel;
import model.Ziel;

/**
 * Adaptin verwandle Monster in ein Trank
 * 
 * @author he
 *
 */
public class AdeptinRegel extends RegelController {

	/**
	 * 
	 */
	@Override
	public void setMasterController(MasterController masterController) {
		// TODO Auto-generated method stub
		super.setMasterController(masterController);
	}

	@Override
	public boolean pruefeAuswahlErlaubt(Quelle quelle, List<Ziel> ziel) {
		boolean erlaubt = false;
		if (quelle.alsHeld() != null) {
			Held held = quelle.alsHeld();
			if (held.getHeldtyp() == Heldtyp.ADEPTINHEXE) {
				if (!held.isUltimativeEingesetzt()) {
					// UF Stufe 1 ist nur ein Monster gegen Trank ersetzen
					if (ziel.size() == 1 && !held.isLevelUp()) {
						if (ziel.get(0).alsWuerfel() != null) {
							Wuerfel monster = ziel.get(0).alsWuerfel();
							if (!monster.isWeiss()) {
								if (monster.getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.KOBOLD
										|| monster.getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.SCHLEIMWESEN
										|| monster.getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.SKELETT) {
									erlaubt = true;
								}
							}
						}
					} else // UF Stufe 2 ist zwei Monster gegen 2 Traenke ersetzen
					if (ziel.size() == 2) {
						if (ziel.get(0).alsWuerfel() != null && ziel.get(1).alsWuerfel() != null) {
							Wuerfel monster_1 = ziel.get(0).alsWuerfel();
							Wuerfel monster_2 = ziel.get(1).alsWuerfel();
							if (!monster_1.isWeiss() && !monster_2.isWeiss()) {
								if (monster_1.getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.KOBOLD
										|| monster_1.getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.SCHLEIMWESEN
										|| monster_1.getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.SKELETT) {
									if (monster_2.getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.KOBOLD
											|| monster_2.getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.SCHLEIMWESEN
											|| monster_2.getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.SKELETT) {
										erlaubt = true;
									}
								}
							}
						}
					}
				}
			}
		}
		return erlaubt;
	}

	@Override
	public void regelAusfuehren(Quelle quelle, List<Ziel> ziel) {
		// Spielzustaend klonen & aktualisieren
		Spielzustand geklonterZustand = masterController.getDungeonRoll().getAktuellerZustand()
				.neuenSpielzustandErzeugen();
		masterController.getDungeonRoll().setAktuellerZustand(geklonterZustand);

		// UF auf genutzt setzen
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld()
				.setUltimativeEingesetzt(true);

		// Monster auf Trank drehen
		for (Ziel aktuellesZiel : ziel) {
			masterController.getWuerfelController().wuerfelseiteErsetzen(aktuellesZiel.alsWuerfel(),
					SchwarzeWuerfelseite.TRANK);
		}
		
		masterController.getAbenteuerController().phaseWechseln();
	}

}
