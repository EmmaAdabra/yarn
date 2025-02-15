package com.adb.chat_app.controllers;

import com.adb.chat_app.dao.postDao.PostDao;
import com.adb.chat_app.dto.SessionUserDTO;
import com.adb.chat_app.exceptions.InputValidationException;
import com.adb.chat_app.models.Post;
import com.adb.chat_app.utils.PostUtils;
import com.adb.chat_app.utils.ResponseCode;
import com.adb.chat_app.utils.ValidateInputs;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "PostServlet", value = "/submit_post")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB threshold before writing to disk
        maxFileSize = 10 * 1024 * 1024,  // Max file size 10MB
        maxRequestSize = 15 * 1024 * 1024 // Max request size 15MB
)
public class SubmitPostServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(SubmitPostServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonResponse = new JSONObject();
        PostDao postDao = new PostDao(); // temp use

        try {
            HttpSession session = request.getSession(false);

            if(session != null && session.getAttribute("sessionUser") != null){
                SessionUserDTO sessionUser = (SessionUserDTO) session.getAttribute("sessionUser");
                int userId = (int) sessionUser.getUserID();

                String postContent = request.getParameter("postContent");
                String postTitle = request.getParameter("postTitle");
                Part imagePart = request.getPart("postImage");
                String imagePath = null;

                if(imagePart != null && imagePart.getSize() > 0) {
                    ValidateInputs.validatePostImage(imagePart); // throws InputValidationException

                    String fileName = PostUtils.generateFileName(imagePart);
                    String uploadDir = getServletContext().getRealPath("/uploads");
                    logger.info("post upload dir: {}", uploadDir);

                    File uploadFolder = new File(uploadDir);
                    // Ensure the upload folder exists
                    if (!uploadFolder.exists()) uploadFolder.mkdirs();

                    // Save the image file
//                    File file = new File(uploadFolder, fileName);
//                    imagePart.write(file.getAbsolutePath());

                    // Write file to the upload folder
                    String fullImagePath = uploadDir + File.separator + fileName;
                    imagePart.write(fullImagePath);

                    logger.info("image save to --- {}", uploadDir + File.separator + fileName);

                    // Store only the relative path
                    imagePath = "uploads/" + fileName;
                }

                if(imagePart != null || (postContent != null || postContent.trim().isEmpty())){
                    Post post = new Post (userId, postTitle, postContent, imagePath);

                    postDao.save(post);
                    jsonResponse.put("status", ResponseCode.RESOURCE_CREATED.getCode());
                    jsonResponse.put("message", "Post created successfully");
                    response.getWriter().write(jsonResponse.toString());
                } else {
                    jsonResponse.put("status", HttpServletResponse.SC_BAD_REQUEST);
                    jsonResponse.put("message", "Post can't be empty");
                    response.getWriter().write(jsonResponse.toString());
                }


            } else {
                jsonResponse.put("status", HttpServletResponse.SC_UNAUTHORIZED);
                jsonResponse.put("message", "Session Expired, refresh and login");
                response.getWriter().write(jsonResponse.toString());
            }
        } catch (InputValidationException e){
            logger.error(e.getMessage());
            jsonResponse.put("status", String.valueOf(HttpServletResponse.SC_BAD_REQUEST));
            jsonResponse.put("message", e.getMessage());
            response.getWriter().write(jsonResponse.toString());
        }
        catch (Exception e){
            jsonResponse.put("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.put("message", "Internal server error, fail to create post");
            response.getWriter().write(jsonResponse.toString());
            logger.error("An error occur while saving post to db -- {}", e.getMessage(), e);
        }
    }
}
