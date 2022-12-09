package config.objects;

public record Config(Integer httpServerPort, String discordClientId, String discordClientSecret, String discordAppRedirectUri, String discordOAuthUrl) {
}
