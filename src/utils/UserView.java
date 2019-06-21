package utils;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserView extends JPanel {
	User user;
	
	public UserView(User user) {
		this.user = user;
		JLabel text = new JLabel();
		text.setText("Welcome, " + user.getName() + ". You currently have $" + user.getMoney() + " left.");
		add(text);
	}
}
