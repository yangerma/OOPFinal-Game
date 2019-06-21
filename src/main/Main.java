package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.Login;
import utils.User;

public class Main implements Observer{
	static JFrame window;
	static boolean loggedIn = false;
	static Login loginPage;
	
	public static void main(String[] args) {
		Main index = new Main();
        window = new JFrame();
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPage = new Login();
        loginPage.addObserver(index);
       	window.add(loginPage, BorderLayout.CENTER);
       	window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		User temp = (User) arg;
		window.remove(loginPage);
		window.add(new Controller(temp), BorderLayout.CENTER);
		window.repaint();
		window.revalidate();
	}
}
