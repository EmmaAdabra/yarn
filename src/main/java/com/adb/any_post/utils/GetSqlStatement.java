package com.adb.any_post.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class GetSqlStatement {
    public static String sqlQueryBuilder(String filePath) throws IOException {
        InputStream sqlScriptStream = GetSqlStatement.class.getClassLoader().getResourceAsStream(filePath);
        StringBuilder sqlQuery = new StringBuilder();

        if(sqlScriptStream == null){
            throw new IOException("file not found: " + filePath);
        }

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sqlScriptStream, StandardCharsets.UTF_8))){
            String line;

            while ((line = bufferedReader.readLine()) != null){
                if(line.trim().startsWith("--")){
                    continue;
                }

                sqlQuery.append(line).append(" ");
            }
        }

        return sqlQuery.toString();
    }
}
