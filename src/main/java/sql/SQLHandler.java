package sql;

import java.sql.*;

public class SQLHandler {
    // Initialize the database by connecting to it and creating the necessary tables if they don't exist
    public static void init() throws ClassNotFoundException, SQLException {
        // Check if the database file exists and create it if it doesn't
        Checks.checks();
        // Connect to the database
        Connection connection = connect();
        // Close the connection
        connection.close();
    }

    public static void addColumn(String name, String type, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("ALTER TABLE users ADD COLUMN " + name + " " + type + " NOT NULL");
        } catch (SQLException e) {
            e.printStackTrace();
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
                discordId text PRIMARY KEY,
                username text NOT NULL,
                avatar text NOT NULL,
                discriminator integer NOT NULL,
                banner text NOT NULL,
                locale text NOT NULL,
                email text NOT NULL,
                verified boolean NOT NULL
                )""");
    }

    // Initialize the database by creating the necessary tables
    public static void initDB(Connection connection) throws SQLException {
        // Create the 'users' table
        createUsersStructure(connection);
    }
}
