package game;

import java.beans.PropertyChangeListener;

import javax.swing.*;

public abstract class View extends JPanel implements PropertyChangeListener {
	public View(Model model) {
		model.addPropertyChangeListener(this);
	}
	protected void showMessage(String message) {
	    showMessage(message, "Message");
	}
	protected void showMessage(String message, String title) {
	    JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
	}
}
