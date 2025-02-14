package com.adb.chat_app.utils;

import com.adb.chat_app.dto.PostDto;
import com.adb.chat_app.dto.PosterDto;
import com.adb.chat_app.models.Post;
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

    public static PostDto postMapper(ResultSet resultSet) throws SQLException {
        PostDto postDto = new PostDto();
        postDto.setPostId(resultSet.getInt("post_id"));
        postDto.setPostTile(resultSet.getString("title"));
        postDto.setContent(resultSet.getString("content"));
        postDto.setMedia(resultSet.getString("media_path"));
        postDto.setPostDate(DateUtil.formatPostDate(resultSet.getTimestamp("created_at")));

        return postDto;
    }

    public static PosterDto posterMapper(String appPath, ResultSet resultSet) throws SQLException {
        PosterDto posterDto = new PosterDto();
        int posterId = resultSet.getInt("user_id");

        posterDto.setId(posterId);
        posterDto.setFirstName(resultSet.getString("first_name"));
        posterDto.setName(
                StringUtil.getUSerFullName(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                )
        );
        posterDto.setInitial(
                StringUtil.getUserInitial(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                )
        );

        if(resultSet.getInt("has_pfp") == 1){
            posterDto.setPfp(UserUtil.getUserPfpUrl(appPath, posterId));
        }

        return posterDto;
    }

}
