package com.ib.ibcheckweb.service.check;

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
	
		//GetGreekbyAccount(wrapper.getClient(),path);
		
		GetGreekbyAccount(wrapper.getClient(),path_all);
	
		Thread.sleep(15000);
		m_client.eDisconnect();
		Thread.sleep(1000);
		
		
	}
	
	
	
	
	
	public static void GetGreekbyAccount(EClientSocket client,String path) throws InterruptedException
	{
		AccountPosition accountposition=new AccountPosition(); 
		int request_num=0;//本地文件返回请求conid的个数
		

		
		//取出全部conid，包括stock和option
		request_num= accountposition.getSecuityFromFile(path).length;
		 
		
		 int contractid[]=new int[request_num];
        contractid=accountposition.getSecuityFromFile(path);

		//! [reqmarketdatatype]
        /*** Switch to live (1) frozen (2) delayed (3) or delayed frozen (4)***/
        //! [reqmarketdatatype]
        
        client.reqMarketDataType(2); 
        
    
     
        //取得本地文件里的conid，挨个请求市场数据
        for(int i=0;i<request_num;i++)
        {      	
        //每次将一个security的GREEK写回本地文件	
        
        	client.reqMktData(contractid[i], contractByID(contractid[i]),"", false, false, null);//返回一堆参数Tick AAPL Price size field   
        	System.out.println("第"+i+"个请求"+contractid[i]);
        	Thread.sleep(100);//1秒
     	}
        
     //   client.reqMktData(4001, contractByID(469204796),"", false, false, null);
        
          
        client.reqMarketDataType(4); 
        client.reqMktData(2001, getSPXIndex(),"", false, false, null);
        Thread.sleep(100);
        client.reqMktData(3001, getNASDAQIndex(),"", false, false, null);
        
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
