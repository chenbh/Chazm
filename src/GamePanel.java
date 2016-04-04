import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

/**@author Daniel Chen
  * @version 3.0, June 11, 2014
  * The purpose of the program is to deal with all the in-game action
  * including number of lives, difficulty, experience points,
  * character evolutions, etc.
  * <p>
  * Variable Dictionary<br>
  * Name                Type                 Purpose<br>
  * difficulty          int                  Stores difficulty.<br>
  * imageWidth          int                  Stores tile width.<br>
  * imageHeight         int                  Stores tile height.<br>
  * level               int                  Stores experience level.<br>
  * score               int                  Stores score.<br>
  * lives               int                  Stores number of lives.<br>
  * xp                  int                  Stores experience points.<br>
  * marginWidth         int                  Stores tile margin width.<br>
  * marginHeight        int                  Stores tile margin height.<br>
  * map                 BufferedImage        Stores image of maze.<br>
  * charactersOriginal  ImageIcon [][]       Stores original character icons.<br>
  * character           ImageIcon []         Stores current character icon.<br>
  * layout              SpringLayout         Gives program access to SpringLayout variables and methods.<br>
  * maze                MazeMap              Gives program access to MazeMap methods and variables.<br>
  * player              JLabel               JLabel to contain Icon of player character - to be placed on screen.<br>
  * stats               JLabel []            JLabel containing stats of character - lives and xp points.<br>
  * f                   Main                 Gives program access to variables and methods of Main.<br>
  * e                   EndPanel             Gives program access to variables and methods of EndPanel.<br>
  * d                   JDialog              Gives program access to variables and methods of JDialog.<br>
  * MAX_LIVES           final int            Sets the maximum amount of lives as 9. (Cannot be changed.)<br>
  * b                   TrapBox              Gives the program access to trap box methods and variables.
 */
public class GamePanel extends JPanel implements KeyListener, ActionListener
{
  private int difficulty, imageWidth, imageHeight, level, score, lives, xp, marginWidth, marginHeight;
  private BufferedImage map;
  private ImageIcon [][] charactersOriginal;
  private ImageIcon [] character;
  private SpringLayout layout;
  private MazeMap maze;
  private JLabel player;
  private JLabel [] stats;
  private Main f;
  private EndPanel e;
  private JDialog d;
  private final int MAX_LIVES = 9;
  private TrapBox b;
  
  /** This is the class constructor. This constructor read in all the required files.
    * (Currently unresized when first read in.) Furthermore, this constructor also
    * sets up all the default values for stats that would appear at the start 
    * of a game.
    * <p>
    * Loops and Conditions<br>
    * for (int i =... The purpose of this loop is to read in a character image.<br>
    * for (int n =... The purpose of this loop is to read in a character image.<br>
    * for (int i =... The purpose of this loop is to set the default number of lives at the start of a level.<br>
    * try{}catch(IOEx.... The purpose of this structure is to prevent the program from crashing when reading in all the image files. (Character images).
    * @param end Gives program access to variables and methods of EndPanel.
    * @param frame Gives program access to Main frame.
    * @param img Template for getting character graphics.
    * @param e Variable associated to portion of code used to prevent program from crashing due to an IOException error.
    * @exception IOException If program incorrectly reads character image or character image does not exist.
   */
  public GamePanel (EndPanel end, Main frame)
  {
    e = end;
    f = frame;
    setFocusable (true);
    layout = new SpringLayout ();
    setLayout (layout);
    charactersOriginal = new ImageIcon [3][3];
    character = new ImageIcon [3];
    
    player = new JLabel (new ImageIcon (getClass ().getResource ("/resources/Error.png")));
    for (int i = 0; i < charactersOriginal.length; i ++)
    {
      for (int n = 0; n < charactersOriginal [0].length; n ++)
      {
        charactersOriginal [i][n] = new ImageIcon (getClass ().getResource ("/resources/Characters/" + i + n + ".png"));
      }
    }
    add (player);
    
    stats = new JLabel [MAX_LIVES + 1];
    for (int i = 0; i < MAX_LIVES; i ++)
    {
      BufferedImage img = new BufferedImage (35, 35 , BufferedImage.TYPE_INT_ARGB);
      try
      {
        img.getGraphics ().drawImage (ImageIO.read (getClass ().getResource ("/resources/Heart.png")), 0, 0, img.getWidth (), img.getHeight (), null);
      }
      catch (IOException e)
      {
      }
      stats [i]= new JLabel (new ImageIcon (img));
      add (stats [i]);
      layout.putConstraint (SpringLayout.EAST, stats [i], -i* stats [i].getIcon ().getIconWidth (), SpringLayout.EAST, this);
      layout.putConstraint (SpringLayout.SOUTH, stats [i], 0, SpringLayout.SOUTH, this);
    }
    stats [MAX_LIVES] = new JLabel (); //Ensures last one is the xp
    stats [MAX_LIVES].setFont (new Font ("Arial", Font.BOLD, 20));
    stats [MAX_LIVES].setForeground (Color.GREEN);
    add (stats [MAX_LIVES]);
    layout.putConstraint (SpringLayout.WEST, stats [MAX_LIVES], 0, SpringLayout.WEST, this);
    layout.putConstraint (SpringLayout.SOUTH, stats [MAX_LIVES], 0, SpringLayout.SOUTH, this);
    
    b = new TrapBox (f);
    addKeyListener (this);
  }
  
