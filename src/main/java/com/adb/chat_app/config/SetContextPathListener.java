package com.adb.chat_app.config;

import com.adb.chat_app.utils.GetContextPath;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SetContextPathListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String contextPath = sce.getServletContext().getContextPath();
        GetContextPath.setContextPath(contextPath);
    }
}

