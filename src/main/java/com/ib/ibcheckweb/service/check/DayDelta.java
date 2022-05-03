package com.ib.ibcheckweb.service.check;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ib.ibcheckweb.bean.account.*;
import com.ib.ibcheckweb.bean.underlying.*;

public class DayDelta {
	List<Option> optionList = new ArrayList<Option>();
	List<Stock> stockList = new ArrayList<Stock>();
//  2个账户分别取List的时候
//	List<Security> securityList = new ArrayList<Security>();// 全部头寸都进入此
	double spxprice = 1;
	double spx_delta = 0;
	double nasdaq = 0;

	DealFile dealfile = new DealFile();

	/*
	 * //取期权 public Account getOPTPflioDelta(List<Option> optionList) {
	 * 
	 * int portfolio_delta = 0; int portfolio_cost = 0;
	 * 
	 * double portfolio_theta = 0; double portfolio_vega = 0; double portfolio_gamma
	 * = 0;
	 * 
	 * // call int long_call_delta = 0; int short_call_delta = 0; // put int
	 * long_put_delta = 0; int short_put_delta = 0;
	 * 
	 * int long_call_num = 0; int short_call_num = 0;
	 * 
	 * int long_put_num = 0; int short_put_num = 0;
	 * 
	 * int long_call_cost = 0; int short_call_cost = 0;
	 * 
	 * int long_put_cost = 0; int short_put_cost = 0;
	 * 
	 * int call_cost = 0; int put_cost = 0;
	 * 
	 * // call put 总数 int call_delta = 0; int put_delta = 0;
	 * 
	 * // call数量，put数量 int call_num = 0; int put_num = 0;
	 * 
	 * // long 数量 short 数量 int long_num = 0; int short_num = 0;
	 * 
	 * // long short总数 int long_delta = 0; int short_delta = 0;
	 * 
	 * int op_num = 0; Account account=new Account();
	 * 
	 * int i = 0; for (i = 0; i < optionList.size(); i++) { Option option = new
	 * Option(); option = optionList.get(i); double delta = 0; double cost = 0;
	 * double gamma = 0; double theta = 0; double vega = 0;
	 * 
	 * delta = option.getDelta() * option.getPosition() * option.getStrikePrice() *
	 * 100;// 取每个期权的delta值 int deltaDollars = (int) delta; cost =
	 * option.getAvgCost() * option.getPosition(); int optioncost = (int) cost;
	 * 
	 * theta = option.getTheta() * option.getPosition() * 100; portfolio_theta =
	 * portfolio_theta + theta;
	 * 
	 * gamma = option.getGamma() * option.getPosition() * 100; portfolio_gamma =
	 * portfolio_gamma + gamma;
	 * 
	 * vega = option.getVega() * option.getPosition() * 100; portfolio_vega =
	 * portfolio_vega + vega;
	 * 
	 * // System.out.println(option.getLocalSymbol() + "的delta值=" + (int) //
	 * deltaDollars);
	 * 
	 * if (option.getRight().contentEquals("P"))// put { if (option.getPosition() <
	 * 0)// short put {
	 * 
	 * short_put_delta = short_put_delta + deltaDollars; short_put_cost =
	 * short_put_cost + optioncost; short_put_num++; }
	 * 
	 * if (option.getPosition() > 0)// long put { long_put_delta = long_put_delta +
	 * deltaDollars; long_put_cost = long_put_cost + optioncost; long_put_num++; }
	 * 
	 * put_num++;
	 * 
	 * }
	 * 
	 * if (option.getRight().contentEquals("C"))// call {
	 * 
	 * if (option.getPosition() > 0)// long call { long_call_delta = long_call_delta
	 * + deltaDollars; long_call_cost = long_call_cost + optioncost;
	 * long_call_num++; }
	 * 
	 * if (option.getPosition() < 0)// short call {
	 * 
	 * short_call_delta = short_call_delta + deltaDollars; short_call_cost =
	 * short_call_cost + optioncost; short_call_num++; }
	 * 
	 * } put_num = short_put_num + long_put_num; call_num = short_call_num +
	 * long_call_num; op_num = short_put_num + long_put_num + short_call_num +
	 * long_call_num; portfolio_delta = deltaDollars + portfolio_delta; call_delta =
	 * long_call_delta + short_call_delta; put_delta = short_put_delta +
	 * long_put_delta; call_cost = long_call_cost + short_call_cost; put_cost =
	 * short_put_cost + long_put_cost; portfolio_cost = call_cost + put_cost;
	 * //将结果写入Account里
	 * 
	 * account.setAccount(option.getAccount());
	 * 
	 * account.setPortfolioDelta(portfolio_delta);
	 * account.setPortfoliotheta((int)portfolio_theta);
	 * account.setPortfolioGamma((int)portfolio_gamma );
	 * account.setPortfoliovega((int)portfolio_vega);
	 * 
	 * account.setOp_num(op_num); account.setPortfolio_cost(portfolio_cost);
	 * 
	 * account.setPut_delta(put_delta); account.setPut_num(put_num);
	 * account.setPut_cost(put_cost);
	 * 
	 * account.setCall_delta(call_delta); account.setCall_num(call_num);
	 * account.setCall_cost(call_cost);
	 * 
	 * account.setShort_put_delta(short_put_delta);
	 * account.setShort_put_num(short_put_num);
	 * account.setShort_put_cost(short_put_cost);
	 * 
	 * account.setLong_call_delta(long_call_delta);
	 * account.setLong_call_num(long_call_num);
	 * account.setLong_call_cost(long_call_cost);
	 * 
	 * account.setShort_call_delta(short_call_delta);
	 * account.setShort_call_num(short_call_num);
	 * account.setShort_call_cost(short_call_cost);
	 * 
	 * account.setLong_put_delta(long_put_delta);
	 * account.setLong_put_num(long_put_num);
	 * account.setLong_put_cost(long_put_cost);
	 * 
	 * 
	 * } // end for
	 * 
	 * 
	 * System.out.println("整个组合里期权的delta总值为:" + portfolio_delta + ",期权成本为:" +
	 * portfolio_cost); System.out.println("整个组合的Theta为:" +String.format("%.2f",
	 * portfolio_theta) + ",Vega为" + String.format("%.2f", portfolio_vega) +
	 * "，Gamma为:" + String.format("%.2f", portfolio_gamma)); System.out.println();
	 * System.out.print("账户里期权个数总计为:" + op_num); System.out.print("，其中Put个数共有:" +
	 * put_num); System.out.println("，其中Call个数共有:" + call_num);
	 * System.out.println("Call Delta合计:" + call_delta + ",成本为:" + call_cost);
	 * System.out.println("Put Delta合计：" + put_delta + ",成本为:" + put_cost);
	 * System.out.println(); System.out.println( "期权组合中的short put 合计delta为:" +
	 * short_put_delta + "，数量为:" + short_put_num + "，成本为:" + short_put_cost);
	 * System.out.println( "期权组合中的long call 合计delta为:" + long_call_delta + "，数量为:" +
	 * long_call_num+ "，成本为:" + long_call_cost); System.out.println(
	 * "期权组合中的short call 合计delta为:" + short_call_delta + "，数量为:" + short_call_num +
	 * "，成本为:" + short_call_cost); System.out.println( "期权组合中的long put 合计delta为:" +
	 * long_put_delta + "，数量为:" + long_put_num + "，成本为:" + long_put_cost);
	 * System.out.println();
	 * 
	 * return account; }
	 * 
	 */
	

