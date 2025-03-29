package com.adb.yarn.dao.admindao;

import com.adb.yarn.exceptions.DAOException;
import com.adb.yarn.utils.CreateDbConnection;
import com.adb.yarn.utils.GetSqlStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.HashSet;

public class AdminDao {
    private static final Logger logger = LoggerFactory.getLogger(AdminDao.class);

    public HashSet<Integer> getAdminId() throws DAOException {
        HashSet<Integer> adminIdList = new HashSet<>();

        try(Connection connection = CreateDbConnection.getConnection()) {

            String sqlScriptPath = AdminSqlScriptsPath.GET_ADMIN_ID.getPath();
            String getAdminIdQuery = GetSqlStatement.sqlQueryBuilder(sqlScriptPath);

            try(Statement statement = connection.createStatement()){

                 try(ResultSet resultSet = statement.executeQuery(getAdminIdQuery)) {
                     while (resultSet.next()){
                         adminIdList.add(resultSet.getInt("user_id"));
                     }
                 }

            } catch (SQLException e){
                logger.error("SQL query execution error: " + e.getMessage(), e);
                throw new DAOException("sql execution error", e);
            }

        } catch (SQLException e) {
            logger.error("Fail to connect to database: " + e.getMessage(), e);
            throw new DAOException("Fail to connect to database", e);
        } catch (IOException e){
            logger.error("Failed to fetch get admin id sql query -- " + e.getMessage());
            throw new DAOException("Failed to fetch get admin id sql query", e);
        }

        return adminIdList;
    }
}
