/**
 * The `EditProfileServlet` class in the `com.adb.any_post.controllers` package handles GET requests to
 * display the user profile editing page.
 */
package com.adb.yarn.controllers;

import com.adb.yarn.utils.WebPagePaths;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditProfileServlet", value = "/edit_profile")
public class EditProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(WebPagePaths.EDIT_USER_PROFILE.getPagePath());
        dispatcher.forward(request, response);
    }
}