  /** The purpose of this method is to reset all data.
    * (Preparation for the start of a new game.)
   */
  public void startNewGame (int i) 
  {
    e.reset ();
    level = 0;
    xp = 0;
    newGame (i);
  }
  
  /** The purpose of this method is to actually generate the new game.
    * (The previous method sets up for the new game.) This method
    * will generate the correct level difficulty, update all characters,
    * and update the screen.
    * <p>
    * Loops and Conditions<br>
    * if (diffiul... The purpose of this method is to make sure the level with the correct difficulty is generated.
    * @param i The variable represents the desired level of difficulty.
   */
  public void newGame (int i) 
  {
    f.switchPanel ("Loading");
    difficulty = i;
    lives = 5 + 2 * difficulty;
    if (difficulty == 0)
      maze = new MazeMap (10);
    else if (difficulty == 1)
      maze = new MazeMap (15);
    else
      maze = new MazeMap (25);
    map = maze.getMap ();
    
    imageWidth = f.getContentPane().getWidth () / maze.getSize ();
    imageHeight = f.getContentPane().getHeight ()/ maze.getSize ();
    marginWidth = (f.getContentPane().getWidth() - imageWidth * maze.getSize())/2;
    marginHeight = (f.getContentPane().getHeight() - imageHeight * maze.getSize())/2;
    
    updateSprites ();
    player.setIcon (character [0]);
    updateScreen ();
    
    f.switchPanel ("Game");
    requestFocusInWindow ();
  }
  
  /** The purpose of this method is to update/resize the character sprite.
    * <p>
    * Loops and Conditions<br>
    * for (int i = ... The purpose of this loop is to make sure all characters are updated (resized) for the game.
    * @param i Loop counter for for loop.
    * @param img Template for resizing all characters.
   */
  private void updateSprites ()
  {
    for (int i = 0; i < 3; i ++)
    {
      BufferedImage img = new BufferedImage (imageWidth, imageHeight , BufferedImage.TYPE_INT_ARGB);
      img.getGraphics ().drawImage (charactersOriginal [level][i].getImage (), 0, 0, imageWidth, imageHeight, null);
      character [i]= new ImageIcon (img);
    }
  }
  
  /** The purpose of this method is to draw the graphics.
    * <p>
    * Loops and Conditions<br>
    * if (map !=... The purpose of this structure is to make sure the map image is existant.
    * @param g Gives program access to variables and methods of Graphics.
   */
  public void paintComponent(Graphics g) 
  {
    int screenWidth = f.getContentPane().getWidth ();
    int screenHeight = f.getContentPane().getHeight ();
    super.paintComponent (g); 
    if (map != null)
      g.drawImage(map, marginWidth, marginHeight, imageWidth * maze.getSize(), imageHeight * maze.getSize(), Color.WHITE, f);
  }
  
  /** The purpose of this method is to update the screen. 
    * More specifically, to update the player position on
    * the map as well as the player status (Lives & xp).
    * <p>
    * for (int i... The purpose of this loop is to update the number of lives the player has.<br>
    * if (i < ... The purpose of this structure is to prevent the program from drawing lost lives on the screen.
    * @param i Loop counter for for loop.
   */
  private void updateScreen () 
  {
    layout.putConstraint (SpringLayout.WEST, player, marginWidth + imageWidth * (int) maze.getLocation ().getX(), SpringLayout.WEST, this);
    layout.putConstraint (SpringLayout.NORTH, player, marginHeight + imageHeight * (int) maze.getLocation ().getY(), SpringLayout.NORTH, this);
    for (int i = 0; i < MAX_LIVES; i ++)
    {
      if (i < lives)
        stats [i].setVisible (true);
      else
        stats [i].setVisible (false);
    }
    stats [MAX_LIVES].setText ("Level " + (level + 1) + ", " + xp + "/20 experience");
    revalidate ();
    repaint ();
  }
  
