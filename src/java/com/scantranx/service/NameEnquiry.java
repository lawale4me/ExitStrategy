/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scantranx.service;

import com.scantranx.core.AdminRepositoryImpl;
import com.scantranx.core.AppManager;
import com.scantranx.dto.NameResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ahmed
 */
@Path("/nameenquiry")
public class NameEnquiry {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public NameResponse nameEnquiry(@QueryParam("accountnumber")String accountnumber,@QueryParam("uuid")String uuid){                
        NameResponse res;
        AppManager appManager=new AppManager(new AdminRepositoryImpl());
        int tranxID = appManager.logTranx(uuid,"NAMEENQUIRY");
        res=appManager.nameEnquiry(accountnumber,uuid);
        appManager.updateTranx(tranxID,res.getStatuscode());
        return res;
    }
    
}
