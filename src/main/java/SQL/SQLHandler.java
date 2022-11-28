package SQL;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHandler {
    public static void init() throws ClassNotFoundException, SQLException {
        checks();
    }
    public static void checks() throws ClassNotFoundException, SQLException {
        File db = new File("database.db");
        if (!db.exists() || db.isDirectory()) {
            createDB();
        }
    }
    public static void createDB() throws ClassNotFoundException, SQLException {
        String url = "jdbc:sqlite:database.db";
        System.out.println(url);
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db")) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("The database has been created.");
            }
        }
    }
}
