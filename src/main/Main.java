package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.Login;
import utils.User;

public class Main implements PropertyChangeListener {
	static JFrame window;
	static boolean loggedIn = false;
	static Login loginPage;
	
	public static void main(String[] args) {
		Main index = new Main();
        window = new JFrame();
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPage = new Login();
        loginPage.addPropertyChangeListener(index);
       	window.add(loginPage, BorderLayout.CENTER);
       	window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		loggedIn = true;
		window.remove(loginPage);
		window.add(new Controller((User) evt.getNewValue()), BorderLayout.CENTER);
		window.repaint();
		window.revalidate();
	}
}
