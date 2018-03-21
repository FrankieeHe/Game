package control;

import java.util.List;
import java.util.Random;

import model.Schatz;

public class SchatzController {
	
	private MasterController masterController;
	
	public SchatzController(MasterController masterController) {
		this.masterController = masterController;
	}
	/**
	 * @author Julian
	 * @param schatz übergibt den Schatz der in die Schatztruhe zurückgelegt werden soll
	 * Gibt einen ausgewählten/benutzten Schatz zurück in die Schatztruhe
	 */
	public void schatzZuruecklegen(Schatz schatz) {
		List<Schatz> schaetzeAbenteuer = masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getSpielerWerte().getSchaetze();
		List<Schatz> schatztruhe = masterController.getDungeonRoll().getAktuellerZustand().getSchaetze();
		if(schaetzeAbenteuer.contains(schatz)){
			schaetzeAbenteuer.remove(schatz);
		}
		else{
			//Es wird angenommen, dass der Schatz dann auf jeden Fall in den Schätzen des aktuellen Spielers ist!
			List<Schatz> schaetzeSpieler = masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getSpielerWerte().getSchaetze();
			schaetzeSpieler.remove(schatz);
		}
		schatztruhe.add(schatz);
		
	}
	/**
	 * @author Julian
	 * Zieht einen zufälligen Schatz aus der Schatzkiste, falls einer vorhanden ist. In dem Fall wird dieser dann aus der 
	 * Truhe entfernt.
	 * @returns null falls kein Schatz vorhanden ist
	 * @returns Schatz den zufällig ausgewählten Schatz aus der Schatztruhe
	 */
	public Schatz schatzZiehen() {
		List<Schatz> schatztruhe = masterController.getDungeonRoll().getAktuellerZustand().getSchaetze();
		if(!schatztruhe.isEmpty()){
			Random randGen = new Random();
	        int index = randGen.nextInt(schatztruhe.size());
	        //remove gibt den Schatz zurück, der entfernt wurde
	        return schatztruhe.remove(index);     
		}
		else{
			return null;
		}		
	}

}
