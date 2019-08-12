import java.time.LocalDate;

class TestBonusmedlem {
  public static void main(String[] args) throws Exception {
    Personalia ole = new Personalia("Olsen", "Ole", "ole.olsen@dot.com", "ole");
    Personalia tove = new Personalia("Hansen", "Tove", "tove.hansen@dot.com", "tove");
    LocalDate testdato = LocalDate.of(2008, 2, 10);
    System.out.println("Totalt antall tester: 8");

    BasicMember b1 = new BasicMember(100, ole, LocalDate.of(2006, 2, 15));
    b1.registerPoints(30000);
    if (b1.findQualifiedPoints(testdato) == 0 && b1.getPoints() == 30000) {
      System.out.println("Test 1 ok");
    }
    b1.registerPoints(15000);
    if (b1.findQualifiedPoints(testdato) == 0 && b1.getPoints() == 45000) {
      System.out.println("Test 2 ok");
    }

    BasicMember b2 = new BasicMember(110, tove, LocalDate.of(2007, 3, 5));
    b2.registerPoints(30000);
    if (b2.findQualifiedPoints(testdato) == 30000 && b2.getPoints() == 30000) {
      System.out.println("Test 3 ok");
    }

    SilverMember b3 = new SilverMember(b2.getMemberID(), b2.getPersonalia(), b2.getSignUpDate(), b2.getPoints());
    b3.registerPoints(50000);
    if (b3.findQualifiedPoints(testdato) == 90000 && b3.getPoints() == 90000) {
      System.out.println("Test 4 ok");
    }

    GoldMember b4 = new GoldMember(b3.getMemberID(), b3.getPersonalia(), b3.getSignUpDate(), b3.getPoints());
    b4.registerPoints(30000);
    if (b4.findQualifiedPoints(testdato) == 135000 && b4.getPoints() == 135000) {
      System.out.println("Test 5 ok");
    }

    testdato = LocalDate.of(2008, 12, 10);
    if (b4.findQualifiedPoints(testdato) == 0 && b4.getPoints() == 135000) {
      System.out.println("Test 6 ok");
    }

    if (!ole.okPassord("OOO")) {
      System.out.println("Test 7 ok");
    }
    if (tove.okPassord("tove")) {
      System.out.println("Test 8 ok");
    }
  }
}

/*

First run: Invalid point count --> found out the points weren't being set (I had forgotten "this")
Second run: 3, 7 and 8 ok --> BasicMember point setting works, the password check in Personalia works, but the formula for calculating points must be off.
Turns out the getDays() method in Period is very wrongk, redone with getYears()
Third run: 1-3 and 7-8, still problems with calculating points in gold and silver
fixed later, it seems. problem was the constructor using the regPoints method, which increased the starting amount... whoops.

 */