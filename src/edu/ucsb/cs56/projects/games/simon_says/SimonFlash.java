
package edu.ucsb.cs56.projects.games.simon_says;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/** Flashes a sequence of buttons
 */

public class SimonFlash
 {
     //private ArrayList<Integer>  userButtonPresses; Thought there' be a use for this, apparently not
     private ArrayList<Integer> computerButtonPresses;
     private SimonButton[] buttons; // order: Green Red, Yellow, Blue
     private JButton startButton;
     private JComponent startButtonLocation;
     private int currentButton; 
     private int placeInSequence; // will be zero-based
     
     public static void  FlashSequence(ArrayList<Integer> flashes, SimonButton[] buttons, JButton startButton, JComponent startButtonLocation) {
	 SimonFlash sequence = new SimonFlash(flashes, buttons, startButton, startButtonLocation);
	 sequence.go();
     }

     public SimonFlash() { 
	 //userButtonPresses = new ArrayList<Integer>();
	 computerButtonPresses = new ArrayList<Integer>();
	 buttons = new SimonButton[4];
	 for (int i=0; i<4; i++) {
	     buttons[i] = new SimonButton();
	 }
	 startButton = new JButton();
	 startButtonLocation = new JPanel();
	 currentButton = 0;
     }

     public SimonFlash(ArrayList<Integer> flashes, SimonButton[] buttons, JButton startButton, JComponent startButtonLocation) {
	 //userButtonPresses = new ArrayList<Integer>();
	 //	 this.computerButtonPresses = new ArrayList<Integer>();
	 computerButtonPresses = flashes;
	 // System.out.println(flashes.get(0));
	 //System.out.println(flashes.get(1));
	 //System.out.println(flashes.get(2)+"\n");

	 //computerButtonPresses.addAll(flashes);
	 //Debug
	 //System.out.println(computerButtonPresses.get(0));
	 //System.out.println(computerButtonPresses.get(1));
	 //System.out.println(computerButtonPresses.get(2));
	 this.buttons = buttons;
	 this.currentButton = flashes.get(0);
	 this.startButton = startButton;
	 this.startButtonLocation = startButtonLocation;
     }


     /** Flashes in sequential order a sequence of numbers
     */
     public void go() {

	 
	 new Thread(new Runnable() {
		 public void run() {
		     try {
			 for (SimonButton button : buttons) {
			     button.setEnabled(false);
			     ///   button.removeActionListeners();
			 }
			 for (int button_num : computerButtonPresses) { // iterate through each sequence element
			     Thread.sleep(500);
			     SimonButton button = buttons[button_num]; // for readiblity
			     //System.out.println("hey"); // DEBUG
			     Color buttonColor = button.getBackground();
			     button.setBackground(Color.WHITE);
			     Thread.sleep(250);
			     button.setBackground(buttonColor);
			 }

			 for (SimonButton button : buttons) { // reactivate buttons
			     button.setEnabled(true);
			 }
		     } catch (InterruptedException ex) {ex.printStackTrace();}
	     }
	     }).start();
     
     // Change this to 1 later -  DEBUG
     if (computerButtonPresses.size() == 1 ) {
	 buttons[0].addActionListener(new GreenPushListener()); // listen for inputs
	 buttons[1].addActionListener(new RedPushListener());
	 buttons[2].addActionListener(new YellowPushListener());
	 buttons[3].addActionListener(new BluePushListener());
	 startButton.addActionListener(new StartPushListener());
	 }
}

     private void  lossCheck(int buttonNum) {
	 //userButtonPresses.add(computerButtonPresses.get(currentButton));
	 placeInSequence++;
	 boolean didWeLose = false; // initialization just in case for debug

	 //debug
	 
	 System.out.println("current button: "+currentButton);
	 System.out.println("button number: "+buttonNum);
	 System.out.println("place in sequence: "+placeInSequence);
	 System.out.println("size of computerButtonPresses: "+computerButtonPresses.size());
	 
	 if (currentButton != buttonNum) {
	     didWeLose = true;
	     //System.out.println(computerButtonPresses.get(currentButton)
	     // if (placeInSequence < computerButtonPresses.size())
	     //	 currentButton = computerButtonPresses.get(placeInSequence); 
	     this.endRound(didWeLose); // we lost
	 }
	 else if (placeInSequence >= computerButtonPresses.size()) {
	     //Debug
	     // System.out.println("placeinSequence bigger than computerButtonPresses.size()");
	     didWeLose = false;
	     this.endRound(didWeLose); // we did *not* lose; game continues
	 }
	 else if (currentButton == buttonNum) {
	     //
	     currentButton = computerButtonPresses.get(placeInSequence);
	 }
     }

     private void endRound(boolean didWeLose) {
	 if (didWeLose == true) {
	     for (SimonButton button : buttons) {
		 button.setEnabled(false);
		 button.removeActionListeners();
		 System.out.println("set buttons enabled false"); // DEBUG
	     }
	     System.out.println("You lost! Press start to begin again.");

	     placeInSequence = 0;
	     Random randomGen = new Random(System.currentTimeMillis());
	     int randomNum = randomGen.nextInt(4);
	     int randomNum2 = (int)( Math.random() * 3.9999999);
	     computerButtonPresses = new ArrayList<Integer>();
	     computerButtonPresses.add(randomNum2);
	     currentButton = computerButtonPresses.get(0);

	     startButtonLocation.add(startButton); // add button back to screen
	     startButtonLocation.revalidate();
	     startButtonLocation.repaint();
	 }
	 else if (didWeLose == false) {
	     System.out.println("Success! Onto the next round!");
	     // initiate new round
	     Random randomGen = new Random(System.currentTimeMillis());
	     int randomNum = randomGen.nextInt(4);
	     int randomNum2 = (int)( Math.random() * 3.9999999); 
	     computerButtonPresses.add(randomNum2);
	     placeInSequence = 0;
	     currentButton = computerButtonPresses.get(0);
	     go();
	 }
     }

     public class GreenPushListener implements ActionListener {
	 public void actionPerformed(ActionEvent e) {
	     lossCheck(0);
	 }
     }
     public class RedPushListener implements ActionListener {
	 public void actionPerformed(ActionEvent e) {
	     lossCheck(1);
	 }
     }
     public class YellowPushListener implements ActionListener {
	 public void actionPerformed(ActionEvent e) {
	     lossCheck(2);
	 }
     }
     public class BluePushListener implements ActionListener {
	 public void actionPerformed(ActionEvent e) {
	     lossCheck(3);
	 }
     }

     public class StartPushListener implements ActionListener {
	 public void actionPerformed(ActionEvent ex) {
	      startButtonLocation.remove(startButton); // erase button from screen
	      startButtonLocation.revalidate();
	      startButtonLocation.repaint();
	      go();
	 } 
     }

} 

