/**
 * This Java servlet class handles the deletion of a like associated with a post, including input
 * validation and error handling.
 */
package com.adb.any_post.controllers;

import com.adb.any_post.dao.likesDao.LikeDao;
import com.adb.any_post.dto.LikedPost;
import com.adb.any_post.exceptions.InputValidationException;
import com.adb.any_post.utils.ValidateInputs;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteLikeServlet", value = "/deleteLike")
public class DeleteLikeServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DeleteLikeServlet.class);
    private final LikeDao likeDao = new LikeDao();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonMapper likePostMapper = new JsonMapper();
        Integer likedId = 0;

        try {
            LikedPost likedPost = likePostMapper.readValue(request.getReader(), LikedPost.class);

            likedId = likedPost.getLikedId();
            ValidateInputs.validateQueryParam(likedId);

            int deletedRow;

            HttpSession session = request.getSession(false);

            boolean isGuest = (session == null || session.getAttribute("sessionUser") == null);

            deletedRow = likeDao.deleteLike(likedId);

            if (deletedRow > 0){
                if (isGuest){
                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "This post no longer exist");
            }

        } catch (InputValidationException e) {
            logger.error(e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing like ID");
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            logger.error("Internal error occur while deleting like with id: ${}", likedId, e);
        }
    }
}
