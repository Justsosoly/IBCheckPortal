package com.ib.ibcheckweb.service.check;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.ib.ibcheckweb.bean.account.Account;
import com.ib.ibcheckweb.bean.underlying.Option;
import com.ib.ibcheckweb.bean.underlying.Security;


public class DealFile {

	String date = FileNameDate();

	public String path_U1001 = "/Users/jiao/Documents/IBAPI/U10019359/" + date;
	public String path_U9238 = "/Users/jiao/Documents/IBAPI/U9238923/" + date;
	//2个账号合并1个
	public String path_All="/Users/jiao/Documents/IBAPI/AccountAll/"+ date+"position";
	public String path_AllMarket = path_All + "Market";
	public String path_GREEK=path_All + "GREEK";
	
	public String path_U9238GREEK = path_U9238 + "GREEK";
	public String path_U1001GREEK = path_U1001 + "GREEK";

	public String FILE_NAME = "/Users/jiao/Documents/IBAPI/DailyCheckExcel.xlsx";

	//该方法只新建不更新 getGREEK用，增加判断是否文本里有该conid，有则不写，返回
	public void FileWriteGREEK(Security security, String pathGREEK) {
	
		String filetext = this.ReadFile(pathGREEK);
		String conid = "conid=" + String.valueOf(security.getConid());
		if (filetext.contains(conid))// 如果有该id则跳出
		{
			System.out.println("文件里已经写有conid=" + conid + "的GREEK，则不再写文件操作");
			return;
		} else
			try {
				BufferedWriter bufferedWriter = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(pathGREEK, true), "UTF-8")); // false则每次都覆盖

