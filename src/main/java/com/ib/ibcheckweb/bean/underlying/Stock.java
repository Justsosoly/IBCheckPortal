package com.ib.ibcheckweb.bean.underlying;
	
	//股票类型
	public class Stock extends Security
	{
		 int conid;
		 String account;
		 String secType;
		 String symbol;
		 double position;
		 double avgCost;
		 double price;
		 
		 public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public int getConid() {
			return conid;
		}
		public void setConid(int conid) {
			this.conid = conid;
		}
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public String getSecType() {
			return secType;
		}
		public void setSecType(String secType) {
			this.secType = secType;
		}
		public String getSymbol() {
			return symbol;
		}
		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}
		public double getPosition() {
			return position;
		}
		public void setPosition(double position) {
			this.position = position;
		}
		public double getAvgCost() {
			return avgCost;
		}
		public void setAvgCost(double avgCost) {
			this.avgCost = avgCost;
		}

	}


