package sql.objects;

@SuppressWarnings("unused")
public record User(Integer id, String username, String password, Integer discordId) {
}