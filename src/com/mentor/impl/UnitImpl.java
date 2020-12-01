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

import com.mentor.action.UnitAction;
import com.mentor.resource.ConnectionToDataBase;
import com.mentor.utility.Constants;
import com.mentor.utility.Utility;

public class UnitImpl {

	public String printReport(UnitAction action) {
		System.out.println("Impl check");

		String mypath = Constants.JBOSS_SERVER_PATH + Constants.JBOSS_LINX_PATH;
		String relativePath = mypath + File.separator + "ExciseUp"
				+ File.separator + "reports";
		System.out.println("====--------------------------" + relativePath);
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		PreparedStatement pst = null;
		Connection con = null;
		ResultSet rs = null;
		System.out.println(action.getYear());
		String reportQuery = null;
		try {
			con = ConnectionToDataBase.getConnection();

	
			reportQuery =  "select  x.type,x.unit_name,x.brand_name, x.strength,x.package_name,x.edp,x.duty, x.adduty,x.permit, x.mrp from "+
					" (select  'BWFL' as type,c.vch_firm_name as unit_name, a.brand_name, a.strength, b.package_name, b.edp, b.duty, b.adduty,0 as permit, b.mrp "+
					"		 from      distillery.brand_registration as a, "+
					"		          distillery.packaging_details as b ,bwfl.registration_of_bwfl_lic_holder c "+
					"		 where a.brand_id=b.brand_id_fk AND a.int_bwfl_id=c.int_id AND case when a.brewery_id!=0 then a.license_category!= 'BEER' else 1=1 end "+
					"		 UNION "+
					"		 select  'Brewery' as type,d.brewery_nm as unit_name, a.brand_name, a.strength, b.package_name, b.edp, b.duty, b.adduty,0 as permit, b.mrp "+
					"		 from      distillery.brand_registration as a, "+
					"		          distillery.packaging_details as b , public.bre_mst_b1_lic as d "+
					"		 where  a.brand_id=b.brand_id_fk AND  a.brewery_id=d.vch_app_id_f AND case when a.int_bwfl_id!=0 then a.license_category= 'BEER' else 1=1 end "+
					"		 UNION "+
					"		 select  'FL2D' as type,e.vch_firm_name as unit_name, a.brand_name, a.strength, b.package_name, b.edp, 0 as duty, 0 as adduty,b.permit, b.mrp "+
					"		 from      distillery.brand_registration as a, "+
					"		          distillery.packaging_details as b ,bwfl.registration_of_bwfl_lic_holder c, licence.fl2_2b_2d as e "+
					"		 where  a.brand_id=b.brand_id_fk AND  a.int_fl2d_id=e.int_app_id "+
					"		 UNION "+
					"		 select  'Distillery' as type,f.vch_undertaking_name as unit_name, a.brand_name, a.strength, b.package_name, b.edp, b.duty, b.adduty,0 as permit, b.mrp "+ 
					"		 from      distillery.brand_registration as a, "+
					"		          distillery.packaging_details as b ,bwfl.registration_of_bwfl_lic_holder c, public.dis_mst_pd1_pd2_lic as f "+
					"		 where  a.brand_id=b.brand_id_fk AND  a.distillery_id=f.int_app_id_f "+

					"		 UNION "+
					"		 select  'FL2A' as type,f.vch_firm_name as unit_name, a.brand_name, a.strength, b.package_name, b.edp, b.duty, b.adduty,0 as permit, b.mrp "+
					"		 from      distillery.brand_registration as a, "+
					"		          distillery.packaging_details as b ,licence.fl2_2b_2d as f "+
					"		 where  a.brand_id=b.brand_id_fk AND  a.int_fl2a_id=f.int_app_id ) as x "+
					"		 order by x.unit_name,x.brand_name ; ";
			
			
			
			/* reportQuery = " select x.type,x.unit_name,x.brand_name, x.strength,x.package_name,x.edp,x.duty, x.adduty, x.mrp from "+

	 " (select distinct 'BWFL' as type,c.vch_firm_name as unit_name, a.brand_name, a.strength, b.package_name, b.edp, b.duty, b.adduty, b.mrp "+
	 " from      distillery.brand_registration as a, "+
	  "         distillery.packaging_details as b ,bwfl.registration_of_bwfl_lic_holder c, public.mst_season as y "+
	 " where  a.brand_id=b.brand_id_fk AND a.int_bwfl_id=c.int_id  "+

	 " UNION "+
	  
	 " select distinct 'Brewery' as type,d.brewery_nm as unit_name, a.brand_name, a.strength, b.package_name, b.edp, b.duty, b.adduty, b.mrp "+
	 " from      distillery.brand_registration as a,"+
	 "          distillery.packaging_details as b , public.bre_mst_b1_lic as d, public.mst_season as y "+
	 " where  a.brand_id=b.brand_id_fk AND  a.brewery_id=d.vch_app_id_f  "+
	 "  AND a.license_category = 'BEER'"+

	 " UNION "+
	  
	 " select distinct 'FL2D' as type,e.vch_firm_name as unit_name, a.brand_name, a.strength, b.package_name, b.edp, b.duty, b.adduty, b.mrp "+
	 " from      distillery.brand_registration as a, "+
	  "         distillery.packaging_details as b ,bwfl.registration_of_bwfl_lic_holder c, licence.fl2_2b_2d as e, public.mst_season as y "+
	 " where  a.brand_id=b.brand_id_fk AND  a.int_fl2d_id=e.int_app_id "+

	 " UNION "+

	 " select distinct 'Distillery' as type,f.vch_undertaking_name as unit_name, a.brand_name, a.strength, b.package_name, b.edp, b.duty, b.adduty, b.mrp "+
	 " from      distillery.brand_registration as a, "+
	 "          distillery.packaging_details as b ,bwfl.registration_of_bwfl_lic_holder c, public.dis_mst_pd1_pd2_lic as f, public.mst_season as y "+
	 " where  a.brand_id=b.brand_id_fk AND  a.distillery_id=f.int_app_id_f) as x "+

	 " order by x.unit_name,x.type,x.brand_name "; */
			System.out.println("query" + reportQuery);
			pst = con.prepareStatement(reportQuery);

			rs = pst.executeQuery();
			// if (rs.next())
			// {
			// rs=pst.executeQuery();
			// JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);
			// //jasperReport = (JasperReport)
			// JRLoader.loadObject(relativePath+File.separator+"ExciseUp"
			// +File.separator+"reports"+File.separator+"ApplicationReport.jasper");
			//
			// jasperReport = (JasperReport)
			// JRLoader.loadObject(relativePath+File.separator+
			// "unit_details.jasper");
			//
			// System.out.println("hellooooooooooooooooooooooo");
			// Map parameters = new HashMap();
			//
			// JasperPrint print = JasperFillManager.fillReport(jasperReport,
			// parameters, jrRs);
			// JasperExportManager.exportReportToPdfFile(print,relativePath+File.separator+"UnitReport.pdf");
			// action.setPrintName("UnitReport.pdf");
			// action.setPrintFlag(true);
			// }
			// else {
			// //ResourceUtil.addErrorMessage(Constants.NO_RECORD_FOUND,Constants.NO_RECORD_FOUND);
			// }

			if (rs.next()) {

				rs = pst.executeQuery();
				Map parameters = new HashMap();

				// parameters.put("image", relativePath + File.separator);

				JRResultSetDataSource jrRs = new JRResultSetDataSource(rs);

				jasperReport = (JasperReport) JRLoader.loadObject(relativePath
						+ File.separator + "unit_details.jasper");

				JasperPrint print = JasperFillManager.fillReport(jasperReport,
						parameters, jrRs);
				Random rand = new Random();
				int n = rand.nextInt(250) + 1;

				JasperExportManager.exportReportToPdfFile(print, relativePath
						+ File.separator + "BrandWiseRegistration" + "-" + n + ".pdf");
				action.setPrintName("BrandWiseRegistration" + "-" + n + ".pdf");
				action.setPrintFlag(true);

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

		return "";

	}

	public boolean writeExcel(UnitAction action) {
		Connection con = null;
		String type = "";
		con = ConnectionToDataBase.getConnection();
		String reportQuery = "";

		reportQuery =  "select  x.type,x.unit_name,x.brand_name, x.strength,x.package_name,x.edp,x.duty, x.adduty,x.permit, x.mrp from "+
				" (select  'BWFL' as type,c.vch_firm_name as unit_name, a.brand_name, a.strength, b.package_name, b.edp, b.duty, b.adduty,0 as permit, b.mrp "+
				"		 from      distillery.brand_registration as a, "+
				"		          distillery.packaging_details as b ,bwfl.registration_of_bwfl_lic_holder c "+
				"		 where a.brand_id=b.brand_id_fk AND a.int_bwfl_id=c.int_id AND case when a.brewery_id!=0 then a.license_category!= 'BEER' else 1=1 end "+
				"		 UNION "+
				"		 select  'Brewery' as type,d.brewery_nm as unit_name, a.brand_name, a.strength, b.package_name, b.edp, b.duty, b.adduty,0 as permit, b.mrp "+
				"		 from      distillery.brand_registration as a, "+
				"		          distillery.packaging_details as b , public.bre_mst_b1_lic as d "+
				"		 where  a.brand_id=b.brand_id_fk AND  a.brewery_id=d.vch_app_id_f AND case when a.int_bwfl_id!=0 then a.license_category= 'BEER' else 1=1 end "+
				"		 UNION "+
				"		 select  'FL2D' as type,e.vch_firm_name as unit_name, a.brand_name, a.strength, b.package_name, b.edp, 0 as duty, 0 as adduty,b.permit, b.mrp "+
				"		 from      distillery.brand_registration as a, "+
				"		          distillery.packaging_details as b ,bwfl.registration_of_bwfl_lic_holder c, licence.fl2_2b_2d as e "+
				"		 where  a.brand_id=b.brand_id_fk AND  a.int_fl2d_id=e.int_app_id "+
				"		 UNION "+
				"		 select  'Distillery' as type,f.vch_undertaking_name as unit_name, a.brand_name, a.strength, b.package_name, b.edp, b.duty, b.adduty,0 as permit, b.mrp "+ 
				"		 from      distillery.brand_registration as a, "+
				"		          distillery.packaging_details as b ,bwfl.registration_of_bwfl_lic_holder c, public.dis_mst_pd1_pd2_lic as f "+
				"		 where  a.brand_id=b.brand_id_fk AND  a.distillery_id=f.int_app_id_f "+

				"		 UNION "+
				"		 select  'FL2A' as type,f.vch_firm_name as unit_name, a.brand_name, a.strength, b.package_name, b.edp, b.duty, b.adduty,0 as permit, b.mrp "+
				"		 from      distillery.brand_registration as a, "+
				"		          distillery.packaging_details as b ,licence.fl2_2b_2d as f "+
				"		 where  a.brand_id=b.brand_id_fk AND  a.int_fl2a_id=f.int_app_id ) as x "+
				"		 order by x.unit_name,x.brand_name ; ";
System.out.println("--------- Excel  Distillery  query    --------"
				+ reportQuery);

		String relativePath = Constants.JBOSS_SERVER_PATH
				+ Constants.JBOSS_LINX_PATH;
		FileOutputStream fileOut = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		long k = 0;
		double sumEdp = 0;
		double sumDuty = 0;
		double sumAd = 0;
		double sumMrp = 0;
		double sumPermit = 0;

		double totalsumEdp = 0;
		double totalsumDuty = 0;
		double totalsumAd = 0;
		double totalsumMrp = 0;
		double totalsumPermit = 0;

		try {

			pstmt = con.prepareStatement(reportQuery);
			// pstmt.setDate(1,
			// Utility.convertUtilDateToSQLDate(action.getFromdate()));
			// pstmt.setDate(2,
			// Utility.convertUtilDateToSQLDate(action.getTodate()));
			rs = pstmt.executeQuery();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook
					.createSheet("Brand Wise Registration");
			// CellStyle unlockedCellStyle = workbook.createCellStyle();
			// unlockedCellStyle.setLocked(false);
			// worksheet.protectSheet("UP-EX-MIS");
			worksheet.autoSizeColumn(0);
			worksheet.setColumnWidth(1, 12000);
			worksheet.setColumnWidth(2, 5000);
			worksheet.setColumnWidth(3, 4999);
			worksheet.setColumnWidth(4, 9999);
			worksheet.setColumnWidth(5, 5000);
			worksheet.setColumnWidth(6, 5000);
			worksheet.setColumnWidth(7, 5000);
			worksheet.setColumnWidth(8, 5000);

			XSSFRow rowhead0 = worksheet.createRow((int) 0);
			XSSFCell cellhead0 = rowhead0.createCell((int) 0);
			cellhead0.setCellValue(" Brand Wise Registration");
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

			String temp = "";
			String temp2 = "";
			int l = 0;
			while (rs.next()) {
				l++;
				if (!temp.equalsIgnoreCase(rs.getString("unit_name"))
						|| !temp2.equals(rs.getString("type"))) {
					if (k > 0) {
						k++;
						XSSFRow rheadk = worksheet.createRow((int) k);
						XSSFCell chead0 = rheadk.createCell((int) 0);
						XSSFCell chead4 = rheadk.createCell((int) 4);
						XSSFCell chead5 = rheadk.createCell((int) 5);
						XSSFCell chead6 = rheadk.createCell((int) 6);
						XSSFCell chead7 = rheadk.createCell((int) 7);
						XSSFCell chead8 = rheadk.createCell((int) 8);

						chead0.setCellValue("Total");

						chead4.setCellValue(Math.round(sumEdp*100.0)/100.0);
						// totalsumEdp += sumEdp;
						sumEdp = 0;
						chead5.setCellValue(Math.round(sumDuty*1000.0)/1000.0);
						// totalsumDuty += sumDuty;
						sumDuty = 0;
						chead6.setCellValue(Math.round(sumAd*1000.0)/1000.0);
						// totalsumAd += sumAd;
						sumAd = 0;
						
						chead7.setCellValue(Math.round(sumPermit*1000.0)/1000.0);
						// totalsumMrp += sumMrp;
						sumPermit = 0;
						chead8.setCellValue(Math.round(sumMrp*100.0)/100.0);
						// totalsumMrp += sumMrp;
						sumMrp = 0;

					}
					k++;
					XSSFRow rhead0 = worksheet.createRow((int) k);
					XSSFCell chead0 = rhead0.createCell((int) 0);
					XSSFCell chead1 = rhead0.createCell((int) 3);
					chead0.setCellValue("Unit Name : "
							+ rs.getString("unit_name"));
					chead0.setCellStyle(cellStyl);
					chead1.setCellValue("Unit Type : " + rs.getString("type"));
					chead1.setCellStyle(cellStyl);
					temp = rs.getString("unit_name");
					temp2 = rs.getString("type");

					rhead0.setHeight((short) 700);

					k++;

					XSSFRow rowhead = worksheet.createRow((int) k);

					XSSFCell cellhead1 = rowhead.createCell((int) 0);
					cellhead1.setCellValue("S.No.");

					cellhead1.setCellStyle(cellStyle);

					XSSFCell cellhead2 = rowhead.createCell((int) 1);
					cellhead2.setCellValue("Brand");
					cellhead2.setCellStyle(cellStyle);

					XSSFCell cellhead3 = rowhead.createCell((int) 2);
					cellhead3.setCellValue("Strength");
					cellhead3.setCellStyle(cellStyle);

					XSSFCell cellhead4 = rowhead.createCell((int) 3);
					cellhead4.setCellValue("Size");
					cellhead4.setCellStyle(cellStyle);

					XSSFCell cellhead5 = rowhead.createCell((int) 4);
					cellhead5.setCellValue("EDP");
					cellhead5.setCellStyle(cellStyle);

					XSSFCell cellhead6 = rowhead.createCell((int) 5);
					cellhead6.setCellValue("Duty");
					cellhead6.setCellStyle(cellStyle);

					XSSFCell cellhead7 = rowhead.createCell((int) 6);
					cellhead7.setCellValue("Ad. Duty");
					cellhead7.setCellStyle(cellStyle);
					
					XSSFCell cellhead8 = rowhead.createCell((int) 7);
					cellhead8.setCellValue("Permit");
					cellhead8.setCellStyle(cellStyle);

					XSSFCell cellhead9 = rowhead.createCell((int) 8);
					cellhead9.setCellValue("MRP");
					cellhead9.setCellStyle(cellStyle);
				}
				k++;

				XSSFRow row1 = worksheet.createRow((int) k);
				XSSFCell cellA1 = row1.createCell((int) 0);
				cellA1.setCellValue(l);

				XSSFCell cellB1 = row1.createCell((int) 1);
				cellB1.setCellValue(rs.getString("brand_name"));

				XSSFCell cellC1 = row1.createCell((int) 2);
				cellC1.setCellValue(rs.getString("strength")); // date

				XSSFCell cellD1 = row1.createCell((int) 3);
				cellD1.setCellValue(rs.getString("package_name"));

				XSSFCell cellE1 = row1.createCell((int) 4);
				cellE1.setCellValue(rs.getDouble("edp"));

				if (rs.getString("edp") != null) {
					sumEdp = sumEdp + Double.parseDouble(rs.getString("edp"));
					totalsumEdp = totalsumEdp
							+ Double.parseDouble(rs.getString("edp"));
				}

				XSSFCell cellF1 = row1.createCell((int) 5);
				cellF1.setCellValue(rs.getString("duty"));
				if (rs.getString("duty") != null) {
					sumDuty = sumDuty
							+ Double.parseDouble(rs.getString("duty"));
					totalsumDuty = totalsumDuty
							+ Double.parseDouble(rs.getString("duty"));
				}

				XSSFCell cellG1 = row1.createCell((int) 6);
				cellG1.setCellValue(rs.getString("adduty"));
				if (rs.getString("adduty") != null) {
					sumAd = sumAd + Double.parseDouble(rs.getString("adduty"));
					totalsumAd = totalsumAd
							+ Double.parseDouble(rs.getString("adduty"));
				}
				
				XSSFCell cellH1 = row1.createCell((int) 7);
				cellH1.setCellValue(rs.getString("permit"));
				if (rs.getString("permit") != null) {
					sumPermit = sumPermit + Double.parseDouble(rs.getString("permit"));
					totalsumPermit = totalsumPermit
							+ Double.parseDouble(rs.getString("permit"));
				}

				XSSFCell cellI1 = row1.createCell((int) 8);
				cellI1.setCellValue(rs.getString("mrp"));
				if (rs.getString("mrp") != null) {
					sumMrp = sumMrp + Double.parseDouble(rs.getString("mrp"));
					totalsumMrp = totalsumMrp
							+ Double.parseDouble(rs.getString("mrp"));
				}

			}
			Random rand = new Random();
			int n = rand.nextInt(550) + 1;

			fileOut = new FileOutputStream(relativePath
					+ "//ExciseUp//WholeSale//Excel//"
					+ "BrandWiseRegistration" + n + "Report.xls");
			action.setExlname("BrandWiseRegistration" + n);

			k++;
			XSSFRow rheadk = worksheet.createRow((int) k);
			XSSFCell chead0 = rheadk.createCell((int) 0);
			XSSFCell chead4 = rheadk.createCell((int) 4);
			XSSFCell chead5 = rheadk.createCell((int) 5);
			XSSFCell chead6 = rheadk.createCell((int) 6);
			XSSFCell chead7 = rheadk.createCell((int) 7);
			XSSFCell chead8 = rheadk.createCell((int) 8);

			chead0.setCellValue("Total");
			chead0.setCellStyle(cellStyl);

			chead4.setCellValue(Math.round(sumEdp*100.0)/100.0);
			// totalsumEdp += sumEdp;
			sumEdp = 0;
			chead5.setCellValue(Math.round(sumDuty*1000.0)/1000.0);
			// totalsumDuty += sumDuty;
			sumDuty = 0;
			chead6.setCellValue(Math.round(sumAd*1000.0)/1000.0);
			// totalsumAd += sumAd;
			sumAd = 0;
			
			chead7.setCellValue(Math.round(sumPermit*1000.0)/1000.0);
			// totalsumMrp += sumMrp;
			sumPermit = 0;
			
			chead8.setCellValue(Math.round(sumMrp*100.0)/100.0);
			// totalsumMrp += sumMrp;
			sumMrp = 0;

			XSSFRow row2 = worksheet.createRow((int) k + 1);

			XSSFCell cellA11 = row2.createCell((int) 0);
			cellA11.setCellValue(" ");
			cellA11.setCellStyle(cellStyl);

			XSSFCell cellA51 = row2.createCell((int) 4);
			cellA51.setCellValue(" ");
			cellA51.setCellStyle(cellStyl);

			XSSFCell cellA61 = row2.createCell((int) 5);
			cellA61.setCellValue(" ");
			cellA61.setCellStyle(cellStyl);

			XSSFCell cellA71 = row2.createCell((int) 6);
			cellA71.setCellValue(" ");
			cellA71.setCellStyle(cellStyl);

			XSSFCell cellA81 = row2.createCell((int) 7);
			cellA81.setCellValue(" ");
			cellA81.setCellStyle(cellStyl);
			
			XSSFCell cellA91 = row2.createCell((int) 8);
			cellA91.setCellValue(" ");
			cellA91.setCellStyle(cellStyl);

			XSSFRow row1 = worksheet.createRow((int) k + 2);

			XSSFCell cellA1 = row1.createCell((int) 0);
			cellA1.setCellValue("Grand Total ");
			cellA1.setCellStyle(cellStyl);

			XSSFCell cellA5 = row1.createCell((int) 4);
			cellA5.setCellValue(Math.round(totalsumEdp*100.0)/100.0);
			cellA5.setCellStyle(cellStyl);

			XSSFCell cellA6 = row1.createCell((int) 5);
			cellA6.setCellValue(Math.round(totalsumDuty*1000.0)/1000.0);
			cellA6.setCellStyle(cellStyl);

			XSSFCell cellA7 = row1.createCell((int) 6);
			cellA7.setCellValue(Math.round(totalsumAd*1000.0)/1000.0);
			cellA7.setCellStyle(cellStyl);

			XSSFCell cellA8 = row1.createCell((int) 7);
			cellA8.setCellValue(Math.round(totalsumPermit*1000.0)/1000.0);
			cellA8.setCellStyle(cellStyl);
			
			XSSFCell cellA9 = row1.createCell((int) 8);
			cellA9.setCellValue(Math.round(totalsumMrp*100.0)/100.0);
			cellA9.setCellStyle(cellStyl);

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			flag = true;
			action.setExcelFlag(true);
			con.close();

		} catch (Exception e) {

			System.out.println("xls2" + e.getMessage());
			e.printStackTrace();
		}

		return flag;
	}

}
