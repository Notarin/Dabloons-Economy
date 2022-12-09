package config;

import config.objects.Config;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

@SuppressWarnings("unused")
public class ConfigHandler {

    public static Config load() {
        check(loadConfig(), loadExample());
        return constructConfig(loadConfig());
    }

    private static Map<String, Object> loadConfig() {
        Map<String, Object> config;

        try {
            //Loading config file
            InputStream inputStream = new FileInputStream("config.yml");
            Yaml yaml = new Yaml();
            //set the config object
            config = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            System.out.println(
                    "[ERROR] Config file missing, please copy config.example.yml to config.yml and configure values"
            );
            throw new RuntimeException(e);
        }

        return config;
    }

    private static Map<String, Object> loadExample() {
        Map<String, Object> exampleConfig;

        try {
            //Loading config example file
            InputStream inputStream = new FileInputStream("config.example.yml");
            Yaml yaml = new Yaml();
            //set the config object
            exampleConfig = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return exampleConfig;
    }

    private static void check(Map<String, Object> config, Map<String, Object> exampleConfig) {
        //check for values awaiting configuration in config, or useless values in config
        for (Map.Entry<String, Object> configIteration : config.entrySet()) {
            //Check for keys in config not present in exampleConfig, this isn't really a problem so just warn.
            if (exampleConfig.get(configIteration.getKey()) == null) {
                System.out.println("[WARNING] Key found in config not present in example config: " + configIteration.getKey());
            }
            if (configIteration.getValue().equals(exampleConfig.get(configIteration.getKey()))) {
                System.out.println("[ERROR] config.Config value unchanged, please configure key \"" + configIteration.getKey() + "\"");
                System.exit(1);
            }
        }

        //check for missing keys in config
        for (Map.Entry<String, Object> exampleConfigIteration : exampleConfig.entrySet()) {
            if (config.get(exampleConfigIteration.getKey()) == null) {
                System.out.println("[ERROR] Key found in example config not present in config: " + exampleConfigIteration.getKey() +
                        "\nPlease copy all missing values and set them.");
                System.exit(1);
            }
        }
    }

    private static Config constructConfig(Map<String, Object> config) {
        return new Config(
                Integer.parseInt(config.get("httpServerPort").toString()),
                config.get("discordClientId").toString(),
                config.get("discordClientSecret").toString(),
                config.get("discordAppRedirectUri").toString(),
                config.get("discordOAuthUrl").toString()
        );
    }
}
