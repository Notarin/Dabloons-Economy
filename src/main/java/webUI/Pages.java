package webUI;

import io.javalin.Javalin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Pages {
    public static void load(Javalin server) {
        server.get("/", ctx -> ctx.html(PageData.indexHtml()));
        server.get("/debug", ctx -> ctx.result(
                "ctx.ip() : " + ctx.ip() + "\n"
        ));
    }
}

class PageData {
    public static String indexHtml() throws IOException {
        String indexHtml;

        Path path = Paths.get("Pages/index.html");
        indexHtml = new String(Files.readAllBytes(path));
        return indexHtml;
    }
}