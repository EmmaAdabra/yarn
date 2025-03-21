/**
 * The `AuthenticationServlet` class is a servlet in a Java web application that forwards requests to
 * the "/home" page when handling a GET request.
 */
package com.adb.any_post.controllers;

import com.adb.any_post.utils.WebPagePaths;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AuthenticationServlet", value = "/authenticateUser")
public class AuthenticationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/home");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
