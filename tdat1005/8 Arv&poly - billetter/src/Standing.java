/**
 * For ståtribunene trenger man bare å holde rede på hvor mange plasser som er solgt
 *
 *
 */

class Standing extends Tribune {

    private int ticketsSold;

    public Standing(String name, int capacity, int price) {

        super(name, capacity, price);

        ticketsSold = 0;

    }

    @Override // WHY does IntelliJ let this pop up here? it's not defined in the superclass
    public int getTicketsSold() {
        return ticketsSold;
    }

    public int getIncome() {

        return ticketsSold * getPrice();

    }

    public Ticket[] buyTickets(int amount) {

        if (amount < 1 || getTicketsSold() + amount > getCapacity()) { // if invalid or **impossible** amount
            return null;
        }

        Ticket[] tickets = new Ticket[amount];

        for (int i = 0; i < amount; i++) {

            tickets[i] = new StandingTicket(getName(), getPrice());

        }

        ticketsSold += amount;

        return tickets;

    }

    public Ticket[] buyTickets(String[] names) {

        if (!verifyArray(names)) { // takes care of everything
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
        bldr.append(ticketsSold);
        bldr.append(" granting a total income of: ");
        bldr.append(getIncome());

        return bldr.toString();

    }
    */

}
