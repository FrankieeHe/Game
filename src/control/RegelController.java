package control;

import java.util.List;

import model.Quelle;
import model.Ziel;



public abstract class RegelController {

	protected MasterController masterController;
	
	public RegelController() {
		
	}
	
	public void setMasterController(MasterController masterController) {
		this.masterController = masterController;
	}
	
/**
 * @author Merle
 * @param Quelle Loest Aktion aus
 * @param ziel wird durch die Aktion beeinflusst
 * @return false, Handlung nicht moeglich
 * @return true, Handlung moeglich
 * <br/>
 * @description prueft, ob mit der Quelle die Ziele veraendert werden duerfen
 */
	public boolean pruefeAuswahlErlaubt(Quelle quelle, List<Ziel> ziel) {
		return false;
	}
	
	/**
 * @author Merle
 * @param quelle Ursache fuer die Aktion
 * @param zelle Werden von der Aktion beeinflusst
 * <br/>
 * @description fuehrt die Handlung gemaess der Regel aus
 */
	public void regelAusfuehren(Quelle quelle, List<Ziel> ziel) {
		
	}

}
