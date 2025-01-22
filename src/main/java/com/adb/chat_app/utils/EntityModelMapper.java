package com.adb.chat_app.utils;

import com.adb.chat_app.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityModelMapper  {
    public static User userMapper(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setProfilePicture(resultSet.getString("profile_picture"));
        user.setBio(resultSet.getString("bio"));
        user.setCreatedAt(resultSet.getTimestamp("created_at"));

        return user;
    }
}
