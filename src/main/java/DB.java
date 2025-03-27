import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
  public static void main(String[] args) {

    String url = "jdbc:postgresql://localhost:5432/postgres";
    String username = "postgres";
    String password = "postgres";

    Connection connection = null;

    try {
      Class.forName("org.postgresql.Driver");

      connection = DriverManager.getConnection(url, username, password);

      Statement statement = connection.createStatement();

     ResultSet resultSet = statement.executeQuery("SELECT * FROM homework.workspace");

      while (resultSet.next()) {
        System.out.println(resultSet.getString("details"));
      }

    } catch (ClassNotFoundException e) {
      System.out.println("JDBC Driver not found");
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("Connection failed");
      e.printStackTrace();
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}