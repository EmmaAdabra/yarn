package com.adb.any_post.controllers;

import com.adb.any_post.dao.likesDao.LikeDao;
import com.adb.any_post.dao.postDao.PostDao;
import com.adb.any_post.dao.userdao.UserDao;
import com.adb.any_post.dto.LikedPost;
import com.adb.any_post.dto.PostDto;
import com.adb.any_post.dto.SessionUserDTO;
import com.adb.any_post.exceptions.DAOException;
import com.adb.any_post.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DashboardServlet", value = "/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
    private static final UserService userService = new UserService(new UserDao());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PostDao postDao = new PostDao();
        LikeDao likeDao = new LikeDao();
        List<PostDto> allPost;
        Map<Integer, LikedPost> userLikedPostsMap;

        HttpSession session = request.getSession(false);

        if(session != null && session.getAttribute("sessionUser") != null){
            SessionUserDTO sessionUser = (SessionUserDTO) session.getAttribute("sessionUser");

            //  fetch posts
            try {
                allPost = postDao.getAllPost();
                request.setAttribute("posts", allPost);

                userLikedPostsMap = likeDao.getUserLikedPosts(sessionUser.getUserId());
                request.setAttribute("likedPostsMap", userLikedPostsMap);
            } catch (DAOException e) {
                request.setAttribute("FetchPostError", true);
                logger.error("Failed to fetch posts -- {}", e.getMessage());
            } catch (Exception e){
                logger.error("An unknown error occur while fetching post");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Login, session expired");
            logger.error("Failed to load dashboard, user session expired");
        }
    }
}
