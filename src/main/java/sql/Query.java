package sql;

import sql.objects.user;

import java.sql.*;

@SuppressWarnings("unused")
public class Query {
    private static user constructUser(ResultSet sqlUser) throws SQLException {
        return new user(
                sqlUser.getInt("id"),
                sqlUser.getString("username"),
                sqlUser.getString("password"),
                sqlUser.getInt("discordId")
        );
    }
    public static user userById(Connection connection, Integer userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("""
                SELECT id, username, password, discordId FROM users WHERE id = ?
                """);
        statement.setInt(1,1);
        return constructUser(statement.executeQuery());
    }
}