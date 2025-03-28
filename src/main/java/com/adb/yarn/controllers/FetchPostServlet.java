package com.adb.yarn.controllers;

import com.adb.yarn.dao.postDao.PostDao;
import com.adb.yarn.dto.PostDto;
import com.adb.yarn.exceptions.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "FetchPostServlet", value = "/fetch_post")
public class FetchPostServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(FetchPostServlet.class);
    PostDao postDao = new PostDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        List<PostDto> allPost;

        try {
            allPost = postDao.getAllPost();
            request.setAttribute("posts", allPost);
        } catch (DAOException e) {
            logger.error("Failed to fetch posts -- {}", e.getMessage());
        }

        catch (Exception e){
            logger.error("Unknown error occur while fetching post --- {}", e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
