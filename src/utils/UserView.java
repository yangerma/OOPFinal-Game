package utils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserView extends JPanel implements PropertyChangeListener {
    User user;
    JLabel text;

    public UserView(User user) {
        this.user = user;
        user.addPropertyChangeListener(this);
        text = new JLabel();
        text.setText(statusString());
        add(text);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        text.setText(statusString());
    }

    private String statusString() {
        return "Welcome, " + user.getName() + ". You currently have $" + user.getMoney() + " left.";
    }
}
