package com.dabloons.webui;

import com.dabloons.config.ConfigHandler;
import com.dabloons.config.objects.Config;
import com.dabloons.oauth2.OAuth2Handler;
import com.dabloons.sql.Query;
import com.dabloons.sql.objects.User;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.Connection;
import java.util.Objects;

public class Pages {
    // load method takes a Javalin server and a Config object as arguments
    public static void load(
            Javalin server,
            Connection sqlDatabaseConnection,
            Config config
    ) {
        // define a route for the root path and return the index HTML page
        server.get("/", ctx -> ctx.html(PageData.indexHtml(ctx)));
        // define route for the profile path and return profile HTML page
        server.get("/profile", ctx -> {
            if (ctx.sessionAttribute("loggedIn") == "true") {
                ctx.html(PageData.profileHtml(ctx));
            } else {
                ctx.redirect("/");
            }
        });
        server.get("/styles.css", ctx -> {
            ctx.contentType("text/css");
            ctx.result(PageData.styles());
        });
        // define a route for the login path
        // and redirect to the Discord OAuth URL
        server.get("/login", ctx -> {
            if (ctx.sessionAttribute("loggedIn") != "true") {
                ctx.redirect(config.discordOAuthUrl());
            } else {
                ctx.redirect("/");
            }
        });
        // define a route for the receiveOAuth path
        // and process the OAuth code and user data
        server.get("/receiveOAuth", ctx -> {
            // get the OAuth code from the query parameter
            String code = ctx.queryParam("code");
            // use the Config and code to get the access token
            String accessToken = OAuth2Handler
                    .getAccessTokenFromCode(
                            ConfigHandler.load(), code
                    );
            // use the access token to get the user JSON data
            String userJson = OAuth2Handler
                    .getUserJsonByAccessToken(accessToken);
            User user = OAuth2Handler.constructUser(
                    sqlDatabaseConnection, userJson
            );
            ctx.sessionAttribute("loggedIn", "true");
            ctx.sessionAttribute("discordId", user.discordId());
            ctx.sessionAttribute("username", user.username());
            ctx.sessionAttribute("avatar", user.avatar());
            ctx.sessionAttribute("discriminator", user.discriminator());
            ctx.sessionAttribute("banner", user.banner());
            ctx.sessionAttribute("locale", user.locale());
            ctx.sessionAttribute("email", user.email());
            ctx.sessionAttribute("verified", user.verified());
            ctx.sessionAttribute("administrator", user.administrator());
            Query.saveUser(sqlDatabaseConnection, user);
            // redirect the user back to the root path
            ctx.redirect("/profile");
        });
        server.get("/asset/{asset}", ctx -> {
            if (PageData.asset(ctx.pathParam("asset")) != null) {
                ctx.result(
                        Objects.requireNonNull(
                                PageData.asset(ctx.pathParam("asset"))
                        )
                );
            } else {
                ctx.status(404);
            }
        });
    }

    public static String processPage(Context ctx, String html) {
        return html
                .replace("{LOGIN}",
                        Objects.requireNonNull(
                                Objects.requireNonNull(
                                        ctx.sessionAttribute(
                                                "loggedIn"
                                        )
                                ).toString()
                        ))
                .replace("{ID}",
                        Objects.requireNonNull(
                                Objects.requireNonNull(
                                        ctx.sessionAttribute(
                                                "discordId"
                                        )
                                ).toString()
                        ))
                .replace("{USERNAME}",
                        Objects.requireNonNull(
                                Objects.requireNonNull(
                                        ctx.sessionAttribute(
                                                "username"
                                        )
                                ).toString()
                        ))
                .replace("{AVATAR}",
                        Objects.requireNonNull(
                                Objects.requireNonNull(
                                        ctx.sessionAttribute(
                                                "avatar"
                                        )
                                ).toString()
                        ))
                .replace("{DISCRIMINATOR}",
                        Objects.requireNonNull(
                                Objects.requireNonNull(
                                        ctx.sessionAttribute(
                                                "discriminator"
                                        )
                                ).toString()
                        ))
                .replace("{BANNER}",
                        Objects.requireNonNull(
                                Objects.requireNonNull(
                                        ctx.sessionAttribute(
                                                "banner"
                                        )
                                ).toString()
                        ))
                .replace("{LOCALE}",
                        Objects.requireNonNull(
                                Objects.requireNonNull(
                                        ctx.sessionAttribute(
                                                "locale"
                                        )
                                ).toString()
                        ))
                .replace("{EMAIL}",
                        Objects.requireNonNull(
                                Objects.requireNonNull(
                                        ctx.sessionAttribute(
                                                "email"
                                        )
                                ).toString()
                        ))
                .replace("{VERIFIED}",
                        Objects.requireNonNull(
                                Objects.requireNonNull(
                                        ctx.sessionAttribute(
                                                "verified"
                                        )
                                ).toString()))
                .replace("{ADMIN}",
                        Objects.requireNonNull(
                                Objects.requireNonNull(
                                        ctx.sessionAttribute(
                                                "administrator"
                                        )
                                ).toString()
                        ))
                ;
    }
}

