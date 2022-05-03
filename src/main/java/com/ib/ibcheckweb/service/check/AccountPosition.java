package com.ib.ibcheckweb.service.check;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.ib.ibcheckweb.twsapi.client.*;
import com.ib.ibcheckweb.bean.underlying.*;

import org.apache.commons.lang3.*;

/*把request position后get过来的contract 放到List中 
Option为期权类型
Stock为股票类型 
Account为多个账户
*/


public class AccountPosition {

	DealFile dealfile = new DealFile();
	public String path_U1001 = dealfile.path_U1001;
	public String path_U9238 = dealfile.path_U9238;
	
     //写到各自账户中
	//public String path=path_U1001;
//	public String path=path_U9238;
	
	//2个账号写到一个文件中
	public String path= dealfile.path_All;
	public String pathMarket =dealfile.path_AllMarket;
	
	
	public String pathGREEK = path + "GREEK";


	// whole account
	List<Option> optionList = new ArrayList<Option>();
	List<Stock> stockList = new ArrayList<Stock>();
	List<Security> securityList = new ArrayList<Security>();// 全部头寸都进入此

	// stock account
	List<Option> optionList_U1001 = new ArrayList<Option>();
	List<Stock> stockList_U1001 = new ArrayList<Stock>();
	// List<Security> securityList_Stock=new ArrayList<Security>();

	// ETF account
	List<Option> optionList_U9238 = new ArrayList<Option>();
	List<Stock> stockList_U9238 = new ArrayList<Stock>();
	// List<Security> securityList_ETF=new ArrayList<Security>();

	List<Contract> contratList_U9238 = new ArrayList<Contract>();
	List<Contract> contratList_U1001 = new ArrayList<Contract>();

	int call_count = 0;// 期权call计数
	int put_count = 0;// 期权put计数
	int total_op = 0;
	int total_stock = 0;
	int total_secruity = 0;

	public void insertGREEKToFile(int tickerId, int tickType, double impliedVol, double delta, double optPrice,
			double pvDividend, double gamma, double vega, double theta, double undPrice)
	{
		Option option_Greek = new Option();
		option_Greek.setConid(tickerId);
		option_Greek.setImvolatility(impliedVol);
		option_Greek.setDelta(delta);
		option_Greek.setOptionPrice(optPrice);
		option_Greek.setGamma(gamma);
		option_Greek.setVega(vega);
		option_Greek.setTheta(theta);
		option_Greek.setUnderlyingPrice(undPrice);

		String filetext = "";// 取出本地文件
		int i = 0;// 按本地文件行走
		int j = 0;// 按本地文件1行再分开走
		
		//先判断position文件是否存在
		
		File file = new File(path);
	if (file.exists()) {

		filetext = dealfile.ReadFile(path);

		String[] row = filetext.split("\\r\\n");// 转义

		for (i = 0; i < row.length; i++)// 进入文本里，每次一行记录开始循环
		{
			// System.out.println("第"+i+"行记录"+row[i]);

			String str = row[i]; // str为一行的内容
			String conidunit = StringUtils.substringBefore(row[i], "|");// 将conid=123456789取出给conidunit

			// if( str.contains("conid="+tickerId))//循环匹配到有该conid的这行记录
			if (conidunit.equalsIgnoreCase("conid=" + tickerId))// 有重复记录，则把op其他补全
			{

				String contract[] = str.split("\\|");
				for (j = 0; j < contract.length; j++) {
					String unit[] = contract[j].split("=");

					if (contract[j].contains("account")) {
						option_Greek.setAccount(unit[1]);
						continue;
					}
					if (contract[j].contains("localSymbol")) {
						option_Greek.setLocalSymbol(unit[1]);
						continue;
					}
					if (contract[j].contains("secType")) {
						option_Greek.setSecType(unit[1]);
						continue;
					}
					if (contract[j].contains("right")) {
						option_Greek.setRight(unit[1]);
						continue;
					}
					if (contract[j].contains("symbol")) {
						option_Greek.setSymbol(unit[1]);
						continue;
					}
					if (contract[j].contains("date")) {
						option_Greek.setDate(unit[1]);
						continue;
					}
					if (contract[j].contains("position")) {
						option_Greek.setPosition(Double.valueOf(unit[1]));
						continue;
					}
					if (contract[j].contains("avgCost")) {
						option_Greek.setAvgCost(Double.valueOf(unit[1]));
						continue;
					}
					if (contract[j].contains("strikePrice")) {
						option_Greek.setStrikePrice(Double.valueOf(unit[1]));
						continue;
					}

				
				}

			} // end if 一个conid只有一条记录

		}
		//dealfile.FileWriteUPdateGREEK(option_Greek, pathGREEK);
		dealfile.FileWriteGREEK(option_Greek, pathMarket);
	}//end if file exist
	else 
		System.out.println(path+"文件不存在,先执行GetPotion");
		
		dealfile.FileWriteGREEK(option_Greek, pathMarket);
	}
	
	
	
