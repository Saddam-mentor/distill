package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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

import com.mentor.action.DelayedExceptionReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class DelayedExceptionReportImpl {

	// =======================print report=================================

	public void printReport(DelayedExceptionReportAction act) {


		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		
		try {
			con = ConnectionToDataBase.getConnection();
			
			if (act.getRadio().equalsIgnoreCase("CL2"))
			{

			reportQuery = 	" SELECT DISTINCT b.dt_date as transaction_dt, b.cr_date as reporting_dt, " +
							" a.licence_type, a.licence_no, a.licensee_name, c.description,   " +
							" CASE WHEN b.dt_date IS NOT NULL  " +
							" THEN (DATE_PART('day', b.cr_date::timestamp - b.dt_date::timestamp))::text " +
							" WHEN b.dt_date IS NULL THEN 'PENDING' end as delay_days  " +
							" FROM fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN fl2d.cl2_trxn b " +
							" ON a.loging_id=b.int_id  " +
							" AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'  " +
							" AND DATE_PART('day', b.cr_date::timestamp - b.dt_date::timestamp)NOT IN(1,0) ,  " +
							" public.district c   " +
							" WHERE a.licence_type='CL2' AND a.district_id=c.districtid  " +
							" GROUP BY c.description, b.dt_date, b.cr_date, a.licence_type, " +
							" a.licence_no, a.licensee_name " +
							" ORDER BY c.description, b.dt_date";

			}
			
			else if (act.getRadio().equalsIgnoreCase("FL2B"))
			{
			
			reportQuery = 	" SELECT DISTINCT b.opening_date as transaction_dt, b.dt_date as reporting_dt, " +
							" a.licence_type, a.licence_no, a.licensee_name, c.description,  " +
							" CASE WHEN b.opening_date IS NOT NULL  " +
							" THEN (DATE_PART('day', b.dt_date::timestamp - b.opening_date::timestamp))::text " +
							" WHEN b.opening_date IS NULL THEN 'PENDING' end as delay_days   " +
							" FROM fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN fl2d.fl2b_trxn b " +
							" ON a.loging_id=b.int_id  " +
							" AND b.opening_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'  " +
							" AND DATE_PART('day', b.dt_date::timestamp - b.opening_date::timestamp)NOT IN(1,0) ,  " +
							" public.district c   " +
							" WHERE a.licence_type='FL2B' AND a.district_id=c.districtid  " +
							" GROUP BY c.description, b.opening_date, b.dt_date, a.licence_type, " +
							" a.licence_no, a.licensee_name ORDER BY c.description, b.opening_date ";

			}
			
			else if (act.getRadio().equalsIgnoreCase("FL2"))
			{
				
			reportQuery = 	" SELECT DISTINCT b.opening_date as transaction_dt, b.cr_date as reporting_dt, " +
							" a.licence_type, a.licence_no, a.licensee_name, c.description,  " +
							" CASE WHEN b.opening_date IS NOT NULL  " +
							" THEN (DATE_PART('day', b.cr_date::timestamp - b.opening_date::timestamp))::text " +
							" WHEN b.opening_date IS NULL THEN 'PENDING' end as delay_days   " +
							" FROM fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN fl2d.fl2_trxn b " +
							" ON a.loging_id::text=b.int_id  " +
							" AND b.opening_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'  " +
							" AND DATE_PART('day', b.cr_date::timestamp - b.opening_date::timestamp)NOT IN(1,0) , " +
							" public.district c   " +
							" WHERE a.licence_type='FL2' AND a.district_id=c.districtid  " +
							" GROUP BY c.description, b.opening_date, b.cr_date, a.licence_type, " +
							" a.licence_no, a.licensee_name " +
							" ORDER BY c.description, b.opening_date ";
		

			}
			else{
				reportQuery="";
			}

				pst = con.prepareStatement(reportQuery);
				//System.out.println("reportQuery----------"+reportQuery+"=======act.getRadio()--------"+act.getRadio());
				
				rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("radioType",act.getRadio());
				parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
				parameters.put("toDate",Utility.convertUtilDateToSQLDate(act.getToDate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "ExceptionDelayReport.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "ExceptionDelayReport" + "-" + n + ".pdf");
				act.setPdfName("ExceptionDelayReport" + "-" + n + ".pdf");
				act.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"No Data Found!!", "No Data Found!!"));
				act.setPrintFlag(false);
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
	
	
	
	}
	
	
	//----------------------generate excel for report------------------------------
	
		public boolean writeExcel(DelayedExceptionReportAction act)
		{


			Connection con = null;
			con = ConnectionToDataBase.getConnection();

			double boxesTotal = 0;
			double blTotal = 0;
			
			String excelQuery = null;		
			String type="";

			if (act.getRadio().equalsIgnoreCase("CL2"))
			{

			type="CL2";
			
			excelQuery = 	" SELECT DISTINCT b.dt_date as transaction_dt, b.cr_date as reporting_dt, " +
							" a.licence_type, a.licence_no, a.licensee_name, c.description,   " +
							" CASE WHEN b.dt_date IS NOT NULL  " +
							" THEN (DATE_PART('day', b.cr_date::timestamp - b.dt_date::timestamp))::text " +
							" WHEN b.dt_date IS NULL THEN 'PENDING' end as delay_days  " +
							" FROM fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN fl2d.cl2_trxn b " +
							" ON a.loging_id=b.int_id  " +
							" AND b.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'  " +
							" AND DATE_PART('day', b.cr_date::timestamp - b.dt_date::timestamp)NOT IN(1,0) ,  " +
							" public.district c   " +
							" WHERE a.licence_type='CL2' AND a.district_id=c.districtid  " +
							" GROUP BY c.description, b.dt_date, b.cr_date, a.licence_type, " +
							" a.licence_no, a.licensee_name " +
							" ORDER BY c.description, b.dt_date";

			}
			
			else if (act.getRadio().equalsIgnoreCase("FL2B"))
			{
			type="FL2B";
			
			excelQuery = 	" SELECT DISTINCT b.opening_date as transaction_dt, b.dt_date as reporting_dt, " +
							" a.licence_type, a.licence_no, a.licensee_name, c.description,  " +
							" CASE WHEN b.opening_date IS NOT NULL  " +
							" THEN (DATE_PART('day', b.dt_date::timestamp - b.opening_date::timestamp))::text " +
							" WHEN b.opening_date IS NULL THEN 'PENDING' end as delay_days   " +
							" FROM fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN fl2d.fl2b_trxn b " +
							" ON a.loging_id=b.int_id  " +
							" AND b.opening_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'  " +
							" AND DATE_PART('day', b.dt_date::timestamp - b.opening_date::timestamp)NOT IN(1,0) ,  " +
							" public.district c   " +
							" WHERE a.licence_type='FL2B' AND a.district_id=c.districtid  " +
							" GROUP BY c.description, b.opening_date, b.dt_date, a.licence_type, " +
							" a.licence_no, a.licensee_name ORDER BY c.description, b.opening_date ";

			}
			
			else if (act.getRadio().equalsIgnoreCase("FL2"))
			{
			type="FL2";
			
			excelQuery = 	" SELECT DISTINCT b.opening_date as transaction_dt, b.cr_date as reporting_dt, " +
							" a.licence_type, a.licence_no, a.licensee_name, c.description,  " +
							" CASE WHEN b.opening_date IS NOT NULL  " +
							" THEN (DATE_PART('day', b.cr_date::timestamp - b.opening_date::timestamp))::text " +
							" WHEN b.opening_date IS NULL THEN 'PENDING' end as delay_days   " +
							" FROM fl2d.opening_stock_fl2_fl2b_cl a LEFT JOIN fl2d.fl2_trxn b " +
							" ON a.loging_id::text=b.int_id  " +
							" AND b.opening_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND  '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'  " +
							" AND DATE_PART('day', b.cr_date::timestamp - b.opening_date::timestamp)NOT IN(1,0) , " +
							" public.district c   " +
							" WHERE a.licence_type='FL2' AND a.district_id=c.districtid  " +
							" GROUP BY c.description, b.opening_date, b.cr_date, a.licence_type, " +
							" a.licence_no, a.licensee_name " +
							" ORDER BY c.description, b.opening_date ";
		

			}
			else{
				excelQuery="";
			}

			
			String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
			FileOutputStream fileOut = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			boolean flag = false;
			long k = 0;
			String date = null;
			String date1 = null;
			

			try {			
				
				
				pstmt = con.prepareStatement(excelQuery);
				
				rs = pstmt.executeQuery();

				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet worksheet = workbook.createSheet("Barcode Report");

				worksheet.setColumnWidth(0, 3000);
				worksheet.setColumnWidth(1, 5000);

				worksheet.setColumnWidth(2, 20000);

				worksheet.setColumnWidth(3, 7000);
				worksheet.setColumnWidth(4, 7000);
				worksheet.setColumnWidth(5, 7000);
				worksheet.setColumnWidth(6, 5000);
				worksheet.setColumnWidth(7, 20000);
				worksheet.setColumnWidth(8, 9000);

				XSSFRow rowhead0 = worksheet.createRow((int) 0);
				XSSFCell cellhead0 = rowhead0.createCell((int) 0);			
				
				cellhead0.setCellValue(" Report Of " + type + " " + " From " + " " + Utility.convertUtilDateToSQLDate(act.getFromDate())+ " " + 
				" To " + " "+ Utility.convertUtilDateToSQLDate(act.getToDate()));
				
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
				cellhead2.setCellValue("District");
				cellhead2.setCellStyle(cellStyle);

				XSSFCell cellhead3 = rowhead.createCell((int) 2);
				cellhead3.setCellValue("License Number");
				cellhead3.setCellStyle(cellStyle);

				XSSFCell cellhead4 = rowhead.createCell((int) 3);
				cellhead4.setCellValue("Licensee Name");
				cellhead4.setCellStyle(cellStyle);

				XSSFCell cellhead5 = rowhead.createCell((int) 4);
				cellhead5.setCellValue("Transaction Date");
				cellhead5.setCellStyle(cellStyle);

				XSSFCell cellhead6 = rowhead.createCell((int) 5);
				cellhead6.setCellValue("Reporting Date");
				cellhead6.setCellStyle(cellStyle);

				XSSFCell cellhead7 = rowhead.createCell((int) 6);
				cellhead7.setCellValue("Delay In Days");
				cellhead7.setCellStyle(cellStyle);
				
				/*XSSFCell cellhead8 = rowhead.createCell((int) 7);
				cellhead8.setCellValue("Recieve From");
				cellhead8.setCellStyle(cellStyle);*/
				

				while (rs.next()) {

					if(rs.getDate("transaction_dt")!=null && rs.getDate("reporting_dt")!=null)
					{
					date = null;
					date1 = null;
					Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("transaction_dt"));
					DateFormat formatter;
					formatter = new SimpleDateFormat("dd/MM/yyyy");
					date = formatter.format(dat);
					
					Date dat1 = Utility.convertSqlDateToUtilDate(rs.getDate("reporting_dt"));
					date1 = formatter.format(dat1);
					}
					else{
						date = null;
						date1 = null;
					}
					
					k++;

					XSSFRow row1 = worksheet.createRow((int) k);

					XSSFCell cellA1 = row1.createCell((int) 0);
					cellA1.setCellValue(k-1);

					XSSFCell cellB1 = row1.createCell((int) 1);
					cellB1.setCellValue(rs.getString("description"));

					XSSFCell cellK1 = row1.createCell((int) 2);
					cellK1.setCellValue(rs.getString("licence_no"));

					XSSFCell cellC1 = row1.createCell((int) 3);
					cellC1.setCellValue(rs.getString("licensee_name"));					
					
					XSSFCell cellD1 = row1.createCell((int) 4);
					cellD1.setCellValue(date);

					XSSFCell cellE1 = row1.createCell((int) 5);
					cellE1.setCellValue(date1);

					XSSFCell cellF1 = row1.createCell((int) 6);
					cellF1.setCellValue(rs.getString("delay_days"));
					
					/*XSSFCell cellG1 = row1.createCell((int) 7);
					cellG1.setCellValue(rs.getString("delay_days"));*/
		

				}
				Random rand = new Random();
				int n = rand.nextInt(550) + 1;
				fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n+ "-ExceptionDelayReport.xls");
				act.setExlname(n + "-ExceptionDelayReport");

				/*XSSFRow row1 = worksheet.createRow((int) k + 1);

				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(" ");
				cellA1.setCellStyle(cellStyle);

				XSSFCell cellA2 = row1.createCell((int) 1);
				cellA2.setCellValue(" ");
				cellA2.setCellStyle(cellStyle);

				XSSFCell cellA3 = row1.createCell((int) 2);
				cellA3.setCellValue(" ");
				cellA3.setCellStyle(cellStyle);

				XSSFCell cellA4 = row1.createCell((int) 3);
				cellA4.setCellValue(" Total ");
				cellA4.setCellStyle(cellStyle);

				XSSFCell cellA5 = row1.createCell((int) 4);
				cellA5.setCellValue(boxesTotal);
				cellA5.setCellStyle(cellStyle);

				XSSFCell cellA6 = row1.createCell((int) 5);
				cellA6.setCellValue(blTotal);
				cellA6.setCellStyle(cellStyle);*/
				

				
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
		
				flag = true;
				act.setExcelFlag(true);
				con.close();
			} catch (Exception e) {			
				e.printStackTrace();
			} finally {
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
