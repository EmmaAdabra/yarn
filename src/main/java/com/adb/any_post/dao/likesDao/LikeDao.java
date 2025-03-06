package com.adb.any_post.dao.likesDao;

import com.adb.any_post.dao.commentDao.CommentSqlScriptsPath;
import com.adb.any_post.exceptions.DAOException;
import com.adb.any_post.models.Like;
import com.adb.any_post.utils.BuildSqlStatement;
import com.adb.any_post.utils.CreateDbConnection;
import com.adb.any_post.utils.GetSqlStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeDao {
    private static final Logger logger = LoggerFactory.getLogger(LikeDao.class);

    public int saveLike(Like like) throws DAOException{
        int likeId = 0;
        try(Connection connection = CreateDbConnection.getConnection()){
            String sqlScriptPath = LikeSqlScriptsPath.INSERT_LIKE_SCRIPT.getPath();
            String insertLikeQuery = GetSqlStatement.sqlQueryBuilder(sqlScriptPath);

            try(PreparedStatement preparedStatement = connection.prepareStatement(insertLikeQuery)){

                BuildSqlStatement.setParameters(
                        preparedStatement,
                        like.getPostId(),
                        like.getUserId()
                        );

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    likeId = resultSet.getInt("id");
                }

            } catch (SQLException e){
                logger.error("SQL query execution error: " + e.getMessage(), e);
                throw new DAOException("sql execution error", e);
            }

        } catch (SQLException e) {
            logger.error("Fail to connect to database: " + e.getMessage(), e);
            throw new DAOException("Fail to connect to database", e);
        } catch (IOException e){
            logger.error("Failed to fetch insert like sql query -- " + e.getMessage(), e);
            throw new DAOException("Failed to fetch insert like sql query", e);
        }

        return likeId;
    }
}
