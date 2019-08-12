import java.io.*;
import java.util.Scanner;

public class TransactionHandler {
	private double balance = -1; // floating point for now; will make currency class
	private double newBalance = -1; // potential balance
	private final String BALANCE_FILE;

	//private final File whereBalanceLies; and possibly transaction file setting?

	// CONSTRUCTORS
	public TransactionHandler(String file) {
		if(!(readBalance(file))) {
			throw new IllegalArgumentException("Could not set balance. Construction aborted.");
		}
		BALANCE_FILE = file;
	}

	public TransactionHandler() {
		BALANCE_FILE = "saldo.txt";
		if(!(readBalance())) {
			throw new IllegalArgumentException("Could not set balance. Construction aborted.");
		}
	}

	//TO THE STRING
	public String toString() {
		return "Current balance: " + balance /*+ "\nNew balance: " + newBalance*/;
	}

	// GET METHOD
	public double getBalance() {
		return balance;
	}

	// PRIVATE SET METHOD
	private boolean setBalance(double balanceToSet) {
		if (balanceToSet < 0) {
			return false;
		} else {
			balance = balanceToSet;
			return true;
		}
	}

	// PUBLIC RESET METHOD
	public void resetBalance() {
		balance = 0;
		write();
	}

	// READ BALANCE FILE
	public boolean readBalance() {
		return readBalance(BALANCE_FILE);
	}

	public boolean readBalance(String file) {
		if (file == null || file.trim().equals("")) {
			return false;
		}

		boolean success = false;

		try (FileReader linkRead = new FileReader(file);
			 BufferedReader reader = new BufferedReader(linkRead)) {
			
			Scanner sc = new Scanner(reader);
			double balanceToSet;

			if (sc.hasNext()) {
				try {
					balanceToSet = sc.nextDouble();
					if (setBalance(balanceToSet)) {
						success = true;
					} else {
						success = false;
					}
				} catch (/*InputMismatch*/Exception e) {
					System.out.println("File may not contain a number (at the right place).");
				}
			} else {
				success = false;
			}

		}  catch (FileNotFoundException e) {
			System.out.println("File \"" + file + "\" not found");
		} catch (IOException e) {
			System.out.println("Something happened during transfer. Check your files. Error: " + e);
		}
		return success;
	}

	// PARSE AND INTERPRET TRANSACTION
	public boolean readTransaction(String file) {
		if (file == null || file.trim().equals("")) {
			return false;
		}

		boolean success = false;

		try (FileReader linkRead = new FileReader(file);
			 BufferedReader reader = new BufferedReader(linkRead)) {
			
			Scanner sc = new Scanner(reader);

			if (!sc.hasNext()) {
				return false;
			}

			char mode;
			double value;
			String valueDummy;
			newBalance = balance;

			while (sc.hasNext()) {
				try {
					mode = sc.next().charAt(0);
					//System.out.println(mode);
					valueDummy = sc.nextLine(); // to avoid problems with *that*
					value = Double.parseDouble(valueDummy);
					//System.out.println("" + value);
					if (interpretTransactionLine(mode, value)) {
						success = true;
					} else {
						success = false;
					}
					
				} catch (/*InputMismatch*/Exception e) {
					System.out.println("Transaction file might not be properly formatted.");
					success = false; // necessary?
				}
			}

		} catch (EOFException e) {
			System.out.println("Mysterious way of ending the file, miss.");
			success = false;
		} catch (FileNotFoundException e) {
			System.out.println("File \"" + file + "\" not found");
		} catch (IOException e) {
			System.out.println("Something happened during transfer. Check your files. Error: " + e);
			success = false;
		}

		if (!success) {
			newBalance = -1; // so nothing happens with it
		}
		return success;
	}

	private boolean interpretTransactionLine(char mode, double value) {
		if (mode == 'I') {
			newBalance += value;
			//System.out.println("adding");
		} else if (mode == 'U') {
			newBalance -= value;
			//System.out.println("subtracting");
		} else {
			return false;
		}
		return true;
	}

	// wrappers
	public boolean processTransaction(String input, String output, String source) { // unused but ok
		return readBalance(source) && readTransaction(input) && write(output);
	}

	public boolean processTransaction(String input, String output) {
		return readBalance(output) && readTransaction(input) && write(output);
	}

