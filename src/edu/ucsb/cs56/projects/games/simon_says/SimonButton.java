package edu.ucsb.cs56.projects.games.simon_says;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** A button that can be clicked on by the user.
*  There will be four of these, colored green, red, yellow, and blue.
*  Curretly, this JButton subclass automatically sets the preferred size.
*  It also has a constructor which can take as a Color as a parameter to
*  initialize it with a certain color.
*/
public class SimonButton extends JButton {
  private String flashIcon = "lib/Icons/GrayButtonSmall.png";
  private boolean on = false;
  /** Default no-arg constructor sets preferred size to a reasonable
  *  size.
  */
  public SimonButton() {
    this.setPreferredSize(new Dimension(100,100));
    try{
    ImageIcon flashIcon = new ImageIcon(getFlashIcon());
    this.setPressedIcon(flashIcon);
  } catch (Exception e){
    System.err.println("could not load icon");
  }
  }

  /** Constructor with Color parameter.
  * Calls no-arg constructor to set size and sets button color to
  * Color passed in as an argument.
  * @param Color color
  */
  public SimonButton(Color color) {
    this();
    // add ImageIcon
    try{
    ImageIcon icon = new ImageIcon(iconPicker(color));
    ImageIcon flashIcon = new ImageIcon(getFlashIcon());
    System.out.println("after");
    this.setPressedIcon(flashIcon);
    this.setIcon(icon);
    this.setDisabledIcon(flashIcon);
  } catch (Exception e){
    System.err.println("could not load icon");
  }
    //this.setBackground(color);
    this.setOpaque(false);
    this.setContentAreaFilled(false);
    this.setBorderPainted(false);
    this.setFocusPainted(false);
  }

  public String iconPicker(Color color){
    if(color == Color.BLACK) return "lib/Icons/BlackButtonSmall.png";
    if(color == Color.RED) return "lib/Icons/RedButtonSmall.png";
    if(color == Color.BLUE) return "lib/Icons/BlueButtonSmall.png";
    if(color == Color.GREEN) return "lib/Icons/GreenButtonSmall.png";
    if(color == Color.GRAY) return "lib/Icons/GrayButtonSmall.png";
    if(color == Color.PINK) return "lib/Icons/OrangeButtonSmall.png";
    if(color == Color.YELLOW) return"lib/Icons/YellowButtonSmall.png";
    return "lib/Icons/RedButtonSmall.png";
  }
  public String getFlashIcon(){
    return this.flashIcon;
  }
  public void flash(int delay){
    try{
      Thread.sleep(delay/4);
      setEnabled(false);
      Thread.sleep(delay);
      setEnabled(true);
      revalidate();
      repaint();
      Thread.sleep(delay/4);
    } catch(Exception e) {
      System.err.println("flash failed");
    }

    System.out.println("after flash");

  }
  // public Timer buttonTimer(int delay) {
  // this.timer.addActionListener((icon) -> this.setIcon(icon));
  //   Timer timer = new Timer(delay, (e) -> System.out.println("delay"));
  //   timer.setRepeats(false);
  //   timer.start();
  //
  // }
  // public class flashListener implements ActionListener {
  //   public void actionPerformed(ActionEvent ex) {
  //
  //   }
  // }
  /** Removes actionListeners so-as to disable user-interactivity features temporarily
  *
  */
  public void removeActionListeners() {
    for (ActionListener listener : this.getActionListeners()) {
      this.removeActionListener(listener);
    }
  }

  /** Empty main method. If code were added to it, could be used for testing
  * if desired.
  */
  public static void main(String[] args) {
  }
}
