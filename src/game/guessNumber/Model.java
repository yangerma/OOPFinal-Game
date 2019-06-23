package game.guessNumber;

import java.util.ArrayList;
import java.util.Collections;

import utils.myRandom;
import utils.User;

class InvalidBetException extends Exception {
	public InvalidBetException(String msg) {
		super(msg);
	}
}

public class Model extends game.Model {
	Integer myAnswer;
	private final static int MAX = 10000;
	private static final ArrayList<Integer> validNumbers = new ArrayList<Integer>(MAX);
	private ArrayList<Integer> possibleAnswers;
	private Turn turn;
	private Integer bet;
	private Integer lastGuess;
	
	private enum Turn {
		PLAYER, COMPUTER;
	}
	
		
	static {
		for(int i=0; i<MAX; i++) {
			try {
				checkNumber(i);
				validNumbers.add(i);
			} catch(RuntimeException e) {}
		}
	}
	
	private static void checkNumber(int a) {
		if(a < 0 || a>= MAX)
			throw new RuntimeException("Number out of range.");
		
		int[] digits = new int[4];
		for(int i=0; i<4; i++) {
			digits[i] = a%10;
			for(int j=0; j<i; j++)
				if(digits[i] == digits[j])
					throw new RuntimeException("Duplicated digit.");
			a/=10;
		}
	}
	
	private static void checkResponse(String response) {
		if(!response.matches("[01234]A[01234]B"))
			throw new RuntimeException("Invalid response.");
	}
	
	public Model(User user) {
		super(user);
	}
	
	void newGame(String msg) {
		try {
			bet = Integer.parseInt(msg);
		} catch(Exception e) {
			throw new RuntimeException("Your bet is not a number.");
		}
		user.subMoney(bet);
		
		myAnswer = validNumbers.get(myRandom.randInt(0, validNumbers.size()-1));
		possibleAnswers = new ArrayList<Integer>(MAX);
		possibleAnswers.addAll(0, validNumbers);
		Collections.shuffle(possibleAnswers);
		turn = Turn.PLAYER;
		lastGuess = null;
		
    	pcs.firePropertyChange(Properties.infoUpdate, "", 
    			"You go first!\nGuess a Number.\n");
	}
	
	private String compare(int x, int y) {
		checkNumber(x);
		checkNumber(y);
		int[] digitX = new int[4];
		int[] digitY = new int[4];
		for(int i=0; i<4; i++) {
			digitX[i] = x%10;
			digitY[i] = y%10;
			x/=10;
			y/=10;
		}
		int a=0, b=0;
		for(int i=0; i<4; i++)
			if(digitX[i] == digitY[i])
				a++;
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++)
				if(i!=j && digitX[i] == digitY[j]) {
					b++;
					break;
				}
		return String.format("%dA%dB", a, b);
	}
	
	private void guess() {
		lastGuess = possibleAnswers.get(0);
		pcs.firePropertyChange(Properties.infoAppend, "", 
				String.format("Now it's my turn.\nWhat's the result of %04d?\n", 
						lastGuess));
	}
	
	private void updatePossible(String response) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(Integer val : possibleAnswers) {
			if(response.equals(compare(val, lastGuess)))
				temp.add(val);
		}
		possibleAnswers = temp;
	}
	
	void playerWin() {
		pcs.firePropertyChange(Properties.infoUpdate, "",
				String.format("Contrats, you win!\n"
						+ "You get %d dollars in this game.\n", bet));
		user.addMoney(2*bet);
		pcs.firePropertyChange(Properties.button, "", "restart");
	}
	
	void playerLose() {
		pcs.firePropertyChange(Properties.infoUpdate, "",
				String.format("Oops. Seems like I win :D\n"
						+ "You lose your bet of %d dollars.\n", bet));
		pcs.firePropertyChange(Properties.button, "", "restart");
	}
	
	void cheat() {
		pcs.firePropertyChange(Properties.infoUpdate, "",
				String.format("You Cheat!\nNo number corresponds to your responses.\n"
						+ "You lose your bet of %d dollars.\n", bet));
		pcs.firePropertyChange(Properties.button, "", "restart");
	}
	
	
	void submit(String msg) {
		switch(turn) {
		case PLAYER:
			Integer guess = Integer.parseInt(msg);
			if(guess.equals(myAnswer)) {
				playerWin();
				break;
			}
			String result = compare(guess, myAnswer);
			pcs.firePropertyChange(Properties.infoUpdate, "",
					String.format("Result for guess %s is %s.\n", msg, result));
			pcs.firePropertyChange(Properties.playerGuess, "", 
					String.format("%04d -> %s\n", guess, result));
			turn = Turn.COMPUTER;
			guess();
			break;
		case COMPUTER:
			checkResponse(msg);
			updatePossible(msg);
			if(possibleAnswers.size() == 0) {
				cheat();
				pcs.firePropertyChange(Properties.computerGuess, "", 
						String.format("%04d -> %s (...!!?)\n", lastGuess, msg));
				break;
			}
			pcs.firePropertyChange(Properties.computerGuess, "", 
					String.format("%04d -> %s\n", lastGuess, msg));
			if(msg.equals("4A0B")) {
				playerLose();
				break;
			}
			pcs.firePropertyChange(Properties.infoUpdate, "",
					"Got it.\nIt's your turn.\nGuess a Number.\n");
			turn = Turn.PLAYER;
			break;
		}
	}
}
