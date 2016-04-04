import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Dialog;

/** @author Daniel Chen
  * @version 2.0, June 11, 2014
  * The purpose of this program is to deal with the level completion screen.
  * The level completion screen will display a unique background and various
  * pieces of information informing player of their current score. (Lives,
  * difficulty bonus, steps taken, etc.)
  * <p>
  * Variable Dictionary<br>
  * Name              Type                 Purpose<br>
  * layout            SpringLayout         Gives program access to methods and variables of SpringLayout.<br>
  * nextLevel         JButton              Button associated to allowing player to continue to next level.<br>
  * g                 GamePanel            Gives program access to methods and variables of GamePanel.<br>
  * difficulty        int                  Stores the level of difficulty.<br>
  * score             int                  Stores the player score.<br>
  * h                 HighScorePanel       Gives program access to methods and variables of HighScorePanel.<br>
  * f                 Main                 Gives program access to Main frame.<br>
  * scoring           JLabel[][]           A series of labels containing information on how the scoring was done that are to be placed on the screen.<br>
  * title             JLabel               A label that stores the title of the screen. <br>
  * image             Image                Stores the background image.<br>
  * d                 JDialog              Gives program access to methods and variables of JDialog.<br>
  * inputField        JTextField           Variable associated to portion of code that allows user to enter in name (if required).
 */
public class EndPanel extends JPanel implements ActionListener
{
  /**
   * The layout manager for this class.
   */
  private SpringLayout layout;
  /**
   * The JButton that allows the user to continue to the next level. Can be shown or hidden depending on difficulty.
   */
  private JButton nextLevel;
  /**
   * The GamePanel that called the display method.
   */
  private GamePanel g;
  /**
   * The difficulty of the level.
   */
  private int difficulty;
  /**
   * The score of the user.
   */
  private int score;
  /**
   * The panel in charge of displaying and updating the highscores
   */
  private HighScorePanel h;
  /**
   * The JFrame that this panel is part of.
   */
  private Main f;
  /**
   * Array of JLabels that displays what the user scored.
   */
  private JLabel [][] scoring;
  /**
   * The title to be displayed at the top of the screen (Level Cleared, Chickened Out, Level Failed).
   */
  private JLabel title;
  /**
   * The background image.
   */
  private Image image;
  /**
   * The JDialog box asking for the user's name.
   */
  private JDialog d;
  /**
   * The text field that the user entered their names in.
   */
  private JTextField inputField;
  
