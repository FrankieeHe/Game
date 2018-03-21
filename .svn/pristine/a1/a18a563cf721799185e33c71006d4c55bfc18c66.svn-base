package control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import model.HighscoreEintrag;
import model.Spielzustand;

public class SpeicherController {

	private MasterController masterController;

	public SpeicherController(MasterController masterController) {
		this.masterController = masterController;
	}

	/**
	 * Alle Spieldaten werden in einer Datei gesichert.
	 * 
	 * @param pfad
	 *            Dateipfad, in welchen das Spiel exportiert werden soll.
	 * @return <b>true</b>, wenn keine Exception aufgetreten ist, ansonsten
	 *         <b>false</b>
	 */
	public boolean spielSpeichern(String pfad) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(pfad);
			ObjectOutputStream o = new ObjectOutputStream(fos);
			o.writeObject(masterController.getDungeonRoll().getAktuellerZustand());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Die Daten eines Spiels werden aus einer Datei geladen.
	 * 
	 * @param pfad
	 *            Dateipfad, aus welchem das Spiel geladen werden soll
	 * @return <b>true</b>, wenn keine Exception aufgetreten ist, ansonsten
	 *         <b>false</b>
	 */
	public boolean spielLaden(String pfad) {

		InputStream fis = null;
		try {
			fis = new FileInputStream(pfad);
			ObjectInputStream o = new ObjectInputStream(fis);

			Spielzustand sz = (Spielzustand) o.readObject();
			masterController.spielzustandSetzen(sz);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Das gleiche wie Spiel speichern, allerdings wird das Spiel auch
	 * geschlossen.
	 */
	public void spielUnterbrechen() {

	}

	/**
	 * Lädt die Highscore-Liste aus der Datei "Highscores.hs"
	 * @return <b>true</b>, wenn keine Exception aufgetreten ist, ansonsten
	 *         <b>false</b>
	 */
	public boolean highscoresLaden() {
		InputStream fis = null;
		try {
			fis = new FileInputStream("Highscores.hs");
			ObjectInputStream o = new ObjectInputStream(fis);

			List<HighscoreEintrag> he = (List<HighscoreEintrag>) o.readObject();
			masterController.getDungeonRoll().setHighscore(he);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Speichert die Highscore-Liste in die Datei "Highscores.hs"
	 * @return <b>true</b>, wenn keine Exception aufgetreten ist, ansonsten
	 *         <b>false</b>
	 */
	public boolean highscoreSpeichern() {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("Highscores.hs");
			ObjectOutputStream o = new ObjectOutputStream(fos);
			o.writeObject(masterController.getDungeonRoll().getHighscore());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
