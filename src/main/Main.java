package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

import utils.Login;
import utils.User;
import utils.UserView;

import java.io.File;

public class Main implements PropertyChangeListener {
	static JFrame window;
	static boolean loggedIn = false;
	static Login loginPage;
	private static final String dbPathTail = File.separator + "sqlite" 
			+ File.separator + "db";
	public static String url;
	private static final String tableExistQuery = "SELECT name FROM sqlite_master WHERE "
			+ "type='table' AND name='users'";
	private static final String createTable = "CREATE TABLE users ("
			+ "`name` TEXT NOT NULL,"
			+ "`password` TEXT NOT NULL,"
			+ "`money` INT NOT NULL)";
	
    public static void createNewDatabase() {
    	String path = System.getProperty("user.home") + dbPathTail;
    	File dir = new File(path);
    	dir.mkdirs();
    	
    	url = "jdbc:sqlite:"+dir+ File.separator+"gameUsers.db";
        try(
        	Connection conn = DriverManager.getConnection(url);
        	Statement statement = conn.createStatement();
        ) {
        	statement.setQueryTimeout(5);
        	try(ResultSet res = statement.executeQuery(tableExistQuery)) {
            	if(!res.next())
            		statement.execute(createTable);
        	}
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
	
	public static void main(String[] args) {
		createNewDatabase();
		Main index = new Main();
        window = new JFrame();
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPage = new Login();
        loginPage.addPropertyChangeListener(index);
       	window.add(loginPage, BorderLayout.CENTER);
       	window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		loggedIn = true;
		window.remove(loginPage);
		User user = (User)evt.getNewValue();
		window.add(new UserView(user), BorderLayout.NORTH);
		window.add(new Controller(user), BorderLayout.CENTER);
		window.repaint();
		window.revalidate();
	}
}
