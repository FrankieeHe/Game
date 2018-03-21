/**
 * 
 */
package control;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.Phase;
import model.Spielzustand;
import model.Spieler;
/**
 * Eine JUnit-Testklasse für den {@link AbenteuerController}}
 * @author sopr102
 *
 */
public class AbenteuerControllerTest {
	private MasterController mc;
	private AbenteuerController ac;
	public void init() {
		mc = new MasterController();
		mc.getDungeonRoll().setAktuellerZustand(new Spielzustand());
		List<Spieler> spielers = new ArrayList(){{
			add(new Spieler());
			add(new Spieler());
			add(new Spieler());
			add(new Spieler());
			}} ;
		mc.getDungeonRoll().getAktuellerZustand().setSpieler(spielers);
		
		ac = new AbenteuerController(mc);
		
	}

	@Test
	/**
	 * test, ob die Spiel richtig start werden
	 */
	public void testneuesAbenteuerErstellen() {
		init();
		ac.neuesAbenteuerErstellen();
		Spielzustand sz = mc.getDungeonRoll().getAktuellerZustand();
		
		assertEquals(sz.getSchaetze(),null);

		assertEquals(sz.getAbenteuer().getPhase(),Phase.KAMPFPHASE);

		assertEquals(sz.aktuellerSpieler().getHeld().isLevelUp(),false);
		
		assertEquals(sz.aktuellerSpieler().getSpielerWerte().getErfahrungspunkte(),0);
		
		assertEquals(sz.getAbenteuer().getDungeon().size()+sz.getAbenteuer().getDrachen().size(),1);

		assertEquals(sz.getAbenteuer().getGefaehrten().size(),7);
				
		assertEquals(sz.getAbenteuer().getFriedhof().size(),0);
		
		
	}

}
