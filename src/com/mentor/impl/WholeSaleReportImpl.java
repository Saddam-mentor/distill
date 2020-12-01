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

import com.mentor.action.StockAtWholeSellersAction;
import com.mentor.action.WholeSaleReportAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class WholeSaleReportImpl {


	
	
	// =======================print report=================================

	public void printReport(WholeSaleReportAction act)
	{

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String type=null;
		
		if(act.getRadio().equalsIgnoreCase("CL2")){
			
			type="CL";
			
		}else if(act.getRadio().equalsIgnoreCase("FL2")){
			
			type="FL";
			
		}else{
			type="BEER";
		}
		
		
		
		try {
			con = ConnectionToDataBase.getConnection();
			
					
				
			reportQuery = 	" select CONCAT(a.shop_id,'-',a.shop_nm) as idname,a.shop_id,a.shop_nm,d.brand_name,e.quantity,c.dispatch_bottle , c.dispatch_box ," +
					" (((c.dispatch_bottle)*e.quantity)/1000) as bl" +
				
					
					" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a," +
					" licence.fl2_2b_2d_19_20 b," +
					" fl2d.fl2_stock_trxn_fl2_fl2b c," +
					" distillery.brand_registration_19_20 d," +
					" distillery.packaging_details_19_20 e" +
					
					
					" where a.int_fl2_fl2b_id=b.int_app_id AND a.int_fl2_fl2b_id=c.int_fl2_fl2b_id AND" +
					" a.vch_gatepass_no=c.vch_gatepass_no and a.dt_date=c.dt AND" +
					"   d.brand_id=e.brand_id_fk AND c.int_pckg_id=e.package_id" +
					" AND a.dt_date between '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' " +
					" and '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' AND a.int_fl2_fl2b_id= '"+Integer.parseInt(act.getWholesaleid())+"' ";

			
				pst = con.prepareStatement(reportQuery);
				
				System.out.println("reportQuery jasper:-"+reportQuery);
				
				rs = pst.executeQuery();

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				parameters.put("image", relativePath + File.separator);
				parameters.put("radio",type);
				parameters.put("fromDate",Utility.convertUtilDateToSQLDate(act.getFromDate()));
				parameters.put("toDate",Utility.convertUtilDateToSQLDate(act.getToDate()));
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "StockReportAtWholesale.jasper");
				

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "StockReportAtWholesale" + "-" + n + ".pdf");
				act.setPdfName("StockReportAtWholesale" + "-" + n + ".pdf");
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
	
	public boolean writeExcel(WholeSaleReportAction act)
	{



		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double boxesTotal = 0;
		double bottleTotal = 0;
		double blTotal = 0;
		
		String excelQuery = null;		
		
		String type=null;
		
		if(act.getRadio().equalsIgnoreCase("CL2")){
			
			type="CL";
			
		}else if(act.getRadio().equalsIgnoreCase("FL2")){
			
			type="FL";
			
		}else{
			type="BEER";
		}

		
			
		excelQuery = 	" select CONCAT(a.shop_id,'-',a.shop_nm) as idname,a.shop_id,a.shop_nm,d.brand_name,e.quantity,c.dispatch_bottle , c.dispatch_box ," +
					" (((c.dispatch_bottle)*e.quantity)/1000) as bl" +
					
					" from fl2d.gatepass_to_districtwholesale_fl2_fl2b_19_20 a,licence.fl2_2b_2d_19_20 b," +
					" fl2d.fl2_stock_trxn_fl2_fl2b c,distillery.brand_registration_19_20 d,distillery.packaging_details_19_20 e" +
					
					" where a.int_fl2_fl2b_id=b.int_app_id AND a.int_fl2_fl2b_id=c.int_fl2_fl2b_id AND" +
					" a.vch_gatepass_no=c.vch_gatepass_no and a.dt_date=c.dt AND" +
					"   d.brand_id=e.brand_id_fk AND c.int_pckg_id=e.package_id" +
					" AND a.dt_date between '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "' " +
					" and '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' AND a.int_fl2_fl2b_id= '"+Integer.parseInt(act.getWholesaleid())+"' ";
 
		System.out.println("excelQuery excelQuery:-"+excelQuery);
		
	
		
		String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;
		

		try {			
			
			
			pstmt = con.prepareStatement(excelQuery);
			
			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");

			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 7000);
			worksheet.setColumnWidth(2, 7000);
			worksheet.setColumnWidth(3, 7000);
			
			worksheet.setColumnWidth(4, 7000);
			worksheet.setColumnWidth(5, 7000);
			worksheet.setColumnWidth(6, 7000);
			/*	worksheet.setColumnWidth(7, 9000);
			worksheet.setColumnWidth(8, 20000);*/
			

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);			
			
			cellhead0.setCellValue(" WHOLESALEWISE SALE REPORT BETWEEN FROM " + Utility.convertUtilDateToSQLDate(act.getFromDate())+ " " + 
			" To " + " "+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "For" + type  );
			
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
			cellhead2.setCellValue("Shop Id and Shop Name");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Brand Name");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Size (ml)");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Bottles");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Boxes");
			cellhead6.setCellStyle(cellStyle);
			
			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("BL");
			cellhead7.setCellStyle(cellStyle);

			/*XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("Date");
			cellhead8.setCellStyle(cellStyle);
			
			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("Recieve From");
			cellhead9.setCellStyle(cellStyle);*/
			

			while (rs.next()) {

				/*Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("dt"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);*/
				

				boxesTotal = boxesTotal + rs.getDouble("dispatch_box");
				blTotal = blTotal + rs.getDouble("bl");
				bottleTotal = bottleTotal + rs.getDouble("dispatch_bottle");
				
				
				k++;

				XSSFRow row1 = worksheet.createRow((int) k);

				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("idname"));

				XSSFCell cellK1 = row1.createCell((int) 2);
				cellK1.setCellValue(rs.getString("brand_name"));

				XSSFCell cellC1 = row1.createCell((int) 3);
				cellC1.setCellValue(rs.getDouble("quantity"));

				XSSFCell cellD1 = row1.createCell((int) 4);
				cellD1.setCellValue(rs.getInt("dispatch_bottle"));

				XSSFCell cellE1 = row1.createCell((int) 5);
				cellE1.setCellValue(rs.getDouble("dispatch_box"));
								
				XSSFCell cellF1 = row1.createCell((int) 6);
				cellF1.setCellValue(rs.getDouble("bl"));
				
				/*		XSSFCell cellG1 = row1.createCell((int) 7);
				cellG1.setCellValue(rs.getString("recv_from"));
				
				XSSFCell cellH1 = row1.createCell((int) 8);
				cellH1.setCellValue(rs.getDouble("strength"));*/
	

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n+ "-StockReportAtWholesale.xls");
			act.setExlname(n + "-StockReportAtWholesale");

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
			cellA4.setCellValue("Total");
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(bottleTotal);
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(boxesTotal);
			cellA6.setCellStyle(cellStyle);
			
			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(blTotal);
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
	//--------------------------------------------------------------------------
	public ArrayList getWholesaleList(WholeSaleReportAction ac) {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		SelectItem item = new SelectItem();
		String query="";
		try {
			
			if (ResourceUtil.getUserNameAllReq().trim().substring(0, 10).equalsIgnoreCase("Excise-DEO")) 
			{
				

				 query =" SELECT CONCAT(vch_firm_name,'-',vch_licence_no)as firm_name,int_app_id," +
						"  vch_firm_name FROM licence.fl2_2b_2d_19_20 " +
						"  where vch_license_type='"+ac.getRadio()+"'  and  " +
						" core_district_id='"+ac.getDistrictId()+"'  order by vch_firm_name ";
				
			} 
				else if(Integer.parseInt(ac.getDistid())==9999)
				{
					
					query =" SELECT CONCAT(vch_firm_name,'-',vch_licence_no)as firm_name,int_app_id," +
							"  vch_firm_name FROM licence.fl2_2b_2d_19_20 " +
							"  where vch_license_type='"+ac.getRadio()+"'   " +
							"  order by vch_firm_name ";	
					
				}
					else
					{
						
						query =" SELECT CONCAT(vch_firm_name,'-',vch_licence_no)as firm_name,int_app_id," +
								"  vch_firm_name FROM licence.fl2_2b_2d_19_20 " +
								"  where vch_license_type='"+ac.getRadio()+"'  and  " +
								" core_district_id='"+Integer.parseInt(ac.getDistid())+"'  order by vch_firm_name ";	
						
			
					}
			 
			
			System.out.println("licence.fl2_2b_2d_19_20:-"+query);
			
			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) 
			{
				item = new SelectItem();
				item.setValue(rs.getString("int_app_id"));
				item.setLabel(rs.getString("firm_name"));

				list.add(item);
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;

	}
	public void getDetails(WholeSaleReportAction ac) 
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select  distinct b.description ,b.districtid  from public.district b "
				+ "where    b.deo='"
				+ ResourceUtil.getUserNameAllReq().trim()
				+ "'";
		
		
		try {
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) 
			{
				ac.setDistrict(rs.getString("description"));

				ac.setDistrictId(rs.getInt("districtid"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	
	public ArrayList getDistList() 
	{

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue("9999");
		list.add(item);
		try {
			String query = " SELECT DistrictID, Description FROM district order by Description ";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("DistrictID"));
				item.setLabel(rs.getString("Description"));

				list.add(item);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
