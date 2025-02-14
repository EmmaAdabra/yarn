package com.adb.chat_app.filters;

import com.adb.chat_app.utils.WebPagePaths;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/dashboard"})
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpSession session = request.getSession(false);

        if(session != null && session.getAttribute("sessionUser") != null){
            filterChain.doFilter(request, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/login");
        }
    }
}
