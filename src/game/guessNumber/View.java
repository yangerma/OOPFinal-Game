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

	private static final JLabel betLabel = new JLabel("Your bet:");
	private final JButton startButton = new JButton("Start");
	private final JButton restartButton = new JButton("Restart");
	private final JTextField  betField = new JTextField();
	
	private static final JLabel playerLabel = new JLabel("Your guesses:");
	private static final JLabel computerLabel = new JLabel("Computer's guesses:");

	private final JButton submitButton = new JButton("Submit");
	private final JTextArea infoArea = new JTextArea();	
	private final JTextArea playerArea = new JTextArea();
	private final JTextArea computerArea = new JTextArea();
	private final JTextField submitField = new JTextField();
	
	private final JPanel history = new JPanel();
	private final JPanel communicate = new JPanel();
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
			playerArea.setText("");
			computerArea.setText("");
			
			communicate.remove(restartButton);
			View.this.add(history);
			View.this.add(communicate);
			View.this.repaint();
			View.this.revalidate();
		}	
	}
	
	private void loadStart() {
		removeAll();
		setLayout(new GridBagLayout());
		betField.setText("");
        JPanel betPanel = new JPanel(new GridLayout(1, 2));
        betPanel.add(betLabel);
        betPanel.add(betField);
        
        add(betPanel, config);
        add(startButton, config);
        repaint();
        revalidate();
	}
	
	private class restartHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			loadStart();
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
			submitField.setText("");
		}
	}
	
	public View(Model model) {
		super(model);
		this.model = model;
		support = new PropertyChangeSupport(this);
		startButton.addActionListener(new startHandler());
		
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
        infoArea.setPreferredSize(new Dimension(300, 80));
		communicate.add(infoArea, config);
		communicate.add(playingPanel, config);
		restartButton.addActionListener(new restartHandler());
		
		loadStart();
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
		case Properties.computerGuess:
			computerArea.append((String)evt.getNewValue());
			break;
		case Properties.button:
			communicate.add(restartButton, config);
			
		//assert(false);
		}
	}
}
