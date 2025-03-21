package com.adb.any_post.dao.postDao;

import com.adb.any_post.dto.PostDto;
import com.adb.any_post.dto.PosterDto;
import com.adb.any_post.exceptions.DAOException;
import com.adb.any_post.models.Post;
import com.adb.any_post.utils.BuildSqlStatement;
import com.adb.any_post.utils.CreateDbConnection;
import com.adb.any_post.utils.EntityModelMapper;
import com.adb.any_post.utils.GetSqlStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDao implements IPostDao{
    private static final Logger logger = LoggerFactory.getLogger(IPostDao.class);
    public List<PostDto> getAllPost() throws DAOException {
        List<PostDto> allPosts = new ArrayList<>();

        try(Connection connection = CreateDbConnection.getConnection()){
            String sqlScript = PostSqlScriptsPath.GET_ALL_POSTS.getPath();
            String getAllPostsQuery = GetSqlStatement.sqlQueryBuilder(sqlScript);

            try(CallableStatement callableStatement = connection.prepareCall(getAllPostsQuery)){

                try(ResultSet resultSet = callableStatement.executeQuery()){
                    while(resultSet.next()){
                        PostDto postDto;
                        PosterDto posterDto;
                        postDto = EntityModelMapper.postMapper(resultSet);
                        posterDto = EntityModelMapper.posterMapper(resultSet);
                        postDto.setPosterData(posterDto);
                        allPosts.add(postDto);
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
            logger.error("Failed to fetch get all post sql query -- " + e.getMessage());
            throw new DAOException("Failed to fetch get all post sql query", e);
        }

        return allPosts;
    }

    @Override
    public int save(Post post) throws DAOException {
        int updatedRow;
        try(Connection connection = CreateDbConnection.getConnection()){
            String sqlScriptPath = PostSqlScriptsPath.INSERT_USER_SCRIPT.getPath();
            String insertPostQuery = GetSqlStatement.sqlQueryBuilder(sqlScriptPath);

            try(PreparedStatement preparedStatement = connection.prepareStatement(insertPostQuery)){

                BuildSqlStatement.setParameters(
                        preparedStatement,
                        post.getUserId(),
                        post.getPostTile(),
                        post.getContent(),
                        post.getMedia()
                );

                updatedRow = preparedStatement.executeUpdate();

            } catch (SQLException e){
                logger.error("SQL query execution error: " + e.getMessage(), e);
                throw new DAOException("sql execution error", e);
            }

        } catch (SQLException e) {
            logger.error("Fail to connect to database: " + e.getMessage(), e);
            throw new DAOException("Fail to connect to database", e);
        } catch (IOException e){
            logger.error("Failed to fetch insert post sql query -- " + e.getMessage());
            throw new DAOException("Failed to fetch insert post sql query", e);
        }

        return updatedRow;
    }

    public int deletePost(int postId, int ownerId) throws DAOException{
        int deletedRow;

        try(Connection connection = CreateDbConnection.getConnection()){
            String sqlScriptPath = PostSqlScriptsPath.DELETE_POST.getPath();
            String deletePostQuery = GetSqlStatement.sqlQueryBuilder(sqlScriptPath);

            try(PreparedStatement preparedStatement = connection.prepareStatement(deletePostQuery)){

               preparedStatement.setInt(1, postId);
               preparedStatement.setInt(2, ownerId);

               deletedRow = preparedStatement.executeUpdate();

            } catch (SQLException e){
                logger.error("SQL query execution error: " + e.getMessage(), e);
                throw new DAOException("sql execution error", e);
            }

        } catch (SQLException e) {
            logger.error("Fail to connect to database: " + e.getMessage(), e);
            throw new DAOException("Fail to connect to database", e);
        } catch (IOException e){
            logger.error("Failed to fetch delete post sql query -- " + e.getMessage());
            throw new DAOException("Failed to fetch delete post sql query", e);
        }

        return deletedRow;
    }

    public boolean doesPostExist(int postId) {
        String sql = "SELECT COUNT(*) FROM posts WHERE id = ?";

        try (Connection conn = CreateDbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if the post exists
                }
            }

        } catch (SQLException e) {
            logger.error("An error occurred while checking if the post exists -- {}", e.getMessage(), e);
            throw new RuntimeException("An error occurred while checking the post.", e);
        }

        return false; // Post does not exist
    }
}
