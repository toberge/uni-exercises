package ArrayList;

/**
 *
 * OppgaveOversikt.java
 *
 * Studentobjektene er aggregater!
 * hugs hugs
 *
 * Dette er utgaven med ArrayList. Handle with care.
 *
 */

public class OppgaveOversikt {

    private java.util.ArrayList<Student> studenter = new java.util.ArrayList<Student>();
    //private int antStud = 0;

    // Trenger ingen spesifikk konstruktør

    // hjelpemetode
    private boolean isNullOrEmpty(String subject) {
        return (subject == null || subject.trim().equals(""));
    }

    private int indexOf(String navn) {

        // no need to check since it's internal

        // vewy schimple solushon THAT IS INCREDIBLY FAULTY OH SHIT NOOOOOO      YOU
        // kan fortsatt ha en metode for å skrive bittelitt mindre I guess      BLOODY
        // men den kan oppsummeres i studenter.indexOf(new Student(navn)) så...  FOOL
        // fixed by actually making an equals method.
        //Student temp = new Student(navn);
        //return studenter.indexOf(temp); // it not equal you shithead unless u maek equals method

        // forkaster det ovenfor. lite effektivt å opprette et nytt objekt for å søke gjennom, dessuten gjorde jeg noe galt.


        for (int i = 0; i < studenter.size(); i++) {

            if (navn.equalsIgnoreCase(studenter.get(i).getNavn())) {
                return i; // found
            }

        }

        return -1; // if none found

    }

    // trenger ikke utvide tabell

    public boolean regNyStudent(String navn) {

        if (isNullOrEmpty(navn) || indexOf(navn) >= 0) {
            return false; // gtfo if invalid str or already existing
        }

        studenter.add(new Student(navn)); // so much simpler

        return true;

    }

    public int finnAntStud() {

        return studenter.size();

    }

    public int finnAntOppgaver(String navn) {

        if (isNullOrEmpty(navn)) {
            return -1; // gtfo if invalid str
        }

        int i = indexOf(navn);

        if (i < 0) {
            return -1; // finner ikke stud
        }

        return studenter.get(i).getAntOppg();

    }

    public boolean økAntOppg(String navn, int økning) {

        if (isNullOrEmpty(navn)) {
            return false; // gtfo if invalid str (we do safeguard against this here tho)
        }

        int i = indexOf(navn);

        if (i < 0) {
            return false; // finner ikke stud
        }

        int grunnlag = studenter.get(i).getAntOppg();
        studenter.get(i).setAntOppg(grunnlag + økning);

        return true;

    }

    public String[] finnAlleNavn() { // navnene til alle studentene

        String[] list = new String[studenter.size()];

        for (int i = 0; i < studenter.size(); i++) {

            list[i] = studenter.get(i).getNavn();

        }

        return list;

    }

    @Override
    public String toString() {

        StringBuilder bldr = new StringBuilder("Her kommer info om alle studentene:\n");

        if (studenter.size() > 0) {

            for (int i = 0; i < studenter.size(); i++) {

                bldr.append(studenter.get(i).toString());
                bldr.append("\n");

            }

        } else {
            bldr.append("Ingen studenter å si noe om.\n");
        }

        return bldr.toString();

    }

}
