package com.ib.ibcheckweb.mapper;


import java.util.List;
import org.apache.ibatis.annotations.Mapper;


import com.ib.ibcheckweb.bean.account.AccountSummary;

@Mapper
public interface AccountSummaryMapper {
	

	
	public List<AccountSummary> getbyDate(String date);
	
	public List<AccountSummary> getbyAccountID(String acctsummary);
   
	public void insert(AccountSummary acctsummary);

    public void update(AccountSummary acctsummary);

    public AccountSummary find(int id);
    
    public void delete(int id);
  

	
}