	// 取期权和股票及 ETF
	public Account getALLPflioDelta(List<Security> secList,String acct) {

		int option_delta = 0;
		int stock_delta = 0;
		int portfolio_delta = 0;

		int portfolio_cost = 0;

		double portfolio_theta = 0;
		double portfolio_vega = 0;
		double portfolio_gamma = 0;

		// call
		int long_call_delta = 0;
		int short_call_delta = 0;
		// put
		int long_put_delta = 0;
		int short_put_delta = 0;

		int long_call_num = 0;
		int short_call_num = 0;

		int long_put_num = 0;
		int short_put_num = 0;

		int long_call_cost = 0;
		int short_call_cost = 0;

		int long_put_cost = 0;
		int short_put_cost = 0;

		int call_cost = 0;
		int put_cost = 0;

		// call put 总数
		int call_delta = 0;
		int put_delta = 0;

		// call数量，put数量
		int call_num = 0;
		int put_num = 0;

		// long 数量 short 数量
		int long_num = 0;
		int short_num = 0;

		// long short总数
		int long_delta = 0;
		int short_delta = 0;

		int op_num = 0;
		Account account = new Account();

		int i = 0;
		for (i = 0; i < secList.size(); i++) {
			double delta = 0;
			Security secutity = secList.get(i);
			
			if (secutity.getSecType().equalsIgnoreCase("IND"))// 指数则取出SPX的收盘价格
			{

				Index index = (Index) secutity;

				if (secutity.getSymbol().equalsIgnoreCase("SPX"))
					spxprice = index.getPrice();
				if (secutity.getSymbol().equalsIgnoreCase("COMP"))
					nasdaq = index.getPrice();
			}
			
			//2个账户分开
			if(secutity.getAccount().equals(acct))
			{	
			// 如果是期权类型则进入
			if (secutity.getSecType().equalsIgnoreCase("OPT")) {

				double cost = 0;
				double gamma = 0;
				double theta = 0;
				double vega = 0;
				int num = 0;
				Option option = new Option();
				option = (Option) secutity;

				// 取每个期权的delta值，价格是市场价，不是执行价
				delta = option.getDelta() * option.getPosition() * option.getUnderlyingPrice() * 100;
				int deltaDollars = (int) delta;
				// System.out.println(option.getLocalSymbol()+"的delta值为："+deltaDollars);
				cost = option.getAvgCost() * option.getPosition();
				int optioncost = (int) cost;

				theta = option.getTheta() * option.getPosition() * 100;
				portfolio_theta = portfolio_theta + theta;
				// System.out.println(option.getLocalSymbol()+"的theta值为："+theta);
				// System.out.println(option.getLocalSymbol()+"的portfolio_theta值为："+portfolio_theta);
				gamma = option.getGamma() * option.getPosition() * 100;
				portfolio_gamma = portfolio_gamma + gamma;

				vega = option.getVega() * option.getPosition() * 100;
				portfolio_vega = portfolio_vega + vega;

				num = (int) option.getPosition();

				if (option.getRight().contentEquals("P"))// put
				{
					if (option.getPosition() < 0)// short put
					{

						short_put_delta = short_put_delta + deltaDollars;
						short_put_cost = short_put_cost + optioncost;
						short_put_num = short_put_num + num;
					}

					if (option.getPosition() > 0)// long put
					{
						long_put_delta = long_put_delta + deltaDollars;
						long_put_cost = long_put_cost + optioncost;
						long_put_num = long_put_num + num;
					}

				} // end if P

				if (option.getRight().contentEquals("C"))// call
				{

					if (option.getPosition() > 0)// long call
					{
						long_call_delta = long_call_delta + deltaDollars;
						long_call_cost = long_call_cost + optioncost;
						long_call_num = long_call_num + num;
					}

					if (option.getPosition() < 0)// short call
					{

						short_call_delta = short_call_delta + deltaDollars;
						short_call_cost = short_call_cost + optioncost;
						short_call_num = short_call_num + num;
					}

				} // end if C
				put_num = short_put_num + long_put_num;
				call_num = short_call_num + long_call_num;
				op_num = short_put_num + long_put_num + short_call_num + long_call_num;
				option_delta = deltaDollars + option_delta;
				call_delta = long_call_delta + short_call_delta;
				put_delta = short_put_delta + long_put_delta;
				call_cost = long_call_cost + short_call_cost;
				put_cost = short_put_cost + long_put_cost;
				portfolio_cost = call_cost + put_cost;

			} // end if OPT
			else // 股票及ETF则进入
			if (secutity.getSecType().equalsIgnoreCase("STK")) {

				Stock stock = (Stock) secutity;
				// System.out.println("stock is:"+stock.getSymbol());

				delta = stock.getPrice() * stock.getPosition();// 取每个期权的delta值

				stock_delta = stock_delta + (int) delta;

			} // end if STK

			

			portfolio_delta = stock_delta + option_delta;
			
			}//end account
		

		} // end for

		// 将结果写入Account里

		spx_delta = portfolio_delta / spxprice; // 取SPX
		account.setAccount(acct);
		DealFile dealfile=new DealFile();
		account.setDate(dealfile.FileNameTime());
		account.setNasdaq((int) nasdaq);
		account.setSpx((int) spxprice);

		account.setPortfolioDelta((int) portfolio_delta);
		account.setOption_delta((int) option_delta);
		account.setStock_delta((int) stock_delta);

		account.setSpx_delta((int) spx_delta);
		account.setPortfoliotheta((int) portfolio_theta);
		account.setPortfolioGamma((int) portfolio_gamma);
		account.setPortfoliovega((int) portfolio_vega);

		account.setOp_num(op_num);
		account.setPortfolio_cost(portfolio_cost);

		account.setPut_delta(put_delta);
		account.setPut_num(put_num);
		account.setPut_cost(put_cost);

		account.setCall_delta(call_delta);
		account.setCall_num(call_num);
		account.setCall_cost(call_cost);

		account.setShort_put_delta(short_put_delta);
		account.setShort_put_num(short_put_num);
		account.setShort_put_cost(short_put_cost);

		account.setLong_call_delta(long_call_delta);
		account.setLong_call_num(long_call_num);
		account.setLong_call_cost(long_call_cost);

		account.setShort_call_delta(short_call_delta);
		account.setShort_call_num(short_call_num);
		account.setShort_call_cost(short_call_cost);

		account.setLong_put_delta(long_put_delta);
		account.setLong_put_num(long_put_num);
		account.setLong_put_cost(long_put_cost);

		// System.out.println("SPX price is :"+spxprice);

		System.out.print(dealfile.FileNameDate());
		System.out.println("日的NASDAQ指数是:" + nasdaq + ",SPX指数是:" + spxprice);

		System.out.println(acct + "账户的delta总值为:" + portfolio_delta + ",股票的delta总值为:" + stock_delta
				+ ",期权的delta总值为:" + option_delta + ",期权权利金为:" + portfolio_cost);
		System.out.println("整个组合的SPX Delta:" + String.format("%.1f", spx_delta) + ",Theta为:"
				+ String.format("%.1f", portfolio_theta) + ",Vega为" + String.format("%.1f", portfolio_vega) + "，Gamma为:"
				+ String.format("%.1f", portfolio_gamma));
		System.out.println();

		System.out.print("账户里Put个数共有:" + put_num);
		System.out.print("，Call个数共有:" + call_num);
		System.out.println("，总计为:" + op_num);

		System.out.println("Call Delta合计:" + (int) call_delta + ",成本为:" + call_cost);
		System.out.println("Put Delta合计:" + (int) put_delta + ",成本为:" + put_cost);
		System.out.println();
		System.out.println("期权组合中的short put 合计delta为:" + (int) short_put_delta + "，数量为:" + short_put_num + "，成本为:"
				+ short_put_cost);
		System.out.println("期权组合中的long call 合计delta为:" + (int) long_call_delta + "，数量为:" + long_call_num + "，成本为:"
				+ long_call_cost);
		System.out.println("期权组合中的short call 合计delta为:" + (int) short_call_delta + "，数量为:" + short_call_num + "，成本为:"
				+ short_call_cost);
		System.out.println(
				"期权组合中的long put 合计delta为:" + (int) long_put_delta + "，数量为:" + long_put_num + "，成本为:" + long_put_cost);
		System.out.println();

		return account;
	}
	
	
	
	
	
	

