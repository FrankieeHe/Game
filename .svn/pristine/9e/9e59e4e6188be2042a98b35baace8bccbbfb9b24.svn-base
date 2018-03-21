package control.regeln;

import java.util.List;

import control.MasterController;
import control.RegelController;
import model.Heldtyp;
import model.Quelle;
import model.Spielzustand;
import model.WeisseWuerfelseite;
import model.Ziel;
/**
 * 
 * @author sopr104
 *
 */
public class WuerfelDrehen extends RegelController {

	@Override
	public void setMasterController(MasterController masterController) {
		super.setMasterController(masterController);
	}
	boolean pruefeKombination(Heldtyp ht, WeisseWuerfelseite s1, WeisseWuerfelseite s2, Quelle quelle, List<Ziel> ziel){
		return ht.equals(quelle.alsHeld().getHeldtyp()) && (ziel.stream().allMatch(z -> z.alsWuerfel().getWeisseWuerfelseite().equals(s1) || z.alsWuerfel().equals(s2)));
	}
	void seiteErsetzen(WeisseWuerfelseite s1, WeisseWuerfelseite s2, List<Ziel> ziel){
		for(Ziel z : ziel){
			if(s1.equals(z.alsWuerfel().getWeisseWuerfelseite())) 
				masterController.getWuerfelController().wuerfelseiteErsetzen(z.alsWuerfel(), s2); 
			else
				masterController.getWuerfelController().wuerfelseiteErsetzen(z.alsWuerfel(), s1);			
		}
	}

	@Override
	public boolean pruefeAuswahlErlaubt(Quelle quelle, List<Ziel> ziel) {
		// Pruefe ziele (Muss weisser Wuerfel sein der kein Champion ist)
		if(ziel.isEmpty() || quelle.alsHeld()==null) return false;
		for(Ziel z : ziel){
			if(z.alsWuerfel() == null) return false;
			if(!z.alsWuerfel().isWeiss()) return false;
			if(z.alsWuerfel().getWeisseWuerfelseite().equals(WeisseWuerfelseite.CHAMPION)) return false;
		}
		// Pruefe quelle (Muss Held von bestimmtem Typ sein)
		if (quelle.alsHeld() == null) {
			return false;
		}
		Heldtyp ht = quelle.alsHeld().getHeldtyp();
		switch(ht){
		case ADEPTINHEXE: //TODO Spruchrolle als alles einsetzbar
			break;
		case ARKANERSCHWERTMEISTERKAMPFMAGIER: //Krieger Zauberer
			return pruefeKombination(ht, WeisseWuerfelseite.KRIEGER, WeisseWuerfelseite.ZAUBERER, quelle,ziel);
		case KREUZRITTERINPALADININ: //Krieger Priester
			return pruefeKombination(ht, WeisseWuerfelseite.KRIEGER, WeisseWuerfelseite.PRIESTER, quelle, ziel);
		case MINNESAENGERBARDE: //Dieb Zauberer
			return pruefeKombination(ht, WeisseWuerfelseite.DIEB, WeisseWuerfelseite.ZAUBERER, quelle,ziel);
		case OKKULTISTINTOTENBESCHWOERERIN: //Priester Zauberer
			return pruefeKombination(ht,WeisseWuerfelseite.PRIESTER, WeisseWuerfelseite.ZAUBERER, quelle, ziel);
		default:
			return false;
		}
		return false;
	}

	@Override
	public void regelAusfuehren(Quelle quelle, List<Ziel> ziel) {
		Spielzustand geklonterZustand = masterController.getDungeonRoll().getAktuellerZustand().neuenSpielzustandErzeugen();
		masterController.getDungeonRoll().setAktuellerZustand(geklonterZustand);
		// quelle ermitteln
		Heldtyp ht = quelle.alsHeld().getHeldtyp();
		switch(ht){
		case ADEPTINHEXE:
			break;
		case ARKANERSCHWERTMEISTERKAMPFMAGIER:
			seiteErsetzen(WeisseWuerfelseite.KRIEGER, WeisseWuerfelseite.ZAUBERER, ziel);
			break;
		case KREUZRITTERINPALADININ:
			seiteErsetzen(WeisseWuerfelseite.PRIESTER, WeisseWuerfelseite.KRIEGER, ziel);
			break;
		case MINNESAENGERBARDE:
			seiteErsetzen(WeisseWuerfelseite.DIEB, WeisseWuerfelseite.ZAUBERER, ziel);
			break;
		case OKKULTISTINTOTENBESCHWOERERIN:
			seiteErsetzen(WeisseWuerfelseite.PRIESTER, WeisseWuerfelseite.ZAUBERER, ziel);
			break;
		default:
			break;
		}
	}

}
