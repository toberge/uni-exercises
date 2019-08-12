/**
*
* Oppgave 4.14.5:
*
* Vi skal sjekke om et bruker-spesifisert heltall er et primtall.
* Må løpe gjennom det aktuelle tallområdet og dividere det på alt (men 1 og tallet selv trengs ikke)
* Om tallet faktisk er 1 kan vi gi opp hele greia, det regnes ikke som et primtall.
* Om tallet er 0 eller mindre gir vi brukeren en liten kilevink - eh, jeg mente å si feilmelding. Nettopp.
* Etter testen skal vi spørre om brukeren vil gjøre det hele en gang til.
*
*/

import static javax.swing.JOptionPane.*;
class Øving3_2 {
  public static void main(String[] args) {
    boolean fortsett = true;
     do {

      String tallLest = showInputDialog("Hei, din djevel. Gi meg et POSITIVT HELTALL. Ikke 0.");
      if(tallLest == null) {
        showMessageDialog(null,"Vennligst skriv inn noe.");
      } else {
        int tall = Integer.parseInt(tallLest);
        if(tall <= 0) {
        showMessageDialog(null,"Positivt, sa jeg. Ikke 0, sa jeg. Hører du?");
      } else if(tall == 1) {
        System.out.println("Brukeren tastet 1.");
        showMessageDialog(null,"Tallet er ikke et primtall.");
      } else { // i så fall starter prosessen.
        int divisor = 2; // starter med de laveste divisorene
        boolean prime = true;
        while(prime && divisor < tall) {
          System.out.print("Sjekker " + tall + " dividert med " + divisor + "... ");
          if(tall % divisor == 0) { //sjekker divisjonen
            prime = false;
            System.out.println("Divisjonen går opp!");
          } else {
            System.out.println("Divisjonen går ikke opp.");
          }
          divisor++;
        }
        if(prime) {
          System.out.println("Primtall.");
          showMessageDialog(null,"Tallet er et primtall.");
        } else {
          System.out.println("Ikke primtall.");
          showMessageDialog(null,"Tallet er ikke et primtall.");
        }
      }
      }
      String fortsettSjekkLes = showInputDialog("Vil du kjøre testen på nytt? (J/N)");
      char fortsettSjekk = fortsettSjekkLes.charAt(0);
      if(fortsettSjekk != 'J') {
        fortsett = false;
      }
    } while(fortsett);
  }
}
