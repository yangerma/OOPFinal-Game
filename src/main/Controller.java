package main;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;


public class Controller extends JPanel {
	Controller() {
        super(new BorderLayout());
        JButton x = new JButton("Controller");
        this.add(x, BorderLayout.CENTER);
	}
	
}
