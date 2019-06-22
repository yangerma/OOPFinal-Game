package game.hourse;

import utils.User;

import javax.swing.JLabel;

public class Model extends game.Model {
	public Model(User user) {
        super(user);
    }
	public int getMoney() {
		return user.getMoney();
	}
	public void setMoney(int money) {
		user.setMoney(money);
	}
	public void check(int horse, int guess, int money, JLabel label) {
		if (horse == guess) {
			setMoney(getMoney()+money*10);
			label.setText("Horse "+Integer.valueOf(horse).toString()+" wins! You get $"+Integer.valueOf(money*10).toString());
		}
		else {
			label.setText("Horse "+Integer.valueOf(horse).toString()+" wins! You lose your money");
		}
	}
}
