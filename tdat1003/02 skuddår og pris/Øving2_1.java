/**
* Dette er en mulig løsning av oppgave 3.12.3
*
* Den avgjør om et år er skuddår eller ikke.
*/

import static javax.swing.JOptionPane.*;
class Øving2_1 {
  public static void main(String[] args) {

      String yearIn = showInputDialog("Gi meg et år: ");
      int year = Integer.parseInt(yearIn);
      String message;

      /* Sjekker om året er før eller etter Kristi fødsel eller akkurat det året */
      if(year < 0) {
        year = year - 2 * year;
        message = year  + " fvt. ";
      } else if(year == 0) {
        message = year + " ";
      } else {
        message = year + " evt. ";
      }

      /* Vi kjører testen */
      if(year % 400 == 0) {
        message = message + "er et skuddår.";
      } else if(year % 100 == 0) {
        message = message + "er ikke et skuddår.";
      } else if(year % 4 == 0) {
        message = message + "er et skuddår.";
      } else {
        message = message + "er ikke et skuddår.";
      }

      /* Gir brukeren resultatet. */
      showMessageDialog(null,message);
  }
}
