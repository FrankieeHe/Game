package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import control.AuswahlController;
import control.MasterController;
import javafx.util.Pair;

public class KI extends Spieler implements Serializable{
	
	private static final long serialVersionUID = 1303406316938918640L;

	public enum Strategy {WENIG_RISIKO, BESTE_VALUE, RANDOM}
	
	public static DefaultPossibleMoveGenerator defaultPossibleMoveGenerator(MasterController masterController, KI ki) {
		return new DefaultPossibleMoveGenerator(ki, masterController);
	}
	
	private Strategy strategy;
	
	public KI(Strategy strategy) {
		super();
		this.strategy = strategy;
	}

	public Action getAction(MasterController masterController, ValueFunction valueFunction, PossibleActionGenerator moveGenerator, RisikoFunction risikoFunction, Spielzustand spielzustand) {
		Set<Action> possibleMoves = moveGenerator.apply(spielzustand);
		if (possibleMoves.isEmpty())
			return null;
		List<Action> possibleMoveList = new ArrayList<>(possibleMoves);
		
		if (strategy == Strategy.WENIG_RISIKO) {
			return getLeastRisikoAction(masterController, risikoFunction,spielzustand,  possibleMoves);
		} else if (strategy == Strategy.BESTE_VALUE) {
			return mostValueAction(masterController, valueFunction, spielzustand, possibleMoves);
		} else {
			return possibleMoveList.get(new Random().nextInt(possibleMoveList.size()));
		}
		
	}

	/**
	 * Gibt die Aktion zurück, die den höchsten Wert besitzt
	 * @param valueFunction Funktion, die einem Spielzustand einen Wert zuordnet
	 * @param spielzustand der Spielzustand, von dem ausgegangen werden soll
	 * @param possibleMoves Menge der Züge, aus der die passende Aktion gesucht werden soll
	 * @return die Aktion aus possibleMoves, die den höchsten Wert besitzt
	 */
	private Action mostValueAction(MasterController masterController, ValueFunction valueFunction, Spielzustand spielzustand, Set<Action> possibleMoves) {
		Map<Action, Float> newValueByAction = new HashMap<>();
		for (Action action : possibleMoves) {
			boolean highscoreRelevant = spielzustand.isHighscoreRelevant();
			newValueByAction.put(action, valueFunction.apply(applyAction(spielzustand, action)));
			masterController.undo();
			spielzustand.setHighscoreRelevant(highscoreRelevant);
			spielzustand.setNachfolgenderSpielzustand(null);
		}
		Action mostValueAction = null;
		float mostValue = Float.MIN_VALUE;
		
		for (Entry<Action, Float> entry : newValueByAction.entrySet()) {
			if (entry.getValue() > mostValue) {
				mostValue = entry.getValue();
				mostValueAction = entry.getKey();
			}
		}
		return mostValueAction;
	}

	/**
	 * Gibt die Aktion zurück, die das geringste Risiko besitzt
	 * @param risikoFunction Eine Funktion, die jedem Spielzustand einen Risikowert zuordnet
	 * @param possibleMoves Menge der Aktionen, aus der die Aktion gesucht werden soll, die das geringste Risiko besitzt
	 * @return Aktion, die das gerinste Risiko besitzt
	 */
	private Action getLeastRisikoAction(MasterController masterController, RisikoFunction risikoFunction, Spielzustand spielzustand, Set<Action> possibleMoves) {
		Map<Action, Float> risikoByAction = new HashMap<>();
		for (Action action : possibleMoves) {
			boolean highscoreErlaubt = spielzustand.isHighscoreRelevant();
			risikoByAction.put(action, risikoFunction.apply(spielzustand, action));
			masterController.undo();
			spielzustand.setHighscoreRelevant(highscoreErlaubt);
			spielzustand.setNachfolgenderSpielzustand(null);
			
		}
		Action leastRisikoAction = null;
		float leastRisiko = Float.MAX_VALUE;
		
		for (Entry<Action, Float> entry : risikoByAction.entrySet()) {
			if (entry.getValue() < leastRisiko) {
				leastRisiko = entry.getValue();
				leastRisikoAction = entry.getKey();
			}
		}
		return leastRisikoAction;
	}
	
