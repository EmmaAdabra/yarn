package com.adb.chat_app.utils;

import com.adb.chat_app.dao.userdao.UserDao;
import com.adb.chat_app.exceptions.UnknownException;
import com.adb.chat_app.models.User;
import com.adb.chat_app.services.UserService;

public class UserUtil {
    private static final UserService userService = new UserService(new UserDao());

    public static boolean verifyIsUser(String email) throws UnknownException{
            Response<User> serviceResponse = userService.getUserByEmail(email);

        return serviceResponse.getStatus_code() == ResponseCode.SUCCESS.getCode();
    }

}
