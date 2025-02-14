package com.adb.chat_app.dao.postDao;

import com.adb.chat_app.dao.userdao.UserDao;
import com.adb.chat_app.dto.PostDto;
import com.adb.chat_app.dto.PosterDto;
import com.adb.chat_app.exceptions.DAOException;
import com.adb.chat_app.models.Post;
import com.adb.chat_app.utils.BuildSqlStatement;
import com.adb.chat_app.utils.CreateDbConnection;
import com.adb.chat_app.utils.EntityModelMapper;
import com.adb.chat_app.utils.GetSqlStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostDao implements IPostDao{
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Override
    public Optional<Post> get(long ID) throws DAOException {
        return Optional.empty();
    }

    @Override
    public List<Post> getAll() throws DAOException {
       return null;
    }

    public List<PostDto> getAllPost(String appPath) throws DAOException {
        List<PostDto> allPosts = new ArrayList<>();

        try(Connection connection = CreateDbConnection.getConnection()){
            String sqlScript = PostSqlScriptsPath.GET_ALL_POSTS.getPath();
            String getAllPostsQuery = GetSqlStatement.sqlQueryBuilder(sqlScript);

            try(CallableStatement callableStatement = connection.prepareCall(getAllPostsQuery)){

                try(ResultSet resultSet = callableStatement.executeQuery()){
                    while(resultSet.next()){
                        PostDto postDto = new PostDto();
                        PosterDto posterDto = new PosterDto();
                        postDto = EntityModelMapper.postMapper(resultSet);
                        posterDto = EntityModelMapper.posterMapper(appPath, resultSet);
                        postDto.setPosterData(posterDto);
                        allPosts.add(postDto);
                    }
                }

                logger.info("total post: {}", allPosts.size());
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
            logger.error("Failed to fetch update user bio sql query -- " + e.getMessage());
            throw new DAOException("Failed to fetch get insert user pfp sql query", e);
        }

        return updatedRow;
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
