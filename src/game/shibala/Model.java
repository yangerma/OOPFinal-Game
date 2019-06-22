package game.shibala;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import utils.User;

public class Model extends game.Model{
	Timer timer;
	int dieOne, dieTwo, dieThree;
	
	public Model(User user) {
		super(user);
	}
	
	private class RandomDieHandler implements ActionListener {
		int oneHit = 0;
		int twoHit = 0;
		int threeHit = 0;
		int result;
		@Override
		public void actionPerformed(ActionEvent e) {
			if(oneHit == 2 && twoHit == 2 && threeHit == 2) {
				timer.stop();
				return;
			}
			if(oneHit != 2) {
				result = (int) (Math.random() * 6 + 1);
				if(result == dieOne) oneHit++;
				pcs.firePropertyChange("dieOne", null, result);
			}
			if(twoHit != 2) {
				result = (int) (Math.random() * 6 + 1);
				if(result == dieTwo) twoHit++;
				pcs.firePropertyChange("dieTwo", null, result);
			}
			if(threeHit != 2) {
				result = (int) (Math.random() * 6 + 1);
				if(result == dieThree) threeHit++;
				pcs.firePropertyChange("dieThree", null, result);
			}
		}
	}
	
	public void rollDice() {
		dieOne = (int) (Math.random() * 6 + 1);
		dieTwo = (int) (Math.random() * 6 + 1);
		dieThree = (int) (Math.random() * 6 + 1);
		timer = new Timer(100, new RandomDieHandler());
		timer.start();
	}
	
	public void startGame(String betValue) {
		try {
			int bet = Integer.parseInt(betValue);
			if(bet == 0) {
				pcs.firePropertyChange("err", null, "You cannot bet 0 dollars!");
				return;
			}
			if(bet > user.getMoney()) {
				pcs.firePropertyChange("err", null, "You don't have enough money!");
				return;
			}
			pcs.firePropertyChange("init", null, "Bet placed, value : " + bet + "\nHere we go!\n");
		} catch (NumberFormatException e){
			pcs.firePropertyChange("err", null, "Wrong format!");
		}
		rollDice();
	}
}
