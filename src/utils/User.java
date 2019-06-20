package utils;

public class User {
	public String name;
	private String pw;
	private int money;
	
	public User(String name, String pw) {
		money = 0;
		this.name = name;
		this.pw = pw;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
}
