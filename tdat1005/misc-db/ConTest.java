import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ConTest implements AutoCloseable {

    private Connection connection = null;

    public ConTest() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Your password please");
        String pw = sc.nextLine();
        sc.close();
        String url = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/toberge?user=toberge&password=" + pw;

        boolean ok = false;

        for (int i = 0; i < 4 && !ok; i++) { // pfft, try 4 times or whatever
            try {
                connection = DriverManager.getConnection(url);
                ok = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




}
