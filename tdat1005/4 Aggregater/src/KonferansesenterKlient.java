import static javax.swing.JOptionPane.*;

/**
 *
 *     Lag et klientprogram der brukeren kan legge inn data. Sørg for at alle metodene i klassen Konferansesenter blir prøvd ut. Bruk for eksempel følgende mal:
 *
 *         Begynn med å registrere alle rommene.
 *         Les inn noen reservasjoner.
 *         Skriv ut all registrert informasjon. Bruk metodene som finner antall rom og rom gitt indeks.
 *         Prøv ut metoden som finner et rom, gitt romnummer. La brukeren skrive inn romnummeret. All info om rommet, inkludert reservasjonene skal skrives ut.
 *
 */

public class KonferansesenterKlient {

    public static void main(String[] args) {

        GUI gui = new GUI();

        String navn = gui.fetchString("Gi konferansesenteret et navn:");
        Konferansesenter konf = new Konferansesenter(navn);

        int antRom = gui.fetchInt("Hvor mange rom skal det ha?", 1);

        for (int i = 0; i < antRom; i++) {

            int romnr = gui.fetchInt("Romnummer:", 1);
            int størrelse = gui.fetchInt("Kapasitet i antall personer:", 1);

            konf.regNyttRom(romnr, størrelse);

        } // rommene er registrert

        int antRes = gui.fetchInt("Hvor mange reservasjoner vil du gjøre?", 0);

        for (int i = 0; i < antRes; i++) {

            // omfg
            String count = "(" + (i+1) + " av " + antRes + ")";

            Tidspunkt fraTid = gui.fetchTime("Fra når");
            Tidspunkt tilTid = gui.fetchTime("Til når");
            gui.showInfoMsg("Altså fra " + fraTid + System.lineSeparator() + " til " + tilTid);

            int størrelse = gui.fetchInt("Minste aksepterte kapasitet:", 1);
            String kundensNavn = gui.fetchString("Hva er denne kundens navn?");
            String tlf = gui.fetchString("Du skulle vel ikke tilfeldigvis ha kundens tlfnr. også?");

            if (konf.reserverRom(fraTid, tilTid, størrelse, kundensNavn, tlf)) {
                gui.showInfoMsg("Jaggu meg gikk det bra, rom reservert " + count);
            } else {
                gui.showErrorMsg("Det oppstod et problem med reservasjonen" + count);
            }

        } // eventuelle reservasjoner er registrert

        gui.showInfoMsg(konf.toString(), "vær så god all info");

        // rom gitt indeks
        // hvorfor


        // rom gitt romnr
        int nr = gui.fetchInt("Oppgi et romnummer for å søke", 1, 9999);
        Rom funn = konf.finnRomNummer(nr);
        if (funn != null) {
            gui.showInfoMsg("Vi fant et rom: " + System.lineSeparator() + funn);
        } else {
            gui.showErrorMsg("Jaggu meg var det ikke noe rom registrert på det nummeret");
        }

        System.out.println("Brukeren kom seg helskinnet gjennom programmet.");

    }

}

class GUI {

    private static final String[] choices = {""};

    public void showErrorMsg(String msg) {
        showMessageDialog(null, "you got failmail:\n" + msg, "Error message", ERROR_MESSAGE);
    }

    public void showInfoMsg(String msg) {
        showMessageDialog(null, msg, "Gode nyheter", INFORMATION_MESSAGE);
    }

    public void showInfoMsg(String msg, String title) {
        showMessageDialog(null, msg, title, INFORMATION_MESSAGE);
    }

    public String fetchString(String msg) {

        String res;

        do {

            res = showInputDialog(msg);

        } while (res == null || res.trim().equals("")); // goddamn persistent stuff

        return res;

    }

    public int fetchInt(String msg, int lowerLimit) {
        return fetchInt(msg, lowerLimit, Integer.MAX_VALUE); // yeah
    }

    public int fetchInt(String msg, int lowerLimit, int upperLimit) {

        int res = lowerLimit - 1;

        do {

            try {

                res = Integer.parseInt(showInputDialog(msg));

            } catch (NumberFormatException e) {
                showErrorMsg("Ugyldig tall");
            }

        } while (res < lowerLimit && res > upperLimit);

        return res;

    }

    public Tidspunkt fetchTime(String msg) {

        // sørger for å bare akseptere tall i brukbare intervaller
        int dato = fetchInt(msg + " - oppgi dato (ååååmmdd)", 1, 99993112);
        int tid = fetchInt(msg + " - oppgi tid (mmss)", 1, 5959);

        return new Tidspunkt((long)dato * 10000 + tid);
        //return new Tidspunkt(Long.parseLong(showInputDialog(msg))); // placeholder

    }

}