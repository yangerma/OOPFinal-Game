package utils;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.Controller;

class Notifier extends Observable {
	
	@Override
	public void notifyObservers(Object arg) {
		super.setChanged();
		super.notifyObservers(arg);
	}
}

public class Login extends JPanel {
	private Notifier notifier;
	JLabel userLabel, pwLabel;
	JTextField userArea, pwArea;
	JButton loginButton;
	
	public void addObserver(Observer o) {
		notifier.addObserver(o);
	}
	
	private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	User user = new User(userArea.getText(), pwArea.getText());
        	notifier.notifyObservers(user);
        }
    }
	
	public Login() {
        super(new BorderLayout());
        notifier = new Notifier();
        
        userLabel = new JLabel();
        userLabel.setText("Username : ");
        userArea = new JTextField();
        
        pwLabel = new JLabel();
        pwLabel.setText("Password : ");
        pwArea = new JPasswordField();
        
        JPanel fields = new JPanel(new GridLayout(2, 2));
        fields.add(userLabel);
        fields.add(userArea);
        fields.add(pwLabel);
        fields.add(pwArea);
        
        loginButton = new JButton("Log In");
        loginButton.addActionListener(new ButtonHandler());
        this.add(fields, BorderLayout.CENTER);
        this.add(loginButton, BorderLayout.SOUTH);
	}
}
