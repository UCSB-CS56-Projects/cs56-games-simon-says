package edu.ucsb.cs56.projects.games.simon_says;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class SimonMenu extends JFrame {
  private JPanel mp;
  private ImagePanel ip;
  private ActionListener al;
  private final String MENU[] = {"New Game", "Rules", "High Scores", "Exit"};
  public Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
  private String buttonImage = "lib/Icons/MenuButton.png";
  private String buttonPressedImage = "lib/Icons/MenuButtonPressed.png";
  public SimonMenu() {
    super("Simon");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setupLayout();
    generateMenuButtons();
    setSize(600, 600);
    this.setLocation(dim.width/2-this.getSize().width/2,dim.height/2-this.getSize().height/2);
    setVisible(true);
  }

  public void setupLayout() {
    mp = new JPanel();
    mp.setBackground(Color.BLACK);
    mp.setLayout(null);
    ip = new ImagePanel(1);
    ip.setBackground(Color.BLACK);
    ip.setPreferredSize(new Dimension(300, 150));
    getContentPane().add(BorderLayout.CENTER, mp);
    getContentPane().add(BorderLayout.NORTH,ip);
  }

  public void generateMenuButtons() {
    for(int i = 0; i < MENU.length; i++) {
      JButton jb = new JButton(MENU[i]);
      //jb.setBackground(new Color(0xCC99FF));
      jb.addActionListener(new MenuListener());
      jb.setPreferredSize(new Dimension(300,60));
      jb.setBounds(140, 10 + 100 * i, 300, 60);
      try{
      ImageIcon icon = new ImageIcon(getMenuButton(),MENU[i]);
      ImageIcon iconP = new ImageIcon(getMenuButtonPressed(),MENU[i]);
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
      // jb.addMouseListener(new java.awt.event.MouseAdapter() {
      //   public void mouseEntered(java.awt.event.MouseEvent evt) {
      //     jb.setBackground(new JButton().getBackground());
      //   }
      // });
      // jb.addMouseListener(new java.awt.event.MouseAdapter() {
      //   public void mouseExited(java.awt.event.MouseEvent evt) {
      //     jb.setBackground(new Color(0xCC99FF));
      //   }
      // });
      mp.add(jb);
    }
  }

  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.drawString("Description and your name go here", 20,20);

    Image image = new ImageIcon("lib/JPG/Simon_Says_robredeyes2.jpg").getImage();
    g2.drawImage(image, 10,10,this);
  }
  public String getMenuButton() {
    return this.buttonImage;
  }
  public String getMenuButtonPressed() {
    return this.buttonPressedImage;
  }
  public class MenuListener implements ActionListener {
    public void actionPerformed(ActionEvent ex) {
      String cmd = ex.getActionCommand();
      if(cmd.equals("New Game")) {
        dispose();
        new SimonLevel();
      }
      else if(cmd.equals("Rules")) {
        dispose();
        new SimonRules().setVisible(true);
      }
      else if (cmd.equals("High Scores")) {
        dispose();
        new SimonHighScores().setVisible(true);
      }
      else if(cmd.equals("Exit")) {
        dispose();
        System.exit(0);
      }
    }
  }

  private void startMidi() {
    try {
      Sequence sequence = MidiSystem.getSequence(new File("skrillex.mid"));
      Sequencer sequencer = MidiSystem.getSequencer();
      sequencer.open();
      sequencer.setSequence(sequence);
      sequencer.start();
      sequencer.setLoopCount(Integer.MAX_VALUE);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    SimonMenu menu = new SimonMenu();
    PictureComponent component = new PictureComponent();
    menu.add(component);
    menu.setVisible(true);
  }
}
