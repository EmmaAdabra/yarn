/**
 * The `HomeServlet` class in a Java web application fetches posts from a database using `PostDao` and
 * forwards the data to a JSP file for display.
 */
package com.adb.yarn.controllers;

import com.adb.yarn.dao.postDao.PostDao;
import com.adb.yarn.dto.PostDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(HomeServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PostDao postDao = new PostDao();
        List<PostDto> allPost = new ArrayList<>(); // Initialize to avoid null pointer

        // Fetch posts
        try {
            allPost = postDao.getAllPost();
        } catch (Exception e) {
            logger.error("Failed to fetch posts -- {}", e.getMessage());
            // Optionally, you can set an error message as a request attribute
            request.setAttribute("error", "Failed to fetch posts. Please try again later.");
        }

        // Set the posts attribute
        request.setAttribute("posts", allPost);

        // Forward to the JSP
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

}
