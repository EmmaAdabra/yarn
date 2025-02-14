package com.adb.chat_app.controllers;

import com.adb.chat_app.dao.userdao.UserDao;
import com.adb.chat_app.dto.SessionUserDTO;
import com.adb.chat_app.exceptions.UnknownException;
import com.adb.chat_app.services.UserService;
import com.adb.chat_app.utils.Response;
import com.adb.chat_app.utils.ResponseCode;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "FetchProfilePictureServlet", value = "/fetchPfp")
public class FetchProfilePictureServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(FetchProfilePictureServlet.class);
    private static final UserService userService = new UserService(new UserDao());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Tika tika = new Tika();
        String userId = request.getParameter("id");

        if(userId == null || userId.isEmpty()){
            logger.warn("can't fetch user pfp, userId is missing");
        } else {
            long usrId = Long.parseLong(userId);
            try {
                Response<byte[]> fetchPfpResponse = userService.fetchPfp(usrId);
                byte[] userPfp = fetchPfpResponse.getData();

                if( userPfp == null || userPfp.length == 0){
                    logger.warn("user with id -- {} have no pfp", userId);
                }

                String imageType = tika.detect(userPfp);
                response.setContentType(imageType);

//            write image
                OutputStream os = response.getOutputStream();
                os.write(userPfp);
                os.flush();

            } catch (Exception e) {
                request.setAttribute("hasPfp", false);
                logger.error("Error occur loading user pfp -- {}", e.getMessage(), e);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
