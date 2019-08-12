package ArrayList;

/**
 *
 * Student.java
 * Fairly simple
 * Quite the pimple
 *
 */

public class Student {

    private final String navn;
    private int antOppg;

    // con stru ct
    public Student(String navn, int antOppg) {
        this.navn = navn;
        this.antOppg = antOppg;
    }

    public Student(String navn) {
        this.navn = navn;
        this.antOppg = 0;
    }

    // get shit
    public String getNavn() {
        return navn;
    }

    public int getAntOppg() {
        return antOppg;
    }

    public void setAntOppg(int antOppg) {
        if (antOppg < 0) {
            throw new IllegalArgumentException("Negativt antall oppgaver. Begone.");
        }
        this.antOppg = antOppg;
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Student)) {
            return false;
        }

        if (o == this) {
            return true;
        }

        Student tmp = (Student) o;

        return this.navn.equalsIgnoreCase(tmp.getNavn()); // let name be deciding factor

    }

    @Override
    public String toString() {

        StringBuilder bldr = new StringBuilder("Navn: ");
        bldr.append(navn);
        bldr.append(", antall oppgaver løst: ");
        bldr.append(antOppg);

        return bldr.toString();

    }

}
