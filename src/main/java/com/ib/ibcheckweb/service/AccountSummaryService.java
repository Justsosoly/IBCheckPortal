package com.ib.ibcheckweb.service;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import com.ib.ibcheckweb.bean.account.AccountSummary;
import com.ib.ibcheckweb.mapper.AccountSummaryMapper;


@ComponentScan({"com.ib.ibcheckweb.mapper.AccountSummaryMapper"})
@Service("AccountSummaryService")
	public class AccountSummaryService implements AccountSummaryServiceInterface{

	    @Resource
	    private AccountSummaryMapper acctSummaryMapper;
	    
	    @Override
	    public void insert(AccountSummary accsummary) {
	    	acctSummaryMapper.insert(accsummary);
	    }

	    public void update(AccountSummary accsummary) {
	    	acctSummaryMapper.update(accsummary);
	    }

	    public AccountSummary find(int id) {
	        return acctSummaryMapper.find(id);
	    }
	    
	    public void delete(int id){
	    	acctSummaryMapper.delete(id);
	    }
	    
	    @Override
	    public List<AccountSummary> getbyDate(String date)
	    {
	    	System.out.println("the service!! getbydate");
	    	return acctSummaryMapper.getbyDate(date);
	    	
	    }
	    
	    
	    @Override
	    public List<AccountSummary> getbyAccountID(String account)
	    {
	    	System.out.println("the service!! getbyaccount");
	    	return acctSummaryMapper.getbyAccountID(account);
	    	
	    }
	}


