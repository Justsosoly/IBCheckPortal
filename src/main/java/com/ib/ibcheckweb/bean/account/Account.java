package com.ib.ibcheckweb.bean.account;

public class Account {

	String account;
	String date;
	int portfolio_Delta;
	int option_delta;
	int stock_delta;

	int portfolio_cost;


	int spx;
	int nasdaq;
	int spx_delta;

	int portfolioGamma;
	int portfoliovega;
	int portfoliotheta;
	int op_num = 0;


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
	
	
	public int getSpx() {
		return spx;
	}
	public void setSpx(int spx) {
		this.spx = spx;
	}
	public int getNasdaq() {
		return nasdaq;
	}
	public void setNasdaq(int nasdaq) {
		this.nasdaq = nasdaq;
	}
	public int getSpx_delta() {
		return spx_delta;
	}
	public void setSpx_delta(int spx_delta) {
		this.spx_delta = spx_delta;
	}
	public int getOption_delta() {
		return option_delta;
	}
	public void setOption_delta(int option_delta) {
		this.option_delta = option_delta;
	}
	public int getStock_delta() {
		return stock_delta;
	}
	public void setStock_delta(int stock_delta) {
		this.stock_delta = stock_delta;
	}
	public int getOp_num() {
		return op_num;
	}
	public void setOp_num(int op_num) {
		this.op_num = op_num;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getPortfolioDelta() {
		return portfolio_Delta;
	}
	public void setPortfolioDelta(int portfolioDelta) {
		this.portfolio_Delta = portfolioDelta;
	}
	public int getPortfolioGamma() {
		return portfolioGamma;
	}
	public void setPortfolioGamma(int portfolioGamma) {
		this.portfolioGamma = portfolioGamma;
	}
	public int getPortfoliovega() {
		return portfoliovega;
	}
	public void setPortfoliovega(int portfoliovega) {
		this.portfoliovega = portfoliovega;
	}
	public int getPortfoliotheta() {
		return portfoliotheta;
	}
	public void setPortfoliotheta(int portfoliotheta) {
		this.portfoliotheta = portfoliotheta;
	}
	public int getLong_call_delta() {
		return long_call_delta;
	}
	public void setLong_call_delta(int long_call_delta) {
		this.long_call_delta = long_call_delta;
	}
	public int getShort_call_delta() {
		return short_call_delta;
	}
	public void setShort_call_delta(int short_call_delta) {
		this.short_call_delta = short_call_delta;
	}
	public int getLong_put_delta() {
		return long_put_delta;
	}
	public void setLong_put_delta(int long_put_delta) {
		this.long_put_delta = long_put_delta;
	}
	public int getShort_put_delta() {
		return short_put_delta;
	}
	public void setShort_put_delta(int short_put_delta) {
		this.short_put_delta = short_put_delta;
	}
	public int getLong_call_num() {
		return long_call_num;
	}
	public void setLong_call_num(int long_call_num) {
		this.long_call_num = long_call_num;
	}
	public int getShort_call_num() {
		return short_call_num;
	}
	public void setShort_call_num(int short_call_num) {
		this.short_call_num = short_call_num;
	}
	public int getLong_put_num() {
		return long_put_num;
	}
	public void setLong_put_num(int long_put_num) {
		this.long_put_num = long_put_num;
	}
	public int getShort_put_num() {
		return short_put_num;
	}
	public void setShort_put_num(int short_put_num) {
		this.short_put_num = short_put_num;
	}
	public int getLong_call_cost() {
		return long_call_cost;
	}
	public void setLong_call_cost(int long_call_cost) {
		this.long_call_cost = long_call_cost;
	}
	public int getShort_call_cost() {
		return short_call_cost;
	}
	public void setShort_call_cost(int short_call_cost) {
		this.short_call_cost = short_call_cost;
	}
	public int getLong_put_cost() {
		return long_put_cost;
	}
	public void setLong_put_cost(int long_put_cost) {
		this.long_put_cost = long_put_cost;
	}
	public int getShort_put_cost() {
		return short_put_cost;
	}
	public void setShort_put_cost(int short_put_cost) {
		this.short_put_cost = short_put_cost;
	}
	public int getCall_cost() {
		return call_cost;
	}
	public void setCall_cost(int call_cost) {
		this.call_cost = call_cost;
	}
	public int getPut_cost() {
		return put_cost;
	}
	public void setPut_cost(int put_cost) {
		this.put_cost = put_cost;
	}
	public int getCall_delta() {
		return call_delta;
	}
	public void setCall_delta(int call_delta) {
		this.call_delta = call_delta;
	}
	public int getPut_delta() {
		return put_delta;
	}
	public void setPut_delta(int put_delta) {
		this.put_delta = put_delta;
	}
	public int getCall_num() {
		return call_num;
	}
	public void setCall_num(int call_num) {
		this.call_num = call_num;
	}
	public int getPut_num() {
		return put_num;
	}
	public void setPut_num(int put_num) {
		this.put_num = put_num;
	}
	public int getPortfolio_cost() {
		return portfolio_cost;
	}
	public void setPortfolio_cost(int portfolio_cost) {
		this.portfolio_cost = portfolio_cost;
	}
}
