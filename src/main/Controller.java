package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;


public class Controller extends JPanel{
	Controller() {
        super(new GridLayout(3, 1));
        JButton game1Button = new JButton("Game 1");
        JButton game2Button = new JButton("Game 2");
        JButton game3Button = new JButton("Game 3");
        this.add(game1Button);
        this.add(game2Button);
        this.add(game3Button);
	}
}
