/**
* Første forsøk forseres - trygt?
* Ja, bortsett fra en ørliten, nei, kolossal feil, der centimetrene ble færre.
*
* Dette er Øving1(_4des), et program hvis funksjon er tredelt og kommentert nedenfor.
*/
import static javax.swing.JOptionPane.*;
class Øving1_4des {
  public static void main(String[] args) {
    System.out.println("Velkommen, godtfolk.\nHer oppgis resultater og alt som er med maksimalt fire desimaler.\nJeg spør dere:");
    /* Vi begynner */
    final double tommeFaktor = 2.54;
    String tommerLest = showInputDialog("Hvor mange tommer skal centreres?");
    double tommer = Double.parseDouble(tommerLest);

    if(tommer > 0) {
      System.out.println("Folket har talt.");
      double centi = tommer * tommeFaktor;
      /* Reduserer mengden desimaltall. */
      java.util.Formatter tomRed = new java.util.Formatter();
      tomRed.format("%.4f",tommer);
      java.util.Formatter centiRed = new java.util.Formatter();
      centiRed.format("%.4f",centi);
      String tommeMsg = tomRed.toString() + " tommer er " + centiRed + " centimeter.";
      System.out.println(tommeMsg);
      showMessageDialog(null,tommeMsg);

    } else {
      showMessageDialog(null,"Hva er det som feiler dette folket?\nIngen negativitet eller ikke-eksistens ønskes.");
      System.out.println("Folket har talefeil.");
    }

    /* Regne om timer, minutter og sekunder til total mengde sekunder */
    System.out.println("Jeg spør dere igjen:");
    String timeLest = showInputDialog("Hvor mange timer?");
    String minLest = showInputDialog("Hvor mange minutter?");
    String sekLest = showInputDialog("Hvor mange sekunder?");
    int time = Integer.parseInt(timeLest);
    int minutt = Integer.parseInt(minLest);
    double sekund = Double.parseDouble(sekLest);

    final short TIMSEK = 3600;
    final byte MINSEK = 60;

    if(time > 0 && minutt > 0 && sekund > 0) {
      System.out.println("Folket har igjen gitt lyd av seg.");
      double totaleSekunder = TIMSEK * time + MINSEK * minutt + sekund;
      java.util.Formatter totSekRed = new java.util.Formatter();
      totSekRed.format("%.4f",totaleSekunder);
      String totSekMsg = "Dette blir til sammen " + totSekRed.toString();
      System.out.println(totSekMsg);
      showMessageDialog(null,totSekMsg);

    } else {
      showMessageDialog(null,"Hva er det dere tror dere driver med?\nIngen nuller eller negative tall, takk.");
      System.out.println("Folket er kanskje tre ganger så uskikkelig som jeg trodde.");
    }

    /* Regne om sekunder til timer, minutter og færre sekunder. */
    System.out.println("Atter en gang stiller jeg et spørsmål:");
    String sekunderLest = showInputDialog("Hvor mange sekunder skal stykkes opp?");
    double sekunder = Double.parseDouble(sekunderLest);

    if(sekunder > 0) {
      System.out.println("Siste svar er mottatt. Resultatet vil snart meddeles.");
      int timer = (int) (sekunder + 0.5) / TIMSEK;
      if(timer == 596523) {
        System.out.println("Tallet ditt er trolig for stort.");
      }
      sekunder = sekunder % TIMSEK;
      int minutter = (int) (sekunder + 0.5) / MINSEK;
      sekunder = sekunder % MINSEK;
      java.util.Formatter partSekRed = new java.util.Formatter();
      partSekRed.format("%.4f",sekunder);
      String partSekMsg = "Sekundene er partert. De utgjør " + timer + " timer, " + minutter + " minutter og " + partSekRed.toString() + " sekunder.";
      System.out.println(partSekMsg);
      showMessageDialog(null,partSekMsg);
    } else {
      showMessageDialog(null,"Jeg gir opp. Vær positive. Please.");
      System.out.println("*tableflip*");
    }

    /* dis is it.*/
  }
}
