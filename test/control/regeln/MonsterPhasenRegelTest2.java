package control.regeln;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import control.MasterController;
import model.Abenteuer;
import model.DungeonRoll;
import model.Held;
import model.Heldtyp;
import model.Phase;
import model.Quelle;
import model.Schatz;
import model.SchwarzeWuerfelseite;
import model.Spieler;
import model.SpielerWerte;
import model.Spielzustand;
import model.WeisseWuerfelseite;
import model.Wuerfel;
import model.Ziel;

public class MonsterPhasenRegelTest2 {

	private MasterController masterController;
	private MonsterPhasenRegel monsterPhasenRegel;
	private Abenteuer abenteuer;
	private DungeonRoll dungeonRoll;
	private Spielzustand spielzustand;
	private List<Spieler> spieler;
	private Quelle dieb, champion, spruchrolle, zauberer, priester, krieger;
	private Quelle adeptin, arkaner, haeuptling, paladinin, barde, okkultistin, ritter, soeldner;
	private Quelle spruchrolle_schatz, drachenschuppen, diebeswerkzeug, drachenkoeder, koepferklinge, talisman,
			stadtportal, zepterdermacht, trank_schatz, unsichtbarkeitsring;
	private List<Ziel> monster;
	private Ziel kobold, kobold1, kobold2, schleimwesen, schleimwesen1, schleimwesen2, trank, drachenkopf, truhe,
			skelett, skelett1, skelett2;

	@Before
	public void SetUp() {
		System.out.println("Erstellen der Spielbasis...");
		// Spiel
		masterController = new MasterController();
		monsterPhasenRegel = new MonsterPhasenRegel();
		monsterPhasenRegel.setMasterController(masterController);
		dungeonRoll = masterController.getDungeonRoll();
		spielzustand = new Spielzustand();
		dungeonRoll.setAktuellerZustand(spielzustand);
		masterController.getDungeonRoll().getAktuellerZustand().setMaxRunde(5);
		masterController.getDungeonRoll().getAktuellerZustand().setRunde(0);
		abenteuer = new Abenteuer();
		abenteuer.setLevel(1);
		masterController.getDungeonRoll().getAktuellerZustand().setAbenteuer(abenteuer);
		spieler = new ArrayList<Spieler>();
		Spieler einspieler = new Spieler();
		spieler.add(einspieler);
		masterController.getDungeonRoll().getAktuellerZustand().setSpieler(spieler);

		// Gefaehrten
		dieb = new Wuerfel(true);
		dieb.alsWuerfel().setWeisseWuerfelseite(WeisseWuerfelseite.DIEB);

		champion = new Wuerfel(true);
		champion.alsWuerfel().setWeisseWuerfelseite(WeisseWuerfelseite.CHAMPION);

		krieger = new Wuerfel(true);
		krieger.alsWuerfel().setWeisseWuerfelseite(WeisseWuerfelseite.KRIEGER);

		priester = new Wuerfel(true);
		priester.alsWuerfel().setWeisseWuerfelseite(WeisseWuerfelseite.PRIESTER);

		spruchrolle = new Wuerfel(true);
		spruchrolle.alsWuerfel().setWeisseWuerfelseite(WeisseWuerfelseite.SPRUCHROLLE);

		zauberer = new Wuerfel(true);
		zauberer.alsWuerfel().setWeisseWuerfelseite(WeisseWuerfelseite.ZAUBERER);

		spruchrolle = new Wuerfel(false);
		spruchrolle.alsWuerfel().setWeisseWuerfelseite(WeisseWuerfelseite.SPRUCHROLLE);

		// Helden
		adeptin = new Held();
		adeptin.alsHeld().setLevelUp(false);
		adeptin.alsHeld().setHeldtyp(Heldtyp.ADEPTINHEXE);

		arkaner = new Held();
		arkaner.alsHeld().setLevelUp(false);
		arkaner.alsHeld().setHeldtyp(Heldtyp.ARKANERSCHWERTMEISTERKAMPFMAGIER);

		haeuptling = new Held();
		haeuptling.alsHeld().setLevelUp(false);
		haeuptling.alsHeld().setHeldtyp(Heldtyp.HALBKOBOLDHAEUPTLING);

		paladinin = new Held();
		paladinin.alsHeld().setLevelUp(false);
		paladinin.alsHeld().setHeldtyp(Heldtyp.KREUZRITTERINPALADININ);

		barde = new Held();
		barde.alsHeld().setLevelUp(true);
		barde.alsHeld().setHeldtyp(Heldtyp.MINNESAENGERBARDE);

		okkultistin = new Held();
		okkultistin.alsHeld().setLevelUp(false);
		okkultistin.alsHeld().setHeldtyp(Heldtyp.OKKULTISTINTOTENBESCHWOERERIN);

		ritter = new Held();
		ritter.alsHeld().setLevelUp(false);
		ritter.alsHeld().setHeldtyp(Heldtyp.RITTERDRACHENTOETER);

		soeldner = new Held();
		soeldner.alsHeld().setLevelUp(false);
		soeldner.alsHeld().setHeldtyp(Heldtyp.SOELDNERKOMMANDANT);

		// Schaetze
		spruchrolle_schatz = Schatz.SPRUCHROLLE;
		drachenschuppen = Schatz.DRACHENSCHUPPEN;
		diebeswerkzeug = Schatz.DIEBESWERKZEUG;
		drachenkoeder = Schatz.DRACHENKOEDER;
		koepferklinge = Schatz.KOEPFERKLINGE;
		talisman = Schatz.TALISMAN;
		stadtportal = Schatz.STADTPORTAL;
		zepterdermacht = Schatz.ZEPTERDERMACHT;
		trank_schatz = Schatz.TRANK;
		unsichtbarkeitsring = Schatz.UNSICHTBARKEITSRING;

		// bunte Monsterliste
		monster = new ArrayList<Ziel>();

		kobold = new Wuerfel(false);
		kobold.alsWuerfel().setSchwarzeWuerfelseite(SchwarzeWuerfelseite.KOBOLD);
		monster.add(kobold);
		kobold1 = new Wuerfel(false);
		kobold1.alsWuerfel().setSchwarzeWuerfelseite(SchwarzeWuerfelseite.KOBOLD);
		kobold2 = new Wuerfel(false);
		kobold2.alsWuerfel().setSchwarzeWuerfelseite(SchwarzeWuerfelseite.KOBOLD);

		schleimwesen = new Wuerfel(false);
		schleimwesen.alsWuerfel().setSchwarzeWuerfelseite(SchwarzeWuerfelseite.SCHLEIMWESEN);
		monster.add(schleimwesen);
		schleimwesen1 = new Wuerfel(false);
		schleimwesen1.alsWuerfel().setSchwarzeWuerfelseite(SchwarzeWuerfelseite.SCHLEIMWESEN);
		schleimwesen2 = new Wuerfel(false);
		schleimwesen2.alsWuerfel().setSchwarzeWuerfelseite(SchwarzeWuerfelseite.SCHLEIMWESEN);

		skelett = new Wuerfel(false);
		skelett.alsWuerfel().setSchwarzeWuerfelseite(SchwarzeWuerfelseite.SKELETT);
		monster.add(skelett);
		skelett1 = new Wuerfel(false);
		skelett1.alsWuerfel().setSchwarzeWuerfelseite(SchwarzeWuerfelseite.SKELETT);
		skelett2 = new Wuerfel(false);
		skelett2.alsWuerfel().setSchwarzeWuerfelseite(SchwarzeWuerfelseite.SKELETT);
		trank = new Wuerfel(false);
		trank.alsWuerfel().setSchwarzeWuerfelseite(SchwarzeWuerfelseite.TRANK);
		monster.add(trank);

		drachenkopf = new Wuerfel(false);
		drachenkopf.alsWuerfel().setSchwarzeWuerfelseite(SchwarzeWuerfelseite.DRACHENKOPF);
		monster.add(drachenkopf);

		truhe = new Wuerfel(false);
		truhe.alsWuerfel().setSchwarzeWuerfelseite(SchwarzeWuerfelseite.TRUHE);
		monster.add(truhe);

		System.out.println("fertig");
	}

