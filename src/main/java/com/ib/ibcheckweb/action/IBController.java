package com.ib.ibcheckweb.action;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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


//@RestController
@Controller
@RequestMapping
public class IBController {
 
    @Autowired
    private AccountService accountService;
	
    @RequestMapping("/ibcheck")
    public String hello() {
        return "ibcheck";
    }
    @GetMapping("makeLineAndShapeChart")
    public String makeLineAndShapeChart(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        System.out.println("已进");
        // 定义图表对象数据，数据
        DefaultCategoryDataset  dataset  =createDataset();
        JFreeChart  chart = ChartFactory.createLineChart(
                "折线图", // chart title
                "时间", // domain axis label
                "销售额(百万)", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips
                false // urls
        );
 
        CategoryPlot plot = chart.getCategoryPlot();
        // 设置图示字体
        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 22));
        //设置横轴的字体
        CategoryAxis categoryAxis = plot.getDomainAxis();
        categoryAxis.setLabelFont(new Font("宋体", Font.BOLD, 22));//x轴标题字体
        categoryAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 16));//x轴刻度字体
 
        //以下两行 设置图例的字体
        LegendTitle legend = chart.getLegend(0);
        legend.setItemFont(new Font("宋体", Font.BOLD, 14));
        //设置竖轴的字体
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("宋体" , Font.BOLD , 19)); //设置数轴的字体
        rangeAxis.setTickLabelFont(new Font("宋体" , Font.BOLD , 16));
        LineAndShapeRenderer lasp = (LineAndShapeRenderer) plot.getRenderer();
        lasp.setBaseShapesVisible(true);
        lasp.setDrawOutlines(true);
        // 设置线条是否被显示填充颜色
        lasp.setUseFillPaint(false);
        // 设置拐点颜色
        lasp.setBaseFillPaint(Color.red);//蓝色
        rangeAxis.setAxisLinePaint(Color.black);
        // 设置背景颜色
        plot.setBackgroundPaint(Color.white);
        // 设置网格竖线颜色
        plot.setDomainGridlinePaint(Color.pink);
        // 设置网格横线颜色
        plot.setRangeGridlinePaint(Color.pink);
 
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());//去掉竖轴字体显示不全
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setUpperMargin(0.20);
        rangeAxis.setLabelAngle(Math.PI / 2.0);
 
        LineAndShapeRenderer  renderer =  (LineAndShapeRenderer) plot
                .getRenderer();
        renderer.setSeriesPaint(0, Color.BLACK);//折线颜色，下标0始
 
        // 6. 将图形转换为图片，传到前台
        String fileName = ServletUtilities.saveChartAsJPEG(chart, 1000, 400, null, request.getSession());
        String chartURL = request.getContextPath() + "/chart?filename=" + fileName;
        model.addAttribute("makeLineAndShapeChart", chartURL);
 
        return "LineChart";
    }

   
    // 生成数据
    public static DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
 
        // 各曲线名称
        String [] series= {"冰箱","彩电","菠萝"};
        // 横轴名称(列名称)
        String [] month = {"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
        //具体数据
//      为了方便你们操作成功，这里数据写死
        double [] num1 = {4,5,6,10,1,2,3,7,4,2,8,3};
        double [] num2 = {10,15,20,16,7,4,7,11,21,42,54,32};
        double [] num3 = {10,18,25,19,43,12,54,22,24,65,11,32};
        //将多个数组合并，且不打乱顺序
        double[] num= new double[num1.length + num2.length + num3.length];
        System.arraycopy(num1, 0, num, 0, num1.length);
        System.arraycopy(num2, 0, num, num1.length, num2.length);
        System.arraycopy(num3, 0, num, num1.length+num2.length, num3.length);
        int l1 = num.length/series.length; //4
        int l2 = month.length;
        int j=0;
        for(int i=0;i<num.length;i++) {
            linedataset.addValue(num[i], series[i/l1], month[j]);
            j++;
            if(j==month.length)
                j=0;
        }
        return linedataset;
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
    public String index(Model model) {
    	 List<Account> list=(List<Account>) accountService.getbyAccount("U10019359");
   	     model.addAttribute("accounts",list);
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

    	   //向页面themaleaf传递2个account对象
    	   model.addAttribute("accounts_U9238",account_U9238);
    	   model.addAttribute("account_U1001",account_U1001);
    	   
    	   
    	   return "insertAccount";
        
    	
    }
    
}
