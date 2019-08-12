import static javax.swing.JOptionPane.*;
import static simplegui.Prompts.*;

class Client {


	private static Restaurant instance;

	private static final String[] CHOICES = {"Reserve tables", "Find tables", "Free tables", "Change name", "Exit"};

	private static final byte RESERVE = 0;
	private static final byte FIND = 1;
	private static final byte FREE = 2;
	private static final byte RENAME = 3;
	private static final byte EXIT = 4;

	private static int showPrompt() {

		return showOptionDialog(null, "eyo chooz something\n" + instance, "superb menu", YES_NO_OPTION, PLAIN_MESSAGE, null, CHOICES, CHOICES[0]);

	}

	private static void reserveTable() {

		String client = fetchString("In whose name shall this table be taken?");
    	int amount = fetchInt("How many tables");

    	if (instance.reserveTables(client, amount)) {
    		showMsg("It is done.");
    	} else {
    		showErrorMsg("Couldn't do as requested");
    	}

	}

	private static void findTable() {

		String client = fetchString("What name are you l00king for?");

    	int[] array = instance.findTables(client); // something weird about this method
    	String msg = "Results:\n";
    	for (int i = 0; i < array.length; i++) {
    		msg += (i+1) + ": table no. " + (array[i]+1) + ": " + instance.getNameOfTable(array[i]) + "\n";
    	}

    	showMsg(msg);
		
	}

	private static void freeTable() {

		int length = fetchInt("How many shall be freed?");
    	int[] array = new int[length];

    	for (int i = 0; i < length; i++) {
    		array[i] = fetchInt("Ey, table number (" + (length - i - 1) + " left)");
    	}

    	if (instance.freeTables(array)) {
    		showMsg("It is done.");
    	} else {
    		showErrorMsg("Couldn't do as requested");
    	}
		
	}

	private static void rename() {

		String newName = fetchString("Dat new naeme");
		instance.setName(newName);

	}
	
	public static void main(String[] args) {
		

		String name = fetchString("Name");
		int estYear = fetchInt("Established in the year");
		int numTables = fetchInt("Num tables");

		instance = new Restaurant(name, estYear, numTables);

		int opt;

		do {
			opt = showPrompt();

    		switch (opt) {

		        case RESERVE:  // reservere et antall bord på et bestemt navn
		            //....les inn navn og antall bord, og kall metode...
		        	reserveTable();
		        	break;
		        case FIND:  // finne alle bordene som er reservert på et bestemt navn
		            //...les inn navn, og kall metode...
		        	findTable();
		        	break;
		        case FREE:  // frigi en rekke bord, bordnummer er gitt
		            //....les inn aktuelle bordnummer og kall metode...
		        	freeTable();
		        	break;
		        case RENAME:
		        	rename();
		        	break;
		        case EXIT:
		            //....uh what m8? fool.
		        	break;
		        default: break;

		    }

		} while (opt != EXIT);

	}

}





/*
spør brukeren om restaurantnavn, etableringsår, og antall bord.
opprett et Restaurant-objekt
do {     valg = vis liste over mulige valg
    switch (valg) {
        case reserverBord:  // reservere et antall bord på et bestemt navn
            ....les inn navn og antall bord, og kall metode...
        case finnBordnr:  // finne alle bordene som er reservert på et bestemt navn
            ...les inn navn, og kall metode...
        case frigiBord:  // frigi en rekke bord, bordnummer er gitt
            ....les inn aktuelle bordnummer og kall metode...
        case avslutt:
            ....
} while (valg != avslutt)

AND THEN 
Sørg også for at metodene som finner alderen og endrer restaurantnavnet blir prøvd ut.
*/