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
    public static void load(Javalin server, Config config) {
        server.get("/", ctx -> ctx.html(PageData.indexHtml()));
        server.get("/login", ctx -> {
            if (ctx.sessionAttribute("loggedIn") != "true") {
                ctx.redirect(config.discordOAuthUrl());
            } else {
                ctx.redirect("/");
            }
        });
        server.get("/receiveOAuth", ctx -> {
            String code = ctx.queryParam("code");
            String accessToken = OAuth2Handler.getAccessTokenFromCode(ConfigHandler.load(), code);
            String userJson = OAuth2Handler.getUserJsonByAccessToken(accessToken);
            System.out.println(userJson);
            ctx.redirect("/");
        });
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