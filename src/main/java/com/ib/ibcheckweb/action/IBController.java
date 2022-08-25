package com.ib.ibcheckweb.action;


import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.servlet.ServletUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ib.ibcheckweb.service.AccountService;
import com.ib.ibcheckweb.service.AccountSummaryService;
import com.ib.ibcheckweb.service.check.AccountPosition;
import com.ib.ibcheckweb.service.check.DayDelta;
import com.ib.ibcheckweb.service.check.DealFile;
import com.ib.ibcheckweb.service.check.GetGreek;
import com.ib.ibcheckweb.service.check.GetPosition;
import com.ib.ibcheckweb.service.jfreechart.JfreeChartUtil;
import com.ib.ibcheckweb.bean.account.Account;
import com.ib.ibcheckweb.bean.account.AccountSummary;
import com.ib.ibcheckweb.bean.underlying.Security;


//@RestController
@Controller
@RequestMapping
public class IBController {
	  int  width=6000;
      int  height=600;
 
    @Autowired
    private  AccountService accountService;
    
    @Autowired
    private  AccountSummaryService accountsummaryService;
    
	
    @RequestMapping("/ibcheck")
    public String hello() {
        return "ibcheck";
    }
    
    //双Y轴delta+nasdaq
    @GetMapping("doubleYChart")
    public String makeDoubleYChart(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
          JfreeChartUtil jutil=new JfreeChartUtil();
          List<Account> list=(List<Account>) accountService.getbyAccount("U10019359");
          JFreeChart chart= jutil.doubleYChart(list);
        
	
        // 6. 将图形转换为图片，传到前台
        String fileName = ServletUtilities.saveChartAsJPEG(chart, width, height, null, request.getSession());
        String chartURL = request.getContextPath() + "/chart?filename=" + fileName;
        model.addAttribute("makeDeltaNasdaqChart", chartURL);
          //对应 delta_nasdaq.html
        return "delta_nasdaq";
    }
    
    
    
    
    //双Y轴delta_netliquidation_nasdaq
    @GetMapping("doubleYValueNasChart")
    public String makeDoubleYValueNasChart(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
          JfreeChartUtil jutil=new JfreeChartUtil();
          List<Account> accountlist=(List<Account>) accountService.getbyAccount("U10019359");
          List<AccountSummary> accsumlist= accountsummaryService.getbyAccountID("U10019359");
          JFreeChart chart= jutil.doubleYValueNasChart(accsumlist,accountlist);
        
	
        // 6. 将图形转换为图片，传到前台
        String fileName = ServletUtilities.saveChartAsJPEG(chart, width, height, null, request.getSession());
        String chartURL = request.getContextPath() + "/chart?filename=" + fileName;
        model.addAttribute("makeDeltaNetliquidationNasdaqChart", chartURL);
          //对应 delta_netliquidation_nasdaq.html
        return "delta_netliquidation_nasdaq";
    }
    
    
    //双Y轴delta_theta_vega
    @GetMapping("doubleYTHeVeChart")
    public String makeDoubleYTHeVeChart(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
          JfreeChartUtil jutil=new JfreeChartUtil();
          List<Account> list=(List<Account>) accountService.getbyAccount("U10019359");
          JFreeChart chart= jutil.doubleYTHVegaChart(list);
	
        // 6. 将图形转换为图片，传到前台
        String fileName = ServletUtilities.saveChartAsJPEG(chart, width, height, null, request.getSession());
        String chartURL = request.getContextPath() + "/chart?filename=" + fileName;
        model.addAttribute("makeDeltaThetaVegaChart", chartURL);
          //对应 delta_theta_vega.html
        return "delta_theta_vega";
    }
    
    
    

