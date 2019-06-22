package game.shibala;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.shibala.Model;

public class View extends game.View{
	JButton rollDice;
	DicePanel dicePanel;
	Model model;
	GridBagConstraints config = new GridBagConstraints();
	
	private class DicePanel extends JPanel{
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
		}
	}
	
	public class RolledHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dicePanel.setDieOne(3);
			dicePanel.repaint();
			dicePanel.revalidate();
		}
	}
	
	public View(Model model) {
		super(model);
		this.model = model;
		setLayout(new GridBagLayout());
		
		dicePanel = new DicePanel();
		rollDice = new JButton("Roll!");
		rollDice.addActionListener(new RolledHandler());
		
		add(dicePanel, config);
		add(rollDice, config);
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		
	}
}
