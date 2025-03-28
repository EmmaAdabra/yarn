// This code snippet is defining an interface named `ICommentDao` in the package
// `com.adb.any_post.dao.commentDao`. The interface declares three methods related to handling
// comments:
package com.adb.yarn.dao.commentDao;

import com.adb.yarn.dto.CommentDto;
import com.adb.yarn.exceptions.DAOException;
import com.adb.yarn.models.Comment;

import java.util.List;
import java.util.Optional;

public interface ICommentDao {
    List<CommentDto> getPostComments(int postId) throws DAOException;
    Optional<CommentDto> saveComment(Comment comment) throws DAOException;
    int deleteComment(int commentId, int ownerId) throws DAOException;
}
