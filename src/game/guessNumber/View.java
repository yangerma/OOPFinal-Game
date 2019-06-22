package game.guessNumber;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class View extends game.View {
	Model model;
	JTextField  betArea;
	private PropertyChangeSupport support;
	
	private class startHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {
			// TODO Auto-generated method stub
			try {

				model.newGame(betArea.getText());
			} catch(Exception e) {
				View.super.showMessage(e.getMessage());
			}
		}
		
	}
	
	public View(Model model) {
		super(model);
		this.model = model;
		setLayout(new GridBagLayout());
		support = new PropertyChangeSupport(this);
		
		GridBagConstraints config = new GridBagConstraints();
		config.anchor = GridBagConstraints.CENTER;
		config.gridwidth = GridBagConstraints.REMAINDER;
		JLabel betLabel = new JLabel("Your bet:");
		betArea = new JTextField();
        JPanel betPanel = new JPanel();
        betPanel.setLayout(new GridLayout(1, 2));
        betPanel.add(betLabel);
        betPanel.add(betArea);
        
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new startHandler());
        add(betPanel, config);
        add(startButton, config);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub		
	}
}
