import java.util.Random;

public class Randumb
  {
  private final Random rng = new Random();

  public int nesteHeltall(int nedre, int ovre) // [nedre, ovre]
  {
    return nedre + rng.nextInt(ovre - nedre);
  }

  public double nesteDesimaltall(double nedre, double ovre) // <nedre, ovre>
  {
    return nedre + rng.nextDouble() * (ovre - nedre);
  }

  public static void main(String[] args)
  {

    System.out.println("Totalt antall tester: 2");

    Randumb lol = new Randumb();
    int up = 255;
    int down = -256;
    double wup = 20;
    double wdown = -10;
    boolean wentIntWell = true;
    boolean wentDoublyWell = true;
    int tegger;
    double desim;

    for(int i = 0; i < 100; i++)
    {
      tegger = lol.nesteHeltall(down,up);
      if(tegger > up || tegger < down)
      {
        wentIntWell = false;
      }
      System.out.print("  " + tegger);
    }

    System.out.println("");

    for(int i = 0; i < 100; i++)
    {
      desim = lol.nesteDesimaltall(wdown,wup);
      if(desim > wup || desim < wdown)
      {
        wentDoublyWell = false;
      }
      System.out.print("  " + desim);
    }

    System.out.println("");

    if(wentIntWell)
    {
      System.out.println("Test 1 vellykket.");
    }

    if(wentDoublyWell)
    {
      System.out.println("Test 2 vellykket.");
    }
  }
}
