package com.ib.ibcheckweb.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ib.ibcheckweb.service.AccountService;
import com.ib.ibcheckweb.service.check.AccountPosition;
import com.ib.ibcheckweb.service.check.DayDelta;
import com.ib.ibcheckweb.service.check.DealFile;
import com.ib.ibcheckweb.service.check.GetGreek;
import com.ib.ibcheckweb.service.check.GetPosition;
import com.ib.ibcheckweb.bean.account.Account;
import com.ib.ibcheckweb.bean.underlying.Security;

@RestController
//@ComponentScan({"smm.springboot_ftl.service"})
//@MapperScan("smm.springboot_ftl.mapper")
public class IBController {
 

	
    @RequestMapping("/ibcheck")
    public String hello() {
    	
    	
    	
        return "Hello Spring Boot!";
    }
   
    @Resource
    @Autowired
    private AccountService accountService;
    
    @RequestMapping("/getbyDate")
    public void getbyDate() {
     
//        Account account =  accountService.getbyDate("2022/03/26");
//        ModelAndView mav = new ModelAndView();
//       
//        mav.addObject("user","sadf");
       // return "HelloIBCheck"+"the data are--"+account.getDate()+"--"+account.getAccount();
    	
    	   List<Account> list=(List<Account>) accountService.getbyDate("2022/05");

           System.out.println("----------查询数据------");
           list.stream().forEach(item->{
               System.out.println(item.getAccount()+"------>"+item.getDate());
           });
    }
    
    
    
    @RequestMapping("/getbyAccount")
    public void getbyAccount() {
    	  List<Account> list=(List<Account>) accountService.getbyAccount("U10019359");

          System.out.println("----------查询数据------");
          list.stream().forEach(item->{
              System.out.println(item.getAccount()+"------>"+item.getDate());
          });
    	
    }
    
    @RequestMapping("/insertAccount")
    public String insertAccount() throws InterruptedException, IOException {
    
    	//取得position内容文件
		GetPosition gepo= new GetPosition();
		gepo.getPosition();
		
		//获得Market数据文件
		AccountPosition accountposition = new AccountPosition();
		GetGreek getGreek=new GetGreek();
     	String path_all=accountposition.path;//2个账号
     	
		getGreek.DealAllGreek(path_all,6);//port 6 is random
		List<Security> secList = new ArrayList<Security>();
		DayDelta daydelta = new DayDelta();
		DealFile dealfile = new DealFile();

	    //2个账户合并的情况
		List<Security> secPosition = new ArrayList<Security>();
		List<Security> secMarket = new ArrayList<Security>();
		Account account_U9238 = new Account();
		Account account_U1001=new Account();
		secPosition = daydelta.getALLSecruity(dealfile.path_All);
		secMarket = daydelta.getALLSecruity(dealfile.path_AllMarket);
		secList =daydelta.twoinoneSecuity(secPosition, secMarket);//最终所获得信息不放文件中
		
		account_U9238 = daydelta.getALLPflioDelta(secList,"U9238923");// 获取并封装账户相关的Greek等信息
		daydelta.getALLCloseOptionList(secList,"U9238923");// 提示获取时间价值的Option
		daydelta.getNeutralOPNum(daydelta.getMapofSecList(secList,"U9238923"));
		dealfile.ResultWriteTOExcel(account_U9238);// 将每个账户写入excel里
		
		account_U1001 =daydelta.getALLPflioDelta(secList,"U10019359");
		daydelta.getALLCloseOptionList(secList,"U10019359");// 提示获取时间价值的Option
		daydelta.getNeutralOPNum(daydelta.getMapofSecList(secList,"U10019359"));
		dealfile.ResultWriteTOExcel(account_U1001);// 将每个账户写入excel里
    	
    	
    	
    	   accountService.insertAccount(account_U9238);
    	   accountService.insertAccount(account_U1001);

    	   return "----------插入数据------";
        
    	
    }
    
}