	// 取期权和股票及 ETF
	public Account getALLPflioDelta(List<Security> secList) {

		int option_delta = 0;
		int stock_delta = 0;
		int portfolio_delta = 0;

		int portfolio_cost = 0;

		double portfolio_theta = 0;
		double portfolio_vega = 0;
		double portfolio_gamma = 0;

		// call
		int long_call_delta = 0;
		int short_call_delta = 0;
		// put
		int long_put_delta = 0;
		int short_put_delta = 0;

		int long_call_num = 0;
		int short_call_num = 0;

		int long_put_num = 0;
		int short_put_num = 0;

		int long_call_cost = 0;
		int short_call_cost = 0;

		int long_put_cost = 0;
		int short_put_cost = 0;

		int call_cost = 0;
		int put_cost = 0;

		// call put 总数
		int call_delta = 0;
		int put_delta = 0;

		// call数量，put数量
		int call_num = 0;
		int put_num = 0;

		// long 数量 short 数量
		int long_num = 0;
		int short_num = 0;

		// long short总数
		int long_delta = 0;
		int short_delta = 0;

		int op_num = 0;
		Account account = new Account();

		int i = 0;
		for (i = 0; i < secList.size(); i++) {
			double delta = 0;
			Security secutity = secList.get(i);

			// 如果是期权类型则进入
			if (secutity.getSecType().equalsIgnoreCase("OPT")) {

				double cost = 0;
				double gamma = 0;
				double theta = 0;
				double vega = 0;
				int num = 0;
				Option option = new Option();
				option = (Option) secutity;

				// 取每个期权的delta值，价格是市场价，不是执行价
				delta = option.getDelta() * option.getPosition() * option.getUnderlyingPrice() * 100;
				int deltaDollars = (int) delta;
				// System.out.println(option.getLocalSymbol()+"的delta值为："+deltaDollars);
				cost = option.getAvgCost() * option.getPosition();
				int optioncost = (int) cost;

				theta = option.getTheta() * option.getPosition() * 100;
				portfolio_theta = portfolio_theta + theta;
				// System.out.println(option.getLocalSymbol()+"的theta值为："+theta);
				// System.out.println(option.getLocalSymbol()+"的portfolio_theta值为："+portfolio_theta);
				gamma = option.getGamma() * option.getPosition() * 100;
				portfolio_gamma = portfolio_gamma + gamma;

				vega = option.getVega() * option.getPosition() * 100;
				portfolio_vega = portfolio_vega + vega;

				num = (int) option.getPosition();

				if (option.getRight().contentEquals("P"))// put
				{
					if (option.getPosition() < 0)// short put
					{

						short_put_delta = short_put_delta + deltaDollars;
						short_put_cost = short_put_cost + optioncost;
						short_put_num = short_put_num + num;
					}

					if (option.getPosition() > 0)// long put
					{
						long_put_delta = long_put_delta + deltaDollars;
						long_put_cost = long_put_cost + optioncost;
						long_put_num = long_put_num + num;
					}

				} // end if P

				if (option.getRight().contentEquals("C"))// call
				{

					if (option.getPosition() > 0)// long call
					{
						long_call_delta = long_call_delta + deltaDollars;
						long_call_cost = long_call_cost + optioncost;
						long_call_num = long_call_num + num;
					}

					if (option.getPosition() < 0)// short call
					{

						short_call_delta = short_call_delta + deltaDollars;
						short_call_cost = short_call_cost + optioncost;
						short_call_num = short_call_num + num;
					}

				} // end if C
				put_num = short_put_num + long_put_num;
				call_num = short_call_num + long_call_num;
				op_num = short_put_num + long_put_num + short_call_num + long_call_num;
				option_delta = deltaDollars + option_delta;
				call_delta = long_call_delta + short_call_delta;
				put_delta = short_put_delta + long_put_delta;
				call_cost = long_call_cost + short_call_cost;
				put_cost = short_put_cost + long_put_cost;
				portfolio_cost = call_cost + put_cost;

			} // end if OPT
			else // 股票及ETF则进入
			if (secutity.getSecType().equalsIgnoreCase("STK")) {

				Stock stock = (Stock) secutity;
				// System.out.println("stock is:"+stock.getSymbol());

				delta = stock.getPrice() * stock.getPosition();// 取每个期权的delta值

				stock_delta = stock_delta + (int) delta;

			} // end if STK

			else if (secutity.getSecType().equalsIgnoreCase("IND"))// 指数则取出SPX的收盘价格
			{

				Index index = (Index) secutity;

				if (secutity.getSymbol().equalsIgnoreCase("SPX"))
					spxprice = index.getPrice();
				if (secutity.getSymbol().equalsIgnoreCase("COMP"))
					nasdaq = index.getPrice();
			}

			portfolio_delta = stock_delta + option_delta;

		} // end for

		// 将结果写入Account里

		spx_delta = portfolio_delta / spxprice; // 取SPX
		account.setAccount(secList.get(0).getAccount());// 就取第一个

		account.setNasdaq((int) nasdaq);
		account.setSpx((int) spxprice);

		account.setPortfolioDelta((int) portfolio_delta);
		account.setOption_delta((int) option_delta);
		account.setStock_delta((int) stock_delta);

		account.setSpx_delta((int) spx_delta);
		account.setPortfoliotheta((int) portfolio_theta);
		account.setPortfolioGamma((int) portfolio_gamma);
		account.setPortfoliovega((int) portfolio_vega);

		account.setOp_num(op_num);
		account.setPortfolio_cost(portfolio_cost);

		account.setPut_delta(put_delta);
		account.setPut_num(put_num);
		account.setPut_cost(put_cost);

		account.setCall_delta(call_delta);
		account.setCall_num(call_num);
		account.setCall_cost(call_cost);

		account.setShort_put_delta(short_put_delta);
		account.setShort_put_num(short_put_num);
		account.setShort_put_cost(short_put_cost);

		account.setLong_call_delta(long_call_delta);
		account.setLong_call_num(long_call_num);
		account.setLong_call_cost(long_call_cost);

		account.setShort_call_delta(short_call_delta);
		account.setShort_call_num(short_call_num);
		account.setShort_call_cost(short_call_cost);

		account.setLong_put_delta(long_put_delta);
		account.setLong_put_num(long_put_num);
		account.setLong_put_cost(long_put_cost);

		// System.out.println("SPX price is :"+spxprice);

		System.out.print(dealfile.FileNameDate());
		System.out.println("日的NASDAQ指数是:" + nasdaq + ",SPX指数是:" + spxprice);

		System.out.println(account.getAccount() + "账户的delta总值为:" + portfolio_delta + ",股票的delta总值为:" + stock_delta
				+ ",期权的delta总值为:" + option_delta + ",期权权利金为:" + portfolio_cost);
		System.out.println("整个组合的SPX Delta:" + String.format("%.1f", spx_delta) + ",Theta为:"
				+ String.format("%.1f", portfolio_theta) + ",Vega为" + String.format("%.1f", portfolio_vega) + "，Gamma为:"
				+ String.format("%.1f", portfolio_gamma));
		System.out.println();

		System.out.print("账户里Put个数共有:" + put_num);
		System.out.print("，Call个数共有:" + call_num);
		System.out.println("，总计为:" + op_num);

		System.out.println("Call Delta合计:" + (int) call_delta + ",成本为:" + call_cost);
		System.out.println("Put Delta合计:" + (int) put_delta + ",成本为:" + put_cost);
		System.out.println();
		System.out.println("期权组合中的short put 合计delta为:" + (int) short_put_delta + "，数量为:" + short_put_num + "，成本为:"
				+ short_put_cost);
		System.out.println("期权组合中的long call 合计delta为:" + (int) long_call_delta + "，数量为:" + long_call_num + "，成本为:"
				+ long_call_cost);
		System.out.println("期权组合中的short call 合计delta为:" + (int) short_call_delta + "，数量为:" + short_call_num + "，成本为:"
				+ short_call_cost);
		System.out.println(
				"期权组合中的long put 合计delta为:" + (int) long_put_delta + "，数量为:" + long_put_num + "，成本为:" + long_put_cost);
		System.out.println();

		return account;
	}

