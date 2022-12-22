package com.dabloons.webui;

import io.javalin.http.Context;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// class for static methods that return page data
class PageData {
    // method that returns the index HTML page
    public static String indexHtml(Context ctx) throws IOException {
        String indexHtml;
        // get the path to the index.html file
        Path path = Paths.get("Pages/index.html");
        // get the profile button HTML
        String profileButtonHtml =
                new String(
                        Files.readAllBytes(
                                Paths.get(
                                        "Pages/Widgets/Profile-Button.html"
                                )
                        )
                );
        // get the login button HTML
        String loginButtonHtml =
                new String(
                        Files.readAllBytes(
                                Paths.get(
                                        "Pages/Widgets/Login-Button.html"
                                )
                        )
                );
        // read the contents of the file and convert to a string
        indexHtml = new String(Files.readAllBytes(path));
        if (ctx.sessionAttribute("loggedIn") == null) {
            indexHtml = indexHtml
                    .replace("{Profile-Button}", loginButtonHtml);
        } else {
            indexHtml = indexHtml
                    .replace("{Profile-Button}", profileButtonHtml);
        }
        return indexHtml;
    }

    public static String profileHtml(Context ctx) throws IOException {
        String indexHtml;
        // get path to profile.html file
        Path path = Paths.get("Pages/profile.html");
        // read file contents and convert to string
        indexHtml = new String(Files.readAllBytes(path));
        return Pages.processPage(ctx, indexHtml);
    }

    public static String styles() throws IOException {
        // get path to styles.css file
        Path path = Paths.get("Pages/styles.css");
        // read file contents and convert to string
        return new String(Files.readAllBytes(path));
    }

    public static byte[] asset(String asset) {
        try {
            // get path to asset file
            Path path = Paths.get("Pages/assets/" + asset);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            return null;
        }
    }
}
