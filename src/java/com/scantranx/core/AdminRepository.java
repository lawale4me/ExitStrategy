/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scantranx.core;

import com.scantranx.dto.NameResponse;
import com.scantranx.dto.Response;
import com.scantranx.entities.Account;
import com.scantranx.entities.Branch;
import com.scantranx.entities.Ideposit;
import com.scantranx.entities.Merchant;
import com.scantranx.entities.Merchantprofile;
import com.scantranx.entities.Profile;
import com.scantranx.entities.Transactionhistory;
import com.scantranx.entities.Transactions;
import java.util.List;


/**
 *
 * @author Ahmed
 */
public interface AdminRepository extends UnitOfWork
{
    
    
    public Account nameEnquiry(String accountnumber, String uuid);
    public Response deposit(Ideposit ideposit);
    public void logTranx(Transactionhistory transactionhistory);
    public void updateTranx(Transactionhistory transactionhistory);
    public Transactionhistory findTranx(int tranxID);
    public void registerUser(Profile profile);
    public Profile findProfile(String userName);
    public void registerMerchant(Merchantprofile profile);
    public void logTranx(Transactions tranx);
    public List<Branch> getBranches(Merchant merchant);
    public Merchant findMerchant(String globalMerchantID);
    public Branch findBranch(Integer branchMerchant);
    public Merchantprofile findMProfile(String userName);
    
}
