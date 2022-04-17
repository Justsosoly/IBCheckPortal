package com.ib.ibcheckweb.sql;

import java.sql.*;

public class ConnectDB {

	
	  public static void main(String args[]) {
	        Connection conn=null;
	        try {
	          Class.forName("com.mysql.cj.jdbc.Driver");     
	          System.out.println("Success loading Mysql Driver!");
	        }
	        catch (Exception e) {
	          System.out.print("Error loading Mysql Driver!");
	          e.printStackTrace();
	        }

	        try {
	          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ibcheck", "root", "liaoyi2200");
	          System.out.println("Success connect Mysql server!");
	          conn.close();

	        }
	        catch (Exception e) {
	          System.out.print("get data error!");
	          e.printStackTrace();
	        }  
	    }

}
