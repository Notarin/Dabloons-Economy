package com.dabloons.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Checks {
    // Check if the database file exists and create it if it doesn't
    public static void checks() throws ClassNotFoundException, SQLException {
        // Create a File object for the database file
        File db = new File("database.db");
        // If the database file doesn't exist or is a directory, create it
        if (!db.exists() || db.isDirectory()) {
            SQLHandler.createDB();
            // Create the database
            Connection connection = SQLHandler.connect();
            // Initialize the database by creating the necessary tables
            SQLHandler.initDB(connection);
            // Close the connection
            connection.close();
        } else {
            Connection connection = SQLHandler.connect();
            checkTables(connection);
            checkColumns(connection);
            connection.close();
        }
    }

    public static void checkTables(Connection connection) {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet res;
            {
                res = meta.getTables(null, null, "users", null);
                if (!res.next()) {
                    // The users table does not exist, so create it
                    Query.createUsersTable(connection);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkColumns(Connection connection) {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet res = meta.getColumns(null, null, "users", null);
            List<String> columns = new ArrayList<>();
            while (res.next()) columns.add(res.getString("COLUMN_NAME"));
            String table;
            {
                table = "users";
                if (!columns.contains("discordId")) Query.addColumn(table, "discordId", "text", connection);
                if (!columns.contains("username")) Query.addColumn(table, "username", "text", connection);
                if (!columns.contains("avatar")) Query.addColumn(table, "avatar", "text", connection);
                if (!columns.contains("discriminator")) Query.addColumn(table, "discriminator", "integer", connection);
                if (!columns.contains("banner")) Query.addColumn(table, "banner", "text", connection);
                if (!columns.contains("locale")) Query.addColumn(table, "locale", "text", connection);
                if (!columns.contains("email")) Query.addColumn(table, "email", "text", connection);
                if (!columns.contains("verified")) Query.addColumn(table, "verified", "boolean", connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}