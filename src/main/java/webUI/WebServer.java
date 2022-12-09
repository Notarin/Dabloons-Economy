package webUI;

import config.objects.Config;
import io.javalin.Javalin;

import java.sql.Connection;

public class WebServer {
    // Initialize the web server by creating a Javalin server instance and starting it, see https://javalin.io/documentation for more info
    public static void init(Connection sqlDatabaseConnection, Config config) {
        // Create a Javalin server instance
        Javalin server = Javalin.create();
        // Load the pages on the server
        Pages.load(server, sqlDatabaseConnection, config);
        // Start the server on the port specified in the configuration
        server.start(config.httpServerPort());
    }
}
