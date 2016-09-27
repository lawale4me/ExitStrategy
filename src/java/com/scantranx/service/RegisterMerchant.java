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
@Path("/registerMA")
public class RegisterMerchant {
    
    
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerMA(@FormParam("userName") String userName, @FormParam("password") String password, @FormParam("fullName") String fullName, @FormParam("phoneNo") String phoneNo, @FormParam("email") String email, @FormParam("location") String location, @FormParam("latlng") String latlng, @FormParam("branchMerchant") Integer branchMerchant, @FormParam("uuid") String uuid) {
        Response res = new Response();
        AppManager appManager = new AppManager(new AdminRepositoryImpl());
        if (!appManager.checkUserExists1(userName)) {
            boolean status=appManager.registerMerchant(userName, password, fullName, phoneNo, email, location, branchMerchant, uuid,latlng);
            if(status){
            res.setStatus(status);
            res.setDescription("User successfully registered");
            res.setStatuscode(00);
            }
            else{
               res.setStatus(status);
            res.setDescription("Branch code not found");
            res.setStatuscode(10);
            }
        } else {
            res.setDescription("User Already exists");
            res.setStatus(false);
            res.setStatuscode(10);
        }
        return res;
    }
    
    
}
