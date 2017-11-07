package edu.ucsb.cs56.projects.games.simon_says;

import java.awt.*;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.lang.Object;
import java.awt.geom.Dimension2D;
import java.awt.Dimension;


public class SimonRules extends JFrame{
  JPanel panel;
  JPanel returnPanel;
  JButton returnButton;
  JPanel nextPanel;
  JButton nextButton;
  //    JFrame f = new JFrame();

  //    Timer tm;
  JLabel pic;
  JTextArea textArea;
  JScrollPane scrollPane;
  int x = 0;
  String list[] = {
    "lib/JPG/slide_1.jpg",
    "lib/JPG/slide_2.jpg",
    "lib/JPG/slide_3.jpg",
    "lib/JPG/slide_4.jpg",
    "lib/JPG/slide_5.jpg",
    "lib/JPG/slide_6.jpg",
    "lib/JPG/slide_7.jpg",
    "lib/JPG/slide_8.jpg",
    "lib/JPG/slide_9.jpg"
  };

  public SimonRules(){
    super("Simon Rules");
    this.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
    this.setSize(600,600);
    panel=new JPanel(new BorderLayout());
    final Dimension fillerSizeVert = new Dimension(0, 100);
    final Dimension fillerSizeHoriz = new Dimension(150, 0);

    returnPanel=new JPanel(new BorderLayout());
    this.getContentPane().add(BorderLayout.WEST,returnPanel);
    returnPanel.setBackground(Color.BLACK);
    returnButton=new JButton("EXIT");
    returnButton.setBorderPainted(false);
    returnButton.setOpaque(true);
    returnButton.setBackground(Color.BLACK);
    returnButton.setForeground(Color.CYAN);
    returnButton.setFocusPainted(false);
    returnPanel.add(BorderLayout.SOUTH,returnButton);
    returnButton.addActionListener(new returnListener());

    nextPanel=new JPanel(new BorderLayout());
    this.getContentPane().add(BorderLayout.EAST,nextPanel);
    nextPanel.setBackground(Color.BLACK);
    nextButton=new JButton("Next");
    nextButton.setBorderPainted(false);
    nextButton.setOpaque(true);
    nextButton.setBackground(Color.BLACK);
    nextButton.setForeground(Color.CYAN);
    nextButton.setFocusPainted(false);
    nextPanel.add(BorderLayout.SOUTH,nextButton);
    nextButton.addActionListener(new nextListener());

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setLocation(dim.width/2-this.getSize().width/2,dim.height/2-this.getSize().height/2);

    pic = new JLabel();
    SetSlide(x);
    add(pic);
    setVisible(true);

  }

  public void SetSlide(int i){
    ImageIcon image = new ImageIcon(list[i]);
    Image img = image.getImage();
    Image newimg = img.getScaledInstance(475,600, Image.SCALE_SMOOTH);
    image = new ImageIcon(newimg);
    pic.setIcon(image);

  }

  public class returnListener implements ActionListener {
    public void actionPerformed(ActionEvent ex){
      dispose();
      new SimonMenu().setVisible(true);

    }
  }

  public class nextListener implements ActionListener {
    public void actionPerformed(ActionEvent ex){
      x += 1;
      if(x== list.length-1){//if on last slide
        nextButton.setText("Close");//change "next" to "close"
        returnButton.setText("");//clear the returnbutton
      }
      if (x>=list.length){//if slide is over
        dispose();//close the SimoneRules
        new SimonMenu().setVisible(true);//and return to menu
      }
      SetSlide(x);
    }
  }


}
