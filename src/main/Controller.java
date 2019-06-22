package main;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
//import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import utils.*;
import game.*;


public class Controller extends JPanel {
	private static JPanel menu;
	//JPanel currentPanel;
	//Model currentModel;
	
	
	private class Buttons extends JPanel {
		JButton game1Button;
		
		GridBagConstraints config = new GridBagConstraints();
		
		Buttons() {
			setLayout(new GridBagLayout());
			
			config.anchor = GridBagConstraints.CENTER;
			config.gridwidth = GridBagConstraints.REMAINDER;
			
	        game1Button = new JButton("Game 1");
	        game1Button.addActionListener(new ChoosingHandler1());

	        this.add(game1Button, config);

		}
	}
	
	private class ChoosingHandler1 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			Controller.this.removeAll();
			Model model = new game.buttonGrowth.Model();
			View view  = new game.buttonGrowth.View((game.buttonGrowth.Model)model);
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
			Controller.this.removeAll();
			//currentPanel = menu;
			Controller.this.add(menu, BorderLayout.CENTER);
			Controller.this.repaint();
			Controller.this.revalidate();
		}
	}
	
	Controller(User user) {
		setLayout(new BorderLayout());
		menu = new Buttons();
		this.add(menu, BorderLayout.CENTER);
	}
}
