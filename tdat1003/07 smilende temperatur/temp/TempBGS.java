import static javax.swing.JOptionPane.*;
import java.util.Formatter;

public class TempBGS
{
  private final String[] VALGENE = {"Middeltemperatur DAG", "Middeltemperatur TIME", "Middeltemperatur MÅNED", "Antall døgn innenfor GRUPPE", "AVSLUTT"};
  private final byte MIDDEL_DAG = 0;
  private final byte MIDDEL_TIME = 1;
  private final byte MIDDEL_MANED = 2;
  private final byte ANTALL_I_GRUPPE = 3;
  private final byte AVSLUTT = 4;

  private final Temperaturer objekt;
  private Formatter f = new Formatter();

  public TempBGS(Temperaturer objektet)
  {
    objekt = objektet;
  }

  public int spørBrukern()
  {
    int valg = showOptionDialog(null, "Gjør ditt VALG", "Temperaturer går på tur i skogen (virkelig)",
                                DEFAULT_OPTION, WARNING_MESSAGE, null,
                                VALGENE, VALGENE[0]);
    if (valg == AVSLUTT)
    {
      return - 1;
    }

    return valg;
  }

  public void adlydOrdre(int valg)
  {
    switch (valg)
    {
      case MIDDEL_DAG:
        middelDag();
        break;
      case MIDDEL_TIME:
        middelTime();
        break;
      case MIDDEL_MANED:
        middelManed();
        break;
      case ANTALL_I_GRUPPE:
        antallGruppe();
        break;
      default:
        break;
    }
  } // end command section

  private void middelDag()
  {
    float[] tabell = objekt.middeltempDag();
    f = new Formatter(); // OMG MEMOY USAGE HOOOOLY SHIEEEET
    for (int i = 0; i < tabell.length; i++)
    {
      f.format("%.2f  ", tabell[i]); //saw String.format(shit) somewhere tho
    }
    showMessageDialog(null, f); // THIS WAS THE SOLUTION FFS
  }

  private void middelTime()
  {
    float[] tabell = objekt.middeltempTime();
    f = new Formatter();
    for (int i = 0; i < tabell.length; i++)
    {
      f.format("%.2f  ", tabell[i]);
    }
    showMessageDialog(null, f);
  }

  private void middelManed()
  {
    f = new Formatter();
    f.format("%.2f", objekt.middeltempManed());
    showMessageDialog(null, f);
  }

  private void antallGruppe()
  {
    String res = "";
    byte[] tabell = objekt.antallInnenforGruppe();
    for (int i = 0; i < tabell.length; i++)
    {
      res += tabell[i] + "  ";
    }
    showMessageDialog(null, res);
  }
}
