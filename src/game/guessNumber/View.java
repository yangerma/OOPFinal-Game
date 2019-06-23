package game.guessNumber;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class View extends game.View {
	Model model;
	
	private static final GridBagConstraints config = new GridBagConstraints();
	private static final JLabel playerLabel = new JLabel("Your guesses:");
	private static final JLabel computerLabel = new JLabel("Computer's guesses:");
	
	private final JTextArea infoArea = new JTextArea();	
	private final JTextArea playerArea = new JTextArea();
	private final JTextArea computerArea = new JTextArea();
	private final JTextField submitField = new JTextField();
	private final JButton submitButton = new JButton("Submit");
	private final JButton restartButton = new JButton("Restart");
	
	private JPanel history = new JPanel();
	private JPanel communicate = new JPanel();
	private JTextField  betField = new JTextField();
	private PropertyChangeSupport support;
	
	static {
		config.anchor = GridBagConstraints.CENTER;
		config.gridwidth = GridBagConstraints.REMAINDER;
		playerLabel.setHorizontalAlignment(JLabel.CENTER);
		computerLabel.setHorizontalAlignment(JLabel.CENTER);
	}
	
	private class startHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {
			try {
				model.newGame(betField.getText());
			} catch(Exception e) {
				View.super.showMessage(e.getMessage());
				return;
			}
			View.this.removeAll();
			View.this.setLayout(new GridLayout(2, 1));
			View.this.add(history);
			View.this.add(communicate);
		}	
	}
	
	private class submitHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			try {
				model.submit(submitField.getText());
			} catch(Exception e) {
				View.super.showMessage(e.getMessage());
			}
		}
	}
	
	public View(Model model) {
		super(model);
		this.model = model;
		support = new PropertyChangeSupport(this);
		history.setLayout(new BorderLayout());
		communicate.setLayout(new GridBagLayout());
		JPanel labels = new JPanel(new GridLayout(1, 2));
		JPanel areas = new JPanel(new GridLayout(1, 2));
		labels.add(playerLabel);
		labels.add(computerLabel);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		playerArea.setBorder(border);
		playerArea.setEditable(false);
		computerArea.setBorder(border);
		computerArea.setEditable(false);
		areas.add(playerArea);
		areas.add(computerArea);
		history.add(labels, BorderLayout.NORTH);
		history.add(areas, BorderLayout.CENTER);
        JPanel playingPanel = new JPanel();
        playingPanel.setLayout(new GridLayout(1, 2));
        playingPanel.add(submitField);
        submitButton.addActionListener(new submitHandler());
        playingPanel.add(submitButton);
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setPreferredSize(new Dimension(300, 50));
		communicate.add(infoArea, config);
		communicate.add(playingPanel, config);
		
		setLayout(new GridBagLayout());
		JLabel betLabel = new JLabel("Your bet:");
		betField = new JTextField();
        JPanel betPanel = new JPanel();
        betPanel.setLayout(new GridLayout(1, 2));
        betPanel.add(betLabel);
        betPanel.add(betField);
        
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new startHandler());
        add(betPanel, config);
        add(startButton, config);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		switch(evt.getPropertyName()) {
		case Properties.infoUpdate:
			infoArea.setText((String)evt.getNewValue());
			break;
		case Properties.infoAppend:
			infoArea.setText(infoArea.getText()+(String)evt.getNewValue());
			break;
		case Properties.playerGuess:
			playerArea.append((String)evt.getNewValue());
			break;
		//assert(false);
		}
	}
}
