/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scantranx.service;

import com.scantranx.core.AdminRepositoryImpl;
import com.scantranx.core.AppManager;
import com.scantranx.core.PushServiceCore;
import com.scantranx.dto.AppInfo;
import com.scantranx.dto.Response;
import com.scantranx.entities.Profile;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ahmed
 */
@Path("/register")
public class Register {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@FormParam("userName") String userName,
            @FormParam("password") String password,
            @FormParam("fullName") String fullName,
            @FormParam("phoneNo") String phoneNo,
            @FormParam("email") String email,
            @FormParam("location") String location, 
            @FormParam("latlng") String latlng,
            @FormParam("uuid") String uuid) {
        Response res = new Response();
        AppManager appManager = new AppManager(new AdminRepositoryImpl());
        if (!appManager.checkUserExists(userName)) {
            appManager.registerUser(userName, password, fullName, phoneNo, email, location, uuid, latlng);
            res.setStatus(true);
            res.setDescription("User successfully registered");
            res.setStatuscode(00);
            
            Profile profile= appManager.getUser(userName);
            new PushServiceCore().pushToAndroidDevice(profile.getDeviceId(), "You have registered on Scantranx Successfully", AppInfo.REGISTER_NOTIFICATION);
        } else {
            res.setDescription("User Already exists");
            res.setStatus(false);
            res.setStatuscode(10);
        }
        return res;
    }
}
