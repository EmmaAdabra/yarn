package com.adb.any_post.controllers;

import com.adb.any_post.dao.postDao.PostDao;
import com.adb.any_post.dao.userdao.UserDao;
import com.adb.any_post.dto.PostDto;
import com.adb.any_post.exceptions.DAOException;
import com.adb.any_post.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DashboardServlet", value = "/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
    private static final UserService userService = new UserService(new UserDao());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            PostDao postDao = new PostDao();
            List<PostDto> allPost;

//        fetch posts
        try {
            allPost = postDao.getAllPost();
            request.setAttribute("posts", allPost);
        } catch (DAOException e) {
            logger.error("Failed to fetch posts -- {}", e.getMessage());
        }

//        temporary use only (for development)
//        try {
//            Response<User> serviceResponse = userService.validateUser("emma@gmail.com", "1234");
//            if(serviceResponse.getStatus() == ResponseCode.SUCCESS.getCode()){
//                Response<SessionUserDTO> sessionUserRes = userService.createSessionUser(serviceResponse.getData());
//                SessionUserDTO sessionUser = sessionUserRes.getData();
////                add pfp url
//                if(sessionUser.isHasPfp()){
//                    String pfpUrl = request.getContextPath() + "/fetchPfp?id=" + sessionUser.getUserID();
//                    sessionUser.setPfpUrl(pfpUrl);
//                    System.out.println("pfp url: " + sessionUser.getPfpUrl());
//                }
////                creating session
//                HttpSession session = request.getSession();
//                session.setAttribute("sessionUser", sessionUser);
//
//
////
//
//                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
//                dispatcher.forward(request, response);
//            }
//
//        } catch (Exception e) {
//            GlobalErrorHandler.handleError(e);
//            logger.error(e.getMessage(), e);
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
//        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
