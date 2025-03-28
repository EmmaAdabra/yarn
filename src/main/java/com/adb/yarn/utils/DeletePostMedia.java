package com.adb.yarn.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeletePostMedia {
    private static final Logger logger = LoggerFactory.getLogger(DeletePostMedia.class);
    public static void deletePostMedia(ServletContext context, String filePath) {
        String fullFilePath = "";
        String uploadDir = System.getenv("UPLOAD_DIR");

        if(uploadDir == null || uploadDir.isEmpty()) {
            fullFilePath = context.getRealPath(filePath);
        } else {
            fullFilePath = uploadDir + Paths.get(fullFilePath).getFileName();
        }

        Path postMedia = Paths.get(fullFilePath);

        try{
            if(Files.deleteIfExists(postMedia)){
                logger.info("Post media upload,deleted successfully - {}", filePath);
            }

        } catch (Exception e){
            logger.error("Couldn't delete post media upload - {}", filePath, e);
        }
    }
}
