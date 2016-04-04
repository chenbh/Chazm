import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.util.ArrayList;

/** @author Daniel Chen
  * @version 2.0, May 17, 2014
  * This program will generate the map and return
  * information about the map, such as if a certain
  * tile is available to be moved onto by the player 
  * character (The tile would not be available if
  * there was a wall there), the size of the map, the
  * location of the player, the player's score
  * at a given time, and more. Not only that, this program
  * will also solve for the shortest path possible
  * to complete the maze, which will be used to help
  * calculate the score of the player.
  * <p>
  * Variable Dictionary <br>
  * Name              Type              Purpose<br>
  * length            int               Stores the dimensions of the maze.<br>
  * tileX             int               Acts as a template to determine what the length-wise dimension of all the tiles should be.<br>
  * tileY             int               Acts as a template to determine what the height/width-wise dimension of all the tiles should be.<br>
  * steps             int               Stores the number of steps taken by the player.<br>
  * perfect           int               Stores the shortest distance possible for a particular map.<br>
  * maze              BufferedImage     Stores the image of the maze to be displayed during gameplay.<br>
  * tiles             BufferedImage[]   Stores the image of all the tiles that will be used to make the image of the maze.<br>
  * exit              Point             Stores the location of the exit.<br>
  * player            Point             Stores the current location of the player character on the map.
 */
public class MazeMap
{
  private int length, tileX, tileY, steps, perfect;
  private MazeTile [][] map;
  private BufferedImage maze;
  private BufferedImage [] tiles;
  private Point exit, player, entrance;
  
  /** The purpose of this method is to check and see which walls are removed
    * and will return an ArrayList of all possible locations that can be accessed from 
    * the tile.
    * <p>
    * Loops and Conditions<br>
    * if (y > 0... This structure is used to check if a particular wall is existant or not.<br>
    * if (y < 0... This structure is used to check if a particular wall is existant or not.<br>
    * if (x > 0... This structure is used to check if a particular wall is existant or not.<br>
    * if (x < 0... This structure is used to check if a particular wall is existant or not.
    * @param y Variable storing the location's vertical components.
    * @param x Variable storing the location's horizontal components.
    * @return An ArrayList of all possible locations that can be accessed from that tile.
   */
  public ArrayList <Point> getNeighbours (Point loc) 
  {
    int y = (int) loc.getY ();
    int x = (int) loc.getX ();
    ArrayList <Point> result = new ArrayList <Point> ();
    if (y > 0 && map [y - 1][x].isVisited ()== false)
      result.add (new Point (x, y -1));
    if ((y < this.length - 1) && map [y + 1][x].isVisited ()== false)
      result.add (new Point (x, y + 1));
    if (x > 0 && map [y][x -1].isVisited ()== false)
      result.add (new Point (x - 1, y));
    if ((x < this.length  - 1)&& map [y][x + 1].isVisited ()== false)
      result.add (new Point (x + 1, y));
    return result;    
  }
  
  /** The purpose of this method is the generate the map.
    * <p>
    * Loops and Conditions.<br>
    * while (... The purpose of this loop is to run recursively and generate the map.<br>
    * if (loc... The purpose of this structure is to make sure that the maze is legitimate.
    * @param neighbours This variable stores all the possible locations that can be accessed from a particular tile.
    * @param n This variable stores a random number that will be used in generating the maze.
    * @param d This variable represents direction and is also used to help generate the maze. (Which alls to be removed in which direction.)
    * @param newPoint This variable represents a neighbour that is to be removed. (Randomly chosen with variable 'n'.)
   */
  public void generateTile (Point loc, int direction) 
  {
    map [(int)loc.getY ()][(int)loc.getX()].toggleVisit ();
    map [(int)loc.getY ()][(int)loc.getX()].removeWall (direction);
    
    ArrayList <Point> neighbours = getNeighbours (loc);
    while (neighbours.size () != 0)
    {
      int n = (int) (Math.random () * neighbours.size ());
      Point newPoint = neighbours.remove (n);
      int d;
      if (loc.getY () > newPoint.getY ())
        d = 1;
      else if (loc.getX () < newPoint.getX ())
        d = 2;
      else if (loc.getY () < newPoint.getY ())
        d = -1;
      else 
        d = -2;
      map [(int)loc.getY ()][(int)loc.getX()].removeWall (d);
      generateTile (newPoint, -d);
      neighbours = getNeighbours (loc);
    }
  }
  
