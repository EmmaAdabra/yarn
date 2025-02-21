package com.adb.chat_app.controllers;

import com.adb.chat_app.dao.postDao.PostDao;
import com.adb.chat_app.dto.PostDto;
import com.adb.chat_app.utils.WebPagePaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(HomeServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PostDao postDao = new PostDao();
        List<PostDto> allPost;

//        fetch posts
        try {
            allPost = postDao.getAllPost();
            request.setAttribute("posts", allPost);
        } catch (Exception e) {
            logger.error("Failed to fetch posts -- {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
