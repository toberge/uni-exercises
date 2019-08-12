public class TekstMord
{
  private String tekst;

  public TekstMord(String teksten)
  {
    tekst = teksten;
  }

  public String toString()
  {
    return tekst;
  }

  public String toUpperCaseString()
  {
    return tekst.toUpperCase(); // works flawlessly for me
  }

  /*
   * JEG BURDE KANSKJE HA BRUKT StringTokenizer
   * eller kanskje sjekka String.split() litt bedre og sett at
   * flere skilletegn kan spesifiseres...
   */

  private boolean sjekkOmSkilletegn(char tegn)
  {
    if (tegn == '.' || tegn == ',' || tegn == '!' || tegn == '?' || tegn == ':' || tegn == ';' || tegn == '\t')
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  public int antOrd()
  {
    String[] merTekst = tekst.split("[ \n]+");
    int antallOrd = 0;

    /* Den gamle, enkle.
    for (int i = 0; i < merTekst.length; i++)
    {
      antallOrd++; // teller ord adskilt av mellomrom
    }
    */

    for (int i = 0; i < merTekst.length; i++) // for hvert mulige ord
    {
      boolean erEtOrd = false;

      for (int j = 0; j < merTekst[i].length(); j++) // for hvert bidige tegn
      {
        if (!sjekkOmSkilletegn(merTekst[i].charAt(j)))
        {
          erEtOrd = true; // vil ikke utløses hvis "ordet" kun består av skilletegn
        }
      } // end tegnløkke

      if (erEtOrd)
      {
        antallOrd++;
      }
    } // end ordløkke

    return antallOrd;
  }

  public int ordLengde() // gjennomsnittlig ordlengde
  {
    String[] merTekst = tekst.split("[ \n]+"); // splitt etter mellomrom
    int totalLengde = 0;
    int antallOrd = 0;

    for (int i = 0; i < merTekst.length; i++) // for hvert mulige ord
    {
      int lengde = 0;

      for (int j = 0; j < merTekst[i].length(); j++) // for hvert bidige tegn
      {
        if (!sjekkOmSkilletegn(merTekst[i].charAt(j)))
        {
          lengde++; // tell ikke med skilletegn
        }
      } // end tegnløkke

      if (lengde > 0)
      {
        antallOrd++; // ta bort hvis du bruker antOrd()
      }

      totalLengde += lengde; // legg til lengden
    } // end ordløkke

    return (int) (((double) totalLengde / antallOrd) + 0.5); // ops, heltallsdivisjon...
    /* velger å bruke denne metoden for å telle antall ord,
     * det vil være liten vits i å kjøre gjennom de samme to for-løkkene atter en gang,
     * spesielt siden det er meget lett å slenge det med her.
     */
  }

  private boolean sjekkOmPeriodemerke(char tegn)
  {
    if (tegn == '.' || tegn == '!' || tegn == '?' || tegn == ':' || tegn == '\n') // periodeskillene
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  public int antOrdPeriode()
  {
    String[] merTekst = tekst.split(" "); // splitt etter mellomrom
    int antallOrd = 0;
    int antallPerioder = 1; // siden vi ser etter skilletegnene og det da er enda en periode

    for (int i = 0; i < merTekst.length; i++) // for hvert ord
    {
      boolean utenPeriode = true;
      boolean førPeriode = false;
      boolean etterPeriode = false;

      for (int j = 0; j < merTekst[i].length(); j++) // for hvert bidige tegn
      {
        if (sjekkOmPeriodemerke(merTekst[i].charAt(j))) // sjekk om det er et periodeskille
        {
          if (utenPeriode) // hvis det ikke har vært periodeskille før i ordet
          {
            if (j > 1) // unngå ikke-eksisterende indekser
            {
              if (!sjekkOmPeriodemerke(merTekst[i].charAt(j - 1)))
              {
                førPeriode = true; // det er noe annet FØR periodemarkøren
              }
            }
            if (j < merTekst[i].length() - 1) // same
            {
              if (!sjekkOmPeriodemerke(merTekst[i].charAt(j + 1)))
              {
                etterPeriode = true; // det er noe ETTER periodemarkøren
              }
            }
          } // end if (utenPeriode)
          if((!førPeriode && i == 0) || (!etterPeriode && i == merTekst.length - 1)) // i tilfelle vi finner en akkurat i begynnelsen
          {
            utenPeriode = true; // i spesialtilfeller
          }
          else
          {
            utenPeriode = false; // ved periodemarkør
          }
        }
      } // end tegnløkke

      if (utenPeriode) // hvis dette bare var et ord
      {
        antallOrd++; // øk antall ord
      }
      else // hvis det var en markør her
      {
        if (førPeriode)
        {
          antallOrd++;
        }
        if (etterPeriode)
        {
          antallOrd++;
        }
        if (i > 0) // i tilfelle periodemarkør i første ord.
        {
          antallPerioder++;
        }
      }

    } // end ordløkke

    return (int) (((double) antallOrd / antallPerioder) + 0.5);
  }

  public boolean byttUt(String gammeltOrd, String nyttOrd)
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
    //String texxt1 = "Æ tru!r du e heil.t : SLecHTå undø. TRAUærig";
    String texxt1 = "Lag en klasse for enkel tekstbehandling. Konstruktøren skal ta en tekst som argument. Klassen skal tilby følgende tjenester:\nÅ finne verdens særeste ostetype\nÅ myrde vår alles kjære tekstbehandler\nÅ gi svaret på spørsmålet om livet, universet og alt muliglele";
    /*
     * 40 ord, 5.4 bokstaver per ord, 6.667 ord per periode.
     */

    TekstMord murder = new TekstMord(texxt1);

    System.out.println("---Vår vidunderlige tekst:\n\n" + texxt1 + "\n");
    System.out.println("---Med store bokstaver:\n\n" + murder.toUpperCaseString() + "\n");
    System.out.println("---Den inneholder " + murder.antOrd() +
                       " ord, med gjennomsnittlig lengde " + murder.ordLengde() + ".");
    System.out.println("---Det er i gjennomsnitt " + murder.antOrdPeriode() + " ord per periode.");
    System.out.println("---Vi bytter nå ut \"e\" med \"u\". Vent i spenning.");
    murder.byttUt("e", "U");
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
