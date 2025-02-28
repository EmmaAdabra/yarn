package com.adb.any_post.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/dashboard", "/edit_profile"})
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpSession session = request.getSession(false);

        if(session != null && session.getAttribute("sessionUser") != null){
            filterChain.doFilter(request, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/");
        }
    }
}
