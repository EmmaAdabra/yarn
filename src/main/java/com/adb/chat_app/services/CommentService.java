package com.adb.chat_app.services;

import com.adb.chat_app.dao.commentDao.CommentDao;
import com.adb.chat_app.dto.CommentDto;
import com.adb.chat_app.exceptions.DAOException;
import com.adb.chat_app.exceptions.UnknownException;
import com.adb.chat_app.models.Comment;
import com.adb.chat_app.utils.*;
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

    public Response<CommentDto> addComment(Comment comment) throws UnknownException {
        Response<CommentDto> serviceResponse;

        try {
            Optional<CommentDto> optionalCommentDto = commentDao.saveComment(comment);

            if(optionalCommentDto.isPresent()){
                CommentDto commentDto = optionalCommentDto.get();

                serviceResponse = new Response<>(ResponseCode.RESOURCE_CREATED.getCode(), "comment added", commentDto);
                logger.info("Comment with ID: {} added to post with ID {}", commentDto.getCommentId(), comment.getPostId());
            } else {
                serviceResponse = new Response<>(HttpServletResponse.SC_NOT_FOUND,
                        "This have been deleted or do no longer exist");
            }
        } catch (DAOException e) {
            serviceResponse = new Response<>(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error, fail to add comment");
        }

        return serviceResponse;
    }

//    public CommentDto createCommenterDto(Comment comment) throws DAOException {
//        UserDao userDao = new UserDao();
//        String commenterName = null;
//        String commenterPfpUrl = null;
//        String commenterInitial = null;
//
//
//        if(comment.getUserId() != 0){
//            Optional<User> user = userDao.get(comment.getUserId());
//
//            if(user.isPresent()){
//                User commenter = user.get();
//                commenterName = StringUtil.getUSerFullName(
//                        commenter.getFirstName(),
//                        commenter.getLastName()
//                );
//
//                commenterInitial = StringUtil.getUserInitial( commenter.getFirstName(),
//                        commenter.getLastName());
//
//                commenterPfpUrl = (userDao.hasUserPfp(commenter.getId())) ?
//                        UserUtil.getUserPfpUrl(comment.getUserId()) : null;
//
//            }
//        } else {
//            commenterInitial = "A";
//            commenterName = "Anonymous";
//            commenterPfpUrl = StringUtil.randomAvatarUrl();
//        }
//        String time = (DateUtil.formatPostDate(new Date()));
//        CommentDto commentDto = new CommentDto(1, commenterName, commenterPfpUrl, commenterInitial, time, comment);
//
//        return commentDto;
//    }
}