	// 传递过来的List 有股票也有期权
	public List<Option> getALLCloseOptionList(List<Security> secList) {
		int sum_short = 0;
		int sum_long = 0;
		List<Option> opCloseList = new ArrayList<Option>();
		for (int i = 0; i < secList.size(); i++) {

			// 为期权类型才进入
			if (secList.get(i).getSecType().equalsIgnoreCase("OPT")) {

				Option option = (Option) secList.get(i);
				double op_price = option.getOptionPrice() * 100;
				double op_avgprice = option.getAvgCost();
				double short_ratio = (1 - op_price / op_avgprice) * 100;
				double long_ratio = 100 * (op_price - op_avgprice) / op_avgprice;
				// 未实现盈利金额
				double profit = (op_price - op_avgprice) * option.getPosition();

				// 先判断如果position为负，即short put or call则把盈利70%以上的分别列出来。
				if (option.getPosition() < 0) {

					if (short_ratio > 70) {
						/*
						 * System.out.println(option.getDate() + "日到期" + option.getSymbol() + "执行价" +
						 * (int) option.getStrikePrice() + "持有" + (int) option.getPosition() + "张" +
						 * option.getRight() + "，未实现盈利%=" + String.format("%.2f", short_ratio) + "%" +
						 * "，未实现盈利="+(int) profit );
						 */
						sum_short = sum_short + (int) option.getPosition();
						option.setUnrealized_profit(profit);
						option.setShort_ratio(short_ratio);
						opCloseList.add(option);
					}
				} // end if<0
					// 若是long option,则有65%的盈利则分别列出
				if (option.getPosition() > 0) {
					if (long_ratio > 65) {
						/*
						 * System.out.println(option.getDate() + "日到期" + option.getSymbol() + "执行价" +
						 * (int) option.getStrikePrice() + "持有" + (int) option.getPosition() + "张" +
						 * option.getRight() + "，未实现盈利=" + String.format("%.2f", long_ratio) + "%" +
						 * "，未实现盈利="+(int) profit );
						 */
						sum_long = sum_long + (int) option.getPosition();
						option.setUnrealized_profit(profit);
						option.setLong_ratio(long_ratio);
						opCloseList.add(option);
					}
				} // end if >0
			} // end if OPT
		} // end for

		opCloseList.sort((y, x) -> Double.compare(x.getUnrealized_profit(), y.getUnrealized_profit()));// 这方法需要jdk1.8以上

		for (Option op : opCloseList) {
			if (op.getPosition() > 0) {
				System.out.println(op.getDate() + "日到期" + op.getSymbol() + "执行价" + (int) op.getStrikePrice() + "持有"
						+ (int) op.getPosition() + "张" + op.getRight() + "，未实现盈利="
						+ String.format("%.2f", op.getLong_ratio()) + "%" + "，未实现盈利="
						+ (int) op.getUnrealized_profit());
				// System.out.println(op.getSymbol()+"long ration is
				// :"+op.getLong_ratio()+",unrealized profit is:"+op.getUnrealized_profit());
			}

			if (op.getPosition() < 0) {
				System.out.println(op.getDate() + "日到期" + op.getSymbol() + "执行价" + (int) op.getStrikePrice() + "持有"
						+ (int) op.getPosition() + "张" + op.getRight() + "，未实现盈利="
						+ String.format("%.2f", op.getShort_ratio()) + "%" + "，未实现盈利="
						+ (int) op.getUnrealized_profit());
				// System.out.println(op.getSymbol()+"short ration is
				// :"+op.getShort_ratio()+",unrealized profit is:"+op.getUnrealized_profit());
			}

		}

		System.out.println("建议平仓short的数量小计为:" + sum_short);
		System.out.println("建议平仓Long的数量小计为:" + sum_long);

		return opCloseList;

	}
	
	
	// 传递过来的List 有股票也有期权
		public List<Option> getALLCloseOptionList(List<Security> secList,String accut) {
			int sum_short = 0;
			int sum_long = 0;
			List<Option> opCloseList = new ArrayList<Option>();
	
			for (int i = 0; i < secList.size(); i++) {
				Security sec=secList.get(i);
				if(sec.getAccount().equals(accut))
				{
				// 为期权类型才进入
				if (secList.get(i).getSecType().equalsIgnoreCase("OPT")) {

					Option option = (Option) secList.get(i);
					double op_price = option.getOptionPrice() * 100;
					double op_avgprice = option.getAvgCost();
					double short_ratio = (1 - op_price / op_avgprice) * 100;
					double long_ratio = 100 * (op_price - op_avgprice) / op_avgprice;
					// 未实现盈利金额
					double profit = (op_price - op_avgprice) * option.getPosition();

					// 先判断如果position为负，即short put or call则把盈利70%以上的分别列出来。
					if (option.getPosition() < 0) {

						if (short_ratio > 70) {
							/*
							 * System.out.println(option.getDate() + "日到期" + option.getSymbol() + "执行价" +
							 * (int) option.getStrikePrice() + "持有" + (int) option.getPosition() + "张" +
							 * option.getRight() + "，未实现盈利%=" + String.format("%.2f", short_ratio) + "%" +
							 * "，未实现盈利="+(int) profit );
							 */
							sum_short = sum_short + (int) option.getPosition();
							option.setUnrealized_profit(profit);
							option.setShort_ratio(short_ratio);
							opCloseList.add(option);
						}
					} // end if<0
						// 若是long option,则有65%的盈利则分别列出
					if (option.getPosition() > 0) {
						if (long_ratio > 65) {
							/*
							 * System.out.println(option.getDate() + "日到期" + option.getSymbol() + "执行价" +
							 * (int) option.getStrikePrice() + "持有" + (int) option.getPosition() + "张" +
							 * option.getRight() + "，未实现盈利=" + String.format("%.2f", long_ratio) + "%" +
							 * "，未实现盈利="+(int) profit );
							 */
							sum_long = sum_long + (int) option.getPosition();
							option.setUnrealized_profit(profit);
							option.setLong_ratio(long_ratio);
							opCloseList.add(option);
						}
					} // end if >0
				} // end if OPT
				}//end if account
			} // end for i

			opCloseList.sort((y, x) -> Double.compare(x.getUnrealized_profit(), y.getUnrealized_profit()));// 这方法需要jdk1.8以上

			for (Option op : opCloseList) {
				if (op.getPosition() > 0) {
					System.out.println(op.getDate() + "日到期" + op.getSymbol() + "执行价" + (int) op.getStrikePrice() + "持有"
							+ (int) op.getPosition() + "张" + op.getRight() + "，未实现盈利="
							+ String.format("%.2f", op.getLong_ratio()) + "%" + "，未实现盈利="
							+ (int) op.getUnrealized_profit());
					// System.out.println(op.getSymbol()+"long ration is
					// :"+op.getLong_ratio()+",unrealized profit is:"+op.getUnrealized_profit());
				}

				if (op.getPosition() < 0) {
					System.out.println(op.getDate() + "日到期" + op.getSymbol() + "执行价" + (int) op.getStrikePrice() + "持有"
							+ (int) op.getPosition() + "张" + op.getRight() + "，未实现盈利="
							+ String.format("%.2f", op.getShort_ratio()) + "%" + "，未实现盈利="
							+ (int) op.getUnrealized_profit());
					// System.out.println(op.getSymbol()+"short ration is
					// :"+op.getShort_ratio()+",unrealized profit is:"+op.getUnrealized_profit());
				}

			}

			System.out.println("建议平仓short的数量小计为:" + sum_short);
			System.out.println("建议平仓Long的数量小计为:" + sum_long);

			return opCloseList;

		}

