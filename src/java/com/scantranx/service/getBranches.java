/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scantranx.service;

import com.scantranx.core.AdminRepositoryImpl;
import com.scantranx.core.AppManager;
import com.scantranx.entities.Branch;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ahmed
 */
@Path("/getBranches")
public class getBranches {
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Branch> getBranches(@QueryParam("globalMerchantID")String globalMerchantID,@QueryParam("uuid")String uuid){                
        List<Branch> res;
        AppManager appManager=new AppManager(new AdminRepositoryImpl());
        int tranxID = appManager.logTranx(uuid,"getBranches");
        res=appManager.getBranches(globalMerchantID,uuid);
        appManager.updateTranx(tranxID,10);
        return res;
    }
    
}
