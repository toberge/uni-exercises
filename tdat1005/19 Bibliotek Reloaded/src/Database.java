import java.sql.*;

public class Database implements AutoCloseable {

    Connection connection;

    public Database() {

        try {
            String url = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/toberge?user=toberge&password=PASSWORD";
            connection = DriverManager.getConnection(url);
        } catch (SQLException sqle) {
            System.err.println("Failed to establish connection");
            sqle.printStackTrace();
            System.exit(-1); // exit
        }

    }

    @Override
    public void close() {

        if (connection != null) {

            try {

                connection.close();

            } catch (SQLException sqle) {

                sqle.printStackTrace();

            }

        }

    }

    public void rollback() {

        if (connection != null) {

            try {

                if (!connection.getAutoCommit()) { // we were in an unsuccessfull transaction...
                    // --> this singles out one way for rollback to throw its exception (if auto-commit ON)

                    try {

                        connection.rollback();

                    } catch (SQLException sqle) {

                        System.err.println("Failed to perform a rollback.");
                        sqle.printStackTrace();

                    }

                } else {

                    System.err.println("AutoCommit was ON when we tried rolling back.");

                }

            } catch (SQLException sqle) {

                System.err.println("Failed to get state of AutoCommit during rollback.");
                sqle.printStackTrace();

            }

        }

    }

    // just a wrapper around setAutoCommit(true), can be put in a finally block w/o the exception-catching found here
    private void enableAutoCommit() {

        if (connection != null) {

            try {

                connection.setAutoCommit(true);

            } catch (SQLException sqle) {

                System.err.println("Failed to enable AutoCommit.");
                sqle.printStackTrace();

            }

        }

    }

    /**
     * Performing these SQL sentences:
     *
     * insert into boktittel(isbn, forfatter, tittel) values(<isbn>, <forfatter>, <tittel>)
     * insert into eksemplar(isbn, eks_nr) values (<isbn>, 1);
     *
     * Both need to happen as one atomic transaction, rolled back if failing.
     * - In particular, the second statement should NOT run if the first fails.
     * That might indicate that the book already exists.
     *
     * @param bok   to be registered
     * @return      success/failure
     */
    public boolean regNyBok(Bok bok) {

        if (bok == null) return false; // no need to bother.

        String sql1 = "insert into boktittel(isbn, forfatter, tittel) values(?, ?, ?)";
        String sql2 = "insert into eksemplar(isbn, eks_nr) values (?, 1);";

        try (PreparedStatement createBook = connection.prepareStatement(sql1);
             PreparedStatement createInstance = connection.prepareStatement(sql2)) { // using the try-with-resources goodness

            // setting autocommit to false
            connection.setAutoCommit(false);

            // preparing the book
            createBook.setString(1, bok.getIsbn());
            createBook.setString(2, bok.getForfatter());
            createBook.setString(3, bok.getTittel());
            // inserting it, automatically closing if we fail.
            createBook.executeUpdate();

            //preparing the 1st copy
            createInstance.setString(1, bok.getIsbn());
            // inserting
            createInstance.executeUpdate();

            connection.commit(); // WHOOOOOOOPPS WHOOOOOOOOOO but it went fine without it... It happens automatically?

            //connection.setAutoCommit(true); // ATTENTION: this happens even if we fail, right? In the rollback() method in the lib itself, I mean.

            return true; // success

        } catch (SQLException sqle) {

            rollback();
            return false; // failure

        } finally {

            //connection.setAutoCommit(true);
            enableAutoCommit(); // reset AutoCommit

        }

    }

