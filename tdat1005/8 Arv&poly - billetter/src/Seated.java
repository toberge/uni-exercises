/**
 *
 * på sittetribunene må man vite hvor mange som er solgt på hver rad. Vi antar at plassene selges i stigende rekkefølge innenfor hver rad.
 * I tillegg antar vi at programmet, ikke kjøperen, bestemmer hvilken rad som skal benyttes, noe du vil få bruk for i oppgave c.
 *
 *
 */


class Seated extends Tribune {

    private int[] numTaken;  // tabellstørrelse: antall rader

    public Seated(String name, int capacity, int price, int rows) { // hey there is no row-specific capacity

        super(name, capacity, price);

        numTaken = new int[rows];

    }

    // WHYYYY
    /*
    protected int getNumTaken(int i) {
        return numTaken[i]; // djeez
    }
    */

    public int getTicketsSold() {
        int sum = 0;

        for (int num : numTaken) {

            //num += sum; WTF
            sum += num; // there we go

        }

        return sum;
    }

    public int getIncome() {

        return getTicketsSold() * getPrice();

    }


    protected int findSuitableRow(int amount) {

        int bestIndex = -1;
        int bestCount = amount + 1;

        for (int i = 0; i < numTaken.length; i++) {

            if (numTaken[i] < bestCount) {

                bestIndex = i;
                bestCount = numTaken[i]; // whoops forgot this one

            }

        }

        return bestIndex;

    }

    /**
     * For alle tribunetyper gjelder at hvis man ønsker seg flere billetter enn det som kan effektueres, *********får man ingen.************
     * På sittetribuner begrenses dette av at alle billetter som selges i en bestilling, skal være på samme rad.
     * På ståtribuner er det bare den totale kapasiteten, og hvor mange som hittil er solgt, som setter begrensninger.
     */
    public Ticket[] buyTickets(int amount) {

        if (getTicketsSold() + amount > getCapacity()) { // the ultimate test ++++

            return null;

        }

        int index = findSuitableRow(amount);
        if (index < 0) return null;

        Ticket[] tickets = new Ticket[amount];

        for (int i = 0; i < amount; i++) {

            numTaken[index]++; // TODO why this not affect toString?
            tickets[i] = new SeatedTicket(getName(), getPrice(), index, numTaken[index]); // exploiting the array to get seat number

        }

        return tickets;

    }

    public Ticket[] buyTickets(String[] names) {

        if (!verifyArray(names)) {
            return null;
        }

        return buyTickets(names.length);

    }

    /**
     * Oppgaveteksten: For hver tribune skal du også skrive ut tribunenavn, kapasitet, antall solgte billetter og inntekten.
     */
    /*
    @Override
    public String toString() {

        StringBuilder bldr = new StringBuilder(getName());

        bldr.append(", Capacity: ");
        bldr.append(getCapacity());
        bldr.append(", tickets sold: ");
        bldr.append(getTicketsSold());
        bldr.append(" granting a total income of: ");
        bldr.append(getIncome());

        return bldr.toString();

    }
    */

}
