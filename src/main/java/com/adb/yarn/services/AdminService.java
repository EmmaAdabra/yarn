package com.adb.yarn.services;

import com.adb.yarn.dao.admindao.AdminDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    private final static HashSet<Integer> adminIdList = new HashSet<>();

    public static void loadAdminId(){
        try{
            HashSet<Integer>  idList = new AdminDao().getAdminId();

            if(!idList.isEmpty()){
                adminIdList.addAll(idList);
            } else {
                logger.warn("No admin found, setting default admin id to 27");
                adminIdList.add(27);
            }

        } catch (Exception e) {
            logger.error("Failed to fetch all admin Id from database - {}", e.getMessage(), e);
            logger.warn("Setting default admin id to 27");
            adminIdList.add(27);
        }
    }

    public static boolean isAdmin(int userId){
        return adminIdList.contains(userId);
    }
}
