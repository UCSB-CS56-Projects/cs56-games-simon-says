package edu.ucsb.cs56.projects.games.simon_says;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Graphics2D;

public class ImagePanel extends JPanel{

    private BufferedImage logo;
    private BufferedImage gameover;
    int picNum; //logo is pic 1, gameover is pic 2
    

    public  BufferedImage resizeImage(BufferedImage image, int width, int height) {
             int type=0;
            type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
            BufferedImage resizedImage = new BufferedImage(width, height,type);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.dispose();
            return resizedImage;
         }

    public void setpicNum(int picNum){
	this.picNum=picNum;
    }
    
    public ImagePanel(int picNum) {
	this.picNum = picNum;
       try {
          logo = ImageIO.read(new File("Simon_Says_robredeyes2.jpg"));
	  gameover = ImageIO.read(new File("gameover.jpg"));
          logo = resizeImage(logo,500,125);
	  gameover = resizeImage(gameover,300,300);
      } catch (IOException ex) {
            // handle exception...
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
	if (picNum == 1){
	    g.drawImage(logo, 50, 0, null);
	}
	else if(picNum == 2){
	    g.drawImage(gameover,150,0,null);
	}
    }

}
