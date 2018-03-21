package control.regeln;

import java.util.List;

import control.MasterController;
import control.RegelController;
import model.Held;
import model.Heldtyp;
import model.Phase;
import model.Quelle;
import model.Schatz;
import model.SchwarzeWuerfelseite;
import model.Spielzustand;
import model.Wuerfel;
import model.Ziel;

public class KistenOeffnerRegel extends RegelController {

	@Override
	public void setMasterController(MasterController masterController) {
		// TODO Auto-generated method stub
		super.setMasterController(masterController);
	}

	@Override
	public boolean pruefeAuswahlErlaubt(Quelle quelle, List<Ziel> ziel) {
		if (quelle == null || ziel == null || ziel.isEmpty()) {
			return false;
		}
		
		// Pruefen, ob in der Zielliste nur Truhen-Wuerfel enthalten sind
		for(Ziel aktuellesZiel : ziel){
			if(aktuellesZiel.alsWuerfel() == null){
				return false;
			}
			if(aktuellesZiel.alsWuerfel().getSchwarzeWuerfelseite() != SchwarzeWuerfelseite.TRUHE){
				System.out.println("Kisten oeffner Regel Ziel nicht Truhe");
				return false;
			}
		}
		System.out.println("KistenOeffner Ziele ok");
		Held held = masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld();
		Phase phase = masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getPhase();
		
		int moeglicheKistenAnzahl = 0;
		
		// Die SF von Halbkobold und Haeuptling ermoeglicht es, immer zu pluendern,
		// Andere Helden duerfen dies nur in der Pluenderphase
		if(held.getHeldtyp() != Heldtyp.HALBKOBOLDHAEUPTLING && phase != Phase.PLUENDERPHASE){
			System.out.println("Problem");
			return false;
		}
		
		// Quelle ist Wuerfel
		if(quelle.alsWuerfel() != null){
			if(!quelle.alsWuerfel().isWeiss()){ // Quelle muss weisser Wuerfel (Gefaehrte) sein
				return false;
			}
			Wuerfel wuerfel = quelle.alsWuerfel();
			switch(wuerfel.getWeisseWuerfelseite()){
			case KRIEGER: 
				moeglicheKistenAnzahl = 1;
				break;
			case PRIESTER:
				moeglicheKistenAnzahl = 1;
				break;
			case ZAUBERER:
				// Minnesaenger und Barden SF ist es Zauberer wie Diebe zu verwenden
				if(held.getHeldtyp() == Heldtyp.MINNESAENGERBARDE){
					moeglicheKistenAnzahl = Integer.MAX_VALUE;
				}
				else{
					moeglicheKistenAnzahl = 1;
				}				
				break;
			case DIEB:
				moeglicheKistenAnzahl = Integer.MAX_VALUE;
				break;
			case CHAMPION:
				moeglicheKistenAnzahl = 1;
				break;
			case SPRUCHROLLE:
				// Adeptin darf Spruchrollen wie beliebigen Gefaehrten einsetzen, 
				// Waehle also Gefaehrte = Dieb -> beliebig viele besiegbar
				if(held.getHeldtyp() == Heldtyp.ADEPTINHEXE){
					System.out.println("Kisten oeffner Regel");
					moeglicheKistenAnzahl = Integer.MAX_VALUE;
					break;
				}
			default:
				return false;
			}
		}else if(quelle.alsSchatz() != null){//Quelle ist Schatz
			switch(quelle.alsSchatz()){
			case KOEPFERKLINGE:
				moeglicheKistenAnzahl = 1;
				break;
			case TALISMAN: 
				moeglicheKistenAnzahl = 1;
				break;
			case ZEPTERDERMACHT:
				if(held.getHeldtyp() == Heldtyp.MINNESAENGERBARDE){
					moeglicheKistenAnzahl = Integer.MAX_VALUE;
				}
				else{
					moeglicheKistenAnzahl = 1;
				}
				
				break;
			case DIEBESWERKZEUG: 
				moeglicheKistenAnzahl = Integer.MAX_VALUE;
				break;
			case SPRUCHROLLE:
				// Adeptin darf Spruchrollen wie beliebigen Gefaehrten einsetzen, 
				// Waehle also Gefaehrte = Dieb -> beliebig viele besiegbar
				if(held.getHeldtyp() == Heldtyp.ADEPTINHEXE){
					moeglicheKistenAnzahl = Integer.MAX_VALUE;
					break;
				}
			default: return false;
			}
		}else{ // Quelle ist Held
			switch(held.getHeldtyp()){
			case KREUZRITTERINPALADININ:
				// Wird als Priester oder Krieger benutzt
				if(!held.isLevelUp()){
					if(!held.isUltimativeEingesetzt()){
						moeglicheKistenAnzahl = 1;
					}
				}
				break;
			case ARKANERSCHWERTMEISTERKAMPFMAGIER:
				// Wird als Zauberer oder Krieger benutzt
				if(!held.isLevelUp()){
					if(!held.isUltimativeEingesetzt()){
						moeglicheKistenAnzahl = 1;
					}
				}
				break;
			default:
				return false;
			}
		}
		System.out.println("Kisten oeffner Regel");
		return moeglicheKistenAnzahl >= ziel.size();
	}

	@Override
	public void regelAusfuehren(Quelle quelle, List<Ziel> ziel) {
		// Spielzustand aktualisieren und klonen
		Spielzustand geklonterZustand = masterController.getDungeonRoll().getAktuellerZustand().neuenSpielzustandErzeugen();
		masterController.getDungeonRoll().setAktuellerZustand(geklonterZustand);
		
		// Quelle war Wuerfel -> Gefaehrten auf den Friedhof verschieben
		if(quelle.alsWuerfel() != null){
			System.out.println("Gefaehrten verschieben");
			geklonterZustand.getAbenteuer().getFriedhof().add(quelle.alsWuerfel());
			geklonterZustand.getAbenteuer().getGefaehrten().remove(quelle.alsWuerfel());
		}
		else if(quelle.alsSchatz() != null){ // Quelle war Schatz -> Schatz aus Spielerwerten entfernen
			masterController.getSchatzController().schatzZuruecklegen(quelle.alsSchatz());
		}
		else{// Quelle war Held -> UF auf genutzt setzen
			geklonterZustand.aktuellerSpieler().getHeld().setUltimativeEingesetzt(true);
		}
		
		// Kisten oeffnen
		for(Ziel aktuellesZiel : ziel){
			// Kiste "verbrauchen"
			geklonterZustand.getAbenteuer().getDungeon().remove(aktuellesZiel.alsWuerfel());
			// Schatz ziehen
			Schatz schatz = masterController.getSchatzController().schatzZiehen();
			System.out.println("schatz ziehen");
			// Wenn keine Schaetze mehr vorhanden sind, bekommt der Spieler einen EP gutgeschrieben
			if(schatz == null){
				int erfahrungspunkte = geklonterZustand.getAbenteuer().getSpielerWerte().getErfahrungspunkte();
				geklonterZustand.getAbenteuer().getSpielerWerte().setErfahrungspunkte(erfahrungspunkte + 1);
			}else{
				List<Schatz> schaetze = geklonterZustand.aktuellerSpieler().getSpielerWerte().getSchaetze();
				schaetze.add(schatz);
			}
		}
		
		// Phase wechseln
		if(geklonterZustand.getAbenteuer().getDungeon().isEmpty()){
			masterController.getAbenteuerController().phaseWechseln();
		}
		
	}

}
