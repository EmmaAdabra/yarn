package com.adb.chat_app.filters;

import com.adb.chat_app.exceptions.InputValidationException;
import com.adb.chat_app.utils.*;
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
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
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
                String email = ValidateInputs.verifyEmail(request.getParameter("email"));

                if(UserUtil.verifyIsUser(email)) {
                    throw new InputValidationException("User with email - " + email + " already exist");
                }

                ValidateInputs.verifyName(request.getParameter("firstName"));
                ValidateInputs.verifyName(request.getParameter("lastName"));
                ValidateInputs.verifyUsername(request.getParameter("username"));
                password = ValidateInputs.verifyPassword(request.getParameter("password")).trim();
                confirmPassword = request.getParameter("confirmPassword").trim();
                ValidateInputs.verifyConfirmPassword(password, confirmPassword);

//                send request to the appropriate servlet
                filterChain.doFilter(request, response);

            } catch (InputValidationException e){
                logger.error(e.getMessage());
                request.setAttribute("error", e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher(WebPagePaths.REGISTER.getPagePath());
                dispatcher.forward(request, response);
            } catch (Exception e){
                GlobalErrorHandler.handleError(e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal sever error occur");
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
