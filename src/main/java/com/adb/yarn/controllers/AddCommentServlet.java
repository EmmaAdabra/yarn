/**
 * The `AddCommentServlet` class in Java handles the addition of comments to a post, including input
 * validation and error handling.
 */
package com.adb.yarn.controllers;

import com.adb.yarn.dao.commentDao.CommentDao;
import com.adb.yarn.dto.CommentDto;
import com.adb.yarn.dto.SessionUserDTO;
import com.adb.yarn.exceptions.InputValidationException;
import com.adb.yarn.models.Comment;
import com.adb.yarn.services.CommentService;
import com.adb.yarn.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddCommentServlet", value = "/add_comment")
public class AddCommentServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AddCommentServlet.class);
    private final CommentService commentService = new CommentService(new CommentDao());


    @SuppressWarnings("unused")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        Response<CommentDto> jsonResponse;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ObjectMapper commentMapper = new ObjectMapper();
            Comment comment = commentMapper.readValue(request.getReader(), Comment.class);
            Integer postId = comment.getPostId();
            String commentText = SanitizeUserContent.sanitize(comment.getComment());

            if(postId != null ){
                ValidateInputs.validateComment(commentText);

                HttpSession session = request.getSession(false);
                SessionUserDTO sessionUser = (session != null) ? (SessionUserDTO) session.getAttribute("sessionUser") : null;

                comment.setUserId((sessionUser != null) ? sessionUser.getUserId() : 0);

                jsonResponse = commentService.addComment(comment);
                response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));

            } else {
                jsonResponse = new Response<>(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Bad request, no post id"
                );
                response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
            }
        } catch (InputValidationException e) {
            jsonResponse = new Response<>(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Bad request, " + e.getMessage()
            );
            response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
            logger.error("An error occur while adding comment -- {}", e.getMessage(), e);

        } catch (Exception e){
            jsonResponse = new Response<>(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error, fail to add comment");
            response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
            logger.error("An error occur while add comment -- {}", e.getMessage(), e);
        }
    }
}
