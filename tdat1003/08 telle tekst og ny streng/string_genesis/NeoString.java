public class NeoString
{
  private final String theString;

  public NeoString(String thatString)
  {
    theString = thatString;
  }

  public NeoString()
  {
    theString = "";
  }

  public String toString()
  {
    return theString;
  }

  public String minimize()
  {
    String[] dismembered = theString.split(" ");
    String thatString = "";
    for (int i = 0; i < dismembered.length; i++)
    {
      thatString += dismembered[i].charAt(0);
    }
    return thatString;
  }

  public String remove(String victim)
  {
    String[] dismembered = theString.split(victim);
    String thatString = "";
    for (int i = 0; i < dismembered.length; i++)
    {
      thatString += dismembered[i];
    }
    return thatString;
  }

  public static void main(String[] args)
  {
    NeoString firstString = new NeoString("Hello from the other side");
    System.out.println(firstString);
    System.out.println(firstString.minimize());
    NeoString otherString = new NeoString("Hello from the other side");
    System.out.println(otherString.remove("e"));
    System.out.println(otherString.remove("o"));
  }
}
