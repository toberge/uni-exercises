/**
* Dette er en mulig løsning av oppgave 3.12.3
*
* Den avgjør om et år er skuddår eller ikke.
*/

import static javax.swing.JOptionPane.*;
class Øving2_1_optim {
  public static void main(String[] args) {

      String yearIn = showInputDialog("Gi meg et år: ");
      int year = Integer.parseInt(yearIn);
      String message;

      /* Sjekker om året er før eller etter Kristi fødsel eller akkurat det året */
      if(year > 0) {
        message = year + " evt. ";
      } else if(year < 0) {
        year = year - 2 * year;
        message = year  + " fvt. ";
      } else { // hvis ikke er det null
        message = year + " ";
      }

      /* Vi kjører testen */
      if(year % 4 == 0) { // tester om året i det hele tatt kan være et skuddår, mest effektivt nå.
        if(year % 100 != 0) { // i så fall, er det noe annet enn et hundreår?
          message = message + "er et skuddår.";
        } else if(year % 400 == 0) { //
          message = message + "er et skuddår.";
        } else {
          message = message + "er ikke et skuddår.";
        }
      } else {
        message = message + "er ikke et skuddår.";
      }

      /* Gir brukeren resultatet. */
      showMessageDialog(null,"År " + message);
  }
}
