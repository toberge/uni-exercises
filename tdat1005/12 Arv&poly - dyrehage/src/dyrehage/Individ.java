package dyrehage;

// abstrakt siden vi lager spesialutgaver for hunn- og hannindivid (wot ab0ut friggin not-damn-gendered animals? ahahaha)
abstract class Individ extends Dyr implements SkandinaviskeRovdyr {

    private final String navn;
    private final int fDato;
    private final boolean hanndyr; // not allowing transgender whales I guess
    private final boolean farlig;

    public Individ(String norskNavn, String latNavn, String latFamilie, int ankommetDato, String adresse,
                   String navn, int fDato, boolean hanndyr, boolean farlig) {

        super(norskNavn, latNavn, latFamilie, ankommetDato, adresse);

        if (navn == null || navn.trim().equals("") || fDato < 19000000) {
            throw new IllegalArgumentException("Hey, you've got some faulty args in your INDIVIDUALITY");
        }

        // TODO check input

        this.navn = navn;
        this.fDato = fDato;
        this.hanndyr = hanndyr;
        this.farlig = farlig;

    }

    @Override
    public String getNavn() {
        return navn;
    }

    @Override
    public int getFdato() {
        return fDato;
    }

    @Override
    public int getAlder() {
        return (getAnkommetDato() - fDato) / 10000; // making it be year count, no rounding because there should not be any
    }

    public boolean isHanndyr() {
        return hanndyr;
    }

    public boolean isFarlig() {
        return farlig;
    }

    // why does Intellij scream about these needing to be public cuz it otherwise would be of weaker access?
    @Override
    public void flytt(String nyAdresse) {

        if (nyAdresse != null && !nyAdresse.trim().equals("")) {

            super.setAdresse(nyAdresse);

        }

    }

    @Override
    public String skrivUtInfo() {
        return toString(); // TODO decide if more should be done here
    }

    @Override
    public String toString() {
        StringBuilder bldr = new StringBuilder(navn);

        bldr.append(", født ");
        bldr.append(fDato);
        bldr.append(", et ");
        bldr.append(farlig? "farlig " : "ufarlig ");
        bldr.append(hanndyr? "hanndyr " : "hunndyr ");
        bldr.append("av arten"); // info, tho. ikke godt ordvalg
        bldr.append(System.lineSeparator());
        bldr.append("\t");
        bldr.append(super.toString());

        return bldr.toString();
    }
}
