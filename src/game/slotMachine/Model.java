package game.slotMachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.Timer;

import utils.User;

public class Model extends game.Model{
	Timer timer;
	int slotOne, slotTwo, slotThree, result, bet;
	// A constructor with the user as argument
	public Model(User user) {
		super(user);
	}
	
	// A method to check the result of slot machine
	private int checkSlots() {
		if(slotOne == slotTwo && slotTwo == slotThree) return 10 + slotOne;
		if(slotOne == slotTwo) return slotOne;
		if(slotTwo == slotThree) return slotTwo;
		if(slotOne == slotThree) return slotThree;
		return -1;
	}
	
	// A method to show the result of slot machine
	private void showResult() {
		result = checkSlots();
		int earned = 0;
		if(result == -1) 
			pcs.firePropertyChange("msg", null, "Oof, it's a lost\nYou lost " + bet + " dollar(s).\n");
		else if(result == 1) {
			pcs.firePropertyChange("msg", null, "You got 2 lucky sevens and won 50 times your bet!\n");
			user.addMoney(51 * bet);
			earned = 50 * bet;
		}
		else if(result == 2) {
			pcs.firePropertyChange("msg", null, "You got 2 diamonds and won 10 times your bet!\n");
			user.addMoney(11 * bet);
			earned = 10 * bet;
		}
		else if(result < 10) {
			pcs.firePropertyChange("msg", null, "You got 2 same symbol won the same as your bet!\n");
			user.addMoney(2 * bet);
			earned = bet;
		}
		else {
			result -= 10;
			if(result == 1) {
				pcs.firePropertyChange("msg", null, "JACKPOT!! You won 200 times your bet!!\n");
				user.addMoney(201 * bet);
				earned = 200 * bet;
			}
			else if(result == 2) {
				pcs.firePropertyChange("msg", null, "3 diamonds in a row! You won 50 times your bet!!\n");
				user.addMoney(51 * bet);
				earned = 50 * bet;
			}
			else {
				pcs.firePropertyChange("msg", null, "3 in a row! You won 10 times your bet!!\n");
				user.addMoney(11 * bet);
				earned = 10 * bet;
			}
		}
		if(result != -1) pcs.firePropertyChange("msg", null, "You won "+ earned + " dollar(s)\n");
		pcs.firePropertyChange("status", false, true);
		pcs.firePropertyChange("msg", null, "If you want to play again, just place another bet.\n");
	}
	
	// The timer to start animation of dice roll
	private class RandomSlotHandler implements ActionListener {
		int oneHit = 0;
		int twoHit = 0;
		int threeHit = 0;
		@Override
		public void actionPerformed(ActionEvent e) {
			if(oneHit == 2 && twoHit == 2 && threeHit == 2) {
				timer.stop();
				showResult();
			}
			if(oneHit != 2) {
				result = (int) (Math.random() * 6 + 1);
				if(result == slotOne) oneHit++;
				pcs.firePropertyChange("slotOne", null, result);
			}
			if(twoHit != 2) {
				result = (int) (Math.random() * 6 + 1);
				if(result == slotTwo) twoHit++;
				pcs.firePropertyChange("slotTwo", null, result);
			}
			if(threeHit != 2) {
				result = (int) (Math.random() * 6 + 1);
				if(result == slotThree) threeHit++;
				pcs.firePropertyChange("slotThree", null, result);
			}
		}
	}
	
	/* A method to decide final result
	 * 5% for a 7 symbol
	 * 15% for a diamond symbol
	 * 20% for the rest
	 */
	private int randMap(int num) {
		if(num <= 5) return 1;
		if(num <= 20) return 2;
		if(num <= 40) return 3;
		if(num <= 60) return 4;
		if(num <= 80) return 5;
		return 6;
	}
	
	// Roll the dice until valid result
	public void rollSlot() {
		slotOne = randMap((int) (Math.random() * 100 + 1));
		slotTwo = randMap((int) (Math.random() * 100 + 1));
		slotThree = randMap((int) (Math.random() * 100 + 1));
		timer = new Timer(100, new RandomSlotHandler());
		timer.start();
	}
	
	// Game initialization
	public void startGame(String betValue) {
		try {
			bet = Integer.parseInt(betValue);
			user.subMoney(bet);
			pcs.firePropertyChange("init", null, "Bet placed, value : " + bet + "\nPress roll button to start!\n");
		} catch (NumberFormatException e){
			pcs.firePropertyChange("err", null, "Wrong format!");
		}
	}
}