    //时序图 
    @GetMapping("timeSeriesChart")
    public String makeTimeSeriesChart(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    
          JfreeChartUtil jutil=new JfreeChartUtil();
          List<Account> list=(List<Account>) accountService.getbyAccount("U10019359");
          JFreeChart chart= jutil.timeSeriesChart(list);
	
        // 6. 将图形转换为图片，传到前台
        String fileName = ServletUtilities.saveChartAsJPEG(chart, width, height, null, request.getSession());
        String chartURL = request.getContextPath() + "/chart?filename=" + fileName;
        model.addAttribute("makeLineAndShapeChart", chartURL);
          //对应 LineChart.html
        return "LineChart";
    }
	
    
    //折线图 theta+vega，需要用accountDataset方法的数据集
    @GetMapping("lineChart")
    public String makeLineChart(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
 
          JfreeChartUtil jutil=new JfreeChartUtil();
          List<Account> list=(List<Account>) accountService.getbyAccount("U10019359");
          JFreeChart chart= jutil.lineChart(list);
	
        // 6. 将图形转换为图片，传到前台
        String fileName = ServletUtilities.saveChartAsJPEG(chart, width, height, null, request.getSession());
        String chartURL = request.getContextPath() + "/chart?filename=" + fileName;
        model.addAttribute("makeLineAndShapeChart", chartURL);
 
        return "LineChart";
    }
    
    
    
    
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
    
    
    /*
   // @RequestMapping("/getbyAccount")
    public void getbyAccount() {
    	  List<Account> list=(List<Account>) accountService.getbyAccount("U10019359");
    	  model.addAttribute("getbyAccount",list);
          System.out.println("----------查询数据------");
          list.stream().forEach(item->{
              System.out.println(item.getAccount()+"------>"+item.getDate());
          });
    	
    }
  */
    
    
    
	@RequestMapping("/index")
    public String index(Model model) throws InterruptedException {

        return "index";
    }
   
    
    @RequestMapping("/insertAccount")
    public String insertAccount(Model model) throws InterruptedException, IOException {
    	
    	//取得position内容文件
		GetPosition gepo= new GetPosition();
		gepo.getPosition();
		
		//获得Market数据文件
		AccountPosition accountposition = new AccountPosition();
		GetGreek getGreek=new GetGreek();
     	String path_all=accountposition.path;//2个账号
     	String path_summary=accountposition.path_AccountSummary;
     	
     	
		getGreek.DealAllGreek(path_all,6);//port 6 is random
		List<Security> secList = new ArrayList<Security>();
		DayDelta daydelta = new DayDelta();
		DealFile dealfile = new DealFile();

	    //2个账户合并的情况
		List<Security> secPosition = new ArrayList<Security>();
		List<Security> secMarket = new ArrayList<Security>();
		Account account_U9238 = new Account();
		Account account_U1001=new Account();
		AccountSummary accsumm_U9238 = new AccountSummary();
		AccountSummary accsumm_U1001 = new AccountSummary();
		
		//daydelta.selfPrint();//system.out.print's content print from console to the txt file
		
		
		 PrintStream out;
	     out = new PrintStream(dealfile.path_All+"print");
	     System.setOut(out);
	     
		secPosition = daydelta.getALLSecruity(dealfile.path_All);//获取2个账号所有的position
		secMarket = daydelta.getALLSecruity(dealfile.path_AllMarket);//获取2个账号全部标的市场价格，GREEK等信息
		//通常market的标的数量会比positon的少，因为position里2个账号可能会共有标的，所以在marke里是一个 
		secList =daydelta.twoinoneSecuity(secPosition, secMarket);//最终所获得信息不放文件中
		
		account_U9238 = daydelta.getALLPflioDelta(secList,"U9238923");// 获取并封装账户相关的Greek等信息
		daydelta.getALLCloseOptionList(secList,"U9238923");// 提示获取时间价值的Option
		daydelta.getNeutralOPNum(daydelta.getMapofSecList(secList,"U9238923"));//get the num if the underlying want to neutral
		dealfile.ResultWriteTOExcel(account_U9238);// 将每个账户写入excel里
		
		
		account_U1001 =daydelta.getALLPflioDelta(secList,"U10019359");
		daydelta.getALLCloseOptionList(secList,"U10019359");// 提示获取时间价值的Option
		daydelta.getNeutralOPNum(daydelta.getMapofSecList(secList,"U10019359"));
		dealfile.ResultWriteTOExcel(account_U1001);// 将每个账户写入excel里
	    
		out.close();//close the print,so the exception not in the file
		
		accsumm_U9238=daydelta.getAccountSummary(path_summary+"U9238923");
		accsumm_U1001=daydelta.getAccountSummary(path_summary+"U10019359");
		
		//写数据库 
    	   accountService.insertAccount(account_U9238);
    	   accountService.insertAccount(account_U1001);
    	   accountsummaryService.insert(accsumm_U9238);
    	   accountsummaryService.insert(accsumm_U1001);
    	 

    	   //向页面themaleaf传递2个account对象
    	   model.addAttribute("accounts_U9238",account_U9238);
    	   model.addAttribute("account_U1001",account_U1001);
    	   
    	   
    	   return "insertAccount";
        
    	
    }
    
    
    
    
    
    
   
    
}
