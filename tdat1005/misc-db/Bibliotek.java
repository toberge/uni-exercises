import java.io.Console;
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import qqlib.cli.ScanWrapper;

public class Bibliotek {

    // burde finne en god måte å pakke inn dette på - og ta hensyn til at det kan kastes exceptions OG at ResultSet må håndteres FØR Connection lukkes

    private static ArrayList<String> runQuery(String query) {


        String password;

        Console co = System.console();
        Scanner sc = new Scanner(System.in);

        System.out.println("Your password please");

        if (co != null) {
            password = String.valueOf(co.readPassword());
        } else {
            password = sc.nextLine();
            sc.close(); // only if sc created here
        }

        String url = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/toberge?user=toberge&password=" + password;

        try (Connection con = DriverManager.getConnection(url)) {

            Statement statement = con.createStatement(); // MUST ALSO BE CLOSED
            ResultSet res = statement.executeQuery(query);

            return fetchTitles(res);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    private static ArrayList<String> fetchTitles(ResultSet res) throws SQLException {

        //ResultSet res = runQuery("SELECT tittel FROM bok WHERE 1");
        ArrayList<String> titles = new ArrayList<>();

        while (res.next()) {

            titles.add(res.getString("tittel"));

        }

        return titles;

    }

    public static void main(String[] args) {

        /*
        try {
            ArrayList<String> titles = fetchTitles();

            for (String title : titles) {
                System.out.println(title);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        */

        ArrayList<String> titles = runQuery("SELECT tittel FROM bok WHERE 1");

        for (String title : titles) {
            System.out.println(title);
        }

        ScanWrapper sc = new ScanWrapper();
    }
}
