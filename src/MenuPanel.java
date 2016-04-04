import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
 
/** @author Daniel Chen, Eric Tseng
  * @version 2.0, May 15, 2014
  * This program will create the main menu screen. This program will
  * place a unique background gif on the screen, and on top of that, 
  * this program will place a variety of buttons that do a variety of
  * things. (There will be 3 buttons designated for gameplay, Easy,
  * Medium, and Hard, 1 for Instructions, 1 for Credis, and the final
  * button for Quitting.) Finally, this program will take all the buttons
  * and place them in a location that suits the background gif. (So that
  * the buttons won't cover up text or animations.)
  * <p>
  * Variable Dictionary<br>
  * Name            Type             Purpose<br>
  * s               SpringLayout     Gives program access to methods and variables of SpringLayout, and also allows program to set up screen with SpringLayout.<br>
  * f               Main             Gives program access to methods and variables in Main.
  * image           Image            Stores the menu gif that is to become the background of the menu screen.
  * frame           Main             Gives the program access to the Main frame.
 */
public class MenuPanel extends JPanel
{
  SpringLayout s;
  Main f;
  Image image;
  
  /** This is the class constructor. This class constructor sets it's
    * layout as SpringLayout, sets the Main frame as it's own frame, 
    * places the buttons on the screen, and then reads in the image
    * that will become the background image.
    * @param frame Gives constructor access to the Main frame.
   */
  public MenuPanel(Main frame)
  {
    s = new SpringLayout ();
    f = frame;
    buttons ();
    image = Toolkit.getDefaultToolkit().createImage(getClass ().getResource ("/resources/Menu.gif"));
  }
  
  /** The purpose of this method is to draw out the desired background
    * image (in this case, a gif.).
    * <p>
    * Loops and Conditions<br>
    * if (image != null) The purpose of this structure is to make sure that the image actually exists.
    * @param g Gives program access to variables and methods of Graphics.
   */
  public void paintComponent(Graphics g) 
  {     
    super.paintComponent (g); 
    if (image != null)
      g.drawImage(image, 0, 0, f.getContentPane().getWidth (), f.getContentPane().getHeight(), Color.WHITE, f);
  }
 
  /** Purpose of this method is to draw buttons on the screen, centered,
    * in an orderly fashion. This method also gives these buttons the ability
    * to 'listen'.
    * <p>
    * Loops and Conditions<br>
    * for (int i = 0... The purpose of this loop is to make sure that all the buttons are generated, are added to the screen, and are all placed in the right spot.<br>
    * if (i == 0) The purpose of this is to make sure that the first button is placed in the correct spot, so all the other buttons can be placed relative to that button.
    * @param buttons This variable is used to store all the buttons.
    * @param i This variable helps the for loop by acting as a loop counter
   */
  public void buttons ()
  {
    setLayout (s);
    String [] names = {"Easy Mode", "Medium Mode", "Hard Mode", "Instructions", "High Scores", "Credits", "About", "Quit"};
    int [] keyCodes = {KeyEvent.VK_E, KeyEvent.VK_M, KeyEvent.VK_H, KeyEvent.VK_I, KeyEvent.VK_H, KeyEvent.VK_C, KeyEvent.VK_A, KeyEvent.VK_Q,}; // </3 jdk 1.7
    JButton [] buttons = new JButton [names.length];
        
    for (int i = 0; i < buttons.length; i ++)
    {
      buttons [i] = new JButton (names [i]);
      buttons [i].setMnemonic (keyCodes [i]);
      buttons [i].addActionListener (f);
      add (buttons [i]);
      
      if (i == 0)
        s.putConstraint (SpringLayout.NORTH, buttons [i], (int) (0.45 * f.getContentPane().getHeight()), SpringLayout.NORTH, this);
      else
        s.putConstraint (SpringLayout.NORTH, buttons [i], 20, SpringLayout.SOUTH, buttons [i-1]);
      s.putConstraint (SpringLayout.HORIZONTAL_CENTER, buttons [i], 0, SpringLayout.HORIZONTAL_CENTER, this);
    }
    revalidate();
    repaint();
  }
} 