				bufferedWriter.write((showALLSecurity(security)));
				bufferedWriter.newLine();// 换行
				bufferedWriter.flush();
				bufferedWriter.close();
				System.out.println("将conid写进" + security.getConid());

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//end if file exist

	}


	// 将封装对象写入本地文件,getposition功能里会调用
	public void FileWritePosition(Security security, String path) {

		try {
			BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(path, true), "UTF-8")); // false则每次都覆盖

			bufferedWriter.write((showALLSecurity(security)));
			bufferedWriter.newLine();// 换行
			bufferedWriter.flush();
			bufferedWriter.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 输出全部头寸信息，写文件用
	public String showALLSecurity(Security sec) {
		String str = "";

		str = str + printFieldsValue(sec);// 不要在这里加换行，已经有换行了
		// +"\n";

		return str;

	}

	// 将List里的封装对象取出到字符串，写入文件或者打印出来，写文件用
	public String printFieldsValue(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		String str = "";
		try {
			for (Field f : fields) {
				f.setAccessible(true);
				str = str + f.getName() + "=" + f.get(obj) + "|";
				// System.out.println(f.getName()+" "+f.get(obj));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return str;
	}

	// 每天生成不同的文件名
	public String FileNameDate() {
		Date date = new Date();
		String filedate = "";

		String year = String.format("%tY", date);

		String month = String.format("%tm", date);

		String day = String.format("%td", date);

		filedate = year + month + day;
		// System.out.println("今天是："+filedate);

		return filedate;
	}
		
	public String FileNameTime() {
		Date date = new Date();
		String filetime = "";

		String year = String.format("%tY", date);

		String month = String.format("%tm", date);

		String day = String.format("%td", date);
		
		String hour = String.format("%tH", date);
		
		String min = String.format("%tM ", date);
		
		 

		filetime = year +"/"+ month+"/" + day+"/" +hour+":"+min;
		// System.out.println("今天是："+filedate);

		return filetime;
	}
	
	
	

//从本地文件读出
	public String ReadFile(String path) {
		StringBuffer buffer = new StringBuffer();
		File file = new File(path);
		if (file.exists()) {

			try {
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(new FileInputStream(path), "UTF-8"));
				String data = null;
				while ((data = bufferedReader.readLine()) != null) {

					buffer.append(data + "\r\n");
				}
				// System.out.println("the buffer is :"+buffer);
				bufferedReader.close();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//end if 
		return buffer.toString();
	}

	public void ResultWriteTOExcel(Account account) throws IOException {

		int rowNum = 1;

		// 如果文件存在，则不写文件头，取得sheet的最后一行添加
		File file = new File(FILE_NAME);
		if (file.exists()) {

			FileInputStream fileinputstream = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fileinputstream);

			XSSFSheet sheet = workbook.getSheetAt(0);
			rowNum = sheet.getLastRowNum();

			// rowNum++;

			Object[][] datatypes = { { this.FileNameTime(),
				account.getAccount(),
				
				account.getNasdaq(),
				account.getSpx(),
				
				account.getPortfolio_Delta(), 
				account.getStock_delta(),
				account.getOption_delta(),
				
				account.getSpx_delta(),
				account.getPortfoliotheta(),
				account.getPortfoliovega(),
				account.getPortfolioGamma(),
				
				account.getOp_num(),
				account.getPortfolio_cost(),
				
				account.getPut_delta(),
				account.getPut_num(),
				account.getPut_cost(),
				
				account.getCall_delta(),
				account.getCall_num(),
				account.getCall_cost(),
				
				account.getShort_put_delta(),
				account.getShort_put_num(),
				account.getShort_put_cost(),

				account.getLong_call_delta(),
				account.getLong_call_num(),
				account.getLong_call_cost(),
		
				account.getShort_call_delta(),
				account.getShort_call_num(),
				account.getShort_call_cost(),
				
				account.getLong_put_delta(),
				account.getLong_put_num(),
				account.getLong_put_cost()
				},
			};

			for (Object[] datatype : datatypes) {

				rowNum++;
				System.out.println("the last row num is: " + rowNum);
				Row row = sheet.createRow(rowNum);
			//	System.out.println("Row: " + row);

				int colNum = 0;
				for (Object field : datatype) {
					Cell cell = row.createCell(colNum++);
					if (field instanceof String) {
						cell.setCellValue((String) field);
					} else if (field instanceof Integer) {
						cell.setCellValue((Integer) field);
					}
				}
			}

			try {
				FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
				workbook.write(outputStream);
				workbook.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// 如果是第一次运行，则做表头并在其后添加新的行
		else {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("CheckRisk");
			sheet.setDefaultColumnWidth(16);

			// 写文件头
			Row header = sheet.createRow(0);

			Cell headerCell = header.createCell(0);
			headerCell.setCellValue("Date");
			headerCell = header.createCell(1);
			headerCell.setCellValue("Account");
		
			headerCell = header.createCell(2);
			headerCell.setCellValue("NASDAQ");
			headerCell = header.createCell(3);
			headerCell.setCellValue("SPX");
			
			
			headerCell = header.createCell(4);
			headerCell.setCellValue("PortfolioDelta");
			headerCell = header.createCell(5);
			headerCell.setCellValue("Stock Delta");
			headerCell = header.createCell(6);
			headerCell.setCellValue("Option Delta");
			
			headerCell = header.createCell(7);
			headerCell.setCellValue("SPXDelta");
			headerCell = header.createCell(8);
			headerCell.setCellValue("PortfolioTheta");
			headerCell = header.createCell(9);
			headerCell.setCellValue("PortfolioVega");
			headerCell = header.createCell(10);
			headerCell.setCellValue("PortfolioGamma");
			

			headerCell = header.createCell(11);
			headerCell.setCellValue("Option Num");
			headerCell = header.createCell(12);
			headerCell.setCellValue("Total Option Cost");
			
			
			headerCell = header.createCell(13);
			headerCell.setCellValue("Total Put Delta");
			headerCell = header.createCell(14);
			headerCell.setCellValue("Put Num");
			headerCell = header.createCell(15);
			headerCell.setCellValue("Put Cost");
			
			
			headerCell = header.createCell(16);
			headerCell.setCellValue("Total Call Delta");
			headerCell = header.createCell(17);
			headerCell.setCellValue("Call Num");
			headerCell = header.createCell(18);
			headerCell.setCellValue("Call Cost");
			
		
			headerCell = header.createCell(19);
			headerCell.setCellValue("Total Short Put Delta");
			headerCell = header.createCell(20);
			headerCell.setCellValue("Total Short Put Num");
			headerCell = header.createCell(21);
			headerCell.setCellValue("Total Short Put Cost");
			
			
			headerCell = header.createCell(22);
			headerCell.setCellValue("Total Long Call Delta");
			headerCell = header.createCell(23);
			headerCell.setCellValue("Total Long Call Num");
			headerCell = header.createCell(24);
			headerCell.setCellValue("Total Long Call Cost");
			

			headerCell = header.createCell(25);
			headerCell.setCellValue("Total Short Call Delta");
			headerCell = header.createCell(26);
			headerCell.setCellValue("Total Short Call Num");
			headerCell = header.createCell(27);
			headerCell.setCellValue("Total Short Call Cost");
			
			
			headerCell = header.createCell(28);
			headerCell.setCellValue("Total Long Put Delta");
			headerCell = header.createCell(29);
			headerCell.setCellValue("Total Long Put Num");
			headerCell = header.createCell(30);
			headerCell.setCellValue("Total Long Put Cost");
		


			Object[][] datatypes = { { this.FileNameTime(),
				account.getAccount(),
				
				account.getNasdaq(),
				account.getSpx(),
				
				account.getPortfolio_Delta(), 
				account.getStock_delta(),
				account.getOption_delta(),
				account.getSpx_delta(),
				account.getPortfoliotheta(),
				account.getPortfoliovega(),
				account.getPortfolioGamma(),
				account.getOp_num(),
				account.getPortfolio_cost(),
				
				account.getPut_delta(),
				account.getPut_num(),
				account.getPut_cost(),
				
				account.getCall_delta(),
				account.getCall_num(),
				account.getCall_cost(),
				
				account.getShort_put_delta(),
				account.getShort_put_num(),
				account.getShort_put_cost(),

				account.getLong_call_delta(),
				account.getLong_call_num(),
				account.getLong_call_cost(),
		
				account.getShort_call_delta(),
				account.getShort_call_num(),
				account.getShort_call_cost(),
				
				account.getLong_put_delta(),
				account.getLong_put_num(),
				account.getLong_put_cost()
				},
			};

			for (Object[] datatype : datatypes) {
				// Row row = sheet.createRow(rowNum++);

				rowNum = sheet.getLastRowNum();
				System.out.println("the lastrownum is: " + rowNum);
				rowNum++;
				Row row = sheet.createRow(rowNum);
				System.out.println("Row: " + row);

				int colNum = 0;
				for (Object field : datatype) {
					Cell cell = row.createCell(colNum++);
					if (field instanceof String) {
						cell.setCellValue((String) field);
					} else if (field instanceof Integer) {
						cell.setCellValue((Integer) field);
					}
				}
			}

			try {
				FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
				workbook.write(outputStream);
				workbook.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} // end else

		System.out.println("Done");
	}

	public void ResultAddtoExcel(Account account) {

	}

	public static void main(String args[]) throws IOException {
		DealFile dealfile = new DealFile();
		DayDelta daydelta = new DayDelta();

		List<Option> opList = new ArrayList<Option>();
	//	opList = daydelta.getALLOP(dealfile.path_U1001GREEK);
		opList = daydelta.getALLOP(dealfile.path_U9238GREEK);
	//	dealfile.ResultWriteTOExcel(daydelta.getOPTPflioDelta(opList));
	}

}
