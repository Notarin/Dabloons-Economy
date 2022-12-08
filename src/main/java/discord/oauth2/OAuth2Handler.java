package discord.oauth2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
}
