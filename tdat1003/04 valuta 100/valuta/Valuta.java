/*
* Definerer ei klasse for valutaobjekter
* Disse skal regne om fra NOK til andre valutaer.
*/

class Valuta {
  private final String navn;
  private final double faktor;

  public Valuta(String navn, double faktor) {
    this.navn = navn;
    this.faktor = faktor;
  }

  public String toString() {
    return "1 " + navn + " er " + faktor + " NOK.";
  }

  public String getNavn() {
    return navn;
  }

  public double getFaktor() {
    return faktor;
  }

  public double regnOmTil(double beløp) {
    beløp = beløp * faktor;
    return beløp;
  }

  public double regnOmFra(double beløp) {
    beløp = beløp / faktor;
    return beløp;
  }

  public String regnStringTil(double beløp) {
    return beløp + " " + navn + " er " + regnOmTil(beløp) + " NOK.";
  }
  public String regnStringFra(double beløp) {
    return beløp + " NOK er " + regnOmFra(beløp) + " " + navn + ".";
  }
}
