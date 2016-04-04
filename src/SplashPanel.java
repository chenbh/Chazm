import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.SpringLayout;
import java.awt.*;

/**@author Eric Tseng
  * @version 2.0, June 6, 2014
  * This program will generate the splash screen 
  * of our game, which includes an animation,
  * the company logo, and the company name.
  * <p>
  * Variable Dictionary<br>
  * Name              Type                Purpose<br>
  * image             Image               Allows program to access methods and variables within Image.<br>
  * f                 Main                Allows program to access Main frame.
 */
public class SplashPanel extends JPanel
{
  Image image;
  Main f;
  
  /** This is the class constructor. This constructor
    * sets up the screen with SpringLayout layout and
    * then paints the screen with the desired gif and 
    * logo to make the splash screen.
    * @param frame This variable gives the program access to the Main frame.
   */
  public SplashPanel (Main frame)
  {
    f = frame;
    SpringLayout layout = new SpringLayout ();
    setLayout (layout);
    setPreferredSize (new Dimension (f.getContentPane ().getWidth (), f.getContentPane ().getHeight ()));
    
    image = Toolkit.getDefaultToolkit().createImage(getClass ().getResource ("/resources/ChenetSplash.gif"));
    JLabel logo = new JLabel (new ImageIcon (getClass ().getResource ("/resources/Logo.png")));
    layout.putConstraint (SpringLayout.NORTH, logo, 0, SpringLayout.NORTH, this);
    layout.putConstraint (SpringLayout.EAST, logo, 0, SpringLayout.EAST, this);
    add (logo);
  } 

  /** The purpose of this method is to draw the image.
    * <p>
    * Loops the Conditions<br>
    * if (image != null) The purpose of this structure is to check if the image exists or not.
    * @param g This variable gives the method access to variables and methods of Graphics - helps paint the picture on the window.
   */
  public void paintComponent(Graphics g) 
  {
    super.paintComponent(g);
    if (image != null) 
      g.drawImage(image, 0, 0, f.getContentPane ().getWidth (), f.getContentPane ().getHeight (), Color.WHITE, this);
  }
} 
