import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

/** @author Daniel Chen
  * @version 1.1, May 11, 2014
  * This program will display the high score screen.
  * The high score screen will have a unique background
  * and will display the top 10 players with their respective
  * scores. (This program also includes methods that allow
  * the scores to be updated and then displayed.)
  * <p>
  * Variable Dictionary<br>
  * Name             Type                Purpose<br>
  * m                HighScoreManager    Gives program access to variables & methods of HighScoreManager.<br>
  * layout           SpringLayout        Gives program access to variables and methods of SpringLayout. <br>
  * f                Main                Gives program access to variables and methods of Main.<br>
  * name             JLabel[]            Stores the names of the top 10 players.<br>
  * score            JLabel[]            Stores the scores of the top 10 players.<br>
  * m                HighScoreManager    Gives program access to variables and methods of HighScoreManager.<br>
  * f                Main                Gives program access to Main frame.
 */
public class HighScorePanel extends InfoPanel implements ActionListener
{
  private HighScoreManager m;
  private SpringLayout layout;
  private Main f;
  private JLabel [] name, score;
  private final double INITIAL = 0.05;
  
  /** This is the class constructor. This constructor sets up
    * the locations of the components of the high scores to be
    * displayed.
    * <p>
    * Loops and Conditions<br>
    * for (int i == 0... The purpose of this loop is to help format the screen.<br>
    * for (int i == 1... The purpose of this loop is to help format the screen.
    * @param m Gives program access to HighScoreManager methods and variables.
    * @param f Gives program access to Main frame.
    * @param print Variable associated to button that allows player to print.
    * @param reset Variable associated to button that allows player to reset scores.
    * @param font Gives constructor access to methods and variables of Font.
    * @param i Loop counter for for loop.
    * @param i Loop counter for for loop.
   */
  public HighScorePanel (HighScoreManager m, Main f) // sets up the locations of the components
  {
    super (f, "Highscore.png", 35);
    this.f = f;
    this.m = m;
    layout = getLayout ();
    
    JButton print = new JButton ("Print Scores");
    JButton reset = new JButton ("Reset Scores");
    print.addActionListener (this);
    reset.addActionListener (this);
    add (print);
    add (reset);
    
    layout.putConstraint (SpringLayout.SOUTH, print, -15, SpringLayout.NORTH, reset);
    layout.putConstraint (SpringLayout.HORIZONTAL_CENTER, print, 0, SpringLayout.HORIZONTAL_CENTER, this);
    layout.putConstraint (SpringLayout.SOUTH, reset, -60, SpringLayout.VERTICAL_CENTER, this);
    layout.putConstraint (SpringLayout.HORIZONTAL_CENTER, reset, 0, SpringLayout.HORIZONTAL_CENTER, this);  
    
    Font font = new Font ("Serif", Font.PLAIN, 30);
    name = new JLabel [10];
    score = new JLabel [10];
    for (int i = 0; i < 10; i ++)
    {
      name [i] = new JLabel ();
      name [i].setFont (font);
      score [i] = new JLabel ();
      score [i].setFont (font);
      add (name [i]);
      add (score [i]);
    }
    
    layout.putConstraint (SpringLayout.WEST, name [0], 0, SpringLayout.WEST, this);
    layout.putConstraint (SpringLayout.NORTH, name [0], (int) (0.05 * f.getContentPane ().getHeight ()), SpringLayout.NORTH, this);
    
    layout.putConstraint (SpringLayout.EAST, score [0], 0, SpringLayout.EAST, this);
    layout.putConstraint (SpringLayout.VERTICAL_CENTER, score [0], 0, SpringLayout.VERTICAL_CENTER, name [0]);
    
    for (int i = 1; i < 10; i ++)
    {
      layout.putConstraint (SpringLayout.WEST, name [i], 0, SpringLayout.WEST, this);
      layout.putConstraint (SpringLayout.NORTH, name [i], (int) (i==5?(0.5 * f.getContentPane ().getHeight ()): 0), SpringLayout.SOUTH, name [i-1]);
      
      layout.putConstraint (SpringLayout.EAST, score [i], 0, SpringLayout.EAST, this);
      layout.putConstraint (SpringLayout.VERTICAL_CENTER, score [i], 0, SpringLayout.VERTICAL_CENTER, name [i]);
    }
    update ();
  }
  
  /** The purpose of this method is to return the HighScoreManager reference.
    * @return HighScoreManager reference.
   */
  public HighScoreManager getManager () 
  {
    return m;
  }
  
  /** The purpose of this method is to update the screen.
    * <p> 
    * Loops and Conditions<br>
    * for (int i ... The purpose of this loop is to update all 10 high scores currently listed.<br>
    * if (... The purpose of this loop is to check if the number of high scores is 0 or not.
    * @param list This variable stores all the data that will be updated and placed on the screen.
    * @param i Loop counter for for loop.
    * @param n Helps format and display the high scores after they've been checked.
    * @param s Helps format and display the high scores after they've been checked.
   */
  public void update () 
  {
    ArrayList <Score> list = m.getData ();
    
    for (int i = 0; i < 10; i ++)
    {
      String n = i < list.size ()?(i+1) +  ". " + list.get (i).getName ():"";
      String s = i < list.size ()?"" + list.get (i).getScore ():"";
      name [i].setText (n);
      score [i].setText(s);
    }
    if (list.size () == 0)
      name [0].setText ("1.");
    
    revalidate ();
    repaint ();
  }
  
  /** This method allows the program to 'listen' for user input
    * and to perform the correct, respective action based on user input.
    * <p>
    * Loops and Conditions<br>
    * if (e.get... The purpose of this structure is to check if the user has selected to print or not.<br>
    * if (m.get... The purpose of this structure is to check if the current list of stored scores is greater than 0 or not.<br>
    * if (m.print.. The purpose of this structure is to check if the printing screwed up.
    * @param e Gives method access to methods and variables of ActionEvent.
   */
  public void actionPerformed (ActionEvent e) 
  {
    if (e.getActionCommand ().equals ("Print Scores"))
    {
      if (m.getData ().size () > 0)
      {
        if (m.print () == false)
          JOptionPane.showMessageDialog (f, "Something went wrong with the printing.", "Printing failed", JOptionPane.ERROR_MESSAGE);
      }
      else
          JOptionPane.showMessageDialog (f, "There's nothing to print!", "Blank scores", JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      m.clear ();
      update ();
      for (Score s : m.getData ())
        System.out.println (s.toString ());
    }
  }
}