package main;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

import utils.Login;
import utils.User;
import utils.UserView;

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
		User user = (User)evt.getNewValue();
		window.add(new UserView(user), BorderLayout.NORTH);
		window.add(new Controller(user), BorderLayout.CENTER);
		window.repaint();
		window.revalidate();
	}
}
