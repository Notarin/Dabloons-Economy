package sql;

import java.io.File;
import java.sql.*;

public class SQLHandler {
    // Initialize the database by connecting to it and creating the necessary tables if they don't exist
    public static void init() throws ClassNotFoundException, SQLException {
        // Check if the database file exists and create it if it doesn't
        checks();
        // Connect to the database
        Connection connection = connect();
        // Close the connection
        connection.close();
    }

    // Check if the database file exists and create it if it doesn't
    public static void checks() throws ClassNotFoundException, SQLException {
        // Create a File object for the database file
        File db = new File("database.db");
        // If the database file doesn't exist or is a directory, create it
        if (!db.exists() || db.isDirectory()) {
            createDB();
            // Create the database
            Connection connection = connect();
            // Initialize the database by creating the necessary tables
            initDB(connection);
            // Close the connection
            connection.close();
        }
    }

    // Create the database file
    public static void createDB() throws ClassNotFoundException, SQLException {
        // Load the JDBC driver for SQLite
        Class.forName("org.sqlite.JDBC");
        // Try to connect to the database file. If it doesn't exist, it will be created (which is exactly what will happen in this case)
        try (Connection ignored = DriverManager.getConnection("jdbc:sqlite:database.db")) {
            System.out.println("The database has been created.");
        }
    }

    // Connect to the database
    public static Connection connect() throws ClassNotFoundException, SQLException {
        // Load the JDBC driver for SQLite
        Class.forName("org.sqlite.JDBC");
        // Return a connection to the database
        return DriverManager.getConnection("jdbc:sqlite:database.db");
    }

    // Create the 'users' table in the database
    public static void createUsersStructure(Connection connection) throws SQLException {
        // Create a statement object
        Statement statement = connection.createStatement();
        // Use the statement to execute an SQL query that creates the 'users' table
        statement.execute("""
                CREATE TABLE IF NOT EXISTS users (
                id integer PRIMARY KEY,
                username text NOT NULL,
                password text NOT NULL,
                discordId text)""");
    }

    // Initialize the database by creating the necessary tables
    public static void initDB(Connection connection) throws SQLException {
        // Create the 'users' table
        createUsersStructure(connection);
    }
}
