package game.buttonGrowth;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;

import javax.swing.JButton;
//import javax.swing.JPanel;

public class View extends game.View {
	//private static final long serialVersionUID = 1L;

	JButton game1Button;
	JButton game2Button;
	JButton game3Button;
	GridBagConstraints config = new GridBagConstraints();
	Model model;

	public View(Model model) {
		super(model);
		this.model = model;
		setLayout(new GridBagLayout());
		config.anchor = GridBagConstraints.CENTER;
		config.gridwidth = GridBagConstraints.REMAINDER;
        game1Button = new JButton("Game 1");
        game1Button.addActionListener(new ButtonHandler());
        
        game2Button = new JButton("Game 2");
        game2Button.addActionListener(new ButtonHandler());
        
        game3Button = new JButton("Game 3");
        game3Button.addActionListener(new ButtonHandler());
        
		add(game1Button, config);
		add(game2Button, config);
		add(game3Button, config);
	}
	public void propertyChange(PropertyChangeEvent evt) {
		
	}
	private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	//View.this.model.click()
        	String command = "You pressed " + e.getActionCommand();
        	JButton newOne = new JButton(command);
        	newOne.addActionListener(new ButtonHandler());
        	View.this.add(newOne, View.this.config);
        	View.this.repaint();
        	View.this.revalidate();
        	
        	View.this.remove((JButton) e.getSource());
        	
        	View.this.repaint();
        	View.this.revalidate();
        }
    }
	
}
