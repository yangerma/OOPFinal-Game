package game.slotMachine;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import game.slotMachine.Model;

public class View extends game.View{
	JButton rollSlots, setBet;
	JTextArea messages;
	NumberFormat intFormat;
	JFormattedTextField betArea;
	SlotsPanel slotsPanel;
	Model model;
	GridBagConstraints config = new GridBagConstraints();
	
	// The panel that shows the slots
	private class SlotsPanel extends JPanel {
		ImageIcon[] allSlots = new ImageIcon[7];
		JLabel slotOne, slotTwo, slotThree;
		
		SlotsPanel() {
			setLayout(new GridBagLayout());
			config.insets = new Insets(0, 20, 0, 20);
			
			for(int i = 1; i < 7; i++) {
				allSlots[i] = new ImageIcon(getClass().getResource("/res/slot" + i + ".png"));
			}
			slotOne = new JLabel(allSlots[1]);
			slotTwo = new JLabel(allSlots[1]);
			slotThree = new JLabel(allSlots[1]);
			
			this.add(slotOne, config);
			this.add(slotTwo, config);
			config.gridwidth = GridBagConstraints.REMAINDER;
			this.add(slotThree, config);
		}
		
		public void setSlotOne(int which) {
			slotOne.setIcon(allSlots[which]);
			this.repaint();
			this.revalidate();
		}
		public void setSlotTwo(int which) {
			slotTwo.setIcon(allSlots[which]);
			this.repaint();
			this.revalidate();
		}
		public void setSlotThree(int which) {
			slotThree.setIcon(allSlots[which]);
			this.repaint();
			this.revalidate();
		}
	}
	
	// Handler when user presses "Roll!"
	private class RolledHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			try {
				model.rollSlot();
			} catch (RuntimeException e) {
				showMessage(e.getMessage());
			}
		}
	}
	
	// Handler when user places bet
	private class BetPlacedHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			try {
				setBet.setEnabled(false);
				model.startGame(betArea.getText());
				rollSlots.setEnabled(true);
			} catch (RuntimeException e) {
				showMessage(e.getMessage());
				setBet.setEnabled(true);
			}
		}
	}
	
	public View(Model model) {
		super(model);
		this.model = model;
		setLayout(new GridBagLayout());
		
		// Setting up the UI
		slotsPanel = new SlotsPanel();
		rollSlots = new JButton("Roll!");
		rollSlots.addActionListener(new RolledHandler());
		rollSlots.setEnabled(false);
		intFormat = NumberFormat.getIntegerInstance();
		intFormat.setGroupingUsed(false);
		betArea = new JFormattedTextField(intFormat);
		betArea.setFocusLostBehavior(JFormattedTextField.PERSIST);
		setBet = new JButton("Place Bet");
		setBet.addActionListener(new BetPlacedHandler());
		messages = new JTextArea("Welcome to the slot machine!\n");
		messages.setEditable(false);
		messages.setPreferredSize(new Dimension(1000, 300));
		JScrollPane scrollPane = new JScrollPane();
		
		add(slotsPanel, config);
		add(rollSlots, config);
		JPanel betPanel = new JPanel(new GridLayout(1, 3));
		betPanel.add(new JLabel("Place your bet here : "));
		betPanel.add(betArea);
		betPanel.add(setBet);
		add(betPanel, config);
		add(scrollPane);
		scrollPane.setViewportView(messages);
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		String changed = evt.getPropertyName();
		if(changed == "err") {
			showMessage((String) evt.getNewValue());
			setBet.setEnabled(true);
		}
		else if(changed == "init") messages.setText((String) evt.getNewValue());
		else if(changed == "msg") messages.append((String) evt.getNewValue());
		else if(changed == "slotOne") slotsPanel.setSlotOne((int) evt.getNewValue());
		else if(changed == "slotTwo") slotsPanel.setSlotTwo((int) evt.getNewValue());
		else if(changed == "slotThree") slotsPanel.setSlotThree((int) evt.getNewValue());
		else if((Boolean) evt.getNewValue() == true){
			rollSlots.setEnabled(false);
			setBet.setEnabled(true);
		}
	}
}