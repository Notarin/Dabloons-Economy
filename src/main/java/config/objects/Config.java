package config.objects;

// Config is a record that holds the configuration data for the app
public record Config(
        // the port for the HTTP server
        Integer httpServerPort,
        // the Discord app client ID
        String discordClientId,
        // the Discord app client secret
        String discordClientSecret,
        // the Discord app redirect URI
        String discordAppRedirectUri,
        // the Discord OAuth URL
        String discordOAuthUrl
) {
}
