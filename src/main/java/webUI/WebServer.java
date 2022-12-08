package webUI;

import config.objects.Config;
import io.javalin.Javalin;

public class WebServer {
    public static void init(Config config) {
        //See https://javalin.io/documentation
        Javalin server = Javalin.create();
        Pages.load(server);
        server.start(config.httpServerPort());
    }
}
