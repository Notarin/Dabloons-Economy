import config.ConfigHandler;
import config.objects.Config;
import sql.SQLHandler;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Config config = ConfigHandler.load();
        SQLHandler.init();
        webUI.webServer.init(config);
    }
}
