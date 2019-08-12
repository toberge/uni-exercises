// absolutely brilliant GUI (tm)

import static javax.swing.JOptionPane.*;
//import java.util.Formatter;

public class BGS {
  private final String[] VALGENE = {"Endre månedslønn", "Endre skatteprosent", "Avslutt"};
  private final byte MÅNEDSLØNN = 0;
  private final byte SKATTEPROS = 1;
  private final byte AVSLUTT = 2;

  private final ArbTaker objekt;

  public BGS(ArbTaker objektet) {
    objekt = objektet;
  }

  public String toString() { // absolutely pointless easter egg
    System.out.println("I am a machine. You can't just string me up.");
  }

  public int spørBrukern() {
    int valg = showOptionDialog(null, "Vår kjære arbeidstaker:\n" + objekt, "Intet mystisk skjer her",
                                DEFAULT_OPTION, WARNING_MESSAGE, null,
                                VALGENE, VALGENE[0]);
    if (valg == AVSLUTT) {
      return - 1;
    }

    return valg;
  } // end questioning

  public void adlydOrdre(int valg) {
    switch (valg) {

      case MÅNEDSLØNN:
        endreLønn();
        break;
      case SKATTEPROS:
        endreSkatt();
        break;
      default:
        break;
    }
  } // end command section

  private String lesVerdi(String melding) {
    return showInputDialog(melding); // should check if null or something, but not necessary here
  }

  private void endreLønn() {
    float nyLønn = (float) Double.parseDouble(lesVerdi("Skriv den nye lønna i kr (innen rimelige grenser):"));
    objekt.setMånedslønn(nyLønn);
  }

  private void endreSkatt() {
    float nySkatt = (float) Double.parseDouble(lesVerdi("Skriv den nye skatteprosenten:"));
    objekt.setSkatteprosent(nySkatt);
  }
}
