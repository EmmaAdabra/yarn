package com.adb.any_post.dao.likesDao;

import com.adb.any_post.dto.LikedPost;
import com.adb.any_post.exceptions.DAOException;
import com.adb.any_post.models.Like;
import com.adb.any_post.utils.BuildSqlStatement;
import com.adb.any_post.utils.CreateDbConnection;
import com.adb.any_post.utils.EntityModelMapper;
import com.adb.any_post.utils.GetSqlStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<Integer, LikedPost> getUserLikedPosts(int user_id) throws DAOException{
        Map<Integer, LikedPost> likedPostMap = new HashMap<>();

        try(Connection connection = CreateDbConnection.getConnection()){
            String sqlScriptPath = LikeSqlScriptsPath.GET_USER_LIKED_POSTS.getPath();
            String getUserLikedPostQuery = GetSqlStatement.sqlQueryBuilder(sqlScriptPath);

            try(PreparedStatement preparedStatement = connection.prepareStatement(getUserLikedPostQuery)){

                preparedStatement.setInt(1, user_id);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                    LikedPost likedPost = new LikedPost(resultSet.getInt("post_id"), resultSet.getInt("id"));
                    likedPostMap.put(likedPost.getPostId(), likedPost);
                }

            } catch (SQLException e){
                logger.error("SQL query execution error: " + e.getMessage(), e);
                throw new DAOException("sql execution error", e);
            }

        } catch (SQLException e) {
            logger.error("Fail to connect to database: " + e.getMessage(), e);
            throw new DAOException("Fail to connect to database", e);
        } catch (IOException e){
            logger.error("Failed to fetch get user liked posts sql query -- " + e.getMessage(), e);
            throw new DAOException("Failed to fetch,  get user liked posts sql query", e);
        }

        return likedPostMap;
    }
}
