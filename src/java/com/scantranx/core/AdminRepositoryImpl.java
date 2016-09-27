/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scantranx.core;



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
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author Ahmed
 */

public class AdminRepositoryImpl extends RepositoryBase implements AdminRepository 
{
    
    public AdminRepositoryImpl() {
    }


    
    @Override
    public Response deposit(Ideposit ideposit)
    {
        Response res=new Response();              
        try{ 
        if(session!=null&&session.isActive())
        {                                            
            manager.persist(ideposit);              
        }
        else
        {
            EntityManager manager = RepositoryManager.getManager();
            manager.persist(ideposit);        
            manager.close();                             
         }
                res.setStatus(true);
                res.setDescription("Transaction Successful");         
                return res;
         } catch (NoResultException e) {
             res.setStatus(true);
             res.setDescription("Error Occured");         
             return res;
            }
    }

    @Override
    public Account nameEnquiry(String accountnumber, String uuid) {                
      if(session!=null&&session.isActive())
        {
        List<Account> account = manager.createNamedQuery("Account.findByAccountNumber", Account.class).setParameter("accountNumber", accountnumber).getResultList();
        return account.isEmpty() ? null : account.get(0);            
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Account> account = manager.createNamedQuery("Account.findByAccountNumber", Account.class).setParameter("accountNumber", accountnumber).getResultList();        
        manager.close();
        return account.isEmpty() ? null : account.get(0);            
        }          
      
    }

    @Override
    public void logTranx(Transactionhistory transactionhistory) {
                    
        try{ 
        if(session!=null&&session.isActive())
        {                                            
            manager.persist(transactionhistory);              
        }
        else
        {
            EntityManager manager = RepositoryManager.getManager();
            manager.persist(transactionhistory);        
            manager.close();                             
         }
        
         } catch (NoResultException e) {
        
             System.out.println("Error Occured while logging tranx");                 
            }
    }

    @Override
    public void updateTranx(Transactionhistory transactionhistory) {         
        if(session!=null&&!session.isActive()){
        EntityManager manager = RepositoryManager.getManager();
        manager.getTransaction().begin();
        manager.merge(transactionhistory);
        manager.getTransaction().commit();
        }
    }

    @Override
    public Transactionhistory findTranx(int tranxID) {
        if(session!=null&&session.isActive())
        {
        List<Transactionhistory> transhist = manager.createNamedQuery("Transactionhistory.findByTranxID", Transactionhistory.class).setParameter("tranxID", tranxID).getResultList();
        return transhist.isEmpty() ? null : transhist.get(0);            
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Transactionhistory> transhist = manager.createNamedQuery("Transactionhistory.findByTranxID", Transactionhistory.class).setParameter("tranxID", tranxID).getResultList();       
        manager.close();
        return transhist.isEmpty() ? null : transhist.get(0);            
        }
    }
    
    
    @Override
    public void registerUser(Profile profile){
        try
        { 
        if(session!=null&&session.isActive())
        {                                            
            manager.persist(profile);              
        }
        else
        {
            EntityManager manager = RepositoryManager.getManager();
            manager.persist(profile);        
            manager.close();                             
         }
        
         }
        catch (NoResultException e) 
         {
        
             System.out.println("Error Occured while logging tranx");                 
         }
    }
    
    @Override
    public Profile findProfile(String userName){
        if(session!=null&&session.isActive())
        {
        List<Profile> profile = manager.createNamedQuery("Profile.findByUserName", Profile.class).setParameter("userName", userName).getResultList();
        return profile.isEmpty() ? null : profile.get(0);            
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Profile> profile = manager.createNamedQuery("Profile.findByUserName", Profile.class).setParameter("userName", userName).getResultList();        
        manager.close();
        return profile.isEmpty() ? null : profile.get(0); 
        }
    }
    
    @Override
     public Merchantprofile findMProfile(String userName){
        if(session!=null&&session.isActive())
        {
        List<Merchantprofile> profile = manager.createNamedQuery("Merchantprofile.findByUserName", Merchantprofile.class).setParameter("userName", userName).getResultList();
        return profile.isEmpty() ? null : profile.get(0);            
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Merchantprofile> profile = manager.createNamedQuery("Merchantprofile.findByUserName", Merchantprofile.class).setParameter("userName", userName).getResultList();
        manager.close();
        return profile.isEmpty() ? null : profile.get(0); 
        }
    }
    
    

    @Override
    public void registerMerchant(Merchantprofile profile) {
           try
        { 
        if(session!=null&&session.isActive())
        {                                            
            manager.persist(profile);              
        }
        else
        {
            EntityManager manager = RepositoryManager.getManager();
            manager.persist(profile);        
            manager.close();                             
         }
        
         }
        catch (NoResultException e) 
         {
        
             System.out.println("Error Occured while logging tranx");                 
         }
    }

    @Override
    public void logTranx(Transactions tranx) {
                            
        try{ 
        if(session!=null&&session.isActive())
        {                                            
            manager.persist(tranx);              
        }
        else
        {
            EntityManager manager = RepositoryManager.getManager();
            manager.persist(tranx);        
            manager.close();                             
         }
        
         } catch (NoResultException e) {
        
             System.out.println("Error Occured while logging tranx");                 
            }
    }

    @Override
    public List<Branch> getBranches(Merchant merchant) {
        if(session!=null&&session.isActive())
        {
        List<Branch> branches = manager.createNamedQuery("Branch.findByGlobalMerchantID", Branch.class).setParameter("globalMerchantID", merchant).getResultList();
        return branches.isEmpty() ? null : branches;            
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Branch> branches = manager.createNamedQuery("Branch.findByGlobalMerchantID", Branch.class).setParameter("globalMerchantID", merchant).getResultList();
        manager.close();
        return branches.isEmpty() ? null : branches;            
        }
    }

    @Override
    public Merchant findMerchant(String globalMerchantID) {
           if(session!=null&&session.isActive())
        {
        List<Merchant> merchant = manager.createNamedQuery("Merchant.findByMerchantID", Merchant.class).setParameter("merchantID", Integer.parseInt(globalMerchantID)).getResultList();
        return merchant.isEmpty() ? null : merchant.get(0);            
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Merchant> merchant = manager.createNamedQuery("Merchant.findByMerchantID", Merchant.class).setParameter("merchantID", Integer.parseInt(globalMerchantID)).getResultList();
        manager.close();
        return merchant.isEmpty() ? null : merchant.get(0);            
        }
    }

    @Override
    public Branch findBranch(Integer branchMerchant) {
           if(session!=null&&session.isActive())
        {
        List<Branch> branch = manager.createNamedQuery("Branch.findByBranchId", Branch.class).setParameter("branchId", branchMerchant).getResultList();
        return branch.isEmpty() ? null : branch.get(0);            
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Branch> branch = manager.createNamedQuery("Branch.findByBranchId", Branch.class).setParameter("branchId", branchMerchant).getResultList();        
        manager.close();
        return branch.isEmpty() ? null : branch.get(0);            
        }
    }

    
}
