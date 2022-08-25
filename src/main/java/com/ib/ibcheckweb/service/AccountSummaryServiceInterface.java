package com.ib.ibcheckweb.service;

import java.util.List;

import com.ib.ibcheckweb.bean.account.AccountSummary;


public interface AccountSummaryServiceInterface {
	
	
    public void insert(AccountSummary accsummary);

    public void update(AccountSummary accsummary);

  //  public Account find(int id);
  
    public void delete(int id);
    
    public List<AccountSummary> getbyDate(String date);
    
    public List<AccountSummary> getbyAccountID(String accountid);

}
