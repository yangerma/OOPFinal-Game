package main;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
//import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import utils.*;
import game.*;


public class Controller extends JPanel {
	private static JPanel menu;
	private gameFinder gameFinder;
	private String currentGameName;
	
	private class Buttons extends JPanel {
		
		GridBagConstraints config = new GridBagConstraints();
		
		Buttons() {
			setLayout(new GridBagLayout());
			
			config.anchor = GridBagConstraints.CENTER;
			config.gridwidth = GridBagConstraints.REMAINDER;
			
			for (String name : gameFinder.gameNames()) {
			    JButton gameButton = new JButton(name);
			    gameButton.addActionListener(new ChoosingHandler());
			    this.add(gameButton, config);
			}
		}
	}
	
	private class ChoosingHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			Controller.this.removeAll();
			View view  = gameFinder.findGame(evt.getActionCommand());
			currentGameName = new String(evt.getActionCommand());
			Controller.this.add(view, BorderLayout.CENTER);
			JButton exitButton = new JButton("Exit Game");
			exitButton.addActionListener(new ExitHandler());
			Controller.this.add(exitButton, BorderLayout.SOUTH);
			Controller.this.repaint();
			Controller.this.revalidate();
		}
	}
	
	private class ExitHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
		    int res = JOptionPane.showConfirmDialog(Controller.this, "Are you sure you want to leave " + currentGameName + "? "
		            + "You may lose your bets if the game is not ended.", "Message", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		    if (res != JOptionPane.YES_OPTION)
		        return;
			Controller.this.removeAll();
			Controller.this.add(menu, BorderLayout.CENTER);
			Controller.this.repaint();
			Controller.this.revalidate();
		}
	}
	
	Controller(User user) {
	    gameFinder = new gameFinder(user);
		setLayout(new BorderLayout());
		menu = new Buttons();
		this.add(menu, BorderLayout.CENTER);
	}
}
