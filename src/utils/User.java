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

import jbcrypt.*;


public class User {
    private String name;
    private int money;
    //private Connection connection;

    private static final int initialMoney = 1000;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    User(String name, int money) {
    	this.name = name;
    	this.money = money;
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
        try (Connection conn = DriverManager.getConnection(Main.url);
        		Statement stmt = conn.createStatement()){
            stmt.setQueryTimeout(5);
            stmt.executeUpdate(String.format("UPDATE users SET money=%d WHERE name='%s'", this.money, this.name));	
        } catch(SQLException e) {
    		System.err.println(e.getMessage());
        }
        pcs.firePropertyChange("money", null, money);
    }
    
    public static User login(String username, String password) {
    	User user = null;
    	//user.startConnection();
		String command = "SELECT * FROM users WHERE name = ?";
    	try (
    		Connection conn = DriverManager.getConnection(Main.url);
    		PreparedStatement pstmt = conn.prepareStatement(command);
    	) {
			pstmt.setString(1, username);
			try (ResultSet rs = pstmt.executeQuery()) {
				if(!rs.next())
					throw new RuntimeException("Wrong username or password.");
				if(!BCrypt.checkpw(password, rs.getString("password")))
					throw new RuntimeException("Wrong username or password.");
				user = new User(rs.getString("name"), rs.getInt("money"));
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
    	return user;
    }
    
    public static void register(String username, String password) {
		String command = "SELECT * FROM users WHERE name = ?";
    	try (
    		Connection conn = DriverManager.getConnection(Main.url);
    		PreparedStatement pstmt = conn.prepareStatement(command);		
    	) {
			pstmt.setString(1, username);
			try (ResultSet rs = pstmt.executeQuery()) {
				if(rs.next())
					throw new RuntimeException("This username has already been used.");
			}
			command = "INSERT INTO users VALUES (?, ?, ?)";
			String passwordHashed = BCrypt.hashpw(password, BCrypt.gensalt());
			try(PreparedStatement updatestmt = conn.prepareStatement(command)) {
				updatestmt.setString(1, username);
				updatestmt.setString(2, passwordHashed);
				updatestmt.setInt(3, initialMoney);
				updatestmt.executeUpdate();
			}
    	} catch (SQLException e) {
			System.err.println(e);
			throw new RuntimeException("Database error: Register unsuccesful.");
    	}
    }
    
    class NegativeMoneyException extends RuntimeException {
        NegativeMoneyException() {
            super("You don't have enough money.");
        }
    }
}
