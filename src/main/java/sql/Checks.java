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
            if (!columns.contains("discordId")) SQLHandler.addColumn("discordId", "text", connection);
            if (!columns.contains("username")) SQLHandler.addColumn("username", "text", connection);
            if (!columns.contains("avatar")) SQLHandler.addColumn("avatar", "text", connection);
            if (!columns.contains("discriminator")) SQLHandler.addColumn("discriminator", "integer", connection);
            if (!columns.contains("banner")) SQLHandler.addColumn("banner", "text", connection);
            if (!columns.contains("locale")) SQLHandler.addColumn("locale", "text", connection);
            if (!columns.contains("email")) SQLHandler.addColumn("email", "text", connection);
            if (!columns.contains("verified")) SQLHandler.addColumn("verified", "boolean", connection);
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
