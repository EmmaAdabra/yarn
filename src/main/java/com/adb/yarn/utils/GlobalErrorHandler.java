package com.adb.yarn.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalErrorHandler.class);

    public static void handleError(Throwable t){
        logger.error("Unknown error occurred - " + t.getMessage(), t);
    }
}
