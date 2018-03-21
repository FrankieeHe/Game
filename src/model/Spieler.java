package model;

import java.io.Serializable;

/**Die Klasse Spieler verwaltet die Werte eines Spielers, wobei es sich um einen menschlichen Spieler,
 * oder auch einen KI-Spieler handeln kann. Diese Unterscheidung wird �ber das Attribut spielertyp 
 * verwaltet. 
 * @author Rahel
 *
 */
public class Spieler implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3184243684284461368L;

	/**
	 * Der Name des Spielers.
	 */
	private String name;

	/**
	 * Der dem Spieler zugeordnete Held, ein Held ist h�chstens einem Spieler zugeordnet.
	 */
	private Held held;

	/**
	 * Enum-Wert, anhand dessen zwischen menschlichen Spielern und verschiednen KI-Typen differenziert
	 * werden kann.
	 */
	private Spielertyp spielertyp;

	/**
	 * Die vom Spieler im bisherigen Spielverlauf gesammelten Sch�tze und Erfahrungspunkte, die im
	 * aktuellen Abenteuer gesammelten Werte sind hier nicht enthalten.
	 */
	private SpielerWerte spielerWerte;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Held getHeld() {
		return held;
	}

	public void setHeld(Held held) {
		this.held = held;
	}

	public Spielertyp getSpielertyp() {
		return spielertyp;
	}

	public void setSpielertyp(Spielertyp spielertyp) {
		this.spielertyp = spielertyp;
	}

	public SpielerWerte getSpielerWerte() {
		return spielerWerte;
	}

	public void setSpielerWerte(SpielerWerte spielerWerte) {
		this.spielerWerte = spielerWerte;
	}

	@Override
	public String toString() {
		return "Spieler [name=" + name + ", held=" + held + ", spielertyp=" + spielertyp + ", spielerWerte="
				+ spielerWerte + "]";
	}
	
	public Spieler klonen()
	{
		Spieler klon = new Spieler();
		klon.setHeld(held.klonen());
		klon.setName(name);
		klon.setSpielertyp(spielertyp);
		klon.setSpielerWerte(spielerWerte.klonen());
		return klon;
	}

}