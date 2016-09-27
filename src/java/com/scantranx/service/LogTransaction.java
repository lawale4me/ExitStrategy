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
@Path("/logTranx")
public class LogTransaction {

    @POST    
    @Produces(MediaType.APPLICATION_JSON)
    public Response logTranx(@FormParam("userName") String userName,
            @FormParam("action") String action,
            @FormParam("beneficiary") String beneficiary, 
            @FormParam("reference") String reference,
            @FormParam("details") String details, 
            @FormParam("loctaion") String location, 
            @FormParam("globalMerchant") String globalMerchant,
            @FormParam("amount") String amount,            
            @FormParam("latlng") String latlng,
            @FormParam("status") String status,
            @FormParam("message") String message,
            @FormParam("uuid") String uuid) {
        Response res = new Response();
        AppManager appManager = new AppManager(new AdminRepositoryImpl());
        appManager.logTranx(userName, action, beneficiary, reference, details, location,Integer.parseInt(globalMerchant) , uuid,Double.parseDouble(amount),latlng,status,message);
        
        Profile profile= appManager.getUser(userName);
        if(profile!=null&&profile.getDeviceId()!=null& status.equalsIgnoreCase("SUCCESSFUL")){
        new PushServiceCore().pushToAndroidDevice(profile.getDeviceId(), message, action);
        }
        
        res.setStatus(true);
        res.setDescription("Transaction logged successfully registered");
        res.setStatuscode(00);
        return res;
    }
}
