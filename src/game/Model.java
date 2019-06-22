package game;


import java.beans.*;

import utils.User;

public abstract class Model {
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected User user;

	public Model(User user) {
	    this.user = user;
	}

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }
}
