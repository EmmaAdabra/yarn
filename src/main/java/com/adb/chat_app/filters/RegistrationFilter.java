package com.adb.chat_app.filters;

import com.adb.chat_app.dao.userdao.UserDao;
import com.adb.chat_app.exceptions.InputValidationException;
import com.adb.chat_app.models.User;
import com.adb.chat_app.services.UserService;
import com.adb.chat_app.utils.Response;
import com.adb.chat_app.utils.ResponseCode;
import com.adb.chat_app.utils.ValidateUserInputs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/register")
public class RegistrationFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationFilter.class);
    private UserService userService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        userService = new UserService(new UserDao());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException
    {
        String  password, confirmPassword;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if("POST".equalsIgnoreCase(request.getMethod())) {
            try{

//                Verify if user already exist
                String email = ValidateUserInputs.verifyEmail(request.getParameter("email"));
                Response<User> serviceResponse = userService.getUserByEmail(email);

                if(serviceResponse.getStatus_code() == ResponseCode.SUCCESS.getCode()) {
                    throw new InputValidationException("User with email - " + email + " already exist");
                }
//                proceed to verify other data if user do not exist
                ValidateUserInputs.verifyName(request.getParameter("firstName"));
                ValidateUserInputs.verifyName(request.getParameter("lastName"));
                ValidateUserInputs.verifyUsername(request.getParameter("username"));
                password = ValidateUserInputs.verifyPassword(request.getParameter("password")).trim();
                confirmPassword = request.getParameter("confirmPassword").trim();
                ValidateUserInputs.verifyConfirmPassword(password, confirmPassword);

//                send request to the appropriate servlet
                filterChain.doFilter(request, response);

            } catch (InputValidationException e){
                logger.error(e.getMessage());
                request.setAttribute("error", e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/registerUser.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