	// 获取市场数据，主要把GREEK信息再补充到账号各自的本地文件
	public void addGREEKToFile(int tickerId, int tickType, double impliedVol, double delta, double optPrice,
			double pvDividend, double gamma, double vega, double theta, double undPrice) {
		// 把Greek先放到Option对象中
		Option option_Greek = new Option();
		option_Greek.setConid(tickerId);
		option_Greek.setImvolatility(impliedVol);
		option_Greek.setDelta(delta);
		option_Greek.setOptionPrice(optPrice);
		option_Greek.setGamma(gamma);
		option_Greek.setVega(vega);
		option_Greek.setTheta(theta);
		option_Greek.setUnderlyingPrice(undPrice);

		String filetext = "";// 取出本地文件
		int i = 0;// 按本地文件行走
		int j = 0;// 按本地文件1行再分开走
		
		//先判断position文件是否存在
		
		File file = new File(path);
	if (file.exists()) {

		filetext = dealfile.ReadFile(path);

		String[] row = filetext.split("\\r\\n");// 转义

		for (i = 0; i < row.length; i++)// 进入文本里，每次一行记录开始循环
		{
			// System.out.println("第"+i+"行记录"+row[i]);

			String str = row[i]; // str为一行的内容
			String conidunit = StringUtils.substringBefore(row[i], "|");// 将conid=123456789取出给conidunit

			// if( str.contains("conid="+tickerId))//循环匹配到有该conid的这行记录
			if (conidunit.equalsIgnoreCase("conid=" + tickerId))// 有重复记录，则把op其他补全
			{

				String contract[] = str.split("\\|");
				for (j = 0; j < contract.length; j++) {
					String unit[] = contract[j].split("=");

					if (contract[j].contains("account")) {
						option_Greek.setAccount(unit[1]);
						continue;
					}
					if (contract[j].contains("localSymbol")) {
						option_Greek.setLocalSymbol(unit[1]);
						continue;
					}
					if (contract[j].contains("secType")) {
						option_Greek.setSecType(unit[1]);
						continue;
					}
					if (contract[j].contains("right")) {
						option_Greek.setRight(unit[1]);
						continue;
					}
					if (contract[j].contains("symbol")) {
						option_Greek.setSymbol(unit[1]);
						continue;
					}
					if (contract[j].contains("date")) {
						option_Greek.setDate(unit[1]);
						continue;
					}
					if (contract[j].contains("position")) {
						option_Greek.setPosition(Double.valueOf(unit[1]));
						continue;
					}
					if (contract[j].contains("avgCost")) {
						option_Greek.setAvgCost(Double.valueOf(unit[1]));
						continue;
					}
					if (contract[j].contains("strikePrice")) {
						option_Greek.setStrikePrice(Double.valueOf(unit[1]));
						continue;
					}

				
				}

			} // end if 一个conid只有一条记录

		}
		//dealfile.FileWriteUPdateGREEK(option_Greek, pathGREEK);
		dealfile.FileWriteGREEK(option_Greek, pathGREEK);
	}//end if file exist
	else 
		System.out.println(path+"文件不存在,先执行GetPotion");
		
	}

