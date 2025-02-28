package com.adb.any_post.utils;

import javax.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;

public class PostUtils {
    public static String generateFileName(Part filePart){
        return System.currentTimeMillis() + "_" +  Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
    }

    public static String getUploadDir(String fileName, String uploadDir) {
        return uploadDir + File.separator + fileName;
    }
}


