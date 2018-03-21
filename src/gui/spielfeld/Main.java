package gui.spielfeld;

import java.util.ArrayList;
import java.util.List;

import control.MasterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Abenteuer;
import model.DungeonRoll;
import model.Held;
import model.Heldtyp;
import model.Schatz;
import model.SchwarzeWuerfelseite;
import model.Spieler;
import model.SpielerWerte;
import model.Spielertyp;
import model.Spielzustand;
import model.WeisseWuerfelseite;
import model.Wuerfel;

public class Main extends Application{

		
	@Override
	public void start(Stage primaryStage) {
		
		// Testing
		MasterController masterController = new MasterController();
		DungeonRoll dgRoll = masterController.getDungeonRoll();
		Spielzustand spZust = new Spielzustand();
		Abenteuer abent = new Abenteuer();
		List<Wuerfel> fr = new ArrayList<Wuerfel>();
		List<Wuerfel> dung = new ArrayList<Wuerfel>();
		List<Wuerfel> dra = new ArrayList<Wuerfel>();
		List<Wuerfel> gef = new ArrayList<Wuerfel>();
		Wuerfel wuer1 = new Wuerfel(true);
		wuer1.setWeisseWuerfelseite(WeisseWuerfelseite.CHAMPION);
		Wuerfel wuer2 = new Wuerfel(true);
		wuer2.setWeisseWuerfelseite(WeisseWuerfelseite.KRIEGER);
		Wuerfel wuer3 = new Wuerfel(true);
		wuer3.setWeisseWuerfelseite(WeisseWuerfelseite.PRIESTER);
		Wuerfel wuer4 = new Wuerfel(true);
		wuer4.setWeisseWuerfelseite(WeisseWuerfelseite.DIEB);
		fr.add(wuer1);
		fr.add(wuer2);
		fr.add(wuer3);
		
		Wuerfel wuer10 = new Wuerfel(false);
		wuer10.setSchwarzeWuerfelseite(SchwarzeWuerfelseite.SCHLEIMWESEN);
		dung.add(wuer10);
		
		Wuerfel wuer5 = new Wuerfel(true);
		wuer5.setWeisseWuerfelseite(WeisseWuerfelseite.CHAMPION);
		Wuerfel wuer6 = new Wuerfel(true);
		wuer6.setWeisseWuerfelseite(WeisseWuerfelseite.KRIEGER);
		Wuerfel wuer7 = new Wuerfel(true);
		wuer7.setWeisseWuerfelseite(WeisseWuerfelseite.PRIESTER);
		Wuerfel wuer8 = new Wuerfel(true);
		wuer8.setWeisseWuerfelseite(WeisseWuerfelseite.DIEB);
		
		gef.add(wuer5);
		gef.add(wuer6);
		gef.add(wuer7);
		gef.add(wuer8);
		
		SpielerWerte spWerte = new SpielerWerte();
    	spWerte.setErfahrungspunkte(9);
    	spWerte.setSchaetze(new ArrayList<Schatz>());
		
		abent.setDrachen(dra);
		abent.setDungeon(dung);
		abent.setFriedhof(fr);
		abent.setGefaehrten(gef);
		abent.setSpielerWerte(spWerte);
		spZust.setAbenteuer(abent);

		Held held1 = new Held();
    	held1.setLevelUp(false);
    	held1.setUltimativeEingesetzt(false);;
    	held1.setHeldtyp(Heldtyp.HALBKOBOLDHAEUPTLING);
    	ArrayList<Schatz> schatzList = new ArrayList<Schatz>();
    	Schatz sch1 = Schatz.DIEBESWERKZEUG;
    	Schatz sch2 = Schatz.DRACHENSCHUPPEN;
    	Schatz sch3 = Schatz.SPRUCHROLLE;
    	schatzList.add(sch1);
    	schatzList.add(sch2);
    	schatzList.add(sch3);
    	SpielerWerte spWert1 = new SpielerWerte();
    	spWert1.setErfahrungspunkte(5);    	
    	spWert1.setSchaetze(schatzList);
    	
		Spieler sp1 = new Spieler(), sp2 = new Spieler();    	
    	sp1.setName("Mikhail");
    	sp1.setHeld(held1);
    	sp1.setSpielertyp(Spielertyp.MENSCHLICH);
    	sp1.setSpielerWerte(spWert1);
    		
    	sp2.setName("Dmytro");
    	sp2.setSpielertyp(Spielertyp.MENSCHLICH);
    	sp2.setSpielerWerte(spWert1);
    	sp2.setHeld(held1);
    	
    	List<Spieler> spielerList = new ArrayList<Spieler>();
    	spielerList.add(sp1);
    	spielerList.add(sp2);
    	
		spZust.setSpieler(spielerList);
		spZust.setSpielerIndex(0);
		dgRoll.setAktuellerZustand(spZust);
		
		FXMLLoader loader = new FXMLLoader();
		try {
			Parent root = loader.load(getClass().getResource("Spielfeld.fxml").openStream());
			
			SpielfeldController spielfeldController = loader.getController();
			spielfeldController.setMasterController(masterController);
			
			Scene scene = new Scene(root,1280,1020);
			scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
			primaryStage.close();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}