	public boolean processTransaction(String input) {
		return readBalance() && readTransaction(input) && write();
	}

	// WRITE BALANCE TO FILE
	public boolean write() {
		return write(BALANCE_FILE);
	}

	public boolean write(String file) { // must only write if valid
		if (newBalance < 0 || balance < 0) {
			return false;
		}

		try (FileWriter linkWrite = new FileWriter(file); // set second argument to true to avoid overwriting, false lets you overwrite
			 PrintWriter writer = new PrintWriter(new BufferedWriter(linkWrite))) {

			java.util.Formatter f = new java.util.Formatter();
			f.format("%.2f", newBalance);
			writer.print(f);
			balance = Double.parseDouble(f.toString());
			//writer.printf("%.2f", newBalance);
			//balance = newBalance;
			newBalance = -1; // to make sure the user doesn't do the thing twice
		} catch (Exception e) {
			System.out.println("Something happened, aborting.");
			return false;
		}

		// set boolean written to true?
		// set balance = newBalance if valid - or don't set newBalance if invalid (bad idea, seriously)
		return true;
	}

	public static void main(String[] args) {
		String transactionFile = "transaksjon.txt";
		TransactionHandler bankier;
		boolean cont = true;

		if (args.length > 2) {
			System.out.println("Usage: TransactionHandler [transaction file] [balance file]");
			System.out.println("The executable file or command might differ.");
			bankier = new TransactionHandler(); // because the java compiler demands it
			System.exit(1);
		} else if (args.length == 2) {
			try {
				bankier = new TransactionHandler(args[1]);
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid balance file, using default.");
				bankier = new TransactionHandler();
			}

			if (args[0].equals("clear")) { // for my own convenience
				System.out.println("Setting balance to 0");
				bankier.resetBalance();
				cont = false;
			}
			transactionFile = args[0];
		} else if (args.length == 1) {
			bankier = new TransactionHandler();
			if (args[0].equals("clear")) { // for my own convenience
				System.out.println("Setting balance to 0");
				bankier.resetBalance();
				cont = false;
			}
			transactionFile = args[0];	
		} else {
			bankier = new TransactionHandler();
		}


		if (cont) {
			System.out.println("We will now process the transaction.\n" + bankier +
							   "\nThe file \"" + transactionFile + "\" looks like this:");

			try (FileReader linkRead = new FileReader(transactionFile);
				 BufferedReader reader = new BufferedReader(linkRead)) {
				

				String readLines = "\n";
				String line = reader.readLine();
				
				while (line != null) {
					readLines += (line + "\n");
					line = reader.readLine();
				}
				System.out.println(readLines);

			} catch (FileNotFoundException e) {
				//System.out.println("File not found."); // redundant since it already gets checked in method
			} catch (IOException ioe) {
				System.out.println("IO-exception: " + ioe);
			} catch (Exception e) {
				System.out.println("\noh, whoops, file not found perhaps\n");
			}

			if (bankier.processTransaction(transactionFile)) {
				System.out.println("Transaction proceeded smoothly.");
				System.out.println(bankier);
			} else {
				System.out.println("Transaction invalid, balance not written.");
			}
		}


		/* obsolete test block
		try {
			FileReader linkRead = new FileReader(transactionFile);

			BufferedReader reader = new BufferedReader(linkRead);

			String readLines = "trolololol";
			String line = reader.readLine();
			
			while (line != null) {
				readLines += ("\n" + line);
				line = reader.readLine();
			}
			reader.close();
			System.out.println(readLines);

		} catch (Exception e) {
			System.out.println("fuk it");
		}

		/*
		System.out.println();

		TransactionHandler omg = new TransactionHandler();
		omg.newBalance = 234;
		omg.write("salute.txt");
		omg.newBalance = -234;
		omg.write("salute.txt");
		omg.newBalance = 22;
		omg.readBalance("salute.txt");
		omg.readBalance("heffalomp");
		*/
	}
}

// VARIOUS MESSAGES

/*
IF YOU DO
try (FileReader helvete = new FileReader("helvete.pptx")) {
	stuff.happens();
} catch FileNotFoundException e ETC {
THEY GET CLOSED AUTOMATICALLY
*/

/*
Store, tungvine metoder --> kanskje slenge ut i hjelpemetode
*/