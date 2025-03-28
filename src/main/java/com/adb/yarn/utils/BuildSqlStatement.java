package com.adb.yarn.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BuildSqlStatement {
    public static PreparedStatement setParameters(PreparedStatement ps, Object ... params) throws SQLException {
        for(int i = 0; i < params.length; i++){
            ps.setObject(i + 1, params[i]);
        }

        return ps;
    }
}
