/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scantranx.core;

import com.scantranx.dto.NameResponse;
import com.scantranx.dto.Response;
import com.scantranx.dto.Util;
import com.scantranx.entities.Account;
import com.scantranx.entities.Branch;
import com.scantranx.entities.Ideposit;
import com.scantranx.entities.Merchant;
import com.scantranx.entities.Merchantprofile;
import com.scantranx.entities.Profile;
import com.scantranx.entities.Transactionhistory;
import com.scantranx.entities.Transactions;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Ahmed
 */
public class AppManager 
{
    
    private final AdminRepository adminrepo;


    public AppManager(AdminRepositoryImpl adminRepositoryImpl) {
        this.adminrepo = adminRepositoryImpl;
    }

  
    public Response deposit(Ideposit ideposit) {
        UnitOfWorkSession ses = adminrepo.begin();        
        Response res = adminrepo.deposit(ideposit);
        res.setStatuscode(ResponseCodes.SUCCESSFUL);
        res.setDescription("Deposit was Successful");
        ses.commit();
        return res;
    }
    
    public NameResponse nameEnquiry(String accountname, String uuid) {
        UnitOfWorkSession ses = adminrepo.begin();       
        NameResponse res =new NameResponse();
        res.setStatuscode(ResponseCodes.FAILED);
        Account account = adminrepo.nameEnquiry(accountname,uuid);
        if(account!=null){
            res.setStatus(true);
            res.setACCOUNTNAME(account.getAccountName());
            res.setStatuscode(ResponseCodes.SUCCESSFUL);
        }
        ses.commit();
        return res;
    }

    public int logTranx(String uuid, String description) {
        UnitOfWorkSession ses = adminrepo.begin();        
        Transactionhistory tranx=new Transactionhistory(uuid,description);
        adminrepo.logTranx(tranx);
        ses.commit();
        return tranx.getTranxID();
    }
    
    public void updateTranx(Transactionhistory transactionhistory)
    {   
        UnitOfWorkSession ses = adminrepo.begin(); 
        adminrepo.updateTranx(transactionhistory);
        ses.commit();
    }

    public Transactionhistory findTranx(int tranxID) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        Transactionhistory transactionhistory = adminrepo.findTranx(tranxID);
        ses.commit();
        return transactionhistory;
    }

    public void updateTranx(int tranxID,int statuscode) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        Transactionhistory trans = findTranx(tranxID);
        if(trans!=null){
        trans.setTranxStatus(statuscode);
        adminrepo.updateTranx(trans);
        }
        ses.commit();
    }

    public void registerUser(String userName, String password, String fullName, String phoneNo, String email, String location, String uuid,String latlng) {
        UnitOfWorkSession ses = adminrepo.begin();        
        Profile profile=new Profile();
        profile.setEmail(email);
        profile.setLocation(location);
        profile.setFullName(fullName);
        profile.setPassword(password);
        profile.setPhoneNo(phoneNo);
        profile.setStatus(Boolean.TRUE);
        profile.setUserName(userName);
        profile.setUuid(uuid);
        profile.setRegDate(new Date());
        profile.setLatlng(latlng);
        adminrepo.registerUser(profile);
        ses.commit();        
    }

    public Response login(String userName, String password, String uuid) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        Response res=new Response();
        Profile profile = adminrepo.findProfile(userName);
        if(profile!=null){
            
            if (profile.getPassword().equals(password)){
                res.setDescription("Login successful");
                res.setStatus(true);
                res.setStatuscode(ResponseCodes.SUCCESSFUL);
            }
            else{
                res.setDescription("Invalid password");
                res.setStatus(false);
                res.setStatuscode(ResponseCodes.FAILED);
            }
        }
        else{
         res.setDescription("User does not exist");
         res.setStatus(false);
         res.setStatuscode(ResponseCodes.FAILED);
        }
        ses.commit();
        return res;
    }

    public boolean checkUserExists(String userName) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        Profile profile = adminrepo.findProfile(userName);
        ses.commit();
        return profile==null?false:true;
    }
    
    public boolean checkUserExists1(String userName) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        Merchantprofile profile = adminrepo.findMProfile(userName);
        ses.commit();
        return profile==null?false:true;
    }

    public boolean registerMerchant(String userName, String password, String fullName, String phoneNo, String email, String location, Integer branchMerchant, String uuid,String latlng) {
        boolean b;
        
        UnitOfWorkSession ses = adminrepo.begin();                
        Branch branch=adminrepo.findBranch(branchMerchant);
        if(branch!=null){
        Merchantprofile profile=new Merchantprofile();
            try {
                password =Util.hash(password);
            } catch (Exception ex) {
                Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        profile.setEmail(email);
        profile.setLocation(location);
        profile.setFullName(fullName);
        profile.setPassword(password);
        profile.setPhoneNo(phoneNo);
        profile.setStatus(Boolean.TRUE);
        profile.setUserName(userName);
        profile.setUuid(uuid);
        profile.setBranch(branch);        
        profile.setLatlng(latlng);
        adminrepo.registerMerchant(profile);
        b=true;
        }
        else {
            b= false;
        }
        ses.commit();  
        return b;
    }

    public void logTranx(String userName, String action, String beneficiary, String reference, String details, String location, Integer globalMerchant, String uuid, Double amount,String lnglat,String status,String message) {
        UnitOfWorkSession ses = adminrepo.begin();        
        Transactions tranx=new Transactions();
        tranx.setUserName(adminrepo.findProfile(userName));
        tranx.setAction(action);
        tranx.setBeneficiary(beneficiary);
        tranx.setReference(reference);
        tranx.setDetails(details);
        tranx.setLocation(location);
        tranx.setUuid(uuid);
        tranx.setGlobalMerchant(globalMerchant);
        tranx.setAmount(amount);
        tranx.setTrxnDate(new Date());
        tranx.setLatlng(lnglat);
        tranx.setStatus(status);
        tranx.setMessage(message);
        adminrepo.logTranx(tranx);
        ses.commit(); 
    }

    public void updateUser(String userName, String deviceId, String deviceType) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        Profile profile = adminrepo.findProfile(userName);
        profile.setDeviceId(deviceId);
        profile.setDeviceType(deviceType);
        ses.commit();
    }
    
    
    public Profile getUser(String userName) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        Profile profile = adminrepo.findProfile(userName);
        ses.commit();
        return profile;
    }

    public List<Branch> getBranches(String globalMerchantID, String uuid) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        Merchant merchant=adminrepo.findMerchant(globalMerchantID);
        if(merchant==null){
         ses.commit();
         return null;
        }
        List<Branch> branches = adminrepo.getBranches(merchant);
        ses.commit();
        return branches;
    }


}
