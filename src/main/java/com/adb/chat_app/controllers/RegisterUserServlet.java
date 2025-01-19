package com.adb.chat_app.controllers;

import com.adb.chat_app.dao.userdao.IUserDao;
import com.adb.chat_app.dao.userdao.UserDao;
import com.adb.chat_app.exceptions.DAOException;
import com.adb.chat_app.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RegisterUserServlet", value = "/register")
public class RegisterUserServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(RegisterUserServlet.class);
    private IUserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
