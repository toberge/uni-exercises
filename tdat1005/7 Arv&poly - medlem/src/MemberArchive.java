import java.time.LocalDate;

class MemberArchive {

    private java.util.ArrayList<BonusMember> members = new java.util.ArrayList<>();


    private BonusMember findMember(int memberID) {

        if (memberID <= 0) {
            return null;
        }

        for (BonusMember member : members) {

            if (member.getMemberID() == memberID) { // lezzee if found
                return member;
            }

        }

        return null;

    }

    /**
     * finnPoeng() skal ta medlemsnummer og passord som argument og returnere antall poeng denne kunden har spart opp.
     * Returner en negativ verdi hvis medlem med dette nr ikke fins, eller passord er ugyldig.
     */
    public int findPoints(int memberID, String password) {

        BonusMember member = findMember(memberID);

        if (member != null) {

            return member.okPassword(password)? member.getPoints() : -1; // return if matching password

        } else {

            return -1;

        }

    }

    // for verification purposes during testing
    String findClass(int memberID) {

        BonusMember member = findMember(memberID);

        if (member != null) {

            return member.getClass().getSimpleName().replace("Member", "");

        } else {

            return null;

        }

    }

    /**
     * registrerPoeng() skal ta medlemsnummer og antall poeng som argument og sørge for at riktig antall poeng blir registrert for dette medlemmet.
     * Returner false dersom medlem med dette nr ikke fins.
     */
    public boolean registerPoints(int memberID, int points) {

        BonusMember member = findMember(memberID);

        if (member != null) {

            return member.registerPoints(points); // does the point validation by itself

        } else {

            return false;

        }

    }

    /**
     * will make computer spin infinitely when all ints are used, but that's a tad too unlikely here
     *
     * @return a *new* random ID
     */
    private int findUnusedID() {

        java.util.Random rng = new java.util.Random();

        int memberID;

        do {

            memberID = Math.abs(rng.nextInt()); // no bound?

        } while (findMember(memberID) != null); // while it isn't a new one

        return memberID;

    }

    /**
     * Metoden skal opprette et objekt av klassen BasicMedlem og legge dette inn i arkivet. (Alle medlemmer begynner som basic-medlemmer.) Metoden skal returnere medlemsnummeret.
     *
     * Metoden skal bestemme medlemsnummeret ved å kalle følgende private metode:
     *
     * private int finnLedigNr()
     *
     * Denne metoden skal hente ut et tilfeldig heltall (bruk klassen Random) som ikke allerede er i bruk som medlemsnr.
     *
     * @return
     */
    public int newMember(Personalia personalia, LocalDate signUpDate) {

        BasicMember newcomer;
        int newID = findUnusedID();

        try {

            newcomer = new BasicMember(newID, personalia, signUpDate);

        } catch (IllegalArgumentException e) {
            System.out.println(personalia);
            System.out.println(signUpDate);
            e.printStackTrace();
            return -1;
        }

        members.add(newcomer);
        return newID;

    }


    /**
     * sjekkMedlemmer() skal gå gjennom alle medlemmene og foreta oppgradering av medlemmer som er kvalifisert for det.
     * Basic-medlemmer kan kvalifisere seg for sølv eller gull, mens sølvmedlemmer kan kvalifisere seg for gull.
     * Tips: Du trenger å finne ut hvilken klasse et objekt tilhører. Bruk operatoren instanceof.
     * Det er ikke mulig å omforme klassetilhørigheten til et objekt. Du må i stedet lage et nytt objekt med data fra det gamle.
     * Det nye objektet må legges inn i ArrayListen på den plassen der det gamle lå (bruk metoden set()).
     */
    public void updateMembers(LocalDate date) {

        for (int i = 0; i < members.size(); i++) {

            BonusMember member = members.get(i);

            if (member instanceof BasicMember) {

                if (GoldMember.REQUIRED_POINTS <= member.findQualifiedPoints(date)) { // GOLD 4 U

                    members.set(i, new GoldMember(member.getMemberID(), member.getPersonalia(), member.getSignUpDate(), member.getPoints()));

                } else if (SilverMember.REQUIRED_POINTS <= member.findQualifiedPoints(date)) { // SILVER 4 U

                    members.set(i, new SilverMember(member.getMemberID(), member.getPersonalia(), member.getSignUpDate(), member.getPoints()));

                }

            } else if (member instanceof SilverMember) {

                if (GoldMember.REQUIRED_POINTS <= member.findQualifiedPoints(date)) { // GOLD 4 U

                    members.set(i, new GoldMember(member.getMemberID(), member.getPersonalia(), member.getSignUpDate(), member.getPoints()));

                }

            } // no upgrade for goldies

        }

    }

    public String toString() {

        StringBuilder res = new StringBuilder("MemberArchive:");

        for (BonusMember member : members) {

            res.append(System.lineSeparator());
            res.append(member);

        }

        return res.toString();

    }

}
