/**
 * The `RegisterUserServlet` class in Java handles user registration requests by saving user data and
 * displaying appropriate responses.
 */
package com.adb.yarn.controllers;

import com.adb.yarn.dao.userdao.UserDao;
import com.adb.yarn.models.User;
import com.adb.yarn.services.UserService;
import com.adb.yarn.utils.GlobalErrorHandler;
import com.adb.yarn.utils.Response;
import com.adb.yarn.utils.ResponseCode;
import com.adb.yarn.utils.WebPagePaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RegisterUserServlet", value = "/register")
public class RegisterUserServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RegisterUserServlet.class);
    private UserService userService;
    @Override
    public void init() throws ServletException {
        userService = new UserService(new UserDao());
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        RequestDispatcher dispatcher = request.getRequestDispatcher(WebPagePaths.REGISTER.getPagePath());
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String firstName, lastName, email, password;

        try{
            firstName = request.getParameter("firstName").trim();
            lastName = request.getParameter("lastName").trim();
            email = request.getParameter("email").trim();
            password = request.getParameter("password");

            User user = new User(firstName, lastName, email, password);

            Response<Integer> serviceResponse  = userService.saveUser(user);

            if(serviceResponse.getStatus() == ResponseCode.RESOURCE_CREATED.getCode()){
                logger.info("Account created successfully. UserID - {}", serviceResponse.getData());
                response.sendRedirect("/login");
            }

            if(serviceResponse.getStatus() == ResponseCode.INTERNAL_SERVER_ERROR.getCode()){
                System.out.println("Fail to create Account");
            }
        } catch (Exception e){
            GlobalErrorHandler.handleError(e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error occur");
        }
    }
}
