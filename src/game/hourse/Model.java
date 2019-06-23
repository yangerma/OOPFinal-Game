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
	public void subtract(int money) {
		user.subMoney(money);
	}
	public void check(int horse, int guess, int money, JLabel label) {
		try {
			if (horse == guess) {
				setMoney(getMoney()+money*6);
				label.setText("Horse "+Integer.valueOf(horse).toString()+" wins! You get $"+Integer.valueOf(money*5).toString());
			}
			else {
				user.subMoney(money);
				label.setText("Horse "+Integer.valueOf(horse).toString()+" wins! You lose your money");
			}
		}catch(Exception err) {
			label.setText("You lose!! You don't have enogh money!!");
			setMoney(0);
		}
		
	}
}
