package com.adb.chat_app.dao.userdao;

import com.adb.chat_app.exceptions.DAOException;
import com.adb.chat_app.models.User;
import com.adb.chat_app.utils.CreateDbConnection;
import com.adb.chat_app.utils.GetSqlStatement;
import com.adb.chat_app.utils.BuildSqlStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDao implements IUserDao{
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
    @Override
    public Optional<User> get(long ID) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() throws DAOException {
        return null;
    }

    @Override
    public int save(User user) throws DAOException {
        logger.info("connecting to database");
        int userID = 0;


        try(Connection connection = CreateDbConnection.getConnection())
        {
            logger.info("Database connected successfully");

            String insertUserQuery;
            String sqlScriptPath = UserSqlScriptsPath.INSERT_USER_SCRIPT.getPath();

            logger.info("fetching insert user sql query from " + sqlScriptPath);
            try {
                insertUserQuery = GetSqlStatement.sqlQueryBuilder(sqlScriptPath);
                logger.info("insert user sql query fetched successfully");
            } catch (IOException e) {
                logger.error("Failed to fetch insert user sql query from " + sqlScriptPath, e);
                throw new DAOException("Failed to fetch insert user sql query", e);
            }

            try(PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery))
            {

                BuildSqlStatement.setParameters(
                        preparedStatement,
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getFirstName(),
                        user.getLastName()
                );

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    userID = resultSet.getInt("id");
                }

            } catch (SQLException e){
                logger.error("SQL execution error: " + e.getMessage(), e);
                throw new DAOException("sql execution error", e);
            }

        } catch (SQLException e) {
            logger.error("Fail to connect to database: " + e.getMessage(), e);
            throw new DAOException("Fail to connect to database", e);
        }


        return userID;
    }

    @Override
    public int update(Object... params) throws DAOException {
        return 0;
    }

    @Override
    public int delete(long id) throws DAOException {
        return 0;
    }
}
