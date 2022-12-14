package sql;

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
            checkColumns();
        }
    }

    public static void checkColumns() throws SQLException, ClassNotFoundException {
        Connection connection = SQLHandler.connect();
        try {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet res = meta.getColumns(null, null, "users", null);
            List<String> columns = new ArrayList<>();
            while (res.next()) columns.add(res.getString("COLUMN_NAME"));
            if (!columns.contains("discordId")) SQLHandler.addColumn("users", "discordId", "text", connection);
            if (!columns.contains("username")) SQLHandler.addColumn("users", "username", "text", connection);
            if (!columns.contains("avatar")) SQLHandler.addColumn("users", "avatar", "text", connection);
            if (!columns.contains("discriminator")) SQLHandler.addColumn("users", "discriminator", "integer", connection);
            if (!columns.contains("banner")) SQLHandler.addColumn("users", "banner", "text", connection);
            if (!columns.contains("locale")) SQLHandler.addColumn("users", "locale", "text", connection);
            if (!columns.contains("email")) SQLHandler.addColumn("users", "email", "text", connection);
            if (!columns.contains("verified")) SQLHandler.addColumn("users", "verified", "boolean", connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
