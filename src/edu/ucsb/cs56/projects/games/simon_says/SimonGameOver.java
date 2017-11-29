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


public class SimonGameOver extends JFrame{
    JPanel panel;
    ImagePanel ip;
    JPanel returnPanel;
    JButton returnButton;

    JTextArea textArea;
    JScrollPane scrollPane;

    public SimonGameOver(){
        super("Simon GAME OVER");
        this.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
        this.setSize(600,600);

        panel=new JPanel(new BorderLayout());
	ip = new ImagePanel(2);
	ip.setBackground(Color.BLACK);
	ip.setPreferredSize(new Dimension(400,300));
        final Dimension fillerSizeVert = new Dimension(0, 150);
        final Dimension fillerSizeHoriz = new Dimension(150, 0);

        this.getContentPane().add(BorderLayout.NORTH, ip);
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



        try{
          InputStream in = getClass().getResourceAsStream("/resources/TextFiles/Score.txt");
          BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;

            while((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
	    }
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
        }
    }
}
