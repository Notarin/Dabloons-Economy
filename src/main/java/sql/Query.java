package sql;

import sql.objects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//TODO: Remove warning suppression for "unused" when finally using query classes.
public class Query {
// Commented out because I'm planning on revising how users work, I plan on instead relying on Discord Oauth2
    private static User constructUser(ResultSet sqlUser) throws SQLException {
        return new User(
                sqlUser.getString("discordId"),
                sqlUser.getString("username"),
                sqlUser.getString("avatar"),
                sqlUser.getInt("discriminator"),
                sqlUser.getString("banner"),
                sqlUser.getString("locale"),
                sqlUser.getString("email"),
                sqlUser.getBoolean("verified")
        );
    }

    public static User userById(Connection connection, String userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("""
                SELECT
                    discordId,
                    username,
                    avatar,
                    discriminator,
                    banner,
                    locale,
                    email,
                    verified
                FROM users WHERE discordId = ?
                """);
        statement.setString(1, userId);
        ResultSet sqlUser = statement.executeQuery();
        if (sqlUser.next()) {
            return constructUser(sqlUser);
        } else {
            return null;
        }
    }

    // Save a User to the 'users' table in the database
    public static void saveUser(Connection connection, User user) throws SQLException {
        // Create a prepared statement to insert the User data into the 'users' table
        PreparedStatement statement = connection.prepareStatement("""
            INSERT INTO users (
                discordId,
                username,
                avatar,
                discriminator,
                banner,
                locale,
                email,
                verified
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """);
        // Set the values of the prepared statement's parameters using the data from the User object
        statement.setString(1, user.discordId());
        statement.setString(2, user.username());
        statement.setString(3, user.avatar());
        statement.setInt(4, user.discriminator());
        statement.setString(5, user.banner());
        statement.setString(6, user.locale());
        statement.setString(7, user.email());
        statement.setBoolean(8, user.verified());
        // Execute the prepared statement
        statement.execute();
    }
}