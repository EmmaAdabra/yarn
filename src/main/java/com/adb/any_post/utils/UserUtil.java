package com.adb.any_post.utils;

import com.adb.any_post.dao.userdao.UserDao;
import com.adb.any_post.exceptions.UnknownException;
import com.adb.any_post.models.User;
import com.adb.any_post.services.UserService;

public class UserUtil {
    private static final UserService userService = new UserService(new UserDao());

    public static boolean verifyIsUser(String email) throws UnknownException{
            Response<User> serviceResponse = userService.getUserByEmail(email);

        return serviceResponse.getStatus() == ResponseCode.SUCCESS.getCode();
    }

    public static String getUserPfpUrl(int userId){
        return GetContextPath.getContextPath() + "/fetchPfp?id=" + userId;
    }
}
