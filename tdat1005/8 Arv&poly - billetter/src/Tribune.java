abstract class Tribune implements Comparable<Tribune>, java.io.Serializable {

    private final String name;
    private final int capacity;
    private final int price;

    protected Tribune(String name, int capacity, int price) {

        this.name = name;
        this.capacity = capacity;
        this.price = price;

    }

    // NECESSARY get methods

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPrice() {
        return price;
    }

    // ABSTRACT methods

    public abstract int getTicketsSold();

    public abstract int getIncome();

    /**
     * This defines an array as acceptable only if it doesn't contain empty shit
     */
    protected boolean verifyArray(String[] names) {

        if (names == null) {

            return false; // GTFO

        }

        for (String name : names) {

            if (name == null || name.trim().equals("")) { // if empty

                return false; // fuck them all

            }

        }

        return true; // nah they fine

    }

    public abstract Ticket[] buyTickets(int amount);

    public abstract Ticket[] buyTickets(String[] names);

    /**
     * OPS det er ikke frivillig å sortere, nei.
     */
    @Override
    public int compareTo(Tribune tribune) {

        // return Integer.compare(this.getIncome(), tribune.getIncome()); foreslår IntelliJ, ja

        if (this.getIncome() < tribune.getIncome()) { // mindre --> foran
            return -1;
        } else if (this.getIncome() > tribune.getIncome()) { // større --> etter
            return 1;
        } else {
            return 0; // ergo like
        }
    }

    /**
     * Oppgaveteksten: For hver tribune skal du også skrive ut tribunenavn, kapasitet, antall solgte billetter og inntekten.
     */
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
}
