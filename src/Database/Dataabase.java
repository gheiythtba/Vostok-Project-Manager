package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Dataabase {

    private Connection c;
    private static String URL = "jdbc:mysql://localhost/taskmanager?serverTimezone=Africa/Lagos";
    private static String user = "root";
    private static String password = "271746451";

    public Connection connectDb() throws ClassNotFoundException, SQLException {
        if (c == null || c.isClosed()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL, user, password);
            System.out.println("well the connection worked");
        }
        return c;
    }
  

    public List<String> fetchMaterialnamesFromMaterialTable() {
        List<String> materials = new ArrayList<>();

        try {
            connectDb();
            String query = "SELECT material_name FROM material";
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String material_name = resultSet.getString("material_name");
                materials.add(material_name);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return materials;
    }

        public List<String> fetchUsernamesFromAdminTable() {
        List<String> usernames = new ArrayList<>();

        try {
            connectDb();
            String query = "SELECT username FROM admin";
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                usernames.add(username);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return usernames;
    }


    
}