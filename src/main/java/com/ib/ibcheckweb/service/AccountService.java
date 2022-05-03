package com.ib.ibcheckweb.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.ib.ibcheckweb.bean.account.Account;
import com.ib.ibcheckweb.mapper.AccountMapper;

@ComponentScan({"com.ib.ibcheckweb.mapper.AccountMapper"})
@Service("AccountService")
	public class AccountService implements AccountServiceInterface{

	    @Resource
	    private AccountMapper accountMapper;
	    
	  
	    
	    @Override
	    public void insertAccount(Account account) {
	    	 accountMapper.insertAccount(account);
	    }

	    public void update(Account user) {
	    	accountMapper.update(user);
	    }

	    public Account find(int id) {
	        return accountMapper.find(id);
	    }
	    
	    public void delete(int id){
	    	accountMapper.delete(id);
	    }
	    
	    @Override
	    public List<Account> getbyDate(String date)
	    {
	    	System.out.println("the service!! getbydate");
	    	return accountMapper.getbyDate(date);
	    	
	    }
	    
	    
	    @Override
	    public List<Account> getbyAccount(String account)
	    {
	    	System.out.println("the service!! getbyaccount");
	    	return accountMapper.getbyAccount(account);
	    	
	    }
	}


