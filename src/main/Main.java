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
import utils.User;

public class Main implements Observer{
	static JFrame f;
	static boolean loggedIn = false;
	static Login current;
	
	public static void main(String[] args) {
		Main index = new Main();
        f = new JFrame();
        f.setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        current = new Login();
        current.addObserver(index);
       	f.add(current, BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		User temp = (User) arg;
		System.out.println(arg);
	}
}
