package config;

import config.objects.Config;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

@SuppressWarnings("unused")
// The ConfigHandler class is a utility for loading and checking configuration files.
public class ConfigHandler {
    // static method to load the configuration file and check it against the example configuration file
    public static Config load() {
        // check the values in the configuration file against the values in the example configuration file
        check(loadConfig(), loadExample());
        // return a Config object constructed from the values in the configuration file
        return constructConfig(loadConfig());
    }

    // method to load the configuration file and return a map of its key-value pairs
    private static Map<String, Object> loadConfig() {
        // map to hold the key-value pairs from the configuration file
        Map<String, Object> config;

        try {
            // create an input stream from the configuration file
            InputStream inputStream = new FileInputStream("config.yml");
            // create a new instance of the SnakeYAML parser
            Yaml yaml = new Yaml();
            // load the key-value pairs from the configuration file into the map
            config = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            // if the configuration file is not found, print an error message and throw a runtime exception
            System.out.println(
                    "[ERROR] Config file missing, please copy config.example.yml to config.yml and configure values"
            );
            throw new RuntimeException(e);
        }

        // return the map of key-value pairs from the configuration file
        return config;
    }

    // method to load the example configuration file and return a map of its key-value pairs
    private static Map<String, Object> loadExample() {
        // map to hold the key-value pairs from the example configuration file
        Map<String, Object> exampleConfig;

        try {
            // create an input stream from the example configuration file
            InputStream inputStream = new FileInputStream("config.example.yml");
            // create a new instance of the SnakeYAML parser
            Yaml yaml = new Yaml();
            // load the key-value pairs from the example configuration file into the map
            exampleConfig = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            // if the example configuration file is not found, throw a runtime exception
            throw new RuntimeException(e);
        }
        // return the map of key-value pairs from the example configuration file
        return exampleConfig;
    }

    // method to check the values in the configuration file against the values in the example configuration file
    private static void check(Map<String, Object> config, Map<String, Object> exampleConfig) {
        // loop through the entries in the configuration map
        for (Map.Entry<String, Object> configIteration : config.entrySet()) {
            // check if the key in the configuration map exists in the example configuration map
            if (exampleConfig.get(configIteration.getKey()) == null) {
                // if the key does not exist in the example configuration map, print a warning message
                System.out.println("[WARNING] Key found in config not present in example config: " + configIteration.getKey());
            }

            // check if the value of the key in the configuration map is equal to the value of the key in the example configuration map
            if (configIteration.getValue().equals(exampleConfig.get(configIteration.getKey()))) {
                // if the values are equal, print an error message and exit the program
                System.out.println("[ERROR] Config value unchanged, please configure key \"" + configIteration.getKey() + "\"");
                System.exit(1);
            }
        }
        // loop through the entries in the example configuration map
        for (Map.Entry<String, Object> exampleConfigIteration : exampleConfig.entrySet()) {
            // check if the key in the example configuration map exists in the configuration map
            if (config.get(exampleConfigIteration.getKey()) == null) {
                // if the key does not exist in the configuration map, print an error message and exit the program
                System.out.println("[ERROR] Key found in example config not present in config: " + exampleConfigIteration.getKey() +
                        "\nPlease copy all missing values and set them.");
                System.exit(1);
            }
        }
    }

    // method to construct a Config object from a map of key-value pairs
    private static Config constructConfig(Map<String, Object> config) {
        // create a new Config object with values from the map
        return new Config(
                // set the httpServerPort value to the value of the "httpServerPort" key in the map
                Integer.parseInt(config.get("httpServerPort").toString()),
                // set the discordClientId value to the value of the "discordClientId" key in the map
                config.get("discordClientId").toString(),
                // set the discordClientSecret value to the value of the "discordClientSecret" key in the map
                config.get("discordClientSecret").toString(),
                // set the discordAppRedirectUri value to the value of the "discordAppRedirectUri" key in the map
                config.get("discordAppRedirectUri").toString(),
                // set the discordOAuthUrl value to the value of the "discordOAuthUrl" key in the map
                config.get("discordOAuthUrl").toString()
        );
    }
}