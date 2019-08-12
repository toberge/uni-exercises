/**
 *
 *	tables flip themselves indefinitely
 *
 */


public class Tables {
	
	private String[] reservations;

	public Tables(int numTables) {

		if (numTables <= 0) {
			throw new IllegalArgumentException("invalid table count passed on omg"); // that one lonely exception
		}

		reservations = new String[numTables];

	}

	public int length() {
		return reservations.length;
	}

	public int getNumFree() {

		int counter = 0;

		for (int i = 0; i < reservations.length; i++) {

			if (reservations[i] == null) {

				counter++;

			}

		}

		return counter;

	}

	public int getNumTaken() {

		int counter = 0;

		for (int i = 0; i < reservations.length; i++) {

			if (reservations[i] != null) { // a named pearson has resurved

				counter++;

			}

		}

		return counter;

		// or simply return reservations.length - getNumFree(); or the opposite way

	}

	public boolean freeTable(int i) {

		if (i >= 0 && i < reservations.length) {


			if (reservations[i] != null) {

				reservations[i] = null; // freeing
				return true;

			} else {
				// allerede ryddet
				return true; // I guess

			}

			// eller bare reservations[i] = null; then return true;

		}

		return false;

	}

	public boolean reserveTable(int i, String name) {

		if (i >= 0 && i < reservations.length && name != null) {

			if (reservations[i] == null) {

				reservations[i] = name;
				return true;

			} else {

				return false; // not free

			}

		}

		return false;

	}

	/* NUH NUH DONT USE
	public boolean checkTable(int i, String client) {

		if (i >= 0 && i < reservations.length && client != null) {

			return client.equalsIgnoreCase(reservations[i]); // will do the check

		}

		return false;

	}
	*/

	// USE THIS
	public String getName(int i) {
		return reservations[i]; // will return null but that works here
	}

}

/*

    Finn antall ledige bord
    Finn antall bord opptatt
    Frigi bord som er ryddet
    Reserver bord

*/



//pack.age::Class in class diagram - okay?