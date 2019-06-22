package game;

import java.util.*;

import utils.User;

public class gameFinder {
	//private final String gameNames = {"Button Growth Game", "Shi Ba La", "Hourse Game"};
	private final String buttonGrowth = "Button Growth Game";
	private final String shibala = "Shi Ba La";
	private final String hourse = "Hourse Game";
	private final String guessNumber = "Guess Number";
	List<String> nameList = new ArrayList<String>();
    User user;
    public gameFinder(User user) {
        this.user = user;
        
        nameList.add(buttonGrowth);
        nameList.add(shibala);
        nameList.add(hourse);
        nameList.add(guessNumber);
    }
    
    public List<String> gameNames() {
        return new ArrayList<String>(nameList);
    }
    
    public View findGame(String name) {
        switch (name) {
        case buttonGrowth :
            return new game.buttonGrowth.View(new game.buttonGrowth.Model(user));
        case shibala:
            return new game.shibala.View(new game.shibala.Model(user));
        case hourse:
        	return new game.hourse.View(new game.hourse.Model(user));
        case guessNumber:
        	return new game.guessNumber.View(new game.guessNumber.Model(user));
        }
        assert(false);
        return null;
    }
}
