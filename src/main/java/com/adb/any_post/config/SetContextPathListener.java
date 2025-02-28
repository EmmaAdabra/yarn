package com.adb.any_post.config;

import com.adb.any_post.utils.GetContextPath;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SetContextPathListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String contextPath = sce.getServletContext().getContextPath();
        GetContextPath.setContextPath(contextPath);
    }
}

