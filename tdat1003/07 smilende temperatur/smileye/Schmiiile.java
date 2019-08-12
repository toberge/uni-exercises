import java.awt.Graphics;
import java.awt.Color;
//import java.awt.Font; // really now?
import javax.swing.JFrame;
import javax.swing.JPanel;

class Window extends JFrame // is a subclass of JFrame
{
  public Window(String title)
  {
    setTitle(title);
    setSize(512, 512); // width,height
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // program stops when windie closd, JFrame. is superfluous
    Smiley theSmiley = new Smiley(); // NEW SMILEYYYYYY
    add(theSmiley); // adds drawing to window
  }
}

class Smiley extends JPanel // is a subclass of JPanel
{
  public void paintComponent(Graphics parchment)
  {
    setBackground(Color.BLUE); // from class Component
    super.paintComponent(parchment); // to make the upper classes draw this drawing board
    int width = getWidth();
    int height = getHeight(); // remember, starts at 0 and ends at value - 1
    int midpoint = width / 2;
    byte start = 32;
    parchment.setColor(Color.YELLOW); // ugly as fu
    parchment.fillOval(start, start, width - 2 * start, height - 2 * start); // the base
    parchment.setColor(Color.BLACK);
    parchment.fillOval(midpoint - 128 - 32, start + 96, 128, 128); // eyesockets
    parchment.fillOval(midpoint + 32, start + 96, 128, 128);
    parchment.fillArc(start + 96, height / 2, width - start * 2 - 96 * 2, height / 2 - 96, 0, -180);
    parchment.setColor(Color.RED);
    parchment.fillOval(midpoint - 128, start + 128, 64, 64); // eyelights
    parchment.fillOval(midpoint + 64, start + 128, 64, 64);
  }
}

class Schmiiile
{
  public static void main(String[] args)
  {
    Window datWindow = new Window("spreke farger roter rundt"); // new windings
    datWindow.setVisible(true); // make visibility go wiiiiiild
  }
}