	@Test
	public void testTestpruefeAuswahlErlaubtWuerfel() {
		abenteuer.setPhase(Phase.KAMPFPHASE);

		// Fall 1. Gefaehrten mit dem Held SOELDNER/KOMMANDANT
		// Dieser beeinflusst das Default-Kaempfen nicht
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().setHeld(soeldner.alsHeld());
		// Fall 1.1
		// Gefaehrte ist DIEB
		// Fall 1.1.1
		// Zu besigende Monster sind bunt, laenge ist groesser als 1
		assertFalse(monsterPhasenRegel.pruefeAuswahlErlaubt(dieb, monster));
		// Fall 1.1.2
		// Zu besiegendes Monster ist DRACHENKOPF
		monster.clear();
		monster.add(drachenkopf);
		assertFalse(monsterPhasenRegel.pruefeAuswahlErlaubt(dieb, monster));
		// Fall 1.1.3
		// Zu besiegendes Monster ist TRANK
		monster.clear();
		monster.add(trank);
		assertFalse(monsterPhasenRegel.pruefeAuswahlErlaubt(dieb, monster));
		// Fall 1.1.4
		// Zu besiegendes Monster ist TRUHE
		monster.clear();
		monster.add(truhe);
		assertFalse(monsterPhasenRegel.pruefeAuswahlErlaubt(dieb, monster));
		// Fall 1.1.5
		// Zu besiegendes Monster ist ein SCHLEIMWESEN
		monster.clear();
		monster.add(schleimwesen);
		assertTrue(monsterPhasenRegel.pruefeAuswahlErlaubt(dieb, monster));
		// Fall 1.1.6
		// Zu besiegendes Monster ist ein SKELETT
		monster.clear();
		monster.add(skelett);
		assertTrue(monsterPhasenRegel.pruefeAuswahlErlaubt(dieb, monster));
		// Fall 1.1.7
		// Zu besiegendes Monster ist ein KOBOLD
		monster.clear();
		monster.add(kobold);
		assertTrue(monsterPhasenRegel.pruefeAuswahlErlaubt(dieb, monster));

		monster.clear();
		monster.add(drachenkopf);
		monster.add(schleimwesen);
		monster.add(skelett);
		monster.add(truhe);
		monster.add(kobold);
		monster.add(trank);

		// Fall 1.2
		// Gefaehrte ist Champion
		// Fall 1.2.1
		// Zu besiegende Monster sind bunt (mit Truhe, Drache, Trank)
		assertFalse("1.2.1", monsterPhasenRegel.pruefeAuswahlErlaubt(dieb, monster));
		// Fall 1.2.2
		// Zu besiegendes Monster ist DRACHENKOPF
		monster.clear();
		monster.add(drachenkopf);
		assertFalse("1.2.2", monsterPhasenRegel.pruefeAuswahlErlaubt(champion, monster));
		// Fall 1.2.3
		// Zu besiegendes Monster ist TRANK
		monster.clear();
		monster.add(trank);
		assertFalse("1.2.3", monsterPhasenRegel.pruefeAuswahlErlaubt(champion, monster));
		// Fall 1.2.4
		// Zu besiegendes Monster ist TRUHE
		monster.clear();
		monster.add(truhe);
		assertFalse("1.2.4", monsterPhasenRegel.pruefeAuswahlErlaubt(champion, monster));
		// Fall 1.2.5
		// Zu besiegendes Monster sind SCHLEIMWESEN
		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);
		monster.add(schleimwesen2);
		assertTrue("1.2.5", monsterPhasenRegel.pruefeAuswahlErlaubt(champion, monster));
		// Fall 1.2.6
		// Zu besiegendes Monster sind SKELETTE
		monster.clear();
		monster.add(skelett);
		Ziel skelett1 = new Wuerfel(false);
		skelett1.alsWuerfel().setSchwarzeWuerfelseite(SchwarzeWuerfelseite.SKELETT);
		monster.add(skelett1);
		assertTrue("1.2.6", monsterPhasenRegel.pruefeAuswahlErlaubt(champion, monster));
		// Fall 1.2.7
		// Zu besiegendes Monster sind KOBOLDE
		monster.clear();
		monster.add(kobold);
		Ziel kobold1 = new Wuerfel(false);
		kobold1.alsWuerfel().setSchwarzeWuerfelseite(SchwarzeWuerfelseite.KOBOLD);
		Ziel kobold2 = new Wuerfel(false);
		kobold2.alsWuerfel().setSchwarzeWuerfelseite(SchwarzeWuerfelseite.KOBOLD);
		monster.add(kobold1);
		monster.add(kobold2);
		assertTrue("1.2.7", monsterPhasenRegel.pruefeAuswahlErlaubt(champion, monster));
		// Fall 1.2.8
		// Zu besiegende Monster gehoeren 2 verschiedenen Rassen an
		monster.clear();
		monster.add(kobold);
		monster.add(schleimwesen);
		assertFalse("1.2.8", monsterPhasenRegel.pruefeAuswahlErlaubt(dieb, monster));

