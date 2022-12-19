package com.dabloons.sql;

import com.dabloons.sql.objects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {
    private static User constructUser(ResultSet sqlUser) throws SQLException {
        return new User(
                sqlUser.getString("discordId"),
                sqlUser.getString("username"),
                sqlUser.getString("avatar"),
                sqlUser.getInt("discriminator"),
                sqlUser.getString("banner"),
                sqlUser.getString("locale"),
                sqlUser.getString("email"),
                sqlUser.getBoolean("verified")
        );
    }

    public static User userById(Connection connection, String userId)
            throws SQLException {
        PreparedStatement statement = connection.prepareStatement("""
                SELECT
                    discordId,
                    username,
                    avatar,
                    discriminator,
                    banner,
                    locale,
                    email,
                    verified
                FROM users WHERE discordId = ?
                """);
        statement.setString(1, userId);
        ResultSet sqlUser = statement.executeQuery();
        if (sqlUser.next()) {
            return constructUser(sqlUser);
        } else {
            return null;
        }
    }

    // Save a User to the 'users' table in the database
    public static void saveUser(Connection connection, User user)
            throws SQLException {
        // Check if the user already exists in the database
        User existingUser = userById(connection, user.discordId());
        PreparedStatement statement;
        if (existingUser != null) {
            // Create a prepared statement to update the User data
            // in the 'users' table
            statement = connection.prepareStatement("""
                    UPDATE users
                    SET
                        username = ?,
                        avatar = ?,
                        discriminator = ?,
                        banner = ?,
                        locale = ?,
                        email = ?,
                        verified = ?
                    WHERE discordId = ?
                    """);
            // Set the values of the prepared statement's parameters
            // using the data from the User object
            statement.setString(1, user.username());
            statement.setString(2, user.avatar());
            statement.setInt(3, user.discriminator());
            statement.setString(4, user.banner());
            statement.setString(5, user.locale());
            statement.setString(6, user.email());
            statement.setBoolean(7, user.verified());
            statement.setString(8, user.discordId());
            // Execute the prepared statement
        } else {
            // Create a prepared statement
            // to insert the User data into the 'users' table
            statement = connection.prepareStatement("""
                    INSERT INTO users (
                        discordId,
                        username,
                        avatar,
                        discriminator,
                        banner,
                        locale,
                        email,
                        verified
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    """);
            // Set the values of the prepared statement's parameters
            // using the data from the User object
            statement.setString(1, user.discordId());
            statement.setString(2, user.username());
            statement.setString(3, user.avatar());
            statement.setInt(4, user.discriminator());
            statement.setString(5, user.banner());
            statement.setString(6, user.locale());
            statement.setString(7, user.email());
            statement.setBoolean(8, user.verified());
        }
        // Execute the prepared statement
        statement.execute();
    }

    // Create the 'users' table in the database
    public static void createUsersTable(Connection connection)
            throws SQLException {
        // Create a statement object
        Statement statement = connection.createStatement();
        // Use the statement to execute an SQL query
        // that creates the 'users' table
        statement.execute("""
                CREATE TABLE IF NOT EXISTS users (
                discordId text PRIMARY KEY,
                username text NOT NULL,
                avatar text NOT NULL,
                discriminator integer NOT NULL,
                banner text NOT NULL,
                locale text NOT NULL,
                email text NOT NULL,
                verified boolean NOT NULL
                )""");
    }

    public static void createAdministratorsTable(Connection connection)
            throws SQLException {
        // Create a statement object
        Statement statement = connection.createStatement();
        // Use the statement to execute an SQL query
        // that creates the 'administrators' table
        statement.execute("""
                CREATE TABLE "administrators" (
                    "discordId" TEXT REFERENCES "users"("discordId"),
                    PRIMARY KEY("discordId")
                 );""");
    }

    public static void addColumn(
            String table,
            String value,
            String type,
            Connection connection
    ) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(
                    "ALTER TABLE " + table + " ADD COLUMN " + value + " " + type
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}