package com.adb.any_post.controllers;

import com.adb.any_post.dao.userdao.UserDao;
import com.adb.any_post.dto.SessionUserDTO;
import com.adb.any_post.exceptions.InputValidationException;
import com.adb.any_post.models.User;
import com.adb.any_post.utils.ResponseCode;
import com.adb.any_post.utils.StringUtils;
import com.adb.any_post.utils.ValidateInputs;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UpdatePersonalDataServlet", value = "/editPersonalData")
public class UpdatePersonalDataServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UpdatePersonalDataServlet.class);
    private static final  UserDao userDao = new UserDao();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        JSONObject jsonResponse = new JSONObject();

        if(session != null && session.getAttribute("sessionUser") != null){
            SessionUserDTO sessionUser = (SessionUserDTO) session.getAttribute("sessionUser");

            try {
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String email = request.getParameter("email");

                ValidateInputs.validateQueryParam(firstName, lastName, email);

                ValidateInputs.verifyName(firstName);
                ValidateInputs.verifyName(lastName);
                ValidateInputs.verifyEmail(email);

                System.out.println(firstName);
                System.out.println(lastName);
                System.out.println(email);

                //    verify if user email already exist
                User user = userDao.findUserByEmail(email);
                if(user == null ||  user.getId() == sessionUser.getUserId()){
                    int updatedRow = userDao.updatePersonalData(firstName,
                            lastName, email, sessionUser.getUserId());
                    if(updatedRow > 0){
                        sessionUser.setfName(firstName);
                        sessionUser.setlName(lastName);
                        sessionUser.setEmail(email);
                        session.setAttribute("sessionUser", sessionUser);

                        jsonResponse.put("status", HttpServletResponse.SC_CREATED);
                        jsonResponse.put("message", "Data updated successfully");
                        response.getWriter().write(jsonResponse.toString());
                    } else {
                        jsonResponse.put("status", HttpServletResponse.SC_NOT_FOUND);
                        jsonResponse.put("message", "User not found");
                        response.getWriter().write(jsonResponse.toString());
                    }

                } else {
                    jsonResponse.put("status", HttpServletResponse.SC_CONFLICT);
                    jsonResponse.put("message", "email: " + StringUtils.maskEmail(email) + " " +
                            "already exist");
                    response.getWriter().write(jsonResponse.toString());
                    logger.error("exist, user personal data update failed");
                }
            } catch (InputValidationException e){
                jsonResponse.put("status", ResponseCode.VALIDATION_ERROR.getCode());
                jsonResponse.put("message", e.getMessage());
                response.getWriter().write(jsonResponse.toString());
                logger.error("validation error: {}", e.getMessage());
            }

            catch (Exception e){
                jsonResponse.put("status", ResponseCode.INTERNAL_SERVER_ERROR.getCode());
                jsonResponse.put("message", "Internal server error");
                response.getWriter().write(jsonResponse.toString());
                logger.error("An unknown error occur while updating user personal data: {}",
                        e.getMessage(), e);
            }
        } else {
            jsonResponse.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            jsonResponse.put("message", "Session Expired, refresh and login");
            response.getWriter().write(jsonResponse.toString());
            logger.warn("User session expired, can't update user personal data");
        }
    }
}
