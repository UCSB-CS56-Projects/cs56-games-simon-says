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
import java.io.InputStream;

public class SimonProFlash {
  private ArrayList<Integer> computerButtonPresses;
  private SimonButton[] buttons; // order: Green Red, Yellow, Blue
  private JButton startButton;
  private JButton returnButton;
  private JComponent startButtonLocation;
  private int currentButton;
  private int placeInSequence; // will be zero-based
  private JLabel score;
  private JLabel HighScore;
  private JLabel Lives;
  private int Score=0;
  private  int highScore=0;
  private String l2;
  private int lives = 3;
  private boolean roundPassed = true;
  private boolean finishInTime = false;
  private String ln1;
  private String ln2;
  private String ln3;
  private String new_ln3;
  private int flash_delay = 1000;
  private boolean endLevel = false;

  public static void  FlashSequence(ArrayList<Integer> flashes, SimonButton[] buttons, JButton startButton, JButton returnButton, JComponent startButtonLocation, JLabel HighScore, JLabel score, JLabel Lives) {
    SimonAmFlash sequence = new SimonAmFlash(flashes, buttons, startButton, returnButton, startButtonLocation,HighScore, score, Lives);
    sequence.go();
  }

  public SimonProFlash() {
    //userButtonPresses = new ArrayList<Integer>();
    computerButtonPresses = new ArrayList<Integer>();
    buttons = new SimonButton[4];
    for (int i=0; i<4; i++) {
      buttons[i] = new SimonButton();
    }
    startButton = new JButton();
    returnButton = new JButton();

    try {
      InputStream in = getClass().getResourceAsStream("/resources/TextFiles/HighScores.txt");
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String line2;
      for(int i=0; i<3; ++i) {
        line2=reader.readLine();
        l2 = cipher.decrypt(line2, 5);
        //l2=line2;
      }
      System.out.println(l2);
      HighScore = new JLabel(l2);
      HighScore.setForeground(Color.WHITE);
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
    //score = new JLabel("Score: 0  ");
    //Lives = new JLabel("Lives: " + lives);
  }

  public SimonProFlash(ArrayList<Integer> flashes, SimonButton[] buttons, JButton startButton, JButton returnButton, JComponent startButtonLocation, JLabel HighScore, JLabel score, JLabel Lives) {
    this.computerButtonPresses = flashes;
    this.buttons = buttons;
    this.currentButton = flashes.get(0);
    this.startButton = startButton;
    this.returnButton = returnButton;
    this.score = score;
    this.HighScore = HighScore;
    this.Lives = Lives;
    Lives.setText("Lives: "+lives+"  ");
    this.startButtonLocation = startButtonLocation;
  }
  /** Flashes in sequential order a sequence of numbers
  */
  public void go() {
  new Thread(new Runnable() {
      public void run() {
        try {
          for (int button_num : computerButtonPresses) { // iterate through each sequence element
              SimonButton button = buttons[button_num]; // for readiblity
              button.flash(flash_delay);
              startMidi();
          }
          System.out.println("after flash call in ProFlash");
        } catch (Exception ex) {ex.printStackTrace();}
      }
    }).start();

    LevelTimer();
    // lives  == 3 must be changed here to if you change the amount of lives per level
    // Change this to 1 later -  DEBUG
    if (computerButtonPresses.size() == 1  && lives == 3) { // added and lives == 3, to make sure this is the first round
      buttons[0].addActionListener(new GreenPushListener()); // listen for inputs
      buttons[1].addActionListener(new RedPushListener());
      buttons[2].addActionListener(new YellowPushListener());
      buttons[3].addActionListener(new BluePushListener());
      if(buttons.length > 4){
        buttons[4].addActionListener(new PinkPushListener());
        buttons[5].addActionListener(new GrayPushListener());
      }
      returnButton.addActionListener(new ExitPushListener());
    }
  }

  protected void  lossCheck(int buttonNum) {
    placeInSequence++;
    roundPassed = true; // initialization just in case for debug
    boolean didWeLose = false; // initialization just in case for debug
    System.out.println("current button: "+currentButton);
    System.out.println("button number: "+buttonNum);
    System.out.println("place in sequence: "+placeInSequence);
    System.out.println("size of computerButtonPresses: "+computerButtonPresses.size());
    if (currentButton != buttonNum) {//they clicked the wrong button, subtract a life and check if they are out
      lives--;
      Lives.setText("Lives: "+lives+"  ");
      System.out.println("LIVES = " + lives);
      finishInTime = true; //kills timer thread
      if(lives == 0){
        didWeLose = true;
      this.endRound(didWeLose); // we lost
      }
      else{
        roundPassed = false;
        this.endRound(didWeLose);
      }
    }
    else if (placeInSequence >= computerButtonPresses.size()) {
      didWeLose = false;
      roundPassed = true;
      finishInTime = true;
      this.endRound(didWeLose); // we did *not* lose; game continues
    }
    else if (currentButton == buttonNum) {
      currentButton = computerButtonPresses.get(placeInSequence);
    }
  }
  private void endRound(boolean didWeLose) {
    if (didWeLose == true) {
      try {
        FileWriter writer = new FileWriter("/resources/TextFiles/Score.txt");
        writer.write("Your score was "+ Score + "!");
        writer.close();
      } catch(IOException e){
        e.printStackTrace();
      }
      for (SimonButton button : buttons) {
        button.removeActionListeners();
      }
      System.out.println("You lost! Press start to begin again.");
      new SimonGameOver();
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
      if(!roundPassed){ // if you did not get the right sequence, but you still have lives
        // initiate same round
        //System.out.println("SEE HOw MANY TIMES THIS RUNS");
        roundPassed = true;
        placeInSequence = 0;
        currentButton = computerButtonPresses.get(0);
        go();
      }
      else{ // Pattern was input correctly, add to sequence and start new round
        System.out.println("Success! Onto the next round!");
        Score++;
        score.setText("Score: "+Score+"  ");

        try{
          InputStream in = getClass().getResourceAsStream("/resources/TextFiles/HighScores.txt");
          BufferedReader reader = new BufferedReader(new InputStreamReader(in));
          String line;
          String l=null;
          for(int i =0; i<3; ++i){
            line=reader.readLine();
            if(i==0){
              ln1 = line;
            }
            else if(i==1){
              ln2 = line;
            }
            else if(i==2){
              ln3 = cipher.decrypt(line, 5);
              l = cipher.decrypt(line, 5);
            }
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
              FileWriter writer = new FileWriter("/resources/TextFiles/HighScores.txt");
              String sc = Integer.toString(Score);
              String new_line3 = "Highest Score: "+ sc;
              new_ln3 = cipher.encrypt(new_line3, 5);
              //writer.write("Highest Score: "+ Score + '\n');

              writer.write(ln1 + '\n');
              writer.write(ln2 + '\n');
              writer.write(new_ln3 + '\n');
              writer.close();
              score.setForeground(Color.RED);
            }catch(IOException ex){
              ex.printStackTrace();
            }
          }
        }catch (IOException ex){
          try{
            FileWriter writer = new FileWriter("/resources/TextFiles/HighScores.txt");
            String sc = Integer.toString(Score);
            String new_line3 = "Highest Score: "+ sc;
            new_ln3 = cipher.encrypt(new_line3, 5);
            //writer.write("Highest Score: "+ Score + '\n');
            writer.write(ln1 + '\n');
            writer.write(ln2 + '\n');
            writer.write(new_ln3 + '\n');
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

  public class PinkPushListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      startMidi();
      lossCheck(4);
    }
  }

  public class GrayPushListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      startMidi();
      lossCheck(5);
    }
  }

  public class StartPushListener implements ActionListener {
    public void actionPerformed(ActionEvent ex) {
    }
  }
  public class ExitPushListener implements ActionListener {
    public void actionPerformed(ActionEvent ex) {
      endLevel = true;
    }
  }

  private void startMidi() {
    try {
      Sequence sequence = MidiSystem.getSequence(getClass().getResourceAsStream("/resources/Sounds/beep.mid"));
      Sequencer sequencer = MidiSystem.getSequencer();
      sequencer.open();
      sequencer.setSequence(sequence);
      sequencer.start();
    }  catch (Exception e) {  e.printStackTrace();}
  }
  public void LevelTimer(){
    // this gives you a default 5 seconds + the number of buttons in the sequence to see input the correct sequence
    new Thread(new Runnable() {
      public void run(){
        try {
          int time = 10;
          for(int i =1; i < time + computerButtonPresses.size()*2; i++){
            Thread.sleep(1000);
            System.out.println("time: "+ i);
            if(finishInTime){
              break;
            }
            if(endLevel){
              return;
            }
          }
          if(!finishInTime){
            //System.out.println("TEST TO SEE IF THIS IS WHY IT IS ENDING EARLY");
            lossCheck(-1); // if the loop finishes time expires and it is a loss
          }
          finishInTime = false; //this checks to see if this bool is changed in the didWeLose method
        }
        catch(InterruptedException ex) {ex.printStackTrace();}
      }
    }).start();
  }
}
