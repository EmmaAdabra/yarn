package com.adb.chat_app.dao.commentDao;

import com.adb.chat_app.exceptions.DAOException;
import com.adb.chat_app.models.Comment;
import com.adb.chat_app.utils.BuildSqlStatement;
import com.adb.chat_app.utils.CreateDbConnection;
import com.adb.chat_app.utils.GetSqlStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CommentDao implements ICommentDao{
    private static final Logger logger = LoggerFactory.getLogger(CommentDao.class);

    @Override
    public Optional<Comment> get(long ID) throws DAOException {
        return Optional.empty();
    }

    @Override
    public List<Comment> getAll() throws DAOException {
        return null;
    }

    @Override
    public int save(Comment comment) throws DAOException {
        int commentId = 0;

        try(Connection connection = CreateDbConnection.getConnection()){
            String sqlScriptPath = CommentSqlScriptsPath.INSERT_COMMENT.getPath();
            String insertCommentQuery = GetSqlStatement.sqlQueryBuilder(sqlScriptPath);

            try(PreparedStatement preparedStatement = connection.prepareStatement(insertCommentQuery)){
                Integer userId = comment.getUserId();
                userId = (userId != 0) ? userId : null;

                BuildSqlStatement.setParameters(
                        preparedStatement,
                        comment.getPostId(),
                        userId,
                        comment.getComment()
                );

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    commentId = resultSet.getInt("id");
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
        return commentId;
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
