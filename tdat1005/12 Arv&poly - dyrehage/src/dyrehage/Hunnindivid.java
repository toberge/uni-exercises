package dyrehage;

class Hunnindivid extends Individ {

    private int antKull;

    public Hunnindivid(String norskNavn, String latNavn, String latFamilie, int ankommetDato, String adresse,
                       String navn, int fDato, boolean farlig,
                       int antKull) {

        super(norskNavn, latNavn, latFamilie, ankommetDato, adresse, navn, fDato, false, farlig);

        if (antKull < 0) throw new IllegalArgumentException("kull null/mindre");

        this.antKull = antKull; // in case there already were bits of coal attached to this one's body...

    }

    public Hunnindivid(String norskNavn, String latNavn, String latFamilie, int ankommetDato, String adresse,
                       String navn, int fDato, boolean farlig) {

        super(norskNavn, latNavn, latFamilie, ankommetDato, adresse, navn, fDato, false, farlig);

        antKull = 0; // in case there already were bits of coal attached to this one's body...

    }

    public int getAntKull() {
        return antKull;
    }

    public void leggTilKull(int antall) {
        if (antall > 0) {
            antKull += antall;
        }
    }

    public void leggTilNyttKull() {
        antKull++;
    }

    @Override
    public String toString() {
        return super.toString() + " --> antall kull: " + antKull;
    } // very lazy I know

}

class Hannindivid extends Individ {

    public Hannindivid(String norskNavn, String latNavn, String latFamilie, int ankommetDato, String adresse,
                       String navn, int fDato, boolean farlig) {

        super(norskNavn, latNavn, latFamilie, ankommetDato, adresse, navn, fDato, true, farlig);
        // schimple dimple
    }

    @Override
    public int getAntKull() {
        return 0;
    }

    @Override
    public void leggTilKull(int antall) {
    }

    @Override
    public void leggTilNyttKull() {
    }

}
