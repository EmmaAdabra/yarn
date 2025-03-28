package com.adb.yarn.dao.userdao;

import com.adb.yarn.exceptions.DAOException;
import com.adb.yarn.models.User;

import javax.servlet.http.Part;
import java.util.Optional;

public interface IUserDao{
    Optional<User> get(long userId) throws DAOException;
    int save(User user) throws DAOException;
    User findUserByEmail(String email) throws DAOException;
    boolean saveUserPfp(long userId, Part imagePart) throws DAOException;
    boolean hasUserPfp(long userID) throws DAOException;
    byte[] fetchUserPfp(long userId) throws DAOException;
    int updateUserBio(String bio, int userId) throws DAOException;
}
