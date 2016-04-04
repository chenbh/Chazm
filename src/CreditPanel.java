import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.IOException;

/** @author Eric Tseng
 * @version 3.0, June 8, 2014
 * This program will create the credits screen. 
 * The credits screen will include a background,
 * a button that allows the user to return to menu,
 * and a brief citation of all online sources used.
 * <p>
 * Variable Dictionary <br>
 * Name           Type            Purpose <br>
 * f              Main            Gives the program access to the Main frame.
 */
public class CreditPanel extends InfoPanel
{
  private SpringLayout s;
  
  /** This is the class constructor. This constructor will
    * place an unique background on the screen as well as
    * a brief description of which sources the programmers have
    * used, properly formatted and laid out on the screen (using
    * SpringLayout).
    * <p>
    * Loops and Conditions<br>
    * for (int x...) The purpose of this loop is to simplify code.
    * @param f Gives constructor access to Main frame.
    * @param logo Associated to the logo image that is to be displayed.
    * @param credL Stores all the text (credits) that are to be placed on the screen.
    * @param x Helps the for loop by acting as a counter.
   */
  public CreditPanel (Main f)
  {
    super (f, "Credits.png", 10);
    s = getLayout ();
    JLabel logo = new JLabel (new ImageIcon (getClass ().getResource ("/resources/Logo.png")));
    s.putConstraint (SpringLayout.NORTH, logo, 0, SpringLayout.NORTH, this);
    s.putConstraint (SpringLayout.EAST, logo, 0, SpringLayout.EAST, this);
    add (logo);
    
    JLabel [] credL = new JLabel [7]; //Change based on how many credits you import.
    credL [0] = new JLabel ("Credits");
    credL [1] = new JLabel ("By: Daniel Chen, Eric Tseng");
    credL [2] = new JLabel ("Logo - istockphoto");
    credL [3] = new JLabel ("Logo - mysoti");
    credL [4] = new JLabel ("Questions - Sheffield");
    credL [5] = new JLabel ("Questions - misterguch.brinkster");
    credL [6] = new JLabel ("Questions - chemteam");
    for (int x = credL.length - 1; x >= 0 ; x--)
    {
      credL[x].setFont (new Font ("Arial", Font.BOLD, 16));
      this.add(credL[x]); 
      s.putConstraint (SpringLayout.WEST, credL[x], 20, SpringLayout.WEST, this);
      s.putConstraint (SpringLayout.SOUTH, credL[x], -(x*25), SpringLayout.SOUTH, this);
    }
  }
}