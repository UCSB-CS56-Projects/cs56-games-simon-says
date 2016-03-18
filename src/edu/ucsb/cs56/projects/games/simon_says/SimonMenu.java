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


public class SimonMenu extends JFrame
{
    
    private JPanel mp;
    private ImagePanel ip;
    private ActionListener al;
    private final String MENU[] = {"New Game", "Rules", "High Scores", "Exit"};
    
   
    public SimonMenu()
    {
        super("Simon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
        mp = new JPanel();
        mp.setBackground(Color.BLACK);
        mp.setLayout(null);
        ip = new ImagePanel(1);
        ip.setBackground(Color.BLACK);
        ip.setPreferredSize(new Dimension(300,150));
//        BufferedImage myPic;
//    try {
//        myPic = ImageIO.read(new File("/cs/student/davidwang/cs56/cs56-games-simon-says/src/edu/ucsb/cs56/projects/games/simon_says/Simon_Says_robredeyes2.jpg"));
//    } catch(IOException ex) {}
//        JLabel picLabel = new JLabel(new ImageIcon(myPic));
//        ip.add(picLabel);

             
        getContentPane().add(BorderLayout.CENTER, mp);
        getContentPane().add(BorderLayout.NORTH,ip);
        for(int i = 0; i < MENU.length; i++)
	    {
		JButton jb = new JButton(MENU[i]);
		jb.setBackground(new Color(0xCC99FF));


		jb.setBorderPainted(false);
		jb.setOpaque(true);
		jb.addActionListener(new MenuListener());
                jb.setBounds(140, 10 + 100 * i, 300, 60);
		mp.add(jb);
	    }

        setSize(600, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2,dim.height/2-this.getSize().height/2);
        setVisible(true);
    }

    public void paintComponent(Graphics g) {
	Graphics2D g2 = (Graphics2D) g;
	g2.drawString("Description and your name go here", 20,20);

        Image image = new ImageIcon("Simon_Says_robredeyes2.jpg").getImage();
        g2.drawImage(image, 10,10,this);
    }

      
    public class MenuListener implements ActionListener{
	public void actionPerformed(ActionEvent ex) {
	    String cmd = ex.getActionCommand();
	    if(cmd.equals("New Game")){
		dispose();
                new SimonLevel();
	    }

	    else if(cmd.equals("Rules")){
		dispose();
		new SimonRules().setVisible(true);
	    }
            else if (cmd.equals("High Scores")){
                dispose();
                new SimonHighScores().setVisible(true);
            }

	    else if(cmd.equals("Exit"))
		dispose();
	        
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
