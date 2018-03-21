package control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import control.regeln.AdeptinRegel;
import control.regeln.DrachenkampfRegel;
import control.regeln.DrachenkoederRegel;
import control.regeln.HalbkoboldHaeuptlingRegel;
import control.regeln.HeldAlsGefaehrte;
import control.regeln.KampfmagierRegel;
import control.regeln.KistenOeffnerRegel;
import control.regeln.MinnesaengerBardeRegel;
import control.regeln.MonsterPhasenRegel;
import control.regeln.NeuWuerfeln;
import control.regeln.OkkultistinRegel;
import control.regeln.PaladininRegel;
import control.regeln.RitterDrachentoeterRegel;
import control.regeln.SoeldnerRegel;
import control.regeln.StadtportalRegel;
import control.regeln.TrankRegel;
import control.regeln.UnsichtbarkeitsringRegel;
import control.regeln.WuerfelDrehen;
import model.Abenteuer;
import model.Quelle;
import model.Schatz;
import model.SchwarzeWuerfelseite;
import model.Spieler;
import model.WeisseWuerfelseite;
import model.Wuerfel;
import model.Ziel;

public class AuswahlController {

	/**
	 * Liste der RegelController Klassen
	 */
	private List<Class<? extends RegelController>> listeAllerRegelKlassen = Arrays.asList(AdeptinRegel.class,
			DrachenkampfRegel.class, DrachenkoederRegel.class, 
			HalbkoboldHaeuptlingRegel.class, HeldAlsGefaehrte.class, KampfmagierRegel.class, KistenOeffnerRegel.class,
			MinnesaengerBardeRegel.class, MonsterPhasenRegel.class, NeuWuerfeln.class, OkkultistinRegel.class,
			PaladininRegel.class, RitterDrachentoeterRegel.class, SoeldnerRegel.class, StadtportalRegel.class,
			TrankRegel.class, UnsichtbarkeitsringRegel.class, WuerfelDrehen.class);

	/**
	 * alle möglichen Regeln des Spiels
	 */
	private Collection<RegelController> regelController = new ArrayList<>();

	private final MasterController masterController;

	public AuswahlController(MasterController masterController) {
		this.masterController = masterController;

		for (Class<? extends RegelController> regelClass : listeAllerRegelKlassen) {
			regelController.add(createNewRegelController(regelClass));
		}

	}

