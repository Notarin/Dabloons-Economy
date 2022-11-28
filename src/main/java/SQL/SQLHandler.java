package SQL;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHandler {
    public static void init() throws ClassNotFoundException, SQLException {
        checks();
        Connection connection = connect();
    }
    public static void checks() throws ClassNotFoundException, SQLException {
        File db = new File("database.db");
        if (!db.exists() || db.isDirectory()) {
            createDB();
        }
    }
    public static void createDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        try (Connection ignored = DriverManager.getConnection("jdbc:sqlite:database.db")) {
            System.out.println("The database has been created.");
        }
    }
    public static Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
        return conn;
    }
}
