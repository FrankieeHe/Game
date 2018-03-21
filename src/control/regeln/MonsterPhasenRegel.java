package control.regeln;

import java.util.List;
import java.util.stream.Collectors;

import control.MasterController;
import control.RegelController;
import model.Held;
import model.Heldtyp;
import model.Phase;
import model.Quelle;
import model.SchwarzeWuerfelseite;
import model.Spielzustand;
import model.WeisseWuerfelseite;
import model.Wuerfel;
import model.Ziel;

/**
 * @description Regel fuer die MonsterPhase
 * 
 * @author sopr102
 *
 */

public class MonsterPhasenRegel extends RegelController {

	@Override
	public void setMasterController(MasterController masterController) {
		super.setMasterController(masterController);
	}

	/**
	 * @description Realsiert die Kampfregeln der Kampfphase gegen Monster *
	 * @param quelle
	 *            Ursache fuer die Aktion
	 * @param ziel
	 *            Monster die bekaempft werden sollen
	 * @return true wenn Monster mit dem gewaehlten Gefaherten(jeglicher Art)
	 *         besiegt werden koennen
	 * @return false wenn Monster so nicht besiegt werden koennen
	 */
	@Override
	public boolean pruefeAuswahlErlaubt(Quelle quelle, List<Ziel> ziel) {
		if (ziel == null || ziel.isEmpty()) {
			return false;
		}

		if (quelle.alsHeld() == null) {
			System.out.println("quelle ist kein Held");
		} else if (quelle.alsSchatz() == null) {
			System.out.println("quelle ist kein Schatz");
		} else if (quelle.alsWuerfel() == null) {
			System.out.println("quelle ist kein wuerfel");
		}
		boolean zieleOk, quelleOk = false, phaseOk = Phase.KAMPFPHASE == masterController.getDungeonRoll()
				.getAktuellerZustand().getAbenteuer().getPhase();
		// Pruefe ob alle Ziele Wuerfel sind
		zieleOk = ziel.stream().allMatch(aktuellesZiel -> {
			return (aktuellesZiel.alsWuerfel() != null) && (aktuellesZiel.alsWuerfel().isMonster());
		});

		if (quelle.alsHeld() != null) {
			System.out.println("MonsterPhaseRegel=> Quelle ist Held");
			quelleOk = testeFaehigkeitHelden(quelle.alsHeld(), ziel);
			System.out.println(quelleOk);
		} else if (quelle.alsSchatz() != null) {
			// Erstellt einen Wuerfel, dreht diesen auf die entsprechende Gefaehrtenseite
			// und ruft anschließend die
			// Gefaehrten Test Methode auf
			Wuerfel schatzWuerfel = new Wuerfel(true);
			switch (quelle.alsSchatz()) {
			case DIEBESWERKZEUG:
				masterController.getWuerfelController().wuerfelseiteErsetzen(schatzWuerfel, WeisseWuerfelseite.DIEB);
				break;
			case KOEPFERKLINGE:
				masterController.getWuerfelController().wuerfelseiteErsetzen(schatzWuerfel, WeisseWuerfelseite.KRIEGER);
				break;
			case SPRUCHROLLE:
				masterController.getWuerfelController().wuerfelseiteErsetzen(schatzWuerfel,
						WeisseWuerfelseite.SPRUCHROLLE);
				break;
			case TALISMAN:
				masterController.getWuerfelController().wuerfelseiteErsetzen(schatzWuerfel,
						WeisseWuerfelseite.PRIESTER);
				break;
			case ZEPTERDERMACHT:
				masterController.getWuerfelController().wuerfelseiteErsetzen(schatzWuerfel,
						WeisseWuerfelseite.ZAUBERER);
				break;
			default:
				return false;
			}
			quelleOk = testeGefaehrten(schatzWuerfel, ziel);
		} else// Teste Gefährten
		if (quelle.alsWuerfel() != null)
			quelleOk = testeGefaehrten(quelle.alsWuerfel(), ziel);

		return zieleOk && quelleOk && phaseOk;
	}

	private boolean testeGefaehrten(Wuerfel wuerfel, List<Ziel> ziel) {
		return testeGefaehrten(wuerfel, ziel, false);
	}

