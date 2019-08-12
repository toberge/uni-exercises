import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

public class Fakutrering {

    private Connection getConnection() {
        try {
            String url = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/toberge?user=toberge&password=PASSWORD";
            return DriverManager.getConnection(url);
        } catch (SQLException sqle) {
            System.err.println("Failed to establish connection");
            sqle.printStackTrace();
            //System.exit(-1); // exit
            return null;
        }
    }

    private boolean writeArrayToFile(String filename, ArrayList<String> array) {

        try (FileWriter fw = new FileWriter(filename);
             PrintWriter pw = new PrintWriter(fw)) {

            for (String line : array) {
                if (line != null && !line.trim().equals("")) {
                    pw.println(line);
                }
            }
            return true;

        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

    }

    public boolean fakturer(int monthNumber, String filename) {

        String sqlUpdateDate = "UPDATE prosjektarbeid pa, prosjektkostnader pk\n" +
                               "SET pa.faktura_sendt = CURDATE(), pk.faktura_sendt = CURDATE()\n" +
                               "WHERE (pa.prosj_id = ? OR pk.prosj_id = ?)\n" +
                               "  AND (MONTH(pa.dato) = ? OR MONTH(pk.dato) = ?)\n" +
                               "  AND pa.faktura_sendt IS NULL AND pk.faktura_sendt IS NULL;";


        String viewEmployeeCosts =  "SELECT MONTH(dato) AS mnd, p.prosj_id, 'timer' AS tekst, kunde, SUM(ant_timer * timefaktor * timelønn) AS beløp -- alle kolonnene som trengs + mnd for å identifisere\n" +
                                    "FROM prosjekt p,\n" +
                                    "     prosjektarbeid pa,\n" +
                                    "     ansatt a\n" +
                                    "WHERE a.ans_id = pa.ans_id\n" +
                                    "AND p.prosj_id = pa.prosj_id\n" +
                                    "GROUP BY mnd, prosj_id\n" +
                                    "ORDER BY mnd, prosj_id ASC, kunde ASC";

        String viewMiscCosts = "SELECT MONTH(dato) AS mnd, p.prosj_id, tekst, kunde, beløp\n" +
                               "FROM prosjekt p,\n" +
                               "prosjektkostnader pk\n" +
                               "WHERE p.prosj_id = pk.prosj_id\n" +
                               "ORDER BY mnd, p.prosj_id ASC, kunde ASC, beløp DESC";

        String sqlFetchLinesView =  "SELECT CONCAT_WS(';', prosj_id, tekst, kunde, beløp) AS line, prosj_id\n" +
                                "FROM (\n" +
                                "       SELECT *\n" +
                                "       FROM timeoversikt\n" +
                                "          UNION\n" +
                                "       SELECT *\n" +
                                "       FROM kostnadoversikt\n" +
                                "          ORDER BY prosj_id ASC, kunde ASC, beløp DESC -- gjelder for unionen\n" +
                                "     ) oversikt\n" +
                                "WHERE mnd = ?;";

        // replaced views with the statements they were created with

        String sqlFetchLines =  "SELECT CONCAT_WS(';', prosj_id, tekst, kunde, beløp) AS line, prosj_id\n" +
                                "FROM (\n" +
                                "       SELECT *\n" +
                                "       FROM (" + viewEmployeeCosts + ") AS emp\n" +
                                "          UNION\n" +
                                "       SELECT *\n" +
                                "       FROM (" + viewMiscCosts + ") AS misc\n" +
                                "          ORDER BY prosj_id ASC, kunde ASC -- gjelder for unionen\n" +
                                "     ) oversikt\n" +
                                "WHERE mnd = ?;";

        // jadda, jeg bare knøvla teksten hensynsløst inn og lot IntelliJ gjøre formateringa

        try (Connection connection = getConnection()) {

            if (connection == null) return false;

            try (PreparedStatement fetchLines = connection.prepareStatement(sqlFetchLines);
                 PreparedStatement updateDate = connection.prepareStatement(sqlUpdateDate)) {

                connection.setAutoCommit(false);


                System.out.println(connection.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ ?
                                    "Sufficient transaction level" : "Hm... Why did it change?");
                // dette burde være tilstrekkelig
                // egentlig kunne SERIALIZABLE vurderes


                // create arrays, fill them with results from statement
                fetchLines.setInt(1, monthNumber);
                ResultSet lineSet = fetchLines.executeQuery();

                ArrayList<String> linesToBeWritten = new ArrayList<>();
                ArrayList<Integer> projectsToUpdate = new ArrayList<>();

                while (lineSet.next()) {

                    linesToBeWritten.add(lineSet.getString("line"));
                    projectsToUpdate.add(lineSet.getInt("prosj_id"));

                }


                if (writeArrayToFile(filename, linesToBeWritten)) { // try writing

                    //boolean ok = true;

                    for (int projID : projectsToUpdate) { // if written, start updating date values

                        updateDate.setInt(1, projID);
                        updateDate.setInt(2, projID);
                        updateDate.setInt(3, monthNumber);
                        updateDate.setInt(4, monthNumber);
                        updateDate.executeUpdate();

                        //if (updateDate.executeUpdate() < 0) ok = false; // execute and check

                    }

                    /*
                    if (ok) {
                        connection.commit(); // commit changes
                        return true; // we did it
                    } else {
                        System.err.println("it wrongk");
                        connection.rollback(); // might've failed at updating TODO wtf
                        return false; // gone wronk
                    }
                    */

                    connection.commit();
                    return true;

                } else {

                    connection.rollback(); // eeeeeeh not necessary tho, we just read some stuff
                    return false;
                }

            } catch (SQLException sqle) {
                rollTheHeckBack(connection);
                sqle.printStackTrace();
            } finally {
                resetAutoCommit(connection);
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return false;

    }

    private void rollTheHeckBack(Connection connection) {

        if (connection != null) {

            try {

                connection.rollback();

            } catch (SQLException sqle) {
                System.err.println("Failed to roll back");
                sqle.printStackTrace();
            }

        }

    }

    private void resetAutoCommit(Connection connection) {

        if (connection != null) {

            try {

                connection.setAutoCommit(true);

            } catch (SQLException sqle) {
                System.err.println("Failes to re-enable state of auto-commit");
                sqle.printStackTrace();
            }

        }

    }

    public static void main(String[] args) {

        Fakutrering instance = new Fakutrering();

        boolean ok = instance.fakturer(10, "2008-10 faktura.csv"); // CSV: Comma-Separated Values - we use semikolon so yeeeah

        if (ok) System.out.println("we good"); else System.out.println("oh no");

    }

}