	public void insertStockToFile(int tickerId, int tickType, double price) {
		
		Stock stock = new Stock();

		stock.setPrice(price);
		stock.setConid(tickerId);
		

		String filetext = "";// 取出本地文件
		int i = 0;// 按本地文件行走
		int j = 0;// 按本地文件1行再分开走

		filetext = dealfile.ReadFile(path);
	

		String[] row = filetext.split("\\r\\n");// 转义

		for (i = 0; i < row.length; i++)// 进入文本里，每次一行记录开始循环
		{
			// System.out.println("第"+i+"行记录"+row[i]);

			String str = row[i]; // str为一行的内容
			String conidunit = StringUtils.substringBefore(row[i], "|");// 将conid=123456789取出给conidunit

			// 该行记录为stk记录才按补价格的stock处理
			if (str.contains("secType=STK")) {
				if (conidunit.equalsIgnoreCase("conid=" + tickerId))// 只记录一次，保障不重复，把stock价格补全
				{

					String contract[] = str.split("\\|");
					for (j = 0; j < contract.length; j++) {
						String unit[] = contract[j].split("=");

						if (contract[j].contains("account")) {
							stock.setAccount(unit[1]);
							continue;
						}
						if (contract[j].contains("secType")) {
							stock.setSecType(unit[1]);
							continue;
						}

						if (contract[j].contains("symbol")) {
							stock.setSymbol(unit[1]);
							continue;
						}
						if (contract[j].contains("position")) {
							stock.setPosition(Double.valueOf(unit[1]));
							continue;
						}

						if (contract[j].contains("avgCost")) {
							stock.setAvgCost(Double.valueOf(unit[1]));
							continue;
						}
					} // end for j

				} // end if conid 重复记录

			} // end if STK

		} // end for i row

		if (tickerId == 2001)// tick id=2001的是SPX
		{
//用path字符串来判断account信息
			stock.setSecType("IND");
			stock.setSymbol("SPX");
			if (path.contains("U1001")) {
				stock.setAccount("U10019359");
			} 
			else if (path.contains("U9238")) {
				stock.setAccount("U9238923");
			}
			else stock.setAccount("ALL");
		}
		if (tickerId == 3001)// tick id=3001的是COMP
		{
//用path字符串来判断account信息
			stock.setSecType("IND");
			stock.setSymbol("COMP");
			if (path.contains("U1001")) {
				stock.setAccount("U10019359");
			} else if (path.contains("U9238")) {
				stock.setAccount("U9238923");
			}
			else stock.setAccount("ALL");
		}
		
		if(stock.getSecType()!=null)
		{
		// secType不为空
		if (stock.getSecType().equalsIgnoreCase("STK")||stock.getSecType().equalsIgnoreCase("IND")) {
			dealfile.FileWriteGREEK(stock, pathMarket);
		}
		}
		
		
	}
	
	
	
	
	
	// 获取市场数据，主要把价格信息补充到本地文件里 ,在edecoder里调用
	public void addStockToFile(int tickerId, int tickType, double price) {

		Stock stock = new Stock();
		// 把市场价格放到stock对象中
		stock.setPrice(price);
		stock.setConid(tickerId);

		String filetext = "";// 取出本地文件
		int i = 0;// 按本地文件行走
		int j = 0;// 按本地文件1行再分开走

		filetext = dealfile.ReadFile(path);
	

		String[] row = filetext.split("\\r\\n");// 转义

		for (i = 0; i < row.length; i++)// 进入文本里，每次一行记录开始循环
		{
			// System.out.println("第"+i+"行记录"+row[i]);

			String str = row[i]; // str为一行的内容
			String conidunit = StringUtils.substringBefore(row[i], "|");// 将conid=123456789取出给conidunit

			// 该行记录为stk记录才按补价格的stock处理
			if (str.contains("secType=STK")) {
				if (conidunit.equalsIgnoreCase("conid=" + tickerId))// 只记录一次，保障不重复，把stock价格补全
				{

					String contract[] = str.split("\\|");
					for (j = 0; j < contract.length; j++) {
						String unit[] = contract[j].split("=");

						if (contract[j].contains("account")) {
							stock.setAccount(unit[1]);
							continue;
						}
						if (contract[j].contains("secType")) {
							stock.setSecType(unit[1]);
							continue;
						}

						if (contract[j].contains("symbol")) {
							stock.setSymbol(unit[1]);
							continue;
						}
						if (contract[j].contains("position")) {
							stock.setPosition(Double.valueOf(unit[1]));
							continue;
						}

						if (contract[j].contains("avgCost")) {
							stock.setAvgCost(Double.valueOf(unit[1]));
							continue;
						}
					} // end for j

				} // end if conid 重复记录

			} // end if STK

		} // end for i row

		if (tickerId == 2001)// tick id=2001的是SPX
		{
//用path字符串来判断account信息
			stock.setSecType("IND");
			stock.setSymbol("SPX");
			if (path.contains("U1001")) {
				stock.setAccount("U10019359");
			} 
			else if (path.contains("U9238")) {
				stock.setAccount("U9238923");
			}
			else stock.setAccount("ALL");
		}
		if (tickerId == 3001)// tick id=3001的是COMP
		{
//用path字符串来判断account信息
			stock.setSecType("IND");
			stock.setSymbol("COMP");
			if (path.contains("U1001")) {
				stock.setAccount("U10019359");
			} else if (path.contains("U9238")) {
				stock.setAccount("U9238923");
			}
			else stock.setAccount("ALL");
		}
		
		if(stock.getSecType()!=null)
		{
		// secType不为空
		if (stock.getSecType().equalsIgnoreCase("STK")||stock.getSecType().equalsIgnoreCase("IND")) {
			dealfile.FileWriteGREEK(stock, pathGREEK);
		}
		}

	
	}

