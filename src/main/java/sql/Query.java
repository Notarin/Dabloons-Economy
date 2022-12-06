package sql;

//TODO: Remove warning suppression for "unused" when finally using query classes.
@SuppressWarnings({"unused", "CommentedOutCode", "GrazieInspection"})
public class Query {
/* // Commented out because I'm planning on revising how users work, I plan on instead relying on Discord Oauth2
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
*/
}