package com.adb.chat_app.controllers;

import com.adb.chat_app.dao.userdao.UserDao;
import com.adb.chat_app.dto.SessionUserDTO;
import com.adb.chat_app.exceptions.InputValidationException;
import com.adb.chat_app.services.UserService;
import com.adb.chat_app.utils.*;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

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

                Response<Object> serviceResponse = userService.uploadUserPfp(sessionUser.getUserID(), filePart);
                sessionUser.setHasPfp(true);
                sessionUser.setPfpUrl(UserUtil.getUserPfpUrl(sessionUser.getUserID()));

                session.setAttribute("sessionUser", sessionUser);
                jsonResponse.put("status", String.valueOf(serviceResponse.getStatus()));
                jsonResponse.put("message", serviceResponse.getMessage());
                response.getWriter().write(jsonResponse.toString());

            } catch (InputValidationException e){
                logger.error(e.getMessage());
                jsonResponse.put("status", String.valueOf(ResponseCode.VALIDATION_ERROR));
                jsonResponse.put("message", e.getMessage());
                response.getWriter().write(jsonResponse.toString());
            } catch (Exception e){
                logger.error(e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            jsonResponse.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            jsonResponse.put("message", "Session Expired, refresh and login");
            response.getWriter().write(jsonResponse.toString());
        }
    }
}