	/**
	 * Erzeugt einen neuen Spielzustand und führt die Aktion auf diesem aus.<br>
	 * Der neu erzeugte Spielzustand wird <b>nicht</b> direkt im Spiel umgesetzt!
	 * @param spielzustand der aktuelle Spielzustand
	 * @param action die Aktion, die auf dem aktuellen Spielzustand ausgeführt werden soll
	 * @return den Spielzustand, der durch das Anwenden der Aktion auf dem angegebenen Spielzustand entsteht
	 */
	private static Spielzustand applyAction(Spielzustand spielzustand, Action action) {
		action.apply(spielzustand);
		return spielzustand;
	}
	
	/**
	 * Berechnet den Wert eines Spielzustands.<br>
	 * Je höher der Wert, desto besser hat der Spieler bis zu dem gegebenen Spielzustand gespielt
	 * @author Pascal
	 *
	 */
	public static interface ValueFunction extends Function<Spielzustand, Float> {
		
	}
	
	/**
	 * Erzeugt eine Menge von allen Züge, die im übergebenen Spielzustand möglich sind
	 * @author Pascal
	 *
	 */
	public static interface PossibleActionGenerator extends Function<Spielzustand, Set<Action>> {
		
	}
	
	/**
	 * 
	 * @author julien
	 * Funktionales Interface um einen Spielzustand zu nehmen
	 * und auf ihn eine Action anzuwenden
	 * @param <I1>Input 1
	 * @param <I2>Input 2
	 * @param <Out>Output
	 */
	@FunctionalInterface
	private interface Risk<I1, I2, Out>{
		public Out apply(I1 i1, I2 i2);
	}
	
	/**
	 * Berechnet das Risko einer Action.<br>
	 * Je höher das Risiko, desto wahrscheinlicher ist es, dass der Spieler aus dem Dungeon fliehen muss
	 * @author Pascal
	 *
	 */
	public static interface RisikoFunction extends Risk<Spielzustand, Action, Float> {
		
	}
	
	
	public static final ValueFunction DefaultValueFunction = t -> {
		final Spieler aktuellerSpieler = t.getSpieler().get(t.getSpielerIndex());
		float score = aktuellerSpieler.getSpielerWerte().getErfahrungspunkte();
		score += aktuellerSpieler.getSpielerWerte().getSchaetze().size();
		score += 0.1 * t.getAbenteuer().getGefaehrten().size();
		score += t.getSchaetze().size();
			
		return score;
			
	};
	
	public static class DefaultPossibleMoveGenerator implements PossibleActionGenerator {
		
		private KI ki;
		private MasterController masterController;
		
		public DefaultPossibleMoveGenerator(final KI ki, final MasterController masterController) {
			this.ki = ki;
			this.masterController = masterController;
		}
		
		@Override
		public Set<Action> apply(Spielzustand t) {
			Set<Action> possibleMoves = new HashSet<>();
			System.out.println(t.aktuellerSpieler().getName()+" vs "+ki.getName());
			if (!t.aktuellerSpieler().getName().equals(ki.getName())) {
				return possibleMoves;
			}
			possibleMoves.add(new Action.BeendenAction(masterController));
			possibleMoves.add(new Action.NaechstesLevelAction(masterController));
			
			Set<Pair<Quelle, Set<Ziel>>> alleQuelleZielPairs = ki.getAlleQuelleZielPairs(masterController.getAuswahlController(), t.getAbenteuer());
			
			alleQuelleZielPairs.forEach(pair -> 
				possibleMoves.add(new Action.RegelAusfuehrenAction(masterController, pair.getKey(), new ArrayList<>(pair.getValue())))
			);
			
			return possibleMoves;
		}
	};
	
