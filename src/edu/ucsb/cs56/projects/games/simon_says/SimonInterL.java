package edu.ucsb.cs56.projects.games.simon_says;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/** Sped up version of SimonAmFlash for intermediate level
 */
public class SimonInterL {
    
    private ArrayList<Integer> computerButtonPresses;
    private SimonButton[] buttons; // order: Green Red, Yellow, Blue
    private JButton startButton;
    private JButton returnButton;
    private JComponent startButtonLocation;
    private int currentButton; 
    private int placeInSequence; // will be zero-based
    private JLabel score;
    private JLabel HighScore;
    private int Score=0;
    private  int highScore=0;
    private String l2;
    
    public static void  FlashSequence(ArrayList<Integer> flashes, SimonButton[] buttons, JButton startButton, JButton returnButton, JComponent startButtonLocation, JLabel HighScore, JLabel score) {
	SimonAmFlash sequence = new SimonAmFlash(flashes, buttons, startButton, returnButton,startButtonLocation,HighScore, score);
	 sequence.go();
    }
    
    public SimonInterL() { 
    	computerButtonPresses = new ArrayList<Integer>();
	buttons = new SimonButton[4];
	for (int i=0; i<4; i++) {
	    buttons[i] = new SimonButton();
	}
	startButton = new JButton();
	returnButton = new JButton();
	//	returnButton.setFocusPainted(false);
	//	startButton.setFocusPainted(false);
	try {
	    File myFile = new File("lib/TextFiles/HighScoreInterLevel.txt");
	    FileReader fileReader = new FileReader(myFile);
	    BufferedReader reader = new BufferedReader(fileReader);
	    String line2;
	    while((line2=reader.readLine())!=null) {
		l2=line2;
	    }
	    System.out.println(l2);
	    HighScore = new JLabel(l2);
	    HighScore.setForeground(Color.WHITE);
	}
	catch (IOException ex) {
	    ex.printStackTrace();
	}

	// score = new JLabel("Score: 0  ");
	startButtonLocation = new JPanel();
	currentButton = 0;

    }
    
    public SimonInterL(ArrayList<Integer> flashes, SimonButton[] buttons, JButton startButton, JButton returnButton, JComponent startButtonLocation, JLabel HighScore, JLabel score) {
    	computerButtonPresses = flashes;
    	this.buttons = buttons;
	this.currentButton = flashes.get(0);
	this.startButton = startButton;
        this.returnButton = returnButton;
	this.HighScore = HighScore;
        this.score = score;
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
			   
			 }
			 for (int button_num : computerButtonPresses) { // iterate through each sequence element
			     Thread.sleep(400);
			     SimonButton button = buttons[button_num]; // for readiblity
			     //System.out.println("hey"); // DEBUG
			     Color buttonColor = button.getBackground();
			     button.setBackground(Color.WHITE);
			     startMidi();
			     Thread.sleep(150);
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
	     //	     startButton.addActionListener(new StartPushListener());
	 }
    }
    
    protected void lossCheck(int buttonNum) {
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
	     try {
		 FileWriter writer = new FileWriter("lib/TextFiles/Score.txt");
		 writer.write("Your score was "+ Score + "!");
		 writer.close();
	     } catch(IOException e){
                 e.printStackTrace();
             }
	     new SimonGameOver();
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
	     Score++;
	     score.setText("Score: "+Score+"  ");
	     
	     try{
		 File myFile = new File("lib/TextFiles/HighScoreInterLevel.txt");
		 FileReader fileReader = new FileReader(myFile);
		 BufferedReader reader = new BufferedReader(fileReader);
		 String line;
		 String l=null;
		 while((line=reader.readLine())!=null) {
		     l=line;
		 }
		 String[] HighestScore = l.split(": ");
		 String s=null;
		 for(String token:HighestScore){
		     s=token;
             }
		 highScore= Integer.parseInt(String.valueOf(s));
		 reader.close();
		 
		 if(highScore<Score){
		     try{
			 FileWriter writer = new FileWriter("lib/TextFiles/HighScoreInterLevel.txt");
			 writer.write("Highest Score: "+ Score);
			 writer.close();
                     score.setForeground(Color.RED);
		     }catch(IOException ex){
			 ex.printStackTrace();
		     }
		 }
	     }catch (IOException ex){
		 try{
		     FileWriter writer = new FileWriter("lib/TextFiles/HighScoreInterLevel.txt");
		     writer.write("Highest Score: "+ Score);
		     writer.close();
		 }catch(IOException e){
		     e.printStackTrace();
		 }
		 
	     }
	     
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
	     startMidi();
	     lossCheck(0);
	 }
    }
    public class RedPushListener implements ActionListener {
	 public void actionPerformed(ActionEvent e) {
	     startMidi();
	     lossCheck(1);
	 }
    }
    public class YellowPushListener implements ActionListener {
	 public void actionPerformed(ActionEvent e) {
	     startMidi();
	     lossCheck(2);
	 }
    }
    public class BluePushListener implements ActionListener {
	 public void actionPerformed(ActionEvent e) {
	     startMidi();
	     lossCheck(3);
	 }
    }

    
    public class StartPushListener implements ActionListener {
	public void actionPerformed(ActionEvent ex) {
	    /*
	    startButtonLocation.remove(startButton); // erase button from screen
	    startButtonLocation.revalidate();
	    startButtonLocation.repaint();
	    score.setText("Score: 0  ");
	    Score = 0;
	    score.setForeground(Color.WHITE);
	    go();
	    */  
	} 
    }


        private void startMidi() {
	try {
	    Sequence sequence = MidiSystem.getSequence(new File("lib/Sounds/beep.mid"));
	    Sequencer sequencer = MidiSystem.getSequencer();
	    sequencer.open();
	    sequencer.setSequence(sequence);
	    
	    sequencer.start();
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