  /** The purpose of this method is to allow the player
    * to pause the game during game play. Not only that,
    * this method also allows the player to continue playing
    * or to return to menu. 
    * @param label Stores the text informing player that game is paused.
    * @param stats Stores the text informing player how many lives he/she has left and how many xp points he/she has.
   */
  private void pauseGame () 
  {
    d = new JDialog (f, "Game paused");
    JLabel label = new JLabel ("The game has been paused.");
    JLabel stats = new JLabel (lives + " lives left. " + xp + "/20 exp points");
    JButton mainMenu = new JButton ("Quit");
    mainMenu.setMnemonic (KeyEvent.VK_Q);
    JButton resume = new JButton ("Resume");
    resume.setMnemonic (KeyEvent.VK_R);
    
    d.add (label);
    d.add (stats);
    d.add (mainMenu);
    d.add (resume);
    
    mainMenu.addActionListener (this);
    resume.addActionListener (this);
    d.setModalityType (Dialog.ModalityType.APPLICATION_MODAL);
    d.setSize (200, 110);
    d.setResizable (false);
    d.setLayout (new FlowLayout());
    d.setLocationRelativeTo (this);
    d.setVisible (true);
  }
  
  /** The purpose of this method is to check which option the player
    * has made and to perform the corresponding action based on user
    * choice.
    * <p>
    * Loops and Conditions<br>
    * if (ae.getAction...("Quit")) The purpose of this structure is to check if the player chose to quit or not.<br>
    * if (e.get... The purpose of this structure is to check if the user decided to quit half way, or not as well as to allow the user to return to menu.<br>
    * if (ae.getAction...("Resume")) The purpose of this structure is to check if the player chose to continue.
    * @param ae Variable associated to giving program the ability to 'listen'.
   */
  public void actionPerformed (ActionEvent ae) 
  {
    if (ae.getActionCommand ().equals ("Quit"))
    {
      if (e.getScore () != 0)
        e.display ("Chickened out");
      else
        f.switchPanel ("Main Menu");
      d.dispose ();
      f.setTitle ("The Chazm");
    }
    else 
    {
      if (ae.getActionCommand ().equals ("Resume"))
      {
        d.dispose ();
        f.setTitle ("The Chazm");
      }
    }
  }
  
  /** The purpose of this method is to allow the program to listen for keyboard actions.
    * <p>
    * Loops and Conditions<br>
    * if (ke.get...VK_ESCAPE) The purpose of this structure is to check if the user pressed the ESC button or not.<br>
    * if (ke.get...VK_W) The purpose of this structure is to check which key the player pressed to move the character (WASD/Arrow Keys).<br>
    * if (d != -1 &&... The purpose of this structure is to check if the player was moved or not, and if the player has moved, to update all sprites.<br>
    * switch (b.create... The purpose of this structure is to create the trap. If the player answers correctly, xp will increase. If the player answers incorrectly, a life will be lost.<br>
    * if (xp == 20) The purpose of this structure is to upgrade the character if they have gotten 20 xp points.<br>
    * if (lives == 0) The purpose of this structure is to make sure the player returns to menu after they have lost all their lives. <br>
    * if (maze.atEx... The purpose of this structure is to check if the player is at the exit or not.
    * @param ke Gives program access to variables and methods of KeyEvent.
   */
  public void keyPressed (KeyEvent ke) 
  {
    if (ke.getKeyCode () == KeyEvent.VK_ESCAPE)
    {
      f.setTitle ("The Chazm - PAUSED");
      pauseGame ();
    }
    else
    {
      int d = -1;
      if (ke.getKeyCode () == KeyEvent.VK_UP || ke.getKeyCode () == KeyEvent.VK_W)
      {
        d = 0;
        player.setIcon (character [0]);
      }
      else if (ke.getKeyCode () == KeyEvent.VK_DOWN || ke.getKeyCode () == KeyEvent.VK_S)
      {
        if (!maze.atEntrance ())
          d = 2;
        player.setIcon (character [1]);
      }
      else if (ke.getKeyCode () == KeyEvent.VK_LEFT || ke.getKeyCode () == KeyEvent.VK_A)
      {
        d = 3;
        player.setIcon (character [1]);
      }
      else if (ke.getKeyCode () == KeyEvent.VK_RIGHT || ke.getKeyCode () == KeyEvent.VK_D)
      {
        d = 1;
        player.setIcon (character [2]);
      }
      if (d != -1 && maze.move (d))
      {
        updateScreen (); 
        switch (b.createTrap ())
        {
          case 0:
            xp = level == 2?0:xp + 1;
          break;
          case 1:
            lives --;
            break;
        }
        if (xp == 20)
        {
          level ++;
          xp = 0;
          updateSprites ();
        }
        if (lives == 0)
          e.display ("Level failed");
        if (maze.atExit ())
          e.display (maze.getScore (), lives, difficulty, this);
      }
      updateScreen (); //Updates lives + score after completion of trap question
    }
  }
  
  /** These methods are only required for KeyListener.
    * @param e Gives method access to variables and methods of KeyEvent.
   */
  public void keyTyped (KeyEvent e) {} //required for keylistener
  public void keyReleased(KeyEvent e) {} //required for keylistener
}
