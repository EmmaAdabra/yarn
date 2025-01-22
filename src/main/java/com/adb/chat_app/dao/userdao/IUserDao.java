package com.adb.chat_app.dao.userdao;

import com.adb.chat_app.dao.IDAO;
import com.adb.chat_app.exceptions.DAOException;
import com.adb.chat_app.models.User;

import java.util.Optional;

public interface IUserDao extends IDAO<User> {
    User findUserByEmail(String email) throws DAOException;
}
