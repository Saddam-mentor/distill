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

import com.mentor.action.DispatchReportDIST_BRE_OLDSTOCK_Action;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class DispatchReportDIST_BRE_OLDSTOCK_Impl {


	// =======================print report=================================

	public void printReport(DispatchReportDIST_BRE_OLDSTOCK_Action act) {

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;

		String relativePath = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "jasper";
		String relativePathpdf = mypath + File.separator + "ExciseUp"+ File.separator + "MIS" + File.separator + "pdf";
		JasperReport jasperReport = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		String reportQuery = null;
		String reportQuery1 = null;
		String reportQueryDis = null;
		String reportQueryBrew = null;

		try {
			con = ConnectionToDataBase.getConnection();

			
			if (act.getRadio().equalsIgnoreCase("CD"))
			{
				reportQuery = 	" SELECT x.dt, SUM(x.fl_dispatch_bottle) as fl_dispatch_bottle,  " +
								" SUM(x.fl_sale) as fl_sale, SUM(x.fl_duty) as fl_duty,  " +
								" SUM(x.beer_dispatch_bottle) as beer_dispatch_bottle, " +
								" SUM(x.beer_sale) as  beer_sale, SUM(x.beer_duty) as beer_duty " +
								" FROM  " +
								" (SELECT b.dt as dt, SUM(b.dispatch_bottle) as fl_dispatch_bottle, 0 as fl_duty, " +
								" 0 as beer_dispatch_bottle, 0 as beer_duty, SUM(k.quantity) as quantity, " +
								" SUM(round( CAST(float8(((b.dispatch_bottle)*k.quantity)/1000) as numeric), 3)) as fl_sale,  " +
								" 0 as beer_sale FROM distillery.fl2_stock_trxn_imfl_old_stock b, distillery.packaging_details k  " +
								" WHERE b.dt BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
								" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
								" AND b.int_pckg_id=k.package_id GROUP BY b.dt  " +
								" UNION  " +
								" SELECT e.dt_date as dt, 0 as fl_dispatch_bottle, SUM(d.duty) as fl_duty, " +
								" 0 as beer_dispatch_bottle, 0 as beer_duty, 0 as quantity, 0 as fl_sale, 0 as beer_sale  " +
								" FROM distillery.fl1_stock_trxn_imfl_old_stock d, " +
								" distillery.gatepass_to_manufacturewholesale_imfl_old_stock e " +
								" WHERE  d.int_dissleri_id=e.int_dist_id AND d.vch_gatepass_no=e.vch_gatepass_no  " +
								" AND  e.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
								" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
								" GROUP BY e.dt_date  " +
								" UNION  " +
								" SELECT h.dt as dt, 0 as fl_dispatch_bottle, 0 as fl_duty, " +
								" SUM(h.dispatch_bottle) as  beer_dispatch_bottle, 0 as beer_duty, " +
								" SUM(l.quantity) as quantity, 0 as fl_sale,  " +
								" SUM(round(CAST(float8(((h.dispatch_bottle)*l.quantity)/1000) as numeric), 3)) as beer_sale  " +
								" FROM bwfl.fl2_stock_trxn_old_stock_17_18 h, distillery.packaging_details l  " +
								" WHERE h.dt BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
								" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
								" AND h.int_pckg_id=l.package_id GROUP BY h.dt  " +
								" UNION  " +
								" SELECT g.dt_date as dt, 0 as fl_dispatch_bottle, 0 as fl_duty, 0 as beer_dispatch_bottle, " +
								" SUM(f.duty) as beer_duty, 0 as quantity, 0 as fl_sale, 0 as beer_sale " +
								" FROM  bwfl.fl1_stock_trxn_old_stock_17_18 f, bwfl.gatepass_to_districtwholesale_2017_18_old_stock g " +
								" WHERE  f.int_brewery_id=g.bre_id AND f.vch_gatepass_no=g.vch_gatepass_no " +
								" AND g.dt_date BETWEEN  '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
								" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " + 
								" GROUP BY g.dt_date)x GROUP BY x.dt ORDER BY x.dt ";
			}
			
			
			else if (act.getRadio().equalsIgnoreCase("DBW"))
			{
				reportQuery = 	" SELECT x.dt, SUM(x.fl_dispatch_bottle) as fl_dispatch_bottle,  " +
								" SUM(x.fl_sale) as fl_sale, SUM(x.fl_duty) as fl_duty,  " +
								" SUM(x.beer_dispatch_bottle) as beer_dispatch_bottle, SUM(x.beer_sale) as beer_sale, " +
								" SUM(x.beer_duty) as beer_duty, x.dist_name  " +
								" FROM " +
								" (SELECT b.dt as dt, SUM(b.dispatch_bottle) as fl_dispatch_bottle, 0 as fl_duty,  " +
								" 0 as beer_dispatch_bottle, 0 as beer_duty, SUM(k.quantity) as quantity, " +
								" SUM(round(CAST(float8(((b.dispatch_bottle)*k.quantity)/1000) as numeric), 3)) as fl_sale,  " +
								" 0 as beer_sale, m.vch_undertaking_name||'(DISTILLERY)' as dist_name " +
								" FROM distillery.fl2_stock_trxn_imfl_old_stock b, distillery.packaging_details k,  " +
								" public.dis_mst_pd1_pd2_lic m WHERE " +
								" b.dt BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
								" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " + 
								" AND b.int_pckg_id=k.package_id AND  b.int_dissleri_id=m.int_app_id_f  " +
								" GROUP BY b.dt, m.vch_undertaking_name  " +
								" UNION  " +
								" SELECT e.dt_date as dt, 0 as fl_dispatch_bottle, SUM(d.duty) as fl_duty, " +
								" 0 as beer_dispatch_bottle, 0 as beer_duty,   0 as quantity, 0 as fl_sale, 0 as beer_sale,  " +
								" m.vch_undertaking_name||'(DISTILLERY)' as dist_name " +
								" FROM distillery.fl1_stock_trxn_imfl_old_stock d, distillery.gatepass_to_manufacturewholesale_imfl_old_stock e, " +
								" public.dis_mst_pd1_pd2_lic m WHERE d.int_dissleri_id=e.int_dist_id  " +
								" AND d.vch_gatepass_no=e.vch_gatepass_no " +
								" AND e.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
								" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " + 
								" AND d.int_dissleri_id=m.int_app_id_f " +
								" GROUP BY e.dt_date, m.vch_undertaking_name  " +
								" UNION  " +
								" SELECT h.dt as dt, 0 as fl_dispatch_bottle, 0 as fl_duty,  " +
								" SUM(h.dispatch_bottle) as beer_dispatch_bottle, 0 as beer_duty, SUM(l.quantity) as quantity, 0 as fl_sale, " +
								" SUM(round(CAST(float8(((h.dispatch_bottle)*l.quantity)/1000) as numeric), 3)) as beer_sale, " +
								" m.brewery_nm||'(BREWERY)' as dist_name  " +
								" FROM bwfl.fl2_stock_trxn_old_stock_17_18 h, distillery.packaging_details l, " +
								" public.bre_mst_b1_lic m WHERE  " +
								" h.dt BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
								" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " + 
								" AND h.int_pckg_id=l.package_id AND h.brewery_id=m.vch_app_id_f  " +
								" GROUP BY h.dt, m.brewery_nm  " +
								" UNION " +
								" SELECT g.dt_date as dt, 0 as fl_dispatch_bottle, 0 as fl_duty, 0 as beer_dispatch_bottle, " +
								" SUM(f.duty) as beer_duty,  0 as quantity,  0 as fl_sale, 0 as beer_sale, " +
								" m.brewery_nm||'(BREWERY)' as dist_name " +
								" FROM  bwfl.fl1_stock_trxn_old_stock_17_18 f, bwfl.gatepass_to_districtwholesale_2017_18_old_stock g,  " +
								" public.bre_mst_b1_lic m  WHERE f.int_brewery_id=g.bre_id  " +
								" AND f.vch_gatepass_no=g.vch_gatepass_no AND  " +
								" g.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
								" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " + 
								" AND f.int_brewery_id=m.vch_app_id_f GROUP BY g.dt_date, m.brewery_nm )x " +
								" GROUP BY x.dt, x.dist_name " +
								" ORDER BY x.dist_name, x.dt ";
			}

			else if (act.getRadio().equalsIgnoreCase("SD"))
			{
				reportQuery = 	" SELECT x.dist_name,x.dt, SUM(x.fl_dispatch_bottle) as fl_dispatch_bottle, " +
								" SUM(x.fl_sale) as fl_sale, SUM(x.fl_duty) as fl_duty, " +
								" SUM(x.beer_dispatch_bottle) as beer_dispatch_bottle,  SUM(x.beer_sale) as beer_sale, " +
								" SUM(x.beer_duty) as beer_duty " +
								" FROM  " +
								" (SELECT b.dt as dt, SUM(b.dispatch_bottle) as fl_dispatch_bottle, 0 as fl_duty, " +
								" 0 as beer_dispatch_bottle, 0 as beer_duty, SUM(k.quantity) as quantity, " +
								" SUM(round(CAST(float8(((b.dispatch_bottle)*k.quantity)/1000) as numeric), 3)) as fl_sale,  " +
								" 0 as beer_sale, m.vch_undertaking_name||'(DISTILLERY)' as dist_name " +
								" FROM distillery.fl2_stock_trxn_imfl_old_stock b, " +
								" distillery.packaging_details k, public.dis_mst_pd1_pd2_lic m  " +
								" WHERE b.dt BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
								" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
								" AND b.int_pckg_id=k.package_id AND b.int_dissleri_id=m.int_app_id_f  " +
								" AND m.int_app_id_f="+act.getDistilleryId()+"  " +
								" GROUP BY b.dt, m.vch_undertaking_name  " +
								" UNION  " +
								" SELECT e.dt_date as dt,0 as fl_dispatch_bottle, SUM(d.duty) as fl_duty, " +
								" 0 as beer_dispatch_bottle, 0 as beer_duty, 0 as quantity, 0 as fl_sale, 0 as beer_sale, " +
								" m.vch_undertaking_name||'(DISTILLERY)' as dist_name " +
								" FROM distillery.fl1_stock_trxn_imfl_old_stock d,  " +
								" distillery.gatepass_to_manufacturewholesale_imfl_old_stock e, public.dis_mst_pd1_pd2_lic m " +
								" WHERE d.int_dissleri_id=e.int_dist_id  AND d.vch_gatepass_no=e.vch_gatepass_no  " +
								" AND e.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
								" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
								" AND d.int_dissleri_id=m.int_app_id_f " +
								" AND m.int_app_id_f="+act.getDistilleryId()+" " +
								" GROUP BY e.dt_date, m.vch_undertaking_name )x  " +
								" GROUP BY x.dt, x.dist_name  " +
								" ORDER BY x.dist_name, x.dt ";
			}
			
			else if (act.getRadio().equalsIgnoreCase("SB"))
			{
				reportQuery = 	" SELECT  x.dist_name, x.dt, SUM(x.fl_dispatch_bottle) as fl_dispatch_bottle, " +
								" SUM(x.fl_sale) as fl_sale, SUM(x.fl_duty) as fl_duty, " +
								" SUM(x.beer_dispatch_bottle) as beer_dispatch_bottle, SUM(x.beer_sale) as beer_sale,  " +
								" SUM(x.beer_duty) as beer_duty " +
								" FROM  " +
								" (SELECT h.dt as dt, 0 as fl_dispatch_bottle, 0 as fl_duty,  " +
								" SUM(h.dispatch_bottle) as beer_dispatch_bottle, 0 as beer_duty, " +
								" SUM(l.quantity) as quantity,0 as fl_sale, " +
								" SUM(round(CAST(float8(((h.dispatch_bottle)*l.quantity)/1000) as numeric), 3)) as beer_sale,  " +
								" m.brewery_nm||'(BREWERY)' as dist_name " +
								" FROM bwfl.fl2_stock_trxn_old_stock_17_18 h, distillery.packaging_details l, public.bre_mst_b1_lic m  " +
								" WHERE h.dt BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
								" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
								" AND h.int_pckg_id=l.package_id AND h.brewery_id=m.vch_app_id_f  " +
								" AND m.vch_app_id_f="+act.getBreweryId()+" " +
								" GROUP BY h.dt, m.brewery_nm "+
								" UNION "+ 
								" SELECT g.dt_date as dt, 0 as fl_dispatch_bottle, 0 as fl_duty, 0 as beer_dispatch_bottle, " +
								" SUM(f.duty) as beer_duty, 0 as spirit_sale, 0 as fl_sale, 0 as beer_sale, " +
								" m.brewery_nm||'(BREWERY)' as dist_name  " +
								" FROM bwfl.fl1_stock_trxn_old_stock_17_18 f, " +
								" bwfl.gatepass_to_districtwholesale_2017_18_old_stock g, public.bre_mst_b1_lic m  " +
								" WHERE f.int_brewery_id=g.bre_id AND f.vch_gatepass_no=g.vch_gatepass_no  " +
								" AND g.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
								" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
								" AND f.int_brewery_id=m.vch_app_id_f  AND m.vch_app_id_f="+act.getBreweryId()+"  " +
								" GROUP BY g.dt_date, m.brewery_nm )x  " +
								" GROUP BY x.dt, x.dist_name ORDER BY x.dist_name, x.dt ";
			}
			
			else{
				reportQuery = "";
			}

			
			

				pst = con.prepareStatement(reportQuery);

				//System.out.println("reportQuery---------" + reportQuery);

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

				
				if (act.getRadio().equalsIgnoreCase("CD")) {
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "DispatchStockReport_DIST_BRE_OLDSTOCK.jasper");
				} else if (act.getRadio().equalsIgnoreCase("DBW") || act.getRadio().equalsIgnoreCase("SD") || act.getRadio().equalsIgnoreCase("SB")) {
					jasperReport = (JasperReport) JRLoader.loadObject(relativePath + File.separator+ "DispatchStockReportDistWiseOLDSTOCK.jasper");
				} 
				
							

				JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;
				JasperExportManager.exportReportToPdfFile(print,relativePathpdf + File.separator+ "DispatchStockReport_DIST_BRE_OLDSTOCK" + "-" + n + ".pdf");
				act.setPdfname("DispatchStockReport_DIST_BRE_OLDSTOCK" + "-" + n + ".pdf");
				act.setPrintFlag(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"No Data Found!!", "No Data Found!!"));
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

	public boolean write(DispatchReportDIST_BRE_OLDSTOCK_Action act) {

		Connection con = null;
		con = ConnectionToDataBase.getConnection();
		double spirit_sale = 0;
		double spirit_duty = 0;
		double cl_sale = 0;
		double cl_duty = 0;
		double fl_sale = 0;
		double fl_duty = 0;
		double beer_sale = 0;
		double beer_duty = 0;
		double total_sale = 0;
		double total_duty = 0;
		String sql = "";

		
		sql = 	" SELECT x.dt, SUM(x.fl_dispatch_bottle) as fl_dispatch_bottle,  " +
				" SUM(x.fl_sale) as fl_sale, SUM(x.fl_duty) as fl_duty,  " +
				" SUM(x.beer_dispatch_bottle) as beer_dispatch_bottle, " +
				" SUM(x.beer_sale) as  beer_sale, SUM(x.beer_duty) as beer_duty " +
				" FROM  " +
				" (SELECT b.dt as dt, SUM(b.dispatch_bottle) as fl_dispatch_bottle, 0 as fl_duty, " +
				" 0 as beer_dispatch_bottle, 0 as beer_duty, SUM(k.quantity) as quantity, " +
				" SUM(round( CAST(float8(((b.dispatch_bottle)*k.quantity)/1000) as numeric), 3)) as fl_sale,  " +
				" 0 as beer_sale FROM distillery.fl2_stock_trxn_imfl_old_stock b, distillery.packaging_details k  " +
				" WHERE b.dt BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
				" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
				" AND b.int_pckg_id=k.package_id GROUP BY b.dt  " +
				" UNION  " +
				" SELECT e.dt_date as dt, 0 as fl_dispatch_bottle, SUM(d.duty) as fl_duty, " +
				" 0 as beer_dispatch_bottle, 0 as beer_duty, 0 as quantity, 0 as fl_sale, 0 as beer_sale  " +
				" FROM distillery.fl1_stock_trxn_imfl_old_stock d, " +
				" distillery.gatepass_to_manufacturewholesale_imfl_old_stock e " +
				" WHERE  d.int_dissleri_id=e.int_dist_id AND d.vch_gatepass_no=e.vch_gatepass_no  " +
				" AND  e.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
				" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
				" GROUP BY e.dt_date  " +
				" UNION  " +
				" SELECT h.dt as dt, 0 as fl_dispatch_bottle, 0 as fl_duty, " +
				" SUM(h.dispatch_bottle) as  beer_dispatch_bottle, 0 as beer_duty, " +
				" SUM(l.quantity) as quantity, 0 as fl_sale,  " +
				" SUM(round(CAST(float8(((h.dispatch_bottle)*l.quantity)/1000) as numeric), 3)) as beer_sale  " +
				" FROM bwfl.fl2_stock_trxn_old_stock_17_18 h, distillery.packaging_details l  " +
				" WHERE h.dt BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
				" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
				" AND h.int_pckg_id=l.package_id GROUP BY h.dt  " +
				" UNION  " +
				" SELECT g.dt_date as dt, 0 as fl_dispatch_bottle, 0 as fl_duty, 0 as beer_dispatch_bottle, " +
				" SUM(f.duty) as beer_duty, 0 as quantity, 0 as fl_sale, 0 as beer_sale " +
				" FROM  bwfl.fl1_stock_trxn_old_stock_17_18 f, bwfl.gatepass_to_districtwholesale_2017_18_old_stock g " +
				" WHERE  f.int_brewery_id=g.bre_id AND f.vch_gatepass_no=g.vch_gatepass_no " +
				" AND g.dt_date BETWEEN  '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
				" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " + 
				" GROUP BY g.dt_date)x GROUP BY x.dt ORDER BY x.dt ";

		
		
		String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;
		try {
			pstmt = con.prepareStatement(sql);
			// System.out.println("==SQL=11111=" + sql);
			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");
			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 8000);
			worksheet.setColumnWidth(2, 9999);
			worksheet.setColumnWidth(3, 9999);
			worksheet.setColumnWidth(4, 9999);
			worksheet.setColumnWidth(5, 9999);
			worksheet.setColumnWidth(6, 10000);
			worksheet.setColumnWidth(7, 9000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" OldStock Dispatches Of Distillery/Brewery From "+ " " + Utility.convertUtilDateToSQLDate(act.getFromDate())+ " " +  
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
			cellhead2.setCellValue("Date");
			cellhead2.setCellStyle(cellStyle);
			
			XSSFCell cellhead3 = rowhead.createCell((int) 2);
			cellhead3.setCellValue("FL Sale(BL)");
			cellhead3.setCellStyle(cellStyle);
			
			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("FL Duty");
			cellhead4.setCellStyle(cellStyle);
			
			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("Beer Sale(BL)");
			cellhead5.setCellStyle(cellStyle);
			
			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Beer Duty");
			cellhead6.setCellStyle(cellStyle);
			
			/*XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Spirit Sale(BL)");
			cellhead7.setCellStyle(cellStyle);
			
			XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("Spirit Duty");
			cellhead8.setCellStyle(cellStyle);
			
			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("CL Sale(BL)");
			cellhead9.setCellStyle(cellStyle);
			
			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("CL Duty");
			cellhead10.setCellStyle(cellStyle);*/
			
			int i = 0;
			while (rs.next()) {
				Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("dt"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);

				/*spirit_sale = spirit_sale + rs.getDouble("spirit_sale");
				spirit_duty = spirit_duty + rs.getDouble("spirit_duty");

				cl_duty = cl_duty + rs.getDouble("cl_duty");
				cl_sale = cl_sale + rs.getDouble("cl_sale");*/

				fl_sale = fl_sale + rs.getDouble("fl_sale");
				fl_duty = fl_duty + rs.getDouble("fl_duty");

				beer_sale = beer_sale + rs.getDouble("beer_sale");
				beer_duty = beer_duty + rs.getDouble("beer_duty");
				
				total_sale = total_sale + (rs.getDouble("fl_sale")+rs.getDouble("beer_sale"));
				total_duty = total_duty + (rs.getDouble("fl_duty")+rs.getDouble("beer_duty"));

				k++;
				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k - 1);
				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(date);
				
				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getDouble("fl_sale"));
				
				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getDouble("fl_duty"));
				
				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getDouble("beer_sale"));
				
				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getDouble("beer_duty"));
				
				/*XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getDouble("spirit_sale"));
				
				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getDouble("spirit_duty"));
				
				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getString("cl_sale"));
				
				XSSFCell cellJ1 = row1.createCell((int) 9);
				cellJ1.setCellValue(rs.getString("cl_duty"));*/

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n+ "_DispatchStockReport_DIST_BRE_OLDSTOCK.xls");
			act.setExlname(n + "_DispatchStockReport_DIST_BRE_OLDSTOCK");

			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);

			XSSFCell cellA2 = row1.createCell((int) 1);
			cellA2.setCellValue("Total");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 2);
			cellA3.setCellValue(fl_sale);
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 3);
			cellA4.setCellValue(fl_duty);
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(beer_sale);
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(beer_duty);
			cellA6.setCellStyle(cellStyle);

			/*XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(spirit_sale);
			cellA7.setCellStyle(cellStyle);

			XSSFCell cellA8 = row1.createCell((int) 7);
			cellA8.setCellValue(spirit_duty);
			cellA8.setCellStyle(cellStyle);

			XSSFCell cellA9 = row1.createCell((int) 8);
			cellA9.setCellValue(cl_sale);
			cellA9.setCellStyle(cellStyle);

			XSSFCell cellA10 = row1.createCell((int) 9);
			cellA10.setCellValue(cl_duty);
			cellA10.setCellStyle(cellStyle);*/
			
			
			XSSFRow row2 = worksheet.createRow((int) k + 2);
			

			XSSFCell cellA11 = row2.createCell((int) 0);
			cellA11.setCellValue(" ");
			cellA11.setCellStyle(cellStyle);

			XSSFCell cellA12 = row2.createCell((int) 1);
			cellA12.setCellValue("Final Total Sale:");
			cellA12.setCellStyle(cellStyle);

			XSSFCell cellA113 = row2.createCell((int) 2);
			cellA113.setCellValue(total_sale);
			cellA113.setCellStyle(cellStyle);

			XSSFCell cellA14 = row2.createCell((int) 3);
			cellA14.setCellValue(" ");
			cellA14.setCellStyle(cellStyle);

			XSSFCell cellA15 = row2.createCell((int) 4);
			cellA15.setCellValue("Final Total Duty:");
			cellA15.setCellStyle(cellStyle);

			XSSFCell cellA16 = row2.createCell((int) 5);
			cellA16.setCellValue(total_duty);
			cellA16.setCellStyle(cellStyle);
			
			
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
		return flag;
	}

	public boolean write_ditillery_wise(DispatchReportDIST_BRE_OLDSTOCK_Action act) {

		Connection con = null;
		con = ConnectionToDataBase.getConnection();

		double spirit_sale = 0;
		double spirit_duty = 0;
		double cl_sale = 0;
		double cl_duty = 0;
		double fl_sale = 0;
		double fl_duty = 0;
		double beer_sale = 0;
		double beer_duty = 0;
		String reportQueryDis = null;
		String reportQueryBrew = null;
		String sql = "";

		sql = 	" SELECT x.dt, SUM(x.fl_dispatch_bottle) as fl_dispatch_bottle,  " +
				" SUM(x.fl_sale) as fl_sale, SUM(x.fl_duty) as fl_duty,  " +
				" SUM(x.beer_dispatch_bottle) as beer_dispatch_bottle, SUM(x.beer_sale) as beer_sale, " +
				" SUM(x.beer_duty) as beer_duty, x.dist_name  " +
				" FROM " +
				" (SELECT b.dt as dt, SUM(b.dispatch_bottle) as fl_dispatch_bottle, 0 as fl_duty,  " +
				" 0 as beer_dispatch_bottle, 0 as beer_duty, SUM(k.quantity) as quantity, " +
				" SUM(round(CAST(float8(((b.dispatch_bottle)*k.quantity)/1000) as numeric), 3)) as fl_sale,  " +
				" 0 as beer_sale, m.vch_undertaking_name||'(DISTILLERY)' as dist_name " +
				" FROM distillery.fl2_stock_trxn_imfl_old_stock b, distillery.packaging_details k,  " +
				" public.dis_mst_pd1_pd2_lic m WHERE " +
				" b.dt BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
				" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " + 
				" AND b.int_pckg_id=k.package_id AND  b.int_dissleri_id=m.int_app_id_f  " +
				" GROUP BY b.dt, m.vch_undertaking_name  " +
				" UNION  " +
				" SELECT e.dt_date as dt, 0 as fl_dispatch_bottle, SUM(d.duty) as fl_duty, " +
				" 0 as beer_dispatch_bottle, 0 as beer_duty,   0 as quantity, 0 as fl_sale, 0 as beer_sale,  " +
				" m.vch_undertaking_name||'(DISTILLERY)' as dist_name " +
				" FROM distillery.fl1_stock_trxn_imfl_old_stock d, distillery.gatepass_to_manufacturewholesale_imfl_old_stock e, " +
				" public.dis_mst_pd1_pd2_lic m WHERE d.int_dissleri_id=e.int_dist_id  " +
				" AND d.vch_gatepass_no=e.vch_gatepass_no " +
				" AND e.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
				" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " + 
				" AND d.int_dissleri_id=m.int_app_id_f " +
				" GROUP BY e.dt_date, m.vch_undertaking_name  " +
				" UNION  " +
				" SELECT h.dt as dt, 0 as fl_dispatch_bottle, 0 as fl_duty,  " +
				" SUM(h.dispatch_bottle) as beer_dispatch_bottle, 0 as beer_duty, SUM(l.quantity) as quantity, 0 as fl_sale, " +
				" SUM(round(CAST(float8(((h.dispatch_bottle)*l.quantity)/1000) as numeric), 3)) as beer_sale, " +
				" m.brewery_nm||'(BREWERY)' as dist_name  " +
				" FROM bwfl.fl2_stock_trxn_old_stock_17_18 h, distillery.packaging_details l, " +
				" public.bre_mst_b1_lic m WHERE  " +
				" h.dt BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
				" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " + 
				" AND h.int_pckg_id=l.package_id AND h.brewery_id=m.vch_app_id_f  " +
				" GROUP BY h.dt, m.brewery_nm  " +
				" UNION " +
				" SELECT g.dt_date as dt, 0 as fl_dispatch_bottle, 0 as fl_duty, 0 as beer_dispatch_bottle, " +
				" SUM(f.duty) as beer_duty,  0 as quantity,  0 as fl_sale, 0 as beer_sale, " +
				" m.brewery_nm||'(BREWERY)' as dist_name " +
				" FROM  bwfl.fl1_stock_trxn_old_stock_17_18 f, bwfl.gatepass_to_districtwholesale_2017_18_old_stock g,  " +
				" public.bre_mst_b1_lic m  WHERE f.int_brewery_id=g.bre_id  " +
				" AND f.vch_gatepass_no=g.vch_gatepass_no AND  " +
				" g.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
				" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " + 
				" AND f.int_brewery_id=m.vch_app_id_f GROUP BY g.dt_date, m.brewery_nm )x " +
				" GROUP BY x.dt, x.dist_name " +
				" ORDER BY x.dist_name, x.dt ";

		
		
		reportQueryDis = 	" SELECT x.dist_name,x.dt, SUM(x.fl_dispatch_bottle) as fl_dispatch_bottle, " +
							" SUM(x.fl_sale) as fl_sale, SUM(x.fl_duty) as fl_duty, " +
							" SUM(x.beer_dispatch_bottle) as beer_dispatch_bottle,  SUM(x.beer_sale) as beer_sale, " +
							" SUM(x.beer_duty) as beer_duty " +
							" FROM  " +
							" (SELECT b.dt as dt, SUM(b.dispatch_bottle) as fl_dispatch_bottle, 0 as fl_duty, " +
							" 0 as beer_dispatch_bottle, 0 as beer_duty, SUM(k.quantity) as quantity, " +
							" SUM(round(CAST(float8(((b.dispatch_bottle)*k.quantity)/1000) as numeric), 3)) as fl_sale,  " +
							" 0 as beer_sale, m.vch_undertaking_name||'(DISTILLERY)' as dist_name " +
							" FROM distillery.fl2_stock_trxn_imfl_old_stock b, " +
							" distillery.packaging_details k, public.dis_mst_pd1_pd2_lic m  " +
							" WHERE b.dt BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND b.int_pckg_id=k.package_id AND b.int_dissleri_id=m.int_app_id_f  " +
							" AND m.int_app_id_f="+act.getDistilleryId()+"  " +
							" GROUP BY b.dt, m.vch_undertaking_name  " +
							" UNION  " +
							" SELECT e.dt_date as dt,0 as fl_dispatch_bottle, SUM(d.duty) as fl_duty, " +
							" 0 as beer_dispatch_bottle, 0 as beer_duty, 0 as quantity, 0 as fl_sale, 0 as beer_sale, " +
							" m.vch_undertaking_name||'(DISTILLERY)' as dist_name " +
							" FROM distillery.fl1_stock_trxn_imfl_old_stock d,  " +
							" distillery.gatepass_to_manufacturewholesale_imfl_old_stock e, public.dis_mst_pd1_pd2_lic m " +
							" WHERE d.int_dissleri_id=e.int_dist_id  AND d.vch_gatepass_no=e.vch_gatepass_no  " +
							" AND e.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND d.int_dissleri_id=m.int_app_id_f " +
							" AND m.int_app_id_f="+act.getDistilleryId()+" " +
							" GROUP BY e.dt_date, m.vch_undertaking_name )x  " +
							" GROUP BY x.dt, x.dist_name  " +
							" ORDER BY x.dist_name, x.dt ";

				
		
		reportQueryBrew = 	" SELECT  x.dist_name, x.dt, SUM(x.fl_dispatch_bottle) as fl_dispatch_bottle, " +
							" SUM(x.fl_sale) as fl_sale, SUM(x.fl_duty) as fl_duty, " +
							" SUM(x.beer_dispatch_bottle) as beer_dispatch_bottle, SUM(x.beer_sale) as beer_sale,  " +
							" SUM(x.beer_duty) as beer_duty " +
							" FROM  " +
							" (SELECT h.dt as dt, 0 as fl_dispatch_bottle, 0 as fl_duty,  " +
							" SUM(h.dispatch_bottle) as beer_dispatch_bottle, 0 as beer_duty, " +
							" SUM(l.quantity) as quantity,0 as fl_sale, " +
							" SUM(round(CAST(float8(((h.dispatch_bottle)*l.quantity)/1000) as numeric), 3)) as beer_sale,  " +
							" m.brewery_nm||'(BREWERY)' as dist_name " +
							" FROM bwfl.fl2_stock_trxn_old_stock_17_18 h, distillery.packaging_details l, public.bre_mst_b1_lic m  " +
							" WHERE h.dt BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND h.int_pckg_id=l.package_id AND h.brewery_id=m.vch_app_id_f  " +
							" AND m.vch_app_id_f="+act.getBreweryId()+" " +
							" GROUP BY h.dt, m.brewery_nm "+
							" UNION "+ 
							" SELECT g.dt_date as dt, 0 as fl_dispatch_bottle, 0 as fl_duty, 0 as beer_dispatch_bottle, " +
							" SUM(f.duty) as beer_duty, 0 as spirit_sale, 0 as fl_sale, 0 as beer_sale, " +
							" m.brewery_nm||'(BREWERY)' as dist_name  " +
							" FROM bwfl.fl1_stock_trxn_old_stock_17_18 f, " +
							" bwfl.gatepass_to_districtwholesale_2017_18_old_stock g, public.bre_mst_b1_lic m  " +
							" WHERE f.int_brewery_id=g.bre_id AND f.vch_gatepass_no=g.vch_gatepass_no  " +
							" AND g.dt_date BETWEEN '"+ Utility.convertUtilDateToSQLDate(act.getFromDate())+ "'  " +
							" AND '"+ Utility.convertUtilDateToSQLDate(act.getToDate())+ "' " +
							" AND f.int_brewery_id=m.vch_app_id_f  AND m.vch_app_id_f="+act.getBreweryId()+"  " +
							" GROUP BY g.dt_date, m.brewery_nm )x  " +
							" GROUP BY x.dt, x.dist_name ORDER BY x.dist_name, x.dt ";

		
		
		String relativePath = Constants.JBOSS_SERVER_PATH+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		String date = null;

		try {

			if (act.getRadio().equalsIgnoreCase("DBW")) {
				pstmt = con.prepareStatement(sql);
				
			} else if (act.getRadio().equalsIgnoreCase("SD")) {
				pstmt = con.prepareStatement(reportQueryDis);
				
			} else if (act.getRadio().equalsIgnoreCase("SB")) {
				pstmt = con.prepareStatement(reportQueryBrew);				
			}

			rs = pstmt.executeQuery();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("Barcode Report");

			worksheet.setColumnWidth(0, 3000);
			worksheet.setColumnWidth(1, 4000);

			worksheet.setColumnWidth(2, 20000);

			worksheet.setColumnWidth(3, 9999);
			worksheet.setColumnWidth(4, 9999);
			worksheet.setColumnWidth(5, 9999);
			worksheet.setColumnWidth(6, 9999);
			worksheet.setColumnWidth(7, 10000);
			worksheet.setColumnWidth(8, 9000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" OldStock Dispatches Of Distillery/Brewery From "+ " " + Utility.convertUtilDateToSQLDate(act.getFromDate())
					+ " " + " To " + " " + Utility.convertUtilDateToSQLDate(act.getToDate()));
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

			XSSFCell cellhead3 = rowhead.createCell((int) 2);// /////
			cellhead3.setCellValue("Distillery / Brewery");
			cellhead3.setCellStyle(cellStyle);

			XSSFCell cellhead4 = rowhead.createCell((int) 3);
			cellhead4.setCellValue("FL Sale(BL)");
			cellhead4.setCellStyle(cellStyle);

			XSSFCell cellhead5 = rowhead.createCell((int) 4);
			cellhead5.setCellValue("FL Duty");
			cellhead5.setCellStyle(cellStyle);

			XSSFCell cellhead6 = rowhead.createCell((int) 5);
			cellhead6.setCellValue("Beer Sale(BL)");
			cellhead6.setCellStyle(cellStyle);

			XSSFCell cellhead7 = rowhead.createCell((int) 6);
			cellhead7.setCellValue("Beer Duty");
			cellhead7.setCellStyle(cellStyle);

			/*XSSFCell cellhead8 = rowhead.createCell((int) 7);
			cellhead8.setCellValue("Spirit Sale(BL)");
			cellhead8.setCellStyle(cellStyle);

			XSSFCell cellhead9 = rowhead.createCell((int) 8);
			cellhead9.setCellValue("Spirit Duty");
			cellhead9.setCellStyle(cellStyle);

			XSSFCell cellhead10 = rowhead.createCell((int) 9);
			cellhead10.setCellValue("CL Sale(BL)");
			cellhead10.setCellStyle(cellStyle);

			XSSFCell cellhead11 = rowhead.createCell((int) 10);
			cellhead11.setCellValue("CL Duty");
			cellhead11.setCellStyle(cellStyle);*/

			while (rs.next()) {

				Date dat = Utility.convertSqlDateToUtilDate(rs.getDate("dt"));
				DateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy");
				date = formatter.format(dat);

				/*spirit_sale = spirit_sale + rs.getDouble("spirit_sale");
				spirit_duty = spirit_duty + rs.getDouble("spirit_duty");

				cl_duty = cl_duty + rs.getDouble("cl_duty");
				cl_sale = cl_sale + rs.getDouble("cl_sale");*/

				fl_sale = fl_sale + rs.getDouble("fl_sale");
				fl_duty = fl_duty + rs.getDouble("fl_duty");

				beer_sale = beer_sale + rs.getDouble("beer_sale");				
				beer_duty = beer_duty + rs.getDouble("beer_duty");
				
				k++;

				XSSFRow row1 = worksheet.createRow((int) k);

				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(k-1);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(date);

				XSSFCell cellK1 = row1.createCell((int) 2);// ///
				cellK1.setCellValue(rs.getString("dist_name"));

				XSSFCell cellC1 = row1.createCell((int) 3);
				cellC1.setCellValue(rs.getDouble("fl_sale"));

				XSSFCell cellD1 = row1.createCell((int) 4);
				cellD1.setCellValue(rs.getDouble("fl_duty"));

				XSSFCell cellE1 = row1.createCell((int) 5);
				cellE1.setCellValue(rs.getDouble("beer_sale"));

				XSSFCell cellF1 = row1.createCell((int) 6);
				cellF1.setCellValue(rs.getDouble("beer_duty"));

				/*XSSFCell cellG1 = row1.createCell((int) 7);
				cellG1.setCellValue(rs.getString("spirit_sale"));

				XSSFCell cellH1 = row1.createCell((int) 8);
				cellH1.setCellValue(rs.getString("spirit_duty"));

				XSSFCell cellI1 = row1.createCell((int) 9);
				cellI1.setCellValue(rs.getString("cl_sale"));

				XSSFCell cellJ1 = row1.createCell((int) 10);
				cellJ1.setCellValue(rs.getString("cl_duty"));*/

				

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;
			fileOut = new FileOutputStream(relativePath+ "//ExciseUp//MIS//Excel//" + n+ "DispatchStockReportDistWiseOLDSTOCK.xls");
			act.setExlname(n + "DispatchStockReportDistWiseOLDSTOCK");

			XSSFRow row1 = worksheet.createRow((int) k + 1);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue(" ");
			cellA1.setCellStyle(cellStyle);

			XSSFCell cellA2 = row1.createCell((int) 1);
			cellA2.setCellValue(" ");
			cellA2.setCellStyle(cellStyle);

			XSSFCell cellA3 = row1.createCell((int) 2);
			cellA3.setCellValue("Total ");
			cellA3.setCellStyle(cellStyle);

			XSSFCell cellA4 = row1.createCell((int) 3);
			cellA4.setCellValue(fl_sale);
			cellA4.setCellStyle(cellStyle);

			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(fl_duty);
			cellA5.setCellStyle(cellStyle);

			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(beer_sale);
			cellA6.setCellStyle(cellStyle);

			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(beer_duty);
			cellA7.setCellStyle(cellStyle);

			/*XSSFCell cellA8 = row1.createCell((int) 7);
			cellA8.setCellValue(spirit_sale);
			cellA8.setCellStyle(cellStyle);

			XSSFCell cellA9 = row1.createCell((int) 8);
			cellA9.setCellValue(spirit_duty);
			cellA9.setCellStyle(cellStyle);

			XSSFCell cellA10 = row1.createCell((int) 9);
			cellA10.setCellValue(cl_sale);
			cellA10.setCellStyle(cellStyle);

			XSSFCell cellA11 = row1.createCell((int) 10);
			cellA11.setCellValue(cl_duty);
			cellA11.setCellStyle(cellStyle);*/
			
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
	
			flag = true;
			act.setExcelFlag(true);
			con.close();
		} catch (Exception e) {
			// System.out.println("xls2" + e.getMessage());
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

	// ------------------------------get distillery list---------------------

	public ArrayList getDistilleryList(DispatchReportDIST_BRE_OLDSTOCK_Action act) {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue(0);
		list.add(item);

		try {
			String query = " SELECT vch_undertaking_name, int_app_id_f FROM public.dis_mst_pd1_pd2_lic "
					+ " ORDER BY vch_undertaking_name ";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			// pstmt.setInt(1,id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("int_app_id_f"));
				item.setLabel(rs.getString("vch_undertaking_name"));

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

	// ------------------------------get brewery list---------------------

	public ArrayList getBreweryList(DispatchReportDIST_BRE_OLDSTOCK_Action act) {

		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		SelectItem item = new SelectItem();
		item.setLabel("--select--");
		item.setValue(0);
		list.add(item);

		try {
			String query = " SELECT brewery_nm, vch_app_id_f FROM public.bre_mst_b1_lic ORDER BY brewery_nm ";

			conn = ConnectionToDataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			// pstmt.setInt(1,id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new SelectItem();

				item.setValue(rs.getString("vch_app_id_f"));
				item.setLabel(rs.getString("brewery_nm"));

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