	/**
	 * Die Risikofunktion gibt Risiko zurueck, dass man fliehen muss.
	 * @author julien
	 */
	public static final RisikoFunction DefaultRisikoFunction = (s,a) -> {
		float risk = 0;
		final Spielzustand testZustand = applyAction(s,a);
		
		risk += testZustand.getAbenteuer().getDungeon().size();
		risk -= 0.5*testZustand.getAbenteuer().getGefaehrten().size();
		risk += testZustand.getAbenteuer().getDrachen().size();
		risk -= testZustand.getAbenteuer().getSpielerWerte().getSchaetze().size();
		
		List<Wuerfel> gefWuerfel = testZustand.getAbenteuer().getGefaehrten();
		boolean [] gef = new boolean[5];
		int verschGef = 0;
		for(Wuerfel w : gefWuerfel){
			if (w.getWeisseWuerfelseite().equals(WeisseWuerfelseite.CHAMPION))
				gef[0] = true;
			if (w.getWeisseWuerfelseite().equals(WeisseWuerfelseite.KRIEGER))
				gef[1] = true;
			if (w.getWeisseWuerfelseite().equals(WeisseWuerfelseite.PRIESTER))
				gef[2] = true;
			if (w.getWeisseWuerfelseite().equals(WeisseWuerfelseite.ZAUBERER))
				gef[3] = true;
			if (w.getWeisseWuerfelseite().equals(WeisseWuerfelseite.DIEB))
				gef[4] = true;
		}
		for (boolean b : gef){
			if(b)
				verschGef++;
		}
		risk -= verschGef;
		return risk;
	};
	
	private Set<Pair<Quelle, Set<Ziel>>> getAlleQuelleZielPairs(AuswahlController auswahlController, Abenteuer abenteuer) {
		Set<Pair<Quelle, Set<Ziel>>> result = new HashSet<>();
		
		Set<Ziel> alleZiele = getAlleZiele(abenteuer);
		Set<Set<Ziel>> potenzAlleZiele = AuswahlController.bauePotenzmenge(alleZiele);
		Set<Quelle> alleQuellen = new HashSet<>();
		for (Ziel ziel : alleZiele) {
			alleQuellen.add((Quelle) ziel);
		}
		alleQuellen.add(this.getHeld());
		
		for (Quelle quelle : alleQuellen) {
			for (Set<Ziel> ziele : potenzAlleZiele) {
				for (int i = 0;i<7; i++) {
					Set<Ziel> geklonteZiele = new HashSet<>();
					geklonteZiele.addAll(ziele);
					if (i<6) {
						Wuerfel wuerfel = new Wuerfel(true);
						wuerfel.setWeisseWuerfelseite(WeisseWuerfelseite.values()[i]);
						geklonteZiele.add(wuerfel);
					}
					

					if (quelle instanceof Ziel) {
						geklonteZiele.remove((Ziel)quelle);
					}
				
					if (auswahlController.pruefeAuswahlErlaubt(quelle, new ArrayList<>(geklonteZiele))) {
						result.add(new Pair<Quelle, Set<Ziel>>(quelle, geklonteZiele));
					}
				}
			}
		}
	
		return result;
	}
	
	private Set<Ziel> getAlleZiele(final Abenteuer abenteuer) {
		final Set<Ziel> potentielleZiele = new HashSet<>();
		potentielleZiele.addAll(abenteuer.getDungeon());
		potentielleZiele.addAll(abenteuer.getFriedhof());
		potentielleZiele.addAll(abenteuer.getGefaehrten());
		potentielleZiele.addAll(abenteuer.getDrachen());
		potentielleZiele.addAll(abenteuer.getSpielerWerte().getSchaetze());
		potentielleZiele.addAll(getSpielerWerte().getSchaetze());
		return potentielleZiele;
	}
	
}