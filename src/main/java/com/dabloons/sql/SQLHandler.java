package com.dabloons.sql;

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

    // Initialize the database by creating the necessary tables
    public static void initDB(Connection connection) throws SQLException {
        // Create the 'users' table
        Query.createUsersTable(connection);
    }
}
