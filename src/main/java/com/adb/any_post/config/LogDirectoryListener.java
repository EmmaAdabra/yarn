package com.adb.any_post.config;

import com.adb.any_post.utils.GlobalErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogDirectoryListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(LogDirectoryListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String webAppDir = sce.getServletContext().getRealPath("/");

        try{
            // Now, get the parent directory of the webAppDir, which is the root of the project
            Path projectDir = Paths.get(webAppDir).getParent().getParent();
            Path logsDir = projectDir.resolve("logs");
            Path absolutePath = logsDir.toAbsolutePath();

            // Set the log directory path as a system property
            System.setProperty("logDirPath", absolutePath.toString());

            logger.info("Log directory set to: " + absolutePath);
        } catch (Exception e){
            logger.warn("Could not set log directory");
            GlobalErrorHandler.handleError(e);
        }

    }
}
