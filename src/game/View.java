package game;

import java.beans.PropertyChangeListener;

import javax.swing.*;

public abstract class View extends JPanel implements PropertyChangeListener {
	Model model;
	public View(Model model) {
		model.addPropertyChangeListener(this);
		this.model = model;
	}
}