	/**
	 * @description Geprueft wird, ob man die uebergebene Zielliste, mit dem
	 *              gewaehlten Wuerfel, unter Beachtung der Heldenfaehigkeiten,
	 *              besiegen kann
	 * @param wuerfel
	 *            Gefaehrte
	 * @param ziel
	 *            Zielliste
	 * @return true wenn Gefaehrtenwuerfel die Ziele besigen koennen
	 * @return false wenn Gefaehrtenwuerfel die Ziele nicht besiegen koennen
	 */
	private boolean testeGefaehrten(Wuerfel wuerfel, List<Ziel> ziel, boolean isExecutedRecursively) {
		if (!wuerfel.isWeiss())// Gefaehrten sind weiss
			return false;
		switch (wuerfel.getWeisseWuerfelseite()) {
		case CHAMPION:
			// Barde kann mit Champion ein bel. extra Monster umhauen
			if (masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld()
					.getHeldtyp() == Heldtyp.MINNESAENGERBARDE
					&& masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld()
							.isLevelUp()) {
				int[] anzMonster = new int[3];
				for (Ziel z : ziel) {
					switch (z.alsWuerfel().getSchwarzeWuerfelseite()) {
					case KOBOLD:
						anzMonster[0]++;
						break;
					case SCHLEIMWESEN:
						anzMonster[1]++;
						break;
					case SKELETT:
						anzMonster[2]++;
						break;
					default:
						break;
					}
				} // kein Kobold max 1 Schleimwesen beliebig viele Skelette
				if ((anzMonster[0] == 0 && anzMonster[1] <= 1 && anzMonster[2] >= 1)
						// kein Kobold beliebige Schleimwesen max 1 Skelett
						|| (anzMonster[0] == 0 && anzMonster[1] >= 1 && anzMonster[2] <= 1)
						// max ein Schleimwesen beliebige Schleimwesen kein Skelett
						|| (anzMonster[0] <= 1 && anzMonster[1] >= 1 && anzMonster[2] == 0)
						// max ein Schleimwesen kein Schleimwesen beliebige Skelette
						|| (anzMonster[0] <= 1 && anzMonster[1] == 0 && anzMonster[2] >= 1)
						// beliebige Schleimwesen max ein Schleimwesen kein Skelett
						|| (anzMonster[0] >= 1 && anzMonster[1] <= 1 && anzMonster[2] == 0)
						// beliebig Schleimwesen kein Schleimwesen max ein Skelett
						|| (anzMonster[0] >= 1 && anzMonster[1] == 0 && anzMonster[2] <= 1)) {
					return true;
				} else {
					return false;
				}
			} else {
				if (ziel.stream().allMatch(z -> z.alsWuerfel() != null && !z.alsWuerfel().isWeiss())) {
					int unterschieldicheWuerfel = ziel.stream().map(z -> z.alsWuerfel().getSchwarzeWuerfelseite())
							.collect(Collectors.toSet()).size();
					return unterschieldicheWuerfel == 1;
				}
				return false;
			}
		case DIEB:
			if (masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld()
					.getHeldtyp() == Heldtyp.MINNESAENGERBARDE) {
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.ZAUBERER);
				boolean result = !isExecutedRecursively && testeGefaehrten(wuerfel, ziel, true);
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.DIEB);
				return result;
			} else {
				return ziel.size() == 1;
			}
		case KRIEGER:
			boolean isKommandant = (masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld()
					.getHeldtyp() == Heldtyp.SOELDNERKOMMANDANT
					&& masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld()
							.isLevelUp());
			int kobolde = 0, andere = 0;
			for (Ziel z : ziel) {
				if (z.alsWuerfel() != null && z.alsWuerfel().getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.KOBOLD)
					kobolde++;
				else
					andere++;
			}
			boolean kriegerOk = andere == 0;
			if (!isExecutedRecursively && !kriegerOk && masterController.getDungeonRoll().getAktuellerZustand()
					.aktuellerSpieler().getHeld().getHeldtyp() == Heldtyp.KREUZRITTERINPALADININ) {
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.PRIESTER);
				kriegerOk = testeGefaehrten(wuerfel, ziel, true);
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.KRIEGER);
				return kriegerOk;
			} else if (!isExecutedRecursively && !kriegerOk && masterController.getDungeonRoll().getAktuellerZustand()
					.aktuellerSpieler().getHeld().getHeldtyp() == Heldtyp.ARKANERSCHWERTMEISTERKAMPFMAGIER) {
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.ZAUBERER);
				kriegerOk = testeGefaehrten(wuerfel, ziel, true);
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.KRIEGER);
				return kriegerOk;
			} else {
				return isKommandant ? andere <= 1 || (andere <= 2 && kobolde == 0)
						: andere == 0 || (andere <= 1 && kobolde == 0);
			}

		case PRIESTER:
			boolean priesterOK = ziel.size() == 1 || ziel.stream().allMatch(z -> z.alsWuerfel() != null
					&& z.alsWuerfel().getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.SKELETT);
			if (!isExecutedRecursively && !priesterOK && masterController.getDungeonRoll().getAktuellerZustand()
					.aktuellerSpieler().getHeld().getHeldtyp() == Heldtyp.KREUZRITTERINPALADININ) {
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.KRIEGER);
				priesterOK = testeGefaehrten(wuerfel, ziel, true);
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.PRIESTER);
			}else if(!isExecutedRecursively && !priesterOK && masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld().getHeldtyp() == Heldtyp.OKKULTISTINTOTENBESCHWOERERIN) {
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.ZAUBERER);
				priesterOK = testeGefaehrten(wuerfel, ziel, true);
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.PRIESTER);
			}
			return priesterOK;
		case SPRUCHROLLE:
			if (masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld()
					.getHeldtyp() == Heldtyp.ADEPTINHEXE) {
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.CHAMPION);
				boolean result = !isExecutedRecursively && testeGefaehrten(wuerfel, ziel, true);
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.SPRUCHROLLE);
				return result;
			}
			return false;
		case ZAUBERER:
			boolean zaubererOk = ziel.size() == 1 || ziel.stream().allMatch(z -> z.alsWuerfel() != null
					&& z.alsWuerfel().getSchwarzeWuerfelseite() == SchwarzeWuerfelseite.SCHLEIMWESEN);
			if (!isExecutedRecursively && !zaubererOk && masterController.getDungeonRoll().getAktuellerZustand()
					.aktuellerSpieler().getHeld().getHeldtyp() == Heldtyp.ARKANERSCHWERTMEISTERKAMPFMAGIER) {
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.KRIEGER);
				zaubererOk = testeGefaehrten(wuerfel, ziel, true);
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.ZAUBERER);
			} else if (!isExecutedRecursively && !zaubererOk && masterController.getDungeonRoll().getAktuellerZustand()
					.aktuellerSpieler().getHeld().getHeldtyp() == Heldtyp.OKKULTISTINTOTENBESCHWOERERIN) {
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.PRIESTER);
				zaubererOk = testeGefaehrten(wuerfel, ziel, true);
				wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.ZAUBERER);
			}
			return zaubererOk;
		default:
			return false;

		}

	}

	/**
	 * @description Prueft, ob man mit dem gewaehlten Held, die Ziele besiegen kann
	 * @param quelle
	 *            Held der Monster besiegen soll
	 * @param ziel
	 *            Monster die besiegt werden sollen
	 * @return true, wenn Monster besiegt werden koennen
	 * @return false wenn Monster nicht besiegt werden koennen
	 */
	private boolean testeFaehigkeitHelden(Held quelle, List<Ziel> ziel) {
		// Ultimative Faehigkeit darf noch nicht benutzt sein
		if (quelle.isUltimativeEingesetzt()) {
			return false;
		}
		Wuerfel wuerfel1 = new Wuerfel(true);
		Wuerfel wuerfel2 = new Wuerfel(true);
		switch (quelle.getHeldtyp()) {

		case KREUZRITTERINPALADININ: // Kann 1x pro Abenteuer als Krieger oder Priester genutzt werden
			if (quelle.alsHeld().isLevelUp()) {
				return false;
			}
			wuerfel1.setWeisseWuerfelseite(WeisseWuerfelseite.KRIEGER);
			wuerfel2.setWeisseWuerfelseite(WeisseWuerfelseite.PRIESTER);
			return testeGefaehrten(wuerfel1, ziel) || testeGefaehrten(wuerfel2, ziel);
		case ARKANERSCHWERTMEISTERKAMPFMAGIER: // Kann 1x pro Abenteuer als Krieger oder Zauberer genutzt werden
			wuerfel1.setWeisseWuerfelseite(WeisseWuerfelseite.KRIEGER);
			wuerfel2.setWeisseWuerfelseite(WeisseWuerfelseite.ZAUBERER);
			return testeGefaehrten(wuerfel1, ziel) || testeGefaehrten(wuerfel2, ziel);
		default: // Kein passender Held
			return false;
		}
	}

	/**
	 * @description Setzt das Regelausfuehren um <br>
	 *              Dabei wird die Faehigkeit auf Genutzt gesetzt, der Schatz
	 *              entfernt oder der Gefaehrte auf den Friedhof verschoben
	 * @param quelle
	 *            Ursache fuer die Aktion
	 * @param ziel
	 *            Monster die besiegt werden sollen
	 * 
	 */
	@Override
	public void regelAusfuehren(Quelle quelle, List<Ziel> ziel) {
		// Spielzustand klonen und aktualisieren
		Spielzustand geklonterZustand = masterController.getDungeonRoll().getAktuellerZustand()
				.neuenSpielzustandErzeugen();
		masterController.getDungeonRoll().setAktuellerZustand(geklonterZustand);
		// Genutzt setzen
		if (quelle.alsHeld() != null) {
			masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld()
					.setUltimativeEingesetzt(true);
		} else if (quelle.alsSchatz() != null) {
			masterController.getSchatzController().schatzZuruecklegen(quelle.alsSchatz());
		} else {
			masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getGefaehrten()
					.remove(quelle.alsWuerfel());
			masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getFriedhof()
					.add(quelle.alsWuerfel());
		}
		// Monster besiegen
		for (Ziel z : ziel) {
			masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDungeon().remove(z.alsWuerfel());
		}
		// Phasen wechsel
		masterController.getAbenteuerController().phaseWechseln();
		System.out.println(masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getPhase().toString());

	}
}
