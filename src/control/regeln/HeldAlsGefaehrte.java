package control.regeln;

import java.util.List;

import control.MasterController;
import control.RegelController;
import model.Heldtyp;
import model.Quelle;
import model.Spielzustand;
import model.WeisseWuerfelseite;
import model.Wuerfel;
import model.Ziel;
/**
 * Held als Gefaehrten einsetzen (temporären Würfel zurückgeben)
 * @author sopr104
 *
 */
public class HeldAlsGefaehrte extends RegelController {

	@Override
	public void setMasterController(MasterController masterController) {
		// TODO Auto-generated method stub
		super.setMasterController(masterController);
	}

	@Override
	public boolean pruefeAuswahlErlaubt(Quelle quelle, List<Ziel> ziel) {
		
		// quelle prüfen
		if(quelle.alsHeld() == null || quelle.alsHeld().isUltimativeEingesetzt()) return false;
		if(!Heldtyp.ARKANERSCHWERTMEISTERKAMPFMAGIER.equals(quelle.alsHeld())
		&& !Heldtyp.KREUZRITTERINPALADININ.equals(quelle.alsHeld())){
				return false;
		}
		return ziel == null || ziel.isEmpty();
	}

	@Override
	public void regelAusfuehren(Quelle quelle, List<Ziel> ziel) {
		Spielzustand geklonterZustand = masterController.getDungeonRoll().getAktuellerZustand()
				.neuenSpielzustandErzeugen();
		masterController.getDungeonRoll().setAktuellerZustand(geklonterZustand);
		Wuerfel wuerfel = new Wuerfel(true);
		wuerfel.setTemporaer(true);
		if(Heldtyp.ARKANERSCHWERTMEISTERKAMPFMAGIER.equals(quelle.alsHeld().getHeldtyp())){
			wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.ZAUBERER);
		}
		if(Heldtyp.KREUZRITTERINPALADININ.equals(quelle.alsHeld().getHeldtyp())){
			wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.PRIESTER);
		}

		List<Wuerfel> gefaehrten = masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getGefaehrten();
		gefaehrten.add(wuerfel);
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld().setUltimativeEingesetzt(true);
	}

}
