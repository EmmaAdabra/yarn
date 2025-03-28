package com.adb.yarn.utils;

import com.adb.yarn.dao.userdao.UserDao;
import com.adb.yarn.exceptions.UnknownException;
import com.adb.yarn.models.User;
import com.adb.yarn.services.UserService;

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