		monster.clear();
		monster.add(drachenkopf);
		monster.add(schleimwesen);
		monster.add(skelett);
		monster.add(truhe);
		monster.add(kobold);
		monster.add(trank);

		// Fall 1.3
		// Gefaehrte ist Priester
		// Fall 1.3.1
		// Zu besiegende Monster sind bunt (mit Truhe, Drache, Trank)
		assertFalse("1.3.1", monsterPhasenRegel.pruefeAuswahlErlaubt(priester, monster));
		// Fall 1.3.2
		// Zu besiegendes Monster ist DRACHENKOPF
		monster.clear();
		monster.add(drachenkopf);
		assertFalse("1.3.2", monsterPhasenRegel.pruefeAuswahlErlaubt(priester, monster));
		// Fall 1.3.3
		// Zu besiegendes Monster ist TRANK
		monster.clear();
		monster.add(trank);
		assertFalse("1.3.3", monsterPhasenRegel.pruefeAuswahlErlaubt(priester, monster));
		// Fall 1.3.4
		// Zu besiegendes Monster ist TRUHE
		monster.clear();
		monster.add(truhe);
		assertFalse("1.3.4", monsterPhasenRegel.pruefeAuswahlErlaubt(priester, monster));
		// Fall 1.3.5
		// Zu besiegendes Monster sind SCHLEIMWESEN
		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);
		monster.add(schleimwesen2);
		assertFalse("1.3.5", monsterPhasenRegel.pruefeAuswahlErlaubt(priester, monster));
		// Fall 1.3.6
		// Ein Schleimmonster zu besiegen
		monster.clear();
		monster.add(schleimwesen);
		assertTrue("1.3.6", monsterPhasenRegel.pruefeAuswahlErlaubt(priester, monster));
		// Fall 1.3.7
		// Zu besiegendes Monster sind SKELETTE
		monster.clear();
		monster.add(skelett);
		monster.add(skelett1);
		monster.add(skelett2);
		assertTrue("1.3.7", monsterPhasenRegel.pruefeAuswahlErlaubt(priester, monster));
		// Fall 1.3.8
		// Zu besiegendes Monster ist ein Kobold
		monster.clear();
		monster.add(kobold);
		assertTrue("1.3.8", monsterPhasenRegel.pruefeAuswahlErlaubt(priester, monster));
		// Fall 1.3.9
		// Zu besiegendes Monster sind KOBOLDE
		monster.clear();
		monster.add(kobold);
		monster.add(kobold1);
		monster.add(kobold2);
		assertFalse("1.3.9", monsterPhasenRegel.pruefeAuswahlErlaubt(priester, monster));
		// Fall 1.3.10
		// Zu besiegende Monster gehoeren 2 verschiedenen Rassen an
		monster.clear();
		monster.add(kobold);
		monster.add(schleimwesen);
		assertFalse("1.3.10", monsterPhasenRegel.pruefeAuswahlErlaubt(priester, monster));

		monster.clear();
		monster.add(drachenkopf);
		monster.add(schleimwesen);
		monster.add(skelett);
		monster.add(truhe);
		monster.add(kobold);
		monster.add(trank);

		// Fall 1.4
		// Gefaehrte ist KRIEGER
		// Fall 1.4.1
		// Zu besiegende Monster sind bunt (mit Truhe, Drache, Trank)
		assertFalse("1.4.1", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger, monster));
		// Fall 1.4.2
		// Zu besiegendes Monster ist DRACHENKOPF
		monster.clear();
		monster.add(drachenkopf);
		assertFalse("1.4.2", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger, monster));
		// Fall 1.4.3
		// Zu besiegendes Monster ist TRANK
		monster.clear();
		monster.add(trank);
		assertFalse("1.4.3", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger, monster));
		// Fall 1.4.4
		// Zu besiegendes Monster ist TRUHE
		monster.clear();
		monster.add(truhe);
		assertFalse("1.4.4", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger, monster));
		// Fall 1.4.5
		// Zu besiegendes Monster sind SCHLEIMWESEN
		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);
		monster.add(schleimwesen2);
		assertFalse("1.4.5", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger, monster));
		// Fall 1.4.6
		// Ein Schleimmonster zu besiegen
		monster.clear();
		monster.add(schleimwesen);
		assertTrue("1.4.6", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger, monster));
		// Fall 1.4.7
		// Zu besiegendes Monster sind SKELETTE
		monster.clear();
		monster.add(skelett);
		monster.add(skelett1);
		monster.add(skelett2);
		assertFalse("1.4.7", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger, monster));
		// Fall 1.4.8
		// Zu besiegendes Monster ist ein SKELETT
		monster.clear();
		monster.add(skelett);
		assertTrue("1.4.8", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger, monster));
		// Fall 1.4.9
		// Zu besiegendes Monster sind KOBOLDE
		monster.clear();
		monster.add(kobold);
		monster.add(kobold1);
		monster.add(kobold2);
		assertTrue("1.4.9", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger, monster));
		// Fall 1.4.10
		// Zu besiegende Monster gehoeren 2 verschiedenen Rassen an
		monster.clear();
		monster.add(kobold);
		monster.add(schleimwesen);
		assertFalse("1.4.10", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger, monster));

		monster.clear();
		monster.add(drachenkopf);
		monster.add(schleimwesen);
		monster.add(skelett);
		monster.add(truhe);
		monster.add(kobold);
		monster.add(trank);

		// Fall 1.5
		// Gefaehrte ist ZAUBERER
		// Fall 1.5.1
		// Zu besiegende Monster sind bunt (mit Truhe, Drache, Trank)
		assertFalse("1.5.1", monsterPhasenRegel.pruefeAuswahlErlaubt(zauberer, monster));
		// Fall 1.5.2
		// Zu besiegendes Monster ist DRACHENKOPF
		monster.clear();
		monster.add(drachenkopf);
		assertFalse("1.5.2", monsterPhasenRegel.pruefeAuswahlErlaubt(zauberer, monster));
		// Fall 1.5.3
		// Zu besiegendes Monster ist TRANK
		monster.clear();
		monster.add(trank);
		assertFalse("1.5.3", monsterPhasenRegel.pruefeAuswahlErlaubt(zauberer, monster));
		// Fall 1.5.5
		// Zu besiegendes Monster ist TRUHE
		monster.clear();
		monster.add(truhe);
		assertFalse("1.5.5", monsterPhasenRegel.pruefeAuswahlErlaubt(zauberer, monster));
		// Fall 1.5.5
		// Zu besiegendes Monster sind SCHLEIMWESEN
		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);
		monster.add(schleimwesen2);
		assertTrue("1.5.5", monsterPhasenRegel.pruefeAuswahlErlaubt(zauberer, monster));
		// Fall 1.5.6
		// Zu besiegendes Monster sind SKELETTE
		monster.clear();
		monster.add(skelett);
		monster.add(skelett1);
		monster.add(skelett2);
		assertFalse("1.5.6", monsterPhasenRegel.pruefeAuswahlErlaubt(zauberer, monster));
		// Fall 1.5.7
		// Zu besiegendes Monster ist ein SKELETT
		monster.clear();
		monster.add(skelett);
		assertTrue("1.5.7", monsterPhasenRegel.pruefeAuswahlErlaubt(zauberer, monster));
		// Fall 1.5.8
		// Zu besiegendes Monster sind KOBOLDE
		monster.clear();
		monster.add(kobold);
		monster.add(kobold1);
		monster.add(kobold2);
		assertFalse("1.5.8", monsterPhasenRegel.pruefeAuswahlErlaubt(zauberer, monster));
		// Fall 1.5.9
		// Zu besiegen ist ein KOBOLD
		monster.clear();
		monster.add(kobold);
		assertTrue("1.5.8", monsterPhasenRegel.pruefeAuswahlErlaubt(zauberer, monster));
		// Fall 1.5.10
		// Zu besiegende Monster gehoeren 2 verschiedenen Rassen an
		monster.clear();
		monster.add(kobold);
		monster.add(schleimwesen);
		assertFalse("1.5.10", monsterPhasenRegel.pruefeAuswahlErlaubt(zauberer, monster));

		monster.clear();
		monster.add(drachenkopf);
		monster.add(schleimwesen);
		monster.add(skelett);
		monster.add(truhe);
		monster.add(kobold);
		monster.add(trank);

		// Fall 1.6
		// Gefaehrte ist SPRUCHROLLE
		// Fall 1.6.1
		// Zu besiegende Monster sind bunt (mit Truhe, Drache, Trank)
		assertFalse("1.6.1", monsterPhasenRegel.pruefeAuswahlErlaubt(spruchrolle, monster));
		// Fall 1.6.2
		// Zu besiegende Monster sind bunt
		monster.remove(drachenkopf);
		monster.remove(trank);
		monster.remove(truhe);
		assertFalse("1.6.2", monsterPhasenRegel.pruefeAuswahlErlaubt(zauberer, monster));

		monster.clear();
		monster.add(schleimwesen);
		monster.add(skelett);
		monster.add(kobold);
	}

	@Test
	public void testTestpruefeAuswahlErlaubtSchatz() {
		abenteuer.setPhase(Phase.KAMPFPHASE);
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().setHeld(soeldner.alsHeld());
		// Fall 2
		// Gleicher Held
		// Da in der Methode mit Wuerfeln arbeitet, wird hier nur fuer jeden
		// Schatztyp das postitv-Beispiel getestet
		// Fall 2.1 Spruchrollen_Schatz
		// Fall 2.1.1
		// Zu besigende Monster sind bunt
		assertFalse("2.1.1", monsterPhasenRegel.pruefeAuswahlErlaubt(spruchrolle_schatz, monster));
		// Fall 2.2 drachenschuppen
		// Fall 2.2.1
		// Zu besigende Monster sind bunt
		assertFalse("2.2.1", monsterPhasenRegel.pruefeAuswahlErlaubt(drachenschuppen, monster));
		// Fall 2.3 drachenkoeder
		// Fall 2.3.1
		// Zu besigende Monster sind bunt
		assertFalse("2.3.1", monsterPhasenRegel.pruefeAuswahlErlaubt(drachenkoeder, monster));
		// Fall 2.4 stadtportal
		// Fall 2.4.1
		// Zu besigende Monster sind bunt
		assertFalse("2.4.1", monsterPhasenRegel.pruefeAuswahlErlaubt(stadtportal, monster));
		// Fall 2.5 trank_schatz
		// Fall 2.5.1
		// Zu besigende Monster sind bunt
		assertFalse("2.5.1", monsterPhasenRegel.pruefeAuswahlErlaubt(trank_schatz, monster));
		// Fall 2.6 unsichtbarkeitsring
		// Fall 2.6.1
		// Zu besigende Monster sind bunt
		assertFalse("2.6.1", monsterPhasenRegel.pruefeAuswahlErlaubt(unsichtbarkeitsring, monster));
		// Fall 2.7 diebeswerkzeug
		// Fall 2.7.1
		// Ein zu besiegendes Monster
		monster.clear();
		monster.add(schleimwesen);
		assertTrue("2.7.1", monsterPhasenRegel.pruefeAuswahlErlaubt(diebeswerkzeug, monster));
		// Falll 2.8 Talisman
		// Fall 2.8.1
		// Liste von Skeletten
		monster.clear();
		monster.add(skelett);
		monster.add(skelett1);
		monster.add(skelett2);
		assertTrue("2.8.1", monsterPhasenRegel.pruefeAuswahlErlaubt(talisman, monster));
		// Fall 2.9 Koepferklinge
		// Fall 2.9.1
		// Liste von Kobolden
		monster.clear();
		monster.add(kobold);
		monster.add(kobold1);
		monster.add(kobold2);
		assertTrue("2.9.1", monsterPhasenRegel.pruefeAuswahlErlaubt(koepferklinge, monster));
		// Fall 2.10 Zepter der Macht
		// Fall2.10.1
		// Liste von Schleimwesen
		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);
		monster.add(schleimwesen2);
		assertTrue("2.10.1", monsterPhasenRegel.pruefeAuswahlErlaubt(zepterdermacht, monster));
	}

	@Test
	public void testTestpruefeAuswahlErlaubtHeld() {
		abenteuer.setPhase(Phase.KAMPFPHASE);
		// Fall 3
		// Sonderhelden - passiv
		// 3.1
		// Champion und Barde
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().setHeld(barde.alsHeld());
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld().setLevelUp(true);
		monster.add(kobold);

		assertTrue("3.1", monsterPhasenRegel.pruefeAuswahlErlaubt(champion, monster));

		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld().setLevelUp(false);

		// Quelle = Dieb
		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);
		monster.add(schleimwesen2);
		// TODO
		// assertTrue("3.1", monsterPhasenRegel.pruefeAuswahlErlaubt(dieb, monster));
		// Quelle = Zauberer ist hier nicht spannend, sondern beim Kistenoeffner

		// 3.2 Kommandant und Krieger
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().setHeld(soeldner.alsHeld());
		// Fall 3.2.1 Skelette + Drache
		monster.clear();
		monster.add(skelett);
		monster.add(skelett1);
		monster.add(skelett2);
		monster.add(drachenkopf);
		assertFalse("3.2.1", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger, monster));
		// Fall 3.2.2 Skelette + zwei weitere Monster
		monster.remove(drachenkopf);
		monster.add(schleimwesen);
		monster.add(kobold);
		assertFalse("3.2.2", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger, monster));
		// Fall 3.2.3 Skelette + ein weiteres Monster
		monster.clear();
		monster.add(skelett1);
		monster.add(skelett);
		monster.add(skelett2);
		monster.add(schleimwesen1);
		// TODO
		// assertTrue("3.2.3", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger,
		// monster));
		// Fall 3.2.4 Zwei verschiedene Monster
		monster.clear();
		monster.add(schleimwesen);
		monster.add(kobold);
		// TODO
		// assertTrue("3.2.4", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger,
		// monster));
		// Fall 3.2.5 Drei verschiedenene Monster
		monster.add(kobold);
		assertFalse("3.25", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger, monster));

		// 3.3 Okkultistin Priester und Zauberer
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().setHeld(okkultistin.alsHeld());
		// Fall 3.3.1
		// Priester
		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);
		monster.add(schleimwesen2);
		// TODO
		// assertTrue("3.3.1", monsterPhasenRegel.pruefeAuswahlErlaubt(priester,
		// monster));
		// Zauberer
		monster.clear();
		monster.add(skelett);
		monster.add(skelett1);
		monster.add(skelett2);
		// TODO
		// assertTrue("3.3.1_2", monsterPhasenRegel.pruefeAuswahlErlaubt(zauberer,
		// monster));

		// andere Faelle sind analog zu Fall 1!

		// 3.4 Kreuzritterin Priester als Krieger
		// Fall 3.4.1
		// Priester
		monster.clear();
		monster.add(kobold);
		monster.add(kobold1);
		monster.add(kobold2);
		// TODO
		// assertTrue("3.4.1", monsterPhasenRegel.pruefeAuswahlErlaubt(priester,
		// monster));
		// Krieger
		monster.clear();
		monster.add(skelett);
		monster.add(skelett1);
		monster.add(skelett2);
		// TODO
		// assertTrue("3.4.1_2", monsterPhasenRegel.pruefeAuswahlErlaubt(krieger,
		// monster));

		// 3.5 Adeptin und Spruchrolle
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().setHeld(adeptin.alsHeld());
		// Fall 3.5.1 Spruchrolle + ein Monstertyp
		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);
		monster.add(schleimwesen2);
		// TODO
		// assertTrue("3.5.1", monsterPhasenRegel.pruefeAuswahlErlaubt(spruchrolle,
		// monster));

		// Fall 4
		// Sonderhelden - aktiv
		// 4.1 Kreuzritterinpaladinin
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().setHeld(paladinin.alsHeld());
		// Fall 4.1.1 Priester oder Krieger
		// Priester
		monster.clear();
		monster.add(kobold);
		monster.add(kobold1);
		monster.add(kobold2);
		assertTrue("4.1.1", monsterPhasenRegel.pruefeAuswahlErlaubt(paladinin, monster));
		// Krieger
		monster.clear();
		monster.add(skelett);
		monster.add(skelett1);
		monster.add(skelett2);
		assertTrue("4.1.1_2", monsterPhasenRegel.pruefeAuswahlErlaubt(paladinin, monster));
		// 4.1.2 Ultimative Faehikgeit bereits genutzt
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld()
				.setUltimativeEingesetzt(true);
		assertFalse("4.1.2", monsterPhasenRegel.pruefeAuswahlErlaubt(paladinin, monster));
		// 4.2 Arkanerschwertmeister
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().setHeld(arkaner.alsHeld());
		// 4.2.1 Ultimative Faehigkeit bereits genutzt
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld()
				.setUltimativeEingesetzt(true);
		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);
		monster.add(schleimwesen2);
		assertFalse("4.2.1", monsterPhasenRegel.pruefeAuswahlErlaubt(arkaner, monster));
		// Fall 4.2.2 Zauberer oder Krieger
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().getHeld()
				.setUltimativeEingesetzt(false);
		// Zauberer
		assertTrue("4.2.2", monsterPhasenRegel.pruefeAuswahlErlaubt(arkaner, monster));
		// Krieger
		monster.clear();
		monster.add(kobold);
		monster.add(kobold1);
		monster.add(kobold2);
		assertTrue("4.2.2", monsterPhasenRegel.pruefeAuswahlErlaubt(arkaner, monster));
	}

	@Test
	public void testTestpruefeAuswahlErlaubtPhase() {
		abenteuer.setPhase(Phase.KAMPFPHASE);
		// Fall 5 Phasen
		// Fall 5.1 Wuerfelphase
		abenteuer.setPhase(Phase.WUERFELPHASE);
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().setHeld(soeldner.alsHeld());
		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);
		monster.add(schleimwesen2);
		assertFalse("5.1", monsterPhasenRegel.pruefeAuswahlErlaubt(champion, monster));
		// Fall 5.2 Pluenderphase
		abenteuer.setPhase(Phase.PLUENDERPHASE);
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().setHeld(soeldner.alsHeld());
		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);
		monster.add(schleimwesen2);
		assertFalse("5.2", monsterPhasenRegel.pruefeAuswahlErlaubt(champion, monster));
		// Fall 5.3 Umgruppierungsphase
		abenteuer.setPhase(Phase.UMGRUPPIERUNGSPHASE);
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().setHeld(soeldner.alsHeld());
		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);
		monster.add(schleimwesen2);
		assertFalse("5.3", monsterPhasenRegel.pruefeAuswahlErlaubt(champion, monster));
		// Fall 5.4 Drachenphase
		abenteuer.setPhase(Phase.DRACHENPHASE);
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().setHeld(soeldner.alsHeld());
		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);
		monster.add(schleimwesen2);
		assertFalse("5.4", monsterPhasenRegel.pruefeAuswahlErlaubt(champion, monster));
	}

	@Test
	public void testTestregelAusfuehrenWuerfel() {
		// Vorbedingungen
		List<Wuerfel> dungeon = new ArrayList<Wuerfel>();
		dungeon.add(schleimwesen.alsWuerfel());
		dungeon.add(schleimwesen1.alsWuerfel());
		dungeon.add(skelett.alsWuerfel());
		abenteuer.setDungeon(dungeon);

		List<Wuerfel> gefaehrten = new ArrayList<Wuerfel>();
		gefaehrten.add(champion.alsWuerfel());
		abenteuer.setGefaehrten(gefaehrten);

		List<Wuerfel> friedhof = new ArrayList<Wuerfel>();
		abenteuer.setFriedhof(friedhof);

		abenteuer.setPhase(Phase.KAMPFPHASE);
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().setHeld(soeldner.alsHeld());

		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);

		List<Schatz> schaetze = new ArrayList<Schatz>();
		schaetze.add(Schatz.ZEPTERDERMACHT);

		SpielerWerte spielerWerte = new SpielerWerte();
		Spieler gamer = spieler.get(0);
		gamer.setSpielerWerte(spielerWerte);
		gamer.getSpielerWerte().setSchaetze(schaetze);

		// Normaler Kampf:
		monsterPhasenRegel.regelAusfuehren(champion, monster);

		assertFalse("Champion wurde aus den Gefaehrten entfernt", masterController.getDungeonRoll()
				.getAktuellerZustand().getAbenteuer().getGefaehrten().contains(champion.alsWuerfel()));
		assertTrue("Champion muss auf den Friedhof verschoben werden",
				friedhof.size() == 1 && friedhof.contains(champion.alsWuerfel()));
		assertFalse("Monster wurden besiegt",
				dungeon.contains(schleimwesen.alsWuerfel()) && dungeon.contains(schleimwesen1.alsWuerfel()));
		assertTrue("Skelett wurde nicht besiegt", dungeon.contains(skelett.alsWuerfel()));
		assertNotNull("Spieler behaelt Schatz", gamer.getSpielerWerte().getSchaetze());
		assertFalse("Ulti-Faehigkeit nicht genutzt", gamer.getHeld().isUltimativeEingesetzt());

	}

	@Test
	public void testTestregelAusfuehrenSchatz() {
		// Vorbedingungen
		List<Wuerfel> dungeon = new ArrayList<Wuerfel>();
		dungeon.add(schleimwesen.alsWuerfel());
		dungeon.add(schleimwesen1.alsWuerfel());
		dungeon.add(skelett.alsWuerfel());
		abenteuer.setDungeon(dungeon);

		List<Wuerfel> gefaehrten = new ArrayList<Wuerfel>();
		gefaehrten.add(champion.alsWuerfel());
		abenteuer.setGefaehrten(gefaehrten);

		List<Wuerfel> friedhof = new ArrayList<Wuerfel>();
		abenteuer.setFriedhof(friedhof);

		abenteuer.setPhase(Phase.KAMPFPHASE);
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().setHeld(soeldner.alsHeld());

		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);

		List<Schatz> schaetze = new ArrayList<Schatz>();
		schaetze.add(Schatz.ZEPTERDERMACHT);

		SpielerWerte spielerWerte = new SpielerWerte();
		Spieler gamer = spieler.get(0);
		gamer.setSpielerWerte(spielerWerte);
		gamer.getSpielerWerte().setSchaetze(schaetze);

		// Schatz im Kampf
		monsterPhasenRegel.regelAusfuehren(zepterdermacht, monster);

		assertTrue("Champion noch in den Gefaehrten enthalten", masterController.getDungeonRoll().getAktuellerZustand()
				.getAbenteuer().getGefaehrten().contains(champion.alsWuerfel()));
		assertTrue("Friedhof ist leer",
				masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getFriedhof().isEmpty());
		assertFalse("Monster wurden besiegt",
				masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDungeon()
						.contains(schleimwesen.alsWuerfel())
						&& masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDungeon()
								.contains(schleimwesen1.alsWuerfel()));
		assertTrue("Skelett wurde nicht besiegt", masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer()
				.getDungeon().contains(skelett.alsWuerfel()));
		assertNull("Spieler besitzt keinen Schatz mehr", gamer.getSpielerWerte().getSchaetze());
		assertFalse("Ulti-Faehigkeit nicht genutzt", gamer.getHeld().isUltimativeEingesetzt());

	}

	@Test
	public void testTestregelAusfuehrenHeld() {
		// Vorbedingungen
		List<Wuerfel> dungeon = new ArrayList<Wuerfel>();
		dungeon.add(schleimwesen.alsWuerfel());
		dungeon.add(schleimwesen1.alsWuerfel());
		dungeon.add(skelett.alsWuerfel());
		abenteuer.setDungeon(dungeon);

		List<Wuerfel> gefaehrten = new ArrayList<Wuerfel>();
		gefaehrten.add(champion.alsWuerfel());
		abenteuer.setGefaehrten(gefaehrten);

		List<Wuerfel> friedhof = new ArrayList<Wuerfel>();
		abenteuer.setFriedhof(friedhof);

		abenteuer.setPhase(Phase.KAMPFPHASE);
		masterController.getDungeonRoll().getAktuellerZustand().aktuellerSpieler().setHeld(arkaner.alsHeld());

		monster.clear();
		monster.add(schleimwesen);
		monster.add(schleimwesen1);

		List<Schatz> schaetze = new ArrayList<Schatz>();
		schaetze.add(Schatz.ZEPTERDERMACHT);

		SpielerWerte spielerWerte = new SpielerWerte();
		Spieler gamer = spieler.get(0);
		gamer.setSpielerWerte(spielerWerte);
		gamer.getSpielerWerte().setSchaetze(schaetze);
		gamer.getHeld().setLevelUp(true);

		// Held im Kampf einsetzen
		assertFalse("Monster besiegt",
				masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDungeon()
						.contains(schleimwesen.alsWuerfel())
						&& masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer().getDungeon()
								.contains(schleimwesen1.alsWuerfel()));
		assertTrue("Skelett nicht besiegt", masterController.getDungeonRoll().getAktuellerZustand().getAbenteuer()
				.getDungeon().contains(skelett.alsWuerfel()));
		assertTrue("Gefaehrte nicht benutzt", gefaehrten.size() == 1);
		assertNotNull("Spieler besitzt noch seine Schaetze", gamer.getSpielerWerte().getSchaetze());
		assertTrue("Ult-Faehigkeit wurde genutzt", gamer.getHeld().isUltimativeEingesetzt());
	}

}
