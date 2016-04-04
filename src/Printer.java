import java.awt.print.PrinterJob;
import java.awt.print.Printable;
import java.awt.print.Pageable;
import java.awt.print.PrinterException;
import java.awt.print.PageFormat;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/** @author Daniel Chen
  * @version 2.1, May 15, 2014 
  * Helper class desinged to make printing easier. Basic principle is that this class will act like an output stream allowing
  * for text and graphics be outputted on it. Text are displayed on a line by line format (location of the beginning of the
  * text is preset and follows a uniform pattern. Images can be displayed anywhere and options are available to resize a
  * given image based on the dimensions of the paper.
  * <p>
  * Variable Dictionary<br>
  * Name                 Type                           Purpose<br>
  * left                 Arraylist<String>              Variable used to store all the text that is to be aligned with the left side of the page.<br> 
  * right                Arraylist<String>              Variable used to store all the text that is to be aligned with the right side of the page.<br>    
  * centre               Arraylist<String>              Variable used to store all the text that is to be aligned with the centre side of the page.<br>
  * images               Arraylist<BufferedImage>       Variable used to store all the images that are to be placed and printed on the page.<br>
  * imageLocations       Arraylist<Point>               Variable used to store the locations of all the images on the page.<br>
  * needsResizing        Arraylist<Boolean>             Variable used to toggle wher or not a particular image needs resizing or not.<br>
  * imageSizePercent     Arraylist<int []>              Variable storing all the percentages the images should be resized to.<br>  
  * maxLines             int                            Variable used to store the maximum number of lines allowed on the page.<br>
  * lineSpacing          double                         Variable used to store the spacing between the lines. <br>
  * newLine              boolean                        Variable used for toggling whether or not there will be a new line.<br>
  * pageFormat           PageFormat                     Gives program access to variables and methods of PageFormat.<br>
  * font                 Font                           Gives program access to variables and methods of Font.
  */

/* Update notes:
 15/5/2014 - Created, print and println based on System.out
 
 2/6/2014 - Renamed startPrinting () to printUsingDialog (). Removed print () so only println () can be used. Added new overloads
 to println () which allows printing to different alliment (left, right, center). Added image printing which can print an image
 directly or resize it based a percentage of the paper's dimensions.
 WARNING: This class will not check for overlaps, make sure you predict the location of everything if you dont want overlapping.
 
 02/6/2014 - Added support for printing of multiple pages. Readded print (). Drawing of images now behave like Microsoft Word in
 that it will not print anything that is out of the 1 inch margins. Margins are now unchangeable.
 */

public class Printer implements Printable, Pageable
{
  /**
   * ArrayList containg the text to be printed on the differen allignments.
   */
  private ArrayList <String> left, center, right;
  /**
   * ArrayList containg the images to be printed.
   */
  private ArrayList <BufferedImage> images;
  /**
   * ArrayList containg the locations of the images to be displayed.
   */
  private ArrayList <Point> imageLocations;
  /**
   * ArrayList containg the whether or not an image should be resized.
   */
  private ArrayList <Boolean> needsResizing;
  /**
   * ArrayList containg the percentage of the paper's dimensions the images takes up.
   */
  private ArrayList <int []> imageSizePercent;
  /**
   * Maximum number of lines that can fit on one page.
   */
  private int maxLines;
   /**
   * Spacing of the lines
   */
  private double lineSpacing;
 /**
   * Boolean keeping track of whether or not it is a new line.
   */
  private boolean newLine = true;
  /**
   * PageFormat for all of the pages.
   */
  private PageFormat pageFormat;
  /**
   * Font for the text to be printed.
   */
  private Font font;
  
  
  /**
   * Queues a String alligned on the left side to be printed. Allows for addtional text to be printed on same line.
   * @param text The text to be displayed on the left.
   */
  public void print (String text)
  {
    print (text, "", "");
  }

  /**
   * Queues a line with different text alligned on different places to be printed. Each String will be drawn starting at different locations.
   * The one on the left will be at the margins, the one in the center will be displayed with its center alligned with the center of the page
   * and the right most one will end on the right margin. Allows for addtional text to be printed on same line.
   * <p>
   * Loops and Conditions<br>
   * if (newLine) The purpose of this structure is to check if a new line/string has been added to the ArrayList or not.
   * @param leftText The text that will be displayed on the left side.
   * @param centerText The text that will be centered.
   * @param rightText The text that will be displayed on the right side.
   */
  public void print (String leftText, String centerText, String rightText)
  {
    if (newLine)
    {
      left.add (leftText);
      center.add (centerText);
      right.add (rightText);
    }
    else
    {
      left.add (left.remove (left.size () - 1) + leftText);
      center.add (center.remove (center.size () - 1) + centerText);
      right.add (right.remove (right.size () - 1) + rightText);
    }
    newLine = false;
  }
  
