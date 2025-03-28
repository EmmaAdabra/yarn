/**
 * The `UploadPfpServlet` class in a Java web application handles the uploading of user profile
 * pictures with validation and error handling.
 */
package com.adb.yarn.controllers;

import com.adb.yarn.dao.userdao.UserDao;
import com.adb.yarn.dto.SessionUserDTO;
import com.adb.yarn.exceptions.InputValidationException;
import com.adb.yarn.services.UserService;
import com.adb.yarn.utils.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UploadPfpServlet", value = "/uploadPfp")
@MultipartConfig(maxFileSize = 500 * 1024)
public class UploadPfpServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UploadPfpServlet.class);
    private static final UserService userService = new UserService(new UserDao());
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
//        set response to JSON
        JSONObject jsonResponse = new JSONObject();
        HttpSession session = request.getSession(false);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if(session != null && session.getAttribute("sessionUser") != null){
            System.out.println("I violate test");
            SessionUserDTO sessionUser = (SessionUserDTO) session.getAttribute("sessionUser");
            try {
                Part filePart = request.getPart("image"); // get uploaded image
                ValidateInputs.validatePfpUpload(filePart);

                Response<Object> serviceResponse = userService.uploadUserPfp(sessionUser.getUserId(), filePart);
                if(serviceResponse.getStatus() == ResponseCode.RESOURCE_CREATED.getCode()){
                    sessionUser.setHasPfp(true);
                    sessionUser.setPfpUrl(UserUtil.getUserPfpUrl(sessionUser.getUserId()));
                    session.setAttribute("sessionUser", sessionUser);
                }

                jsonResponse.put("status", String.valueOf(serviceResponse.getStatus()));
                jsonResponse.put("message", serviceResponse.getMessage());
                response.getWriter().write(jsonResponse.toString());

            } catch (InputValidationException e){
                logger.error(e.getMessage());
                jsonResponse.put("status", String.valueOf(ResponseCode.VALIDATION_ERROR));
                jsonResponse.put("message", e.getMessage());
                response.getWriter().write(jsonResponse.toString());
            } catch (Exception e){
                jsonResponse.put("status", ResponseCode.INTERNAL_SERVER_ERROR.getCode());
                jsonResponse.put("message", "Internal server error");
                response.getWriter().write(jsonResponse.toString());
                logger.error(e.getMessage(), e);
            }
        } else {
            jsonResponse.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            jsonResponse.put("message", "Session Expired, refresh and login");
            response.getWriter().write(jsonResponse.toString());
        }
    }
}