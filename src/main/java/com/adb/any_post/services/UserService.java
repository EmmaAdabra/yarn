package com.adb.any_post.services;

import com.adb.any_post.dao.userdao.IUserDao;
import com.adb.any_post.dto.SessionUserDTO;
import com.adb.any_post.exceptions.DAOException;
import com.adb.any_post.exceptions.UnknownException;
import com.adb.any_post.models.User;
import com.adb.any_post.utils.Response;
import com.adb.any_post.utils.ResponseCode;
import com.adb.any_post.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Part;

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
        Response<Integer> response;

        try {
           int userID = userDao.save(user);
           logger.info("successfully save user with ID - {} to database", userID);

           response = new Response<>(ResponseCode.RESOURCE_CREATED.getCode(), "User added to database", userID);
        } catch (DAOException e) {
            logger.error("Error occur while saving user to database");
            response = new Response<>(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), "DAO error occur");
        }

        return response;
    }

    public Response<User> validateUser(String email, String password) throws UnknownException {
        Response<User> response;

        try {
            User user = userDao.findUserByEmail(email);
            if(user != null && user.getPassword().equals(password)) {
                response = new Response<>(ResponseCode.SUCCESS.getCode(), "Valid login detail", user);
            } else {
              response = new Response<>(ResponseCode.VALIDATION_ERROR.getCode(), "Invalid login details");
            }
        } catch (DAOException e) {
            response = new Response<>(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), "Internal server error");
        }

        return response;
    }

    public Response<SessionUserDTO> createSessionUser(User user) throws UnknownException {
        SessionUserDTO sessionUser = new SessionUserDTO();
        sessionUser.setUserId(user.getId());
        sessionUser.setfName(user.getFirstName());
        sessionUser.setlName(user.getLastName());
        sessionUser.setEmail(user.getEmail());
        sessionUser.setUserFullName(StringUtils.getUSerFullName(user.getFirstName(), user.getLastName()));

        if(user.getBio() != null){
            sessionUser.setBio(user.getBio());
        }


        if(hasPfp(user.getId())) {
            sessionUser.setHasPfp(true);
        } else {
            sessionUser.setUserInitial(StringUtils.getUserInitial(user.getFirstName(), user.getLastName()));
        }



        return new Response<>(ResponseCode.SUCCESS.getCode(), "Success", sessionUser);
    }

    public Response<Object> uploadUserPfp(long userId, Part imagePart) throws UnknownException{
        Response<Object> response;
        try{
            if(userDao.saveUserPfp(userId, imagePart)){
                response = new Response<>(ResponseCode.RESOURCE_CREATED.getCode(), "User profile" +
                        " picture " +
                        "uploaded");
            } else{
                response = new Response<>(ResponseCode.RESOURCE_NOT_FOUND.getCode(), "User not " +
                        "found");
            }
        } catch (DAOException e){
           response = new Response<>(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), "Could not " +
                   "upload " +
                   "image, internal " +
                   "server error");
        }
        return response;
    }

    private boolean hasPfp(long userId) throws UnknownException{

        try{
            return userDao.hasUserPfp(userId);
        } catch (DAOException e) {
            logger.error("Error occur checking if user have a pfp -- {}", e.getMessage(), e);
            return false;
        }
    }

    public Response<byte[]> fetchPfp(long userId) throws UnknownException {
        try{
            byte[] userPfp = userDao.fetchUserPfp(userId);
            if(userPfp != null || userPfp.length != 0){
                return new Response<>(ResponseCode.SUCCESS.getCode(), "Success", userPfp);
            }
        } catch (DAOException e) {
            logger.error("Failed to fetch user profile picture -- {}", e.getMessage(), e);
            return new Response<>(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), "Failed to fetch user profile picture", new byte[0]);
        }

        return new Response<>(ResponseCode.RESOURCE_NOT_FOUND.getCode(), "user have no pfp", new byte[0]);
    }

    public Response<Integer> addBio(String bio, int userId) throws UnknownException{
        try{
            int updateBio = userDao.updateUserBio(bio, userId);
            if(updateBio != 0){
                return new Response<>(ResponseCode.RESOURCE_CREATED.getCode(), "User bio updated");
            }
        } catch (DAOException e) {
            logger.error("Failed to fetch user profile picture -- {}", e.getMessage(), e);
            return new Response<>(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), "Failed to update user bio, an internal error occur");
        } catch (Exception e){
            throw new UnknownException("An unknown error -- " + e.getMessage(), e);
        }

        return new Response<>(ResponseCode.RESOURCE_NOT_FOUND.getCode(), "User not found");
    }
}
