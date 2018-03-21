package control;

import java.util.List;

import model.HighscoreEintrag;
import model.Spieler;
import model.Spielertyp;
import model.Spielzustand;

/**
 * Controller für Highscore
 * 
 * @author Dmytro
 */
public class HighscoreController {

	private MasterController masterController;
	
	private List<HighscoreEintrag> highscore;
	
	public HighscoreController(MasterController masterController) {
		this.masterController = masterController;
	}
	
	/**
	 * Ändert die Highscore-Liste anhand vom auktuellen Spielzustand, falls der highscore-Relevant ist.
	 * @param spiel - aktueller Spielzustand
	 */
	
	public void highscoreEintragen(Spielzustand spiel) {
		if (!IstHighscoreZulaessig(spiel)) return;
		
		highscore = masterController.getDungeonRoll().getHighscore();
		
		for(Spieler spieler : spiel.getSpieler()) {
			if (spieler.getSpielertyp() != Spielertyp.MENSCHLICH) continue;
			
			HighscoreEintrag he = new HighscoreEintrag(spieler.getName(), 
                    spieler.getSpielerWerte().getErfahrungspunkte(),
                    spiel.getRunde());
			int platz = highscorePlatzSuchen(he);
			eintragErsetzen(platz, he);
		}
	}

	/**
	 * Prüft, ob Highscore zulässig ist.
	 * @param spiel - aktueller Spielzustand
	 */
	public boolean IstHighscoreZulaessig(Spielzustand spiel) {
		return spiel.isHighscoreRelevant();
	}
	
	/**
	 * Sucht den Platz in Highscore-Liste für den übergebenen HighscoreEintrag.
	 * @param he - aktueller - HighscoreEintrag zum Suchen
	 * @return platz - der PLatz für he
	 */
	private int highscorePlatzSuchen(HighscoreEintrag he) {
		int platz = -1;
		for(int i = 0;i < highscore.size() && platz == -1;i++) {
			HighscoreEintrag cur = highscore.get(i);
			if (he.compareTo(cur) > 0) {
				platz = i;
			}
		}
		
		return platz;
	}
	
	/**
	 * Stellt HighscoreEintrag auf passenden Platz. 
	 * @param platz - Platz für neue HighscoreEintrag
	 * @param he - neue HigshcoreEintrag
	 */
	private void eintragErsetzen(int platz, HighscoreEintrag he) {
		if (platz != -1) {
			for(int i = highscore.size()-1;i > platz;i--) {
				highscore.set(i, highscore.get(i - 1));
			}
			highscore.set(platz, he);
		}
		else if (highscore.size() < 10){
			highscore.add(he);
		}
	}
}
