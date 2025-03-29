package com.adb.yarn.services;

import com.adb.yarn.cache.AdminCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public static boolean isAdmin(int userId){
        return AdminCache.contains(userId);
    }
}
