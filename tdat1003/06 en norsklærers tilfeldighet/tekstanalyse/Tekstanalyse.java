public class Tekstanalyse {
  private int[] antallTegn = new int[30];
  private static final int[] unicode = new int[29]; // siste er alt annet
  private static final String alfa = "abcdefghijklmnopqrstuvwxyzæøå";
  private static boolean unicodeRun = false;

  //private static final int[] unicode = {97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,230,248,229};

  private static boolean initiateUnicode() {
    if (alfa.length() != unicode.length) {
      return false;
    }
    for (int i = 0; i < unicode.length; i++) {
    char tegn = alfa.charAt(i);
    int verdi = tegn;
    unicode[i] = verdi;
    }
    unicodeRun = true;
    if(unicode[unicode.length - 1] <= 0) {
      return false;
    } else {
      return true;
    }
  } // end method

  public Tekstanalyse(String tekst) {
    if (!unicodeRun) {
      throw new IllegalArgumentException("Har ikke blitt kalibrert!");
    }

    tekst = tekst.toLowerCase();

    for (int i = 0; i < tekst.length(); i++) {
      boolean erBokstav = false;

      for (int j = 0; j < unicode.length; j++) {
        if(unicode[j] == tekst.charAt(i)) {
          erBokstav = true; // for å vite om vi fant en bokstav eller ei
          antallTegn[j]++;
        }
      }

      if(!erBokstav) {
        antallTegn[antallTegn.length - 1]++;
      }
    }
  } // end method

  public Tekstanalyse() {
    if(!unicodeRun && !initiateUnicode()) { // oppretter tabellen hvis så ikke allerede er gjort, og sjekker om noe gikk galt der.
      throw new IllegalArgumentException("Alfabetet er av feil lengde, tabellen er allerede initiert, eller noe annet skjedde.");
    }
  }

  public String toString() {
    String res = "";
    for (int i = 0; i < alfa.length() + 1; i++) {
      if (i < alfa.length()) {
        res += alfa.charAt(i) + ":\t" + antallTegn[i] + "\n";
      } else {
        res += "Andre tegn: " + antallTegn[i] + "\n";
      }
    }
    return res;
  }

  public int antForskjelligeBokst() {
    int antall = 0;

    for (int i = 0; i < unicode.length; i++) { // kun for bokstaver
      if(antallTegn[i] > 0) {
        antall++;
      }
    }

    return antall;
  }

  public int antBokstaver() {
    int antall = 0;

    for (int i = 0; i < unicode.length; i++) { // kun for bokstaver
      antall += antallTegn[i];
    }

    return antall;
  }

  public double prosIkkeBokst() {
    return 100 * ((double) antallTegn[unicode.length] / (antBokstaver() + antallTegn[unicode.length]));
  }

  public int forekomstAv(char bokstav) {
    //int verdi = bokstav;
    for (int j = 0; j < unicode.length; j++) {
      if(unicode[j] == bokstav) {
        return antallTegn[j];
      }
    }
    return -1;
  }

  public String mestFrekventeBokst() {
    String res = "";
    int maks = 0;

    for (int i = 0; i < unicode.length; i++) { // finne maksverdi først
      if (antallTegn[i] > maks) {
        maks = antallTegn[i];
      }
    }

    for (int i = 0; i < unicode.length; i++) { // hente bokstavene
      if (antallTegn[i] == maks) {
        res += (char) unicode[i];
      }
    }

    return res;
  }

  public static void main(String[] args) {
    if (initiateUnicode()) {
      for (int i = 0; i < alfa.length(); i++) {
        System.out.println(alfa.charAt(i) + " " + unicode[i]);
      }
      for (int i = 0; i < alfa.length(); i++) {
        System.out.print("" + unicode[i] + ",");
      }
      System.out.println("" + unicode.length);
    }
  }
} // end class
