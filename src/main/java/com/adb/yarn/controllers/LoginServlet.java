/**
 * The `LoginServlet` class handles user login functionality.
 */
package com.adb.yarn.controllers;

import com.adb.yarn.dao.userdao.UserDao;
import com.adb.yarn.dto.SessionUserDTO;
import com.adb.yarn.models.User;
import com.adb.yarn.services.UserService;
import com.adb.yarn.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private static final UserService userService = new UserService(new UserDao());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            RequestDispatcher dispatcher = request.getRequestDispatcher(WebPagePaths.LOGIN.getPagePath());
            dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        String email = StringUtils.trimToNull(request.getParameter("email"));



        try {
            Response<User> serviceResponse = userService.validateUser(email, password);

//            get session user
            if(serviceResponse.getStatus() == ResponseCode.SUCCESS.getCode()){
                Response<SessionUserDTO> sessionUserRes = userService.createSessionUser(serviceResponse.getData());
                SessionUserDTO sessionUser = sessionUserRes.getData();

//                add pfp url
                if(sessionUser.isHasPfp()){
                    String pfpUrl = UserUtil.getUserPfpUrl(sessionUser.getUserId());
                    sessionUser.setPfpUrl(pfpUrl);
                }

//                creating session
                HttpSession session = request.getSession();
                session.setAttribute("sessionUser", sessionUser);

                logger.info("login successfully, userID - {}", serviceResponse.getData().getId());
                response.sendRedirect("/dashboard");
            } else if(serviceResponse.getStatus() == ResponseCode.VALIDATION_ERROR.getCode()){
                logger.error("failed login, user email - {}", email);

                request.setAttribute("error", serviceResponse.getMessage());

                RequestDispatcher dispatcher = request.getRequestDispatcher(WebPagePaths.LOGIN.getPagePath());
                dispatcher.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            }
        } catch (Exception e) {
            GlobalErrorHandler.handleError(e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
