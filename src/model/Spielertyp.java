package model;

import java.io.Serializable;

/**Das Spielertyp-Enum dient der Unterscheidung verschiedener Typen von Spielern. Besonders wichtig
 * ist dies f�r die Unterscheidung zwischen menschlichen und KI-Spielern. Aber auch die verschiednen
 * KI-Strategien werden dar�ber unterschieden.
 * @author Rahel
 *
 */
public enum Spielertyp implements Serializable{

	MENSCHLICH,

	RISIKOFREUDIG,

	AUSGEGLICHEN,

	VORSICHTIG;
}
