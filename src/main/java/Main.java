import config.ConfigHandler;
import config.objects.Config;
import sql.SQLHandler;
import webUI.WebServer;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Load the configuration
        Config config = ConfigHandler.load();
        // Initialize the database
        SQLHandler.init();
        // Initialize the web server
        WebServer.init(config);
    }
}