	private RegelController createNewRegelController(Class<? extends RegelController> regelClass) {
		RegelController controller = null;
		try {
			controller = regelClass.newInstance();
			controller.setMasterController(masterController);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return controller;
	}

	/**
	 * @author Merle
	 * @param quelle
	 *            loest Handlung aus
	 * @param ziel
	 *            werden / wird durch die Handlung beeinflust
	 * @return false, wenn es keine Regel gibt, die diese Handlung erlaubt <br/>
	 *         true, wenn eine Regel gefunden wurde, die diese Handlung erlaubt
	 * @preconditions @see moeglicheZiele wurde aufgerufen
	 * @postconditions @see regelAusfuehren wird aufgerufen <br/>
	 *                 Es wird versucht, eine Regel zu finden, welche die
	 *                 Kombination aus Quelle und Ziel zu einer Handlung
	 *                 kombinieren kann. <br/>
	 *                 Wenn mehrere Regeln moeglich sind, wird die erst
	 *                 moegliche Aktion ausgefuehrt.
	 */

	public boolean pruefeAuswahlErlaubt(final Quelle quelle, final List<Ziel> ziel) {
		Wuerfel platzhalter = platzhalterInListe(ziel);
		if (platzhalter != null) {
			  List<Ziel> temp = ziel.stream().filter(z -> z != platzhalter).collect(Collectors.toList());
			  return pruefeAuswahlErlaubt(quelle, temp, platzhalter);
		} else {
			return pruefeAuswahlErlaubt(quelle, ziel, null);
		}
	}

	private boolean pruefeAuswahlErlaubt(Quelle quelle, List<Ziel> ziel, Wuerfel platzhalter) {
		if (quelle == null || ziel.contains(null)) {
			return false;
		}
		return regelController.stream().anyMatch(regel -> {
			if (quelle!=null && platzhalter != null && TrankRegel.class.equals(regel.getClass()) && !Schatz.TRANK.equals(quelle.alsSchatz())) {
				ziel.add(platzhalter);
				boolean returnValue = regel.pruefeAuswahlErlaubt(quelle, ziel);
				for (int i = 0; i < ziel.size(); i++) {
					if (ziel.get(i).alsWuerfel() == platzhalter) {
						ziel.remove(i);
						break;
					}
				}
				if(returnValue){
					System.out.println(regel.getClass() + " gefunden");
				}
				return returnValue;
			} else {
				if(regel.pruefeAuswahlErlaubt(quelle, ziel)){
					System.out.println(regel.getClass() + " gefunden");
				}
				return regel.pruefeAuswahlErlaubt(quelle, ziel);
			}
		});																								
	}

	/**
	 * @author Merle
	 * @return Liste moeglicher Ziele; null wenn keine vorhanden sind
	 * @postconditions @see pruefeAuswahlErlaubt(Quelle quelle, List ziel) wird
	 *                 aufgerufen
	 * @preconditions Nutzer waehlt Quelle aus <br/>
	 *                Prueft alle Objekte, die ausliegen, ob eine Kombination
	 *                moeglich waere
	 */
	public List<Ziel> moeglicheZiele(final Quelle quelle) {
		final Abenteuer abenteuer = masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer();
		final Set<Ziel> potentielleZiele = erstelleMengeDerPotenziellenZiele(quelle, abenteuer);
		final Set<Set<Ziel>> potenzmengePotenzielleZiele = bauePotenzmenge(potentielleZiele);

		final Optional<Set<Ziel>> ersteMachbareZielmenge = potenzmengePotenzielleZiele.stream()
				.filter(zielMenge -> pruefeAuswahlErlaubt(quelle, new ArrayList<>(zielMenge))).findFirst();

		return ersteMachbareZielmenge.isPresent() ? new ArrayList<>(ersteMachbareZielmenge.get()) : new ArrayList<>();
	}

	/**
	 * @author Merle
	 * @param quelle
	 *            Loest Aktion aus
	 * @param ziel
	 *            Werden von Handlung beeinflusst
	 * @preconditions @see pruefeAuswahlErlaubt lieferte true
	 * @postconditions ruft regelAusfuehren des jeweiligen Regelcontrollers auf
	 */
	public void regelAusfuehren(final Quelle quelle, final List<Ziel> ziel) {
		Wuerfel platzhalter = platzhalterInListe(ziel);
		if (platzhalter != null) {
			  List<Ziel> temp = ziel.stream().filter(z -> z != platzhalter).collect(Collectors.toList());
			  regelAusfuehren(quelle, temp, platzhalter);
		} else {
			regelAusfuehren(quelle, ziel, null);
		}
	}
	
	private Wuerfel platzhalterInListe(List<Ziel> ziele) {
		Abenteuer abenteuer = masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer();
		Optional<Quelle> optionalPlatzhalter = findePlatzhalter(ziele.stream().map(z -> (Quelle)z).collect(Collectors.toList()), abenteuer.getGefaehrten(), abenteuer.getFriedhof(), abenteuer.getDrachen(), abenteuer.getDungeon());
		return optionalPlatzhalter.isPresent() ? optionalPlatzhalter.get().alsWuerfel() : null;
	}

	private void regelAusfuehren(Quelle quelle, List<Ziel> ziel, Wuerfel platzhalter) {
		final Optional<RegelController> controller = regelController.stream()
				.filter(regel -> {
					if (platzhalter != null && TrankRegel.class.equals(regel.getClass()) && !Schatz.TRANK.equals(quelle.alsSchatz())) {
						ziel.add(platzhalter);
						boolean returnValue = regel.pruefeAuswahlErlaubt(quelle, ziel);
						for (int i = 0; i < ziel.size(); i++) {
							if (ziel.get(0).alsWuerfel() == platzhalter) {
								ziel.remove(i);
								break;
							}
						}
						return returnValue;
					} else {
						return regel.pruefeAuswahlErlaubt(quelle, ziel);
					}
				}).findFirst();

		if (controller.isPresent()) {
			System.out.println(controller.get().getClass().toString());
			if(platzhalter != null && TrankRegel.class.equals(controller.get().getClass()) && !Schatz.TRANK.equals(quelle.alsSchatz())){	
				ziel.add(platzhalter);
				controller.get().regelAusfuehren(quelle, ziel);
				// da die Liste Ziel nicht mehr gebraucht wird, muss der
				// platzhalter auch nicht mehr entfernt werden
			} else {
				controller.get().regelAusfuehren(quelle, ziel);
			}
			
			//prüfen ob gelevelt werden muss
			if(masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getSpielerWerte().getErfahrungspunkte() + masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getSpielerWerte().getErfahrungspunkte() >= 5){
				masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld().setLevelUp(true);
			}
		}
	}

	private Set<Ziel> erstelleMengeDerPotenziellenZiele(final Quelle quelle, final Abenteuer abenteuer) {
		final Set<Ziel> potentielleZiele = getAlleZiele(abenteuer);

		if (quelle.alsSchatz() != null) {
			potentielleZiele.remove(quelle.alsSchatz());
		} else if (quelle.alsWuerfel() != null) {
			potentielleZiele.remove(quelle.alsWuerfel());
		}

		return potentielleZiele;
	}

	private Set<Ziel> getAlleZiele(final Abenteuer abenteuer) {
		final Set<Ziel> potentielleZiele = new HashSet<>();
		potentielleZiele.addAll(abenteuer.getDungeon());
		potentielleZiele.addAll(abenteuer.getFriedhof());
		potentielleZiele.addAll(abenteuer.getGefaehrten());
		potentielleZiele.addAll(abenteuer.getDrachen());
		potentielleZiele.addAll(abenteuer.getSpielerWerte().getSchaetze());
		potentielleZiele.addAll(getSchaetze());
		return potentielleZiele;
	}

	public static Set<Set<Ziel>> powerset(Set<Ziel> set) {
		Set<Set<Ziel>> ps = new HashSet<>();
		ps.add(new HashSet<>());
		
		for (Ziel ziel : set) {
			Set<Set<Ziel>> newPs = new HashSet<>();
			for (Set<Ziel> subset : ps) {
				
				int monsterartenAnzahl = subset.stream().filter(z -> z!=null && z.alsWuerfel()!=null && !z.alsWuerfel().isWeiss() && z.alsWuerfel().isMonster()).map(z -> z.alsWuerfel().getSchwarzeWuerfelseite()).collect(Collectors.toSet()).size();
				int gefaehrtenAnzahl = subset.stream().filter(z -> z!=null && z.alsWuerfel()!=null && z.alsWuerfel().isWeiss()).map(z -> z.alsWuerfel().getWeisseWuerfelseite()).collect(Collectors.toSet()).size();
				if (monsterartenAnzahl>1 || gefaehrtenAnzahl>1 && monsterartenAnzahl==1) {
					continue;
				}
				
				
				
				
				if (subset.size() > 5) {
					continue;
				}
				newPs.add(subset);
				
				Set<Ziel> newSubset = new HashSet<>(subset);
				newSubset.add(ziel);
				newPs.add(newSubset);
			}
			
			ps = newPs;
		}
		return ps;
	}
	
	public static Set<Set<Ziel>> bauePotenzmenge(final Set<Ziel> potentielleZiele) {
		return powerset(potentielleZiele);
	}
	
	public static Set<Set<Ziel>> bauePotenzmenge2(final Set<Ziel> potentielleZiele) {
		final Set<Set<Ziel>> potenzmenge = new HashSet<>();
		potenzmenge.add(new HashSet<>());
		if (!potentielleZiele.isEmpty()) {
			for (final Ziel ziel : potentielleZiele) {
				if (potenzmenge.size()>1) {
					return potenzmenge;
				}
				final Set<Ziel> eineStufeWeniger = new HashSet<>(potentielleZiele);
				eineStufeWeniger.remove(ziel);
				final Set<Set<Ziel>> potenzmengeEineStufeWeniger = bauePotenzmenge(eineStufeWeniger);
				for (Set<Ziel> potenzmengenElement : potenzmengeEineStufeWeniger) {
					final Set<Ziel> mitAltemElement = new HashSet<>(potenzmengenElement);
					mitAltemElement.add(ziel);
					potenzmenge.add(potenzmengenElement);
					potenzmenge.add(mitAltemElement);
				}
			}
		}
		return potenzmenge;
	}

	private List<Schatz> getSchaetze() {
		final List<Spieler> alleSpieler = masterController.getDungeonRoll().getAktuellerZustand().getSpieler();
		final int aktuellerSpielerIdx = masterController.getDungeonRoll().getAktuellerZustand().getSpielerIndex();
		final Spieler aktuellerSpieler = alleSpieler.get(aktuellerSpielerIdx);
		return aktuellerSpieler.getSpielerWerte().getSchaetze();
	}

	/**
	 * Die Methode trennt die übergebene Auswahlliste in Quelle und Ziele und
	 * Platzhalter auf ruft implizit die Methoden pruefeAuswahlErlaubt und je
	 * nach Ergebnis dieser auch regelAusfuehren auf.
	 * 
	 * @param auswahl
	 *            Alle in der GUI ausgewählten Objekte mitsamt des
	 *            Friedhof-Platzhalters
	 * @return <code>true</code> falls eine Regel ausgeführt wurde,
	 *         <code>false</code> wenn nicht
	 */
	public boolean auswahlPruefenUndAusfuehren(List<Quelle> auswahl) {
		Quelle quelle = null;
		List<Ziel> ziele = new ArrayList<Ziel>();
		Wuerfel platzhalterWuerfel = null;

		List<Wuerfel> gefaehrten = masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer()
				.getGefaehrten();
		List<Wuerfel> dungeon = masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer()
				.getDungeon();
		List<Wuerfel> friedhof = masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getFriedhof();
		List<Wuerfel> drachen = masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDrachen();

		Optional<Quelle> platzhalter = findePlatzhalter(auswahl, gefaehrten, friedhof, drachen, dungeon);
		if (platzhalter.isPresent()) {
			platzhalterWuerfel = platzhalter.get().alsWuerfel();
			auswahl.removeIf(z -> z==platzhalter.get());
		}

		// Quelle Held
		Optional<Quelle> quellKandidat = auswahl.stream().filter(auswahlObjekt -> auswahlObjekt.alsHeld() != null)
				.findFirst();
		if (quellKandidat.isPresent()) {
			quelle = quellKandidat.get();
			final Quelle vergleichQuelle = quelle;
			if (auswahl.stream()
					.allMatch(auswahlObjekt -> auswahlObjekt.alsHeld() == null || auswahlObjekt == vergleichQuelle)) {
				auswahl.stream().forEach(auswahlObjekt -> {
					if (auswahlObjekt != vergleichQuelle && auswahlObjekt.alsWuerfel() == null) {
						ziele.add(auswahlObjekt.alsSchatz());
					} else if (auswahlObjekt != vergleichQuelle) {
						ziele.add(auswahlObjekt.alsWuerfel());
					}
				});
			} else {
				// mehr als ein Held
				return false;
			}
		}
		// Quelle Drachenwürfel
		else {
			quellKandidat = auswahl.stream().filter(auswahlObjekt -> auswahlObjekt.alsWuerfel() != null
					&& SchwarzeWuerfelseite.DRACHENKOPF.equals(auswahlObjekt.alsWuerfel().getSchwarzeWuerfelseite()))
					.findFirst();
			if (quellKandidat.isPresent()) {
				quelle = quellKandidat.get();
				final Quelle vergleichQuelle = quelle;
				auswahl.stream().forEach(auswahlObjekt -> {
					if (auswahlObjekt != vergleichQuelle && auswahlObjekt.alsWuerfel() == null) {
						ziele.add(auswahlObjekt.alsSchatz());
					} else if (auswahlObjekt != vergleichQuelle) {
						ziele.add(auswahlObjekt.alsWuerfel());
					}
				});
			}
			// Quelle Schatz
			else {
				quellKandidat = auswahl.stream().filter(auswahlObjekt -> auswahlObjekt.alsSchatz() != null).findFirst();
				if (quellKandidat.isPresent()) {
					quelle = quellKandidat.get();
					final Quelle vergleichQuelle = quelle;
					if (auswahl.stream().allMatch(
							auswahlObjekt -> auswahlObjekt.alsWuerfel() != null || auswahlObjekt == vergleichQuelle)) {
						auswahl.stream().forEach(auswahlObjekt -> {
							if (auswahlObjekt.alsWuerfel() != null) {
								ziele.add(auswahlObjekt.alsWuerfel());
							}
						});
					} else {
						// mehr als ein Schatz ist hier nicht erlaubt
						return false;
					}
				}
				//Quelle Spruchrolle
				else {
					quellKandidat = auswahl.stream().filter(auswahlObjekt -> gefaehrten.stream().anyMatch(gefaehrte -> gefaehrte == auswahlObjekt.alsWuerfel()) && WeisseWuerfelseite.SPRUCHROLLE.equals(auswahlObjekt.alsWuerfel().getWeisseWuerfelseite())).findFirst();
					if(quellKandidat.isPresent()){
						quelle = quellKandidat.get();
						final Quelle vergleichQuelle = quelle;
						auswahl.stream().forEach(auswahlObjekt -> {
							if (auswahlObjekt != vergleichQuelle) {
								ziele.add(auswahlObjekt.alsWuerfel());
							}
						});
					}
					// Quelle sonstiger Gefährtenwürfel
					else {
						quellKandidat = auswahl.stream().filter(auswahlObjekt -> gefaehrten.stream()
								.anyMatch(gefaehrte -> gefaehrte == auswahlObjekt.alsWuerfel())).findFirst();
						if (quellKandidat.isPresent()) {
							quelle = quellKandidat.get();
							final Quelle vergleichQuelle = quelle;
							auswahl.stream().forEach(auswahlObjekt -> {
								if (auswahlObjekt != vergleichQuelle) {
									ziele.add(auswahlObjekt.alsWuerfel());
								}
							});
						} else {
							// keine gültige Quelle
							return false;
						}
					}
					
				}
				
			}
		}
		if (pruefeAuswahlErlaubt(quelle, ziele, platzhalterWuerfel)) {
			regelAusfuehren(quelle, ziele, platzhalterWuerfel);

			return true;
		} else {
			return false;
		}
	}

	private Optional<Quelle> findePlatzhalter(List<Quelle> auswahl, List<Wuerfel> gefaehrten, List<Wuerfel> friedhof,
			List<Wuerfel> drachen, List<Wuerfel> dungeon) {
		Optional<Quelle> platzhalter = auswahl.stream()
				.filter(auswahlObjekt -> auswahlObjekt != null && auswahlObjekt.alsWuerfel() != null
						&& gefaehrten.stream().noneMatch(gefaehrte -> gefaehrte == auswahlObjekt.alsWuerfel())
						&& friedhof.stream().noneMatch(gefaehrte -> gefaehrte == auswahlObjekt.alsWuerfel())
						&& dungeon.stream().noneMatch(gefaehrte -> gefaehrte == auswahlObjekt.alsWuerfel())
						&& drachen.stream().noneMatch(gefaehrte -> gefaehrte == auswahlObjekt.alsWuerfel()))
				.findFirst();
		return platzhalter;
	}

}
