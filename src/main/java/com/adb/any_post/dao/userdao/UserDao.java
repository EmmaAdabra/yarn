package com.adb.any_post.dao.userdao;

import com.adb.any_post.exceptions.DAOException;
import com.adb.any_post.models.User;
import com.adb.any_post.utils.CreateDbConnection;
import com.adb.any_post.utils.EntityModelMapper;
import com.adb.any_post.utils.GetSqlStatement;
import com.adb.any_post.utils.BuildSqlStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao implements IUserDao{
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
    @Override
    public Optional<User> get(long userId) throws DAOException {
        Optional<User> userOptional = Optional.empty();

        logger.info("connecting to database");
        try(Connection connection = CreateDbConnection.getConnection()){
            logger.info("Database connected successfully");

            String getUserByIdSqlQuery;
            String sqlScriptPath = UserSqlScriptsPath.GET_USER_BY_ID.getPath();

            logger.info("fetching get user by id sql query from " + sqlScriptPath);
            try{
                getUserByIdSqlQuery = GetSqlStatement.sqlQueryBuilder(sqlScriptPath);
                logger.info("get user by id sql query fetched successfully");
            }  catch (IOException e) {
                logger.error("Failed to fetch get user by id sql query from " + sqlScriptPath, e);
                throw new DAOException("Failed to fetch get user by id sql query", e);
            }

            try(PreparedStatement preparedStatement = connection.prepareStatement(getUserByIdSqlQuery)){
                preparedStatement.setInt(1, (int) userId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    userOptional = Optional.of(EntityModelMapper.userMapper(resultSet));
                }

                logger.info("get user by id query executed successfully");

            } catch (SQLException e){
                logger.error("SQL execution error: " + e.getMessage(), e);
                throw new DAOException("sql execution error", e);
            }

        } catch (SQLException e){
            logger.error("Fail to connect to database: " + e.getMessage(), e);
            throw new DAOException("Fail to connect to database", e);
        }

        return userOptional;
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
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPassword()
                );

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    userID = resultSet.getInt("id");
                }

                logger.info("insert user query executed successfully");

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
    public User findUserByEmail(String email) throws DAOException {
        User user = null;

        logger.info("connecting to database");
        try(Connection connection = CreateDbConnection.getConnection()){
            logger.info("Database connected successfully");

            String getUserByEmailSqlQuery;
            String sqlScriptPath = UserSqlScriptsPath.GET_USER_BY_EMAIL.getPath();

            logger.info("fetching get user by email sql query from " + sqlScriptPath);
            try{
                getUserByEmailSqlQuery = GetSqlStatement.sqlQueryBuilder(sqlScriptPath);
                logger.info("get user by email sql query fetched successfully");
            }  catch (IOException e) {
                logger.error("Failed to fetch get user by email sql query from " + sqlScriptPath, e);
                throw new DAOException("Failed to fetch get user by email sql query", e);
            }

            try(PreparedStatement preparedStatement = connection.prepareStatement(getUserByEmailSqlQuery)){
                preparedStatement.setString(1, email);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    user = EntityModelMapper.userMapper(resultSet);
                }

                logger.info("get user by email query executed successfully");

            } catch (SQLException e){
                logger.error("SQL execution error: " + e.getMessage(), e);
                throw new DAOException("sql execution error", e);
            }

        } catch (SQLException e){
            logger.error("Fail to connect to database: " + e.getMessage(), e);
            throw new DAOException("Fail to connect to database", e);
        }

        return user;
    }

    @Override
    public boolean saveUserPfp(long userId, Part imagePart) throws DAOException {
        logger.info("connecting to database");
        try(Connection connection = CreateDbConnection.getConnection()){
            logger.info("Database connected successfully");

            String insertPfpQuery;
            String sqlScriptPath = UserSqlScriptsPath.INSERT_USER_PFP.getPath();

            insertPfpQuery = GetSqlStatement.sqlQueryBuilder(sqlScriptPath);

            try(PreparedStatement preparedStatement = connection.prepareStatement(insertPfpQuery);
                InputStream fileContent =  imagePart.getInputStream()){
                preparedStatement.setBinaryStream(1, fileContent);
                preparedStatement.setInt(2, (int) userId);

                int rowInserted = preparedStatement.executeUpdate();

                if(rowInserted > 0){
                    return true;
                } else {
                    logger.warn("User with ID: {} not found pfp not save", userId);
                    return false;
                }
            } catch (SQLException e){
                logger.error("SQL execution error: " + e.getMessage(), e);
                throw new DAOException("sql execution error", e);
            }


        } catch (SQLException e) {
            logger.error("Fail to connect to database: " + e.getMessage(), e);
            throw new DAOException("Fail to connect to database", e);
        } catch (IOException e){
            logger.error("Failed to fetch get insert user pfp sql query -- " + e.getMessage());
            throw new DAOException("Failed to fetch get insert user pfp sql query", e);
        }
    }

    @Override
    public boolean hasUserPfp(long userID) throws DAOException {
        try(Connection connection = CreateDbConnection.getConnection()){
            String sqlScriptPath = UserSqlScriptsPath.HAS_USER_PFP.getPath();
            String hasUserPfpQuery = GetSqlStatement.sqlQueryBuilder(sqlScriptPath);

            try(PreparedStatement preparedStatement = connection.prepareStatement(hasUserPfpQuery)){
                preparedStatement.setInt(1, (int) userID);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {
                    return  resultSet.getBoolean(1);
                }
            }  catch (SQLException e){
                logger.error("SQL execution error: " + e.getMessage(), e);
                throw new DAOException("sql execution error", e);
            }

        } catch (SQLException e) {
        logger.error("Fail to connect to database: " + e.getMessage(), e);
        throw new DAOException("Fail to connect to database", e);
        } catch (IOException e){
            logger.error("Failed to fetch has user pfp sql query -- " + e.getMessage());
            throw new DAOException("Failed to fetch get insert user pfp sql query", e);
        }

        return false;
    }

    @Override
    public byte[] fetchUserPfp(long userId) throws DAOException {
        try(Connection connection = CreateDbConnection.getConnection()){

            String sqlScriptPath = UserSqlScriptsPath.FETCH_USER_PFP.getPath();
            String fetchUserPfp = GetSqlStatement.sqlQueryBuilder(sqlScriptPath);

            try(PreparedStatement preparedStatement = connection.prepareStatement(fetchUserPfp)
            ) {
                preparedStatement.setInt(1, (int) userId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    return resultSet.getBytes("profile_picture");
                }
            } catch (SQLException e){
                logger.error("SQL execution error: " + e.getMessage(), e);
                throw new DAOException("sql execution error", e);
            }

        } catch (SQLException e) {
            logger.error("Fail to connect to database: " + e.getMessage(), e);
            throw new DAOException("Fail to connect to database", e);
        } catch (IOException e){
            logger.error("Failed to get fetch user pfp sql query -- " + e.getMessage());
            throw new DAOException("Failed to fetch get insert user pfp sql query", e);
        }
        return new byte[0];
    }

    @Override
    public int updateUserBio(String bio, int userId) throws DAOException {
        int updatedRow;
        try(Connection connection = CreateDbConnection.getConnection()){
            String sqlScript = UserSqlScriptsPath.UPDATE_USER_BIO.getPath();
            String updateUserBioQuery = GetSqlStatement.sqlQueryBuilder(sqlScript);

            try(PreparedStatement preparedStatement = connection.prepareStatement(updateUserBioQuery)){
                BuildSqlStatement.setParameters(preparedStatement, bio, userId);
                updatedRow = preparedStatement.executeUpdate();
                logger.info("user bio: {} ", bio);
            } catch (SQLException e){
                logger.error("SQL query execution error: " + e.getMessage(), e);
                throw new DAOException("sql execution error", e);
            }
        } catch (SQLException e) {
            logger.error("Fail to connect to database: " + e.getMessage(), e);
            throw new DAOException("Fail to connect to database", e);
        } catch (IOException e){
            logger.error("Failed to fetch update user bio sql query -- " + e.getMessage());
            throw new DAOException("Failed to fetch get insert user pfp sql query", e);
        }

        return updatedRow;
    }

    public int updatePersonalData(String firstName, String lastName, String email, int userId) throws DAOException {
        int updatedRow = 0;

        logger.info("connecting to database");

        try(Connection connection = CreateDbConnection.getConnection())
        {
            logger.info("Database connected successfully");

            String updateUserPersonalDataQuery;
            String sqlScriptPath = UserSqlScriptsPath.UPDATE_USER_PERSONAL_DATA.getPath();

            updateUserPersonalDataQuery = GetSqlStatement.sqlQueryBuilder(sqlScriptPath);

            try(PreparedStatement preparedStatement = connection.prepareStatement(updateUserPersonalDataQuery))
            {

                BuildSqlStatement.setParameters(
                        preparedStatement,
                        firstName,
                        lastName,
                        email,
                        userId
                );

               updatedRow = preparedStatement.executeUpdate();

            } catch (SQLException e){
                logger.error("SQL execution error: " + e.getMessage(), e);
                throw new DAOException("sql execution error", e);
            }

        } catch (IOException e) {
            logger.error("Failed to fetch update user personal data sql query from", e);
            throw new DAOException("Failed to fetch update user personal data sql query from", e);
        } catch (SQLException e) {
            logger.error("Fail to connect to database: " + e.getMessage(), e);
            throw new DAOException("Fail to connect to database", e);
        }

        return updatedRow;
    }
}
