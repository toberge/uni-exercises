/**
 * Noen sittetribuner er VIP-tribuner. Der vil vi også holde rede på navnet på de som skal sitte på de ulike plassene.
 */

class VIP extends Seated {

    private String[][] spectators; // tabellstørrelse: antall rader * antall plasser pr rad

    public VIP(String name, int capacity, int price, int rows, int seatsPerRow) {

        super(name, capacity, price, rows);

        spectators = new String[rows][seatsPerRow];

    }

    // will work as long as the array is filled and never has its contents removed.
    public int getTicketsSold() {

        int count = 0;

        for (int i = 0; i < spectators.length; i++) {

            for (int j = 0; j < spectators[i].length; j++) {

                if (spectators[i][j] == null) {
                    break; // go to next subarray - TODO if removing is implemented, remove this.
                } else {
                    count++; // there is a name here (we don't let empty names inside)
                }

            }

        }

        return count;

    }


    // STOP you shouldn't override...
    // or, no, you SHOULD!
    protected int findSuitableRow(int amount) {

        int bestIndex = -1;
        int bestCount = amount + 1; // starting with impossible

        for (int i = 0; i < spectators.length; i++) {

            int count = 0;

            for (int j = 0; j < spectators[i].length; j++) {

                if (spectators[i][j] != null) {

                    count++;

                }

            } // end seats

            if (count <= spectators[i].length - amount && count < bestCount) { // check if valid and better count

                // replacing values
                bestIndex = i;
                bestCount = count;

            }

        } // end rows

        return bestIndex;

    } // end meth

    @Override
    public Ticket[] buyTickets(int amount) {

        return null; // shan't buy a ticket without specifying names

    }

    /**
     * For alle tribunetyper gjelder at hvis man ønsker seg flere billetter enn det som kan effektueres, *********får man ingen.************
     * På sittetribuner begrenses dette av at alle billetter som selges i en bestilling, skal være på samme rad.
     * På ståtribuner er det bare den totale kapasiteten, og hvor mange som hittil er solgt, som setter begrensninger.
     */
    @Override
    public Ticket[] buyTickets(String[] names) {

        if (!verifyArray(names) || names.length > spectators[0].length || getTicketsSold() + names.length > getCapacity()) {

            return null; // if array exceeds seat count or limit or contains empty shit, throw it away

        }

        int amount = names.length; // or loop thru and count not-null names - NOPE
        int row = findSuitableRow(amount);

        if (row < 0) { // if we don't have space here

            return null;

        }

        int counter = 0; // where we are in the array of names and tickets
        Ticket[] tickets = new Ticket[amount];


        for (int seat = 0; counter < amount && seat < spectators[row].length; seat++, counter++) { // TODO unscrew for loop if it's bad enough

            if (spectators[row][seat] == null) {

                spectators[row][seat] = names[counter];
                tickets[counter] = new SeatedTicket(getName(), getPrice(), row, seat);
                //counter++; moved to head
                //amount--; why? excuse me?

            }

        }

        return tickets;

    }

    /**
     * Leste ikke hele oppgaveteksten, djeez
     */
    @Deprecated
    public Ticket[] buyTicketsTheFancyWay(String[] names) {

        // TODO take care of the "fact" that the superclass has a numTaken array
        // no, wait, this is a subclass so we don't need to do that

        // TODO test array BASICALLY DONE

        int nameCounter = 0; // counting 'till end of names array
        int numTickets = 0; // counting successful

        Ticket[] tickets = new Ticket[names.length];

        for (int i = 0; nameCounter < names.length && i < spectators.length; i++) { // thru rows

            for (int j = 0; nameCounter < names.length && j < spectators[i].length; j++) { // thru seats

                if (spectators[i][j] == null) {

                    String name = names[nameCounter];

                    if (name != null) {

                        spectators[i][j] = name; // TODO clean up, check if name is null and make another counter for names array DONE KINDA
                        tickets[numTickets] = new SeatedTicket(getName(), getPrice(), i, j); // make new ticket (use golden not paper)
                        numTickets++;

                    } // end inner if

                    nameCounter++;

                } // end outer if

            } // end seats

        } // end rows

        // TODO reset operation if not enough free seats instead of ignoring it, perhaps...

        // make ticket array smol if needed
        Ticket[] copy = new Ticket[numTickets];

        for (int i = 0; i < numTickets; i++) {

            copy[i] = tickets[i];

        }

        return copy;

    }

}
