import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.event.KeyEvent;

/** @author Daniel Chen
  * @version 1.0, May 25, 2014
  * This purpose of this program is to simplify coding for various
  * other panels, such as CreditPanel, InstructionPanel, etc. What
  * this program does is that it simply puts a desired background
  * picture as well as a centered button on the respective window.
  * The background picture can be any picture and the button is a
  * button that allows the player to return to menu. Not only that,
  * there are constraints set on the button that will allow the
  * programmer to decide how far down the button will be, meaning
  * that it's location can be changed. (However, it will always be
  * centered.) Finally, to further simplify coding, this program also
  * has a specific method designated to return the SpringLayout
  * layout when called, so if the programmer would like to set up
  * a window with SpringLayout, they can simply call this method.
  * <p>
  * Variable Dictionary<br>
  * Name            Type               Purpose<br>
  * s               SpringLayout       Gives program access to methods and variables of SpringLayout - also sets up screen with SpringLayout.<br>
  * f               Main               Gives program access to methods and variables of Main.<br>
  * image           BufferedImage      Stores the desired image that is to be displayed. (Also gives program access to variables and methods of BufferedImage.) <br>
  * frame           Main               Gives program access to methods and variables of Main.<br>
  * name            String             Stores the name of the file that contains the desired image so that the correct image can be accessed and displayed.<br>
  * distance        int                Stores the desired distance (as a percentage) of the button from bottom of the screen. (i.e. '10' means 10% from the bottom, or 90% from the top.)
 */
public class InfoPanel extends JPanel
{
  private SpringLayout s;
  private Main f;
  private BufferedImage image;
  
  
  /** This is the class constructor. This class constructor helps
    * simplify code in other portions of this game. What this constructor does,
    * specifically, is that it draws a desired (not specifed) image as the background,
    * sets up the screen with SpringLayout, and then places a button along the centre 
    * of the screen. (Location of button can be altered vertically.)
    * <p>
    * Loops and Conditions<br>
    * try{}catch(IOException... The purpose of this structure is to prevent the code from crashing due to an IOException error.
    * @param frame Gives program access to Main frame.
    * @param name Stores the name of the file containing the image (....txt).
    * @param distance Stores the percentage distance the button will be located from the bottom of the screen.
    * @param e Variable associated to make sure program will not crash from an IOException error.
    * @exception IOException If the image was not correctly read in.
   */
  public InfoPanel (Main frame, String name, int distance)
  {
    f = frame;
    try
    {
      image = ImageIO.read (getClass ().getResource ("/resources/" + name));
    }
    catch (IOException e)
    {
      e.printStackTrace ();
    }
    setPreferredSize (new Dimension (f.getContentPane ().getWidth (), f.getContentPane ().getHeight ()));
    s = new SpringLayout ();
    setLayout (s);
          
    JButton back = new JButton ("Main Menu");
    back.setMnemonic (KeyEvent.VK_M);
    back.addActionListener (f);
    add (back);
    s.putConstraint (SpringLayout.SOUTH, back, (int) -((double) distance/ 100 * f.getContentPane ().getHeight ()), SpringLayout.SOUTH, this);
    s.putConstraint (SpringLayout.HORIZONTAL_CENTER, back, 0, SpringLayout.HORIZONTAL_CENTER, this);
  }
  
  /** Purpose of this method is to draw the image (background image) onto
    * the screen.
    * <p>
    * Loops and Structures<br>
    * if (image != null) The purpose of this structure is to make sure that the image actually exists.
    * @param g Gives program access to methods and variables of Graphics.
   */
  public void paintComponent(Graphics g) 
  {     
    super.paintComponent (g); 
    if (image != null)
      g.drawImage(image, 0, 0, f.getContentPane().getWidth (), f.getContentPane().getHeight (), Color.WHITE, f);
  }
  
  /** Purpose of this method is to return SpringLayout 
    * when called.
    * @return The SpringLayout layout.
   */
  public SpringLayout getLayout ()
  {
    return s;
  }
} 