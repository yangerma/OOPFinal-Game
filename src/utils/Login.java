package utils;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Login extends JPanel {
	public Login(ActionListener listener) {
        super(new BorderLayout());
        
        JButton loginButton = new JButton("Log In");
        loginButton.addActionListener(listener);
        this.add(loginButton, BorderLayout.CENTER);

	}
}
