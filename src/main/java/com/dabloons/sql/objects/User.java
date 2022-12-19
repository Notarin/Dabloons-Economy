package com.dabloons.sql.objects;

// User is a record class that represents a user in the database
public record User(
        String discordId,
        String username,
        String avatar,
        Integer discriminator,
        String banner,
        String locale,
        String email,
        Boolean verified,
        Boolean administrator
) {}