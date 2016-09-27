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
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ahmed
 */
@Path("/registerDeviceID")
public class RegusterDeviceID {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerDeviceID(@FormParam("userName") String userName, @FormParam("deviceType") String deviceType, @FormParam("deviceId") String deviceId, @FormParam("location") String location, @FormParam("uuid") String uuid) {
        Response res = new Response();
        AppManager appManager = new AppManager(new AdminRepositoryImpl());
        if (appManager.checkUserExists(userName)) {
            appManager.updateUser(userName, deviceId, deviceType);
            res.setStatus(true);
            res.setDescription("User profile successfully updated");            
            res.setStatuscode(00);            
        } else {
            res.setDescription("User does not exists");
            res.setStatus(false);
            res.setStatuscode(10);
        }
        return res;
    }
}
