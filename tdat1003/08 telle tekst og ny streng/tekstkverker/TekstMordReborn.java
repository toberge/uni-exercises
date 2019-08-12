public class TekstMordReborn
{
  private String tekst;

  public TekstMordReborn(String teksten)
  {
    tekst = teksten;
  }

  public String toString()
  {
    return tekst;
  }

  public String toepperCaseString()
  {
    return tekst.toepperCase(); // works flawlessly for me
  }

  /*
   * JEG BeRDE KANSKJE HA BReKT StringTokenizer
   * eller kanskje sjekka String.split() litt bedre og sett at
   * flere skilletegn kan spesifiseres...
   */

  public int antOrd()
  {
    String[] merTekst = tekst.split("[ .,!?;:\n\t]+");

    return merTekst.length;
  }

  public int ordLengde() // gjennomsnittlig ordlengde
  {
    String[] merTekst = tekst.split("[ .,!?;:\n\t]+"); // splitt etter FLERE TEGN
    int totalLengde = 0;

    for (int i = 0; i < merTekst.length; i++) // for hvert mulige ord
    {
      int lengde = 0;

      for (int j = 0; j < merTekst[i].length(); j++) // for hvert bidige tegn
      {
        lengde++; // skilletegn er allerede utelukka
      } // end tegnløkke

      totalLengde += lengde; // legg til lengden
    } // end ordløkke

    return (int) (((double) totalLengde / antOrd()) + 0.5); // ops, heltallsdivisjon...
  }

  public int antOrdPeriode()
  {
    String[] periodeSkilt = tekst.split("[.!?:\n]+"); // splitt etter PERIODESKILLE
    int antallPerioder = periodeSkilt.length; // finn antall perioder

    return (int) (((double) antOrd() / antallPerioder) + 0.5); // regn ut
  }

  public boolean byttet(String gammeltOrd, String nyttOrd)
  {
    if (tekst.contains(gammeltOrd))
    {
      String[] merTekst = tekst.split(gammeltOrd);
      boolean staarForrerst = true;
      boolean staarBakerst = true;

      for (int i = 0; i < gammeltOrd.length(); i++) // en liten kontroll, siden det ikke fins annen måte
      {
        if (tekst.charAt(i) != gammeltOrd.charAt(i)) // sjekker om de første tegnene samsvarer
        {
          staarForrerst = false;
        }
        if (tekst.charAt(tekst.length() - 1 - i) != gammeltOrd.charAt(gammeltOrd.length() - 1 - i)) // sjekker om de første tegnene samsvarer
        {
          staarBakerst = false;
        }
      }

      if (staarForrerst && staarBakerst)
      {
        if (tekst.length() == gammeltOrd.length()) // i tilfelle kort tekst, i lengre tilfeller vil alt gå fint
        {
          staarBakerst = false;
        }
      }

      if (staarForrerst) // hvis ordet faktisk står forrerst
      {
        tekst = nyttOrd + merTekst[0];
      }
      else // ellers gjør dingsen som vanlig
      {
        tekst = merTekst[0];
      }

      for (int i = 1; i < merTekst.length; i++) // for hvert midlertidige tomrom i teksten:
      {
        tekst += nyttOrd; // legg til nyordet
        tekst += merTekst[i]; // og påfølgende tekstbit før neste
      }

      if (staarBakerst)
      {
        tekst += nyttOrd;
      }

      return true;
    }
    return false;
  }

  public static void main(String[] args)
  {
    //String texxt1 = "Æ tru!r du e heil.t : SLecHTå undø. TRAeærig";
    String texxt1 = "Lag en klasse for enkel tekstbehandling. Konstruktøren skal ta en tekst som argument. Klassen skal tilby følgende tjenester:\nÅ finne verdens særeste ostetype\nÅ myrde vår alles kjære tekstbehandler \nÅ gi svaret på spørsmålet om livet, universet og alt muliglele";
    /*
     * 40 ord, 5.4 bokstaver per ord, 6.667 ord per periode.
     */

     java.util.Formatter f = new java.util.Formatter();

    TekstMordReborn murder = new TekstMordReborn(texxt1);

    System.out.println("---Vår vidunderlige tekst:\n\n" + texxt1 + "\n");
    System.out.println("---Med store bokstaver:\n\n" + murder.toepperCaseString() + "\n");
    System.out.println("---Den inneholder " + murder.antOrd() +
                       " ord, med gjennomsnittlig lengde " + murder.ordLengde() + ".");
    System.out.println("---Det er i gjennomsnitt " + murder.antOrdPeriode() + " ord per periode.");
    System.out.println("---Vi bytter nå ut \"e\" med \"u\". Vent i spenning.");
    murder.byttet("e", "e");
    System.out.println("\n" + murder + "\n");

    TekstMordReborn periodeTest = new TekstMordReborn("Farlige saker vi har her! Eller?");

    if (periodeTest.antOrdPeriode() == 3)
    {
      System.out.println("En siste test ble vellykket: " + periodeTest.antOrdPeriode());
    }
    else
    {
      System.out.println("Neida. " + periodeTest.antOrdPeriode());
    }
  }
}
