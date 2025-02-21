package com.adb.chat_app.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class RootFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        String path = request.getRequestURI().replaceAll("/+$", "");
        String path = request.getRequestURI().replaceAll("[/]+$", "");

        if(path.equals("") || path.equals(request.getContextPath())){
            request.getRequestDispatcher("/home").forward(request, servletResponse);
        } else {
            filterChain.doFilter(request, servletResponse);
        }
    }
}
