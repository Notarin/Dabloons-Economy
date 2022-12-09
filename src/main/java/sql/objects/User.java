package sql.objects;

@SuppressWarnings("unused")
// User is a record class that represents a user in the database
public record User(Integer id, String username, String password, Integer discordId) {
}