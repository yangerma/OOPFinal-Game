package game.shibala;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.Timer;

import utils.User;

public class Model extends game.Model{
	Timer timer;
	boolean playerTurn = false;
	int dieOne, dieTwo, dieThree, comResult, bet;
	// A constructor with the user as argument
	public Model(User user) {
		super(user);
	}
	
	// A method to check if result is valid
	private int checkDice() {
		int[] results = {dieOne, dieTwo, dieThree};
		int[] bg = {1, 2, 3};
		int[] ffs = {4, 5, 6};
		Arrays.sort(results);
		if(Arrays.equals(bg, results)) return 0;
		if(Arrays.equals(ffs, results)) return 10;
		if(dieOne == dieTwo && dieTwo == dieThree) return 10 + dieOne;
		if(dieOne == dieTwo) return dieThree;
		if(dieTwo == dieThree) return dieOne;
		if(dieOne == dieThree) return dieTwo;
		return -1;
	}
	
	// A method to show the result of dice roll on computer's turn
	private void showResult1(int result) {
		if(result == 0) pcs.firePropertyChange("msg", null, "Oof, I rolled a 1, 2, 3 :(\n");
		else if(result == 10) pcs.firePropertyChange("msg", null, "Wow, I rolled a 4, 5, 6!\n");
		else if(result > 10) pcs.firePropertyChange("msg", null, "Woohoo, it's a triple " + (result - 10) +"!\n");
		else pcs.firePropertyChange("msg", null, "Well, I rolled a " + result + ".\n");
		comResult = result;
		pcs.firePropertyChange("status", false, true);
		playerTurn = true;
	}
	
	// A method to show the result of dice roll on player's turn
	private void showResult2(int result) {
		if(result == 0) pcs.firePropertyChange("msg", null, "Oof, you rolled a 1, 2, 3 :(\n");
		else if(result == 10) pcs.firePropertyChange("msg", null, "Wow, you rolled a 4, 5, 6!\n");
		else if(result > 10) pcs.firePropertyChange("msg", null, "Oh my god, it's a triple " + (result - 10) +"!\n");
		else pcs.firePropertyChange("msg", null, "Well, you rolled a " + result + ".\n");
		pcs.firePropertyChange("status", true, false);
		playerTurn = false;
		if(result > comResult) {
			pcs.firePropertyChange("msg", null, "That means you beat me, congradulations!\nYou earned " + bet + " dollar(s).\n");
			user.addMoney(2 * bet);
		}
		else if(result < comResult) pcs.firePropertyChange("msg", null, "That means you lost...\nYou lost " + bet + " dollar(s).\n");
		else{
			pcs.firePropertyChange("msg", null, "It's a tie!\n");
			user.addMoney(bet);
		}
		pcs.firePropertyChange("msg", null, "If you want to play again, just place another bet.\n");
	}
	
	// The timer to start animation of dice roll
	private class RandomDieHandler implements ActionListener {
		int oneHit = 0;
		int twoHit = 0;
		int threeHit = 0;
		int result;
		@Override
		public void actionPerformed(ActionEvent e) {
			if(oneHit == 2 && twoHit == 2 && threeHit == 2) {
				timer.stop();
				int result = checkDice();
				if(result == -1) {
					pcs.firePropertyChange("msg", null, "Oops, nothing, let's try again.\n");
					if(!playerTurn) rollDice();
				}
				else if(!playerTurn) showResult1(result);
				else showResult2(result);
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
	
	// Roll the dice until valid result
	public void rollDice() {
		dieOne = (int) (Math.random() * 6 + 1);
		dieTwo = (int) (Math.random() * 6 + 1);
		dieThree = (int) (Math.random() * 6 + 1);
		timer = new Timer(100, new RandomDieHandler());
		timer.start();
	}
	
	// Game initialization
	public void startGame(String betValue) {
		try {
			bet = Integer.parseInt(betValue);
			user.subMoney(bet);
			pcs.firePropertyChange("init", null, "Bet placed, value : " + bet + "\nHere we go!\n");
			rollDice();
		} catch (NumberFormatException e){
			pcs.firePropertyChange("err", null, "Wrong format!");
		}
	}
}
