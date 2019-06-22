package game.shibala;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import game.shibala.Model;

public class View extends game.View{
	JButton rollDice, setBet;
	JTextArea messages;
	NumberFormat intFormat;
	JFormattedTextField betArea;
	DicePanel dicePanel;
	Model model;
	GridBagConstraints config = new GridBagConstraints();
	
	// The panel that shows the three dice
	private class DicePanel extends JPanel {
		ImageIcon[] allDice = new ImageIcon[7];
		JLabel dieOne, dieTwo, dieThree;
		
		DicePanel() {
			setLayout(new GridBagLayout());
			config.insets = new Insets(0, 20, 0, 20);
			
			for(int i = 1; i < 7; i++) {
				allDice[i] = new ImageIcon("res/dice" + i + ".png");
			}
			dieOne = new JLabel(allDice[6]);
			dieTwo = new JLabel(allDice[6]);
			dieThree = new JLabel(allDice[6]);
			
			this.add(dieOne, config);
			this.add(dieTwo, config);
			config.gridwidth = GridBagConstraints.REMAINDER;
			this.add(dieThree, config);
		}
		
		public void setDieOne(int which) {
			dieOne.setIcon(allDice[which]);
			this.repaint();
			this.revalidate();
		}
		public void setDieTwo(int which) {
			dieTwo.setIcon(allDice[which]);
			this.repaint();
			this.revalidate();
		}
		public void setDieThree(int which) {
			dieThree.setIcon(allDice[which]);
			this.repaint();
			this.revalidate();
		}
	}
	
	// Handler when user presses "Roll!"
	private class RolledHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			try {
				model.rollDice();
			} catch (RuntimeException e) {
				showMessage(e.getMessage());
			}
		}
	}
	
	// Handler when user places bet
	private class BetPlacedHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			try {
				setBet.setEnabled(false);
				model.startGame(betArea.getText());
			} catch (RuntimeException e) {
				showMessage(e.getMessage());
			}
		}
	}
	
	public View(Model model) {
		super(model);
		this.model = model;
		setLayout(new GridBagLayout());
		
		// Setting up the UI
		dicePanel = new DicePanel();
		rollDice = new JButton("Roll!");
		rollDice.addActionListener(new RolledHandler());
		rollDice.setEnabled(false);
		intFormat = NumberFormat.getIntegerInstance();
		intFormat.setGroupingUsed(false);
		betArea = new JFormattedTextField(intFormat);
		setBet = new JButton("Place Bet");
		setBet.addActionListener(new BetPlacedHandler());
		messages = new JTextArea("Welcome to Shibala!\n");
		messages.setEditable(false);
		messages.setPreferredSize(new Dimension(1000, 300));
		JScrollPane scrollPane = new JScrollPane();
		
		add(dicePanel, config);
		add(rollDice, config);
		JPanel betPanel = new JPanel(new GridLayout(1, 3));
		betPanel.add(new JLabel("Place your bet here : "));
		betPanel.add(betArea);
		betPanel.add(setBet);
		add(betPanel, config);
		add(scrollPane);
		scrollPane.setViewportView(messages);
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		String changed = evt.getPropertyName();
		if(changed == "err") showMessage((String) evt.getNewValue());
		else if(changed == "init") messages.setText((String) evt.getNewValue());
		else if(changed == "msg") messages.append((String) evt.getNewValue());
		else if(changed == "dieOne") dicePanel.setDieOne((int) evt.getNewValue());
		else if(changed == "dieTwo") dicePanel.setDieTwo((int) evt.getNewValue());
		else if(changed == "dieThree") dicePanel.setDieThree((int) evt.getNewValue());
		else if((Boolean) evt.getNewValue() == true){
			messages.append("Your turn.\n");
			rollDice.setEnabled(true);
		}
		else {
			rollDice.setEnabled(false);
			setBet.setEnabled(true);
		}
	}
}
