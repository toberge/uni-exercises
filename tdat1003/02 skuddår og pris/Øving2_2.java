/**
* Oppgave 3.12.4
*
* Programmet sammenligner prisen på to spesifkke merker kjøttdeig.
* Burde være gydlig løsning på oppgava hvis vi ikke skal gjøre den dynamisk.
*/

class Øving2_2 {
  public static void main(String[] args) {

    /* Bruker spesifikt disse verdiene: */
    float prisA = 35.90F;
    short gramA = 450;
    float prisB = 39.50F;
    short gramB = 500;

    System.out.println("Kjøttdeig av merke A koster " + prisA + " kr for " + gramA + " gram,");
    System.out.println("mens merke B koster " + prisB + " kr for " + gramB + " gram.\nHvilket er billigst, mon tro?");

    /* Sammenlikner prisene */
    if(prisA / gramA < prisB / gramB) {
      System.out.println("Merke A er billigere");
    } else if(prisA / gramA == prisB / gramB) {
      System.out.println("Merkene er (trolig) like dyre");
    } else {
      System.out.println("Merke B er billigere");
    }


  }
}
