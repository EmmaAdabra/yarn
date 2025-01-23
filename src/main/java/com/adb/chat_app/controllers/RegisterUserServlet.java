package com.adb.chat_app.controllers;

import com.adb.chat_app.dao.userdao.UserDao;
import com.adb.chat_app.models.User;
import com.adb.chat_app.services.UserService;
import com.adb.chat_app.utils.GlobalErrorHandler;
import com.adb.chat_app.utils.Response;
import com.adb.chat_app.utils.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RegisterUserServlet", value = "/register")
public class RegisterUserServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(RegisterUserServlet.class);
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/registerUser.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String firstName, lastName, username, email, password;

        try{
            firstName = request.getParameter("firstName");
            lastName = request.getParameter("lastName");
            username = request.getParameter("username");
            email = request.getParameter("email");
            password = request.getParameter("password");

            User user = new User(firstName, lastName, username, email, password);

            Response<Integer> serviceResponse  = userService.saveUser(user);

            if(serviceResponse.getStatus_code() == ResponseCode.RESOURCE_CREATED.getCode()){
                System.out.println("Account created successfully");
            }

            if(serviceResponse.getStatus_code() == ResponseCode.INTERNAL_SERVER_ERROR.getCode()){
                System.out.println("Fail to create Account");
            }
        } catch (Exception e){
            GlobalErrorHandler.handleError(e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error occur");
        }
    }
}
