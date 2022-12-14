package com.dabloons;

import com.dabloons.config.ConfigHandler;
import com.dabloons.config.objects.Config;
import com.dabloons.sql.SQLHandler;
import com.dabloons.webui.WebServer;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args)
            throws
            ClassNotFoundException,
            SQLException,
            FileNotFoundException
    {
        // Load the configuration
        Config config = ConfigHandler.load();
        // Initialize the database
        SQLHandler.init();
        Connection connection = SQLHandler.connect();
        // Initialize the web server
        WebServer.init(connection, config);
    }
}
