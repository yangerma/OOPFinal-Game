package game.guessNumber;

import java.util.ArrayList;
import java.util.Collections;

import utils.IllegalBoundException;
import utils.myRandom;
import utils.User;

class InvalidNumberException extends Exception {
	public InvalidNumberException(String msg) {
		super(msg);
	}
	public InvalidNumberException() {
		super("Not a valid number.");
	}
}

class InvalidBetException extends Exception {
	public InvalidBetException(String msg) {
		super(msg);
	}
}

public class Model extends game.Model {
	Integer myAnswer;
	private final static int MAX = 10000;
	private static ArrayList<Integer> validNumbers = new ArrayList<Integer>(MAX);
	private ArrayList<Integer> possibleAnswers;
		
	static {
		for(int i=0; i<MAX; i++) {
			try {
				check(i);
				validNumbers.add(i);
			} catch(InvalidNumberException e) {}
		}
	}
	
	private static void check(int a) throws InvalidNumberException {
		if(a < 0 || a>= MAX)
			throw new InvalidNumberException("Number out of range.");
		
		int[] digits = new int[4];
		for(int i=0; i<4; i++) {
			digits[i] = a%10;
			for(int j=0; j<i; j++)
				if(digits[i] == digits[j])
					throw new InvalidNumberException("Duplicated digit.");
			a/=10;
		}
	}
	
	public Model(User user) {
		super(user);
	}
	
	public void newGame(String bet) {
		Integer betInt = null;
		try {
			betInt = Integer.parseInt(bet);
		} catch(Exception e) {
			throw new RuntimeException("Your bet is not a number.");
		}
		user.subMoney(betInt);
		
		try {
			myAnswer = validNumbers.get(myRandom.randInt(0, validNumbers.size()-1));
		} catch(IllegalBoundException e) {}
		possibleAnswers = new ArrayList<Integer>(MAX);
		possibleAnswers.addAll(0, validNumbers);
		Collections.shuffle(possibleAnswers);
	}
	
	private String compare(int x, int y) throws InvalidNumberException {
		check(x);
		check(y);
		int[] digitX = new int[4];
		int[] digitY = new int[4];
		for(int i=0; i<4; i++) {
			digitX[i] = x%i;
			digitY[i] = y%i;
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
	
	private Integer guess() {
		return guess();
	}
}
