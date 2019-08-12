/**
*
*
*
*/
import java.util.Scanner;
class Game {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    final byte goal = 100;

    System.out.println("Write player A's name: ");
    Player playerA = new Player(sc.nextLine());
    System.out.println("Write player B's name: ");
    Player playerB = new Player(sc.nextLine());
    System.out.println("");
    Player activePlayer = new Player();


    playerA.killScore();
    playerB.killScore();
    int roundCount = 1;
    do {
      if(roundCount % 2 != 0) {
      System.out.println("This is round " + roundCount / 2);
      }

      if(activePlayer == playerA) {
        activePlayer = playerB;
      } else {
        activePlayer = playerA;
      }

      System.out.println(activePlayer.throwDice()); // the core of the game, in a string

      if(roundCount % 2 == 0 && activePlayer.getScore() < goal) {
        System.out.print("Player " + playerA.getName() + " has " + playerA.getScore() + " points. ");
        System.out.println("Player " + playerB.getName() + " has " + playerB.getScore() + " points.");
        System.out.println("");
      }
      roundCount++;
    } while (activePlayer.getScore() != goal);

    //System.out.prinln("");
    System.out.print("Player " + activePlayer.getName() + " has " + activePlayer.getScore() + " points. ");
    System.out.println("Player " + activePlayer.getName() + " is the winner.");
    //System.out.println("Do you want to play again?");
    //scan for shit
    System.out.println("");

    // TODO
    // announce winner - DONE
    // loop at user's prompt, if ya want - NAH
    // find way to halt - but that'll be tedious given the absurd amount of turns it takes
    // make game be won when score is exactly 100 - DONE
    // refurbish the loop logic, perhaps? - NAH

  }
}

/*private void pressContinue() {

}*/
