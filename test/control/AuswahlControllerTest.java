package control;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import model.SchwarzeWuerfelseite;
import model.Wuerfel;
import model.Ziel;

/**
 * Eine JUnit-Testklasse für den {@link AuswahlController}
 * @author Pascal
 *
 */
public class AuswahlControllerTest {

	/**
	 * Testet, ob die Potenzmengen-Generatormethode (für 0-5 Wuerfel) funktioniert.<br>
	 * (Die Anzahl der Elemente der Potenzmenge muss 2 ^ |Ursprungsmenge| betragen).
	 */
	private static final int ANZAHL_SCHWARZE_WUERFELSEITEN = SchwarzeWuerfelseite.values().length;
	
	@Test
	/**
	 * Testet, ob die Potenzmengen korrekt erstellt werden (0-5 Elemente in der Grundmenge)
	 */
	public void testPotenzmengeAufbauen() {
		final Random random = new Random();
		for (int i = 0; i < 6; i++) {
			final Set<Ziel> potentielleZiele = new HashSet<>();
			for (int j = 0; j< i; j++) {
				final Wuerfel wuerfel = new Wuerfel(true);
				final int valueIdx = random.nextInt(ANZAHL_SCHWARZE_WUERFELSEITEN);
				wuerfel.setSchwarzeWuerfelseite(getSchwarzeWuerfelseite(valueIdx));
				potentielleZiele.add(wuerfel);
			}
			final Set<Set<Ziel>> potenzmenge = AuswahlController.bauePotenzmenge(potentielleZiele);
			assertEquals((int)Math.pow(2, i), potenzmenge.size());
		}
		
	}

	private static SchwarzeWuerfelseite getSchwarzeWuerfelseite(final int valueIdx) {
		final SchwarzeWuerfelseite[] schwarzeSeiten = SchwarzeWuerfelseite.values();
		return schwarzeSeiten[valueIdx];
	}
	
}