	// 从本地文件读出某账户的全部头寸，把conid封成数组返回。
	public int[] getSecuityFromFile(String path) {
		String filetext;
		List<Integer> list = new ArrayList<>();
		filetext = dealfile.ReadFile(path);

		String[] record = filetext.split("\\r\\n");// 转义
		int[] contractid = new int[record.length];
		int i = 0;

		for (i = 0; i < record.length; i++)// 将每行的conid提取出来
		{

			String conidString = StringUtils.substringBefore(record[i], "|");

			if (conidString.contains("conid="))// 只需要取conid的内容
			{
				String str[] = conidString.split("=");
				contractid[i] = Integer.parseInt(str[1]);
				;
			}

		} // end for

		return contractid;

	}

	// 从本地文件读出某账户的全部头寸，并封装成Contract类。并不需要全部读取，只要OPTION的才需要再去读市场数据
	public int[] getOptionFromFile(String path) {
		String filetext;
		List<Integer> list = new ArrayList<>();
		filetext = dealfile.ReadFile(path);
		// System.out.println("the text content is: "+filetext);

		String[] record = filetext.split("\\r\\n");// 转义
		int[] contractid = null;
		int i = 0;
		int j = 0;

		for (i = 0; i < record.length; i++)// 将每行的conid提取出来
		{
			// System.out.println("record[i] is "+record[i]);

			// 如果recond[i]中含有secType=OPT，则计入contractid[],否则跳出循环
			if (record[i].contains("secType=OPT")) {
				String conidString = StringUtils.substringBefore(record[i], "|");
				// System.out.println("conidString is"+conidString);
				if (conidString.contains("conid="))// 只需要取conid的内容
				{
					String str[] = conidString.split("=");

					Integer.parseInt(str[1]);
					// j++;
					// 将
					list.add(Integer.parseInt(str[1]));

					// System.out.println("第"+j+"个：conid="+str[1]);//取conid等号右边的数
				}
			} else
				continue;
		} // end for

		contractid = new int[list.size()];
		for (j = 0; j < list.size(); j++)
			contractid[j] = list.get(j);

		return contractid;

	}

	public void addSecurityToOneFile(String account, Contract contract, double position, double avgCost) 
	{
		
		if (contract.getSecType().equalsIgnoreCase("OPT")) {
			Option op = new Option();

			op.setConid(contract.conid());
			op.setAccount(account);
			op.setSymbol(contract.symbol());
			op.setSecType(contract.getSecType());
			op.setRight(contract.getRight());
			op.setPosition(position);
			op.setAvgCost(avgCost);
			op.setLocalSymbol(contract.localSymbol());
			op.setDate(contract.lastTradeDateOrContractMonth());
			op.setStrikePrice(contract.strike());

			if (contract.getRight().equalsIgnoreCase("C"))// 为call

			{
				call_count++;
				System.out.println("Total Call Option: " + call_count);
			}

			if (contract.getRight().equalsIgnoreCase("P"))// 为put
			{
				put_count++;
				System.out.println("Total Put Option: " + put_count);
			}
			total_op = put_count + call_count;
			System.out.println("Total Option :" + total_op);
			optionList.add(op);// 增加stock account期权记录

			securityList.add(op);// 增加全账户全类型记录
			dealfile.FileWritePosition(op, path);// 写到本地文件
		}//end for OP

		// 如果判断头寸为STK则封装成Stock并添加到List里
		if (contract.getSecType().equalsIgnoreCase("STK")) {
			Stock stock = new Stock();

			stock.setConid(contract.conid());
			stock.setAccount(account);
			stock.setSymbol(contract.symbol());
			stock.setSecType(contract.getSecType());
			stock.setPosition(position);
			stock.setAvgCost(avgCost);
			total_stock++;
			System.out.println("Total stock :" + total_stock);
			stockList.add(stock);// 增加stock account股票记录
			dealfile.FileWritePosition(stock, path);// 写到本地文件
			securityList.add(stock);// 增加全类型
		}
		if (contract.getSecType().isEmpty())
			System.out.println("SecType is empty" + contract.conid());
		
	}
	
