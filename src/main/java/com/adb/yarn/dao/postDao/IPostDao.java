package com.adb.yarn.dao.postDao;

import com.adb.yarn.dto.PostDto;
import com.adb.yarn.exceptions.DAOException;
import com.adb.yarn.models.Post;

import java.util.List;

public interface IPostDao {
    List<PostDto> getAllPost() throws DAOException;
    int save(Post post) throws DAOException;
    String deletePost(int postId, int ownerId) throws DAOException;
}
