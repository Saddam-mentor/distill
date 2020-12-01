package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Date;
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

import com.mentor.action.ena_order_report_action; 
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class ena_order_report_impl {

	 
	
	
	public void printReportWUP(ena_order_report_action act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery1 = null;
		
		String type=null;
		

		try {
			con = ConnectionToDataBase.getConnection();
			
				reportQuery1="SELECT DISTINCT  x.*, (SELECT vch_undertaking_name from PUBLIC.dis_mst_pd1_pd2_lic  " +
                             " WHERE int_app_id_f=x.from_dis_id) as seller" +
                             "  from ("+
                             " SELECT c.vch_undertaking_name as Purcheses_Name , a.user3_dt as orderdate ,coalesce(a.permit_no,'NA') as orderno,a.user3_qty as Approvalqty ,a.purpose,a.from_dis_id" +
                             " from " +
                             " distillery.online_ena_purchase a ,PUBLIC.dis_mst_pd1_pd2_lic c " +
                             " where type ='WUP'  and  a.login_dis_id=c.int_app_id_f  " +
                             "  and a.digital_sign_dt is not null and user3_dt between  '"+Utility.convertUtilDateToSQLDate(act.getFromdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' ORDER BY user3_dt  )x";
						
						
						
				
			pst = con.prepareStatement(reportQuery1);
	 rs = pst.executeQuery();

			if (rs.next()) {

				 
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				//parameters.put("radio",type);
				parameters.put("fromdate",Utility.convertUtilDateToSQLDate(act.getFromdate()));
				parameters.put("todate",Utility.convertUtilDateToSQLDate(act.getTodate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "ena_order_report_wup.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "ena_order_report_wup" + "-" + n + ".pdf");
				act.setPdfName("ena_order_report_wup" + "-" + n + ".pdf");
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
	
	
	
	public void printReportOUP(ena_order_report_action act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		
		String reportQuery2 = null;
	
		String type=null;

		try {
			
			
			con = ConnectionToDataBase.getConnection();
            	reportQuery2="SELECT DISTINCT x.*, (SELECT vch_undertaking_name from PUBLIC.dis_mst_pd1_pd2_lic  " +
"WHERE int_app_id_f=x.from_dis_id) as Exporter" +
"  from (" +
" SELECT a.purchaser_oup as Importer , a.user3_dt as orderdate  ,coalesce(a.permit_no,'NA') as orderno ,a.user3_qty as Approvalqty ,  a.purpose,a.from_dis_id" +
" from " +
" distillery.online_ena_purchase a  " +
" where type ='OUP'  " +
  "  and a.digital_sign_dt is not null and user3_dt between  '"+Utility.convertUtilDateToSQLDate(act.getFromdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' ORDER BY user3_dt  )x";
            			
            	pst = con.prepareStatement(reportQuery2);
		
		
		
			
			
			//System.out.println("reportQuery jasper:-2"+reportQuery2);
			
				rs = pst.executeQuery();

			if (rs.next()) {

				 
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("radio",type);
				parameters.put("fromdate",Utility.convertUtilDateToSQLDate(act.getFromdate()));
				parameters.put("todate",Utility.convertUtilDateToSQLDate(act.getTodate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "ena_order_report_oup.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "ena_order_report_oup" + "-" + n + ".pdf");
				act.setPdfName("ena_order_report_oup" + "-" + n + ".pdf");
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
	
	
	
	
	public void printReportIUP(ena_order_report_action act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery3 = null;
		String type=null;
		//System.out.println("imp r");
	

		try {
			con = ConnectionToDataBase.getConnection();
			//System.out.println("imp r1");
			  reportQuery3="SELECT  DISTINCT x.*, (SELECT vch_undertaking_name from PUBLIC.dis_mst_pd1_pd2_lic  " +
" WHERE int_app_id_f=x.login_dis_id) as Importer" +
 " from (" +
" SELECT (a.imp_distillery_nm||' '||a.imp_state||' '||a.imp_country) as Exporter , a.user3_dt as orderdate  ,coalesce(a.permit_no,'NA') as orderno , a.user3_qty as Approvalqty ,a.purpose,a.login_dis_id" +
" from " +
" distillery.online_ena_purchase a   " +
" where type not in ('OUP','WUP') and a.digital_sign_dt is not null  and user3_dt between  '"+Utility.convertUtilDateToSQLDate(act.getFromdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' ORDER BY user3_dt )x";
					 
      			
      	 pst = con.prepareStatement(reportQuery3);
			
			
 	
				rs = pst.executeQuery();
			 
			if (rs.next()) {

				 
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("radio",type);
				parameters.put("fromdate",Utility.convertUtilDateToSQLDate(act.getFromdate()));
				parameters.put("todate",Utility.convertUtilDateToSQLDate(act.getTodate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "ena_order_report_iup.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "ena_order_report_iup" + "-" + n + ".pdf");
				act.setPdfName("ena_order_report_iup" + "-" + n + ".pdf");
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
	










	public boolean witeExcelWup(ena_order_report_action act) {


		Connection con = null;
		int boxes = 0;
		int bottles = 0;
		double bl = 0.0;
		double duty = 0.0;
		String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String type = "";
		String reportQuery = null; 

		try {			
			
			con = ConnectionToDataBase.getConnection(); 
			 
			
			reportQuery=	"SELECT DISTINCT  x.*, (SELECT vch_undertaking_name from PUBLIC.dis_mst_pd1_pd2_lic  " +
                    " WHERE int_app_id_f=x.from_dis_id) as seller" +
                    "  from ("+
                    " SELECT c.vch_undertaking_name as Purcheses_Name , a.user3_dt as orderdate ,coalesce(a.permit_no,'NA') as orderno,a.user3_qty as Approvalqty ,a.purpose,a.from_dis_id" +
                    " from " +
                    " distillery.online_ena_purchase a ,PUBLIC.dis_mst_pd1_pd2_lic c " +
                    " where type ='WUP'  and  a.login_dis_id=c.int_app_id_f  " +
                    "  and a.digital_sign_dt is not null and user3_dt between  '"+Utility.convertUtilDateToSQLDate(act.getFromdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' ORDER BY user3_dt  )x";
				
			pstmt = con.prepareStatement(reportQuery);

			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Sale Summary Report");

			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 20000);
			worksheet.setColumnWidth(2, 7000);
			worksheet.setColumnWidth(3, 7000);
			worksheet.setColumnWidth(4, 7000);
			worksheet.setColumnWidth(5, 7000);
			worksheet.setColumnWidth(6, 7000);
			

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" ENA ORDER REPORT WUP  " + Utility.convertUtilDateToSQLDate(act.getFromdate())+ " " + " To " + " "+ Utility.convertUtilDateToSQLDate(act.getTodate()));
			
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
			cellhead2.setCellValue("Purcheses_Name");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("seller");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("purpose");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("orderno");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("orderdate");
			cellhead6.setCellStyle(cellStyle);

			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Approvalqty");
			cellhead7.setCellStyle(cellStyle);
			

			while (rs.next()) {

			
				k++;

				XSSFRow row1 = worksheet.createRow((int) k);


				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

	
				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("Purcheses_Name"));

				XSSFCell cellB2 = row1.createCell((int) 2);
				cellB2.setCellValue(rs.getString("seller"));
				
				XSSFCell cellB3 = row1.createCell((int) 3);
				cellB3.setCellValue(rs.getString("purpose"));
				
				XSSFCell cellB4 = row1.createCell((int) 4);
				cellB4.setCellValue(rs.getString("orderno"));
				
				XSSFCell cellB5 = row1.createCell((int) 5);
				cellB5.setCellValue(rs.getDate("orderdate"));
				
				XSSFCell cellB6 = row1.createCell((int) 6);
				cellB6.setCellValue(rs.getLong("Approvalqty"));
			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n+ "ena_order_report.xls");
			act.setExlname(n + "ena_order_report");

			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);

			XSSFCell cellA2 = row1.createCell((int) 1);
			cellA2.setCellValue(" ");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 2);
			cellA3.setCellValue("  ");
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 3);
			cellA4.setCellValue("  ");
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue("  ");
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue("  ");
			cellA6.setCellStyle(cellStyle);

			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue("  ");
			cellA7.setCellStyle(cellStyle);

			
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

	
	
	
	public boolean witeExcelOup(ena_order_report_action act) {
		Connection con = null;
		con = ConnectionToDataBase.getConnection();
		   String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
      		FileOutputStream fileOut = null;
      		PreparedStatement pstmt = null;
      		ResultSet rs = null;
      		boolean flag = false;
      		long k = 0;
      		String date = null;

		String excelQuery = null;		
		
		
		excelQuery="SELECT DISTINCT x.*, (SELECT vch_undertaking_name from PUBLIC.dis_mst_pd1_pd2_lic  " +
				"WHERE int_app_id_f=x.from_dis_id) as Exporter" +
				"  from (" +
				" SELECT a.purchaser_oup as Importer , a.user3_dt as orderdate  ,coalesce(a.permit_no,'NA') as orderno ,a.user3_qty as Approvalqty ,  a.purpose,a.from_dis_id" +
				" from " +
				" distillery.online_ena_purchase a  " +
				" where type ='OUP'  " +
				  "  and a.digital_sign_dt is not null and user3_dt between  '"+Utility.convertUtilDateToSQLDate(act.getFromdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' ORDER BY user3_dt  )x";
				            			
				            	
		
             

           		try {			
           			
           			
           			pstmt = con.prepareStatement(excelQuery);
           			rs = pstmt.executeQuery();

           			XSSFWorkbook workbook = new XSSFWorkbook();
           			XSSFSheet worksheet = workbook.createSheet("ENA ORDER REPORT ");
                     
           			worksheet.setColumnWidth(0, 3000);
           			worksheet.setColumnWidth(1, 7000);
           			worksheet.setColumnWidth(2, 7000);
        			worksheet.setColumnWidth(3, 7000);      			
        			worksheet.setColumnWidth(4, 7000);
        			worksheet.setColumnWidth(5, 7000);
        			worksheet.setColumnWidth(6, 7000);
        			XSSFRow rowhead0 = worksheet.createRow((int) 0);
        			XSSFCell cellhead0 = rowhead0.createCell((int) 0);			
        			cellhead0.setCellValue(" ENA ORDER REPORT OUP BETWEEN FROM " + Utility.convertUtilDateToSQLDate(act.getFromdate())+ " " + 
        					" To " + " "+ Utility.convertUtilDateToSQLDate(act.getTodate()));
           		
        			rowhead0.setHeight((short) 700);
        			XSSFCellStyle cellStyl = workbook.createCellStyle();
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
        			cellhead2.setCellValue("Exporter Name");
        			cellhead2.setCellStyle(cellStyle);
        			


        			XSSFCell cellhead3 = rowhead.createCell((int) 2);
        			cellhead3.setCellValue("Importer  Name");
        			cellhead3.setCellStyle(cellStyle);

        			XSSFCell cellhead4 = rowhead.createCell((int) 3);
        			cellhead4.setCellValue("purpose");
        			cellhead4.setCellStyle(cellStyle);

        			XSSFCell cellhead5 = rowhead.createCell((int) 4);
        			cellhead5.setCellValue("Order No ");
        			cellhead5.setCellStyle(cellStyle);

        			XSSFCell cellhead6 = rowhead.createCell((int) 5);
        			cellhead6.setCellValue("Order Date ");
        			cellhead6.setCellStyle(cellStyle);
        			
        			XSSFCell cellhead7 = rowhead.createCell((int) 6);
        			cellhead7.setCellValue("Approved Quantity");
        			cellhead7.setCellStyle(cellStyle);
        			
        			
        			while (rs.next()) {
        				
        				k++;
        				
        				XSSFRow row1 = worksheet.createRow((int) k);
        				XSSFCell cellA1 = row1.createCell((int) 0);
        				cellA1.setCellValue(k-1);

        				XSSFCell cellB1 = row1.createCell((int) 1);
        				cellB1.setCellValue(rs.getString("Exporter"));

        				XSSFCell cellB2 = row1.createCell((int) 2);
        				cellB2.setCellValue(rs.getString("Importer"));
        				
        				XSSFCell cellB3 = row1.createCell((int) 3);
        				cellB3.setCellValue(rs.getString("purpose"));
        				
        				XSSFCell cellB4 = row1.createCell((int) 4);
        				cellB4.setCellValue(rs.getString("orderno"));
        				
        				XSSFCell cellB5 = row1.createCell((int) 5);
        				cellB5.setCellValue(rs.getDate("orderdate"));
        				
        				XSSFCell cellB6 = row1.createCell((int) 6);
        				cellB6.setCellValue(rs.getLong("Approvalqty"));
        				
        				
        				
        			}
        			Random rand = new Random();
        			int n = rand.nextInt(550) + 1;
        			fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n+ "-ena_order_report.xls");
        			act.setExlname(n + "-ena_order_report");

        			XSSFRow row1 = worksheet.createRow((int) k + 1);
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
        			cellA4.setCellValue("");
        			cellA4.setCellStyle(cellStyle);

        			XSSFCell cellA5 = row1.createCell((int) 4);
        			cellA5.setCellValue("");
        			cellA5.setCellStyle(cellStyle);

        			XSSFCell cellA6 = row1.createCell((int) 5);
        			cellA6.setCellValue("");
        			cellA6.setCellStyle(cellStyle);
        			
        			XSSFCell cellA7 = row1.createCell((int) 6);
        			cellA7.setCellValue("");
        			cellA7.setCellStyle(cellStyle);

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

	
	public boolean witeExcelIup(ena_order_report_action act) {
		Connection con = null;
		con = ConnectionToDataBase.getConnection();
		   String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
      		FileOutputStream fileOut = null;
      		PreparedStatement pstmt = null;
      		ResultSet rs = null;
      		boolean flag = false;
      		long k = 0;
      		String Date = null;

		String excelQuery = null;		
		 
		excelQuery="SELECT  DISTINCT x.*, (SELECT vch_undertaking_name from PUBLIC.dis_mst_pd1_pd2_lic  " +
				" WHERE int_app_id_f=x.login_dis_id) as Importer" +
				 " from (" +
				" SELECT (a.imp_distillery_nm||' '||a.imp_state||' '||a.imp_country) as Exporter , a.user3_dt as orderdate  ,coalesce(a.permit_no,'NA') as orderno , a.user3_qty as Approvalqty ,a.purpose,a.login_dis_id" +
				" from " +
				" distillery.online_ena_purchase a   " +
				" where type not in ('OUP','WUP') and a.digital_sign_dt is not null  and user3_dt between  '"+Utility.convertUtilDateToSQLDate(act.getFromdate())+"'  and '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' ORDER BY user3_dt )x";
									 	 
		 
           		try {			
           			
           			
           			pstmt = con.prepareStatement(excelQuery);
           			////System.out.println("reportQuery jasper:-3"+excelQuery);
           			rs = pstmt.executeQuery();

           			XSSFWorkbook workbook = new XSSFWorkbook();
           			XSSFSheet worksheet = workbook.createSheet("ENA ORDER REPORT ");
                     
           			worksheet.setColumnWidth(0, 3000);
           			worksheet.setColumnWidth(1, 7000);
           			worksheet.setColumnWidth(2, 7000);
        			worksheet.setColumnWidth(3, 7000);      			
        			worksheet.setColumnWidth(4, 7000);
        			worksheet.setColumnWidth(5, 7000);
        			worksheet.setColumnWidth(6, 7000);
        			XSSFRow rowhead0 = worksheet.createRow((int) 0);
        			XSSFCell cellhead0 = rowhead0.createCell((int) 0);			
        			cellhead0.setCellValue(" ENA ORDER IUP REPORT BETWEEN FROM " + Utility.convertUtilDateToSQLDate(act.getFromdate())+ " " + 
        					" To " + " "+ Utility.convertUtilDateToSQLDate(act.getTodate()));
           		
        			rowhead0.setHeight((short) 700);
        			XSSFCellStyle cellStyl = workbook.createCellStyle();
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
        			cellhead2.setCellValue("Exporter");
        			cellhead2.setCellStyle(cellStyle);
        			


        			XSSFCell cellhead3 = rowhead.createCell((int) 2);
        			cellhead3.setCellValue("Importer  Name");
        			cellhead3.setCellStyle(cellStyle);

        			XSSFCell cellhead4 = rowhead.createCell((int) 3);
        			cellhead4.setCellValue("purpose");
        			cellhead4.setCellStyle(cellStyle);

        			XSSFCell cellhead5 = rowhead.createCell((int) 4);
        			cellhead5.setCellValue("Order No ");
        			cellhead5.setCellStyle(cellStyle);

        			XSSFCell cellhead6 = rowhead.createCell((int) 5);
        			cellhead6.setCellValue("Order Date ");
        			cellhead6.setCellStyle(cellStyle);
        			
        			XSSFCell cellhead7 = rowhead.createCell((int) 6);
        			cellhead7.setCellValue("Approved Quantity");
        			cellhead7.setCellStyle(cellStyle);
        			
        			
        			while (rs.next()) {
        				
        				k++;
        				
        				XSSFRow row1 = worksheet.createRow((int) k);
        				XSSFCell cellA1 = row1.createCell((int) 0);
        				cellA1.setCellValue(k-1);

        				XSSFCell cellB1 = row1.createCell((int) 1);
        				cellB1.setCellValue(rs.getString("Exporter_Name"));

        				XSSFCell cellB2 = row1.createCell((int) 2);
        				cellB2.setCellValue(rs.getString("Importer"));
        				
        				XSSFCell cellB3 = row1.createCell((int) 3);
        				cellB3.setCellValue(rs.getString("purpose"));
        				
        				XSSFCell cellB4 = row1.createCell((int) 4);
        				cellB4.setCellValue(rs.getString("orderno"));
        				
        				XSSFCell cellB5 = row1.createCell((int) 5);
        				cellB5.setCellValue(rs.getDate("orderdate"));
        				
        				XSSFCell cellB6 = row1.createCell((int) 6);
        				cellB6.setCellValue(rs.getLong("Approvalqty"));
        				
        				
        				
        			}
        			Random rand = new Random();
        			int n = rand.nextInt(550) + 1;
        			fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n+ "-ena_order_report.xls");
        			act.setExlname(n + "-ena_order_report");

        			XSSFRow row1 = worksheet.createRow((int) k + 1);
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
        			cellA4.setCellValue("");
        			cellA4.setCellStyle(cellStyle);

        			XSSFCell cellA5 = row1.createCell((int) 4);
        			cellA5.setCellValue("");
        			cellA5.setCellStyle(cellStyle);

        			XSSFCell cellA6 = row1.createCell((int) 5);
        			cellA6.setCellValue("");
        			cellA6.setCellStyle(cellStyle);
        			
        			XSSFCell cellA7 = row1.createCell((int) 6);
        			cellA7.setCellValue("");
        			cellA7.setCellStyle(cellStyle);

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