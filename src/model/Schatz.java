package model;

import java.io.Serializable;

/**Das Schatz-Enum dient der Unterscheidung verschiedener Typen von Sch�tzen. Dies ist relevant
 * f�r das Einsetzen der Sch�tze.
 * @author Rahel
 * 
 */
public enum Schatz implements Quelle, Ziel, Serializable {

	SPRUCHROLLE,

	DRACHENSCHUPPEN,

	DIEBESWERKZEUG,

	DRACHENKOEDER,

	KOEPFERKLINGE,

	TALISMAN,

	STADTPORTAL,

	ZEPTERDERMACHT,

	TRANK,

	UNSICHTBARKEITSRING;

	/**
	 * @see model.Quelle#alsWuerfel()
	 */
	public Wuerfel alsWuerfel() {
		return null;
	}


	/**
	 * @see model.Quelle#alsHeld()
	 */
	public Held alsHeld() {
		return null;
	}


	/**Gibt den aktuellen Schatz zur�ck.
	 * @see model.Quelle#alsSchatz()
	 */
	public Schatz alsSchatz() {
		return this;
	}

}
