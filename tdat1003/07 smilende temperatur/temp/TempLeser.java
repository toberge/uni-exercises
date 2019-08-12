class TempLeser {
  public static void main(String[] args) {
    int[][] tempene = {{1,2,3,4},{-1,-2,-3,-4},{100,124,230,204},{-13,-20,-21,-17}};
    Temperaturer heythere = new Temperaturer(tempene);
    TempBGS bgs = new TempBGS(heythere);

    int valg = bgs.spørBrukern();
    while (valg >= 0)
    {
      bgs.adlydOrdre(valg);
      valg = bgs.spørBrukern();
    }
  }
}
