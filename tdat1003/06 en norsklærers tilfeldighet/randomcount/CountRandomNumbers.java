/*
* CountRandomNumbers.java
*
* this thingy takes 1000 random digits and counts the instances of each
* - serving the result in a relatively nice package.
*
*/

import java.util.Random;

class CountRandomNumbers {
  public static void main(String[] args) {
    Random rng = new Random();
    int[] counTable = new int[10];
    final int runs = 1000;

    for(int i = 0; i < runs; i++) {
      counTable[rng.nextInt(10)]++;
    }

    int total = 0;
    int divider = 10;

    for(int i = 0; i < counTable.length; i++) {

      System.out.print("" + i + "  " + counTable[i] + "\t");
      total += counTable[i];

      int stars = (int) (((double) counTable[i] / divider) + 0.5);
      for(int j = 0; j < stars ; j++) {
        System.out.print("*");
      }

      System.out.println();
    }

    if(total != runs) { // just in case some weird something goes wrongk
      System.out.println("Totalt " + total + " numre ble telt...");
    } else {
      System.out.println("Alt gikk fint.");
    }
  }
}
