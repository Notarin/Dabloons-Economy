package discord.oauth2;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import config.objects.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//TODO: remove unused suppression when finally used
@SuppressWarnings("unused")
public class OAuth2Handler {
    // Method that makes a GET request to the Discord API to get information about the user
    public static String getUserJsonByAccessToken(String accessToken) throws Exception {
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
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            content.append(inputLine);
        }
        reader.close();

        // Return the response from the API as a JSON string
        return content.toString();
    }

    public static String getAccessTokenFromCode(Config config, String code) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://discord.com/api/oauth2/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .method("POST", HttpRequest.BodyPublishers.ofString(
                        "client_id=" + config.discordClientId() +
                        "&client_secret=" + config.discordClientSecret() +
                        "&grant_type=authorization_code" +
                        "&code=" + code +
                        "&redirect_uri=" + config.discordAppRedirectUri()))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(response.body(), JsonObject.class);
        return json.get("access_token").getAsString();
    }
}
