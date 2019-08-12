class Evighetsmaskin {
  public static void main(String[] args) {
    Person svingstang = new Person("Åge Marie", "Gundersen", (short) 1930);
    ArbTaker slave = new ArbTaker(svingstang, 30002000, (short) 1969, 30000.0f, 30.0f);
    BGS bgs = new BGS(slave);

    int valg = bgs.spørBrukern();
    while (valg >= 0) {
      bgs.adlydOrdre(valg);
      valg = bgs.spørBrukern();
    }
  }
}
