/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scantranx.service;

import com.scantranx.core.AdminRepositoryImpl;
import com.scantranx.core.AppManager;
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
@Path("/login")
public class Login {
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("userName")String userName,@FormParam("password")String password,@FormParam("uuid")String uuid){                
        Response res;
        AppManager appManager=new AppManager(new AdminRepositoryImpl());
        System.out.println("userName"+userName);
       // int tranxID = appManager.logTranx(uuid,"LOGON");
        res=appManager.login(userName,password,uuid);
        //appManager.updateTranx(tranxID,res.getStatuscode());
        return res;
    }
    
}