  /**Sets up the panel and its components. The background image is read in, the preferred size of this panel is determined, layout
   * manager is created, the buttons created, positioned, and ActionListeners (this) attached. The JLabels that displays the user's
   * score is also created and positioned.
   * <p>
   * Loops and Conditions<br>
   * for (... The purpose of this loop is to simplify the code that formats the screen. (Constraints on the labels.)
   * @param back Button associated to allowing the user to return to menu.
   * @param highScorePanel The panel in charge of displaying and updating the high scores.
   * @param frame The JFrame that this panel is part of.
   */
  public EndPanel (HighScorePanel highScorePanel, Main frame)
  {
    image = Toolkit.getDefaultToolkit().createImage(getClass ().getResource ("/resources/Congrats.png"));
    f = frame;
    h = highScorePanel;
    
    setPreferredSize (new Dimension (f.getContentPane ().getWidth (), f.getContentPane ().getHeight ()));
    layout = new SpringLayout ();
    setLayout (layout);
    
    JButton back = new JButton ("Return to Main Menu");
    back.addActionListener (this);
    back.setMnemonic (KeyEvent.VK_R);
    add (back);
    layout.putConstraint (SpringLayout.SOUTH, back, (int) -50, SpringLayout.SOUTH, this);
    layout.putConstraint (SpringLayout.HORIZONTAL_CENTER, back, 0, SpringLayout.HORIZONTAL_CENTER, this);
    
    nextLevel = new JButton ("Continue to Next Level");
    nextLevel.setMnemonic (KeyEvent.VK_C);
    nextLevel.addActionListener (this);
    add (nextLevel);
    layout.putConstraint (SpringLayout.SOUTH, nextLevel, -20, SpringLayout.NORTH, back);
    layout.putConstraint (SpringLayout.HORIZONTAL_CENTER, nextLevel, 0, SpringLayout.HORIZONTAL_CENTER, this);
    
    title = new JLabel ();
    title.setFont (new Font ("Serif", Font.BOLD, 56));
    layout.putConstraint (SpringLayout.NORTH, title, 150, SpringLayout.NORTH, this);
    layout.putConstraint (SpringLayout.HORIZONTAL_CENTER, title, 0, SpringLayout.HORIZONTAL_CENTER, this);
    add (title);
    
    Font font = new Font ("Serif", Font.BOLD, 40);
    String [] names = {"Previous score:", "Level score:", "Difficulty bonus:", "Lives:", "Total:"};
    
    scoring = new JLabel [5][3];
    scoring [0][0] = new JLabel (names [0]);
    scoring [0][0].setFont (font);
    add (scoring [0][0]);
    layout.putConstraint (SpringLayout.NORTH, scoring [0][0], 250, SpringLayout.NORTH, this);
    layout.putConstraint (SpringLayout.WEST, scoring [0][0], 100, SpringLayout.WEST, this);
    
    scoring [0][1] = new JLabel ();
    scoring [0][1].setFont (font);
    add (scoring [0][1]);
    layout.putConstraint (SpringLayout.VERTICAL_CENTER, scoring [0][1], 0, SpringLayout.VERTICAL_CENTER, scoring [0][0]);
    layout.putConstraint (SpringLayout.HORIZONTAL_CENTER, scoring [0][1], 0, SpringLayout.HORIZONTAL_CENTER, this);
    
    scoring [0][2] = new JLabel ();
    scoring [0][2].setFont (font);
    add (scoring [0][2]);
    layout.putConstraint (SpringLayout.VERTICAL_CENTER, scoring [0][2], 0, SpringLayout.VERTICAL_CENTER, scoring [0][0]);
    layout.putConstraint (SpringLayout.EAST, scoring [0][2], -200, SpringLayout.EAST, this);
    
    for (int i = 1; i < 5; i ++)
    {
      scoring [i][0] = new JLabel (names [i]);
      scoring [i][0].setFont (font);
      add (scoring [i][0]);
      layout.putConstraint (SpringLayout.NORTH, scoring [i][0], 30, SpringLayout.SOUTH, scoring [i - 1][0]);
      layout.putConstraint (SpringLayout.WEST, scoring [i][0], 0, SpringLayout.WEST, scoring [i - 1][0]);
      
      scoring [i][1] = new JLabel ();
      scoring [i][1].setFont (font);
      add (scoring [i][1]);
      layout.putConstraint (SpringLayout.VERTICAL_CENTER, scoring [i][1], 0, SpringLayout.VERTICAL_CENTER, scoring [i][0]);
      layout.putConstraint (SpringLayout.HORIZONTAL_CENTER, scoring [i][1], 0, SpringLayout.HORIZONTAL_CENTER, this);
      
      scoring [i][2] = new JLabel ();
      scoring [i][2].setFont (font);
      add (scoring [i][2]);
      layout.putConstraint (SpringLayout.VERTICAL_CENTER, scoring [i][2], 0, SpringLayout.VERTICAL_CENTER, scoring [i][0]);
      layout.putConstraint (SpringLayout.EAST, scoring [i][2], -200, SpringLayout.EAST, this);
    }
    
    revalidate ();
    repaint ();
  }
  
  /**Called when the user failed to pass a level. Only the previous level's score is accounted for and everything else is 0.
   * The continue to next level button is not shown.
   * @param text The string to display at the top (either Chickened out or Level failed
   */
  public void display (String text)
  {
    f.switchPanel ("End");
    title.setText (text);    
    scoring [0][2].setText ("" + score);
    scoring [1][1].setText ("");
    scoring [1][2].setText ("0");
    scoring [2][1].setText ("");
    scoring [2][2].setText ("0");
    scoring [3][1].setText ("");
    scoring [3][2].setText ("0");
    scoring [4][2].setText ("" + score);
    nextLevel.setVisible (false);
    revalidate ();
    repaint ();
  }
  
  /**Displays the user's score for the level. The user's score is calculated using the shortest distance to the exit from
   * the entrance divided by their steps multiplied by 100 and and bonuses. The level bonus is the difficulty of the level
   * times 50 (easy = 0, hard = 2), the amount of lives they have left over is multiplied by 20 to get the lives bonus.
   * As the user's scores and bonuses are displayed, if the user is on hard, the continue to next level button will not be
   * shown. Updates the user's score.
   * <p>
   * Loops and Conditions<br>
   * if (difficul... The purpose of this structure is to check which level of difficulty to display.
   * @param mazeData Int array containg the amount of steps the user has taken and the shortest amount of steps to the exit.
   * @param lives The amount of lives the user has at the end of the level.
   * @param difficulty The difficulty of the maze.
   * @param game Gives access to the methods of the GamePanel that called this method.
   */
  public void display (int [] mazeData, int lives, int difficulty , GamePanel game)
  {
    g = game;
    f.switchPanel ("End");
    this.difficulty = difficulty;
    
    title.setText ("Level Cleared!");
    
    scoring [0][2].setText ("" + score);
    
    scoring [1][1].setText (mazeData [1] + "/" + mazeData [0]);
    scoring [1][2].setText ("" + (int) (((double) mazeData [1]/mazeData[0]) * 100));
    
    scoring [2][1].setText (difficulty + " x " + 50);
    scoring [2][2].setText ("" + difficulty * 50);
    
    scoring [3][1].setText (lives + " x " + 20);
    scoring [3][2].setText ("" + lives * 20);
    
    score += ((double) mazeData [1]/mazeData[0]) * 100 + difficulty * 50 + lives * 20;
    
    scoring [4][2].setText ("" + score);
    
    if (difficulty == 2)
      nextLevel.setVisible (false);
    else
      nextLevel.setVisible (true);
    revalidate ();
    repaint ();
  }
  
