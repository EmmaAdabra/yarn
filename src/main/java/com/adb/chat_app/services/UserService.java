package com.adb.chat_app.services;

import com.adb.chat_app.dao.userdao.IUserDao;
import com.adb.chat_app.exceptions.DAOException;
import com.adb.chat_app.exceptions.UnknownException;
import com.adb.chat_app.models.User;
import com.adb.chat_app.utils.Response;
import com.adb.chat_app.utils.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final IUserDao userDao;

    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }

    public Response<User> getUserByEmail(String email) throws UnknownException {
        try {
            User user = userDao.findUserByEmail(email);

            if(user != null){
                logger.info("successfully fetch user with email - {}", email);
                return  new Response<>(ResponseCode.SUCCESS.getCode(), "user found", user);
            }
            logger.warn("User with email - {} not found in database", email);
        } catch (DAOException e) {
            logger.error("Error occur fetching user with email {}", email, e);
            return  new Response<>(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), "Internal server error");
        }

        return new Response<>(ResponseCode.RESOURCE_NOT_FOUND.getCode(), "Internal server error");
    }

    public Response<Integer> saveUser(User user) throws UnknownException{
        Response response;

        try {
           int userID = userDao.save(user);
           logger.info("successfully save user with ID - {} to database", userID);

           response = new Response<>(ResponseCode.RESOURCE_CREATED.getCode(), "User added to database", userID);
        } catch (DAOException e) {
            logger.error("Error occur while saving user to database");
            response = new Response<>(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), "user not save to database");
        }

        return response;
    }
}
