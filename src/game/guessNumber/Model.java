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
	
	private enum Turn {
		PLAYER, COMPUTER;
	}
	
		
	static {
		for(int i=0; i<MAX; i++) {
			try {
				check(i);
				validNumbers.add(i);
			} catch(RuntimeException e) {}
		}
	}
	
	private static void check(int a) {
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
	
	public Model(User user) {
		super(user);
	}
	
	void newGame(String bet) {
		Integer betInt = null;
		try {
			betInt = Integer.parseInt(bet);
		} catch(Exception e) {
			throw new RuntimeException("Your bet is not a number.");
		}
		user.subMoney(betInt);
		
		myAnswer = validNumbers.get(myRandom.randInt(0, validNumbers.size()-1));
		possibleAnswers = new ArrayList<Integer>(MAX);
		possibleAnswers.addAll(0, validNumbers);
		Collections.shuffle(possibleAnswers);
		
		turn = Turn.PLAYER;
    	pcs.firePropertyChange(Properties.infoUpdate, "", 
    			"You go first!\nGuess a Number.\n");
	}
	
	private String compare(int x, int y) {
		check(x);
		check(y);
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
	
	String respond(String guess) {
		String result = null;
		try {
			Integer val = Integer.parseInt(guess);
			result = compare(myAnswer, val);
		} catch(Exception e) {}
		return result;
	}
	
	private void guess() {
		pcs.firePropertyChange(Properties.infoAppend, "", 
				String.format("Now it's my turn.\nWhat's the result of %04d?\n", 
						possibleAnswers.get(0)));
	}
	
	void submit(String msg) {
		switch(turn) {
		case PLAYER:
			String result = compare(Integer.parseInt(msg), myAnswer);
			pcs.firePropertyChange(Properties.infoUpdate, "",
					String.format("Result for guess %s is %s.\n", msg, result));
			pcs.firePropertyChange(Properties.playerGuess, "", 
					String.format("%s -> %s\n", msg, result));
			turn = Turn.COMPUTER;
			guess();
			break;
		case COMPUTER:
			
		assert(false);
		}
	}
}
