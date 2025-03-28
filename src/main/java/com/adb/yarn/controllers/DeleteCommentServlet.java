/**
 * This Java servlet class `DeleteCommentServlet` handles the deletion of a comment based on the
 * comment ID and owner ID with input validation and error handling.
 */

package com.adb.yarn.controllers;

import com.adb.yarn.dao.commentDao.CommentDao;
import com.adb.yarn.dto.SessionUserDTO;
import com.adb.yarn.exceptions.InputValidationException;
import com.adb.yarn.utils.ValidateInputs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteCommentServlet", value = "/delete_comment")
public class DeleteCommentServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DeleteCommentServlet.class);
    CommentDao commentDao = new CommentDao();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commentId = request.getParameter("commentId");
        String ownerId = request.getParameter("ownerId");

        HttpSession session = request.getSession(false);

        if(session != null && session.getAttribute("sessionUser") != null){
            SessionUserDTO sessionUser = (SessionUserDTO) session.getAttribute("sessionUser");

            try {
                ValidateInputs.validateQueryParam(commentId, ownerId);

                int comment = Integer.parseInt(commentId);
                int owner = Integer.parseInt(ownerId);
                int deletedRow = 0;

                if(owner != sessionUser.getUserId()){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                } else {
                    deletedRow = commentDao.deleteComment(comment, owner);
                }

                if(deletedRow > 0){
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "comment was not found");
                }

            } catch (InputValidationException e) {
                logger.error(e.getMessage());
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing comment ID or owner ID");
            } catch (Exception e){
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
                logger.error("Internal error occur while deleting comment with id: ${}", commentId, e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Reload page, login and try again");
        }
    }
}
