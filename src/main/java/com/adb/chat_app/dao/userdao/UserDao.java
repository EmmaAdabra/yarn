package com.adb.chat_app.dao.userdao;

import com.adb.chat_app.exceptions.DAOException;
import com.adb.chat_app.models.User;
import com.adb.chat_app.utils.CreateDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDao implements IUserDao{
    @Override
    public Optional<User> get(long ID) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public int save(User user) {
        return 0;
    }

    @Override
    public int update(Object... params) {
        return 0;
    }

    @Override
    public int delete(long id) {
        return 0;
    }
}
