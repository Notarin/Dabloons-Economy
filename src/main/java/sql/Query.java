package sql;

import sql.objects.user;

import java.sql.*;

public class Query {
    public static user userById(Connection connection, Integer userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("""
                SELECT id, username, password, discordId FROM users WHERE id = ?
                """);
        statement.setInt(1,1);
        ResultSet results = statement.executeQuery();
        return new user(
                results.getInt("id"),
                results.getString("username"),
                results.getString("password"),
                results.getInt("discordId")
        );
    }
}