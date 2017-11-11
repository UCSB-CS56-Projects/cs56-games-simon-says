package edu.ucsb.cs56.projects.games.simon_says;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.lang.*;

public class SimonProL extends JFrame{
	SimonButton redButton;
	SimonButton greenButton;
	SimonButton yellowButton;
	SimonButton blueButton;
	SimonButton grayButton;
	SimonButton pinkButton;
	JButton startButton;
	JButton returnButton;
	JLabel score;
	JLabel HighScore;
	JLabel Lives;
	//ArrayList<SimonButton> buttonList;
	JPanel center;
	JPanel top;
	JPanel bottom;
	JPanel bottomInner;
	JPanel topCenter;
	JPanel bottomCenter;
	JPanel topInner;
	String l2;
	final Dimension fillerSizeVert = new Dimension(0, 100);
	final Dimension fillerSizeHoriz = new Dimension(74, 0);
	/** No-arg frame constructor, sets up frame and layout of panels and components
	*/
	public SimonProL() {
		super("Simon Professional");
		this.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
		this.setSize(600,600);
		fillBorder();
		generateButtons();
		generateFrame();
		addButtons();
		readHighScoreFromFile();
		addScoreBoard();
		startButton.addActionListener(new StartListener());
		returnButton.addActionListener(new ExitListener());
		colorBackground(Color.BLACK);
	}

	public void addScoreBoard(){
		score = new JLabel("Score: 0  ");
		score.setForeground(Color.WHITE);
		topInner = new JPanel(new BorderLayout());
		Lives = new JLabel("Lives: 0");
		Lives.setForeground(Color.WHITE);
		topInner.add(BorderLayout.WEST, HighScore);
		topInner.add(BorderLayout.EAST,score);
		topInner.add(BorderLayout.NORTH, Lives);
		Lives.setHorizontalAlignment(SwingConstants.CENTER);
		this.topInner.add(Box.createRigidArea(fillerSizeVert));
		this.getContentPane().add(BorderLayout.NORTH, topInner);
	}

	public void colorBackground(Color color){
		top.setBackground(color);
		bottom.setBackground(color);
		center.setBackground(color);
		bottomInner.setBackground(color);
		topInner.setBackground(color);
	}

	public void readHighScoreFromFile(){
		try {
			File myFile = new File("lib/TextFiles/HighScoreProLevel.txt");
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
	}

	public class StartListener implements ActionListener {
		public void actionPerformed(ActionEvent ex) {
			bottomInner.remove(startButton);
			bottomInner.revalidate();
			bottomInner.repaint();
			new Thread(new Runnable() {
				public void run() {
					startGame();
				}
			}).start();
			System.out.println("Thread test"); // DEBUG
		}
	}

	public class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent ex){
			dispose();
			new SimonMenu();
		}
	}

	public void fillBorder(){
		this.getContentPane().add(BorderLayout.NORTH, Box.createRigidArea(fillerSizeVert));
		this.getContentPane().add(BorderLayout.SOUTH, Box.createRigidArea(fillerSizeVert));
		this.getContentPane().add(BorderLayout.WEST, Box.createRigidArea(fillerSizeHoriz));
		this.getContentPane().add(BorderLayout.EAST, Box.createRigidArea(fillerSizeHoriz));
		this.getContentPane().setBackground(Color.BLACK);
	}

	public void generateFrame(){
		center = new JPanel(new BorderLayout());
		this.getContentPane().add(BorderLayout.CENTER, center);
		top = new JPanel(new BorderLayout());
		center.add(BorderLayout.NORTH, top);
		bottom = new JPanel(new BorderLayout());
		center.add(BorderLayout.SOUTH, bottom);
	}

	public void addButtons(){
		top.add(BorderLayout.WEST, greenButton);
		top.add(BorderLayout.EAST, redButton);
		bottom.add(BorderLayout.WEST, yellowButton);
		bottom.add(BorderLayout.EAST, blueButton);
		topCenter = new JPanel(new BorderLayout());
		topCenter.setBackground(Color.BLACK);
		top.add(BorderLayout.CENTER,topCenter);
		topCenter.add(BorderLayout.WEST, Box.createRigidArea(fillerSizeHoriz));
		topCenter.add(BorderLayout.EAST, Box.createRigidArea(fillerSizeHoriz));
		topCenter.add(BorderLayout.CENTER,grayButton);
		bottomCenter = new JPanel(new BorderLayout());
		bottomCenter.setBackground(Color.BLACK);
		bottom.add(BorderLayout.CENTER,bottomCenter);
		bottomCenter.add(BorderLayout.EAST, Box.createRigidArea(fillerSizeHoriz));
		bottomCenter.add(BorderLayout.WEST, Box.createRigidArea(fillerSizeHoriz));
		bottomCenter.add(BorderLayout.CENTER,pinkButton);
		startButton = new JButton("Start");
		returnButton = new  JButton("Exit");
		startButton.setFocusPainted(false);
		returnButton.setFocusPainted(false);
		bottomInner = new JPanel();
		bottomInner.add(returnButton);
		bottomInner.add(startButton);
		this.bottomInner.add(Box.createRigidArea(fillerSizeVert));
		this.getContentPane().add(BorderLayout.SOUTH, bottomInner);
	}

	public void generateButtons(){
		this.redButton = new SimonButton(Color.RED);
		this.greenButton = new SimonButton(Color.GREEN);
		this.yellowButton = new SimonButton(Color.YELLOW);
		this.blueButton = new SimonButton(Color.BLUE);
		this.grayButton = new SimonButton(Color.GRAY);
		this.pinkButton = new SimonButton(Color.PINK);
	}

	public void display() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2,dim.height/2-this.getSize().height/2);
		this.setVisible(true);
	}

	public void startGame() {
		Random randomGen = new Random(System.currentTimeMillis());
		int randomNum = randomGen.nextInt(6);
		int randomNum2 = (int)( Math.random() * 5.9999999);
		SimonButton button_array[] = {greenButton, redButton, yellowButton, blueButton, grayButton, pinkButton};
		ArrayList<Integer> test_array =  new ArrayList<Integer>();
		test_array.add(randomNum2); // one element to start off with
		SimonProFlash flash = new SimonProFlash(test_array, button_array, startButton, returnButton, bottomInner, HighScore, score, Lives);
		flash.go();
		System.out.println("after flash sequence");
	}
}//end class
