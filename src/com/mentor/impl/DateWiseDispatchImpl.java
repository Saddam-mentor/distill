package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;


import com.mentor.action.DateWiseDispatchAction;

import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;
public class DateWiseDispatchImpl {

	public boolean printReportImpl(DateWiseDispatchAction act) {
		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "WholeSale" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "WholeSale" + File.separator + "pdf";
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		boolean printFlag = false;
		reportQuery = "select x.dat , SUM(x.fl2sale) as salefl2, SUM(x.fl2bsale) as salefl2b,  SUM(x.cl2sale) as salecl2"+
			     " from "+
			       " ( SELECT a.dt_date as dat, SUM(c.quantity) as quantity, "+
				   " SUM(round(CAST(float8(((b.dispatch_bottle)*c.quantity)/1000)   as numeric), 2)) as fl2sale,0 as fl2bsale,0 as cl2sale "+
					
					" FROM "+
					" fl2d.gatepass_to_districtwholesale_fl2_fl2b a, "+
					" fl2d.fl2_stock_trxn_fl2_fl2b b  ,  "+
		            " distillery.packaging_details c "+

					" WHERE a.int_fl2_fl2b_id = b.int_fl2_fl2b_id AND c.package_id = b.int_pckg_id "+
					" AND a.vch_from ='FL2' "+
		        
					 " AND a.vch_gatepass_no=b.vch_gatepass_no "+
		           " AND a.dt_date=b.dt   "+
				   " AND a.dt_date BETWEEN '"
				   
					+ Utility.convertUtilDateToSQLDate(act.getFromdate())
					+ "' AND "
					+ " '"
					+ Utility.convertUtilDateToSQLDate(act.getTodate())
					+ "' group by a.dt_date " +
					
					" union " +


				" SELECT  a.dt_date as dat,SUM(c.quantity) as quantity, "+
				" 0 as fl2sale,	SUM(round(CAST(float8(((b.dispatch_bottle)*c.quantity)/1000)  as numeric), 2)) as fl2bsale ,0 as cl2sale "+
				
				" FROM "+
				"	fl2d.gatepass_to_districtwholesale_fl2_fl2b a, "+
				"	fl2d.fl2_stock_trxn_fl2_fl2b b  , "+
		        "    distillery.packaging_details c "+

				"	WHERE a.int_fl2_fl2b_id = b.int_fl2_fl2b_id AND c.package_id = b.int_pckg_id "+
				"	AND a.vch_from ='FL2B' "+
		        
				"	AND a.vch_gatepass_no=b.vch_gatepass_no "+
		        "   AND a.dt_date=b.dt  "+
				"   AND a.dt_date BETWEEN '" 
				+ Utility.convertUtilDateToSQLDate(act.getFromdate())
				+ "' AND "
				+ " '"
				+ Utility.convertUtilDateToSQLDate(act.getTodate())
				+ "' group by a.dt_date " +
					
				" union "+

				" SELECT a.dt_date as dat,SUM(c.quantity) as quantity, "+
				" 0 as fl2bsale ,0 as fl2sale,	SUM(round(CAST(float8(((b.dispatch_bottle)*c.quantity)/1000)   as numeric), 2)) as cl2sale "+
				
				"	FROM "+
				"	fl2d.gatepass_to_districtwholesale_fl2_fl2b a, "+
				"	fl2d.fl2_stock_trxn_fl2_fl2b b  ,  "+
		        "    distillery.packaging_details c "+

				" WHERE a.int_fl2_fl2b_id = b.int_fl2_fl2b_id AND c.package_id = b.int_pckg_id "+
				" AND a.vch_from ='CL2' "+
		        
				" AND a.vch_gatepass_no=b.vch_gatepass_no "+
		         "  AND a.dt_date=b.dt  "+
				 "  AND a.dt_date BETWEEN ' "
				
					+ Utility.convertUtilDateToSQLDate(act.getFromdate())
					+ "' AND "
					+ " '"
					+ Utility.convertUtilDateToSQLDate(act.getTodate())
					+ "' group by a.dt_date " +
					
				")  x group by x.dat ";
		
		try {
			con = ConnectionToDataBase.getConnection();

			
			//System.out.println("-------------mohsin---------"+reportQuery);
			

			pst = con.prepareStatement(reportQuery);

			rs = pst.executeQuery();
			if (rs.next()) {

				rs = pst.executeQuery();

				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate",
						Utility.convertUtilDateToSQLDate(act.getFromdate()));
				parameters.put("toDate",
						Utility.convertUtilDateToSQLDate(act.getTodate()));

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator
						+ "datewisedispatchaction.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
								+ "datewisedispatchaction" + n + ".pdf");

				//DateWiseDatatable dt = new DateWiseDatatable();
			
			
			
				act.setPdfname("datewisedispatchaction" + n + ".pdf");
				act.setPrintFlag(true);
				printFlag = true;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("No Data Found", "No Data Found"));
				act.setPrintFlag(false);
				printFlag = false;
			}

		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return printFlag;
	}

	

	// -----------------------generate excel---------------------------------

	public boolean write(DateWiseDispatchAction act)
	 {

		Connection con = null;
		
		double fl2_sale = 0;
		double fl2b_sale = 0;
		double cl_sale = 0;
		
		String sql = "";

		sql = "select x.dat , SUM(x.fl2sale) as salefl2, SUM(x.fl2bsale) as salefl2b,  SUM(x.cl2sale) as salecl2"+
			     " from "+
		       " ( SELECT a.dt_date as dat, SUM(c.quantity) as quantity, "+
			   " SUM(round(CAST(float8(((b.dispatch_bottle)*c.quantity)/1000)   as numeric), 2)) as fl2sale,0 as fl2bsale,0 as cl2sale "+
				
				" FROM "+
				" fl2d.gatepass_to_districtwholesale_fl2_fl2b a, "+
				" fl2d.fl2_stock_trxn_fl2_fl2b b  ,  "+
	            " distillery.packaging_details c "+

				" WHERE a.int_fl2_fl2b_id = b.int_fl2_fl2b_id AND c.package_id = b.int_pckg_id "+
				" AND a.vch_from ='FL2' "+
	        
				 " AND a.vch_gatepass_no=b.vch_gatepass_no "+
	           " AND a.dt_date=b.dt   "+
			   " AND a.dt_date BETWEEN '"
			   
				+ Utility.convertUtilDateToSQLDate(act.getFromdate())
				+ "' AND "
				+ " '"
				+ Utility.convertUtilDateToSQLDate(act.getTodate())
				+ "' group by a.dt_date " +
				
				" union " +


			" SELECT  a.dt_date as dat,SUM(c.quantity) as quantity, "+
			" 0 as fl2sale,	SUM(round(CAST(float8(((b.dispatch_bottle)*c.quantity)/1000)  as numeric), 2)) as fl2bsale ,0 as cl2sale "+
			
			" FROM "+
			"	fl2d.gatepass_to_districtwholesale_fl2_fl2b a, "+
			"	fl2d.fl2_stock_trxn_fl2_fl2b b  , "+
	        "    distillery.packaging_details c "+

			"	WHERE a.int_fl2_fl2b_id = b.int_fl2_fl2b_id AND c.package_id = b.int_pckg_id "+
			"	AND a.vch_from ='FL2B' "+
	        
			"	AND a.vch_gatepass_no=b.vch_gatepass_no "+
	        "   AND a.dt_date=b.dt  "+
			"   AND a.dt_date BETWEEN '" 
			+ Utility.convertUtilDateToSQLDate(act.getFromdate())
			+ "' AND "
			+ " '"
			+ Utility.convertUtilDateToSQLDate(act.getTodate())
			+ "' group by a.dt_date " +
				
			" union "+

			" SELECT a.dt_date as dat,SUM(c.quantity) as quantity, "+
			" 0 as fl2bsale ,0 as fl2sale,	SUM(round(CAST(float8(((b.dispatch_bottle)*c.quantity)/1000)   as numeric), 2)) as cl2sale "+
			
			"	FROM "+
			"	fl2d.gatepass_to_districtwholesale_fl2_fl2b a, "+
			"	fl2d.fl2_stock_trxn_fl2_fl2b b  ,  "+
	        "    distillery.packaging_details c "+

			" WHERE a.int_fl2_fl2b_id = b.int_fl2_fl2b_id AND c.package_id = b.int_pckg_id "+
			" AND a.vch_from ='CL2' "+
	        
			" AND a.vch_gatepass_no=b.vch_gatepass_no "+
	         "  AND a.dt_date=b.dt  "+
			 "  AND a.dt_date BETWEEN ' "
			
				+ Utility.convertUtilDateToSQLDate(act.getFromdate())
				+ "' AND "
				+ " '"
				+ Utility.convertUtilDateToSQLDate(act.getTodate())
				+ "' group by a.dt_date " +
				
			")  x group by x.dat ";
		
		

		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = "";
		try {
			con = ConnectionToDataBase.getConnection();
			pstmt = con.prepareStatement(sql);
			// System.out.println("==SQL=11111=" + sql);
			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 8000);
			worksheet.setColumnWidth(2, 4000);
			worksheet.setColumnWidth(3, 9999);
			worksheet.setColumnWidth(4, 9999);
			worksheet.setColumnWidth(5, 4000);
			worksheet.setColumnWidth(6, 10000);
			worksheet.setColumnWidth(7, 9000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" Date Wise Dispatch FL2/FL2B/CL2 From"
					+ " " + Utility.convertUtilDateToSQLDate(act.getFromdate())
					+ " " + " To " + " "
					+ Utility.convertUtilDateToSQLDate(act.getTodate()));
			rowhead0.setHeight((short) 700);
			XSSFCellStyle cellStyl = workbook.createCellStyle();
			cellStyl = workbook.createCellStyle();
			XSSFFont hSSFFont = workbook.createFont();
			hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
			hSSFFont.setFontHeightInPoints((short) 12);
			hSSFFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			hSSFFont.setColor(HSSFColor.GREEN.index);
			cellStyl.setFont(hSSFFont);
			cellhead0.setCellStyle(cellStyl);
			XSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			XSSFCellStyle unlockcellStyle = workbook.createCellStyle();
			unlockcellStyle.setLocked(false);
			k = k + 1;
			XSSFRow rowhead = worksheet.createRow((int) 1);
			
			XSSFCell cellhead1 = rowhead.createCell((int) 0);
			cellhead1.setCellValue("S.No.");
			cellhead1.setCellStyle(cellStyle);
			
			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("Date");
			cellhead2.setCellStyle(cellStyle);
			
			
			
			XSSFCell cellhead5 = rowhead.createCell((int) 2);
			cellhead5.setCellValue("FL2 Sale");
			cellhead5.setCellStyle(cellStyle);
			
			XSSFCell cellhead7 = rowhead.createCell((int) 3);
			cellhead7.setCellValue("FL2B Sale");
			cellhead7.setCellStyle(cellStyle);
			
			XSSFCell cellhead9 = rowhead.createCell((int) 4);
			cellhead9.setCellValue("CL Sale");
			cellhead9.setCellStyle(cellStyle);
			
			int i = 0;
			
			while (rs.next()) {
				
				
				
				Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("dat"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);

				
				
				
				fl2_sale = fl2_sale + rs.getDouble("salefl2");
				fl2b_sale = fl2b_sale + rs.getDouble("salefl2b");
				cl_sale = cl_sale + rs.getDouble("salecl2");

				//System.out.println("-------------"+fl2_sale);
				

			
				

				k++;
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);
				
				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(date);
				
				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(fl2_sale);
				
				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(fl2b_sale);
				
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(cl_sale);
				
				

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath + "/ExciseUp/WholeSale/excel/" + n + "dateWiseDispatchAction.xls");
							
					 act.setExlname(n + "dateWiseDispatchAction");

					
					
					
			
			
	
			
			
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();

			flag = true;
			act.setExcelFlag(true);
			con.close();
		} catch (Exception e) {
			// System.out.println("xls2" + e.getMessage());
			e.printStackTrace();
		} 
		 finally {
				try {
					if (con != null)
						con.close();
					if (pstmt != null)
						pstmt.close();
					if (rs != null)
						rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return flag;
	}
	

	

}
