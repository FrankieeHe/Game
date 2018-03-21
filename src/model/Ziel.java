package model;

/**
 * Ein Ziel ist eine Entität, auf der eine Regel ausgeführt werden kann.<br>
 * Es kann sich dabei um einen Würfel als auch um einen Schatz handeln.
 * @author Pascal
 *
 */
public interface Ziel{
	
	/**
	 * casted dieses Ziel als Würfel und gibt diesen zurück
	 */
	public abstract Wuerfel alsWuerfel();

	/**
	 * casted dieses Ziel als Schatz und gibt diesen zurück
	 */
	public abstract Schatz alsSchatz();

}