	// 新增一个期权or股票并进行分类封装,并写入本地文件里
	public void addSecurityToFile(String account, Contract contract, double position, double avgCost) {

		switch (account) {

		case "U10019359":
			processAccountU1001(account, contract, position, avgCost);
			break;

		case "U9238923":
			processAccountU9238(account, contract, position, avgCost);
			break;
		}

		total_secruity = total_stock + total_op;
		System.out.println("Total secruity :" + total_secruity);

	}

	public void processAccountU1001(String account, Contract contract, double position, double avgCost) {
		if (contract.getSecType().equalsIgnoreCase("OPT")) {
			Option op = new Option();

			op.setConid(contract.conid());
			op.setAccount(account);
			op.setSymbol(contract.symbol());
			op.setSecType(contract.getSecType());
			op.setRight(contract.getRight());
			op.setPosition(position);
			op.setAvgCost(avgCost);
			op.setLocalSymbol(contract.localSymbol());
			op.setDate(contract.lastTradeDateOrContractMonth());
			op.setStrikePrice(contract.strike());

			if (contract.getRight().equalsIgnoreCase("C"))// 为call

			{
				call_count++;
				System.out.println("Total Call Option: " + call_count);
			}

			if (contract.getRight().equalsIgnoreCase("P"))// 为put
			{
				put_count++;
				System.out.println("Total Put Option: " + put_count);
			}
			total_op = put_count + call_count;
			System.out.println("Total Option :" + total_op);
			optionList_U1001.add(op);// 增加stock account期权记录

			securityList.add(op);// 增加全账户全类型记录
			dealfile.FileWritePosition(op, path_U1001);// 写到本地文件
		}

		// 如果判断头寸为STK则封装成Stock并添加到List里
		if (contract.getSecType().equalsIgnoreCase("STK")) {
			Stock stock = new Stock();

			stock.setConid(contract.conid());
			stock.setAccount(account);
			stock.setSymbol(contract.symbol());
			stock.setSecType(contract.getSecType());
			stock.setPosition(position);
			stock.setAvgCost(avgCost);
			total_stock++;
			System.out.println("Total stock :" + total_stock);
			stockList_U1001.add(stock);// 增加stock account股票记录
			dealfile.FileWritePosition(stock, path_U1001);// 写到本地文件
			securityList.add(stock);// 增加全类型
		}
		if (contract.getSecType().isEmpty())
			System.out.println("SecType is empty" + contract.conid());

	}

	public void processAccountU9238(String account, Contract contract, double position, double avgCost) {

		// 如果判断头寸为期权则封装成Option类并加到List里
		if (contract.getSecType().equalsIgnoreCase("OPT")) {
			Option op = new Option();

			op.setConid(contract.conid());
			op.setAccount(account);
			op.setSymbol(contract.symbol());
			op.setSecType(contract.getSecType());
			op.setRight(contract.getRight());
			op.setPosition(position);
			op.setAvgCost(avgCost);
			op.setLocalSymbol(contract.localSymbol());
			op.setDate(contract.lastTradeDateOrContractMonth());
			op.setStrikePrice(contract.strike());

			if (contract.getRight().equalsIgnoreCase("C"))// 为call

			{
				call_count++;
				System.out.println("Total Call Option: " + call_count);
			}

			if (contract.getRight().equalsIgnoreCase("P"))// 为put
			{
				put_count++;
				System.out.println("Total Put Option: " + put_count);
			}
			total_op = put_count + call_count;
			System.out.println("Total Option :" + total_op);
			optionList_U9238.add(op);// 增加stock account期权记录

			securityList.add(op);// 增加全账户全类型记录
			dealfile.FileWritePosition(op, path_U9238);// 写到本地文件
		}

		// 如果判断头寸为STK则封装成Stock并添加到List里
		if (contract.getSecType().equalsIgnoreCase("STK")) {
			Stock stock = new Stock();

			stock.setConid(contract.conid());
			stock.setAccount(account);
			stock.setSymbol(contract.symbol());
			stock.setSecType(contract.getSecType());
			stock.setPosition(position);
			stock.setAvgCost(avgCost);
			total_stock++;
			System.out.println("Total stock :" + total_stock);
			stockList_U9238.add(stock);// 增加stock account股票记录
			dealfile.FileWritePosition(stock, path_U9238);// 写到本地文件
			securityList.add(stock);// 增加全类型
		}

		if (contract.getSecType().isEmpty())
			System.out.println("SecType is empty" + contract.conid());
	}

	public static void main(String args[]) {
		AccountPosition accposition = new AccountPosition();
		try {
			accposition.getSecuityFromFile(accposition.path_U9238);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
