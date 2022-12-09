package webUI;

import config.ConfigHandler;
import config.objects.Config;
import discord.oauth2.OAuth2Handler;
import io.javalin.Javalin;
import sql.Query;
import sql.objects.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

public class Pages {
    // load method takes a Javalin server and a Config object as arguments
    public static void load(Javalin server, Connection sqlDatabaseConnection, Config config) {
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
            User user = OAuth2Handler.constructUser(userJson);
            ctx.sessionAttribute("loggedIn", "true");
            ctx.sessionAttribute("discordId", user.discordId());
            ctx.sessionAttribute("username", user.username());
            ctx.sessionAttribute("avatar", user.avatar());
            ctx.sessionAttribute("discriminator", user.discriminator());
            ctx.sessionAttribute("banner", user.banner());
            ctx.sessionAttribute("locale", user.locale());
            ctx.sessionAttribute("email", user.email());
            ctx.sessionAttribute("verified", user.verified());
            Query.saveUser(sqlDatabaseConnection, user);
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