  /** The purpose of this method is to create a new map including
    * the location of the exit (random location at the top of the screen/map),
    * and the starting location at the centre/bottom of the screen. Furthermore,
    * this method also resets the step counter counting the number of steps taken.
    * <p>
    * Loops and Conditions<br>
    * for (int i... The purpose of this loop is to stitch pixel art tiles and the maze together.<br>
    * for (int n... The purpose of this loop is to stitch pixel art tiles and the maze together.
    * @param i Loop counter for for loop.
    * @param n Loop counter for for loop.
   */
  public void newMap (int length) 
  {
    map = new MazeTile [length][length];
    for (int i = 0; i < length; i ++)
    {
      for (int n =0; n < length; n++)
        map [i][n] = new MazeTile ();
    }
    player = new Point (length/2, length-1);
    entrance = new Point (length/2, length-1);
    exit = new Point ((int) (Math.random () * length), 0);
    generateTile (entrance, -1);
    map [(int) exit.getY ()][(int) exit.getX ()].removeWall (1);
    steps = 0;
  }
  
  /** The purpose of this method is to return the appropriate image for
    * a particular position on the map. (Will return appropriate image 
    * with appropriate rotations to fit into map.)
    * <p>
    * Loops and Conditions<br>
    * if (i ==...8) The purpose of this structure is to make sure the correct tiles are used for the correct placements on the map.<br>
    * if (i ==...14) The purpose of this structure is to make sure the correct rotations have been applied to the tile.
    * @param temp Variable storing the maze tile image.
    * @param r Variable storing how many rotations a tile should have.
    * @param i Variable storing which tile should be used.
    * @param m Gives program access to variables and methods of MazeTile.
    * @return The correct tile with proper rotations.
   */
  public BufferedImage getImage (MazeTile m) 
  {
    BufferedImage temp;
    int r;
    /* 
     * 1: end, 2
     * 2: end, 3
     * 3: turn, 3
     * 4: end, 0
     * 5: straight, 0
     * 6: turn, 0
     * 7, t, 3
     * 8: end, 1
     * 9: turn, 2
     * 10: straight, 1
     * 11, t, 2
     * 12: turn, 1
     * 13: t, 1
     * 14: t, 0
     * 15: all, 0
     */
    int i = Integer.parseInt (m.toString ());
    if (i == 1 || i == 2 || i == 4 || i == 8)
      temp = tiles [0];
    else if (i == 3 || i == 6 || i == 9 || i == 12)
      temp = tiles [1];
    else if (i == 5 || i == 10)
      temp = tiles [2];
    else if (i == 7 || i == 11 || i == 13 || i == 14)
      temp = tiles [3];
    else
      temp = tiles [4];
    
    if (i == 4 || i == 5 || i == 6 || i == 15 || i == 14)
      r = 0;
    else if (i == 8 || i == 10 || i == 12 || i == 13)
      r = 1;
    else if (i == 1 || i == 9 || i == 11)
      r = 2;
    else
      r = 3;
    
    return rotate (temp, r);
  }
  
  /** The purpose of this method is to rotate a particular image by
    * increments of 90 degrees. (90, 180, 270, etc.)
    * @return An image after it has been rotated a certain amount of times.
   */
  public BufferedImage rotate (BufferedImage original, int times) 
  {
    return new AffineTransformOp(AffineTransform.getQuadrantRotateInstance(times, original.getWidth ()/ 2.0, original.getHeight () / 2.0), AffineTransformOp.TYPE_BILINEAR).filter (original, null);
  }
  
