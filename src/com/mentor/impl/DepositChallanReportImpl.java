package com.mentor.impl;

 import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
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


 
import com.mentor.action.DepositChallanReportAction;  
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class DepositChallanReportImpl {
	public void printReport(DepositChallanReportAction act) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"
				+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null; 
		String unitTypeFilte="";
 

		try {
			con = ConnectionToDataBase.getConnection(); 
		 
			Date FromDate=Utility.convertUtilDateToSQLDate(act.getFromDate());
			Date toDate=Utility.convertUtilDateToSQLDate(act.getToDate());
			if(act.getUnitType()== null|| act.getUnitType().equalsIgnoreCase("")){
				unitTypeFilte="";
			}else{
				unitTypeFilte="and a.vch_challan_type='"+act.getUnitType()+"'";
			}
			String loginFilter="";
			if(ResourceUtil.getUserNameAllReq()!=null){
				
				
				if(ResourceUtil.getUserNameAllReq().length()>8){
					
				if(ResourceUtil.getUserNameAllReq().trim().substring(0,10).equalsIgnoreCase("Excise-DEO")){
					 loginFilter=" a.vch_user_id='"+ResourceUtil.getUserNameAllReq().trim()+"' and ";
				 }else{
					 loginFilter="";
				 }
				 }else{
					 loginFilter=""; 
				 }
			}
			if(act.getRadio().equalsIgnoreCase("D")){
			reportQuery=" select SUBSTRING (a.vch_user_id,12)district,a.int_challan_id,a.vch_challan_no,a.date_challan_date,c.head||'||'||b.vch_g6_head_code as head_code,a.double_amt,a.int_distillery_id, "+
						" case when a.vch_challan_type='D' then (select vch_undertaking_name from public.dis_mst_pd1_pd2_lic where int_app_id_f=a.int_distillery_id) "+
						" when a.vch_challan_type='B' then (select brewery_nm from public.bre_mst_b1_lic where vch_app_id_f=a.int_distillery_id) "+
						" when a.vch_challan_type='S' then (select sugarmill_nm from sugarmill_mst_sm1_lic where vch_app_id_f=a.int_distillery_id) "+
						" when a.vch_challan_type='FL2D' then (select vch_firm_name from licence.fl2_2b_2d_19_20 where int_app_id=a.int_distillery_id) "+
						" when a.vch_challan_type='Other' then '' "+
						" when a.vch_challan_type='HBR' then (select concat(name_of_hbr,'-', id) from hotel_bar_rest.registration_for_hotels_bars_restraunt where id=a.int_distillery_id) "+
						" when a.vch_challan_type='Shop' then (select concat(vch_name_of_shop,'-', vch_shop_type,'-', serial_no) from retail.retail_shop where serial_no=a.int_distillery_id) "+
						" when a.vch_challan_type='BWFL' then (select vch_firm_name||'-'||( "+
						" case when ab.vch_license_type='1' then 'BWFL2A' when ab.vch_license_type='2' then 'BWFL2B'  "+
						" when ab.vch_license_type='3' then 'BWFL2C' when ab.vch_license_type='4' then 'BWFL2D' end )  "+
						" from bwfl.registration_of_bwfl_lic_holder_19_20 ab where ab.int_id=a.int_distillery_id) end as unit_nane, "+
						" case when a.vch_challan_type='D' then 'Distillery' when a.vch_challan_type='B' then  'Brewery'    "+
						" when a.vch_challan_type='BWFL' then 'BWFL' when a.vch_challan_type='FL2D' then 'FL2D' when a.vch_challan_type='S' then 'Sugar Mill' else a.vch_challan_type end as type "+
						" from revenue.g6_challan_Deposit a  ,revenue.g6_challn_deposit_detail b , "+
						" licence.challan_heads c where   " +loginFilter+
						" a.int_challan_id=b.int_challan_id and b.vch_head_code=c.head "+
						//unitTypeFilte+
						" and a.date_challan_date between '"+FromDate+"' and '"+toDate+"' order by (a.vch_user_id,12),a.vch_challan_type,unit_nane ";
			}else{
				reportQuery=" select SUBSTRING (a.vch_user_id,12)district,sum(a.double_amt)double_amt " +
						"from revenue.g6_challan_Deposit a  ,revenue.g6_challn_deposit_detail b , "+
						" licence.challan_heads c where   " +loginFilter+
						" a.int_challan_id=b.int_challan_id and b.vch_head_code=c.head "+
						//unitTypeFilte+
						" and a.date_challan_date between '"+FromDate+"' and '"+toDate+"' group by district order by district";
	
			}
			 
			System.out.println("reportQuery---------" + reportQuery);
			pst = con.prepareStatement(reportQuery);
			

			rs = pst.executeQuery();

			if (rs.next()) {
				
				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate", Utility.convertUtilDateToSQLDate(act.getFromDate()));
				parameters.put("toDate", Utility.convertUtilDateToSQLDate(act.getToDate()));
				//parameters.put("type", type);
			 	JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
			 	if(act.getRadio().equalsIgnoreCase("D")){
			 	jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator+ "DepositChallanReportDetail.jasper");
			 	}else{
			 		jasperReport = (JasperReport) JRLoader.loadObject(relativePath
							+ File.separator+ "DepositChallanReportSummary.jasper");
			 	}
				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator + "DepositChallanReport" + "-" + n + ".pdf");
				act.setPdf_name("DepositChallanReport" + "-" + n+ ".pdf");
				act.setPrintFlag(true);
				
			} else { 
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"No Data Found!!", "No Data Found!!"));
				act.setPrintFlag(false);
			}
		} catch (JRException e) {
			act.setPrintFlag(false);
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
		} catch (Exception e) {
			act.setPrintFlag(false);
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
			}
		}
	}
	
	public boolean writeExcel(DepositChallanReportAction act) {

		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double amount=0.0;

		String sql = "";

	 
		String loginFilter="";
		if(ResourceUtil.getUserNameAllReq()!=null){/*
			if(ResourceUtil.getUserNameAllReq().trim().substring(0,10).equalsIgnoreCase("Excise-DEO")){
				 loginFilter=" a.vch_user_id='"+ResourceUtil.getUserNameAllReq().trim()+"' and ";
			 }else{
				 loginFilter="";
			 }
		*/

			
			
			if(ResourceUtil.getUserNameAllReq().length()>8){
				
			if(ResourceUtil.getUserNameAllReq().trim().substring(0,10).equalsIgnoreCase("Excise-DEO")){
				 loginFilter=" a.vch_user_id='"+ResourceUtil.getUserNameAllReq().trim()+"' and ";
			 }else{
				 loginFilter="";
			 }
			 }else{
				 loginFilter=""; 
			 }
		
		
		}	
		if(act.getRadio().equalsIgnoreCase("D")){
				sql=" select SUBSTRING (a.vch_user_id,12)district,a.int_challan_id,a.vch_challan_no,a.date_challan_date,c.head||'||'||b.vch_g6_head_code as head_code,a.double_amt,a.int_distillery_id, "+
					" case when a.vch_challan_type='D' then (select vch_undertaking_name from public.dis_mst_pd1_pd2_lic where int_app_id_f=a.int_distillery_id) "+
					" when a.vch_challan_type='B' then (select brewery_nm from public.bre_mst_b1_lic where vch_app_id_f=a.int_distillery_id) "+
					" when a.vch_challan_type='S' then (select sugarmill_nm from sugarmill_mst_sm1_lic where vch_app_id_f=a.int_distillery_id) "+
					" when a.vch_challan_type='FL2D' then (select vch_firm_name from licence.fl2_2b_2d_19_20 where int_app_id=a.int_distillery_id) "+
					" when a.vch_challan_type='Other' then '' "+
					" when a.vch_challan_type='HBR' then (select concat(name_of_hbr,'-', id) from hotel_bar_rest.registration_for_hotels_bars_restraunt where id=a.int_distillery_id) "+
					" when a.vch_challan_type='Shop' then (select concat(vch_name_of_shop,'-', vch_shop_type,'-', serial_no) from retail.retail_shop where serial_no=a.int_distillery_id) "+
					" when a.vch_challan_type='BWFL' then (select vch_firm_name||'-'||( "+
					" case when ab.vch_license_type='1' then 'BWFL2A' when ab.vch_license_type='2' then 'BWFL2B'  "+
					" when ab.vch_license_type='3' then 'BWFL2C' when ab.vch_license_type='4' then 'BWFL2D' end )  "+
					" from bwfl.registration_of_bwfl_lic_holder_19_20 ab where ab.int_id=a.int_distillery_id) end as unit_nane, "+
					" case when a.vch_challan_type='D' then 'Distillery' when a.vch_challan_type='B' then  'Brewery'    "+
					" when a.vch_challan_type='BWFL' then 'BWFL' when a.vch_challan_type='FL2D' then 'FL2D' when a.vch_challan_type='S' then 'Sugar Mill' else a.vch_challan_type end as type "+
					" from revenue.g6_challan_Deposit a  ,revenue.g6_challn_deposit_detail b , "+
					" licence.challan_heads c where   " +loginFilter+
					" a.int_challan_id=b.int_challan_id and b.vch_head_code=c.head "+
					//unitTypeFilte+
					" and a.date_challan_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' order by (a.vch_user_id,12),a.vch_challan_type,unit_nane ";
	}else{
		sql=" select SUBSTRING (a.vch_user_id,12)district,sum(a.double_amt)double_amt " +
				"from revenue.g6_challan_Deposit a  ,revenue.g6_challn_deposit_detail b , "+
				" licence.challan_heads c where   " +loginFilter+
				" a.int_challan_id=b.int_challan_id and b.vch_head_code=c.head "+
				//unitTypeFilte+
				" and a.date_challan_date between '"+Utility.convertUtilDateToSQLDate(act.getFromDate())+"' and '"+Utility.convertUtilDateToSQLDate(act.getToDate())+"' group by district order by district";

	}

		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");
			worksheet.setColumnWidth(0, 2000);
			worksheet.setColumnWidth(1, 4300);
			worksheet.setColumnWidth(2, 5000);
			worksheet.setColumnWidth(3, 5000);
			worksheet.setColumnWidth(4, 4000);
			worksheet.setColumnWidth(5, 4000);
			worksheet.setColumnWidth(6, 4000);
			worksheet.setColumnWidth(7, 4000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue("Deposit Challan Report from "+ Utility.convertUtilDateToSQLDate(act.getFromDate())+" to "+Utility.convertUtilDateToSQLDate(act.getToDate()));

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
			if(act.getRadio().equalsIgnoreCase("D")){
				XSSFCell cellhead3 = rowhead.createCell((int) 2);
				cellhead3.setCellValue("Unit Name");
				cellhead3.setCellStyle(cellStyle);
	
				XSSFCell cellhead8 = rowhead.createCell((int) 3);
				cellhead8.setCellValue("Challan Number");
				cellhead8.setCellStyle(cellStyle);
	
				XSSFCell cellhead9 = rowhead.createCell((int) 4);
				cellhead9.setCellValue("Challan Date");
				cellhead9.setCellStyle(cellStyle);
				XSSFCell cellhead10 = rowhead.createCell((int) 5);
				cellhead10.setCellValue("Head Code");
				cellhead10.setCellStyle(cellStyle);
				XSSFCell cellhead11 = rowhead.createCell((int) 6);
				cellhead11.setCellValue("Amount");
				cellhead11.setCellStyle(cellStyle);
			}else{
				XSSFCell cellhead11 = rowhead.createCell((int) 2);
				cellhead11.setCellValue("Amount");
				cellhead11.setCellStyle(cellStyle);
			}

			int i = 0;
			while (rs.next()) {
				amount=amount+rs.getDouble("double_amt"); 
				k++;
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k - 1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("district"));
				if(act.getRadio().equalsIgnoreCase("D")){
					XSSFCell cellC1 = row1.createCell((int) 2);
					cellC1.setCellValue(rs.getString("unit_nane"));
					XSSFCell cellD1 = row1.createCell((int) 3);
					cellD1.setCellValue(rs.getString("vch_challan_no"));
					XSSFCell cellE1 = row1.createCell((int) 4);
					cellE1.setCellValue(rs.getString("date_challan_date"));
					XSSFCell cellF1 = row1.createCell((int) 5);
					cellF1.setCellValue(rs.getString("head_code"));
					XSSFCell cellG1 = row1.createCell((int) 6);
					cellG1.setCellValue(rs.getDouble("double_amt"));
				}else{

					XSSFCell cellG1 = row1.createCell((int) 2);
					cellG1.setCellValue(rs.getDouble("double_amt"));
				}
			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" + n
					+ "DepositChallanReport.xls");
			act.setExlname(n + "DepositChallanReport");

			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue("");
			cellA1.setCellStyle(cellStyle);
			if(act.getRadio().equalsIgnoreCase("D")){
				XSSFCell cellA2 = row1.createCell((int) 1);
				cellA2.setCellValue(" ");
				cellA2.setCellStyle(cellStyle);
			
				XSSFCell cellA3 = row1.createCell((int) 2);
				cellA3.setCellValue("");
				cellA3.setCellStyle(cellStyle);
	
				XSSFCell cellA4 = row1.createCell((int) 3);
				cellA4.setCellValue("");
				cellA4.setCellStyle(cellStyle);
	 
				XSSFCell cellA5 = row1.createCell((int) 4);
				cellA5.setCellValue("");
				cellA5.setCellStyle(cellStyle);
				XSSFCell cellA6 = row1.createCell((int) 5);
				cellA6.setCellValue("Total");
				cellA6.setCellStyle(cellStyle);
				XSSFCell cellA7 = row1.createCell((int) 6);
				cellA7.setCellValue(amount);
				cellA7.setCellStyle(cellStyle);
			}else{
				XSSFCell cellA3 = row1.createCell((int) 1);
				cellA3.setCellValue("Total");
				cellA3.setCellStyle(cellStyle);
				XSSFCell cellA4 = row1.createCell((int) 2);
				cellA4.setCellValue(amount);
				cellA4.setCellStyle(cellStyle);
			}
 
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();

			flag = true;
			act.setExcelFlag(true);
			con.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return flag;

	}
}
