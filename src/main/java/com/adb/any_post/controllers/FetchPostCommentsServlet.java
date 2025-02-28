package com.adb.any_post.controllers;

import com.adb.any_post.dao.commentDao.CommentDao;
import com.adb.any_post.dto.CommentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "FetchPostCommentsServlet", value = "/fetch_post_comment")
public class FetchPostCommentsServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(FetchPostCommentsServlet.class);
    private final CommentDao commentDao = new CommentDao();
    String postId;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper responseMapper = new ObjectMapper();
        Map<String, Object> jsonResponse = new HashMap<>();
        List<CommentDto> comments = new ArrayList<>();

        try {
            postId = request.getParameter("postId");

            if(postId != null){
                int id = Integer.parseInt(postId);
                comments = commentDao.getPostComments(id);
                jsonResponse.put("message", "comments fetch successfully");
                jsonResponse.put("data", comments);
                response.getWriter().write(responseMapper.writeValueAsString(jsonResponse));

            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                jsonResponse.put("message", "postId is missing");
                response.getWriter().write(responseMapper.writeValueAsString(jsonResponse));
                logger.warn("Bad request, missing postId");
            }
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.put("message", "A server error occur while fetching comments");
            response.getWriter().write(responseMapper.writeValueAsString(jsonResponse));
            logger.error("An internal error occur while fetching comments for post with id {}\n{}", postId, e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
