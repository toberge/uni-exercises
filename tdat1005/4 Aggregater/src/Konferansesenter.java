import java.util.ArrayList;

/**
 * Konferansesenter.java
 *
 *      Fra oppgaveteksten:
 * Reserver rom, gitt tidspunkt fra og til, antall personer samt navn og telefonnummer til kunden. (Her skal romnummer ikke være parameter,
 * metoden skal selv finne et rom som er ledig og med plass til det oppgitte antallet personer.)
 * Registrer et nytt rom med romnummer og størrelse (ikke tillatt dersom rom med dette nummer fins fra før)
 * Finn antall rom
 * Finn et bestemt rom, gitt indeks
 * Finn et bestemt rom, gitt romnr
 *
 * Inne i klassen Konferansesenter skal du ha en ArrayList av romobjekter. Skisser metodene i klassen Konferansesenter. Det bør gi ideer til hvilke metoder du trenger i klassen Rom.
 *
 *
 * Diverse notater:
 * rommene.sort(); --> bruker Arrays.sort()
 * java.util.Collections.sort(rommene); --> det boka sier (up to date indeed)
 *
 *
 */

public class Konferansesenter {

    private String navn;

    private java.util.ArrayList<Rom> rommene;

    /*
    public Konferansesenter(java.util.ArrayList<Rom> rommene) {

        if (rommene == null) {
            throw new IllegalArgumentException("Lista kan'kke væra null");
        }

        this.rommene = rommene; // oh, the wonders of aggregering

    }
    */

    public Konferansesenter(String navn) {

        if (navn == null || navn.trim().equals("")) {
            throw new IllegalArgumentException("Tomt navn, gitt");
        }

        this.rommene = new ArrayList<>();
        this.navn = navn;

    }

    public Konferansesenter() {

        navn = "Konferansesenter";
        rommene = new ArrayList<>();

    }

    public String getNavn() {
        return navn;
    }

    public int finnAntallRom() {
        return rommene.size(); // skal ikke ha noen null-referanser
    }
    // done

    /**
     * Metoden finner rom gitt indeks
     *
     * @param indeks
     * @return null if not found, Rom if found
     */
    public Rom finnRomIndeks(int indeks) {

        if (indeks < 0 || indeks >= rommene.size()) {
            return null; // ugyldig indeks
        }

        return rommene.get(indeks); // wooo farlige saker med aggregater woohoo

    }
    // done

    /**
     * Metoden finner rom gitt romnummer
     *
     * @param romnr
     * @return null if not found, Rom if found
     */
    public Rom finnRomNummer(int romnr) {

        // kan heller bruke indexOf nå som jeg har equals-metode
        // nei, drit i.
        /*
        Rom rommet;

        try {
            rommet = new Rom(romnr, 1);
        } catch (IllegalArgumentException e) { // bruker konstruktørens sjekker
            return null;
        }

        // vent, vet jeg hva jeg driver med nå?

        int i = rommene.indexOf();
        */


        if (romnr < 1) {
            return null; // ugyldig
        }

        // søke gjennom

        for (Rom rommet : rommene) {

            if (rommet.getRomNr() == romnr) {

                return rommet; // fant riktig nummer

            }

        }

        return null; // fant det ikke

    }

    /**
     * Oppgavetekst:
     * Registrer et nytt rom med romnummer og størrelse (ikke tillatt dersom rom med dette nummer fins fra før)
     *
     * @return hvorvidt det kunne registreres
     */
    public boolean regNyttRom(int romnr, int størrelse) {

        if (størrelse < 1 || romnr < 1 || finnRomNummer(romnr) != null) {

            return false; // gale parametere eller rommet fins fra før

        }

        Rom rommet = new Rom(romnr, størrelse);

        rommene.add(rommet); // lægge te

        return true;

    }


    /**
     * Metoden finner et ledig rom ut fra størrelse på rommet
     * og om tidspunktene overlapper med noen av reservasjonene som allerede er der
     * (privat metode, mind you)
     *
     * @return referanse til et rom i ArrayList-en
     */
    private Rom finnLedigRom(int størrelse, Tidspunkt fraTid, Tidspunkt tilTid) {

        for (Rom rommet : rommene) {

            if (rommet.getStørrelse() >= størrelse && // større eller lik 4 now, TODO: burde prioritere mindre rom e.l.
                rommet.erLedig(fraTid, tilTid)) {

                return rommet;

            }

        }

        return null;

    }

    /**
     * Metoden reserverer et rom hvis parameterne er gyldige og gir et resultat.
     *
     * @return
     */
    public boolean reserverRom(Tidspunkt fraTid, Tidspunkt tilTid, int størrelse, String navn, String tlf) {

        if (fraTid == null || tilTid == null || størrelse < 1) { // there now

            return false;

        }
        // TODO kan evt flyttes til finnLedig

        /*
        Rom rommet;
        boolean funnet = false;

        int indeks = finnLedigRom(størrelse);
        rommet = rommene.get(indeks);

        if (!rommet.overlapp(fraTid, tilTid)) {

        }
        */

        Rom rommet = finnLedigRom(størrelse, fraTid, tilTid);

        if (rommet != null) {

            // oppretter objektene først her
            //Kunde nyKunde = new Kunde(navn, tlf);
            //Reservasjon reservasjon = new Reservasjon(fraTid, tilTid, nyKunde);

            //rommet.reserver(reservasjon); // eeeeeh dis be sketchy

            //return true;

            return rommet.reserver(fraTid, tilTid, navn, tlf); // the better way

        } else {

            return false;

        }

    }

    @Override
    public String toString() {

        String NL = System.lineSeparator();

        StringBuilder bldr = new StringBuilder(navn);
        bldr.append(": ");
        bldr.append(finnAntallRom());
        bldr.append(" rom registrert");
        bldr.append(NL); // evt ant ledige

        for (Rom rommet : rommene) {

            bldr.append(NL);
            bldr.append(rommet.toString());

        }

        return bldr.toString();

    }

}
