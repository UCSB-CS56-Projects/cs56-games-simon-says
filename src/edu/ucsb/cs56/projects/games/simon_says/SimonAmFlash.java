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

/** Flashes a sequence of buttons
*/

public class SimonAmFlash
{
  //private ArrayList<Integer>  userButtonPresses; Thought there' be a use for this, apparently not
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
  private int highScore=0;
  private String l2;
  private String ln1;
  private String ln2;
  private String ln3;
  private String new_ln1;
  private int flash_delay = 1000;



  public static void  FlashSequence(ArrayList<Integer> flashes, SimonButton[] buttons, JButton startButton, JButton returnButton, JComponent startButtonLocation, JLabel HighScore, JLabel score) {
    SimonAmFlash sequence = new SimonAmFlash(flashes, buttons, startButton, returnButton, startButtonLocation, HighScore, score);
    sequence.go();
  }

  public SimonAmFlash() {
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
      for(int i=0; i<1; ++i) {
        line2=reader.readLine();
        l2 = cipher.decrypt(line2, 5);
      //  l2=line2;
      }
      System.out.println(l2);
      HighScore = new JLabel(l2);
      HighScore.setForeground(Color.WHITE);
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
    score = new JLabel("Score: 0  ");

    startButtonLocation = new JPanel();
    currentButton = 0;
  }

  public SimonAmFlash(ArrayList<Integer> flashes, SimonButton[] buttons, JButton startButton, JButton returnButton, JComponent startButtonLocation, JLabel HighScore, JLabel score) {
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
    this.returnButton = returnButton;
    // Add Start button to bottom to Filler area

    startButton.setFocusPainted(false);
    returnButton.setFocusPainted(false);

    this.HighScore = HighScore;
    this.score = score;
    this.startButtonLocation = startButtonLocation;
  }



  //Another constructor, contating an arguement for Lives (Jlabel)
  public SimonAmFlash(ArrayList<Integer> flashes, SimonButton[] buttons, JButton startButton, JButton returnButton, JComponent startButtonLocation, JLabel HighScore, JLabel score, JLabel Lives) {
    computerButtonPresses = flashes;
    this.buttons = buttons;
    this.currentButton = flashes.get(0);
    this.startButton = startButton;
    this.returnButton = returnButton;
    this.HighScore = HighScore;
    this.score = score;
    this.startButtonLocation = startButtonLocation;
    this.Lives = Lives;
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
          System.out.println("after flash call in AmFlash");
        } catch (Exception ex) {ex.printStackTrace();}
      }
    }).start();

    // Change this to 1 later -  DEBUG
    if (computerButtonPresses.size() == 1 ) {
      buttons[0].addActionListener(new GreenPushListener()); // listen for inputs
      buttons[1].addActionListener(new RedPushListener());
      buttons[2].addActionListener(new YellowPushListener());
      buttons[3].addActionListener(new BluePushListener());
      //	 startButton.addActionListener(new StartPushListener());
      // returnButton.addActionListener(new ExitPushListener());
    }
  }

  protected void  lossCheck(int buttonNum) {
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
            ln1 = cipher.decrypt(line, 5);
            //ln1 = line;
            l= cipher.decrypt(line, 5);
            //l=line;
          }
          else if(i==1){ //this does not need to be decrypted
            ln2 = line;
          }
          else if(i==2){ //this does not need to be decrypted
            ln3 = line;
          }
        }
        String[] HighestScore = l.split(": ");
        String s=null;
        for(String token:HighestScore){
          s=token;
        }
        //System.out.println("testing: " + s + " end of line");
        highScore= Integer.parseInt(String.valueOf(s));
        reader.close();

        if(highScore<Score){
          try{
            FileWriter writer = new FileWriter("/resources/TextFiles/HighScores.txt");
            String sc = Integer.toString(Score);
            String new_line1 = "Highest Score: "+ sc;
            new_ln1 = cipher.encrypt(new_line1, 5);
            //writer.write("Highest Score: "+ Score + '\n');
            writer.write(new_ln1 + '\n');
            writer.write(ln2 + '\n');
            writer.write(ln3 + '\n');
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
            String new_line1 = "Highest Score: "+ sc;
            new_ln1 = cipher.encrypt(new_line1, 5);
            //writer.write("Highest Score: "+ Score + '\n');
            writer.write(new_ln1 + '\n');
            writer.write(ln2 + '\n');
            writer.write(ln3 + '\n');
            writer.close();
        }catch(IOException e){
          e.printStackTrace();
        }

      }


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

  private void startMidi() {
    try {
      Sequence sequence = MidiSystem.getSequence(getClass().getResourceAsStream("/resources/Sounds/beep.mid"));
      Sequencer sequencer = MidiSystem.getSequencer();
      sequencer.open();
      sequencer.setSequence(sequence);

      sequencer.start();
    }
    catch (Exception e) {
      e.printStackTrace();
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



}
