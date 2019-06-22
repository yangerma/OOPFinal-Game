package utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

public class UserView extends JPanel implements PropertyChangeListener {
    User user;
    JLabel text;
    JButton moreMoney;

    public UserView(User user) {
        this.user = user;
        user.addPropertyChangeListener(this);
        text = new JLabel();
        text.setText(statusString());
        add(text);
        add(new JLabel("Need more money?"));
        
        moreMoney = new JButton("click me");
        moreMoney.addActionListener(new AddMoneyHandler());
        add(moreMoney);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        text.setText(statusString());
    }

    private String statusString() {
        return "Welcome, " + user.getName() + ". You currently have $" + user.getMoney() + " left.";
    }
    
    private class AddMoneyHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String res = JOptionPane.showInputDialog("How much money do you want to add?");
            int moneyDiff = 0;
            try {
                moneyDiff = Integer.parseInt(res);
            } catch(NumberFormatException exc) {
                JOptionPane pane = new JOptionPane("Your input \"" + res + "\" is not a valid number.");
                JDialog dialog = pane.createDialog("Input not a number");
                dialog.add(new JLabel("~~~"));
                dialog.setVisible(true);
                return;
            }
            user.addMoney(moneyDiff);
        }
        
    }
}
