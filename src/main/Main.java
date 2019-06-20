package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.Login;

public class Main {
	static JFrame f;
	static boolean loggedIn = false;
	static JPanel current;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main index = new Main();
        f = new JFrame();
        f.setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       	current = new Login(index.new ButtonHandler());
        //current = new Login();
       	f.add(current, BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
	}
	
	private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            f.remove(current);
            f.add(new Controller(), BorderLayout.CENTER);
            f.repaint();
            f.revalidate();
        }
    }

}
