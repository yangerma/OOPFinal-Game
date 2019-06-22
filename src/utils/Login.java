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
	
	JLabel userLabel, pwLabel;
	JTextField userArea, pwArea;
	JButton loginButton;
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		this.support.addPropertyChangeListener(pcl);
	}
	
	private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	User user = new User(userArea.getText(), pwArea.getText());
        	support.firePropertyChange("user", 0, user);
        }
    }
	
	public Login() {
		setLayout(new GridBagLayout());
		support = new PropertyChangeSupport(this);
		
		GridBagConstraints config = new GridBagConstraints();
		config.anchor = GridBagConstraints.CENTER;
		config.gridwidth = GridBagConstraints.REMAINDER;
        
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
        this.add(fields, config);
        this.add(loginButton, config);
	}
}
