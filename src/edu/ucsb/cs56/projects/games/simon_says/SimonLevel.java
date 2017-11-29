package edu.ucsb.cs56.projects.games.simon_says;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SimonLevel extends JFrame{

  private JPanel mp;
  private ImagePanel ip;
  private ActionListener al;
  private final String Level[] = {"Amateur", "Intermediate", "Professional"};
  private JButton returnButton;
  private String buttonImage = "lib/Icons/MenuButton.png";
  private String buttonPressedImage = "lib/Icons/MenuButtonPressed.png";

  public SimonLevel()
  {
    super("Simon");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    mp = new JPanel();
    mp.setBackground(Color.BLACK);
    mp.setLayout(null);
    ip = new ImagePanel(1);
    ip.setBackground(Color.BLACK);
    ip.setPreferredSize(new Dimension(300,150));

    returnButton = new JButton("Back");
    returnButton.setBackground(new Color(0x3399FF));
    try{
    ImageIcon iconB = new ImageIcon(getlevelButton(),"Back");
    ImageIcon iconBP = new ImageIcon(getLevelButtonPressed(),"Back");
    returnButton.setIcon(iconB);
    returnButton.setPressedIcon(iconBP);
    }catch (Exception e){
      System.err.println("could not load menu button");
    }
    returnButton.setHorizontalTextPosition(JButton.CENTER);
    returnButton.setVerticalTextPosition(JButton.CENTER);
    returnButton.setOpaque(false);
    returnButton.setContentAreaFilled(false);
    returnButton.setBorderPainted(false);
    returnButton.setFocusPainted(false);
    returnButton.setPreferredSize(new Dimension(300,60));
    returnButton.setBounds(140, 310, 300,60);
    returnButton.addActionListener(new returnListener());

    returnButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        returnButton.setBackground(new JButton().getBackground());
      }
    });

    returnButton.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseExited(java.awt.event.MouseEvent evt) {
        returnButton.setBackground(new Color(0x3399FF));
      }
    });

    mp.add(returnButton);

    getContentPane().add(BorderLayout.CENTER, mp);
    getContentPane().add(BorderLayout.NORTH,ip);
    for(int i = 0; i < Level.length; i++){
      JButton jb = new JButton(Level[i]);
      jb.setBackground(new Color(0x3399FF));
      try{
      ImageIcon icon = new ImageIcon(getlevelButton(),Level[i]);
      ImageIcon iconP = new ImageIcon(getLevelButtonPressed(),Level[i]);
      jb.setIcon(icon);
      jb.setPressedIcon(iconP);
      }catch (Exception e){
        System.err.println("could not load menu button");
      }
      jb.setHorizontalTextPosition(JButton.CENTER);
      jb.setVerticalTextPosition(JButton.CENTER);
      jb.setOpaque(false);
      jb.setContentAreaFilled(false);
      jb.setBorderPainted(false);
      jb.setFocusPainted(false);
      jb.addActionListener(new LevelListener());
      jb.setPreferredSize(new Dimension(300,60));
      jb.setBounds(140, 10 + 100 * i, 300, 60);

      //allows button to flash when cursor hovers over button
      jb.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
          jb.setBackground(new JButton().getBackground());
        }
      });
      //allows button to go back to original color when cursor leaves
      jb.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseExited(java.awt.event.MouseEvent evt) {
          jb.setBackground(new Color(0x3399FF));
        }
      });
      mp.add(jb);
    }
    setSize(600, 600);
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setLocation(dim.width/2-this.getSize().width/2,dim.height/2-this.getSize().height/2);
    setVisible(true);
  }
  public String getlevelButton() {
    return this.buttonImage;
  }
  public String getLevelButtonPressed() {
    return this.buttonPressedImage;
  }
  public class LevelListener implements ActionListener{
    public void actionPerformed(ActionEvent ex) {
      String cmd = ex.getActionCommand();
      if(cmd.equals("Amateur")){
        dispose();
        SimonAmFrame frame = new SimonAmFrame();
        frame.display();
      }
      else if(cmd.equals("Intermediate")){
        dispose();
        SimonInterLF inter=new SimonInterLF();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        inter.setLocation(dim.width/2-inter.getSize().width/2,dim.height/2-inter.getSize().height/2);
        inter.setVisible(true);
      }
      else if(cmd.equals("Professional")){
        dispose();
        SimonProL pro=new SimonProL();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        pro.setLocation(dim.width/2-pro.getSize().width/2,dim.height/2-pro.getSize().height/2);
        pro.setVisible(true);
      }


    }
  }

  public class returnListener implements ActionListener {
    public void actionPerformed(ActionEvent ex){
      dispose();
      new SimonMenu().setVisible(true);

    }
  }
}
