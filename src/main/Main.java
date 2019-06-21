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
	static JFrame f;
	static boolean loggedIn = false;
	static Login loginPage;
	
	public static void main(String[] args) {
		Main index = new Main();
        f = new JFrame();
        f.setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPage = new Login();
        loginPage.addObserver(index);
       	f.add(loginPage, BorderLayout.CENTER);
       	f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		User temp = (User) arg;
		JLabel text = new JLabel();
		text.setText("Welcome, " + temp.name + ". You currently have $" + temp.getMoney() + " left.");
		f.remove(loginPage);
		f.add(text, BorderLayout.NORTH);
		f.add(new Controller(), BorderLayout.CENTER);
		f.repaint();
		f.revalidate();
	}
}
