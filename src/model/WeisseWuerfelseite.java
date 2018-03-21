package model;

import java.io.Serializable;

/**
 * Diese Klasse bietet eine Abstraktion für die Würfelseiten der Gefährtenwürfel.<br>
 * Jede Konstante entspricht einer Würfelseite. 
 * @author Pascal
 *
 */
public enum WeisseWuerfelseite implements Serializable{

	CHAMPION,

	KRIEGER,

	PRIESTER,

	ZAUBERER,

	DIEB,

	SPRUCHROLLE;
}
