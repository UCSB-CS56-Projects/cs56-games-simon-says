package edu.ucsb.cs56.projects.games.simon_says;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.io.InputStream;

/** JFrame which contains all of the components for the Simon game.
*/

public class SimonAmFrame extends JFrame {

  SimonButton redButton;
  SimonButton greenButton;
  SimonButton yellowButton;
  SimonButton blueButton;
  JButton startButton;
  JButton returnButton;
  JLabel  score;
  JLabel HighScore;

  JPanel center;
  JPanel top;
  JPanel bottom;
  JPanel bottomInner;
  JPanel topInner;
  JPanel topInner2;

  String l2;
  /** No-arg frame constructor, sets up frame and layout of panels and components
  */
  public SimonAmFrame() {
    super("Simon"); // Window header title
    this.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
    this.setSize(600,600);

    // Filler areas that former border around frame
    final Dimension fillerSizeVert = new Dimension(0, 120);
    final Dimension fillerSizeHoriz = new Dimension(120, 60);

    this.getContentPane().add(BorderLayout.NORTH, Box.createRigidArea(fillerSizeVert));
    this.getContentPane().add(BorderLayout.SOUTH, Box.createRigidArea(fillerSizeVert));
    this.getContentPane().add(BorderLayout.WEST, Box.createRigidArea(fillerSizeHoriz));
    this.getContentPane().add(BorderLayout.EAST, Box.createRigidArea(fillerSizeHoriz));
    this.getContentPane().setBackground(Color.BLACK); // color borders of frame black

    // Generate all four the buttons, constructor automatically assigns color and sets preferred size
    this.redButton = new SimonButton(Color.RED);
    this.greenButton = new SimonButton(Color.GREEN);
    this.yellowButton = new SimonButton(Color.YELLOW);
    this.blueButton = new SimonButton(Color.BLUE);

    // Generate center of frame for buttons
    center = new JPanel(new BorderLayout());
    this.getContentPane().add(BorderLayout.CENTER, center);

    // Top section of center
    top = new JPanel(new BorderLayout());
    center.add(BorderLayout.NORTH, top);

    // Bottom section of center
    bottom = new JPanel(new BorderLayout());
    center.add(BorderLayout.SOUTH, bottom);

    // Finally, add buttons to panels
    top.add(BorderLayout.WEST, greenButton); // top section of center contains green on the left...
    top.add(BorderLayout.EAST, redButton); // ...and red on the right
    bottom.add(BorderLayout.WEST, yellowButton); // bottom section of center contains yellow on the left...
    bottom.add(BorderLayout.EAST, blueButton); // ...and blue on the right

    // Add Start button to bottom to Filler area
    returnButton = new JButton("Exit");
    startButton = new JButton("Start");
    startButton.setFocusPainted(false);
    returnButton.setFocusPainted(false);
    bottomInner = new JPanel(); // Create a panel to put button so button doesn't span whole border
    bottomInner.add(returnButton);
    bottomInner.add(startButton);
    this.bottomInner.add(Box.createRigidArea(fillerSizeVert)); // Makes sure that when button is deleted, the filler area
    // of the proper size will remain behind it
    this.getContentPane().add(BorderLayout.SOUTH, bottomInner); //

    startButton.addActionListener(new StartListener()); // DEBUG
    returnButton.addActionListener(new ExitListener());
    try {
      InputStream in = getClass().getResourceAsStream("/resources/TextFiles/HighScores.txt");
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String line2;
      for(int i=0; i<1; ++i) {
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
    score = new JLabel("Score: 0  ");
    score.setForeground(Color.WHITE);
    topInner = new JPanel(new BorderLayout());
    //	topInner2 = new JPanel(new BorderLayout());

    //topInner.add(BorderLayout., HighScore);
    topInner.add(BorderLayout.WEST, HighScore);
    topInner.add(BorderLayout.EAST,score);
    this.topInner.add(Box.createRigidArea(fillerSizeVert));
    this.getContentPane().add(BorderLayout.NORTH, topInner);
    //this.topInner2.add(Box.createRigidArea(fillerSizeVert));
    //this.getContentPane().add(BorderLayout.NORTH, topInner2);

    // Color all of the background within the border black as well
    top.setBackground(Color.BLACK);
    bottom.setBackground(Color.BLACK);
    center.setBackground(Color.BLACK);
    bottomInner.setBackground(Color.BLACK);
    topInner.setBackground(Color.BLACK);
    //topInner2.setBackground(Color.BLACK);
  }




  public class StartListener implements ActionListener {
    public void actionPerformed(ActionEvent ex) {
      bottomInner.remove(startButton); // erase button from screen

      bottomInner.revalidate();
      bottomInner.repaint();

      //	    startButton.removeActionListener(this);


      new Thread(new Runnable() {
        public void run() {
          startGame();
        }
      }).start();
      System.out.println("Thread test"); // DEBUG
    }
  }
  /** Method called by main() once all components have been added by frame
  *  to display the frame.
  */

  public class ExitListener implements ActionListener {
    public void actionPerformed(ActionEvent ex){
      dispose();
      new SimonMenu();
    }
  }

  public void display() {
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setLocation(dim.width/2-this.getSize().width/2,dim.height/2-this.getSize().height/2);
    this.setVisible(true);

    /* DEBUG, remove
    try {
    System.out.println("red");
    redButton.doClick(1000);
    Thread.sleep(1000);
    System.out.println("green");
    greenButton.doClick(500);
    Thread.sleep(1000);
    System.out.println("yellow");
    yellowButton.doClick(1000);
  } catch (InterruptedException ex) { }
  */
  // DEBUG
  //yellowButton.addActionListener(new SimonListener());

  /* DEBUG
  ArrayList<Integer> test_array = new ArrayList<Integer>();

  test_array.add(2); test_array.add(0); test_array.add(0); // Ugly, but just for testing

  try { Thread.sleep(2000); } catch (InterruptedException ex) { }
  SimonButton button_array[] = {greenButton, redButton, yellowButton, blueButton};
  SimonFlash.FlashSequence(test_array, button_array, startButton);
  */
  // DEBUG redButton.setBackground(Color.WHITE);
}
/** A main() method which calls for the frame to be displayed
*/

public void startGame() {

  Random randomGen = new Random(System.currentTimeMillis());
  int randomNum = randomGen.nextInt(4);
  int randomNum2 = (int)( Math.random() * 3.9999999);

  SimonButton button_array[] = {greenButton, redButton, yellowButton, blueButton};
  //ArrayList<Integer> test_array = new ArrayList<Integer>();
  //test_array.add(2); test_array.add(0); test_array.add(0); // Ugly, but just for testing

  ArrayList<Integer> test_array =  new ArrayList<Integer>();
  test_array.add(randomNum2); // one element to start off with
  SimonAmFlash flash = new SimonAmFlash(test_array, button_array, startButton, returnButton, bottomInner, HighScore, score);
  flash.go();
  System.out.println("after flash sequence"); // DEBUG}
}


}
