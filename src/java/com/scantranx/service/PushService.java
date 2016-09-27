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
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ahmed
 */
@Path("/pushService")
public class PushService {
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response pushService(@FormParam("deviceId") String deviceId, @FormParam("message") String message, @FormParam("location") String location, @FormParam("uuid") String uuid) {
        Response res = new Response();        
        new PushServiceCore().pushToAndroidDevice(deviceId, message, AppInfo.DEPOSIT_NOTIFICATION);
        res.setStatus(true);
        res.setDescription("Service pushed successfully registered");
        res.setStatuscode(00);       
        return res;
    }
    
}
