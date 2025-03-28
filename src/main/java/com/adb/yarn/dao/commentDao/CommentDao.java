/**
 * The CommentDao class in Java handles database operations related to comments for a post.
 */
package com.adb.yarn.dao.commentDao;

import com.adb.yarn.dto.CommentDto;
import com.adb.yarn.exceptions.DAOException;
import com.adb.yarn.models.Comment;
import com.adb.yarn.utils.BuildSqlStatement;
import com.adb.yarn.utils.CreateDbConnection;
import com.adb.yarn.utils.EntityModelMapper;
import com.adb.yarn.utils.GetSqlStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentDao implements ICommentDao{
    private static final Logger logger = LoggerFactory.getLogger(CommentDao.class);

    public List<CommentDto> getPostComments(int postId) throws DAOException{
        List<CommentDto> allComments = new ArrayList<>();

        try(Connection connection = CreateDbConnection.getConnection()){
            String sqlScript = CommentSqlScriptsPath.GET_POST_COMMENTS.getPath();
            String getAllPostComment = GetSqlStatement.sqlQueryBuilder(sqlScript);

            try(PreparedStatement preparedStatement = connection.prepareStatement(getAllPostComment)){
                preparedStatement.setInt(1, postId);

                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while(resultSet.next()){
                        CommentDto commentDto = EntityModelMapper.commentMapper(resultSet);
                        allComments.add(commentDto);
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
            logger.error("Failed to fetch get post comment sql query -- " + e.getMessage());
            throw new DAOException("Failed to fetch get post comment sql query", e);
        }

        return allComments;
    }

    public Optional<CommentDto> saveComment(Comment comment) throws DAOException{
        Optional<CommentDto> optionalCommentDto = Optional.empty();

        try(Connection connection = CreateDbConnection.getConnection()){
            String sqlScriptPath = CommentSqlScriptsPath.INSERT_COMMENT.getPath();
            String insertCommentQuery = GetSqlStatement.sqlQueryBuilder(sqlScriptPath);

            try(PreparedStatement preparedStatement = connection.prepareStatement(insertCommentQuery)){
                Integer userId = comment.getUserId();
                userId = (userId != 0) ? userId : null;

                BuildSqlStatement.setParameters(
                        preparedStatement,
                        comment.getPostId(),
                        comment.getComment().trim(),
                        userId
                );

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                     optionalCommentDto = Optional.of(EntityModelMapper.commentMapper(resultSet));
                }

                resultSet.close();
            } catch (SQLException e){
                logger.error("SQL query execution error: " + e.getMessage(), e);
                throw new DAOException("sql execution error", e);
            }
        } catch (SQLException e) {
            logger.error("Fail to connect to database: " + e.getMessage(), e);
            throw new DAOException("Fail to connect to database", e);
        } catch (IOException e){
            logger.error("Failed to fetch insert comment sql query -- " + e.getMessage());
            throw new DAOException("Failed to fetch insert comment sql query", e);
        }

        return optionalCommentDto;
    }

    public int deleteComment(int commentId, int ownerId) throws DAOException{
        int deletedRow;

        try(Connection connection = CreateDbConnection.getConnection()){
            String sqlScriptPath = CommentSqlScriptsPath.DELETE_COMMENT.getPath();
            String deleteCommentQuery = GetSqlStatement.sqlQueryBuilder(sqlScriptPath);

            try(PreparedStatement preparedStatement = connection.prepareStatement(deleteCommentQuery)){

                preparedStatement.setInt(1, commentId);
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
            logger.error("Failed to fetch delete comment sql query -- " + e.getMessage());
            throw new DAOException("Failed to fetch delete comment sql query", e);
        }

        return deletedRow;
    }
}
