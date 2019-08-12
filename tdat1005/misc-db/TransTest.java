import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransTest {


    public boolean setTittel(Connection con, String tittel, int id) {

        try (PreparedStatement stmt = con.prepareStatement("UPDATE bok SET tittel = ? WHERE bok_id = ?")) {

            stmt.setString(1, tittel);
            stmt.setInt(2, id);
            System.out.println(stmt.executeUpdate());
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback(); // move down to the ConTest
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }

        return false;

    }

    public static void main(String[] args) {

        try (ConTest cont = new ConTest()) {

            TransTest tt = new TransTest();

            tt.setTittel(cont.getConnection(), "Helvete faller ned", 24); // Tatt av vinden/kvinnen (husker ikke, ops)

        }

    }

}
