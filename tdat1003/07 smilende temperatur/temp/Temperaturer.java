/*********
 * Temperaturer.java
 * Oppretter en tabell som inneholder temperaturer for en måned
 *
 *********/

public class Temperaturer
{

  private int[][] tabell; // = new int[31][24];
  private byte antDager;  // for å holde styr på hvor mye som er fylt inn
  private byte antTimer;  // antar at vi ikke overstiger en byte...

  public Temperaturer(int[][] tabInn)
  {
    if(tabInn.length > 31 || tabInn[0].length > 24)
    {
      throw new IllegalArgumentException("Du overskrider våre grenser");
    }

    antDager = (byte) tabInn.length;
    antTimer = (byte) tabInn[0].length;
    tabell = new int[antDager][antTimer]; // kan altså kun ta tabeller av tabeller med lik lengde

    for (int i = 0; i < tabInn.length; i++)
    {
      for (int j = 0; j < tabInn[i].length; j++)
      {
        tabell[i][j] = tabInn[i][j]; // creating the table
      }
    }
  }

  public float[] middeltempDag()
  {
    float[] middelBase = new float[antDager];
    for (int i = 0; i < antDager; i++)
    {
      int sum = 0;
      for (int j = 0; j < antTimer; j++)
      {
        sum += tabell[i][j]; // adding to the sum for the current day
      }
      middelBase[i] = (float) sum / antTimer; // calculating ze gjennomsnitt 4 ze day
    }
    return middelBase;
  }

  public float[] middeltempTime()
  {
    float[] middelBase = new float[antTimer];
    for (int j = 0; j < antTimer; j++)
    {
      int sum = 0;
      for (int i = 0; i < antDager; i++)
      {
        sum += tabell[i][j]; // adding to the sum for the current HOUR
      }
      middelBase[j] = (float) sum / antDager; // calculating ze gjennomsnitt 4 ze HOUR
    }
    return middelBase;
  }

  public float middeltempManed()
  {
    int sum = 0;
    for (int i = 0; i < antDager; i++)
    {
      for (int j = 0; j < antTimer; j++)
      {
        sum += tabell[i][j]; // adding to the sum
      }
    }
    return (float) sum / (antDager * antTimer);
  }

  public byte[] antallInnenforGruppe() // trenger ikke mer enn byte, vi snakker kun 31 dager
  {
    byte[] gruppert = new byte[5];
    float[] middelDag = middeltempDag();

    for (int i = 0; i < antDager; i++)
    {
      float num = middelDag[i];
      if (num < -5)
      {
        gruppert[0]++;
      }
      else if (num > -5 && num < 0)
      {
        gruppert[1]++;
      }
      else if (num > 0 && num < 5)
      {
        gruppert[2]++;
      }
      else if (num > 5 && num < 10)
      {
        gruppert[3]++;
      }
      else if (num > 10)
      {
        gruppert[4]++;
      }
    }
    return gruppert;
  }


  // unødvendig testmetode
  public static void main(String[] args) {
    final double TOLERANSE = 0.001f;

    int[][] tempie = {{2,3,1,1,4,5,6,4}, {1,3,2,4,5,4,-4,-3}, {1,-5,-10,-6,-2,3,7,14}};
    Temperaturer test = new Temperaturer(tempie);

    float[] middelDagAltså = test.middeltempDag();
    float[] middelTimeAltså = test.middeltempTime();
    float middelManedAltså = test.middeltempManed();
    byte[] gruppertAltså = test.antallInnenforGruppe();

    System.out.println("" + middelTimeAltså[0] + " " + middelTimeAltså[1] + " " + middelTimeAltså[5] + " " + middelManedAltså);
    System.out.println("" + gruppertAltså[0] + " " + gruppertAltså[1] + " " + gruppertAltså[2] + " " + gruppertAltså[3] + " " + gruppertAltså[4] + " " + gruppertAltså[5]);
//3.25 1.5, middel måned er 1.66 (1.67)
    if(middelDagAltså[0] - 3.25f < + TOLERANSE &&
       middelDagAltså[1] - 1.5f < TOLERANSE)
    {
      System.out.println("Test 1 vellykket.");
    }

    int[][] hehe = {{1,1,1,11,1,1,1,11,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},{0}};
    Temperaturer heh = new Temperaturer(hehe);
  }
}
