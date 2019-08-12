import java.util.*;
class Conv {
  public static double fetchBeløp() { // pga styr med switch lager jeg egen funksjon for å hente beløp.
    Scanner knastTast = new Scanner(System.in);
    System.out.print("Skriv inn et beløp: "); //idiot. dette er programmets egentlige funksjon.
    double beløp = knastTast.nextDouble();
    return beløp;
  }
  public static boolean whatsWanted() {
    Scanner knastTast = new Scanner(System.in);
    System.out.print("Vil du regne om til NOK (1) eller fra NOK (2)? Default er fra. "); //idiot. dette er programmets egentlige funksjon.
    byte answer = (byte) knastTast.nextInt();
    if(answer == 1) {
      return true;
    } else {
      return false;
    }
  }
  public static void main(String[] args) {
    Scanner tastKnast = new Scanner(System.in);
    /* Setter valutaenes faktorer basert på tall fra 3. september, hentet fra Norges bank. */
    Valuta dollar = new Valuta("USD", 8.3573); // 1
    Valuta euro = new Valuta("EUR",9.7020); // 2
    Valuta svensker = new Valuta("SEK",0.9170); // 3
    Valuta gærninger = new Valuta("HUF",0.029708); // 4
    Valuta yen = new Valuta("JPY",0.075192); // 5
    boolean fortsett = true;

    do {
      System.out.println("Velg valuta:");
      System.out.println("0: vis tabell");
      System.out.println("1: dollar");
      System.out.println("2: euro");
      System.out.println("3: svenske kroner");
      System.out.println("4: gærninger");
      System.out.println("5: yen");
      System.out.println("6: avslutt");
      System.out.print("Valget ditt: ");
      byte valg = (byte) tastKnast.nextInt();
      String resultat = "";
      double beløp;

      switch(valg) {
      case 0:
        System.out.println("");
        System.out.println(dollar.toString());
        System.out.println(euro.toString());
        System.out.println(svensker.toString());
        System.out.println(gærninger.toString());
        System.out.println(yen.toString());
        break;
      case 1:
        if(whatsWanted()) {
          resultat += dollar.regnStringTil(fetchBeløp());
        } else {
          resultat += dollar.regnStringFra(fetchBeløp());
        }
        break;
      case 2:
        if(whatsWanted()) {
          resultat += euro.regnStringTil(fetchBeløp());
        } else {
          resultat += euro.regnStringFra(fetchBeløp());
        }
        break;
      case 3:
        if(whatsWanted()) {
          resultat += svensker.regnStringTil(fetchBeløp());
        } else {
          resultat += svensker.regnStringFra(fetchBeløp());
        }
        break;
      case 4:
        if(whatsWanted()) {
          resultat += gærninger.regnStringTil(fetchBeløp());
        } else {
          resultat += gærninger.regnStringFra(fetchBeløp());
        }
        break;
      case 5:
        if(whatsWanted()) {
          resultat += yen.regnStringTil(fetchBeløp());
        } else {
          resultat += yen.regnStringFra(fetchBeløp());
        }
        break;
      case 6:
        resultat = "Du vil ikke fortsette, nei.";
        fortsett = false;
        break;
      default:
        resultat = "Du gjorde noe galt. Farvel.";
        fortsett = false;
        break;
      }

        System.out.println(resultat);


    } while(fortsett);
  }
}