	// 全部是期权的时候
	public void getCloseOptionList(List<Option> optionList) {
		int sum_short = 0;
		int sum_long = 0;
		for (int i = 0; i < optionList.size(); i++) {

			Option option = new Option();
			option = optionList.get(i);
			double op_price = option.getOptionPrice() * 100;
			double op_avgprice = option.getAvgCost();
			// 未实现盈利百分比
			double short_ratio = (1 - op_price / op_avgprice) * 100;
			double long_ratio = 100 * (op_price - op_avgprice) / op_avgprice;

			// 未实现盈利金额
			double profit = (op_price - op_avgprice) * option.getPosition();

			// 先判断如果position为负，即short put or call则把盈利65%以上的分别列出来。
			if (option.getPosition() < 0) {

				if (short_ratio > 70) {
					System.out.println(
							option.getDate() + "日到期" + option.getSymbol() + "执行价" + (int) option.getStrikePrice() + "持有"
									+ (int) option.getPosition() + "张" + option.getRight() + "，未实现盈利%="
									+ String.format("%.2f", short_ratio) + "%" + "，未实现盈利=" + (int) profit);
					sum_short = sum_short + (int) option.getPosition();
				}
			}
			// 若是long option,则有65%的盈利则分别列出
			if (option.getPosition() > 0) {
				if (long_ratio > 65) {
					System.out.println(
							option.getDate() + "日到期" + option.getSymbol() + "执行价" + (int) option.getStrikePrice() + "持有"
									+ (int) option.getPosition() + "张" + option.getRight() + "，未实现盈利="
									+ String.format("%.2f", long_ratio) + "%" + "，未实现盈利=" + (int) profit);
					sum_long = sum_long + (int) option.getPosition();
				}
			}

		} // end for

		System.out.println("建议平仓short的数量小计为:" + sum_short);
		System.out.println("建议平仓Long的数量小计为:" + sum_long);
	}

