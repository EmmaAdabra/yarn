package com.adb.chat_app.filters;

import com.adb.chat_app.exceptions.InputValidationException;
import com.adb.chat_app.utils.ValidateUserInputs;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/register")
public class RegistrationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String  password, confirmPassword;

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if("POST".equalsIgnoreCase(request.getMethod())) {
            try{
                ValidateUserInputs.verifyName(request.getParameter("firstName"));
                ValidateUserInputs.verifyName(request.getParameter("lastName"));
                ValidateUserInputs.verifyUsername(request.getParameter("username"));
                ValidateUserInputs.verifyEmail(request.getParameter("email"));
                password = ValidateUserInputs.verifyPassword(request.getParameter("password")).trim();
                confirmPassword = request.getParameter("confirmPassword").trim();
                ValidateUserInputs.verifyConfirmPassword(password, confirmPassword);

                filterChain.doFilter(request, response);

            } catch (InputValidationException e){
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
