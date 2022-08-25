package com.ib.ibcheckweb.service.jfreechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.TextAnchor;

import com.ib.ibcheckweb.bean.account.Account;
import com.ib.ibcheckweb.bean.account.AccountSummary;
import com.ib.ibcheckweb.service.check.DealFile;

public class JfreeChartUtil {

	// 时序图1
	public JFreeChart timeSeriesChart( List<Account> list) throws Exception {
		// 时序图
		TimeSeries series = new TimeSeries("数据标签");
		// XYDataset dataset=(XYDataset)new TimeSeriesCollection(series);
		XYDataset dataset = timeDataset(list);// 来自定义的方法
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"风险杠杆图", // 图表标题
				"日期", // y轴方向数据标签
				"Delta", // x轴方向数据标签
				dataset, // 数据集，即要显示在图表上的数据
				true, // 是否显示图例
				true, // 是否显示提示
				false// 是否生成URL连接
		);

		chart.setBackgroundPaint(Color.white);
		XYPlot plot = chart.getXYPlot();
		setXYPolt(plot);

		return chart;
	}
	
	// 时序图2
	public static void setXYPolt(XYPlot plot) {
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}
	}

	// 时序图3 数据集
	public XYDataset timeDataset( List<Account> list) throws Exception {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		// 各曲线名称
		String[] series = { "Theta", "Vega", "Gamma" };

		// get the attribute of list to array[]
		int theta[] = list.stream().mapToInt(Account::getPortfoliotheta).toArray();
		int vega[] = list.stream().mapToInt(Account::getPortfoliovega).toArray();
		int gamma[] = list.stream().mapToInt(Account::getPortfolioGamma).toArray();
		// 横轴名称
		String date[] = list.stream().map(Account::getDate).toArray(String[]::new);

		TimeSeries deltatimeseries = new TimeSeries("Delta");
		for (int i = 0; i < list.size(); i++) {
			SimpleDateFormat standardDateFormat = new SimpleDateFormat(("yyyy/MM/dd/HH:mm"));
			Date myDate = standardDateFormat.parse(date[i]);
			deltatimeseries.add(new Day(myDate), theta[i]);

		}
		dataset.addSeries(deltatimeseries);

		return dataset;
	}
	
	
	
	
	
	//折线图1 theta+vega
	public JFreeChart lineChart( List<Account> list) throws Exception {
		
		 // 定义图表对象数据，数据
        DefaultCategoryDataset  dataset  =accountDataset(list);
        JFreeChart  chart = ChartFactory.createLineChart(
                "GREEK图", // chart title
                "时间", // domain axis label
                "Value", // range axis label
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
		
		return chart;
	
	}
	
	//折线图2 数据集 
    public  DefaultCategoryDataset accountDataset(List<Account> list) {
        DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
    	
        // 各曲线名称
        String [] series= {"Theta","Vega"};
       //get the attribute of list to array[]
           
       int  theta[]=    list.stream().mapToInt(Account::getPortfoliotheta).toArray();
       int  vega[]=    list.stream().mapToInt(Account::getPortfoliovega).toArray();
     //  int  gamma[]= list.stream().mapToInt(Account::getPortfolioGamma).toArray();
       
       // 横轴名称
       String  date[]=list.stream().map(Account::getDate).toArray(String[]::new);
        //将多个数组合并，且不打乱顺序
        int shownum=theta.length+vega.length;
        int showall[]=new int[shownum];
        System.arraycopy(theta,0,showall,0,theta.length);
        System.arraycopy(vega,0,showall,theta.length,vega.length);
        //System.arraycopy(gamma,0,showall,vega.length,gamma.length);
        
        
        
        int rows = shownum/series.length; //date
        int j=0;
        for(int i=0;i<shownum;i++) {
        
            linedataset.addValue(showall[i], series[i/rows], date[j].substring(6,10));
            j++;
            if(j==date.length)
                j=0;
        }
        return linedataset;
    }
	
    //delta_netliquidation_nasdaq
    public JFreeChart doubleYValueNasChart (List<AccountSummary> accsumlist,List<Account> accountlist) throws Exception {
    {
    	
    	 JFreeChart chart = ChartFactory.
   			  createStackedBarChart(
   					  "Value Nadaq ratio", 
   					  "日期", 
   					  "Value", 
   					  null, 
   					  PlotOrientation.VERTICAL, 
   					  true, 
   					  true, 
   					  false);
   	  
         {
  
             // 获取绘图区对象
             CategoryPlot plot = (CategoryPlot) chart.getPlot();
             
             //柱体 
             ExtendedStackedBarRenderer extendedstackedbarrenderer = new ExtendedStackedBarRenderer(new DecimalFormat()); 

             //柱体标签是否可见 
             
             extendedstackedbarrenderer.setBaseItemLabelsVisible(true); 
             Font labelFont = new Font("宋体", Font.PLAIN, 2); 
             //%比的显示是白色
             extendedstackedbarrenderer.setBaseItemLabelPaint(new GradientPaint(0.0f, 0.0f, new Color(255, 255, 255), 0.0f, 0.0f, new Color(0, 0, 0))); 

             //设置柱体标签值的格式 

             ExtendedStandardCategoryItemLabelGeneratory generator = new ExtendedStandardCategoryItemLabelGeneratory("{3}", 

             NumberFormat.getPercentInstance(), new DecimalFormat("##%"),true);  
             extendedstackedbarrenderer.setBaseItemLabelGenerator(generator); 
            
             
             // 设置每个地区所包含的平行柱的之间距离 
             extendedstackedbarrenderer.setItemMargin(0.7);
             // 设置柱子高度    
             extendedstackedbarrenderer.setMinimumBarLength(0.8);
             // 设置柱子宽度    
             extendedstackedbarrenderer.setMaximumBarWidth(0.9);
           //注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
             extendedstackedbarrenderer.setItemLabelAnchorOffset(0);
             
             
             plot.setRenderer(extendedstackedbarrenderer); 
             
             
             // 设置轴1--数据匹配
             NumberAxis axis0 = new NumberAxis("Profolio Delta");
             plot.setRangeAxis(0, axis0);
             plot.setDataset(0, createBarValueNasDataset(accsumlist,accountlist));
             plot.mapDatasetToRangeAxis(0, 0);
  
             // 重新生成一个图表渲染的对象（折线图渲染对象）。
             LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
             {
  
                 // 显示折点数据。
                 lineandshaperenderer.setBaseItemLabelsVisible(true);
                 lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
  
                 {
                     // 设置拐点是否可见/是否显示拐点
                     lineandshaperenderer.setBaseShapesVisible(true);
  
                     // 设置线条是否被显示填充颜色
                     lineandshaperenderer.setUseFillPaint(true);
  
                     {
                         // 设置第一条折线的拐点颜色
                         lineandshaperenderer.setSeriesFillPaint(0, Color.GREEN);
                         // 设置第二条折线的拐点颜色
                         lineandshaperenderer.setSeriesFillPaint(1, Color.BLUE);
                     }
                     {
                         //设置折线颜色(第一条折线数据线)
                         lineandshaperenderer.setSeriesPaint(0, new Color(91, 155, 213));
                         //设置折线颜色(第二条折折线据线)
                         lineandshaperenderer.setSeriesPaint(1, Color.GREEN);
                     }
                     {
                         // 设置第一条折线的广度（粗细度）
                         lineandshaperenderer.setSeriesStroke(0, new BasicStroke(1.8F));
  
                         // 设置第二条折线的广度（粗细度）
                         lineandshaperenderer.setSeriesStroke(1, new BasicStroke(1.8F));
                     }
                     {
                         //设置拐点数值颜色，默认黑色
                         lineandshaperenderer.setBaseItemLabelsVisible(false); // 默认就是true，这里可以不用刻意声明。
                         lineandshaperenderer.setBaseItemLabelPaint(Color.GREEN);
                     }
                     {
                         // 解决最高柱体或折点提示内容被遮盖的问题。
                         lineandshaperenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
                         lineandshaperenderer.setItemLabelAnchorOffset(2); // 设置柱形图上的文字偏离值
                     }
                 }
             }//LineAndShapeRenderer
  
           
  
             // 设置轴2--数据匹配
             NumberAxis axis1 = new NumberAxis("Nasdaq");
             axis1.setRange(10000, 15000);
             plot.setRangeAxis(1, axis1);
             plot.setDataset(1, createLineValueNasDataset(accsumlist,accountlist));
             plot.mapDatasetToRangeAxis(1, 1);
             plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
             
             // 重构第二个数据对象的渲染方式，由现在默认的Bar（柱状统计图）重构为刚刚初始化的Line（折线统计图）的渲染模式
             plot.setRenderer(1, lineandshaperenderer);
  
             /** ---------------------- 中文乱码问题处理 Start ------------------------------- */
             CategoryAxis domainAxis = plot.getDomainAxis();     //水平底部列表
             domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 14));     //水平底部标题
             domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12)); //垂直标题
  
             ValueAxis rangeAxis = plot.getRangeAxis();//获取柱状
             rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
             chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
             chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));//设置标题字体
             /** ---------------------- 中文乱码问题处理 End ------------------------------- */
  
             {
  
             //    rangeAxis.setAutoRange(true);
             }
         }
  
         // 设置图表控件的背景颜色。
         chart.setBackgroundPaint(Color.WHITE);
   	  
   	return chart;
    }
    	
    }

    
    //delta_netliquidation_nasdaq
    private DefaultCategoryDataset createBarValueNasDataset(List<AccountSummary> accsummlist,List<Account> accountlist) throws ParseException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DealFile dealfile=new DealFile();
        //get the attribute of list to array[] from account
        int  delta[]=accountlist.stream().mapToInt(Account::getPortfolio_Delta).toArray();
        String  date[]=accountlist.stream().map(Account::getDate).toArray(String[]::new);
        String  shortdate[]=new String[date.length];
       
       
        // String  value[]=accsummlist.stream().map(AccountSummary::getNetLiquidationByCurrency).toArray(String[]::new);
        String  value[]=new String[accsummlist.size()];
        DatasetGroup datasetGroup = new DatasetGroup();
        dataset.setGroup(datasetGroup);
        for(int i=0;i<date.length;i++)
        {   
        	//short the date format to show in the chart
        	shortdate[i]=date[i].substring(6,10);
        	//transfor YYYY/MM/DD/HH:MM to YYYYMMDD
        	String seldate=dealfile.transformDate(date[i]);
        	 for(int j=0;j<accsummlist.size();j++)
        	 {
        		 if(accsummlist.get(j).getDate().equals(seldate))
        		 {
        			 value[i]=accsummlist.get(j).getNetLiquidationByCurrency();
        			 break;
        		 }
        	 }
        	if(i>1&&value[i]==null&&value[i-1]!=null)//the two list date is not coinside
        		value[i]=value[i-1];
        		
        	
        	int temp=(int)Math.round(Double.parseDouble(value[i]));
        	int lever=delta[i]-temp;
        	if(lever<0) lever=0;
        dataset.setValue(temp,"NetLiquidation", shortdate[i]);
        dataset.setValue(lever,"Delta-Value", shortdate[i]);
        }
        
   
        return dataset;
    }
    
    
    
    
    
    
    //delta_netliquidation_nasdaq  
    private DefaultCategoryDataset createLineValueNasDataset(List<AccountSummary> accsummlist,List<Account> list) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int  nasdaq[]= list.stream().mapToInt(Account::getNasdaq).toArray();
        // 横轴名称
        String  date[]=list.stream().map(Account::getDate).toArray(String[]::new);
        String  shortdate[]=new String[date.length];
        DatasetGroup datasetGroup = new DatasetGroup();
        dataset.setGroup(datasetGroup);
        for(int i=0;i<date.length;i++)
        {
        	shortdate[i]=date[i].substring(6,10);
        dataset.setValue(nasdaq[i],"Nasdaq", shortdate[i]);
        }
        return dataset;
    }
    
    
    
    
    
    
    
    
    //双Y轴1 nasdaq+delta
    public JFreeChart doubleYChart( List<Account> list) throws Exception {
    
    	  JFreeChart chart = ChartFactory.
    			  createStackedBarChart(
    					  "Risk Ratio Double Y", 
    					  "日期", 
    					  "Value", 
    					  null, 
    					  PlotOrientation.VERTICAL, 
    					  true, 
    					  true, 
    					  false);
    	  
          {
   
              // 获取绘图区对象
              CategoryPlot plot = (CategoryPlot) chart.getPlot();
              
              //柱体 
              ExtendedStackedBarRenderer extendedstackedbarrenderer = new ExtendedStackedBarRenderer(new DecimalFormat()); 

              //柱体标签是否可见 
              
              extendedstackedbarrenderer.setBaseItemLabelsVisible(true); 
              Font labelFont = new Font("宋体", Font.PLAIN, 2); 
              //%比的显示是白色
              extendedstackedbarrenderer.setBaseItemLabelPaint(new GradientPaint(0.0f, 0.0f, new Color(255, 255, 255), 0.0f, 0.0f, new Color(0, 0, 0))); 

              //设置柱体标签值的格式 

              ExtendedStandardCategoryItemLabelGeneratory generator = new ExtendedStandardCategoryItemLabelGeneratory("{3}", 

              NumberFormat.getPercentInstance(), new DecimalFormat("##%"),true);  
              extendedstackedbarrenderer.setBaseItemLabelGenerator(generator); 
             
              
              // 设置每个地区所包含的平行柱的之间距离 
              extendedstackedbarrenderer.setItemMargin(0.7);
              // 设置柱子高度    
              extendedstackedbarrenderer.setMinimumBarLength(0.8);
              // 设置柱子宽度    
              extendedstackedbarrenderer.setMaximumBarWidth(0.9);
            //注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
              extendedstackedbarrenderer.setItemLabelAnchorOffset(0);
              
              
              plot.setRenderer(extendedstackedbarrenderer); 
              
              
              // 设置轴1--数据匹配
              NumberAxis axis0 = new NumberAxis("Profolio Delta");
              plot.setRangeAxis(0, axis0);
              plot.setDataset(0, createBarDataset(list));
              plot.mapDatasetToRangeAxis(0, 0);
   
              // 重新生成一个图表渲染的对象（折线图渲染对象）。
              LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
              {
   
                  // 显示折点数据。
                  lineandshaperenderer.setBaseItemLabelsVisible(true);
                  lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
   
                  {
                      // 设置拐点是否可见/是否显示拐点
                      lineandshaperenderer.setBaseShapesVisible(true);
   
                      // 设置线条是否被显示填充颜色
                      lineandshaperenderer.setUseFillPaint(true);
   
                      {
                          // 设置第一条折线的拐点颜色
                          lineandshaperenderer.setSeriesFillPaint(0, Color.GREEN);
                          // 设置第二条折线的拐点颜色
                          lineandshaperenderer.setSeriesFillPaint(1, Color.BLUE);
                      }
                      {
                          //设置折线颜色(第一条折线数据线)
                          lineandshaperenderer.setSeriesPaint(0, new Color(91, 155, 213));
                          //设置折线颜色(第二条折折线据线)
                          lineandshaperenderer.setSeriesPaint(1, Color.GREEN);
                      }
                      {
                          // 设置第一条折线的广度（粗细度）
                          lineandshaperenderer.setSeriesStroke(0, new BasicStroke(1.8F));
   
                          // 设置第二条折线的广度（粗细度）
                          lineandshaperenderer.setSeriesStroke(1, new BasicStroke(1.8F));
                      }
                      {
                          //设置拐点数值颜色，默认黑色
                          lineandshaperenderer.setBaseItemLabelsVisible(false); // 默认就是true，这里可以不用刻意声明。
                          lineandshaperenderer.setBaseItemLabelPaint(Color.GREEN);
                      }
                      {
                          // 解决最高柱体或折点提示内容被遮盖的问题。
                          lineandshaperenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
                          lineandshaperenderer.setItemLabelAnchorOffset(2); // 设置柱形图上的文字偏离值
                      }
                  }
              }//LineAndShapeRenderer
   
            
   
              // 设置轴2--数据匹配
              NumberAxis axis1 = new NumberAxis("Nasdaq");
              axis1.setRange(10000, 15000);
              plot.setRangeAxis(1, axis1);
              plot.setDataset(1, createLineDataset(list));
              plot.mapDatasetToRangeAxis(1, 1);
              plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
              
              // 重构第二个数据对象的渲染方式，由现在默认的Bar（柱状统计图）重构为刚刚初始化的Line（折线统计图）的渲染模式
              plot.setRenderer(1, lineandshaperenderer);
   
              /** ---------------------- 中文乱码问题处理 Start ------------------------------- */
              CategoryAxis domainAxis = plot.getDomainAxis();     //水平底部列表
              domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 14));     //水平底部标题
              domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12)); //垂直标题
   
              ValueAxis rangeAxis = plot.getRangeAxis();//获取柱状
              rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
              chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
              chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));//设置标题字体
              /** ---------------------- 中文乱码问题处理 End ------------------------------- */
   
              {
   
              //    rangeAxis.setAutoRange(true);
              }
          }
   
          // 设置图表控件的背景颜色。
          chart.setBackgroundPaint(Color.WHITE);
    	  
    	return chart;
    }
    
    
    //双Y轴2 nasdaq+delta
    private DefaultCategoryDataset createBarDataset(List<Account> list) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
 
        //get the attribute of list to array[]
        int  optiondelta[]=list.stream().mapToInt(Account::getOption_delta).toArray();
        int  stockdelta[]=list.stream().mapToInt(Account::getStock_delta).toArray();
        // 横轴名称
        String  date[]=list.stream().map(Account::getDate).toArray(String[]::new);
        String  shortdate[]=new String[date.length];
        DatasetGroup datasetGroup = new DatasetGroup();
        dataset.setGroup(datasetGroup);
        for(int i=0;i<date.length;i++)
        {
        	shortdate[i]=date[i].substring(6,10);
        dataset.setValue(stockdelta[i],"StockDelta", shortdate[i]);
        dataset.setValue(optiondelta[i],"OptionDelta", shortdate[i]);
        }
        
   
        return dataset;
    }
    
    //双Y轴3 nasdaq+delta
    private DefaultCategoryDataset createLineDataset(List<Account> list) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int  nasdaq[]= list.stream().mapToInt(Account::getNasdaq).toArray();
        int  spy[]=    list.stream().mapToInt(Account::getSpx).toArray();
      
        
        // 横轴名称
        String  date[]=list.stream().map(Account::getDate).toArray(String[]::new);
        String  shortdate[]=new String[date.length];
        DatasetGroup datasetGroup = new DatasetGroup();
        dataset.setGroup(datasetGroup);
        for(int i=0;i<date.length;i++)
        {
        	shortdate[i]=date[i].substring(6,10);
        dataset.setValue(nasdaq[i],"Nasdaq", shortdate[i]);
       // dataset.setValue(spy[i],"SPY", date[i]);
        }
        return dataset;
    }
    
    
    
    
    //theta+vega 双Y轴1
    public JFreeChart doubleYTHVegaChart( List<Account> list) throws Exception {
    
    	  JFreeChart chart = ChartFactory.
    			  createStackedBarChart(
    					  "GREEK", 
    					  "日期", 
    					  "Value", 
    					  null, 
    					  PlotOrientation.VERTICAL, 
    					  true, 
    					  true, 
    					  false);
    	  
          {
   
              // 获取绘图区对象
              CategoryPlot plot = (CategoryPlot) chart.getPlot();
              
              //柱体 
              ExtendedStackedBarRenderer extendedstackedbarrenderer = new ExtendedStackedBarRenderer(new DecimalFormat()); 

              //柱体标签是否可见 
              
              extendedstackedbarrenderer.setBaseItemLabelsVisible(true); 
              Font labelFont = new Font("宋体", Font.PLAIN, 2); 
              //%比的显示是白色
              extendedstackedbarrenderer.setBaseItemLabelPaint(new GradientPaint(0.0f, 0.0f, new Color(255, 255, 255), 0.0f, 0.0f, new Color(0, 0, 0))); 

              //设置柱体标签值的格式 

              ExtendedStandardCategoryItemLabelGeneratory generator = new ExtendedStandardCategoryItemLabelGeneratory("{3}", 

              NumberFormat.getPercentInstance(), new DecimalFormat("##%"),true);  
              extendedstackedbarrenderer.setBaseItemLabelGenerator(generator); 
             
              
              // 设置每个地区所包含的平行柱的之间距离 
              extendedstackedbarrenderer.setItemMargin(0.7);
              // 设置柱子高度    
              extendedstackedbarrenderer.setMinimumBarLength(0.8);
              // 设置柱子宽度    
              extendedstackedbarrenderer.setMaximumBarWidth(0.9);
            //注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
              extendedstackedbarrenderer.setItemLabelAnchorOffset(0);
              
              
              plot.setRenderer(extendedstackedbarrenderer); 
              
              
              // 设置轴1--数据匹配
              NumberAxis axis0 = new NumberAxis("Theta");
              plot.setRangeAxis(0, axis0);
              plot.setDataset(0, createBarDataset(list));//和Nasdaq+Delta同图
              plot.mapDatasetToRangeAxis(0, 0);
   
              // 重新生成一个图表渲染的对象（折线图渲染对象）。
              LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
              {
   
                  // 显示折点数据。
                  lineandshaperenderer.setBaseItemLabelsVisible(true);
                  lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
   
                  {
                      // 设置拐点是否可见/是否显示拐点
                      lineandshaperenderer.setBaseShapesVisible(true);
   
                      // 设置线条是否被显示填充颜色
                      lineandshaperenderer.setUseFillPaint(true);
   
                      {
                          // 设置第一条折线的拐点颜色
                          lineandshaperenderer.setSeriesFillPaint(0, Color.GREEN);
                          // 设置第二条折线的拐点颜色
                          lineandshaperenderer.setSeriesFillPaint(1, Color.YELLOW);
                      }
                      {
                          //设置折线颜色(第一条折线数据线) lineandshaperenderer.setSeriesPaint(0, new Color(91, 155, 213));
                          lineandshaperenderer.setSeriesPaint(0,new Color(91, 155, 213));
                          //设置折线颜色(第二条折折线据线)
                          lineandshaperenderer.setSeriesPaint(1, Color.YELLOW);
                      }
                      {
                          // 设置第一条折线的广度（粗细度）
                          lineandshaperenderer.setSeriesStroke(0, new BasicStroke(1.8F));
   
                          // 设置第二条折线的广度（粗细度）
                          lineandshaperenderer.setSeriesStroke(1, new BasicStroke(1.8F));
                      }
                      {
                          //设置拐点数值颜色，默认黑色
                          lineandshaperenderer.setBaseItemLabelsVisible(false); // 默认就是true，这里可以不用刻意声明。
                          lineandshaperenderer.setBaseItemLabelPaint(Color.GREEN);
                      }
                      {
                          // 解决最高柱体或折点提示内容被遮盖的问题。
                          lineandshaperenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
                          lineandshaperenderer.setItemLabelAnchorOffset(2); // 设置柱形图上的文字偏离值
                      }
                  }
              }//LineAndShapeRenderer
   
            
   
              // 设置轴2--数据匹配
              NumberAxis axis1 = new NumberAxis("Value");
          //    axis1.setRange(10000, 15000);
              plot.setRangeAxis(1, axis1);
              plot.setDataset(1, createThetaVegaDataset(list));//
              plot.mapDatasetToRangeAxis(1, 1);
              plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
              
              // 重构第二个数据对象的渲染方式，由现在默认的Bar（柱状统计图）重构为刚刚初始化的Line（折线统计图）的渲染模式
              plot.setRenderer(1, lineandshaperenderer);
   
              /** ---------------------- 中文乱码问题处理 Start ------------------------------- */
              CategoryAxis domainAxis = plot.getDomainAxis();     //水平底部列表
              domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 14));     //水平底部标题
              domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12)); //垂直标题
   
              ValueAxis rangeAxis = plot.getRangeAxis();//获取柱状
              rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
              chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
              chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));//设置标题字体
              /** ---------------------- 中文乱码问题处理 End ------------------------------- */
   
              {
   
              //    rangeAxis.setAutoRange(true);
              }
          }
   
          // 设置图表控件的背景颜色。
          chart.setBackgroundPaint(Color.WHITE);
    	  
    	return chart;
    }
    
    
    
    
    //theta+vega 双Y轴2 
    private DefaultCategoryDataset createThetaVegaBarDataset(List<Account> list) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
   /*
        //get the attribute of list to array[]
        int  theta[]=    list.stream().mapToInt(Account::getPortfoliotheta).toArray();
        int  vega[]=list.stream().mapToInt(Account::getPortfoliovega).toArray();
        // 横轴名称
        String  date[]=list.stream().map(Account::getDate).toArray(String[]::new);
        String  shortdate[]=new String[date.length];
        DatasetGroup datasetGroup = new DatasetGroup();
        dataset.setGroup(datasetGroup);
        for(int i=0;i<date.length;i++)
        {
        	shortdate[i]=date[i].substring(6,10);
        dataset.setValue(theta[i],"Theta", shortdate[i]);
        dataset.setValue(vega[i],"Vega", shortdate[i]);
        }
     */   
   
        return dataset;
    }
    

    
    //双Y轴3 nasdaq+delta
    private DefaultCategoryDataset createThetaVegaDataset(List<Account> list) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int  theta[]=    list.stream().mapToInt(Account::getPortfoliotheta).toArray();
        int  vega[]=    list.stream().mapToInt(Account::getPortfoliovega).toArray();
      
        
        // 横轴名称
        String  date[]=list.stream().map(Account::getDate).toArray(String[]::new);
        String  shortdate[]=new String[date.length];
        DatasetGroup datasetGroup = new DatasetGroup();
        dataset.setGroup(datasetGroup);
        for(int i=0;i<date.length;i++)
        {
        	shortdate[i]=date[i].substring(6,10);
        dataset.setValue(theta[i],"Theta", shortdate[i]);
        dataset.setValue(vega[i],"Vega", shortdate[i]);
        }
        return dataset;
    }
    
    
    
    
    

    
    
    // 生成数据example 放在最后留存而已
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
	
	

}
