package com.adb.chat_app.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogDirectoryListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String webAppDir = sce.getServletContext().getRealPath("/");

        // Now, get the parent directory of the webAppDir, which is the root of the project
        Path projectDir = Paths.get(webAppDir).getParent().getParent();
        Path logsDir = projectDir.resolve("logs");
        Path absolutePath = logsDir.toAbsolutePath();

        // Set the log directory path as a system property
        System.setProperty("logDirPath", absolutePath.toString());

        System.out.println("Log directory set to: " + absolutePath);
    }
}
