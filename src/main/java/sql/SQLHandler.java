package sql;

import java.io.File;
import java.sql.*;

public class SQLHandler {
    public static void init() throws ClassNotFoundException, SQLException {
        checks();
        Connection connection = connect();
        connection.close();
    }
    public static void checks() throws ClassNotFoundException, SQLException {
        File db = new File("database.db");
        if (!db.exists() || db.isDirectory()) {
            createDB();
            Connection connection = connect();
            initDB(connection);
            connection.close();
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
        return DriverManager.getConnection("jdbc:sqlite:database.db");
    }
    public static void createUsersStructure(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS users (
                id integer PRIMARY KEY,
                username text NOT NULL,
                password text NOT NULL,
                discordId text)""");
    }
    public static void initDB(Connection connection) throws SQLException {
        createUsersStructure(connection);
    }
}
