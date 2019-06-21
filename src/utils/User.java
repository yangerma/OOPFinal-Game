package utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class User {
    private String name;
    private String pw;
    private int money;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public User(String name, String pw) {
        money = 0;
        this.name = new String(name);
        this.pw = new String(pw);
    }

    public String getName() {
        return new String(name);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        pcs.firePropertyChange("money", -1, money);
    }
}
