import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    private static ArrayList<Person> getAllPersons() throws SQLException{

        ArrayList<Person> people = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        System.out.println("hey pass me word");
        String password = sc.nextLine();

        String url = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/toberge?user=toberge&password=" + password; // "databasenavn" kalte boka det, okei...


        try (Connection conMan = DriverManager.getConnection(url);) { // TRY WITH RESOURCES FOR RELIABLE CLOSE OMG

            Statement statement = conMan.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM person ORDER BY etternavn");

            while (res.next()) {

                people.add(new Person(res.getInt("persnr"), res.getString("fornavn"), res.getString("etternavn")));

            }

        }

        return people;

    }

    public static void main(String[] args) throws SQLException {

        //String databasedriver = "com.mysql.jdbc.Driver";
        //Class.forName(databasedriver);
        // shit seems to not be necessary

        Scanner sc = new Scanner(System.in);
        System.out.println("hey pass me word");
        String password = sc.nextLine();

        String url = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/toberge?user=toberge&password=" + password; // "databasenavn" kalte boka det, okei...

        //Connection conMan = null;

        try (Connection conMan = DriverManager.getConnection(url);) { // TRY WITH RESOURCES FOR RELIABLE CLOSE OMG
            //conMan = DriverManager.getConnection(url);

            Statement statement = conMan.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM person ORDER BY etternavn");

            while (res.next()) {

                System.out.println("persnr\tfornavn\tetternavn");
                System.out.println(res.getInt("persnr") + "\t\t" + res.getString("fornavn") + "\t\t" + res.getString("etternavn"));

            }

            // tipper jeg?
            //res.close(); nope
            sc.close();
            //conMan.close();

        } /*catch (SQLException e) { // MUST DO SHIT IF SHIT NOT CLOSED UP BEFORE EXCEPTION
            conMan.close();
        } // evt i finally tydeligvis*/

        ArrayList<Person> people = getAllPersons();

        for (Person datGuy : people) {

            System.out.println(datGuy);

        }


    }

}
