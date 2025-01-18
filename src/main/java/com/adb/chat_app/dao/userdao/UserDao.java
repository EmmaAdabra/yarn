package com.adb.chat_app.dao.userdao;

import com.adb.chat_app.exceptions.DAOException;
import com.adb.chat_app.models.User;
import com.adb.chat_app.utils.CreateDbConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        return 0;
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
