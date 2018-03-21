package gui.spielfeld;

import javafx.scene.layout.Pane;
import model.Quelle;

public class QuellePane extends Pane {
	private Quelle quelle;
	
	public QuellePane() {
		super();
	}
	
	public QuellePane(Quelle quelle) {
		super();
		this.quelle = quelle;
	}
	
	public Quelle getQuelle() {
		return quelle;
	}

	public void setQuelle(Quelle quelle) {
		this.quelle = quelle;
	}
}
