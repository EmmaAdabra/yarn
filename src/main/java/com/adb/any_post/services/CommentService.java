package com.adb.any_post.services;

import com.adb.any_post.dao.commentDao.CommentDao;
import com.adb.any_post.dao.postDao.PostDao;
import com.adb.any_post.dto.CommentDto;
import com.adb.any_post.exceptions.DAOException;
import com.adb.any_post.exceptions.UnknownException;
import com.adb.any_post.models.Comment;
import com.adb.any_post.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


public class CommentService {

    public CommentService(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
    private CommentDao commentDao;
    private final PostDao postDao = new PostDao();

    public Response<CommentDto> addComment(Comment comment) throws UnknownException {
        Response<CommentDto> serviceResponse = new Response<>();

        if(postDao.doesPostExist(comment.getPostId())){
            try {
                Optional<CommentDto> optionalCommentDto = commentDao.saveComment(comment);

                if(optionalCommentDto.isPresent()){
                    CommentDto commentDto = optionalCommentDto.get();

                    serviceResponse = new Response<>(ResponseCode.RESOURCE_CREATED.getCode(), "comment added", commentDto);
                    logger.info("Comment with ID: {} added to post with ID {}", commentDto.getCommentId(), comment.getPostId());
                }
            } catch (DAOException e) {
                serviceResponse = new Response<>(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error, fail to add comment");
            }
        } else {
            serviceResponse = new Response<>(HttpServletResponse.SC_NOT_FOUND,
                    "Post no longer exist");
        }


        return serviceResponse;
    }
}
