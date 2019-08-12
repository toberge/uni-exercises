public class Fraction {
  private int teller;
  private int nevner;

  public Fraction(int teller, int nevner) {
    if(nevner == 0) {
      throw new IllegalArgumentException("Nevneren kan ikke settes lik null.");
    }
    this.teller = teller;
    this.nevner = nevner;
  }

  public Fraction(int teller) {
    this.teller = teller;
    nevner = 1;
  }

  public String toString() {
    if(nevner == 1) {
      return "" + teller;
    } else {
      return "" + teller + " / " + nevner;
    }
  }

  public int getTeller() {
    return teller;
  }

  public int getNevner() {
    return nevner;
  }

  /* Does not matter here
  private void checkFract(Object other) {
    if (!(other instanceof Fraction)) {
      throw new IllegalArgumentException("Objektet tilhører ikke riktig klasse.");
    }
    Fraction otherFract = (Fraction) other;
    if(otherFract == this) {
      throw new IllegalArgumentException("Objektet er det samme som dette objektet.");
    }
  }
  */

  public void simplify() { // TODO: MAKE BETTER THING
    if(nevner < 0) {
      teller = -teller;
      nevner = -nevner;
    } // begge verdier inverteres hvis negativ nevner; dette tar for seg alle muligheter
    if(teller % nevner == 0) {
      teller = teller / nevner;
      nevner = 1;
    } else {
      // deprecated, malfunctioning checks:
      // teller / nevner == (int) ((double) teller / (double) nevner + 0.5) || nevner / teller == (int) ((double) nevner / (double) teller + 0.5)
      // if(teller % nevner == 0 || nevner % teller == 0)
      // if(teller * (nevner / teller) == nevner || nevner * (teller / nevner) == nevner)
      // må sjekke flere tall og finne ut om begge kan deles på dem
      int i = 2;
      while(i < teller && i < nevner) {
        //System.out.println("Er " + teller + " og " + nevner + " delelig på " + i + "?");
        if(teller % i == 0 && nevner % i == 0) {
          teller /= i;
          nevner /= i;
          //System.out.println("Ja. Nå er brøken " + toString());
          i = 1;
        }
        i++;
      }
    }
  }

  private int fellesNevner(Fraction otherFract) {
    int teller = otherFract.getTeller();
    int nevner = otherFract.getNevner();

    if(this.nevner != nevner) {
      this.teller *= nevner;
      teller *= this.nevner;
      this.nevner *= nevner;
      return teller;
    } else {
      return teller;
    }
  }

  public void add(Fraction otherFract) {
    int teller = fellesNevner(otherFract);
    this.teller += teller;
    simplify();
  }

  public void sub(Fraction otherFract) {
    int teller = fellesNevner(otherFract);
    this.teller -= teller;
    simplify();
  }

  public void mult(Fraction otherFract) {
    int teller = otherFract.getTeller();
    int nevner = otherFract.getNevner();

    this.teller *= teller;
    this.nevner *= nevner;
    simplify();
  }

  public void div(Fraction otherFract) {
    int teller = otherFract.getTeller();
    int nevner = otherFract.getNevner();

    this.teller *= nevner; //flipped
    this.nevner *= teller;
    simplify();
  }

  public static void main(String[] args) {
    System.out.println("Totalt antall tester: 5");

    Fraction add1 = new Fraction(3,2);
    Fraction add2 = new Fraction(1,3);
    add1.add(add2);
    if(add1.getTeller() == 11 && add1.getNevner() == 6) {
      System.out.println("Test 1 vellykket; addisjon fungerer.");
    }

    Fraction sub1 = new Fraction(4,2);
    Fraction sub2 = new Fraction(1,3);
    sub1.sub(sub2);
    if(sub1.getTeller() == 5 && sub1.getNevner() == 3) {
      System.out.println("Test 2 vellykket; subtraksjon fungerer.");
    }

    Fraction mult1 = new Fraction(4,7);
    Fraction mult2 = new Fraction(6,7);
    mult1.mult(mult2);
    if(mult1.getTeller() == 24 && mult1.getNevner() == 49) {
      System.out.println("Test 3 vellykket; multiplikasjon fungerer.");
    }

    Fraction div1 = new Fraction(4,7);
    Fraction div2 = new Fraction(6,7);
    div1.div(div2);
    if(div1.getTeller() == 2 && div1.getNevner() == 3) {
      System.out.println("Test 4 vellykket; divisjon fungerer.");
    }

    Fraction simp1 = new Fraction(-4,-3);
    Fraction simp2 = new Fraction(4,-3);
    Fraction simp3 = new Fraction(28,7);
    Fraction simp4 = new Fraction(-12,30);
    simp1.simplify();
    simp2.simplify();
    simp3.simplify();
    simp4.simplify();
    if(simp1.getTeller() == 4 && simp1.getNevner() == 3 &&
       simp2.getTeller() == -4 && simp2.getNevner() == 3 &&
       simp3.getTeller() == 4 && simp3.getNevner() == 1 &&
       simp4.getTeller() == -2 && simp4.getNevner() == 5) {
      System.out.println("Test 5 vellykket; forenkling fungerer.");
    }
  }
}
