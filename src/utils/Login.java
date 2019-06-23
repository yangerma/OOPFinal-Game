package utils;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login extends JPanel {
	private PropertyChangeSupport support;
	
	JLabel userLabel, pwLabel, pwConfirmLabel;
	JTextField userArea, pwArea, pwConfirmArea;
	JPanel fields;
	JButton loginButton, registerButton, switchButton;
	GridBagConstraints config;
	boolean isLogin; 
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		this.support.addPropertyChangeListener(pcl);
	}
	private void setLogin() {
	    isLogin = true;
	    switchButton.setText("Don't have an account? Register!");
	    fields.remove(pwConfirmLabel);
	    fields.remove(pwConfirmArea);
	    this.remove(registerButton);
	    this.add(loginButton, config);
	    this.add(switchButton, config);
	}
	private void setRegister() {
	    isLogin = false;
	    switchButton.setText("Have an account? Login!");
	    fields.add(pwConfirmLabel);
        fields.add(pwConfirmArea);
        this.remove(loginButton);
        this.add(registerButton, config);
        this.add(switchButton, config);
	}
	
	private class LoginButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	User user = new User(userArea.getText(), pwArea.getText());
        	support.firePropertyChange("user", 0, user);
        }
    }
	private class RegisterButtonHandler implements ActionListener {
	    @Override
        public void actionPerformed(ActionEvent e) {
	        // TODO
            User user = new User(userArea.getText(), pwArea.getText());
            support.firePropertyChange("user", 0, user);
        }
	}
	private class SwitchButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isLogin)
                setRegister();
            else
                setLogin();
        }
    }
	
	public Login() {
		setLayout(new GridBagLayout());
		support = new PropertyChangeSupport(this);
		
		config = new GridBagConstraints();
		config.anchor = GridBagConstraints.CENTER;
		config.gridwidth = GridBagConstraints.REMAINDER;
        
        userLabel = new JLabel();
        userLabel.setText("Username : ");
        userArea = new JTextField();
        
        pwLabel = new JLabel();
        pwLabel.setText("Password : ");
        pwArea = new JPasswordField();
        
        pwConfirmLabel = new JLabel();
        pwConfirmLabel.setText("Retype Password : ");
        pwConfirmArea = new JPasswordField();
        
        fields = new JPanel(new GridLayout(3, 2));
        fields.add(userLabel);
        fields.add(userArea);
        fields.add(pwLabel);
        fields.add(pwArea);
        
        loginButton = new JButton("Log In");
        loginButton.addActionListener(new LoginButtonHandler());
        registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonHandler());
        switchButton = new JButton("");
        switchButton.addActionListener(new SwitchButtonHandler());
        
        this.add(fields, config);
        
        setLogin();
	}
}
