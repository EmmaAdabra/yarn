/**
 * The `LikePostServlet` class handles the liking of posts, validating inputs and saving likes to the
 * database with appropriate error handling.
 */
package com.adb.any_post.controllers;

import com.adb.any_post.dao.likesDao.LikeDao;
import com.adb.any_post.dao.postDao.PostDao;
import com.adb.any_post.dto.SessionUserDTO;
import com.adb.any_post.exceptions.InputValidationException;
import com.adb.any_post.models.Like;
import com.adb.any_post.utils.ValidateInputs;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
@WebServlet(name = "LikePostServlet", value = "/likePost")
public class LikePostServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LikePostServlet.class);
    private LikeDao likeDao = new LikeDao();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject responseJson = new JSONObject();
        response.setContentType("application/json");

       try{
           ValidateInputs.validateQueryParam(request.getParameter("postId"));
           int postId = Integer.parseInt(request.getParameter("postId"));
           PostDao postDao = new PostDao();
           if(postDao.doesPostExist(postId)){
               Like like;
               HttpSession session = request.getSession(false);

               boolean isGuest = (session == null || session.getAttribute("sessionUser") == null);

               if(isGuest){
                   like = new Like(postId, null);
                   response.setStatus(HttpServletResponse.SC_ACCEPTED);
               } else {
                   SessionUserDTO sessionUser = (SessionUserDTO) session.getAttribute("sessionUser");
                   like = new Like(postId, sessionUser.getUserId());
                   response.setStatus(HttpServletResponse.SC_OK);
               }

               int likeId = likeDao.saveLike(like);

               if(likeId != 0){
                   responseJson.put("message", "Like submitted successfully");
                   responseJson.put("likeId", likeId);
                   response.getWriter().write(responseJson.toString());
               }
           } else {
               response.setStatus(HttpServletResponse.SC_NOT_FOUND);
               responseJson.put("message", "Post have been deleted");

               response.getWriter().write(responseJson.toString());
           }
       } catch (InputValidationException e){
           response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
           response.getWriter().write(responseJson.put("message", "postId should not be null or " +
                   "empty").toString());
           logger.error("error occur while saving like: {}", e.getMessage());
       } catch (Exception e){
           System.out.println("I got in server error");
           response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
           response.getWriter().write(responseJson.put("message",
                   "Internal server error occur while saving like").toString());
           logger.error("error occur while saving like: {}", e.getMessage(), e);
       }
    }
}
