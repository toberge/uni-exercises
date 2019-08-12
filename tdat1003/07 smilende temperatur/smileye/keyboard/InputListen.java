import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class Window extends JFrame // is a subclass of JFrame
{
  public Window(String title)
  {
    setTitle(title);
    setSize(512, 512); // width,height
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // program stops when windie closd, JFrame. is superfluous
    Thing theThing = new Thing();
    add(theThing); // adds drawing to window
  }
}

class MyKeyListener extends KeyAdapter{
     public void keyPressed(KeyEvent e) {
         //accept any key
         Thing.this.start++;//
         Thing.this.parchment.repaint();
         //MulticolorCubeWithKeyboardListener.this.repaint();//repaint our canvas
     }
}

class Thing extends JPanel // is a subclass of JPanel
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
    MyKeyListener ear = new MyKeyListener();
  }
}

class DynamicSmile
{
  public static void main(String[] args)
  {
    Window datWindow = new Window("spreke farger roter rundt"); // new windings
    datWindow.setVisible(true); // make visibility go wiiiiiild
  }
}
