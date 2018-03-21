package gui.spielfeld;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class SpielerGUI {

	private Pane spielerPane;
	private Pane heldPane;
	private Label nameLabel;
	private Label levelLabel;
	private VBox schatzVBox;
	private Tooltip tooltip;
	
	public SpielerGUI(Pane spielerPane, Pane heldPane, Label nameLabel, Label levelLabel, VBox schatzVBox, Tooltip tooltip) {
		this.spielerPane = spielerPane;
		this.heldPane = heldPane;
		this.nameLabel = nameLabel;
		this.levelLabel = levelLabel;
		this.schatzVBox = schatzVBox;
		this.tooltip = tooltip;
		Tooltip.install(heldPane, tooltip);
		tooltip.setWrapText(true);
		hackTooltip(tooltip);
	}

	public void hackTooltip(Tooltip obj) {
		try {
            Class<?> clazz = obj.getClass().getDeclaredClasses()[0];
            Constructor<?> constructor = clazz.getDeclaredConstructor(
                    Duration.class,
                    Duration.class,
                    Duration.class,
                    boolean.class);
            constructor.setAccessible(true);
            Object tooltipBehavior = constructor.newInstance(
                    new Duration(250),  //open
                    new Duration(Double.MAX_VALUE), //visible
                    new Duration(200),  //close
                    false);
            Field fieldBehavior = obj.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            fieldBehavior.set(obj, tooltipBehavior);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public Pane getSpielerPane() {
		return spielerPane;
	}

	public Pane getHeldPane() {
		return heldPane;
	}

	public Label getNameLabel() {
		return nameLabel;
	}

	public Label getLevelLabel() {
		return levelLabel;
	}

	public VBox getSchatzVBox() {
		return schatzVBox;
	}

	public Tooltip getTooltip() {
		return tooltip;
	}
}
