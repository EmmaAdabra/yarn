package com.adb.yarn.config;

import com.adb.yarn.cache.AdminCache;

import javax.servlet.*;
import javax.servlet.annotation.*;


@WebListener
public class AdminConfigListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // fetch admins IDs from database and cache them
        AdminCache.loadAdminId();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
