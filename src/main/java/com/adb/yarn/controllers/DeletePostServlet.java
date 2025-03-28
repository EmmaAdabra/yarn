/**
 * This Java class `DeletePostServlet` handles the deletion of a post based on the post ID and owner
 * ID, with input validation and error handling.
 */
package com.adb.yarn.controllers;

import com.adb.yarn.dao.postDao.PostDao;
import com.adb.yarn.dto.SessionUserDTO;
import com.adb.yarn.exceptions.InputValidationException;
import com.adb.yarn.utils.DeletePostMedia;
import com.adb.yarn.utils.ValidateInputs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "DeletePostServlet", value = "/delete_post")
public class DeletePostServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DeletePostServlet.class);
    PostDao postDao = new PostDao();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String postId = request.getParameter("postId");
        String ownerId = request.getParameter("ownerId");

        HttpSession session = request.getSession(false);

        if(session != null && session.getAttribute("sessionUser") != null){
            SessionUserDTO sessionUser = (SessionUserDTO) session.getAttribute("sessionUser");

            try {
                ValidateInputs.validateQueryParam(postId, ownerId);

                int post = Integer.parseInt(postId);
                int owner = Integer.parseInt(ownerId);
                String filePath;

                if(owner == sessionUser.getUserId()){
                    filePath = postDao.deletePost(post, owner);

                    if(filePath != null){
                        if(!filePath.isEmpty()){
                            DeletePostMedia.deletePostMedia(getServletContext(), filePath);
                        }

                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "post was not found");
                    }

                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }

            } catch (InputValidationException e) {
                logger.error(e.getMessage());
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing post ID or owner ID");
            } catch (Exception e){
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
                logger.error("Internal error occur while deleting post with id: ${}", postId, e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Reload page, login and try again");
        }
    }
}
