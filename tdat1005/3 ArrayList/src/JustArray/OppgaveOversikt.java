package JustArray;

/**
 *
 * OppgaveOversikt.java
 *
 * Studentobjektene er aggregater!
 * hugs hugs
 *
 */

public class OppgaveOversikt {

    private Student[] studenter = new Student[5]; // set amount, how nice for the user who then doesn't need to scroll thru a massive list :p
    private int antStud = 0;

    // Trenger ingen spesifikk konstruktør

    // hjelpemetode
    private boolean isNullOrEmpty(String subject) {
        return (subject == null || subject.trim().equals(""));
    }

    private int indexOf(String navn) {

        // no need to check since it's internal

        for (int i = 0; i < antStud; i++) {

            if (navn.equalsIgnoreCase(studenter[i].getNavn())) {
                return i; // found
            }

        }

        return -1; // if none found

    }

    private void utvidTabell() {

        Student[] nyTab = new Student[studenter.length + 5];
        for (int i = 0; i < antStud; i++) {
            nyTab[i] = studenter[i];
        }
        studenter = nyTab;

    }

    public boolean regNyStudent(String navn) {

        if (isNullOrEmpty(navn) || indexOf(navn) >= 0) {
            return false; // gtfo if invalid str or already existing
        }

        if (antStud == studenter.length) {
            utvidTabell(); // expand
        }

        studenter[antStud] = new Student(navn);
        antStud++; // hendig indeks

        return true;

    }

    public int finnAntStud() {

        return antStud;

    }

    public int finnAntOppgaver(String navn) {

        if (isNullOrEmpty(navn)) {
            return -1; // gtfo if invalid str
        }

        int i = indexOf(navn);

        if (i < 0) {
            return -1; // finner ikke stud
        }

        return studenter[i].getAntOppg();

    }

    public boolean økAntOppg(String navn, int økning) {

        if (isNullOrEmpty(navn) /*|| økning <= 0*/) {
            return false; // gtfo if invalid str or increase NO SKIP THE LAST BIT STUPID
        }

        int i = indexOf(navn);

        if (i < 0) {
            return false; // finner ikke stud
        }

        int grunnlag = studenter[i].getAntOppg();
        studenter[i].setAntOppg(grunnlag + økning);

        return true;

    }

    public String[] finnAlleNavn() { // navnene til alle studentene

        String[] list = new String[antStud];

        for (int i = 0; i < antStud; i++) {

            list[i] = studenter[i].getNavn();

        }

        return list;

    }

    @Override
    public String toString() {

        StringBuilder bldr = new StringBuilder("Her kommer info om alle studentene:\n");

        if (antStud > 0) {

            for (int i = 0; i < antStud; i++) {

                bldr.append(studenter[i].toString());
                bldr.append("\n");

            }

        } else {
            bldr.append("Ingen studenter å si noe om.\n");
        }

        return bldr.toString();

    }

}
