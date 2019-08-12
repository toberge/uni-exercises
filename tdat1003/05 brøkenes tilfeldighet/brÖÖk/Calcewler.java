/*
*
*
* Skriver ut regnestykket før utregninga skjer;
* følgelig trengs ingen flere variabler
*/

import java.util.Scanner;
import static javax.swing.JOptionPane.*;

class Calcewler {
  private static Scanner sc = new Scanner(System.in);

  private static int fetchNum(String type) {
    System.out.print("Skriv inn " + type + ": ");
    int num = sc.nextInt();
    return num;
  }

  public static void main(String[] args) {
    boolean cont = true;
    char op;
    do {
      // operasjon velges og tall leses
      System.out.println("Hva slags operasjon vil du utføre? (+, -, * eller /): ");
      String opRead = showInputDialog("+, -, * eller /, noe annet for å avslutte");
      if(opRead != null) {
        op = opRead.charAt(0);
      } else {
        op = 'n';
      }
      if(op == '+' || op == '-' || op == '*' || op == '/') {
        System.out.println("Vi vil ha tellere og nevnere. De skal være heltall.");
        int teller1 = fetchNum("første teller");
        int nevner1 = fetchNum("første nevner");
        int teller2 = fetchNum("andre teller");
        int nevner2 = fetchNum("andre nevner");
        Fraction fract = new Fraction(teller1, nevner1);
        Fraction fractX = new Fraction(teller2, nevner2);

        // regnestykke skrives
        fract.simplify();
        fractX.simplify();
        System.out.print(fract.toString());
        System.out.print("  " + op + "  ");
        System.out.print(fractX.toString());
        System.out.print("  =  ");

        // operasjoner utføres
        switch(op) {
          case '+':
            fract.add(fractX);
            break;
          case '-':
            fract.sub(fractX);
            break;
          case '*':
            fract.mult(fractX);
            break;
          case '/':
            fract.div(fractX);
            break;
          default:
            cont = false;
            break;
        }

        if(cont) {
          // resultat skrives
          System.out.print(fract.toString());
          System.out.println("");
        } else {
          System.out.println("\nWhat happened here?");
        }
      } else {
        cont = false;
      }
    } while(cont);
    System.out.println("Velkommen tilbake.");
  } // end main
} // end class
