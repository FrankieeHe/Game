package model;

import java.io.Serializable;

/**
 * Die Phase ist nach den Phasen benannt, die in den DungeonRoll Regeln definiert sind.<br>
 * In jeder Phase sind andere Aktionen möglich.<br>
 * Dieses Enum stellt die verschieden Phasen bereit, um sie, bzw. die aktuelle Phase, im {@link Abenteuer} zu speichern.
 * @author Pascal
 *
 */
public enum Phase implements Serializable{

	WUERFELPHASE,

	PLUENDERPHASE,

	KAMPFPHASE,

	UMGRUPPIERUNGSPHASE,

	DRACHENPHASE;

}
