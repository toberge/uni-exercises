/**
 * Klassen Ticket med subklasser - 2010-01-18
 * Denne blir delt ut sammen med øvingen
 */
abstract class Ticket {
  private final String tribuneName;
  private final int price;

  /**
   * Konstruktør:
   * Tribunenavn må oppgis. Ingen krav til pris.
   */
  public Ticket(String tribuneName, int price) {
    if (tribuneName == null || tribuneName.trim().equals("")) {
      throw new IllegalArgumentException("Tribunenavn må oppgis.");
    }
    this.tribuneName = tribuneName.trim();
    this.price = price;
  }

  public String getTribune() {
    return tribuneName;
  }

  public int getPrice() {
    return price;
  }

  public String toString() {
    return "Tribune: "+ tribuneName + " Pris: "+ price;
  }
}

/**
 * Ståplassbilletter.
 */
class StandingTicket extends Ticket {
  public StandingTicket(String tribuneName, int price) {
    super(tribuneName, price);
  }
}

/**
 * Sitteplassbilletter. Rad og plass på raden må oppgis.
 */
class SeatedTicket extends Ticket {
  private final int row;
  private final int seat;

  public SeatedTicket(String tribuneName, int price, int row, int seat) {
    super(tribuneName, price);
    if (row < 0 || seat < 0) {
      throw new IllegalArgumentException("Verken rad eller plass kan være negativ.\n"
                                                           + "Oppgitte verdier: " + row + ", " + seat);
    }
    this.row = row;
    this.seat = seat;
  }

  public int getRow() {
    return row;
  }

  public int getSeat() {
    return seat;
  }

  public String toString() {
    String res = super.toString();
    res += " Rad: "+ row + " Plass: " + seat;
    return res;
  }
}
