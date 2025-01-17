package com.adb.chat_app.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetSqlStatement {
    public static String sqlQueryBuilder(String filePath) throws IOException {

//        Method 1
        Path sqlScriptPath = Paths.get(filePath);
        String sqlStatement = Files.readString(sqlScriptPath);

        sqlStatement.replace("\\s", " ");

//        Method 2
//        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
//
//            StringBuilder sqlQuery = new StringBuilder();
//
//            String line;
//
//            while ((line = bufferedReader.readLine()) != null){
//                if(line.trim().startsWith("--")){
//                    continue;
//                }
//
//                sqlQuery.append(line).append(" ");
//            }
//        }

        return sqlStatement;
    }
}