  /** The purpose of this method is to actually stitch the correct
    * maze tile image together with it's placement on the map.
    * <p>
    * Loops and Structures<br>
    * for (int i... Helps with stitching the correct maze tile with the correct position on the map.<br>
    * for (int n... Helps with stitching the correct maze tile with the correct position on the map.
    * @param g2D Gives program access to variables and methods of Graphics2D
    * @param i Loop counter variable for for loop.
    * @param n Loop counter variable for for loop.
   */
  public void generateIMG () 
  {
    tileX = getImage (map [0][0]).getWidth ();
    tileY = getImage (map [0][0]).getHeight ();
    maze = new BufferedImage (length * tileX, length * tileY, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d= (Graphics2D) maze.getGraphics ();
    for (int i = 0; i < length; i ++)
    {
      for (int n = 0; n < length; n ++)
        g2d.drawImage (getImage (map [i][n]), n * tileX, i * tileY, null);
    }
  }
  
  /** The purpose of this method is to recursively call itself over and over
    * again in order to find the shortest distance possible from start to finish.
    * (Value returned will be 1 more than shortest distance.)
    * <p>
    * Loops and Conditions<br>
    * if (x == (int)... Checks if recursion is already at the exit - if it is, will break the recursion. <br>
    * if (walls [3] == ... The purpose of this structure is to check if the left wall is there or not.<br>
    * if (walls[1] == ... The purpose of this structure is to check if the right wall is there or not.<br>
    * if (walls[0] ==... The purpose of this structure is to check if the top wall is there or not.<br>
    * if (y < ... The purpose of this structure is to check if the bottom wall is there or not.<br>
    * if (temp != 0) The purpose of this structure is to prevent inaccurate dead ends.
    * @param x Index value for location on map.
    * @param y Index value for location on map.
    * @param walls Finds all the walls for their respective tiles.
    * @param result Finds where available locations are. (Where walls are not existant.)
    * @param temp Helps prevent inaccurate dead ends.
    * @return The shortest distance possible in order to solve the maze.
   */
  private int mazeSolver (int x, int y) 
  {
    map [y][x].toggleVisit ();
    boolean [] walls = map [y][x].getWalls ();
    int result = 0;
    int temp;
    if (x == (int) exit.getX () && y == (int) exit.getY ())
      return 1;
    
    if (walls [3] == false && map [y][x - 1].isVisited ())
    {
      temp = mazeSolver (x - 1, y);
      if (temp != 0)
        result = temp;
    }
    if (walls [1] == false && map [y][x + 1].isVisited ())
    {
      temp = mazeSolver (x + 1, y);
      if (temp != 0)
        result = temp;
    }
    if (walls [0] == false && map [y - 1][x].isVisited ())
    {
      temp = mazeSolver (x, y - 1);
      if (temp != 0)
        result = temp;
    } 
    if (y < (this.length - 1) && walls [2] == false && map [y + 1][x].isVisited ()) //starting backflow
    { 
      temp = mazeSolver (x, y + 1);
      if (temp != 0)
        result = temp;
    }
    return result == 0?0:result + 1;
  }
  
  /** The purpose of this method is to return the map as an image.
    * @return The map.
   */
  public BufferedImage getMap () 
  {
    return maze;
  }
  
  /** The purpose of this method is to return the length of the maze.
    * @return Length of maze.
   */
  public int getSize () 
  {
    return length;
  }
  
  /** The purpose of this method is to return the location of the player.
    * @return Player location.
   */
  public Point getLocation () 
  {
    return player;
  }
  
  /** The purpose of this method is to return the array containing number of steps taken by player
    * as well as the shortest distance possible to the exit.
    * @return Steps taken and shortest distance.
   */
  public int [] getScore ()
  {
    int [] result = {steps, mazeSolver (length/2, length-1)-1};
    return result;
  }
  
  /** The purpose of this method is to return whether or not the player is at the entrance or not.
    * @return Whether player is at entrance or not.
   */
  public boolean atEntrance () 
  {
    return player.equals (entrance);
  }
  
  /** The purpose of this method is to return whether the player has reached the end or not.
    * @return If the player is at the end or not.
   */
  public boolean atExit () 
  {
    return exit.equals (player);
  }
  
  /** The purpose of this method is to move the player around on the map
    * based on which direction they chose to move in.
    * <p>
    * Loops and Variables<br>
    * if (walls... The purpose of this structure is to check if there is a wall a particular direction on the tile the player is currently on.<br>
    * if (direction... The purpose of this structure is to check which neighbours are available and will move the player accordingly.
    * @param direction This variable stores the direction the user chose to move in.
    * @param walls This variable stores all the walls around the player at a given moment.
    * @return Whether the player has moved or not. (Walking into a wall does not count.)
   */
  public boolean move (int direction) 
  {
    boolean [] walls = map [(int)player.getY ()][(int)player.getX ()].getWalls ();
    if (walls [direction] == false)
    {
      if (direction == 0)
        player.translate (0, -1);
      else if (direction == 1)
        player.translate (1, 0);
      else if (direction == 2)
        player.translate (0, 1);
      else
        player.translate (-1, 0);
      steps ++;
      return true;
    }
    return false;
  }
  
  /** This is the class constructor. It creates
    * the new map.
    * <p>
    * Loops and Conditions<br>
    * try{}catch(IOEx... The purpose of this structure is to make sure the program will not crash when accessing maze tile image files.<br>
    * for (int... The purpose of this structure is to read in all the tile images.
    * @param length Stores the length of the maze.
    * @param names Stores names of the maze tile files so that they can be accurately located and accessed.
    * @param e variable associated to portion of code that prevents program from crashing due to an IOException error.
    * @exception IOException When program does not properly access maze images or maze images do not exist.
   */
  public MazeMap (int length) 
  {
    this.length = length;
    tiles = new BufferedImage [5];
    String [] names = {"End.png", "Turn.png", "Straight.png", "T.png", "All.png"};
    try
    {
      for (int i = 0; i < 5; i ++)
        tiles [i] = ImageIO.read (getClass ().getResource ("/resources/" + names [i]));
    }
    catch (IOException e)
    {
      e.printStackTrace ();
    }
    
    newMap (length);
    generateIMG ();
  }
}