import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

/** @author Daniel Chen
  * @version 1.0, June 3, 2014
  * This program will create a loading screen. 
  * The purpose of this loading screen is to allow 
  * the user to look at something pleasing to the eye
  * while the map is generating. The loading screen
  * is essentially just an image that is displayed for 
  * a given period of time.
  * Variable Dictionary <br>
  * Name            Type                 Purpose <br>
  * f               Main                 Gives program access to the Main frame.
 */
public class LoadingPanel extends JPanel
{
  /** This is the class constructor. This constructor will set up the
    * loading screen by displaying a unique loading image.
    * <p>
    * Loops and Conditions<br>
    * try{}catch(IOException... This structure is used to make sure that the program will not crash due to a IOException error.
    * @param f This variable gives the program access to the Main frame.
    * @param e This variable is associated to the portion of code that prevents the program from crashing.
    * @param s This variable allows the program to use variables and methods of SpringLayout to format the screen display.
    * @param background This variable is used to store the image to be displayed and place it on the screen.
    * @param image This variable stores the actual image to be displayed.
    * @exception IOException If the program does not probably read in the image.
   */
  public LoadingPanel (Main f)
  {
    setPreferredSize (new Dimension (f.getWidth (), f.getHeight ()));
    SpringLayout s = new SpringLayout ();
    setLayout (s);
    
    BufferedImage image = new BufferedImage (f.getContentPane ().getWidth (), f.getContentPane ().getHeight (), BufferedImage.TYPE_INT_RGB);
    try
    {
      image.getGraphics ().drawImage (ImageIO.read (getClass ().getResource ("/resources/Loading.png")), 0, 0, image.getWidth (), image.getHeight (), Color.WHITE, null);
    }
    catch (IOException e)
    {
      e.printStackTrace ();
    }
    JLabel background = new JLabel (new ImageIcon (image));
    add (background);
    s.putConstraint (SpringLayout.NORTH, background, 0, SpringLayout.NORTH, this);
    s.putConstraint (SpringLayout.SOUTH, background, 0, SpringLayout.SOUTH, this);
    s.putConstraint (SpringLayout.EAST, background, 0, SpringLayout.EAST, this);
    s.putConstraint (SpringLayout.WEST, background, 0, SpringLayout.WEST, this);
  }
}