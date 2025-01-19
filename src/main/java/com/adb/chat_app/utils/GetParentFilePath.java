package com.adb.chat_app.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GetParentFilePath {
    public static String path(String filePath){
        Path path = Paths.get(filePath);

        return path.getParent().toString();
    }
}
