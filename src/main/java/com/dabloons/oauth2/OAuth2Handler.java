package com.dabloons.oauth2;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.dabloons.config.objects.Config;
import com.dabloons.sql.objects.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OAuth2Handler {
    // Method that makes a GET request
    // to the Discord API to get information about the user
    public static String getUserJsonByAccessToken(String accessToken)
            throws Exception {
        // The URL of the Discord API
        String API_URL = "https://discord.com/api";

        // Construct the URL for the API request
        URL url = new URL(API_URL + "/users/@me");

        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to GET
        connection.setRequestMethod("GET");

        // Set the request headers
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        connection.setRequestProperty("Content-Type", "application/json");

        // Read the response from the API
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        String inputLine;
        StringBuilder content = new StringBuilder();
        // Read the response line by line and append it to the 'content' string
        while ((inputLine = reader.readLine()) != null) {
            content.append(inputLine);
        }
        // Close the reader
        reader.close();

        // Return the response from the API as a JSON string
        return content.toString();
    }

    // Method that makes a POST request
    // to the Discord API to exchange
    // an authorization code for an access token
    public static String getAccessTokenFromCode(Config config, String code)
            throws IOException, InterruptedException {
        // Construct the request object
        HttpRequest request = HttpRequest.newBuilder()
                // Set the URL of the Discord API endpoint
                .uri(URI.create("https://discord.com/api/oauth2/token"))
                // Set the request body's Content-Type
                .header("Content-Type", "application/x-www-form-urlencoded")
                // Set the request method to POST and the request body
                .method("POST", HttpRequest.BodyPublishers.ofString(
                        "client_id=" + config.discordClientId() +
                        "&client_secret=" + config.discordClientSecret() +
                        "&grant_type=authorization_code" +
                        "&code=" + code +
                        "&redirect_uri=" + config.discordAppRedirectUri()))
                .build();
        // Send the request and read the response
        HttpResponse<String> response =
                HttpClient.newHttpClient()
                        .send(
                                request, HttpResponse.BodyHandlers.ofString()
                        );
        // Parse the response body as JSON
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(response.body(), JsonObject.class);
        // Return the access token from the JSON object
        return json.get("access_token").getAsString();
    }

    public static User constructUser(String userJson) {
        // Create a Gson instance
        Gson gson = new Gson();
        // Parse the JSON string and convert it to a JsonObject
        JsonObject userObject = gson.fromJson(userJson, JsonObject.class);
        // Extract the necessary information
        // from the JsonObject
        // and use it to construct a User object
        return new User(
                userObject.get("id").getAsString(),
                userObject.get("username").getAsString(),
                userObject.get("avatar").getAsString(),
                userObject.get("discriminator").getAsInt(),
                userObject.get("banner").getAsString(),
                userObject.get("locale").getAsString(),
                userObject.get("email").getAsString(),
                userObject.get("verified").getAsBoolean()
        );
    }
}
