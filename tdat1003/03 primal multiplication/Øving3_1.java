/**
*
* 4.14.1 og 4.14.5 er oppgavene her
* multiplikasjonstabell og primtall
*
* Oppgave 4.14.1:
*
* Vi skal skrive ut en del av multiplikasjonstabellen, brukeren skal bestemme hvor mye (minst ett tall) og tallet skal bare ganges med 1-10.
*
*/

import static javax.swing.JOptionPane.*;
class Øving3_1 {
  public static void main(String[] args) {
    System.out.println("Vi skal dra en liten gangetabell ut av deg, bruker.");


    String lowerIn = showInputDialog("Gi meg et heltall,\nhelst den nedre delen av intervallet");
    String upperIn = showInputDialog("Gi meg et heltall til,\nhelst den øvre delen av intervallet");

    int lower = Integer.parseInt(lowerIn);
    int upper = Integer.parseInt(upperIn);


    if(upper < lower) {
      System.out.println("Huff da, er brukeren virkelig så kranglete?");
      System.out.println("Vi bytter om på rekkefølgen av verdiene. *kremt*");
      int foo = lower;
      lower = upper;
      upper = foo;
    }

    for(int grower = lower;grower <= upper;grower++) { // letting grower grow
      System.out.println("" + grower + "-gangen:");
      for(int times = 0;times <= 10;times++) {
        System.out.println("" + grower + " x " + times + " = " + (grower * times));
      }
    }

  }
}
