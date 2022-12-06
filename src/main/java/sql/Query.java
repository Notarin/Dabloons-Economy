package sql;

import sql.objects.User;

import java.sql.*;

//TODO: Remove warning suppression for "unused" when finally using query classes.
@SuppressWarnings("unused")
public class Query {
    private static User constructUser(ResultSet sqlUser) throws SQLException {
        return new User(
                sqlUser.getInt("id"),
                sqlUser.getString("username"),
                sqlUser.getString("password"),
                sqlUser.getInt("discordId")
        );
    }

    public static User userById(Connection connection, Integer userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("""
                SELECT id, username, password, discordId FROM users WHERE id = ?
                """);
        statement.setInt(1, 1);
        return constructUser(statement.executeQuery());
    }
}