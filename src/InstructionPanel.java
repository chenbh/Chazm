import java.awt.*;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import java.util.ArrayList;
import java.io.*;

/** @author Eric Tseng
  * @version 3.0, June 8, 2014
  * This program will create the instructions screen.
  * The instructions screen will have a unique background,
  * a set of instructions that will help the user play
  * the game, and a button that will allow the user
  * to return to menu. All text has been properly
  * formatted to make sure that the display is pleasing
  * to the eye.
  * <p>
  * Variable Dictionary <br>
  * Name            Type                 Purpose <br>
  * f               Main                 Gives program access to the Main frame.
 */
public class InstructionPanel extends InfoPanel
{
  /** This is the class constructor. This constructor will
    * create the Instructions screen by placing a unique image
    * in the background as well as by placing the instructions (text)
    * in the middle of the screen. The instructions will be 
    * laid out with SpringLayout.
    * <p>
    * Loops and Conditions<br>
    * try{}catch(IOException... The purpose of this structure is to make sure that the program will not crash from an IOException.<br>
    * while (true) The purpose of this loop is to read in all the instructions from the respective .txt file.<br>
    * for (int x... The purpose of this loop is to properly format the text (size, font, etc.).
    * @param f Gives program access to Main frame.
    * @param s Gives program ability to format screen using SpringLayout (methods and variables).
    * @param text Stores all the instructions being read in from the .txt file.
    * @param in Allows the program to access the correct .txt file containing the instructions.
    * @param temp Temporarily stores the instructions while they are being read in from the .txt file.
    * @param e Variable associated to the portion of the code that prevents the program from crashing due to an IOException.
    * @param x Variable used to help the for loop by acting as a loop counter.
    * @param instructionL Variable used to store all the instruction text into a JLabel in order for them to be placed on the screen.
    * @exception IOException When the program fails to read the Instructions file or if the Instructions file does not exist.
   */
  public InstructionPanel (Main f)
  {  
    super (f, "Instructions.png", 8);
    SpringLayout s = getLayout ();
    
    ArrayList <String> text = new ArrayList <String> ();
    try
    {
      BufferedReader in = new BufferedReader (new InputStreamReader (getClass ().getResource ("/resources/Instructions.txt").openStream ()));
      while (true)
      {
        String temp = in.readLine ();
        if (temp == null)
          break;
        text.add (temp);
      }
      in.close ();
    }
    catch (IOException e)
    {
    }
    
    JLabel [] instructionL = new JLabel [text.size ()];
    for (int x = 0; x < text.size (); x++)
    {
      instructionL [x] = new JLabel (text.get (x));
      instructionL[x].setFont (new Font ("Arial", Font.PLAIN, 16));
      add (instructionL[x]);
      s.putConstraint (SpringLayout.HORIZONTAL_CENTER, instructionL[x], 0, SpringLayout.HORIZONTAL_CENTER, this);
      s.putConstraint (SpringLayout.NORTH, instructionL[x], 0 + (25*x), SpringLayout.NORTH, this);
    }                                                            
  }
} 