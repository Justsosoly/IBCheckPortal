package com.ib.ibcheckweb.service;

import java.util.List;

import com.ib.ibcheckweb.bean.account.Account;

public interface AccountServiceInterface {
	
	
    public void insertAccount(Account account);

    public void update(Account account);

  //  public Account find(int id);
  
    public void delete(int id);
    
    public List<Account> getbyDate(String date);
    
    public List<Account> getbyAccount(String account);

}
