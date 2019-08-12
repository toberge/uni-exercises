package dyrehage;

/**
 * Dyregrupper: Dyr vi forholder oss til i grupper, som for eksempel fiskestimer, insektsvermer og fugleflokker.
 * Om hver gruppe lagrer vi gruppenavn og omtrentlig antall individer i gruppen.
 * I tillegg har vi spesielt tilpassede klasser for hhv. fiskestimer
 * (der vi lagrer gjennomsnittlig lengde for et voksent eksemplar av arten og om den kan dele akvarium med en annen dyregruppe)
 * og fugleflokker (der vi lagrer gjennomsnittlig vekt for et voksent eksemplar av arten og om det er en type svømmefugl) som du ser i klassediagrammet.
 */

abstract class Dyregruppe extends Dyr {

    private final String gruppenavn;
    private int antIndivider;


    public Dyregruppe(String norskNavn, String latNavn, String latFamilie, int ankommetDato, String adresse,
               String gruppenavn, int antIndivider) {

        super(norskNavn, latNavn, latFamilie, ankommetDato, adresse);

        if (gruppenavn == null || gruppenavn.trim().equals("") || antIndivider < 0) {
            throw new IllegalArgumentException("Ey, dem GRUPPEARGS BE WRONGK");
        }

        this.gruppenavn = gruppenavn;
        this.antIndivider = antIndivider;

    }

    // metoden i Dyr er final, da kan ingen underklasser override den.
    @Override
    public String getNorskNavn() {
        return "gruppe av " + super.getNorskNavn();
    }

    public String getGruppenavn() {
        return gruppenavn;
    }

    public int getAntIndivider() {
        return antIndivider;
    }

    public void setAntIndivider(int antIndivider) {
        if (antIndivider > 0) {
            this.antIndivider = antIndivider;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

class Fiskestim extends Dyregruppe {

    private final float gjennomsnittligLengde;
    private final boolean kanDeleAkvarium;

    public Fiskestim(String norskNavn, String latNavn, String latFamilie, int ankommetDato, String adresse,
                     String gruppenavn, int antIndivider,
                     float gjennomsnittligLengde, boolean kanDeleAkvarium) {

        super(norskNavn, latNavn, latFamilie, ankommetDato, adresse, gruppenavn, antIndivider);

        if (gjennomsnittligLengde < 0.0f) throw new IllegalArgumentException("Negative length");

        this.gjennomsnittligLengde = gjennomsnittligLengde;
        this.kanDeleAkvarium = kanDeleAkvarium;

    }

    public float getGjennomsnittligLengde() {
        return gjennomsnittligLengde;
    }

    public boolean kanDeleAkvarium() {
        return kanDeleAkvarium;
    }
}

class Fugleflokk extends Dyregruppe {

    private final float gjennomsnittligVekt;
    private final boolean kanSvømme;

    public Fugleflokk(String norskNavn, String latNavn, String latFamilie, int ankommetDato, String adresse,
                     String gruppenavn, int antIndivider,
                     float gjennomsnittligVekt, boolean kanSvømme) {

        super(norskNavn, latNavn, latFamilie, ankommetDato, adresse, gruppenavn, antIndivider);

        if (gjennomsnittligVekt < 0.0f) throw new IllegalArgumentException("Negative weight");

        this.gjennomsnittligVekt = gjennomsnittligVekt;
        this.kanSvømme = kanSvømme;

    }

    public float getGjennomsnittligVekt() {
        return gjennomsnittligVekt;
    }

    public boolean kanSvømme() {
        return kanSvømme;
    }

}