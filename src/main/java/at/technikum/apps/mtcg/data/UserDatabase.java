package at.technikum.apps.mtcg.data;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserDatabase {

    private static final String URL = "jdbc:postgresql://localhost:5432/userdb";
    private static final String USERNAME = "if22b181";
    private static final String PASSWORD = "1234";


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}