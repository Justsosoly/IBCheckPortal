package com.ib.ibcheckweb.service.check;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ib.ibcheckweb.service.check.AccountPosition;
import com.ib.ibcheckweb.twsapi.client.*;


public class GetGreek {
	
	
	public static void main(String[] args) throws InterruptedException {
		AccountPosition accountposition = new AccountPosition();
		GetGreek getGreek=new GetGreek();
		//账号各自分开使用
		//String path=accountposition.path_U1001;
		//String path=accountposition.path_U9238;
		//getGreek.DealAllGreek(path,9238);
//		getGreek.DealAllGreek(path_U1001,1001);
		
		//合成一个文件
     	String path_all=accountposition.path;//2个账号
		getGreek.DealAllGreek(path_all,6);//port 6 is random
		

	}
	
	
	
	public void DealAllGreek(String path_all,int port) throws InterruptedException
	{
	
		EWrapperImpl wrapper = new EWrapperImpl();

		final EClientSocket m_client = wrapper.getClient();
		final EReaderSignal m_signal = wrapper.getSignal();

		m_client.eConnect("127.0.0.1", 7496, port);
	//	m_client.eConnect("127.0.0.1", 4001, port);

		final EReader reader = new EReader(m_client, m_signal);

		reader.start();
		
		new Thread(() -> {
			while (m_client.isConnected()) {
				m_signal.waitForSignal();
				try {
					reader.processMsgs();
				} catch (Exception e) {
					System.out.println("Exception: " + e.getMessage());
				}
			}
		}).start();
	
		Thread.sleep(1000);
	
		GetGreekbyAccount(wrapper.getClient(),path_all);
	
		Thread.sleep(25000);
		m_client.eDisconnect();
		Thread.sleep(1000);
		
		
	}
	
	
	
	
	
	public static void GetGreekbyAccount(EClientSocket client,String path) throws InterruptedException
	{
		AccountPosition accountposition=new AccountPosition(); 
		int request_num=0;//本地文件返回请求conid的个数
		String starttime="04:01";
		String endtime="20:59";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	        String now = sdf.format(new Date());
	        Date nowTime;
		
		//取出全部conid，包括stock和option
		request_num= accountposition.getSecuityFromFile(path).length;
		 
		
		 int contractid[]=new int[request_num];
        contractid=accountposition.getSecuityFromFile(path);

	
       
        
        try{
            nowTime=sdf.parse(now);
            Date startTime= sdf.parse(starttime);
            Date endTime = sdf.parse(endtime);
            // 也可以通过此方式判断当前时间是否具体时间段内 yyyy-MM-dd HH:mm:ss格式 [2022-03-09 12:00:00,2022-03-10 15:00:00]
            //   当前时间和时间段的格式保持一致即可判断
            if (isEffectiveDate(nowTime,startTime,endTime)) {
            	 /*** Switch to live (1) frozen (2) delayed (3) or delayed frozen (4)***/
                client.reqMarketDataType(2); //between 5 to 21 is the frozen type
              System.out.println("当前时间在时间段内["+starttime+","+endtime+"],数据类型=2 frozen ");
            } else {
            	 /*** Switch to live (1) frozen (2) delayed (3) or delayed frozen (4)***/
                client.reqMarketDataType(1); //between 5 to 21 is the frozen type
              //  System.out.println(“数据类型=1 live”);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        
        
        
     
        //取得本地文件里的conid，挨个请求市场数据
        for(int i=0;i<request_num;i++)
        {      	
        //每次将一个security的GREEK写回本地文件	
        
        	client.reqMktData(contractid[i], contractByID(contractid[i]),"", false, false, null);//返回一堆参数Tick AAPL Price size field   
        	System.out.println("第"+i+"个请求"+contractid[i]);
        	Thread.sleep(100);//1秒
     	}
        
        
        client.reqMarketDataType(4); 
        client.reqMktData(2001, getSPXIndex(),"", false, false, null);
        Thread.sleep(100);
        client.reqMktData(3001, getNASDAQIndex(),"", false, false, null);
        
	}
	
	  public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
	        if (nowTime.getTime()== startTime.getTime()
	                || nowTime.getTime() == endTime.getTime()) {
	            return true;
	        }

	        Calendar date = Calendar.getInstance();
	        date.setTime(nowTime);

	        Calendar begin = Calendar.getInstance();
	        begin.setTime(startTime);

	        Calendar end = Calendar.getInstance();
	        end.setTime(endTime);

	        return date.after(begin) && date.before(end);
	    }
	
	
	
	
	public static Contract getSPXIndex() 
	{
		Contract contract = new Contract();
		contract.secType("IND") ;
		contract.symbol("SPX");
		contract.exchange("CBOE");
		contract.currency("USD");
		return contract;
	}
	
	public static Contract getNASDAQIndex() 
	{
		Contract contract = new Contract();
		contract.secType("IND") ;
		contract.symbol("COMP");
		contract.exchange("NASDAQ");
		contract.currency("USD");
		return contract;
	}
	
	public static Contract contractByID(int contractid) {

		Contract contract = new Contract();
		contract.conid(contractid);
		contract.exchange("SMART");
	
		return contract;
	}
}
