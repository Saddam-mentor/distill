package com.mentor.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
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

import com.mentor.action.Report_On_Dispatches_Action;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.ResourceUtil;
import com.mentor.utility.Utility;

public class Report_On_DispatchesImpl {

	public void getdata(Report_On_Dispatches_Action  act){


		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "  select int_app_id,vch_firm_name from licence.fl2_2b_2d_20_21 where loginid= '"+ ResourceUtil.getUserNameAllReq()+ "'";

		//System.out.println("login--check"+query);

		try {
			con = ConnectionToDataBase.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {

				act.setInt_id(rs.getInt("int_app_id"));
				act.setFirm_name(rs.getString("vch_firm_name"));

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


	@SuppressWarnings("deprecation")
	public void printReport(Report_On_Dispatches_Action act) {

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


		try {
			con = ConnectionToDataBase.getConnection();



			/* " select DISTINCT a.dt_date,a.vch_gatepass_no, c.dispatch_box, c.dispatch_bottle as no_bottl, "+ 
					  "case when  COALESCE(d.int_fl2d_id,0)> 0 then e.permit "+   
					  "else  (c.dispatch_bottle+c.breakage)*(e.adduty+e.duty) end as duty "+ 
					  "FROM fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a, "+  
					 " licence.fl2_2b_2d_20_21 b,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 c, "+ 
					  "distillery.brand_registration_20_21 d,distillery.packaging_details_20_21 e,"+   
					 " distillery.box_size_details f where a.int_fl2_fl2b_id=b.int_app_id  "+ 
					 " AND a.int_fl2_fl2b_id=c.int_fl2_fl2b_id AND a.vch_gatepass_no=c.vch_gatepass_no "+ 
					 " and a.dt_date=c.dt AND c.int_brand_id=d.brand_id AND d.brand_id=e.brand_id_fk  "+
					 " AND c.int_pckg_id=e.package_id AND a.int_fl2_fl2b_id='"+act.getInt_id()+"' and b.vch_license_type='"+act.getRadio()+"'  "+
					  "AND   a.dt_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' AND " +
							" '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' order by a.vch_gatepass_no";*/


			reportQuery =" select DISTINCT x.dt_date,x.vch_gatepass_no, sum(x.dispatch_box)dispatch_box, sum(x.dispatch_bottle) as no_bottl,sum(x.duty)duty    "+
					" from(   "+
					" select DISTINCT a.dt_date,a.vch_gatepass_no, c.int_pckg_id,c.dispatch_box, c.dispatch_bottle  ,  "+
					" case when  COALESCE(d.int_fl2d_id,0)> 0 then e.permit     "+
					" else  (c.dispatch_bottle+c.breakage)*(e.adduty+e.duty) end as duty    "+
					" FROM fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,   "+
					" licence.fl2_2b_2d_20_21 b,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 c,  "+
					" distillery.brand_registration_20_21 d,distillery.packaging_details_20_21 e    "+
					"   where a.int_fl2_fl2b_id=b.int_app_id   "+
					" AND a.vch_gatepass_no=c.vch_gatepass_no  AND c.int_brand_id=d.brand_id AND d.brand_id=e.brand_id_fk   "+
					" AND c.int_pckg_id=e.package_id AND a.int_fl2_fl2b_id='"+act.getInt_id()+"'  "+
					" AND   a.dt_date BETWEEN '"+Utility.convertUtilDateToSQLDate(act.getFormdate())+"' AND    "+
					"   '"+Utility.convertUtilDateToSQLDate(act.getTodate())+"' order by a.dt_date,a.vch_gatepass_no)x group by  x.dt_date,x.vch_gatepass_no  ";


			//System.out.println("======check== print jasper====="+reportQuery);
			pst = con.prepareStatement(reportQuery);

			rs = pst.executeQuery();



			if (rs.next()) {



				rs = pst.executeQuery();
				Map parameters = new HashMap();
				parameters.put("REPORT_CONNECTION", con);
				parameters.put("firm_name", act.getFirm_name());
				parameters.put("todate", act.getTodate());
				parameters.put("formdate", act.getFormdate());
				/*	parameters.put("boxes_no", act.getNo_ofboxes());
				parameters.put("no_duty", act.getNo_ofduty());
				parameters.put("bottle_no", act.getNo_ofbottles());*/
				parameters.put("SUBREPORT_DIR", relativePath + File.separator);
				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "Report_On_Dispatches.jasper");


				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,
						relativePathpdf + File.separator
						+ "Report_On_Dispatches" + "-" + n + ".pdf" );
				act.setPdfName("Report_On_Dispatches" + "-" + n + ".pdf");
				//act.setPrintFlag(true);
				act.setPrintFlag(true);

			} else {
				//act.setPrintFlag(false);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
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



	//========================Excel================Print====================================================

	public boolean excelMrpBrandDetails(Report_On_Dispatches_Action action) {

		//System.out.println("--------excelMrpBrandDetails_-- execute ");

		Connection con = null;
		con = ConnectionToDataBase.getConnection();
		DecimalFormat diciformatter = new DecimalFormat("#.##");
		String reportQuery = null;
		String LicType = action.getRadio() ;
		double total = 0 ;
		double total_boxes = 0;
		double total_bottles = 0;
		SimpleDateFormat showDate = new SimpleDateFormat("dd-MM-yyyy");
		String fromdate = showDate.format(Utility
				.convertUtilDateToSQLDate(action.getFormdate()));
		String todate = showDate.format(Utility
				.convertUtilDateToSQLDate(action.getTodate()));

		

		reportQuery =  " select DISTINCT x.dt_date,x.vch_gatepass_no, sum(x.dispatch_box)dispatch_box, sum(x.dispatch_bottle) as no_bottl,sum(x.duty)duty "+
				" from(   "+
				" select DISTINCT a.dt_date,a.vch_gatepass_no, c.int_pckg_id,c.dispatch_box, c.dispatch_bottle  ,   "+
				" case when  COALESCE(d.int_fl2d_id,0)> 0 then e.permit    "+
				" else  (c.dispatch_bottle+c.breakage)*(e.adduty+e.duty) end as duty   "+
				" FROM fl2d.gatepass_to_districtwholesale_fl2_fl2b_20_21 a,   "+
				" licence.fl2_2b_2d_20_21 b,fl2d.fl2_stock_trxn_fl2_fl2b_20_21 c,   "+
				" distillery.brand_registration_20_21 d,distillery.packaging_details_20_21 e  "+
				"   where a.int_fl2_fl2b_id=b.int_app_id  "+
				" AND a.vch_gatepass_no=c.vch_gatepass_no  AND c.int_brand_id=d.brand_id AND d.brand_id=e.brand_id_fk  "+
				" AND c.int_pckg_id=e.package_id AND a.int_fl2_fl2b_id='"+action.getInt_id()+"'    "+
				" AND   a.dt_date BETWEEN '"+Utility.convertUtilDateToSQLDate(action.getFormdate())+"' AND  "+
				"   '"+Utility.convertUtilDateToSQLDate(action.getTodate())+"' order by a.dt_date,a.vch_gatepass_no)x group by  x.dt_date,x.vch_gatepass_no  ";


		//System.out.println("----------  Excel   -----" + reportQuery);


		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;




		try {

			pstmt = con.prepareStatement(reportQuery);

			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Report on Dispatches");
			// CellStyle unlockedCellStyle = workbook.createCellStyle();
			// unlockedCellStyle.setLocked(false);
			// worksheet.protectSheet("UP-EX-MIS");
			worksheet.setColumnWidth(0, 2000);
			worksheet.setColumnWidth(1, 4000);
			worksheet.setColumnWidth(2, 7000);
			worksheet.setColumnWidth(3, 3000);				
			worksheet.setColumnWidth(4, 3000);
			worksheet.setColumnWidth(5, 4000);
			

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 1);
			cellhead0.setCellValue("Report On Dispatches  "+ LicType +"   From  "+ fromdate + " To "+ todate);			
			rowhead0.setHeight((short) 700);


			// cellhead0.setCellStyle(unlockedCellStyle);
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
			cellhead1.setCellValue("Sr.No.");
			cellhead1.setCellStyle(cellStyle);

			XSSFCell cellhead2 = rowhead.createCell((int) 1);
			cellhead2.setCellValue("Date");
			cellhead2.setCellStyle(cellStyle);

			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("Gatepass No.");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("Dispatch Box");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("No. Of Bottle");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Duty");
			cellhead6.setCellStyle(cellStyle);

			


			int i = 0;
			while (rs.next()) {
				
				total = total + rs.getDouble("duty");
				total_boxes = total_boxes + rs.getDouble("dispatch_box");
				total_bottles = total_bottles + rs.getDouble("no_bottl");
				
				k++;
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k - 1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("dt_date"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("vch_gatepass_no") );
				// cellC1.setCellStyle(unlockcellStyle);

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("dispatch_box"));

				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getString("no_bottl"));

				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getString("duty"));

				

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1; 
			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//MIS//Excel//" 
					+ n + "_report_on_dispatches.xlsx");

			action.setExlname( n + "_report_on_dispatches.xlsx" );
			XSSFRow row1 = worksheet.createRow((int) k + 1);
			// XSSFCell cellA1 = row1.createCell((int) 0);
			// cellA1.setCellValue("End");
			// cellA1.setCellStyle(cellStyle);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);

			XSSFCell cellA2 = row1.createCell((int) 1);
			cellA2.setCellValue(" ");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 2);
			cellA3.setCellValue("Total  : ");
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 3);
			cellA4.setCellValue(total_boxes);
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(total_bottles);
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(total);
			cellA6.setCellStyle(cellStyle);

			

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			action.setExcelFlag(true);
			con.close();
			
			


		} catch (Exception e) {

		//System.out.println("xls2" + e.getMessage());
			e.printStackTrace();

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), e.getMessage()));
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"No Data Found!!", "No Data Found!!"));
			
			action.setExcelFlag(false);
			
			

		} finally {

			try {
				con.close();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, e
								.getMessage(), e.getMessage()));
				//System.out.println("  final block ");

				e.printStackTrace();
			}
		}


		return flag;

	}


}
