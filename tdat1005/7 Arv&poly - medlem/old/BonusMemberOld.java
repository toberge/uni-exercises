import java.time.LocalDate;
import java.time.Period;

/**
 * Oppgavetekst:
 * @link http://www.aitel.hist.no/fag/vprg/TDAT1005/ovinger/TDAT1005_arvpoly1.php
 *
 *
 */

class BonusMember {

    private final int memberID;
    private final Personalia personalia; // not an English word, mind you. find a replacement or die
    private final LocalDate signUpDate;
    private int points = 0;

    private static final int GAIN_FACTOR = 0; // factor for calcing points, this is the one that's gonna get overridden

    public BonusMember(int memberID, Personalia personalia, LocalDate signUpDate) {

        if (memberID < 0 || personalia == null || signUpDate == null) {

            throw new IllegalArgumentException("Invalid argument(s)");

        }

        this.memberID = memberID;
        this.personalia = personalia;
        this.signUpDate = signUpDate;

    }

    // constructor that shall always be called by gold and silver
    protected BonusMember(int memberID, Personalia personalia, LocalDate signUpDate, int points) {

        if (memberID < 0 || personalia == null || signUpDate == null || points <= 0) {

            throw new IllegalArgumentException("Invalid argument(s)");

        }

        this.memberID = memberID;
        this.personalia = personalia;
        this.signUpDate = signUpDate;
        this.points = points; // SPECIFICALLY TO SET POINTS djeez this is wrong, right?

    }


    // traditional get methods
    public int getMemberID() {
        return memberID;
    }

    public Personalia getPersonalia() { // aggregate
        return personalia;
    }

    public LocalDate getSignUpDate() { // aggregate
        return signUpDate;
    }

    public int getPoints() {
        return points;
    }

    // one additional get method, TODO: figure out a better way to do this. problem is overriding a constant in the subclasses
    public int getGainFactor() {
        return GAIN_FACTOR;
    }

    /**
     * Takes a date, assumes it's the current one and calculates how many of the accumulated points can be used to upgrade - but in a reeeeeally simple way
     *
     * finnKvalPoeng() skal returnere antall poeng som kan kvalifisere til oppgradering av medlemskapet til sølv eller gull.
     * Dersom innmeldingsdatoen ligger mindre enn 365 dager bak i tid i forhold til datoen som sendes inn som argument, returneres antall poeng.
     * Hvis det er mer enn ett år siden kunden meldte seg inn, returneres 0 poeng.
     *
     * @return points counting toward member upgrade
     */
    public int findQualifiedPoints(LocalDate current) {

        //Objects.requireNonNull(current, "hey it null"); is a thing, okay... shouldn't use it here.

        if (current == null) {

            return -1; // excuse me, no nulling

        }

        // Period seems a little screwed-up for this purpose
        // Oppgaveteksten er direkte villedende

        long monthsPassed = Period.between(signUpDate, current).toTotalMonths();

        // return points if within a year (med noen dagers slingringsmonn pga klassens begrensninger), -1 if negative, 0 if over a year
        return (monthsPassed < 13)?
                        ((monthsPassed > -1)? points : -1)
                        : 0;

        //int yearsPassed = Period.between(signUpDate, current).getYears(); // may be negative


        //return (yearsPassed == 0)? points : 0;

        /*
        if (yearsPassed >= 1) {

            return 0; // nope, won't get upgrade

        } else if (yearsPassed < 0) {

            return -1; // "current" date is *before* sign-up

        } else {

            return points; // yeah boi, you got it in time

        }
        */

        /* getDays() is FUCKING ATROCIOUS
        if (daysPassed > 365) {

            return 0; // nope, won't get upgrade

        } else if (daysPassed < 0) {

            return -1; // "current" date is *before* sign-up

        } else {

            return points; // yeah boi, you got it in time

        }
        */

    }

    /**
     * Simply throws it down to Personalia and lets that class's method handle it
     */
    public boolean okPassword(String password) {

        if (password == null) { // only need to check if null (and I'd prefer if that was done in Personalia

            return false;

        }

        return personalia.okPassord(password); // just passing it

    }


    /**
     * Alle medlemmer tjener i utgangspunktet det samme for den samme reisen på samme klasse.
     * (Og her stopper likheten mellom oppgaveteksten og velkjente bonusordninger...)
     * Sølvmedlemmer får et påslag i antall poeng på 20%, mens gullmedlemmer får et påslag på 50%.
     * Eksempel: Dersom en tur gir 5000 poeng i gevinst, skal 5000 poeng registreres for basic-medlemmer.
     * Sølvmedlemmer skal få 5000 x 1.2 = 6000 poeng, mens gullmedlemmer får 5000 x 1.5 = 7500 poeng.
     *
     * @return true if okay - TODO: decide wether this being boolean is necessary or not
     */
    /*
    public boolean registerPoints(int points) {

        if (points > 0) { // checks for invalid point count, must be positive and nonzero

            //System.out.print(this.points + " adding " + points);
            this.points += (int)(points * ((double)(getGainFactor() + 100) / 100) + 0.5); // this means we prolly won't need to override this method and thus possibly run into trouble with there being no set method for points
            return true;
            //System.out.println(" resulting in " + this.points + " with factor " + ((double)(getGainFactor() + 100) / 100));

            //this.points += (int)(points * getGainFactor() + 0.5); // if refactored to a float/double

        } else {
            return false;
        }

    }
    */
    public boolean registerPoints(int points) {

        if (points > 0) {

            this.points += points;
            return true;

        } else {

            return false;

        }

    }

    @Override
    public String toString() {

        return personalia.getEtternavn() + ", " + personalia.getFornavn() + ": " + this.getClass().getSimpleName() + " with " + getPoints() + "magnificent POINTS";

    }

}


/**
 * The subclasses become very simple
 *
 * Gold and silver set their own gain factor and points required to upgrade to them
 */
class BasicMember extends BonusMember {

    public BasicMember(int membID, Personalia personalia, LocalDate signUpDate) {

        super(membID, personalia, signUpDate);

    }

}

class SilverMember extends BonusMember {

    private static final float GAIN_FACTOR = 0.2f; // 20% more
    public static final int REQUIRED_POINTS = 25000; // available for registering purposes

    public SilverMember(int membID, Personalia personalia, LocalDate signUpDate, int points) {

        super(membID, personalia, signUpDate, points);

    }

    /*
    @Override
    public int getGainFactor() {
        return GAIN_FACTOR;
    }
    */

    // override register, gang med faktor før det sendes inn
    // Yeah, ser mye mer clean ut.

    @Override
    public boolean registerPoints(int points) {

        points *= GAIN_FACTOR;
        return super.registerPoints(points);

    }
}

class GoldMember extends BonusMember {

    private static final float GAIN_FACTOR = 0.5f; // 50% more
    public static final int REQUIRED_POINTS = 75000; // public 4 reasons


    public GoldMember(int membID, Personalia personalia, LocalDate signUpDate, int points) {

        super(membID, personalia, signUpDate, points);

    }

    /* dead constructor
    public GoldMember(int memberID, Personalia personalia, LocalDate signUpDate, int points) {

        super(memberID, personalia, signUpDate);

        if (points < REQUIRED_POINTS || !registerPoints(points)) {
            throw new IllegalArgumentException("Invalid point count");
        }

    }
    */

    /*
    @Override
    public int getGainFactor() {
        return GAIN_FACTOR;
    }
    */

    @Override
    public boolean registerPoints(int points) {

        points *= GAIN_FACTOR;
        return super.registerPoints(points);

    }

}

