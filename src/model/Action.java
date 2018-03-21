package model;

import java.util.List;

import control.AbenteuerController;
import control.MasterController;

/**
 * Eine Aktion, die auf einem Spielzustand angewandt werden kann und konform zu den Regeln des Spiels ist
 * @author Pascal
 *
 */
public abstract class Action {
	
	protected MasterController masterController;
	
	public void setMasterController(MasterController masterController) {
		this.masterController = masterController;
	}
	
	public abstract void apply(Spielzustand zustand);
	
	
	
	public static class BeendenAction extends Action {
		public BeendenAction(MasterController masterController) {
			this.masterController=masterController;
		}
		
		@Override
		public void apply(Spielzustand zustand) {
			masterController.getAbenteuerController().abenteuerBeenden();
		}
	}
	
	public static class NaechstesLevelAction extends Action {
		
		public NaechstesLevelAction(MasterController masterController) {
			this.masterController=masterController;
		}
		
		@Override
		public void apply(Spielzustand zustand) {
			AbenteuerController abenteuerController = masterController.getAbenteuerController();
			abenteuerController.naechstesLevel();
		}
	}
	
	public static class RegelAusfuehrenAction extends Action {

		private Quelle quelle;
		private List<Ziel> ziel;
		
		public RegelAusfuehrenAction(MasterController masterController, Quelle quelle, List<Ziel> ziel) {
			super();
			this.masterController = masterController;
			this.quelle = quelle;
			this.ziel = ziel;
		}

		@Override
		public void apply(Spielzustand zustand) {
			masterController.getAuswahlController().regelAusfuehren(quelle, ziel);
		}	
	}
	
}
