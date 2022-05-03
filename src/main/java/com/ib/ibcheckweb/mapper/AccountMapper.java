package com.ib.ibcheckweb.mapper;

//import java.util.List;
//
//import org.apache.ibatis.annotations.Mapper;
//
//import com.ib.ibcheckweb.bean.account.Account;
//
//
//
//@Mapper
//public interface AccountMapper {
//	
//	public Account getbyDate(String date); 
//	
//	public List<Account> getbyAccount(String account);
//	
//	   
//	   public void insert(Account user);
//
//	    public void update(Account user);
//	    
//	    public void delete(int id);
//	    
//	    public Account find(int id);
//	
//}


import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Results;
import org.springframework.stereotype.Repository;

import com.ib.ibcheckweb.bean.account.Account;

//@Repository
@Mapper
public interface AccountMapper {
	
	//   @Select("select * from PORTFOLIO where data =#{data}")
//	    @Results({
//	            @Result(property = "account",column = "account"),
//	    })
	
	
	public List<Account> getbyDate(String date);
	
	public List<Account> getbyAccount(String account);
   
	public void insertAccount(Account account);

    public void update(Account account);

    public Account find(int id);
    
    public void delete(int id);
  

	
}
