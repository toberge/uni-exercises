import com.sun.istack.internal.NotNull;

import java.util.Objects;

/**
 * Rom.java
 *
 * det oppgitte antallet personer --> trenger variabel for kapasitet
 * dessuten romnummer, obviously
 *
 * Oppgavetxxt:
 * Inne i klassen Rom skal du ha en ArrayList av reservasjonsobjekter. Programmer metodene i klassen Rom.
 * Lag et testprogram for denne klassen på tilsvarende måte som det er gjort for de klassene som er gitt.
 * Prøv ut at metodene virker, f.eks. om de håndterer overlapp i tid ved reservasjon av rommet.
 *
 */

public class Rom {

    private final int romNr;
    private final int størrelse;
    private java.util.ArrayList<Reservasjon> reservasjoner;

    public Rom(int romNr, int størrelse) {

        if (romNr < 1 || størrelse < 1) {

            throw new IllegalArgumentException("Et av tallene er for lite");

        }

        this.romNr = romNr;
        this.størrelse = størrelse;
        reservasjoner = new java.util.ArrayList<>();

    }

    public int getRomNr() {
        return romNr;
    }

    public int getStørrelse() {
        return størrelse;
    }

    // get + set reservasjon somehow
    // done

    // skal jeg virkelig sjekke overlapp her? hm hm, jo, det må jeg nesten, har en tabell
    // omdøpt til erLedig() fra overlapp()
    public boolean erLedig(Tidspunkt fraTid, Tidspunkt tilTid) {

        for (Reservasjon reservasjonen : reservasjoner) {

            if (reservasjonen.overlapp(fraTid, tilTid)) {

                return false; // fant et overlapp --> kan'kke reservere, gitt

            }

        }

        return true; // fant ingen overlappende reservasjoner

    }

    @Deprecated
    public boolean reserver(Reservasjon nyReservasjon) {

        if (nyReservasjon == null || !erLedig(nyReservasjon.getFraTid(), nyReservasjon.getTilTid())) {

            return false; // BUT WE CHECK THAT SHIT BEFORE WE GO HERE!!! welp yeah

        }

        return reservasjoner.add(nyReservasjon); // or just add then return true anyway... but since I can

    }


    /**
     * Legge til en ny reservasjon i lista hvis dette er mulig
     *
     * @return hvorvidt det gikk bra
     */
    public boolean reserver(Tidspunkt fraTid, Tidspunkt tilTid, String navn, String tlf) {

        // hey, dette er overhodet ikke nødvendig i dette tilfellet, men kunne evt vært det ellers I guess
        if (fraTid == null || tilTid == null || navn == null || tlf == null
                || !erLedig(fraTid, tilTid)) { // sjekker også om det er ledig da VENT, det sjekkes også i Konferansesenter-metoden finnLedigRom()... ops.

            return false;

        }

        // hvis sjekken gikk bra er det bare å opprette og legge til
        Kunde nyKunde = new Kunde(navn, tlf);

        Reservasjon reservasjon;
        try {
            reservasjon = new Reservasjon(fraTid, tilTid, nyKunde);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false; // to avoid sudden death
        }

        reservasjoner.add(reservasjon);

        return true;

    }

    @Override
    public boolean equals(Object o) { // brukes ikke as of right now

        if (this == o) return true;

        //if (o == null || getClass() != o.getClass()) return false; IntelliJ sitt forslag, sikkert meget bedre
        if (!(o instanceof Rom)) return false;

        Rom rom = (Rom) o;

        return getRomNr() == rom.getRomNr(); // avhenger av
    }

    @Override
    public String toString() {

        java.util.Formatter f = new java.util.Formatter();

        // 4 sifre bestemte jeg meg for av en eller annen grunn, bruker Formatter pga det
        f.format("Rom nr. %04d, %d-roms - ", romNr, størrelse);

        if (reservasjoner.size() == 0) {
            f.format("fullstendig ledig.");
        } else {
            f.format("reservert:%n");

            for (Reservasjon reservasjon : reservasjoner) {

                f.format("%s%n", reservasjon.toString());

            }

        }

        return f.toString();

    }

    public static void main(String[] args) {

        // testie boi
        System.out.println("Vi skal overkjøre noen jævla tester, totalt 4 (derav 1 unødvendig)");

        Kunde forsøksKanin = new Kunde("Åse Marie Halvorsen", "999 88 999");
        Kunde særing = new Kunde("Petskjora Tempeltut", "314 15 926");
        Rom storsalen = new Rom(0236, 60);

        // teste reservasjon (viktigst)
        Tidspunkt morgon = new Tidspunkt(201901110930L);
        Tidspunkt formiddag = new Tidspunkt(201901111100L);
        Tidspunkt bombe = new Tidspunkt(201901111030L);
        Tidspunkt ettermiddag = new Tidspunkt(201901111330L);
        Tidspunkt kveld = new Tidspunkt(201901111900L);

        Reservasjon kykeliky = new Reservasjon(morgon, formiddag, forsøksKanin);
        Reservasjon kræsj = new Reservasjon(bombe, ettermiddag, særing);
        Reservasjon godKvæll = new Reservasjon(formiddag, kveld, forsøksKanin);

        if (storsalen.reserver(kykeliky) &&
            !storsalen.reserver(kræsj) && // OVERLAPP
            storsalen.reserver(godKvæll)) {

            System.out.println("Test av gammel reservasjon() vellykket.");

        }

        // resetter
        storsalen = new Rom(236, 60);

        if (storsalen.reserver(morgon, formiddag, forsøksKanin.getNavn(), forsøksKanin.getTlf()) &&
            !storsalen.reserver(bombe, ettermiddag, særing.getNavn(), særing.getTlf()) && // OVERLAPP
            storsalen.reserver(formiddag, kveld, forsøksKanin.getNavn(), forsøksKanin.getTlf())) {

            System.out.println("Test av ny reservasjon() vellykket"); // erLedig() er også testet

        }

        if (storsalen.getRomNr() == 236 && storsalen.getStørrelse() == 60) {

            System.out.println("Test av get-metoder vellykket");

        }

        if (storsalen.toString().contains("Rom nr. 0236, 60-roms - ")) { // should not need to depend on reservations having been done

            System.out.println("Test av toString() vellykket");
            System.out.println(System.lineSeparator() + storsalen);

        }

    }

}
