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
import com.scantranx.entities.Ideposit;
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

@Path("/deposit")
public class Deposit{
    
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response deposit(@FormParam("userName")String userName,@FormParam("uuid")String uuid,@FormParam("amount")String amount,@FormParam("depositor")String depositor,@FormParam("accountnumber")String accountnumber,@FormParam("accountname")String accountname,@FormParam("comment")String comment,@FormParam("securityCode")String securityCode){
        AppManager appManager=new AppManager(new AdminRepositoryImpl());
        int tranxID =appManager.logTranx(uuid,"DEPOSIT");
        Ideposit deposit=new Ideposit();
        deposit.setAmount(amount);
        deposit.setAccountnumber(accountnumber);
        deposit.setAccountname(accountname);
        deposit.setDepsitor(depositor);
        deposit.setComment(comment);
        deposit.setUuid(uuid);        
        Response res;        
        res=appManager.deposit(deposit);                    
        appManager.updateTranx(tranxID,res.getStatuscode());
        Profile profile= appManager.getUser(userName);
        if(profile!=null&&profile.getDeviceId()!=null){
        new PushServiceCore().pushToAndroidDevice(profile.getDeviceId(), "You have just deposited "+amount +" to Account:"+accountnumber +" Successfully", AppInfo.DEPOSIT_NOTIFICATION);
        }
        return res;
    }
    
    

    public Deposit() {        
    }

}
