import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JApplet;
import javax.swing.JPanel;

class Smiley extends JPanel // is a subclass of JPanel
{
  public void paintComponent(Graphics parchment)
  {
    setBackground(Color.BLUE); // from class Component
    super.paintComponent(parchment); // to make the upper classes draw this drawing board
    int width = getWidth();
    int height = getHeight(); // remember, starts at 0 and ends at value - 1
    int midpoint = width / 2;
    int midway = height / 2;
    int start = 32; // 32
    int startY = height / 16;
    int eyeSizeX = width / 4; // 128
    int eyeSizeY = height / 4;
    int eyeStartX = eyeSizeX / 4;
    int eyeStartY = eyeSizeY * 2 / 3; // 96
    parchment.setColor(Color.YELLOW); // ugly as fu
    parchment.fillOval(start, start, width - 2 * start, height - 2 * start); // the base
    parchment.setColor(Color.BLACK);
    parchment.fillArc(start * 3, start * 3, width - start * 6, height - start * 6, 0, -180); // the SCHMILE
    parchment.setColor(Color.YELLOW);
    parchment.fillRect(start * 3, midway, width - start * 6, startY * 2); // must be LIMITED
    parchment.setColor(Color.BLACK);
    parchment.fillOval(midpoint - eyeSizeX - eyeStartX, start + eyeStartY, eyeSizeX, eyeSizeY);
    parchment.fillOval(midpoint + eyeStartX, start + eyeStartY, eyeSizeX, eyeSizeY);
    //parchment.fillArc(start + 96, height / 2, width - start * 2 - 96 * 2, height / 2 - 96, 0, -180);
    parchment.setColor(Color.RED);
    parchment.fillOval(midpoint - eyeSizeX, start + eyeStartY + eyeSizeY / 4, eyeSizeX / 2, eyeSizeY / 2);
    parchment.fillOval(midpoint + eyeSizeX / 2, start + eyeStartY + eyeSizeY / 4, eyeSizeX / 2, eyeSizeY / 2);
  }
}

public class AppletSmiles extends JApplet
{
  public void init()
  {
    add(new Smiley());
  }

  public void start()
  {
    System.out.println("I AM BORN");
  }

  public void stop()
  {
    System.out.println("Are you leaving me?");
  }

  public void destroy()
  {
    System.out.println("DON'T EXIT ME LIKE THAT YOU BASTARD");
  }
}
