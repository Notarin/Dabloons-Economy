package webUI;

import config.ConfigHandler;
import config.objects.Config;
import discord.oauth2.OAuth2Handler;
import io.javalin.Javalin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Pages {
    // load method takes a Javalin server and a Config object as arguments
    public static void load(Javalin server, Config config) {
        // define a route for the root path and return the index HTML page
        server.get("/", ctx -> ctx.html(PageData.indexHtml()));
        // define a route for the login path and redirect to the Discord OAuth URL
        server.get("/login", ctx -> {
            if (ctx.sessionAttribute("loggedIn") != "true") {
                ctx.redirect(config.discordOAuthUrl());
            } else {
                ctx.redirect("/");
            }
        });
        // define a route for the receiveOAuth path and process the OAuth code and user data
        server.get("/receiveOAuth", ctx -> {
            // get the OAuth code from the query parameter
            String code = ctx.queryParam("code");
            // use the Config and code to get the access token
            String accessToken = OAuth2Handler.getAccessTokenFromCode(ConfigHandler.load(), code);
            // use the access token to get the user JSON data
            String userJson = OAuth2Handler.getUserJsonByAccessToken(accessToken);
            // print the user JSON data to the console (TODO: use userJson properly and remove this line)
            System.out.println(userJson);
            // redirect the user back to the root path
            ctx.redirect("/");
        });
    }
}

// class for static methods that return page data
class PageData {
    // method that returns the index HTML page
    public static String indexHtml() throws IOException {
        String indexHtml;

        // get the path to the index.html file
        Path path = Paths.get("Pages/index.html");
        // read the contents of the file and convert to a string
        indexHtml = new String(Files.readAllBytes(path));
        return indexHtml;
    }
}
