package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**Die SpielerWerte werden sowohl von Abenteuer, als auch von Spieler genutzt um die von einem Spieler gesammelten
 * Erfahrungspunkte und Sch�tze festzuhalten. Dabei greift das aktuelle Abenteuer auf eine andere Instanz der SpielerWerte
 * zu als die des aktuellen Spieler-Objekts, da die in einem Abenteuer gesammelten Sch�tze und Erfahrungspunkte zun�chst nur
 * dort gespeichert werden, um ein Entfernen bei Scheitern des Abenteuers einfach zu erm�glichen.
 * @author Rahel
 *
 */
public class SpielerWerte implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2738353299425005621L;

	/**
	 * Die vom Spieler gesammelten Erfahrungspunkte.
	 */
	private int erfahrungspunkte;
	
	/**
	 * Die vom Spieler gesammelten Sch�tze
	 */
	private List<Schatz> schaetze;
	
public SpielerWerte() {
		schaetze = new ArrayList<Schatz>();
	}

	public int getErfahrungspunkte() {
		return erfahrungspunkte;
	}

	public void setErfahrungspunkte(int erfahrungspunkte) {
		this.erfahrungspunkte = erfahrungspunkte;
	}

	public List<Schatz> getSchaetze() {
		return schaetze;
	}

	public void setSchaetze(List<Schatz> schaetze) {
		this.schaetze = schaetze;
	}

	@Override
	public String toString() {
		return "SpielerWerte [erfahrungspunkte=" + erfahrungspunkte + ", schaetze=" + schaetze + "]";
	}

	public SpielerWerte klonen() {
		SpielerWerte klon = new SpielerWerte();
		klon.setErfahrungspunkte(erfahrungspunkte);
		List<Schatz> neueSchaetzeListe = new ArrayList<>();
		neueSchaetzeListe.addAll(schaetze);
		klon.setSchaetze(neueSchaetzeListe);
		return klon;
	}
}
