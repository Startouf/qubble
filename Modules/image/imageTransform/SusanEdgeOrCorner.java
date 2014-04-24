package imageTransform;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class SusanEdgeOrCorner {
    
    private BufferedImage input;
    
    public SusanEdgeOrCorner()
    {
        try {
            //load the image from the file
            input = ImageIO.read(new File("C:/Users/Public/Documents/decor/vase.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(SusanEdgeOrCorner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //create an image with the same dimensions of the original one
        //to be manipulated
        BufferedImage im = new BufferedImage(input.getWidth(), input.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = im.createGraphics();
        //draw input into im
        g2d.drawImage(input, 0, 0, null);
        
        //initiate the segmented image with 0;
        for(int w=0; w < input.getWidth(); w++)
        {
            for(int h=0; h < input.getHeight(); h++)
            {
                im.setRGB(w, h, 0);
            }
        }
        
        //SUSAN mask is gonna have 10 pixels of size
        //susan mask
        final int SM=10;
        final int THRED = 25;
        final int THGREEN = 25;
        final int THBLUE = 25;
        
        //now I implement the susan mask , basically a circule around 
        //the pixel in test, pixel to be tested being corner or edge
        for(int w=SM; w < input.getWidth() -SM; w++)
        {
            for(int h=SM; h < input.getHeight() -SM; h++)
            {
                
                    int pix=0;
                    int alpha =  (0xff &(input.getRGB(w, h)>>24));               
                    int red =  ( 0xff &(input.getRGB(w, h)>>16)); 
                    int green = (0xff &(input.getRGB(w, h)>>8));
                    int blue =  (0xff & input.getRGB(w, h));
                                
                    //for each pixel we have the susan account
                    int sc=0;
                    
                    //now I need to implement the mask or SUSAN mask
                    //that is suppose to be a circule
                    //L1
                    //for(int i=-10,j=0; i<=0; i++,j--)
                    for(int i=-10; i <=0; i++)
                    {
                        //for(int j=0;j < =-10;j--)
                        {
                           //collecting the pixel from the mask
                           int redT =  ( 0xff &(input.getRGB(w+i, h-(i+SM))>>16)); 
                           int greenT = (0xff &(input.getRGB(w+i, h-(i+SM))>>8));
                           int blueT =  (0xff & input.getRGB(w+i, h-(i+SM)));
                           //comparing the colours
                           if((Math.abs(red - redT)
                                   &&(Math.abs(green - greenT)
                                   &&(Math.abs( blue- blueT)
                           {//it means they are simillar
                               sc++;
                           } 
                        }//j
                    }//i
                    
                    //L2
                    //for(int i=1,j=-9 ;i < =10; i++,j++)
                    for(int i=1 ;i <=10; i++)
                    {
                        //for(int j=-9;j < =0;j++)
                        {
                           //collecting the pixel from the mask
                           int redT =  ( 0xff &(input.getRGB(w+i, h+i-SM)>>16)); 
                           int greenT = (0xff &(input.getRGB(w+i, h+i-SM)>>8));
                           int blueT =  (0xff & input.getRGB(w+i, h+i-SM));
                           //comparing the colours
                           if((Math.abs(red - redT) && (Math.abs(green - greenT) &&(Math.abs( blue- blueT)
                           {//it means they are simillar
                               sc++;
                           } 
                        }//j
                    }//i
                    
                    //L3
                    //for(int i=9, j=1; i < =0; i--,j++)
                    for(int i=9; i  < = 0; i--)
                    {
                        //for(int j=1;j < = 10;j++)
                        {
                           //collecting the pixel from the mask
                           int redT =  ( 0xff &(input.getRGB(w+i, h+SM-i)>>16)); 
                           int greenT = (0xff &(input.getRGB(w+i, h+SM-i)>>8));
                           int blueT =  (0xff & input.getRGB(w+i, h+SM-i));
                           //comparing the colours
                           if((Math.abs(red - redT)
                                   &&(Math.abs(green - greenT)
                                   &&(Math.abs( blue- blueT)
                           {//it means they are simillar
                               sc++;
                           } 
                        }//j
                    }//i
                    
                    //L4
                    //for(int i=-1, j=9; i<=-9; i--,j--)
                    for(int i=-1; i < = -9; i--)
                    {
                        //for(int j=9;j < = 1; j--)
                        {
                           //collecting the pixel from the mask
                           int redT =  ( 0xff &(input.getRGB(w+i, h+SM+i)>>16)); 
                           int greenT = (0xff &(input.getRGB(w+i, h+SM+i)>>8));
                           int blueT =  (0xff & input.getRGB(w+i, h+SM+i));
                           //comparing the colours
                           if((Math.abs(red - redT)
                                   &&(Math.abs(green - greenT)
                                   &&(Math.abs( blue- blueT)
                           {//it means they are simillar
                               sc++;
                           } 
                        }//j
                    }//i
                    
                    //electing
                    //means that is border or corner
                    if (sc < 3) 
                    {
                       im.setRGB(w, h, 0xFF0000FF);      
                    }
                    
                
                
            }//h
         }//w
        
        
        try {
            //saving the new image
            ImageIO.write(im, "PNG", new File("imageSUSAN.png"));
        } catch (IOException ex) {
            Logger.getLogger(BasicEdgeDetection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
        
    }
    
    
}
