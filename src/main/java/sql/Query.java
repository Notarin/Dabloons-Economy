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
                FROM users WHERE id = ?
                """);
        statement.setString(1, userId);
        return constructUser(statement.executeQuery());
    }
}