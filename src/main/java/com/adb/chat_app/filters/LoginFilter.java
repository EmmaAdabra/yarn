package com.adb.chat_app.filters;

import com.adb.chat_app.dao.userdao.UserDao;
import com.adb.chat_app.exceptions.InputValidationException;
import com.adb.chat_app.exceptions.UnknownException;
import com.adb.chat_app.models.User;
import com.adb.chat_app.services.UserService;
import com.adb.chat_app.utils.*;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/login")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if("POST".equalsIgnoreCase(request.getMethod())){
            try {

                String email = ValidateUserInputs.verifyEmail(request.getParameter("email"));

                if(!UserUtil.verifyIsUser(email)) {
                    throw new InputValidationException("No user found with this email - " + email);
                }

                ValidateUserInputs.verifyPassword(request.getParameter("password"));

                filterChain.doFilter(request, response);

            }  catch (InputValidationException e) {
                request.setAttribute("error", e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher(WebPagePaths.LOGIN.getPagePath());
                dispatcher.forward(request, response);

            } catch (UnknownException e) {
                GlobalErrorHandler.handleError(e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal error occur");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
