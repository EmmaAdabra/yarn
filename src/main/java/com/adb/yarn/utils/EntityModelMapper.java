package com.adb.yarn.utils;

import com.adb.yarn.dao.userdao.UserDao;
import com.adb.yarn.dto.CommentDto;
import com.adb.yarn.dto.PostDto;
import com.adb.yarn.dto.PosterDto;
import com.adb.yarn.exceptions.DAOException;
import com.adb.yarn.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityModelMapper  {
    public static User userMapper(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
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
        postDto.setLongDate(DateUtil.formatPostDate(resultSet.getTimestamp("created_at")));
        postDto.setShortDate(DateUtil.shortDateFormat(resultSet.getTimestamp("created_at")));
        postDto.setComment(resultSet.getInt("comment"));
        postDto.setLikes(resultSet.getInt("likes"));

        return postDto;
    }

    public static PosterDto posterMapper(ResultSet resultSet) throws SQLException {
        PosterDto posterDto = new PosterDto();
        int posterId = resultSet.getInt("user_id");

        posterDto.setId(posterId);
        posterDto.setFirstName(resultSet.getString("first_name"));
        posterDto.setName(
                StringUtils.getUSerFullName(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                )
        );
        posterDto.setInitial(
                StringUtils.getUserInitial(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                )
        );

        if(resultSet.getInt("has_pfp") == 1){
            posterDto.setPfp(UserUtil.getUserPfpUrl(posterId));
        }

        return posterDto;
    }

    public static CommentDto commentMapper(ResultSet resultSet) throws SQLException, DAOException {
        CommentDto commentDto = new CommentDto();
        UserDao userDao = new UserDao();
        String name = resultSet.getString("commenter_name");
        String[] names = name.split(" ");

        commentDto.setCommentId(resultSet.getInt("id"));
        commentDto.setName(name);
        commentDto.setComment(resultSet.getString("content"));
        commentDto.setTime(
                DateUtil.shortDateFormat(resultSet.getTimestamp("created_at"))
        );

        int userId = resultSet.getInt("user_id");
        if(userId != 0){
            commentDto.setUserId(userId);
            if(userDao.hasUserPfp(userId)){
                commentDto.setPfp(UserUtil.getUserPfpUrl(userId));
            } else{
                commentDto.setInitial(
                        StringUtils.getUserInitial(names[0], names[1])
                );
            }
        } else{
            commentDto.setPfp(StringUtils.randomAvatarUrl());
        }

        return commentDto;
    }

}