  /**
   * Queues an empty line to be printed.
   */
  public void println ()
  {
    print ("", "", "");
    newLine = true;
  }
  
  /**
   * Queues a String alligned on the left side to be printed.
   * @param text The text to be displayed on the left.
   */
  public void println (String text)
  {
    print (text, "", "");
    newLine = true;
  }
  
  /**
   * Queues a line with different text alligned on different places to be printed. Each String will be drawn starting at different locations.
   * The one on the left will be at the margins, the one in the center will be displayed with its center alligned with the
   * center of the page and the right most one will end on the right margin.
   * @param leftText The text that will be displayed on the left side.
   * @param centerText The text that will be centered.
   * @param rightText The text that will be displayed on the right side.
   */
  public void println (String leftText, String centerText, String rightText)
  {
    print (leftText, centerText, rightText);
    newLine = true;
  }
  
  /**Queues an image to be printed. The image will be drawn at the location specified without any proccessing for if it goes off the page.
    * Images with positive cordinates will always be printed within the 1 inch margins. The same image will be printed on all of the pages.
    * @param image The image to be printed.
    * @param location The location of the image. Syntax is: new Point (x, y).
    */
  public void printImage (BufferedImage image, Point location)
  {
    images.add (image);
    imageLocations.add (location);
    imageSizePercent.add (null);
    needsResizing.add (new Boolean (false));
  }
  
  /**Queues a square image to be printed with the length based on a percent of one of the dimensions of the paper. The length of the image
   * will be calculated based on the percent multiplied by the width or height of the paper (specified by the boolean). The image will be 
   * drawn at the location specified without any proccessing for if it goes off the page. Images with positive cordinates will always be 
   * printed within the 1 inch margins. The same image will be printed on all of the pages.
   * @param image The image to be printed.
   * @param location The location of the image. Syntax is: new Point (x, y).
   * @param percent The percentage of the dimension to be taken up (0 - 100).
   * @param useWidth Boolean for wether to base the percent on width or height.
   */   
  public void printImage (BufferedImage image, Point location, int percent, boolean useWidth)
  {
    int [] temp = {useWidth?percent:-1, useWidth?-1:percent};
    images.add (image);
    imageLocations.add (location);
    imageSizePercent.add (temp);
    needsResizing.add (new Boolean (true));
  }
  
  /**Queues an image to be printed with the length based on the percent of the dimensions of the paper. The width of the image will be
   * calculated based on the width of the paper multiplied by the percentWidth and the height by the percentHeight. The image will be 
   * drawn at the location specified without any proccessing for if it goes off the page. Images with positive cordinates will always 
   * be printed within the 1 inch margins. The same image will be printed on all of the pages.
   * @param image The image to be printed.
   * @param location The location of the image. Syntax is: new Point (x, y).
   * @param percentWidth The percentage of the width of the paper to base the width of the image on.
   * @param percentHeight The percentage of the height of the paper to base the height of the image on.
   */  
  public void printImage (BufferedImage image, Point location, int percentWidth, int percentHeight)
  {
    int [] temp = {percentWidth, percentHeight};
    images.add (image);
    imageLocations.add (location);
    imageSizePercent.add (temp);
    needsResizing.add (new Boolean (true));
  }
  
  /**Signals the printer to start printing. Users will be able to modify print conditions using the dialog box that pops
   * up.
   * <p>
   * Loops and Conditions<br>
   * if (p.printDialog()) The purpose of this structure is to display the dialog box so the user can properly select which options and to which printer they would like to print to.<br>
   * try {} catch(PrinterException... The purpose of this structure is to make sure that the program will not crash from a PrinterException.
   * @param e PrinterException This variable is associated to the portion of code that enures that the program will not crash due to a PrinterException.
   * @param p This variable gives the method access to variables and methods of PrinterJob.
   * @exception PrinterException If program failed to print.
   * @return boolean representing whether or not the print was sucessful.
   */
  public boolean printUsingDialog ()
  {
    PrinterJob p = PrinterJob.getPrinterJob ();
    if (p.printDialog ())
    {
      pageFormat = p.defaultPage ();
      maxLines = (int) (pageFormat.getImageableHeight () / new Canvas ().getFontMetrics (font).getHeight () * lineSpacing);
      p.setPageable (this);
      try
      {
        p.print ();    
      }
      catch (PrinterException e)
      {
        return false;
      }
      return true;
    }
    else
      return false;
  }
  
