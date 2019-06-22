package game.hourse;

import java.util.Random;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class View extends game.View implements ActionListener,ChangeListener{
	JButton start;
	GridBagConstraints config = new GridBagConstraints();
	JProgressBar hourseOne;
	JProgressBar hourseTwo;
	JProgressBar hourseThree;
	JProgressBar hourseFour;
	Timer timerOne;
	Timer timerTwo;
	Timer timerThree;
	Timer timerFour;
	JLabel info;
	JSpinner inputMoney;
	int counter = 0;
	Random ran = new Random();
	Model model;
	
	public View(Model model) {
		super(model);
		this.model = model;
		setLayout(new GridBagLayout());
		config.anchor = GridBagConstraints.CENTER;
		config.gridwidth = GridBagConstraints.REMAINDER;
		start = new JButton("start!");
		start.addActionListener(this);
		info = new JLabel();
		hourseOne = new JProgressBar();
		setHourse(hourseOne, 1);
		hourseTwo = new JProgressBar();
		setHourse(hourseTwo, 2);
		hourseThree = new JProgressBar();
		setHourse(hourseThree, 3);
		hourseFour = new JProgressBar();
		setHourse(hourseFour, 4);
		timerOne=new Timer(100, this);
		timerTwo=new Timer(100, this);
		timerThree=new Timer(100, this);
		timerFour=new Timer(100, this);
		inputMoney = new JSpinner();
		
		add(inputMoney, config);
		add(start, config);
		add(hourseOne, config);
		add(hourseTwo, config);
		add(hourseThree, config);
		add(hourseFour, config);
		add(info, config);
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		
	}
	
	void setHourse(JProgressBar progressbar, int color) {
		progressbar.setOrientation(JProgressBar.HORIZONTAL);
		progressbar.setMinimum(0);
		progressbar.setMaximum(100);
		progressbar.setValue(0);
		progressbar.setStringPainted(true);
		progressbar.addChangeListener(this);
		progressbar.setPreferredSize(new Dimension(300,20));
		progressbar.setBorderPainted(true);
		switch(color) {
		case 1:
			progressbar.setBackground(Color.pink);
			break;
		case 2:
			progressbar.setBackground(Color.blue);
			break;
		case 3:
			progressbar.setBackground(Color.green);
			break;
		default:
			progressbar.setBackground(Color.red);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==start){
			start.setVisible(false);
			inputMoney.setVisible(false);
			info.setText("");
			hourseOne.setValue(0);
			hourseTwo.setValue(0);
			hourseThree.setValue(0);
			hourseFour.setValue(0);
			timerOne.start();
			timerTwo.start();
			timerThree.start();
			timerFour.start();
			
		}
		if(e.getSource()==timerOne){
			int value = hourseOne.getValue();
			if(value<100)
				hourseOne.setValue(value+=ran.nextInt(3));
			else{
				timerOne.stop();
			}
		}
		if(e.getSource()==timerTwo){
			int value = hourseTwo.getValue();
			if(value<100)
				hourseTwo.setValue(value+=ran.nextInt(3));
			else{
				timerTwo.stop();
			}
		}
		if(e.getSource()==timerThree){
			int value = hourseThree.getValue();
			if(value<100)
				hourseThree.setValue(value+=ran.nextInt(3));
			else{
				timerThree.stop();
			}
		}
		if(e.getSource()==timerFour){
			int value = hourseFour.getValue();
			if(value<100)
				hourseFour.setValue(value+=ran.nextInt(3));
			else{
				timerFour.stop();
			}
		}
	}
	
	public void stateChanged(ChangeEvent e1) {
		if(e1.getSource() == hourseOne){
			if (hourseOne.getValue() == 100) {
				if(counter == 0) {
					info.setText("Hourse 1 wins!");
				}
				counter += 1;
			}
		}
		if(e1.getSource() == hourseTwo){
			if (hourseTwo.getValue() == 100) {
				if(counter == 0) {
					info.setText("Hourse 2 wins!");
				}
				counter += 1;
			}
		}
		if(e1.getSource() == hourseThree){
			if (hourseThree.getValue() == 100) {
				if(counter == 0) {
					info.setText("Hourse 3 wins!");
				}
				counter += 1;
			}
		}
		if(e1.getSource() == hourseFour){
			if (hourseFour.getValue() == 100) {
				if(counter == 0) {
					info.setText("Hourse 4 wins!");
				}
				counter += 1;
			}
		}
		if(counter == 4) {
			counter = 0;
			start.setVisible(true);
		}
	}
}
