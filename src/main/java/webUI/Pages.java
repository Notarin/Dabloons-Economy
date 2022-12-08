package webUI;

import io.javalin.Javalin;

public class Pages {
    public static void load(Javalin server) {
        server.get("/", ctx -> ctx.result("Hello World"));
        server.get("/debug", ctx -> ctx.result(
                "ctx.ip() : " + ctx.ip() + "\n"
        ));
    }
}