  /**Method invoked by PrinterJob to render a page. The queued text and images to be displayed are rendered on to the 
   * paper and the printed. Do NOT try to call directly, use printUsingDialog () instead.
   * <p>
   * Loops and Conditions<br>
   * if (page < 0) This structure checks if there is something to be printed. <br>
   * for (int i = 0... This loop makes sure that all elements that need to be printed are printed. <br>
   * if (!((boolean... This structure makes sure that whatever requires resizing is resized before it is printed. <br>
   * for (int i = page... This loop ensures that what needs to be printed is printed, and that nothing more is printed. (Does not go over max.)<br>
   * if (i >= left.size()) This structure ensures that the index does not go out of bounds (causing a program crash).
   * @param g The context into which the page is drawn.
   * @param pf The size and orientation of the page being drawn.
   * @param page The zero based index of the page to be drawn.
   * @return PAGE_EXISTS if the page is rendered successfully or NO_SUCH_PAGE if pageIndex specifies a non-existent page.
   * @throws PrinterException thrown when the print job is terminated.
   */
  public int print(Graphics g, PageFormat pf, int page) throws PrinterException 
  {
    if (page < 0)
      return NO_SUCH_PAGE;
    
    g.setFont (font);
    Graphics2D g2d = (Graphics2D)g;
    FontMetrics fm = g.getFontMetrics ();
    int y = (int) pf.getImageableY () + fm.getHeight ();
    
    for (int i = 0; i < images.size (); i ++)
    {
      if (!((boolean) needsResizing.get (i)))
        g2d.drawImage (images.get (i), (int) (pf.getImageableX () + imageLocations.get (i).getX ()), (int) (pf.getImageableY () + imageLocations.get (i).getY ()), null);
      else
      {
        int [] temp = imageSizePercent.get (i);
        int width = (temp [0] == -1)?(int) (temp [1] / 100.0 * pf.getHeight ()):(int) (temp [0] / 100.0 * pf.getWidth ());
        int height = (temp [1] == -1)?(int) (temp [0] / 100.0 * pf.getWidth ()):(int) (temp [1] / 100.0 * pf.getHeight ());
        g2d.drawImage (images.get (i), (int) (pf.getImageableX () + imageLocations.get (i).getX ()), (int) (pf.getImageableY () + imageLocations.get (i).getY ()), width, height, null);
      }
    }
    
    for (int i = page * maxLines; i < page * maxLines + maxLines; i ++)
    {
      if (i >= left.size ())
        break;
      g2d.drawString(left.get (i), (int) pf.getImageableX (), y);
      g2d.drawString(center.get (i), (int) (pf.getImageableX () + pf.getImageableWidth ()/2 - fm.stringWidth (center.get (i))/2.0), y); //Roughly center, will lose precision due to double -> int
      g2d.drawString(right.get (i), (int) (pf.getImageableX () + pf.getImageableWidth () - fm.stringWidth (right.get (i))), y);
      y+= (int) (fm.getHeight () * lineSpacing);
    }
    return PAGE_EXISTS;
  }
  
  /** The purpose of this method is to return the number of pages that will be printed based on the number of text lines.
    * <p>
    * Loops and Conditions<br>
    * if (left.size()... The purpose of this loop is to check the number of pages required to print all the information.
   * @return The number of pages that will be printed based on the number of text lines.
   */
  public int getNumberOfPages ()
  {
    if (left.size () == 0 && images.size () != 0)
      return 1;
    else
      return (int) Math.ceil ((double) left.size () / maxLines);
  }
  
  /**
   * Used by PrinterJob to get the PageFormat for a certain page.
   * @param index Index of the page whose PageFormat will be returned.
   */
  public PageFormat getPageFormat (int index)
  {
    return pageFormat;
  }
  
  /**
   * Used by PrinterJob to render each of the pages.
   * @param index Index of the Printable to be rendered.
   */
  public Printable getPrintable (int index)
  {
    return this;
  }
  
  /**
   * Creates a Printer object with Serif size 12 font and single spaced.
   */
  public Printer ()
  {
    this (new Font ("Serif", Font.PLAIN, 12), 1.0);
  }
  
  /**
   * Creates a Printer object with a custom font and single spaced.
   * @param f The font to be used when displaying text.
   */
  public Printer (Font f)
  {    
    this (f, 1.0);
  }
  
  /**
   * Creates a Printer object with a custom font and line spacing.
   * @param f The font to be used when displaying text.
   * @param spacing Amount of space to skip before next line (1.0 = single, 2.0 = double).
   */
  public Printer (Font f, double spacing)
  {    
    left = new ArrayList <String> ();
    center = new ArrayList <String> ();
    right = new ArrayList <String> ();
    images = new ArrayList <BufferedImage> ();
    imageLocations = new ArrayList <Point> ();
    needsResizing = new ArrayList <Boolean> ();
    imageSizePercent = new ArrayList <int []> ();
    font = f;
    lineSpacing = spacing;
  }
}