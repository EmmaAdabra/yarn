/**
 * The UpdateUserBioServlet class in a Java web application handles updating user bio message
 * validation and error handling.
 */
package com.adb.yarn.controllers;

import com.adb.yarn.dao.userdao.UserDao;
import com.adb.yarn.dto.SessionUserDTO;
import com.adb.yarn.exceptions.InputValidationException;
import com.adb.yarn.exceptions.UnknownException;
import com.adb.yarn.services.UserService;
import com.adb.yarn.utils.Response;
import com.adb.yarn.utils.ResponseCode;
import com.adb.yarn.utils.ValidateInputs;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UpdateBioServlet", value = "/updateBio")
public class UpdateUserBioServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UpdateUserBioServlet.class);
    private static final UserService userService = new UserService(new UserDao());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        JSONObject jsonResponse = new JSONObject();

        if(session != null && session.getAttribute("sessionUser") != null){
            SessionUserDTO sessionUser = (SessionUserDTO) session.getAttribute("sessionUser");
            String userBio = request.getParameter("userBio");
            Response<Integer> serviceResponse;

            try {
                if(userBio == null || userBio.isEmpty()){
                    userBio = "";
                } else {
                    ValidateInputs.validateUserBio(userBio);
                }
                serviceResponse = userService.addBio(userBio, sessionUser.getUserId());
                if(serviceResponse.getStatus() == ResponseCode.RESOURCE_CREATED.getCode()){
                    sessionUser.setBio(userBio);
                    session.setAttribute("sessionUser", sessionUser);
                }

                jsonResponse.put("status", serviceResponse.getStatus());
                jsonResponse.put("message", serviceResponse.getMessage());
                response.getWriter().write(jsonResponse.toString());

            } catch (UnknownException e) {
                response.getWriter().write(e.getMessage());
                logger.error("An unknown error -- {}",e.getMessage(), e);
            } catch (InputValidationException e) {
                jsonResponse.put("status", ResponseCode.VALIDATION_ERROR.getCode());
                jsonResponse.put("message", e.getMessage());
                response.getWriter().write(jsonResponse.toString());
                logger.error(e.getMessage());
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
