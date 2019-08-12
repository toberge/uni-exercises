import java.util.Scanner;
import java.util.Formatter;

class Norsklærer {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String tekst;
    int verdi;
    boolean cont = true;
    Formatter f = new Formatter();

    Tekstanalyse unicodeInitiate = new Tekstanalyse(); // oppretter unicode-tabellen

    do {
      System.out.println("Skriv en liten enlinjes tekst:");
      tekst = sc.nextLine();
      //tekst = showInputDialog("Skriv en liten enlinjes tekst:");

      Tekstanalyse lærern = new Tekstanalyse(tekst.toLowerCase());

      System.out.print("Teksten har " + lærern.antForskjelligeBokst() + " forskjellige bokstaver, ");
      System.out.println("totalt " + lærern.antBokstaver() + " bokstaver.");
      f.format("%.2f", lærern.prosIkkeBokst());
      System.out.println("" + f.toString() + "% av teksten er ikke (norske) bokstaver.");

      String oftest = lærern.mestFrekventeBokst();
      String oftestPrint = "";
      if(oftest.length() > 1) {
        oftestPrint += "De hyppigst forekommende bokstavene er ";
      } else {
        oftestPrint += "Den hyppigst forekommende bokstaven er ";
      }
      for (int i = 0; i < oftest.length(); i++) {
        if (oftest.length() == 1) { // **a**
          oftestPrint += oftest.charAt(i);
        } else if (oftest.length() < 3 && i == 0) { // **a** og b
          oftestPrint += oftest.charAt(i) + " ";
        } else if (i < oftest.length() - 1) { // a, b, c,
          oftestPrint += oftest.charAt(i) + ", ";
        } else {
          oftestPrint += "og " + oftest.charAt(i); // og d
        }
      }
      oftestPrint += ".";
      System.out.println(oftestPrint);

      System.out.println("Vil du se en (t)abell med hele alfabetet og forekomsten av alle bokstavene\neller (b)estemme en bokstav du vil se forekomsten av?\nTabell og alt annet avslutter. Væredusågod: ");
      tekst = sc.nextLine();
      tekst = tekst.toLowerCase();
      switch (tekst.charAt(0)) {
        case 't':
          System.out.println(lærern.toString());
          break;
        case 'b':
          boolean contd = true;
          do {
            System.out.print("Skriv en bokstav du vil se forekomsten av: ");
            tekst = sc.nextLine();
            verdi = lærern.forekomstAv(tekst.charAt(0));
            if(verdi == -1) {
              System.out.println("Ugyldig bokstav.");
            } else {
              System.out.println("Bokstaven " + tekst.charAt(0) + " forekommer " + verdi + " gang(er).");
            }

            System.out.print("Gjenta? (y/n) ");
            tekst = sc.nextLine();
            if (tekst.charAt(0) == 'n' || tekst.charAt(0) == 'N') {
              contd = false;
            } else {
              contd = true;
            }
          } while (contd);
          break;
        default:
          System.out.println("Javel da.");
          break;
      }

      /*
      int svar = showConfirmDialog(null,"Avslutte?", "hm", YES_NO_OPTION);
      if (svar == YES_OPTION) {
        cont = false;
      }
      */
      System.out.print("Fortsette? (y/n) ");
      tekst = sc.nextLine();
      if (tekst.charAt(0) == 'n' || tekst.charAt(0) == 'N') {
        cont = false;
      } else {
        cont = true;
      }
      System.out.println();
    } while (cont);
  }
}