	// 返回所有的期权
	public List<Option> getALLOP(String path) {

		String filetext = "";// 取出本地文件
		filetext = dealfile.ReadFile(path);
		int i = 0;// 按本地文件行走
		int j = 0;// 按本地文件1行再分开走
		String[] row = filetext.split("\\r\\n");// 转义

		for (i = 0; i < row.length; i++)// 进入文本里，每次一行记录开始循环
		{
			Option op = new Option();

			String str = row[i]; // str为一行的内容
			String contract[] = str.split("\\|");
			for (j = 0; j < contract.length; j++) {
				String unit[] = contract[j].split("=");
				if (contract[j].contains("conid")) {
					op.setConid(Integer.parseInt(unit[1]));
					continue;
				}

				if (contract[j].contains("account")) {
					op.setAccount(unit[1]);
					continue;
				}
				if (contract[j].contains("localSymbol")) {
					op.setLocalSymbol(unit[1]);
					continue;
				}
				if (contract[j].contains("secType")) {
					op.setSecType(unit[1]);
					continue;
				}
				if (contract[j].contains("right")) {
					op.setRight(unit[1]);
					continue;
				}
				if (contract[j].contains("symbol")) {
					op.setSymbol(unit[1]);
					continue;
				}
				if (contract[j].contains("date")) {
					op.setDate(unit[1]);
					continue;
				}
				if (contract[j].contains("position")) {
					op.setPosition(Double.valueOf(unit[1]));
					continue;
				}
				if (contract[j].contains("avgCost")) {
					op.setAvgCost(Double.valueOf(unit[1]));
					continue;
				}
				if (contract[j].contains("strikePrice")) {
					op.setStrikePrice(Double.valueOf(unit[1]));
					continue;
				}
				if (contract[j].contains("optionPrice")) {
					op.setOptionPrice(Double.valueOf(unit[1]));
					continue;
				}
				if (contract[j].contains("delta")) {
					op.setDelta(Double.valueOf(unit[1]));
					continue;
				}
				if (contract[j].contains("gamma")) {
					op.setGamma(Double.valueOf(unit[1]));
					continue;
				}
				if (contract[j].contains("vega")) {
					op.setVega(Double.valueOf(unit[1]));
					continue;
				}
				if (contract[j].contains("theta")) {
					op.setTheta(Double.valueOf(unit[1]));
					continue;
				}
				if (contract[j].contains("imvolatility")) {
					op.setImvolatility(Double.valueOf(unit[1]));

					continue;
				}
				if (contract[j].contains("underlyingPrice")) {
					op.setUnderlyingPrice(Double.valueOf(unit[1]));

					continue;
				}

			} // end for j
			optionList.add(op);
		} // end for i
		return optionList;
	}

//将position中的每行记录缺少的市场数据从Market文件中的取出来组合
	public List<Security> twoinoneSecuity(List<Security> secPositionList,List<Security> secMarketList)
	{
		List<Security> secList=new ArrayList<Security>();
		for(int i=0;i<secPositionList.size();i++)
		{
			Security secposition= secPositionList.get(i);
			for(int j=0;j<secMarketList.size();j++)
			{
				Security secmarket=secMarketList.get(j);
				//找到相同的conid，将所有信息补齐
				if(secposition.getConid()==secmarket.getConid())
		{		
			//为期权，则补Greek
			if(secposition.getSecType().equalsIgnoreCase("OPT")&&secmarket.getSecType().equals("OPT"))
			{
				Option opposition = (Option) secposition;
				Option opmarket=(Option)secmarket;
				
				opposition.setImvolatility(opmarket.getImvolatility());
				opposition.setDelta(opmarket.getDelta());
				opposition.setOptionPrice(opmarket.getOptionPrice());
				opposition.setGamma(opmarket.getGamma());
				opposition.setVega(opmarket.getVega());
				opposition.setTheta(opmarket.getTheta());
				opposition.setUnderlyingPrice(opmarket.getUnderlyingPrice());
				opposition.setOptionPrice(opmarket.getOptionPrice());
				secList.add(opposition);
			}
			//为股票，则补price
			if(secposition.getSecType().equalsIgnoreCase("STK")&&secmarket.getSecType().equals("STK"))
			{
				Stock stkpositon=(Stock)secposition;
				Stock stkmarket=(Stock) secmarket;
				stkpositon.setPrice(stkmarket.getPrice());
				secList.add(stkpositon);
			}
		}
			}//end for j
		}//end for i
		for(int k=0;k<secMarketList.size();k++)  		
		{	
			Index idxpositon=new Index();
			Security sec=secMarketList.get(k);
		//为指数，则把价格补进去
		if(sec.getSecType().equalsIgnoreCase("IND"))
			{
			
			Index idxmarket=(Index) sec;
			secList.add(idxmarket);
			}//end if Index
		}
		return secList;
	}
	
	