    /**
     * This method will first execute one statement for fetching the int value the second one then will use.
     * Given that the first statement gives relevant info that MUST stay true for the second to work with certainty,
     * this should definitely be performed as an atomic transaction.
     *
     * Cannot use a simple AUTO_INCREMENT in the table since that would <i>not</i> prevent holes in the sequence.
     *
     * @param isbn  the ISBN of the book in question
     * @return      eks_nr of the newly registered one, 0 if no such book was found, -1 if some other error occured.
     */
    public int regNyttEksemplar(String isbn) {

        if (isbn == null || isbn.trim().equals("")) return -1;

        String sqlFindMax = "SELECT MAX(eks_nr) AS max FROM eksemplar WHERE isbn = ?"; // fetches max, we gonna put in max+1
        String sqlInsertIt = "insert into eksemplar(isbn, eks_nr) values (?, ?);"; // see above

        try (PreparedStatement findMax = connection.prepareStatement(sqlFindMax);
             PreparedStatement insertIt = connection.prepareStatement(sqlInsertIt)) {

            connection.setAutoCommit(false);

            // prepare & execute
            findMax.setString(1, isbn);
            ResultSet res = findMax.executeQuery(); // oh shit, there's difference between plain execute and the query and update ones

            if (res.next()) {

                int theNumber = res.getInt("max") + 1;

                insertIt.setInt(2, theNumber); // one more than the max of what is there
                insertIt.setString(1, isbn);
                insertIt.executeUpdate();

                connection.commit(); // commit here

                //connection.setAutoCommit(true); // turn off autocommit

                return theNumber;

            } else {

                System.err.println("ResultSet is empty somehow");
                // found no such ISBN probably, don't go insiiiide

                //connection.setAutoCommit(true); // TODO MOVE THIS ELSEWHERE MAYBE??? aye it done
                return -1; // indeed

            }

        } catch (SQLException sqle) {

            System.err.println("This is not a valid ISBN (or something else happened) - we roll back and away. Error details:");
            sqle.printStackTrace();

            rollback();

            return 0; // other error occurred

        } finally {

            enableAutoCommit();

        }

    }

    public boolean laanUtEksemplar(String isbn, String navn, int eksNr) {

        if (isbn == null || navn == null || eksNr <= 0 || isbn.trim().equals("") || navn.trim().equals("")) return false; // invalid arg(s)


        String sqlUpdateStmt = "update eksemplar set laant_av = ? where isbn = ? and eks_nr = ?";

        try (PreparedStatement updateStmt = connection.prepareStatement(sqlUpdateStmt)) {

            connection.setAutoCommit(false); //hadde ikke trengt

            updateStmt.setString(1, navn);
            updateStmt.setString(2, isbn);
            updateStmt.setInt(3, eksNr);
            int res = updateStmt.executeUpdate();

            if (res > 0) {

                connection.commit();
                return true;

            }

            // else
            rollback(); // uhhhh doesn't seem necessary

        } catch (SQLException sqle) {

            System.out.println("laanUtEksemplar() - something went wronk");
            sqle.printStackTrace();

            rollback();

        } finally {

            enableAutoCommit();

        }

        return false;

    }

    public static void main(String[] args) {

        try (Database db = new Database()) { // the wonders of AutoCloseable

            String datISBN = "0-666-05656-7";


            System.out.println("Start of book registration test");

            boolean regNewOne = db.regNyBok(new Bok(datISBN, "Sanden hviler", "Grønn Storkilius"));
            boolean regOnSameISBN = db.regNyBok(new Bok(datISBN, "Sanden sover", "Grønn Storkilius"));

            System.out.println((regNewOne && !regOnSameISBN)? "Registration test successful"
                                                            : "Something's off here (maybe this test has been run before?)");


            System.out.println("Start of copy registration test");

            int regNewCopy = db.regNyttEksemplar(datISBN);

            System.out.println(regNewCopy > 0 ? "A new copy was registered" : "Didn't get the proper copy registered");

            int regInvalidISBN = db.regNyttEksemplar("0-111-11011-ZZZZ"); // yeah gotta be real bad

            System.out.println(regInvalidISBN > 0 ? "What the fuck how did this work out" :
                               regInvalidISBN == 0 ? "All's well with nonexistent ISBN" : "Nope. This is way off.");


            System.out.println("Start of loan registration test");

            boolean regProperLoan = db.laanUtEksemplar(datISBN, "Kalle Navnestad", 1);
            boolean regStupidLoan = db.laanUtEksemplar(datISBN, "Feiler som bare søren", 999);

            System.out.println((regProperLoan && ! regStupidLoan)? "One loan registered, the other failed, just as planned."
                                                                 : "Loan test failed");


        }

    }

}
