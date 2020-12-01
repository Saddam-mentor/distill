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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mentor.action.CSD_Import_permit_ReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class CSD_Import_permit_ReportImpl {

	// =======================print report=================================

	public void printReport(CSD_Import_permit_ReportAction act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String filter="";
		System.out.println("distId=="+act.getDistrict_id());
		if(act.getDistrict_id()==999){
			filter="";
		}else{
			
			filter=" and a.district_id ='"+act.getDistrict_id()+"' ";
		}
		try {
			con = ConnectionToDataBase.getConnection();
			
			
	reportQuery =   "select  a.app_id , permit_nmbr ,digital_sign_date, sum(b.no_of_cases) as cases , d.description from                        "+
                    " fl2d.application_csd_permit_mst_20_21 a, fl2d.application_csd_permit_detail_20_21 b, district d                                                                           "+
                    " where a.app_id =b.app_id and a.vch_approved ='APPROVED'  "+filter+" and " +
                    " a.district_id =d.districtid and a.digital_sign_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND " +
                    " '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'  group by a.app_id , permit_nmbr ,digital_sign_date ,"+
                    " d.description order by d.description,a.digital_sign_date";
			
		
			System.out.println("reportQuery=="+reportQuery);
				pst = con.prepareStatement(reportQuery);
				 
				rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
				parameters.put("toDate",Utility.convertUtilDateToSQLDate(act.getToDate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "csd_import_report.jasper");
				//jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "FL2_FL2B_CL2_Strength_Report.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "csd_import_report" +  "_" + n + ".pdf");
				act.setPdfName("csd_import_report" + "_" +n + ".pdf");
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
	
	//----------------------generate excel for bwfl and fl2d wise report------------------------------
	
	public boolean writeExcel(CSD_Import_permit_ReportAction act)
	{

		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double boxesTotal = 0;
		double blTotal = 0;
		
		String excelQuery = null;		
		String type="";
		String year = act.getYear();
		String filter = "";
		
		System.out.println("distId=="+act.getDistrict_id());
		if(act.getDistrict_id()==999){
			filter="";
		}else{
			
			filter=" and a.district_id ='"+act.getDistrict_id()+"' ";
		}
			excelQuery=  "select  a.app_id , permit_nmbr ,digital_sign_date, sum(b.no_of_cases) as cases , d.description from                        "+
		                    " fl2d.application_csd_permit_mst_20_21 a, fl2d.application_csd_permit_detail_20_21 b, district d                                                                           "+
		                    " where a.app_id =b.app_id and a.vch_approved ='APPROVED'  "+filter+" and " +
		                    " a.district_id =d.districtid and a.digital_sign_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' AND " +
		                    " '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "'  group by a.app_id , permit_nmbr ,digital_sign_date ,"+
		                    " d.description order by d.description,a.digital_sign_date";

		System.out.println(" ---excelQuery---"+excelQuery);
		
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
			//System.out.println("---excelQuery--"+excelQuery);
			
			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");

			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 5000);

			worksheet.setColumnWidth(2, 12000);

			worksheet.setColumnWidth(3, 3500);
			worksheet.setColumnWidth(4, 3500);
			worksheet.setColumnWidth(5, 15000);
			worksheet.setColumnWidth(6, 3500);
			worksheet.setColumnWidth(7, 4500);
			worksheet.setColumnWidth(8, 20000);
			worksheet.setColumnWidth(9, 9000);
			worksheet.setColumnWidth(10, 9000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);			
			
			cellhead0.setCellValue(" Report CSD Import Permit " + " From " + " " + Utility.convertUtilDateToSQLDate(act.getFromDate())+ " " + 
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
			cellhead3.setCellValue("Permit No.");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Permit Date");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("No. Of Cases");
			cellhead5.setCellStyle(cellStyle);

			
			

			while (rs.next()) {

				Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("digital_sign_date"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);
				

				
				k++;

				XSSFRow row1 = worksheet.createRow((int) k);

				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("description"));

				XSSFCell cellK1 = row1.createCell((int) 2);
				cellK1.setCellValue(rs.getString("permit_nmbr"));

				XSSFCell cellC1 = row1.createCell((int) 3);
				cellC1.setCellValue(date);

				XSSFCell cellD1 = row1.createCell((int) 4);
				cellD1.setCellValue(rs.getInt("cases"));

				
	

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n + "_" +"-csd_import_report.xlsx");
			act.setExlname(n  + "_" + "-csd_import_report");

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
	//=========================================
	
	public ArrayList districtListImpl(CSD_Import_permit_ReportAction act) {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query="";
		SelectItem item = new SelectItem();
		item.setLabel("--All--");
		item.setValue(999);
		list.add(item);
		
		conn = ConnectionToDataBase.getConnection();

		try {
		
	        query = " SELECT districtid, description FROM public.district where districtid>0 and districtid in " +
	        		" (select distinct district_id from  fl2d.application_csd_permit_mst_20_21) order by description";
	      

		System.out.println("district list==="+query);
				ps = conn.prepareStatement(query);
				
			rs = ps.executeQuery();
			while (rs.next()) {

				item = new SelectItem();

				item.setValue(rs.getInt("districtid"));
				item.setLabel(rs.getString("description"));
				
				list.add(item);

			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
			e.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	public ArrayList deoDistrictListImpl(CSD_Import_permit_ReportAction act) {
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query="";
		SelectItem item = new SelectItem();
		
		
		conn = ConnectionToDataBase.getConnection();

		try {
			
			query = " SELECT districtid, description FROM public.district where deo='"+ResourceUtil.getUserNameAllReq()+"' ";
		
		

		System.out.println("district list==="+query);
				ps = conn.prepareStatement(query);
				
			rs = ps.executeQuery();
			while (rs.next()) {

				item = new SelectItem();

				item.setValue(rs.getInt("districtid"));
				item.setLabel(rs.getString("description"));
				
				list.add(item);

			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
			e.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	//==============================================
		
		
	
}