	// 返回所有的stock，option，ETF
	public List<Security> getALLSecruity(String path) {
		List<Security> securityList = new ArrayList<Security>();// 全部头寸都进入此
		String filetext = "";// 取出本地文件
		filetext = dealfile.ReadFile(path);
		int i = 0;// 按本地文件行走
		int j = 0;// 按本地文件1行再分开走
		String[] row = filetext.split("\\r\\n");// 转义

		for (i = 0; i < row.length; i++)// 进入文本里，每次一行记录开始循环
		{

			String str = row[i]; // str为一行的内容
			// 该行记录为期权时进入
			if (str.contains("secType=OPT")) {
				Option op = new Option();
				String contract[] = str.split("\\|");
				for (j = 0; j < contract.length; j++) {
					String unit[] = contract[j].split("=");
					if (contract[j].contains("conid")) {
						op.setConid(Integer.parseInt(unit[1]));
						continue;
					}

					if (contract[j].contains("account")) {
						op.setAccount(unit[1]);
						continue;
					}
					if (contract[j].contains("localSymbol")) {
						op.setLocalSymbol(unit[1]);
						continue;
					}
					if (contract[j].contains("secType")) {
						op.setSecType(unit[1]);
						continue;
					}
					if (contract[j].contains("right")) {
						op.setRight(unit[1]);
						continue;
					}
					if (contract[j].contains("symbol")) {
						op.setSymbol(unit[1]);
						continue;
					}
					if (contract[j].contains("date")) {
						op.setDate(unit[1]);
						continue;
					}
					if (contract[j].contains("position")) {
						op.setPosition(Double.valueOf(unit[1]));
						continue;
					}
					if (contract[j].contains("avgCost")) {
						op.setAvgCost(Double.valueOf(unit[1]));
						continue;
					}
					if (contract[j].contains("strikePrice")) {
						op.setStrikePrice(Double.valueOf(unit[1]));
						continue;
					}
					if (contract[j].contains("optionPrice")) {
						op.setOptionPrice(Double.valueOf(unit[1]));
						continue;
					}
					if (contract[j].contains("delta")) {
						op.setDelta(Double.valueOf(unit[1]));
						continue;
					}
					if (contract[j].contains("gamma")) {
						op.setGamma(Double.valueOf(unit[1]));
						continue;
					}
					if (contract[j].contains("vega")) {
						op.setVega(Double.valueOf(unit[1]));
						continue;
					}
					if (contract[j].contains("theta")) {
						op.setTheta(Double.valueOf(unit[1]));
						continue;
					}
					if (contract[j].contains("imvolatility")) {
						op.setImvolatility(Double.valueOf(unit[1]));

						continue;
					}
					if (contract[j].contains("underlyingPrice")) {
						op.setUnderlyingPrice(Double.valueOf(unit[1]));

						continue;
					}
				} // end for OPT j

				securityList.add(op);
			} // end if opt
			else if (str.contains("secType=STK")) {
				Stock stk = new Stock();

				String contract[] = str.split("\\|");
				for (j = 0; j < contract.length; j++) {
					String unit[] = contract[j].split("=");
					if (contract[j].contains("conid")) {
						stk.setConid(Integer.parseInt(unit[1]));
						continue;
					}
					if (contract[j].contains("account")) {
						stk.setAccount(unit[1]);
						continue;
					}
					if (contract[j].contains("secType")) {
						stk.setSecType(unit[1]);
						continue;
					}
					if (contract[j].contains("symbol")) {
						stk.setSymbol(unit[1]);
						continue;
					}

					if (contract[j].contains("position")) {
						stk.setPosition(Double.valueOf(unit[1]));
						continue;
					}
					if (contract[j].contains("avgCost")) {
						stk.setAvgCost(Double.valueOf(unit[1]));
						continue;
					}
					if (contract[j].contains("price")) {
						stk.setPrice(Double.valueOf(unit[1]));
						continue;
					}
				} // end for STK j
				securityList.add(stk);
			} // end if STK
				// 指数则进入
			else if (str.contains("secType=IND")) {
				Index indx = new Index();

				String contract[] = str.split("\\|");
				for (j = 0; j < contract.length; j++) {
					String unit[] = contract[j].split("=");
					if (contract[j].contains("conid")) {
						indx.setConid(Integer.parseInt(unit[1]));
						continue;
					}
					if (contract[j].contains("account")) {
						indx.setAccount(unit[1]);
						continue;
					}
					if (contract[j].contains("secType")) {
						indx.setSecType(unit[1]);
						continue;
					}
					if (contract[j].contains("symbol")) {
						indx.setSymbol(unit[1]);
						continue;
					}

					if (contract[j].contains("price")) {
						indx.setPrice(Double.valueOf(unit[1]));
						continue;
					}
				} // end for STK j
				securityList.add(indx);
			} // end if STK

		} // end for i
		return securityList;
	}

