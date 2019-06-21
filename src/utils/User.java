package utils;

public class User {
	private String name;
	private String pw;
	private int money;
	
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
	}
}
