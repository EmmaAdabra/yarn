package com.adb.any_post.dao.postDao;

import com.adb.any_post.dto.PostDto;
import com.adb.any_post.exceptions.DAOException;
import com.adb.any_post.models.Post;

import java.util.List;

public interface IPostDao {
    List<PostDto> getAllPost() throws DAOException;
    int save(Post post) throws DAOException;
    int deletePost(int postId, int ownerId) throws DAOException;
}