	// 将同一个symbol的全部头寸进行计算，列出当前数量
	public void getNeutralOPNum(Map<String, List<Security>> map) {
		int i = 0;
		// 每次一个标的
		for (Map.Entry<String, List<Security>> entry : map.entrySet()) {
			int neuOPNum = 0;
			double deltaSum = 0;
			double calldeltasum = 0;
			double putdeltasum = 0;
			int shortputNum = 0;

			String symbol = entry.getKey();

			// 如果为SPX或者COMP指数则跳出本次循环
			if (symbol.equalsIgnoreCase("SPX") || symbol.equalsIgnoreCase("COMP"))
				continue;

			else // 为STK和OPT的进入计算
			{
				List<Security> list = entry.getValue();
				int number = list.size();

				if (number <= 1) // 只有一个，基本为STK的话则不用管理
					continue;
				else {
					// 股票和期权全部进行计算
					for (i = 0; i < list.size(); i++) {
						Security sec = list.get(i);
						String tpye = sec.getSecType();
						double delta = 0;
						// 为股票类型
						if (tpye.equalsIgnoreCase("STK")) {
							Stock stock = (Stock) sec;
							// 中性数量
							neuOPNum = neuOPNum + (int) stock.getPosition() / 100;
							// 当前delta
							delta = stock.getPrice() * stock.getPosition();
							deltaSum = deltaSum + delta;
						}
						// 为期权类型
						if (tpye.equalsIgnoreCase("OPT")) {
							Option option = (Option) sec;
							delta = option.getDelta() * option.getPosition() * option.getUnderlyingPrice() * 100;

							if (option.getRight().equalsIgnoreCase("C")) {
								neuOPNum = neuOPNum + (int) option.getPosition();
								calldeltasum = calldeltasum + delta;
								deltaSum = deltaSum + delta;
							}
							if (option.getRight().equalsIgnoreCase("P")) {
								putdeltasum = putdeltasum + delta;

								shortputNum = shortputNum - (int) option.getPosition();
								deltaSum = deltaSum + delta;
							}

						} // end if OPT
							// System.out.println(sec.getSymbol()+sec.getSecType()+sec.getPosition());
					} // end for i
				}
			} // end else
			System.out.println("Symbol=" + symbol + "持有数量" + neuOPNum + "手，总体delta为：" + String.format("%.0f", deltaSum)
					+ " ，其中的call delta 为：" + String.format("%.0f", calldeltasum) + " ，short put数量为：" + shortputNum
					+ "手，short put delta为：" + String.format("%.0f", putdeltasum));
		} // end for map

	}

	// 将已有的position list类型转成map类型
	public Map<String, List<Security>> getMapofSecList(List<Security> secList) {
		int position_size = secList.size();
		int i = 0;
		int j = 0;

		// 打印出具体内容
		// secList.forEach(se->{System.out.print(se.getSymbol()+se.getSecType()+se.getPosition());});
		Map<String, List<Security>> secListofmap = new HashMap<String, List<Security>>();

		// 遍历position，在secList找出相同的symbol，放到map里，然后连成list，已经有的就不放了
		for (i = 0; i < position_size; i++) {

			Security sec = secList.get(i);// 取出Security对象
			String symbol = sec.getSymbol();// 作为map的key
			// 如果在map中已经有这个symbol了，则跳出本次i循环
			if (secListofmap.keySet().contains(symbol))
				continue;

			else {
				List<Security> sameSymbolList = new ArrayList<Security>();

				// 把第一次出现symbol的Security对象先加到sameSymbolList里
				sameSymbolList.add(sec);
				for (j = i + 1; j < position_size; j++) {
					Security samesymbolsec = secList.get(j);// 取出其余有相同symbol的Security对象

					// 如果是同一个标的,新建map，并加到neutralOPList里
					if (samesymbolsec.getSymbol().equalsIgnoreCase(symbol)) {
						sameSymbolList.add(samesymbolsec);
					} // end if 同一标的
				} // end for j

				secListofmap.put(symbol, sameSymbolList);
			}
		} // end for i

		/*
		 * for(String key : secListofmap.keySet()){ //只能遍历key
		 * System.out.println("Key = "+key); }
		 * System.out.println(secListofmap.keySet());
		 * 
		 * 
		 * for(Map.Entry<String,List<Security>> entry : secListofmap.entrySet()) {
		 * System.out.println("Key = "+entry.getKey()); List<Security>
		 * list=entry.getValue(); for (int x=0;x<list.size();x++) { Security sec=
		 * list.get(x);
		 * System.out.println(sec.getSymbol()+sec.getSecType()+sec.getConid()); } }
		 */

		return secListofmap;
	}

	// 将已有的position list类型转成map类型
	public Map<String, List<Security>> getMapofSecList(List<Security> secList,String accut) {
		int position_size = secList.size();
		int i = 0;
		int j = 0;

		
		Map<String, List<Security>> secListofmap = new HashMap<String, List<Security>>();

		// 遍历position，在secList找出相同的symbol，放到map里，然后连成list，已经有的就不放了
		for (i = 0; i < position_size; i++) {

			Security sec = secList.get(i);// 取出Security对象
			//2个账号分别计算
			if(sec.getAccount().equals(accut))
			{
			String symbol = sec.getSymbol();// 作为map的key
			// 如果在map中已经有这个symbol了，则跳出本次i循环
			if (secListofmap.keySet().contains(symbol))
				continue;

			else {
				List<Security> sameSymbolList = new ArrayList<Security>();

				// 把第一次出现symbol的Security对象先加到sameSymbolList里
				sameSymbolList.add(sec);
				for (j = i + 1; j < position_size; j++) {
					Security samesymbolsec = secList.get(j);// 取出其余有相同symbol的Security对象
                  //2个账号分别计算
					if(samesymbolsec.getAccount().equals(accut))
					{
					// 如果是同一个标的,新建map，并加到neutralOPList里
					if (samesymbolsec.getSymbol().equalsIgnoreCase(symbol)) {
						sameSymbolList.add(samesymbolsec);
					} // end if 同一标的
					}
				} // end for j

				secListofmap.put(symbol, sameSymbolList);
			}
			}
		} // end for i


		return secListofmap;
	}
	
	
	public static void main(String args[]) throws IOException, InterruptedException {

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
	    /*
	     //2个账户分开的情况
	    //secList = daydelta.getALLSecruity(dealfile.path_U1001GREEK);
		// secList = daydelta.getALLSecruity(dealfile.path_U9238GREEK);
		Account account = new Account();
		account = daydelta.getALLPflioDelta(secList);// 获取并封装账户相关的Greek等信息
		daydelta.getALLCloseOptionList(secList);// 提示获取时间价值的Option
		daydelta.getNeutralOPNum(daydelta.getMapofSecList(secList));// 提示达到中性的数量
		dealfile.ResultWriteTOExcel(account);// 将每个账户写入excel里
		*/
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
		
	}

}
