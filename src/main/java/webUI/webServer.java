package webUI;

import config.objects.Config;
import io.javalin.Javalin;

public class webServer {
    public static void init(Config config) {
        //See https://javalin.io/documentation
        Javalin.create(/*config*/)
                .get("/", ctx -> ctx.result("Hello World"))
                .start(config.httpServerPort());
    }
}
