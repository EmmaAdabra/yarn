package com.adb.chat_app.dao.userdao;

import com.adb.chat_app.dao.IDAO;
import com.adb.chat_app.exceptions.DAOException;
import com.adb.chat_app.models.User;

import javax.servlet.http.Part;
import java.util.Optional;

public interface IUserDao extends IDAO<User> {
    User findUserByEmail(String email) throws DAOException;
    boolean saveUserPfp(long userId, Part imagePart) throws DAOException;
    boolean hasUserPfp(long userID) throws DAOException;
    byte[] fetchUserPfp(long userId) throws DAOException;
    int updateUserBio(String bio, int userId) throws DAOException;
}
