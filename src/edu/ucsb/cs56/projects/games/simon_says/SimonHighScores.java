package edu.ucsb.cs56.projects.games.simon_says;

import java.awt.*;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.lang.Object;
import java.awt.geom.Dimension2D;
import java.awt.Dimension;
import java.io.InputStream;



public class SimonHighScores extends JFrame{
  JPanel panel;
  JPanel returnPanel;
  JButton returnButton;

  JTextArea textArea;
  JScrollPane scrollPane;

  private String l1;
  private String l2;
  private String l3;

  public SimonHighScores(){
    super("Simon High Scores");
    this.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
    this.setSize(600,600);

    panel=new JPanel(new BorderLayout());
    final Dimension fillerSizeVert = new Dimension(0, 150);
    final Dimension fillerSizeHoriz = new Dimension(150, 0);

    this.getContentPane().add(BorderLayout.NORTH, Box.createRigidArea(fillerSizeVert));
    this.getContentPane().add(BorderLayout.SOUTH, Box.createRigidArea(fillerSizeVert));
    this.getContentPane().add(BorderLayout.WEST, Box.createRigidArea(fillerSizeHoriz));
    this.getContentPane().add(BorderLayout.EAST, Box.createRigidArea(fillerSizeHoriz));
    this.getContentPane().setBackground(Color.BLACK);
    this.getContentPane().add(BorderLayout.CENTER,panel);
    textArea = new JTextArea();
    //        scrollPane = new JScrollPane(textArea);

    //scrollPane.setPreferredSize(new Dimension(300, 300));
    panel.add(BorderLayout.CENTER,textArea);

    returnPanel=new JPanel(new BorderLayout());

    this.getContentPane().add(BorderLayout.EAST,returnPanel);
    returnPanel.setBackground(Color.BLACK);

    returnButton=new JButton("Back");
    returnButton.setBorderPainted(false);
    returnButton.setOpaque(true);
    returnButton.setBackground(Color.BLACK);
    returnButton.setForeground(Color.CYAN);
    returnPanel.add(BorderLayout.SOUTH,returnButton);
    returnButton.addActionListener(new returnListener());
    returnButton.setFocusPainted(false);


    try{
      InputStream in = getClass().getResourceAsStream("/resources/TextFiles/HighScores.txt");
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String line;
      for(int i=0; i<1; ++i) {
        line=reader.readLine();
        l1 = cipher.decrypt(line, 5);
        //l1=line;
      }
      textArea.append("Amateur Level\n");
      textArea.append(l1 + "\n");
      textArea.append("\n");
      reader.close();
    }catch(IOException e){
      e.printStackTrace();
    }

    try{
      InputStream in = getClass().getResourceAsStream("/resources/TextFiles/HighScores.txt");
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String line;
      for(int i=0; i<2; ++i) {
        line=reader.readLine();
        l2 = cipher.decrypt(line, 5);
        //l2=line;
      }
      textArea.append("Intermediate Level\n");
      textArea.append(l2 + "\n");
      textArea.append("\n");
      reader.close();

      reader.close();
    }catch(IOException e){
      e.printStackTrace();
    }

    try{
      InputStream in = getClass().getResourceAsStream("/resources/TextFiles/HighScores.txt");
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String line;
      for(int i=0; i<3; ++i) {
        line=reader.readLine();
        l3 = cipher.decrypt(line, 5);
        //l3=line;
      }
      textArea.append("Professional\n");
      textArea.append(l3 + "\n");
      textArea.append("\n");
      reader.close();

      reader.close();
    }catch(IOException e){
      e.printStackTrace();
    }

    panel.setBackground(Color.BLACK);
    textArea.setBackground(Color.BLACK);
    textArea.setForeground(Color.YELLOW);
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setLocation(dim.width/2-this.getSize().width/2,dim.height/2-this.getSize().height/2);
    textArea.setEditable(false);
    setVisible(true);

  }


  public class returnListener implements ActionListener {
    public void actionPerformed(ActionEvent ex){
      dispose();
      new SimonMenu().setVisible(true);

    }
  }
}
