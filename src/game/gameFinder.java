package game;

import java.util.*;

import utils.User;

public class gameFinder {
    List<String> nameList = new ArrayList<String>();
    User user;
    public gameFinder(User user) {
        this.user = user;
        
        // Button Growth Game
        nameList.add("Button Growth Game");
        nameList.add("Shi Ba La");
    }
    
    public List<String> gameNames() {
        return new ArrayList<String>(nameList);
    }
    
    public View findGame(String name) {
        switch (name) {
        case "Button Growth Game" :
            return new game.buttonGrowth.View(new game.buttonGrowth.Model(user));
        case "Shi Ba La" :
            return new game.shibala.View(new game.shibala.Model(user));
        }
        assert(false);
        return null;
    }
}
