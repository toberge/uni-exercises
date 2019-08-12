/**
* Player.java
* Manages player stats in this game
*/
import java.util.Random;
class Player {
  private final String name;
  private byte score;
  private Random dice = new Random();

  public Player(String name,byte score) {
    this.name = name;
    this.score = score;
  }

  public Player(String name) {
    this.name = name;
  }
  public Player() {
    this.name = "Unknown";
  }

  public String toString() {
    return "Player " + name + " has " + score + " points.";
  }

  public String getName() {
    return name;
  }

  public byte getScore() {
    return score;
  }

  public void setScore(byte score) {
    this.score = score;
  }

  public void raiseScore(byte raise) {
    score += raise;
  }

  public void killScore() {
    score = 0;
  }

  public String throwDice() {
    byte cast = (byte) (1 + dice.nextInt(6));
    if(cast == 1) {
      this.killScore();
    } else if(score > 100) {
      this.raiseScore((byte) (0 - cast));
    } else {
      this.raiseScore(cast);
    }
    return "Player " + name + " cast " + cast;
  }
}
