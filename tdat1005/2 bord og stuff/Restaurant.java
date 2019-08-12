public class Restaurant {
	
	private String name;
	private int estYear;

	private Tables tables;

	public Restaurant(String name, int estYear, int numTables) {

		if (name == null || numTables <= 0) {
			throw new IllegalArgumentException("eyo");
		}

		this.name = name;
		this.estYear = estYear;
		this.tables = new Tables(numTables);

	}

	public String getName() {

		return name;

	}

	public void setName(String name) {

		if (name != null) {

			this.name = name; // no check - but now there is

		}

	}

	public int getEstYear() { // bad name

		return estYear; // indeed

	}

	public int getAge() {

		java.util.Calendar cal = java.util.Calendar.getInstance(); // is abstract can't be instantiated --> must get instance
		return cal.get(java.util.Calendar.YEAR) /*- 1900*/ - estYear; // calculating years since establishment

	}

	public String getNameOfTable(int i) {
		return tables.getName(i); // calling down
	} 

	// calling down to the tables object
	public int getNumFree() {

		return tables.getNumFree();

	}

	public int getNumTaken() {

		return tables.getNumTaken();

	}

	public boolean reserveTables(String client, int numTables) {

		if (client == null || numTables <= 0 || numTables > tables.getNumFree()) {
			return false;
		}

		// TODO yeah it DONE NOEW

		int i = 0;
		int max = tables.length(); // uh, I realize length might be an unintuitive name, sorry
		while (numTables > 0 && i < max) {

			if (tables.reserveTable(i, client)) { // using Tables method

				numTables--; // found a table to free

			}

			i++; // this makes more sense as a while but could've been a multi-condition for (altho not strictly necessary to have multiple conditions)

		}

		return (numTables == 0); // just in case something went wrong

	}

	// PROBLEM: Somehow the table with index 0 gets pushed to the end of the returned array. wtf.
	// it fixed
	public int[] findTables(String client) {

		if (client == null) return null;

		int counter = 0;
		int numTaken = tables.getNumTaken();
		int[] list = new int[tables.length()];

		//int numCopied = 0; // using a custom index for reasons WAIT A MINUTE IT'S the one above omg I must've been out of my mind

		for (int i = 0; i < tables.length(); i++) {

			if (client.equalsIgnoreCase(tables.getName(i))) {

				list[counter] = i;
				counter++;

				if (counter == numTaken) break;

			}

		}

		int[] res = new int[counter]; // shorter table

		for (int i = 0; i < counter; i++) {

			res[i] = list[i]; // just a shorter one

		}

		return res; // then return that copy

		// IT'S FRIGGIN FIXED HOLY SHIET

		/*
		boolean shitHappens = false; // ABSOLUTELY SHITTY FIX KILL ME PLEASE OH GOD
		// WAIT A MINUTE I DO IT OTHERWISE

		for (int i = 0; i < tables.length(); i++) {

			String thatName = tables.getName(i);

			if (client.equals(thatName)) { // equals takes care of null

				list[counter] = i; // its them indexes you moron
				counter++;

				if (counter == numTaken) break;

				if (i == 0) shitHappens = true; // I should store an... no. no index needed. it shall be in the 0 spot anyway. yeah. --OR WHAT?

			} 

		}

		if (counter == 0) return null; // nut fiend

		int[] res = new int[counter];
		int numCopied = 0;



		for (int i = 0; i < counter; i++) {

			if (list[i] != 0) { // OKAY WTF THE HELL

				res[numCopied] = list[i];
				numCopied++;

				if (numCopied == 0) break; // done

			}
		}

		return res;
		*/

	}

	// I see what you did here, teacher
	public boolean freeTables(int[] indexes) {

		if (indexes == null || indexes.length > tables.length()) {
			return false; // obvious fault
		}

		// testing the array more - should I do that?
		int max = tables.length();
		for (int i = 0; i < indexes.length; i++) {

			if (indexes[i] >= max || indexes[i] < 0) {

				return false; // jump out since bad array

			}

		}

		// we needn't check for dupes, exactly
		for (int i = 0; i < indexes.length; i++) {

			if (!tables.freeTable(indexes[i])) {

				return false; // jump out already or try more? i don't think this'll happen now, even - WHY WOULD IT?

			}

		}

		return true; // we're done

	}

	public String toString() {

		String res = name + " est. " + estYear + " (age " + getAge() + ")\n";

		for (int i = 0; i < tables.length(); i++) {

			String client = tables.getName(i);
			res += (client == null)? (i+1) + ": Free table\n" : (i+1) + ": " + client + "\n";

		}

		return res;

	}

	/*
    *Finn navnet på restauranten
    *Sett et nytt navn på restauranten
    *Finn etableringsåret
    *Finn hvor gammel restauranten er
    *Finn antall ledige bord
    *Finn antall bord opptatt
    *Reserver et bestemt antall bord på Èn person (et navn)
    *Finn hvilke bord en bestemt person har reservert, bordnumrene skal returnes som int[]
    *Frigi bord som er ryddet, bordnumrene skal sendes inn som int[]

    Lag et enkelt testprogram.
	*/

    public static void main(String[] args) {
    	
    	// go thru the complex methods above?
    	System.out.println("We'll be running a few tests");

    	// JUnit FTW

    }

}