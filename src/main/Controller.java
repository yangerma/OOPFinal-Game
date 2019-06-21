package main;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import utils.User;
import utils.UserView;


public class Controller extends JPanel {
	Buttons thing;
	
	private class Buttons extends JPanel {
		JButton game1Button;
		JButton game2Button;
		JButton game3Button;
		
		GridBagConstraints config = new GridBagConstraints();
		
		Buttons() {
			setLayout(new GridBagLayout());
			
			config.anchor = GridBagConstraints.CENTER;
			config.gridwidth = GridBagConstraints.REMAINDER;
			
	        game1Button = new JButton("Game 1");
	        game1Button.addActionListener(new ButtonHandler());
	        
	        game2Button = new JButton("Game 2");
	        game2Button.addActionListener(new ButtonHandler());
	        
	        game3Button = new JButton("Game 3");
	        game3Button.addActionListener(new ButtonHandler());
	        
	        this.add(game1Button, config);
	        this.add(game2Button, config);
	        this.add(game3Button, config);
		}
	}
	
	private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	String command = "You pressed " + e.getActionCommand();
        	JButton newOne = new JButton(command);
        	newOne.addActionListener(new ButtonHandler());
        	thing.add(newOne, thing.config);
        	thing.repaint();
        	thing.revalidate();
        	
        	thing.remove((JButton) e.getSource());
        	
        	thing.repaint();
        	thing.revalidate();
        }
    }
	
	Controller(User user) {
		setLayout(new BorderLayout());
		thing = new Buttons();
		this.add(new UserView(user),BorderLayout.NORTH);
		this.add(thing, BorderLayout.CENTER);
	}
}
