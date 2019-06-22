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
        money = 1000; // XXX for testing
        this.name = new String(name);
        this.pw = new String(pw);
    }

    public String getName() {
        return new String(name);
    }

    public void addMoney(int moneyDiff) {
        //if (moneyDiff < 0)
        //    throw new RuntimeException("System Error.");
        setMoney(this.money + moneyDiff);
    }
    public void subMoney(int moneyDiff) {
        if (!(moneyDiff > 0))
            throw new RuntimeException("This number should be positive");
        setMoney(this.money - moneyDiff);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        if (money < 0)
            throw new NegativeMoneyException();
        this.money = money;
        pcs.firePropertyChange("money", null, money);
    }
    
    class NegativeMoneyException extends RuntimeException {
        NegativeMoneyException() {
            super("You don't have enough money.");
        }
    }
}