  /**The purpose of this method is to draw the background image onto the screen.
    * <p>
    * Loops and Conditions<br>
    * if (image != n... The purpose of this structure is to check if the background image is existant or not.
   * @param g The graphics object the background is to be painted on.
   */
  public void paintComponent(Graphics g) 
  {     
    super.paintComponent (g); 
    if (image != null)
      g.drawImage(image, 0, 0, f.getContentPane().getWidth (), f.getContentPane().getHeight (), Color.WHITE, f);
  }
  
  /**Resets the score that is being tracked in this class.
   */
  public void reset ()
  {
    score = 0;
  }
  
  /**Gets the score of the user.
   * @return The score of the user.
   */
  public int getScore ()
  {
    return score;
  }
  
  /**Called when the user has beaten a high score and is required to enter their names. Will only be called by this
   * class when the user presses the Return to Main Menu button and has beaten a high score. Creates a JDialog window
   * where teh user will be prompted to enter their name in the text box provided. This class will be attached as the
   * ActionListener for the confirm and cancel buttons.
   * @param prompt The prompt telling the user to enter their name.
   * @param confirm The button the user presses to save their name.
   * @param cancel The button the user presses to prevent from saving their name.
   */
  private void enterName ()
  {
    d = new JDialog (f, "New high score");
    JLabel prompt = new JLabel ("Please enter your name:");
    prompt.setFont (new Font ("Serif", Font.PLAIN, 12));
    inputField = new JTextField (25);
    JButton confirm = new JButton ("Enter");
    confirm.setMnemonic (KeyEvent.VK_E);
    JButton cancel = new JButton ("Cancel");    
    cancel.setMnemonic (KeyEvent.VK_C);
    
    d.add (prompt);
    d.add (inputField);
    d.add (confirm);
    d.add (cancel);
    
    confirm.addActionListener (this);
    cancel.addActionListener (this);
    d.setModalityType (Dialog.ModalityType.APPLICATION_MODAL);
    d.setSize (450, 90);
    d.setResizable (false);
    d.setLayout (new FlowLayout());
    d.setLocationRelativeTo (this);
    d.requestFocusInWindow ();
    d.setVisible (true);
  }
  
  /**Method for handling button commands. If the user pressed the Continue to Next Level button, a maze with an increased difficulty will
   * be generated. If the user pressed the Return to Main Menu button, it will prompt the user to enter their names if they have beaten
   * high score. They will get the choice to not enter their name, enter their name, or not go back to the main menu (if they pressed by
   * mistake. If the user pressed Enter or Cancel (from the JDialog prompting them to enter their names) the JDialog will be removed. If
   * the user pressed Enter, if their name is valid (not blank), the high score will be updated and the user will be shown the high scores
   * screen. If the name is blank, the user will be informed that they cannot enter blank. If the user pressed cancel, it will return to
   * the main menu.
   * <p>
   * Loops and Conditions<br>
   * if (ae.get...("Continue to Next Level")) The purpose of this structure is to check if the user has selected to continue to the next level or not.<br>
   * if (score != 0 && h.get... The purpose of this structure is to check if the user has beaten a high score or not.<br>
   * switch (JOptionPane... The purpose of this structure is to check if the player would like to be entered to the high scores or not.<br>
   * if (inputField... The purpose of this structure is to check if the name box is empty or not.<br>
   * if (ae.getActionCommand... The purpose of this structure is to check if the user selects to cancel or not. (Cancel entering their name and entering the high scores list.)
   * @param ae The ActionEvent that is generated.
   */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getActionCommand ().equals ("Continue to Next Level"))
    {
      g.newGame (difficulty + 1);
    }
    else if (ae.getActionCommand ().equals ("Return to Main Menu"))
    {
      if (score != 0 && h.getManager ().checkScore (score))
      {
        switch (JOptionPane.showConfirmDialog (this, "You have beaten a high score, would you like to save it?", "New highscore", JOptionPane.YES_NO_CANCEL_OPTION))
        {
          case JOptionPane.YES_OPTION:
            enterName ();
            break;
          case JOptionPane.NO_OPTION:
            f.switchPanel ("Main Menu");
            break;
        }
      }
      else
        f.switchPanel ("Main Menu");
    }
    else if (ae.getActionCommand ().equals ("Enter"))
    {
      if (!inputField.getText ().equals (""))
      {
        h.getManager ().add (inputField.getText (), score);
        h.update ();
        d.dispose ();
        f.switchPanel ("High Scores");
      }
      else
      {
        JOptionPane.showMessageDialog (this, "Please enter a valid name!", "New highscore", JOptionPane.ERROR_MESSAGE);
        inputField.setText ("");
      }
    }
    else 
    {
      if (ae.getActionCommand ().equals ("Cancel"))
      {
        f.switchPanel ("Main Menu");
        d.dispose ();
      }
    }
  }
}