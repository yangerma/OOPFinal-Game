package utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.Main;


public class User {
    private String name;
    private String pw;
    private int money;
    private Connection connection;

    private static final int initialMoney = 1000;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
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
    private void startConnection() {
    	try {
    		// create a database connection
    		connection = DriverManager.getConnection(Main.url);
    	} catch (SQLException e) {
    		System.err.println(e.getMessage());
    	}
    }
    private void closeConnection() {
    	try {
    		if (connection != null)
    			connection.close();
    	} catch (SQLException e) {
    		System.err.println(e.getMessage());
    	}
    }
    
    public static User login(String username, String password) {
    	User user =  new User();
    	user.startConnection();
    	try {
			String command = "SELECT * FROM users WHERE name = ?";
			PreparedStatement pstmt = user.connection.prepareStatement(command);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if(!rs.next())
				throw new RuntimeException("Wrong username or password.");
			if(!password.contentEquals(rs.getString("password")))
				throw new RuntimeException("Wrong username or password.");
			user.name = rs.getString("name");
			user.money = rs.getInt("money");
		} catch (SQLException e) {
			System.err.println(e);
		}
    	return user;
    }
    
    public static void register(String username, String password) {
    	try {
    		Connection conn = DriverManager.getConnection(Main.url);
			String command = "SELECT * FROM users WHERE name = ?";
			PreparedStatement pstmt = conn.prepareStatement(command);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				throw new RuntimeException("This username has already been used.");
			command = "INSERT INTO users VALUES (?, ?, ?)";
			pstmt = conn.prepareStatement(command);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setInt(3, initialMoney);
			pstmt.executeUpdate();
    	} catch (SQLException e) {
			System.err.println(e);
    	}
    }
    
    
    public void logout() {
    	closeConnection();
    }
    
    class NegativeMoneyException extends RuntimeException {
        NegativeMoneyException() {
            super("You don't have enough money.");
        }
    }
}
