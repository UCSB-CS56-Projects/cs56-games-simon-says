package edu.ucsb.cs56.projects.games.simon_says;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

// the four tools things we'll use to draw

import java.awt.geom.Line2D;  // single lines
import java.awt.geom.Rectangle2D; 

/**
   Drew Taylor and David Wang
*/

// Your class should "extend JComponent
// This is "inheritance", which we'll start readina about in Chapter 10
// It means that PictureComponent "is a" JComponent
//   that is, a special type of JComponent that is for a specific purpose

public class PictureComponent extends JComponent
{  
    
    /** The paintComponent method is always required if you want
     * any graphics to appear in your JComponent.    
     * 
     * There is a paintComponent
     * method that is created for you in the JComponent class, but it
     * doesn't do what we want, so we have to "override" that method with
     * our own method.  
     * 
     * This overriding is typical when inheritance is used.
     * In inheritance, you take something that is a "basic" version of
     * what you want, then you "trick it out" with your own custom features.
     * Sort of a "pimp my Java class" kind of thing.
     */
    
    public void paintComponent(Graphics g)
    {  
        // Recover Graphics2D--we always do this.
        // See sections 2.12, p. 60-61 for an explanation
	
        Graphics2D g2 = (Graphics2D) g;
	
	//        g2.drawString("Description and your name go here", 20,20);

        Image image = new ImageIcon("lib/JPG/Simon_Says_robredeyes2.jpg").getImage();
        g2.drawImage(image, 200,200,this);

        //g2.drawString("Description and your name go here", 20,20);
	
    }
}
